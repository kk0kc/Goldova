package com.example.fintechfilms.convertor

import androidx.room.TypeConverter
import com.example.fintechfilms.response.detail.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreListConverter {
    @TypeConverter
    fun fromString(value: String): List<Genre> {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
