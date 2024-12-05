package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getAll(): List<ServiceAndCurrency> {
        return db.getServiceDao().getAllServicesWithCurrency()
    }

    suspend fun getServiceCount(): Int{
        return db.getServiceDao().getServiceCount()
    }

    suspend fun insert(service: Service) {
        if(service.serviceId != 0){
            db.getServiceDao().update(service)
        }
        else{
            db.getServiceDao().insert(service)
        }
    }

    suspend fun delete(service: Service){
        db.getServiceDao().delete(service)
    }

    suspend fun getByIds(list: List<Int>): List<ServiceAndCurrency> {
        return db.getServiceDao().getByIds(list)
    }

    suspend fun findById(id: Int): ServiceAndCurrency{
        return db.getServiceDao().findById(id)
    }
}