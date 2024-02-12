package com.example.fintechfilms.response.detail

import com.example.fintechfilms.db.entity.Favourite
import com.google.gson.annotations.SerializedName

data class FilmDetailResponse(
    @SerializedName("countries")
    val countries: List<Country>,
    @SerializedName("description")
    val description: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("year")
    val year: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("kinopoiskId")
    val kinopoiskId: Int
)

fun FilmDetailResponse.toFavourite(): Favourite {
    return Favourite(
        idFilm = this.kinopoiskId,
        description = this.description,
        nameRu = this.nameRu,
        countries = this.countries,
        genres = this.genres,
        posterUrl = this.posterUrl,
        year = this.year
    )
}
