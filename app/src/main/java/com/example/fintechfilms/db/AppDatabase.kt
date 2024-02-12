package com.example.fintechfilms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintechfilms.convertor.CountryListConverter
import com.example.fintechfilms.convertor.GenreListConverter
import com.example.fintechfilms.db.dao.FavouriteDao
import com.example.fintechfilms.db.entity.Favourite

@Database(entities = [Favourite::class], version = 1, exportSchema = false)
@TypeConverters(CountryListConverter::class, GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavourite(): FavouriteDao
}
