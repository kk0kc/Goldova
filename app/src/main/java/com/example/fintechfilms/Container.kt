package com.example.fintechfilms

import com.example.fintechfilms.data.FilmApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

object Container {

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10L,TimeUnit.SECONDS)
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val filmApi = retrofit.create(FilmApi::class.java)
}
