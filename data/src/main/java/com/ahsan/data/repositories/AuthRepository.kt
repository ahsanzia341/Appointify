package com.ahsan.data.repositories

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.ahsan.core.FirestoreConstant
import com.ahsan.data.AppDatabase
import com.ahsan.data.AuthUiState
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val appDatabase: AppDatabase) {

    private val uiState = MutableStateFlow<AuthUiState?>(null)
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun login(email: String, password: String): Flow<AuthUiState?> {
        try{
            auth.signInWithEmailAndPassword(email, password).await()
            if(!isServerDataEmpty()){
                synchronizeData()
            }
            uiState.value = AuthUiState.Success
        }
        catch(e: Exception) {
            uiState.value = AuthUiState.Failure(e.message ?: "Something went wrong.")
        }
        return uiState
    }

    suspend fun register(email: String, password: String): Flow<AuthUiState?> {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.sendEmailVerification()
            uiState.value = AuthUiState.Success
        } catch (e: Exception) {
            uiState.value = AuthUiState.Failure(e.message ?: "Something went wrong.")
        }
        return uiState
    }

    suspend fun isServerDataEmpty(): Boolean {
        val businesses = firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).whereEqualTo("ownerId", auth.currentUser?.uid).get().await()
        return businesses.isEmpty
    }

    /*private suspend fun isDatabaseEmpty(): Boolean {
        val clientCount = appDatabase.getClientDao().getClientCount()
        val serviceCount = appDatabase.getServiceDao().getServiceCount()
        return clientCount == 0 && serviceCount == 0
    }*/

    private suspend fun synchronizeData(){
        val businesses = firestore.collection(FirestoreConstant.BUSINESS_COLLECTION).whereEqualTo("ownerId", auth.currentUser?.uid).get().await()
        val dbClients = appDatabase.getClientDao().getAllUnSynchronized()
        businesses.documents.forEach { business ->
            val clients = firestore.collection(FirestoreConstant.CLIENT_COLLECTION).whereNotIn("id", dbClients.map { it.id }).whereEqualTo("businessId", business.id).get().await().toObjects(Client::class.java)
            val services = firestore.collection(FirestoreConstant.SERVICE_COLLECTION).whereEqualTo("businessId", business.id).get().await().toObjects(Service::class.java)
            val appointments = firestore.collection(FirestoreConstant.APPOINTMENT_COLLECTION).whereEqualTo("businessId", business.id).get().await().toObjects(Appointment::class.java)
            appDatabase.getClientDao().insertAll(clients)
            appDatabase.getServiceDao().insertAll(services)
            appDatabase.getAppointmentDao().insertAll(appointments)
            /*val batchClients = dbClients.windowed(25, partialWindows = true)
            batchClients.forEach { clientBatch ->
                val clients = firestore.collection(FirestoreConstant.CLIENT_COLLECTION).whereNotIn("id", clientBatch.map { it.id }).whereEqualTo("businessId", business.id).get().await().toObjects(Client::class.java)
                appDatabase.getClientDao().insertAll(clients)
                clients.map { it.isSynchronized = true }
                appDatabase.getClientDao().updateList(clients)
            }*/
        }
    }

    suspend fun signInWithGoogle(context: Context): Flow<AuthUiState?> {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId("260728293947-045ipi5kh386gleh506q27f756j1o1us.apps.googleusercontent.com")
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = CredentialManager.create(context).getCredential(context, request)
        return handleSignIn(result.credential)
    }

    private suspend fun handleSignIn(credential: Credential): Flow<AuthUiState?> {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            // Sign in to Firebase with using the token
            return firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        }
        else{
            uiState.value = AuthUiState.Failure("Something went wrong.")
        }
        return uiState
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String): Flow<AuthUiState?> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try{
            auth.signInWithCredential(credential).await()
            if(!isServerDataEmpty()){
                synchronizeData()
            }
            uiState.value = AuthUiState.Success
        }
        catch (e: Exception){
            uiState.value = AuthUiState.Failure(e.toString())
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

    suspend fun changePassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)?.await()
    }

    fun isLoggedIn(): String? {
        return if (auth.currentUser == null) null else auth.currentUser?.email
    }

    fun isEmailVerified(): Boolean {
        return auth.currentUser?.isEmailVerified == true
    }

    fun logout() {
        auth.signOut()
    }

    fun delete() {
        val storageReference = FirebaseStorage.getInstance().reference
        storageReference.child("DbBackups/${auth.currentUser?.email}/user_db.db").delete()
        auth.currentUser?.delete()
    }
}