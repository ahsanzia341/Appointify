package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.AppointmentServiceCrossRef

@Dao
interface AppointmentDao {
    @Transaction
    @Query("Select * from appointment where startDate > :currentTimeInMillis and status != 'CANCELED'")
    suspend fun getAllUpcoming(currentTimeInMillis: Long): List<AppointmentAndClient>

    @Transaction
    @Query("Select * from appointment where appointmentId == :id")
    suspend fun findById(id: Int): AppointmentAndClient

    @Transaction
    @Query("Select * from appointment where startDate < :currentTimeInMillis or status == 'CANCELED'")
    suspend fun getAppointmentHistory(currentTimeInMillis: Long): List<AppointmentAndClient>

    @Query("Select * from appointment")
    suspend fun getAll(): List<Appointment>

    @Insert
    suspend fun insertServices(services: List<AppointmentServiceCrossRef>)

    @Insert
    suspend fun insert(appointment: Appointment): Long

    @Insert
    suspend fun insertAll(appointments: List<Appointment>)

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("Delete from appointment")
    suspend fun deleteAll()
}