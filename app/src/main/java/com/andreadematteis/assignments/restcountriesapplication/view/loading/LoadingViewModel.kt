package com.andreadematteis.assignments.restcountriesapplication.view.loading

import android.app.Application
import androidx.lifecycle.*
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

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
                    val countriesMap = withContext(Dispatchers.IO) {
                        delay(1000L)

                        countriesRepository.fetchAndSaveCountries()
                    }

                    mutableProgressPercentage.value = 100
                    mutableIsLoadingDone.value = if (countriesMap.values.none { it == -1L }) {
                        LoadingStatus.SUCCESS
                    } else {
                        LoadingStatus.FAILED
                    }

                }.exceptionOrNull()?.let {
                    it.printStackTrace()
                    mutableIsLoadingDone.value = LoadingStatus.FAILED
                }
        }
    }

    fun startRandomizeText() {
        isRandomizingText = true

        viewModelScope.launch {
            mutableProgressText.value = LOADING_STRINGS[0]

            var progress = 10
            while (true) {
                if(!isRandomizingText) {
                    break
                }

                val randomText = withContext(Dispatchers.IO) {
                    delay(300L)

                    if(progress < 90) {
                        progress += (2..5).random()
                    }

                    LOADING_STRINGS.random()
                }

                mutableProgressPercentage.value = progress

                val randomCounter = (0..10).random()
                if(randomCounter == 1) {
                    mutableProgressText.value = randomText
                }
            }
        }
    }

    fun stopRandomizeText() {
        this.isRandomizingText = false
    }

        private val LOADING_STRINGS = listOf(
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