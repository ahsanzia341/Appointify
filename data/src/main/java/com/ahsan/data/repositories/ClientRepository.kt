package com.ahsan.data.repositories

import com.ahsan.core.FirestoreConstant.CLIENT_COLLECTION
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Client
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientRepository @Inject constructor(private val db: AppDatabase) {
    val firestore = FirebaseFirestore.getInstance()
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
        try {
            val business = BusinessRepository().get()
            if (business != null) {
                val clientDocs =
                    firestore.collection(CLIENT_COLLECTION).whereEqualTo("businessId", business.id)
                        .get().await()
                clientDocs.documents.forEach {
                    if (it.id == client.id.toString()) {
                        it.reference.delete().await()
                    }
                }
                db.getClientDao().delete(client)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun findById(id: Int): Client {
        return db.getClientDao().findById(id)
    }
}