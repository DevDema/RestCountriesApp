package com.andreadematteis.assignments.restcountriesapplication.view.loading

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.repositories.CountryRepository
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.room.utils.toEntity
import com.andreadematteis.assignments.restcountriesapplication.room.utils.toModel
import com.andreadematteis.assignments.restcountriesapplication.utils.toTwoDecimals
import com.andreadematteis.assignments.restcountriesapplication.worker.DownloadImagesForCountryWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class LoadingViewModel @Inject constructor(
    application: Application,
    private val countriesRepository: CountryRepository
) : AndroidViewModel(application) {

    private var isRandomizingText = false

    private val mutableIsLoadingDone = MutableLiveData<LoadingStatus>()
    private val mutableProgressText = MutableLiveData<String>()
    private val mutableProgressPercentage = MutableLiveData<Int>()

    val isLoadingDone: LiveData<LoadingStatus>
        get() = mutableIsLoadingDone
    val progressText: LiveData<String>
        get() = mutableProgressText
    val progressPercentage: LiveData<Int>
        get() = mutableProgressPercentage

    fun loadCountries() {
        viewModelScope.launch {
            mutableIsLoadingDone.value = LoadingStatus.IN_PROGRESS

            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    fetchAndSaveCountries()

                    startWorker(countriesRepository.getAll())
                    delay(1000L)
                }

                mutableProgressPercentage.value = 100
                mutableIsLoadingDone.value = LoadingStatus.SUCCESS

            }.exceptionOrNull()?.let {
                it.printStackTrace()
                if (countriesRepository.getAll().isEmpty()) {
                    mutableIsLoadingDone.value = LoadingStatus.FAILED_BUT_NO_COUNTRIES
                } else {
                    mutableIsLoadingDone.value = LoadingStatus.FAILED
                }
            }
        }
    }

    private suspend fun fetchAndSaveCountries() = mutableMapOf<Country, Long>().apply {
        withContext(Dispatchers.Main) {
            mutableProgressPercentage.value = 0
        }

        val countryList = countriesRepository.fetchCountries().sortedBy { it.name.common }

        withContext(Dispatchers.Main) {
            mutableProgressPercentage.value = 10
        }

        val savedCountryEntityList = countriesRepository.getAll().sortedBy { it.name }

        val countryListToRemove = savedCountryEntityList - countryList.map { it.toEntity() }

        countryListToRemove.forEach {
            countriesRepository.removeCountry(it)
        }

        withContext(Dispatchers.Main) {
            mutableProgressPercentage.value = 15
        }

        val countryListToSave = getCountriesToSave(countryList, savedCountryEntityList)

        withContext(Dispatchers.Main) {
            mutableProgressPercentage.value = 20
        }

        val toSaveSize = countryListToSave.size.toFloat()

        if (toSaveSize > 0) {
            val incrementPercentage = (80F / toSaveSize).toTwoDecimals()
            var completionPercentage = 20F

            countryListToSave.forEach {
                val savedCountryId = countriesRepository.saveCountry(it).also { id ->
                    if (id == -1L) {
                        Log.w(
                            javaClass.simpleName,
                            "Failed to save country ${it.name.common}, Room returned -1."
                        )

                        withContext(Dispatchers.Main) {
                            mutableIsLoadingDone.value = LoadingStatus.FAILED
                        }

                        return@apply
                    } else if (id == -2L) {
                        Log.i(
                            javaClass.simpleName,
                            "Ignoring country ${it.name.common}, no changes detected."
                        )
                    }
                }

                put(it, savedCountryId)

                completionPercentage += incrementPercentage

                withContext(Dispatchers.Main) {
                    mutableProgressPercentage.value = completionPercentage.roundToInt()
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                mutableProgressPercentage.value = 100
            }
        }
    }

    private fun startWorker(countryList: List<CountryEntity>) {
        val dataFlags = Data.Builder().apply {
            putString(
                DownloadImagesForCountryWorker.BASE_URL_KEY,
                DownloadImagesForCountryWorker.FLAGS_BASE_URL
            )

            countryList

                .forEach {
                putString("${it.id}-flag", it.flagsPng)
            }
        }.build()
        val dataCoats = Data.Builder().apply {
            putString(
                DownloadImagesForCountryWorker.BASE_URL_KEY,
                DownloadImagesForCountryWorker.COATS_BASE_URL
            )

            countryList.forEach {
                putString("${it.id}-coat", it.coatOfArmsPng)
            }
        }.build()

        WorkManager.getInstance(getApplication())
            .beginWith(
                OneTimeWorkRequest.Builder(DownloadImagesForCountryWorker::class.java)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(dataFlags)
                    .build()
            ).then(
                OneTimeWorkRequest.Builder(DownloadImagesForCountryWorker::class.java)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(dataCoats)
                    .build()
            )
            .enqueue()
    }

    private fun getCountriesToSave(
        countryList: List<Country>,
        savedCountryEntityList: List<CountryEntity>
    ): List<Country> {
        val savedCountryList = savedCountryEntityList.map { it.toModel() }
        val modified = countryList
            .zip(savedCountryList)
            .asSequence()
            .filter { pair -> pair.first != pair.second }
            .map { it.first }
            .toList()
        val added = countryList - savedCountryList
        return added + modified
    }

    fun startRandomizeText() {
        isRandomizingText = true

        viewModelScope.launch {
            mutableProgressText.value = loadingStrings[0]

            while (true) {
                if (!isRandomizingText) {
                    break
                }

                val randomText = withContext(Dispatchers.IO) {
                    delay((0..20).random() * 500L)

                    loadingStrings.random()
                }

                mutableProgressText.value = randomText

            }
        }
    }

    fun stopRandomizeText() {
        this.isRandomizingText = false
    }

    private val loadingStrings = listOf(
        application.getString(R.string.countries_loading),
        application.getString(R.string.delicious_cookie_loading),
        application.getString(R.string.world_hunger_loading),
        application.getString(R.string.doing_something_loading),
        application.getString(R.string.server_chatting_loading),
        application.getString(R.string.lunchbox_loading),
        application.getString(R.string.six_pm_loading),
        application.getString(R.string.cute_puppies_loading),
        application.getString(R.string.cute_cats_loading),
        application.getString(R.string.generic_info_loading),
    )
}