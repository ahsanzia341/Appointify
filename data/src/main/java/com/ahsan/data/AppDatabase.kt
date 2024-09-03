package com.ahsan.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahsan.data.converters.DateConverter
import com.ahsan.data.dao.AppointmentDao
import com.ahsan.data.dao.ClientDao
import com.ahsan.data.dao.ServiceDao
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service

@Database(entities = [Appointment::class, Client::class, Service::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun getAppointmentDao(): AppointmentDao
    abstract fun getClientDao(): ClientDao
    abstract fun getServiceDao(): ServiceDao
}