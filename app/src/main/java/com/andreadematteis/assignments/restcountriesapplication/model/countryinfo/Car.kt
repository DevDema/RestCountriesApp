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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (side ?: "" != other.side) return false
        if (signs ?: emptyList() != other.signs) return false

        return true
    }

    override fun hashCode(): Int {
        var result = side?.hashCode() ?: 0
        result = 31 * result + (signs?.hashCode() ?: 0)
        return result
    }
}