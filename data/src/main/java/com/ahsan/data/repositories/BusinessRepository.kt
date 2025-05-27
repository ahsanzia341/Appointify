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
            val businessDoc = firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).document()
            businessDoc.set(business)
            val businessData = businessDoc.get().await()
            businessData.data?.set("id", businessData.id)
            if(business.logo != null){
                val ref = storage.reference
                ref.child("businesses/${businessData.id}/logo.jpg").putFile(business.logo)
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun update(business: Business) {
        if(currentUser == null)
            return

        firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).document(business.id).set(business).await()
    }

    suspend fun get(): Business? {
        if(currentUser == null)
            return null

        val businesses = firestore.collection(FirestoreConstant.BUSINESS_COLLECTION)
            .whereEqualTo("ownerId", currentUser.uid).get().await().map {
                it.toObject(Business::class.java)
            }
        return if(businesses.isEmpty()) null else businesses[0]
    }
}