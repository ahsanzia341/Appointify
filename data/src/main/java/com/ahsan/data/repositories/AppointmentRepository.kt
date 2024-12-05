package com.ahsan.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ahsan.data.AlarmReceiver
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.AppointmentServiceCrossRef
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

class AppointmentRepository @Inject constructor(@ApplicationContext val context: Context, private val db: AppDatabase, private val alarmManager: AlarmManager) {
    suspend fun insert(appointment: Appointment, services: List<Int>) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message","Your ${appointment.title} appointment is starting in 15 minutes")
        }.let { intent ->
            PendingIntent.getBroadcast(context, appointment.appointmentId, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            appointment.startDate?.time!! - 15 * 60 * 1000,
            alarmIntent
        )
        val id = db.getAppointmentDao().insert(appointment)
        db.getAppointmentDao().insertServices(services.map { AppointmentServiceCrossRef(id.toInt(), it) })
    }

    suspend fun cancel(appointment: Appointment){
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message","Your ${appointment.title} appointment is starting in 15 minutes")
        }.let { intent ->
            PendingIntent.getBroadcast(context, appointment.appointmentId, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        alarmManager.cancel(
            alarmIntent
        )
        db.getAppointmentDao().delete(appointment)
    }

    suspend fun getAll(): List<AppointmentAndClient> {
        return db.getAppointmentDao().getAllUpcoming(Date().time)
    }

    suspend fun update(appointment: Appointment) {
        db.getAppointmentDao().update(appointment)
    }

    suspend fun findById(id: Int): AppointmentAndClient {
        return db.getAppointmentDao().findById(id)
    }

    suspend fun getAppointmentHistory(): List<AppointmentAndClient> {
        return db.getAppointmentDao().getAppointmentHistory(Date().time)
    }
}