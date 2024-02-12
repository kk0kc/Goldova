package com.example.fintechfilms.data

import com.example.fintechfilms.response.detail.FilmDetailResponse
import com.example.fintechfilms.response.top.FilmTopResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("top?type=TOP_100_POPULAR_FILMS")
    suspend fun getFilms() : FilmTopResponse

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("{id}")
    suspend fun getDescribtion(
        @Path("id") id: Int?
    ) : FilmDetailResponse
}
