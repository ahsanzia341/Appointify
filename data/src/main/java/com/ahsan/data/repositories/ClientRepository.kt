package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Client
import javax.inject.Inject

class ClientRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun insert(client: Client) {
        if(client.id != 0){
            db.getClientDao().update(client)
        }
        else{
            db.getClientDao().insert(client)
        }
    }

    suspend fun getClientCount(): Int{
        return db.getClientDao().getClientCount()
    }

    suspend fun getAll(): List<Client> {
        return db.getClientDao().getAll()
    }

    suspend fun delete(client: Client) {
        db.getClientDao().delete(client)
    }

    suspend fun findById(id: Int): Client {
        return db.getClientDao().findById(id)
    }
}