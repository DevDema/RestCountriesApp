package com.andreadematteis.assignments.restcountriesapplication.view.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreadematteis.assignments.restcountriesapplication.repositories.CountryRepository
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    countriesRepository: CountryRepository
): ViewModel() {

    private val mutableSearchText = MutableLiveData<String>()
    private val mutableCountries = MutableLiveData<List<CountryEntity>>()

    val countries: LiveData<List<CountryEntity>>
        get() = mutableCountries
    val searchText: LiveData<String>
        get() = mutableSearchText

    init {
        viewModelScope.launch {
            val countryEntities = withContext(Dispatchers.IO) {
                countriesRepository.getAll()
            }

            mutableCountries.value = countryEntities
        }
    }

    fun setSearchText(text: String) {
        mutableSearchText.value = text
    }
}