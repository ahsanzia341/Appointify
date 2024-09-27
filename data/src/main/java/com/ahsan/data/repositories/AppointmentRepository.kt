package com.ahsan.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ahsan.data.AlarmReceiver
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppointmentRepository @Inject constructor(@ApplicationContext val context: Context, private val db: AppDatabase, private val alarmManager: AlarmManager) {
    suspend fun insert(appointment: Appointment) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message","Your ${appointment.title} appointment is starting in 15 minutes")
        }.let { intent ->
            PendingIntent.getBroadcast(context, appointment.id, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            appointment.startDate.time - 15 * 60 * 1000,
            alarmIntent
        )
        db.getAppointmentDao().insert(appointment)
    }

    suspend fun cancel(appointment: Appointment){
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message","Your ${appointment.title} appointment is starting in 15 minutes")
        }.let { intent ->
            PendingIntent.getBroadcast(context, appointment.id, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        alarmManager.cancel(
            alarmIntent
        )
        db.getAppointmentDao().delete(appointment)
    }

    suspend fun getAll(): List<AppointmentAndClient> {
        return db.getAppointmentDao().getAll()
    }

    suspend fun update(appointment: Appointment) {
        db.getAppointmentDao().update(appointment)
    }

    suspend fun findById(id: Int): AppointmentAndClient {
        return db.getAppointmentDao().findById(id)
    }

    suspend fun getAppointmentHistory(): List<AppointmentAndClient> {
        return db.getAppointmentDao().getAppointmentHistory()
    }
}