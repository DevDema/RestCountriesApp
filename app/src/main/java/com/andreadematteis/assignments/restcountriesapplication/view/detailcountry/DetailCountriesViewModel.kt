package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailCountriesViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val mutableImage = MutableLiveData<Bitmap?>()
    private val mutableCoatOfArms = MutableLiveData<Bitmap?>()
    private val mutableCountryEntity = MutableLiveData<CountryEntity>()
    private val mutableViewPagerPosition = MutableLiveData<Int>()

    val image: LiveData<Bitmap?>
        get() = mutableImage
    val countryEntity: LiveData<CountryEntity>
        get() = mutableCountryEntity
    val coatOfArms: LiveData<Bitmap?>
        get() = mutableCoatOfArms
    val viewPagerPosition: LiveData<Int>
        get() = mutableViewPagerPosition

    fun setCountryEntity(countryEntity: CountryEntity) = viewModelScope.launch {
        mutableCountryEntity.value = countryEntity
    }

    fun setImage(id: Long, bitmap: Bitmap?) = viewModelScope.launch {
        bitmap?.let {
            mutableImage.value = it
            return@launch
        }

        withContext(Dispatchers.IO) {
            listenForBitmapFile("$id-flag.png", mutableImage)
        }
    }

    fun loadImage(id: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            listenForBitmapFile("$id-coat.png", mutableCoatOfArms)
        }
    }

    private suspend fun listenForBitmapFile(
        fileName: String,
        referenceLiveData: MutableLiveData<Bitmap?>
    ) {
        while (true) {
            delay(1000)

            val file =
                File(getApplication<Application>().cacheDir, fileName)

            if (!file.exists()) {
                withContext(Dispatchers.Main) {
                    referenceLiveData.value = null
                }

                continue
            }

            BitmapFactory.decodeFile(
                file.absolutePath
            )?.let {
                withContext(Dispatchers.Main) {
                    referenceLiveData.value = it
                }
            }

            break
        }
    }

    fun setViewPagerPosition(position: Int) {
        mutableViewPagerPosition.value = position
    }
}