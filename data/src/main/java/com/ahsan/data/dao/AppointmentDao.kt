package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient

@Dao
interface AppointmentDao {
    @Transaction
    @Query("Select * from appointment")
    suspend fun getAll(): List<AppointmentAndClient>

    @Transaction
    @Query("Select * from appointment where id == :id")
    suspend fun findById(id: Int): AppointmentAndClient

    @Transaction
    @Query("Select * from appointment where status == 2")
    suspend fun getAppointmentHistory(): List<AppointmentAndClient>

    @Insert
    suspend fun insert(appointment: Appointment)

    @Update
    fun update(appointment: Appointment)

    @Delete
    fun delete(appointment: Appointment)
}