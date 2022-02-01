package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

interface CountryAdapterBinder {

    fun onCountryResults(countryEntities: List<CountryWrapper>)

    fun onNoCountry()
}
