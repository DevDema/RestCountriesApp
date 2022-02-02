package com.andreadematteis.assignments.restcountriesapplication.room.model

import android.os.Parcelable
import androidx.room.*
import com.andreadematteis.assignments.restcountriesapplication.room.utils.DoubleListConverters
import com.andreadematteis.assignments.restcountriesapplication.room.utils.StringListConverters
import com.andreadematteis.assignments.restcountriesapplication.utils.toCommaSeparatedString
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "countries_table",
    indices = [Index(value = ["name"], unique = true)]
)
@TypeConverters(StringListConverters::class, DoubleListConverters::class)
@Parcelize
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_id")
    val id: Long,
    @ColumnInfo(name = "alt_spellings")
    val altSpellings: List<String>,
    @ColumnInfo(name = "area")
    val area: Double,
    @ColumnInfo(name = "borders")
    val borders: List<String>,
    @ColumnInfo(name = "capital")
    val capital: List<String>,
    @ColumnInfo(name = "capital_latlng")
    val capitalLatLng: List<Double>,
    @ColumnInfo(name = "car_side")
    val carSide: String,
    @ColumnInfo(name = "car_signs")
    val carSigns: List<String>,
    @ColumnInfo(name = "coat_of_arms_png")
    val coatOfArmsPng: String,
    @ColumnInfo(name = "continents")
    val continents: List<String>,
    @ColumnInfo(name = "demonym_male")
    val demonymMale: String,
    @ColumnInfo(name = "demonym_female")
    val demonymFemale: String,
    @ColumnInfo(name ="fifa")
    val fifa: String,
    @ColumnInfo(name ="flag_emoji")
    val flagEmoji: String,
    @ColumnInfo(name ="flags_svg")
    val flagsPng: String,
    @ColumnInfo(name ="independent")
    val independent: Boolean,
    @ColumnInfo(name ="landlocked")
    val landlocked: Boolean,
    @ColumnInfo(name ="latlng")
    val latlng: List<Double>,
    @ColumnInfo(name ="name")
    val name: String,
    @ColumnInfo(name ="native_name_key")
    val nativeNameKey: String,
    @ColumnInfo(name ="native_name_value")
    val nativeNameValue: String,
    @ColumnInfo(name ="official_name")
    val officialName: String,
    @ColumnInfo(name ="population")
    val population: Int,
    @ColumnInfo(name = "region")
    val region: String,
    @ColumnInfo(name = "startOfWeek")
    val startOfWeek: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "subregion")
    val subregion: String,
    @ColumnInfo(name = "timezones")
    val timezones: List<String>,
    @ColumnInfo(name = "tld")
    val tld: List<String>,
    @ColumnInfo(name = "unMember")
    val unMember: Boolean
): Parcelable {

    val commaSeparatedContinents: String
        get() = continents.toCommaSeparatedString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CountryEntity

        if (altSpellings != other.altSpellings) return false
        if (area != other.area) return false
        if (borders != other.borders) return false
        if (capital != other.capital) return false
        if (capitalLatLng != other.capitalLatLng) return false
        if (carSide != other.carSide) return false
        if (carSigns != other.carSigns) return false
        if (coatOfArmsPng != other.coatOfArmsPng) return false
        if (continents != other.continents) return false
        if (demonymMale != other.demonymMale) return false
        if (demonymFemale != other.demonymFemale) return false
        if (fifa != other.fifa) return false
        if (flagEmoji != other.flagEmoji) return false
        if (flagsPng != other.flagsPng) return false
        if (independent != other.independent) return false
        if (landlocked != other.landlocked) return false
        if (latlng != other.latlng) return false
        if (name != other.name) return false
        if (nativeNameKey != other.nativeNameKey) return false
        if (nativeNameValue != other.nativeNameValue) return false
        if (officialName != other.officialName) return false
        if (population != other.population) return false
        if (region != other.region) return false
        if (startOfWeek != other.startOfWeek) return false
        if (status != other.status) return false
        if (subregion != other.subregion) return false
        if (timezones != other.timezones) return false
        if (tld != other.tld) return false
        if (unMember != other.unMember) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + altSpellings.hashCode()
        result = 31 * result + area.hashCode()
        result = 31 * result + borders.hashCode()
        result = 31 * result + capital.hashCode()
        result = 31 * result + capitalLatLng.hashCode()
        result = 31 * result + carSide.hashCode()
        result = 31 * result + carSigns.hashCode()
        result = 31 * result + coatOfArmsPng.hashCode()
        result = 31 * result + continents.hashCode()
        result = 31 * result + demonymMale.hashCode()
        result = 31 * result + demonymFemale.hashCode()
        result = 31 * result + fifa.hashCode()
        result = 31 * result + flagEmoji.hashCode()
        result = 31 * result + flagsPng.hashCode()
        result = 31 * result + independent.hashCode()
        result = 31 * result + landlocked.hashCode()
        result = 31 * result + latlng.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nativeNameKey.hashCode()
        result = 31 * result + nativeNameValue.hashCode()
        result = 31 * result + officialName.hashCode()
        result = 31 * result + population
        result = 31 * result + region.hashCode()
        result = 31 * result + startOfWeek.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + subregion.hashCode()
        result = 31 * result + timezones.hashCode()
        result = 31 * result + tld.hashCode()
        result = 31 * result + unMember.hashCode()
        return result
    }
}