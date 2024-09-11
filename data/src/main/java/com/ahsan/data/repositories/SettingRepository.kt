package com.ahsan.data.repositories

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dbPath = context.getDatabasePath("my_db")
    private val storageReference = FirebaseStorage.getInstance().reference

    fun backupData() {
        storageReference.child("DbBackups/${FirebaseAuth.getInstance().currentUser?.email}/user_db.db")
            .putFile(dbPath.toUri())
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Log.d("Backup", it.message.toString())
            }
    }

    fun loadBackup() {
        val dbPath = context.getDatabasePath("my_db")
        storageReference.child("DbBackups/${FirebaseAuth.getInstance().currentUser?.email}/user_db.db")
            .getFile(dbPath).addOnSuccessListener {

            }
    }

    fun hasBackup(): Flow<Boolean?> {
        val backupFoundState = MutableStateFlow<Boolean?>(null)
        storageReference.child("DbBackups/${FirebaseAuth.getInstance().currentUser?.email}/user_db.db")
            .downloadUrl.addOnSuccessListener {
                backupFoundState.value = true
            }
            .addOnFailureListener {
                backupFoundState.value = false
            }
        return backupFoundState
    }
}