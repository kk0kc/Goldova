package com.example.fintechfilms.convertor

import androidx.room.TypeConverter
import com.example.fintechfilms.response.detail.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryListConverter {
    @TypeConverter
    fun fromString(value: String): List<Country> {
        val listType = object : TypeToken<List<Country>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Country>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
