package com.ahsan.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(foreignKeys = [
    ForeignKey(entity = Client::class, parentColumns = ["id"], childColumns = ["clientId"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)
])
data class Appointment(@PrimaryKey(autoGenerate = true) val appointmentId: Int = 0, val title: String = "",
                       @ColumnInfo(index = true) val clientId: Int = 0, val startDate: Date? = null, val endDate: Date? = null, val location: String = "",
                       val notes: String = "", var status: AppointmentStatus = AppointmentStatus.NOT_STARTED, val createdAt: Date = Date())

enum class AppointmentStatus{
    NOT_STARTED,
    STARTED,
    ENDED,
    CANCELED
}