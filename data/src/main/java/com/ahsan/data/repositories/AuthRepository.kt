package com.ahsan.data.repositories

import com.ahsan.data.AuthUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()
    fun login(email: String, password: String): Flow<AuthUiState?> {
        val uiState = MutableStateFlow<AuthUiState?>(null)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                uiState.value = AuthUiState.Success
            }.addOnFailureListener {
                uiState.value = AuthUiState.Failure(it.message ?: "Something went wrong.")
            }
        return uiState
    }

    fun register(email: String, password: String): Flow<AuthUiState?> {
        val uiState = MutableStateFlow<AuthUiState?>(null)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.sendEmailVerification()
                uiState.value = AuthUiState.Success
            }.addOnFailureListener {
                uiState.value = AuthUiState.Failure(it.message ?: "Something went wrong.")
            }
        return uiState
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                it.message
            }
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun isEmailVerified(): Boolean{
        return auth.currentUser?.isEmailVerified == true
    }

    fun logout(){
        auth.signOut()
    }

    fun delete(){
        auth.currentUser?.delete()
    }
}