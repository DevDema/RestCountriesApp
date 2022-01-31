package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Name(
    @SerializedName("common")
    @Expose
    val common: String,
    @SerializedName("nativeName")
    @Expose
    val nativeName: Pair<String?, String?>?,
    @SerializedName("official")
    @Expose
    val official: String
)