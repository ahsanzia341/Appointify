package com.ahsan.data.repositories

import com.ahsan.core.FirestoreConstant
import com.ahsan.data.models.Business
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessRepository @Inject constructor() {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    suspend fun create(business: Business) {
        if(currentUser == null)
            return

        FirebaseFirestore.getInstance().collection(FirestoreConstant.BUSINESS_COLLECTION).document(currentUser.uid).set(business).await()
    }

    suspend fun update(business: Business) {
        if(currentUser == null)
            return

        FirebaseFirestore.getInstance().collection(FirestoreConstant.BUSINESS_COLLECTION).document(currentUser.uid).set(business).await()
    }

    suspend fun get(): Business {
        if(currentUser == null)
            return Business()

        return FirebaseFirestore.getInstance().collection(FirestoreConstant.BUSINESS_COLLECTION)
            .document(currentUser.uid).get().await().toObject(Business::class.java) ?: Business()
    }
}