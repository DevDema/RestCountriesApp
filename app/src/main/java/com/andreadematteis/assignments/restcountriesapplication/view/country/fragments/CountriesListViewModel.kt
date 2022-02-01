package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andreadematteis.assignments.restcountriesapplication.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    application: Application,
    private val countriesRepository: CountryRepository
) : AndroidViewModel(application) {

    private val mutableIdImage = MutableLiveData<Pair<Long, Bitmap>>()

    fun startWatchingImageCache() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val missingImagesCountry = countriesRepository.getAll().map { it.id }

                while (true) {
                    if(missingImagesCountry.isEmpty()) {
                        break
                    }

                    for (countryId in missingImagesCountry) {
                        val file = File(getApplication<Application>().cacheDir, "$countryId.png")

                        if (!file.exists()) {
                            continue
                        }

                        BitmapFactory.decodeFile(
                            file.absolutePath
                        )?.let {
                            withContext(Dispatchers.Main) {
                                mutableIdImage.value =
                                    countryId to it
                            }
                        }
                    }

                    delay(1000)
                }
            }
        }
    }
}