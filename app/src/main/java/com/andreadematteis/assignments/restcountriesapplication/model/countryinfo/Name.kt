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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Name

        if (common != other.common) return false
        if (official != other.official) return false

        return true
    }

    override fun hashCode(): Int {
        var result = common.hashCode()
        result = 31 * result + official.hashCode()
        return result
    }
}