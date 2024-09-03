package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Client
import javax.inject.Inject

class ClientRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun insert(client: Client){
        db.getClientDao().insert(client)
    }

    suspend fun getAll(): List<Client>{
        return db.getClientDao().getAll()
    }

    suspend fun delete(client: Client){
        db.getClientDao().delete(client)
    }
}