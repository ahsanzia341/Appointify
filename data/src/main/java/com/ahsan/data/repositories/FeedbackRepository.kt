package com.ahsan.data.repositories

import com.ahsan.core.FirestoreConstant
import com.ahsan.data.models.Feedback
import com.ahsan.data.models.FeedbackCategory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedbackRepository @Inject constructor() {
    val firestore = FirebaseFirestore.getInstance()

    suspend fun post(feedback: Feedback){
        firestore.collection(FirestoreConstant.FEEDBACK_COLLECTION).add(feedback).await()
    }

    suspend fun getCategories(): List<FeedbackCategory>{
        return firestore.collection(FirestoreConstant.FEEDBACK_CATEGORY_COLLECTION).get().await().toObjects(
            FeedbackCategory::class.java)
    }
}