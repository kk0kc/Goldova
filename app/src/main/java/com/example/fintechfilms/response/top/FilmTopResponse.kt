package com.example.fintechfilms.response.top


import com.google.gson.annotations.SerializedName

data class FilmTopResponse(
    @SerializedName("films")
    val films: List<Film>,
    @SerializedName("pagesCount")
    val pagesCount: Int
)
