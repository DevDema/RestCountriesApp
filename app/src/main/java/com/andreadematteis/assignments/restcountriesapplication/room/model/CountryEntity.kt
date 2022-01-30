package com.andreadematteis.assignments.restcountriesapplication.room.model

import android.os.Parcelable
import androidx.room.*
import com.andreadematteis.assignments.restcountriesapplication.room.utils.DoubleListConverters
import com.andreadematteis.assignments.restcountriesapplication.room.utils.StringListConverters
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
    @ColumnInfo(name = "coat_of_arms_svg")
    val coatOfArmsSvg: String,
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
    val flagsSvg: String,
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
): Parcelable