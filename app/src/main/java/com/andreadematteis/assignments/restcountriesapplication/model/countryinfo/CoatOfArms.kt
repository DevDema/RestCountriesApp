package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CoatOfArms(
    @SerializedName("svg")
    @Expose
    val svg: String?
)