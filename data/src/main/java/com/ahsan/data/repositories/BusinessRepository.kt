package com.ahsan.data.repositories

import com.ahsan.core.FirestoreConstant
import com.ahsan.data.models.Business
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessRepository @Inject constructor() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun create(business: Business): Boolean {
        if (currentUser == null)
            return false
        business.ownerId = currentUser.uid
        try {
            firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).document(currentUser.uid).set(business).await()
            val id = firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).document(currentUser.uid).get().await().id
            if(business.logo != null){
                val ref = storage.reference
                ref.child("businesses/$id/logo.jpg").putFile(business.logo)
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun update(business: Business) {
        if(currentUser == null)
            return

        firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).document(currentUser.uid).set(business).await()
    }

    suspend fun get(): Business {
        if(currentUser == null)
            return Business()

        return firestore.collection(FirestoreConstant.BUSINESS_COLLECTION)
            .document(currentUser.uid).get().await().toObject(Business::class.java) ?: Business()
    }
}