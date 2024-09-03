package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ahsan.data.models.Client

@Dao
interface ClientDao {
    @Query("Select * from client")
    suspend fun getAll(): List<Client>

    @Insert
    suspend fun insert(client: Client)

    @Delete
    suspend fun delete(client: Client)
}