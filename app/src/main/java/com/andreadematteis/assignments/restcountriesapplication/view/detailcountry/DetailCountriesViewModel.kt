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

    private val mutableImage = MutableLiveData<Bitmap>()
    private val mutableCountryEntity = MutableLiveData<CountryEntity>()

    val image: LiveData<Bitmap>
        get() = mutableImage
    val countryEntity: LiveData<CountryEntity>
        get() = mutableCountryEntity

    fun setCountryEntity(countryEntity: CountryEntity) = viewModelScope.launch {
        mutableCountryEntity.value = countryEntity
    }

    fun setImage(id: Long, bitmap: Bitmap?) = viewModelScope.launch {
        if (bitmap != null) {
            mutableImage.value = bitmap!!
        } else {
            withContext(Dispatchers.IO) {
                while (true) {
                    delay(1000)

                    val file =
                        File(getApplication<Application>().cacheDir, "$id.png")

                    if (!file.exists()) {
                        continue
                    }

                    BitmapFactory.decodeFile(
                        file.absolutePath
                    )?.let {
                        withContext(Dispatchers.Main) {
                            mutableImage.value = it
                        }
                    }

                    break
                }
            }
        }

    }

}