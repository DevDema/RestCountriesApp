package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Translation(
    @SerializedName("common")
    @Expose
    val common: String,
    @SerializedName("official")
    @Expose
    val official: String
)