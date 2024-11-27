package com.ahsan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Appointment(@PrimaryKey(autoGenerate = true) val id: Int = 0, val title: String = "",
                       val clientId: Int = 0, val services: List<Service> = listOf(), val startDate: Date? = null, val endDate: Date? = null,
                       val location: String = "", val notes: String = "", var status: AppointmentStatus = AppointmentStatus.NOT_STARTED, val createdAt: Date = Date())

enum class AppointmentStatus{
    NOT_STARTED,
    STARTED,
    ENDED,
    CANCELED
}