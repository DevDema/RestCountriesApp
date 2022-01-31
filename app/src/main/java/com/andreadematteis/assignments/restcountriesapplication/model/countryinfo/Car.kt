package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Car(
    @SerializedName("side")
    @Expose
    val side: String?,
    @SerializedName("signs")
    @Expose
    val signs: List<String>?
)