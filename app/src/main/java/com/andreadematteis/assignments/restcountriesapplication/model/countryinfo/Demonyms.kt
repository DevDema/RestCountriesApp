package com.andreadematteis.assignments.restcountriesapplication.model.countryinfo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Demonyms(
    @SerializedName("eng")
    @Expose
    val eng: DemonymEnglish?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Demonyms

        if (eng ?: DemonymEnglish("","") != other.eng) return false

        return true
    }

    override fun hashCode(): Int {
        return eng?.hashCode() ?: 0
    }
}