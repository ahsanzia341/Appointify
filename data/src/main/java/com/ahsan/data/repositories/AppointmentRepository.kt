package com.ahsan.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import javax.inject.Inject

class AppointmentRepository @Inject constructor(private val db: AppDatabase, private val alarmManager: AlarmManager, private val alarmIntent: PendingIntent) {
    suspend fun insert(appointment: Appointment) {
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, appointment.startDate.time, alarmIntent)
        db.getAppointmentDao().insert(appointment)
    }

    suspend fun getAll(): List<AppointmentAndClient> {
        return db.getAppointmentDao().getAll()
    }

    fun update(appointment: Appointment){
        db.getAppointmentDao().update(appointment)
    }

    suspend fun findById(id: Int): AppointmentAndClient{
        return db.getAppointmentDao().findById(id)
    }

    suspend fun getAppointmentHistory(): List<AppointmentAndClient>{
        return db.getAppointmentDao().getAppointmentHistory()
    }
}