package com.andreadematteis.assignments.restcountriesapplication.model


import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.*
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Country(
    @Transient
    val roomId: Long,
    @SerializedName("altSpellings")
    @Expose
    val altSpellings: List<String>?,
    @SerializedName("area")
    @Expose
    val area: Double,
    @SerializedName("borders")
    @Expose
    val borders: List<String>?,
    @SerializedName("capital")
    @Expose
    val capital: List<String>?,
    @SerializedName("capitalInfo")
    @Expose
    val capitalInfo: CapitalInfo?,
    @SerializedName("car")
    @Expose
    val car: Car?,
    @SerializedName("coatOfArms")
    @Expose
    val coatOfArms: CoatOfArms?,
    @SerializedName("continents")
    @Expose
    val continents: List<String>?,
    @SerializedName("demonyms")
    @Expose
    val demonyms: Demonyms?,
    @SerializedName("fifa")
    @Expose
    val fifa: String?,
    @SerializedName("flag")
    @Expose
    val flag: String?,
    @SerializedName("flags")
    @Expose
    val flags: Flags?,
    @SerializedName("independent")
    @Expose
    val independent: Boolean,
    @SerializedName("landlocked")
    @Expose
    val landlocked: Boolean,
    @SerializedName("latlng")
    @Expose
    val latlng: List<Double>?,
    @SerializedName("name")
    @Expose
    var name: Name,
    @SerializedName("population")
    @Expose
    val population: Int,
    @SerializedName("region")
    @Expose
    val region: String?,
    @SerializedName("startOfWeek")
    @Expose
    val startOfWeek: String?,
    @SerializedName("status")
    @Expose
    val status: String?,
    @SerializedName("subregion")
    @Expose
    val subregion: String?,
    @SerializedName("timezones")
    @Expose
    val timezones: List<String>?,
    @SerializedName("tld")
    @Expose
    val tld: List<String>?,
    @SerializedName("unMember")
    @Expose
    val unMember: Boolean
) {

    @SerializedName("currencies")
    @Expose
    var currencies: Map<String, Currency>? = emptyMap()

    @SerializedName("languages")
    @Expose
    var languages: Map<String, String>? = emptyMap()

    @SerializedName("translations")
    @Expose
    var translations: Map<String, Translation>? = emptyMap()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Country

        if (altSpellings ?: emptyList() != other.altSpellings) return false
        if (area != other.area) return false
        if (borders ?: emptyList() != other.borders) return false
        if (capital ?: emptyList() != other.capital) return false
        if (capitalInfo != other.capitalInfo) return false
        if (car != other.car) return false
        if (continents ?: emptyList() != other.continents) return false
        if (demonyms ?: Demonyms(DemonymEnglish("","")) != other.demonyms) return false
        if (fifa ?: "" != other.fifa) return false
        if (flag ?: "" != other.flag) return false
        if (independent != other.independent) return false
        if (landlocked != other.landlocked) return false
        if (latlng ?: emptyList() != other.latlng) return false
        if (name != other.name) return false
        if (population != other.population) return false
        if (region ?: "" != other.region) return false
        if (startOfWeek ?: "" != other.startOfWeek) return false
        if (status ?: "" != other.status) return false
        if (subregion ?: "" != other.subregion) return false
        if (timezones ?: emptyList() != other.timezones) return false
        if (tld ?: emptyList() != other.tld) return false
        if (unMember != other.unMember) return false

        return true
    }

    override fun hashCode(): Int {
        var result = altSpellings?.hashCode() ?: 0
        result = 31 * result + area.hashCode()
        result = 31 * result + (borders?.hashCode() ?: 0)
        result = 31 * result + (capital?.hashCode() ?: 0)
        result = 31 * result + (capitalInfo?.hashCode() ?: 0)
        result = 31 * result + (car?.hashCode() ?: 0)
        result = 31 * result + (continents?.hashCode() ?: 0)
        result = 31 * result + (demonyms?.hashCode() ?: 0)
        result = 31 * result + (fifa?.hashCode() ?: 0)
        result = 31 * result + (flag?.hashCode() ?: 0)
        result = 31 * result + independent.hashCode()
        result = 31 * result + landlocked.hashCode()
        result = 31 * result + (latlng?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + population
        result = 31 * result + (region?.hashCode() ?: 0)
        result = 31 * result + (startOfWeek?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (subregion?.hashCode() ?: 0)
        result = 31 * result + (timezones?.hashCode() ?: 0)
        result = 31 * result + (tld?.hashCode() ?: 0)
        result = 31 * result + unMember.hashCode()
        return result
    }


}