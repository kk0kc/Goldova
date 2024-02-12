package com.example.fintechfilms.db

import android.content.Context
import androidx.room.Room
import com.example.fintechfilms.db.entity.Favourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteRepository(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    private val favouriteDao by lazy {
        db.getFavourite()
    }

    suspend fun saveFavourite(favourite: Favourite) {
        favouriteDao.save(favourite)
    }

    suspend fun getAllFavourite() : List<Favourite>? = favouriteDao.getAll()

    suspend fun getFavouriteById(id: Int) : Favourite = favouriteDao.getById(id)

    suspend fun deleteFavourite(favourite: Favourite) {
        favouriteDao.delete(favourite)
    }

    companion object {
        private const val DATABASE_NAME = "fintech.db"
    }
}
