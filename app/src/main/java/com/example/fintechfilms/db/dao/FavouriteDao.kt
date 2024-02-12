package com.example.fintechfilms.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.fintechfilms.db.entity.Favourite

@Dao
interface FavouriteDao {

    @Insert(onConflict = REPLACE)
    suspend fun save(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    suspend fun getAll() : List<Favourite>?

    @Query("SELECT * FROM favourite WHERE id_film = :id")
    suspend fun getById(id: Int): Favourite

}
