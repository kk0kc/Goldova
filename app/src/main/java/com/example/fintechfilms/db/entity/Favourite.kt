package com.example.fintechfilms.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fintechfilms.convertor.CountryListConverter
import com.example.fintechfilms.convertor.GenreListConverter
import com.example.fintechfilms.response.detail.Country
import com.example.fintechfilms.response.detail.FilmDetailResponse
import com.example.fintechfilms.response.detail.Genre
import com.example.fintechfilms.response.top.Film

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_film")
    val idFilm: Int,
    @ColumnInfo(name = "name_ru")
    val nameRu: String,
    val description: String,
    @TypeConverters(CountryListConverter::class)
    val countries: List<Country>,
    @TypeConverters(GenreListConverter::class)
    val genres: List<Genre>,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    val year: String
)
fun Favourite.toFilm() : Film {
    return Film(
        filmId = this.idFilm,
        nameRu = this.nameRu,
        posterUrl = this.posterUrl,
        year = this.year)
}

fun Favourite.toFilmDetailResponce() : FilmDetailResponse {
    return FilmDetailResponse(
        kinopoiskId = this.idFilm,
        nameRu = this.nameRu,
        posterUrl = this.posterUrl,
        year = this.year,
        countries = this.countries,
        description = this.description,
        genres = this.genres
        )
}
