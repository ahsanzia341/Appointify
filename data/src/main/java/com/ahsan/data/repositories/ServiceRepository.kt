package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Service
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getAll(): List<Service>{
        return db.getServiceDao().getAll()
    }
}