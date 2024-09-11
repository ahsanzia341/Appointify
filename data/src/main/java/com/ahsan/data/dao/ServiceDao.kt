package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Dao
interface ServiceDao {

    @Query("Select * from service")
    suspend fun getAll(): List<ServiceAndCurrency>

    @Query("Select * from service where id in (:ids)")
    suspend fun getByIds(ids: List<Int>): List<ServiceAndCurrency>

    @Insert
    suspend fun insert(service: Service)

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)
}