package com.example.fintechfilms.response.top


import com.example.fintechfilms.db.entity.Favourite
import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("filmId")
    val filmId: Int,
    @SerializedName("year")
    val year: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
)
