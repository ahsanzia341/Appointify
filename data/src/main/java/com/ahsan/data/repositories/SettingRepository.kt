package com.ahsan.data.repositories

import android.content.Context
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context){
    fun backupData(){
        val dbPath = context.getDatabasePath("my_db")
        val storageReference = FirebaseStorage.getInstance().reference
        storageReference.child("DbBackups").child("${FirebaseAuth.getInstance().currentUser?.email}").putFile(dbPath.toUri())
            .addOnSuccessListener {

            }
    }
}