package com.ahsan.data.repositories

import com.ahsan.data.AuthUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    private val uiState = MutableStateFlow<AuthUiState?>(null)
    private val auth = FirebaseAuth.getInstance()
    fun login(email: String, password: String): Flow<AuthUiState?> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                uiState.value = AuthUiState.Success
            }.addOnFailureListener {
                uiState.value = AuthUiState.Failure(it.message ?: "Something went wrong.")
            }
        return uiState
    }

    fun register(email: String, password: String): Flow<AuthUiState?> {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.sendEmailVerification()
                uiState.value = AuthUiState.Success
            }.addOnFailureListener {
                uiState.value = AuthUiState.Failure(it.message ?: "Something went wrong.")
            }
        return uiState
    }

    fun resetPassword(email: String): Flow<AuthUiState?> {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                uiState.value = AuthUiState.Success
            }
            .addOnFailureListener {
                uiState.value = AuthUiState.Failure(it.message.toString())
            }
        return uiState
    }

    fun isLoggedIn(): String? {
        return if(auth.currentUser == null) null else auth.currentUser?.email
    }

    fun isEmailVerified(): Boolean{
        return auth.currentUser?.isEmailVerified == true
    }

    fun logout(){
        auth.signOut()
    }

    fun delete(){
        val storageReference = FirebaseStorage.getInstance().reference
        storageReference.child("DbBackups/${auth.currentUser?.email}/user_db.db").delete()
        auth.currentUser?.delete()
    }
}