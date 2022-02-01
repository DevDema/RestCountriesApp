package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CapitalInfo(
    @SerializedName("latlng")
    @Expose
    val latlng: List<Double>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CapitalInfo

        if (latlng ?: emptyList() != other.latlng) return false

        return true
    }

    override fun hashCode(): Int {
        return latlng?.hashCode() ?: 0
    }
}