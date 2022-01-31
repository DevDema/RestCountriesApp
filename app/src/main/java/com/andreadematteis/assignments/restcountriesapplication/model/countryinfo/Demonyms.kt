package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Demonyms(
    @SerializedName("eng")
    @Expose
    val eng: DemonymEnglish?
)