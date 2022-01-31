package com.andreadematteis.assignments.restcountriesapplication.room.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DoubleListConverters {

    val gson = Gson()

    @TypeConverter
    fun fromValue(value: String): List<Double> {
        return gson.fromJson(value, object : TypeToken<List<Double>>() {}.type)
    }

    @TypeConverter
    fun fromList(list: List<Double>): String {
        return gson.toJson(list)
    }
}