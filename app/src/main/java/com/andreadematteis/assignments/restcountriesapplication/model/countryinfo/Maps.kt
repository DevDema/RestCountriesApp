package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Maps(
    @SerializedName("googleMaps")
    @Expose
    val googleMaps: String,
    @SerializedName("openStreetMaps")
    @Expose
    val openStreetMaps: String
)