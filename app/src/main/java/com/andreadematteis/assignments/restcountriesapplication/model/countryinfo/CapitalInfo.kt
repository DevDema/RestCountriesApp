package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CapitalInfo(
    @SerializedName("latlng")
    @Expose
    val latlng: List<Double>
)