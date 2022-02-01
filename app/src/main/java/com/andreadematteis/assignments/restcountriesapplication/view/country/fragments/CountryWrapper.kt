package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.graphics.Bitmap
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

class CountryWrapper(
    val countryEntity: CountryEntity,
    var flag: Bitmap?
)