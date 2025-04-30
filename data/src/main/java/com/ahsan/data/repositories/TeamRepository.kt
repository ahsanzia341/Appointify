package com.ahsan.data.repositories

import com.ahsan.core.FirestoreConstant
import com.ahsan.data.models.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TeamRepository {
    val firestore = FirebaseFirestore.getInstance()
    suspend fun create(team: Team){

        firestore.collection(FirestoreConstant.TEAM_COLLECTION).add(team).await()
    }

    suspend fun getAll(): List<Team>{
        return firestore.collection(FirestoreConstant.TEAM_COLLECTION).get().await().toObjects(Team::class.java)
    }
}