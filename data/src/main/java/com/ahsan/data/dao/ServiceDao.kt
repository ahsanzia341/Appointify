package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Dao
interface ServiceDao {

    @Transaction
    @Query("Select * from service")
    suspend fun getAllServicesWithCurrency(): List<ServiceAndCurrency>

    @Transaction
    @Query("Select * from service where serviceId in (:ids)")
    suspend fun getByIds(ids: List<Int>): List<ServiceAndCurrency>

    @Transaction
    @Query("Select * from service where serviceId == :id")
    suspend fun findById(id: Int): ServiceAndCurrency

    @Query("Select * from service")
    suspend fun getAll(): List<Service>

    @Query("Select Count() from service")
    suspend fun getServiceCount(): Int

    @Query("Select * from service where isSynchronized == 0")
    suspend fun getAllUnSynchronized(): List<Service>

    @Insert
    suspend fun insert(service: Service)

    @Insert
    suspend fun insertAll(services: List<Service>)

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)

    @Query("Delete from service")
    suspend fun deleteAll()
}