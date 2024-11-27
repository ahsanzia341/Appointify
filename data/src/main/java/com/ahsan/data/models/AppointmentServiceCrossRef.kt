package com.ahsan.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["appointmentId", "serviceId"])
data class AppointmentServiceCrossRef(@ColumnInfo(index = true) val appointmentId: Int, @ColumnInfo(index = true) val serviceId: Int)
