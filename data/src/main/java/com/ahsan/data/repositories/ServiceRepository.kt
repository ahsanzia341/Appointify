package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getAll(): List<ServiceAndCurrency> {
        return db.getServiceDao().getAll()
    }

    suspend fun insert(service: Service) {
        db.getServiceDao().insert(service)
    }

    suspend fun getByIds(list: List<Int>): List<ServiceAndCurrency> {
        return db.getServiceDao().getByIds(list)
    }
}