package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.graphics.Bitmap
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

interface CountryAdapterBinder {

    fun onCountryResults(countryEntities: List<CountryWrapper>)

    fun onNoCountry()

    fun openDetails(countryEntity: CountryEntity, bitmap: Bitmap?)
}
