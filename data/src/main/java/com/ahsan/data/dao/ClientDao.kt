package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client

@Dao
interface ClientDao {
    @Query("Select * from client")
    suspend fun getAll(): List<Client>

    @Query("Select * from client where id == :id")
    suspend fun findById(id: Int): Client

    @Query("Select Count() from client")
    suspend fun getClientCount(): Int

    @Query("Select * from client where isSynchronized == 0")
    suspend fun getAllUnSynchronized(): List<Client>

    @Insert
    suspend fun insert(client: Client)

    @Insert
    suspend fun insertAll(clients: List<Client>)

    @Delete
    suspend fun delete(client: Client)

    @Update
    suspend fun update(client: Client)

    @Update
    suspend fun updateList(clients: List<Client>)

    @Query("Delete from client")
    suspend fun deleteAll()
}