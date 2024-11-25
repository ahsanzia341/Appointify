package com.ahsan.data.repositories

import android.content.Context
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ahsan.data.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context,
    private val appDatabase: AppDatabase) {

    private val dbPath = context.getDatabasePath("my_db")
    private val storageReference = FirebaseStorage.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser

    suspend fun backupData() {

        appDatabase.close()
        storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
            .putFile(dbPath.toUri()).await()
    }

    suspend fun loadBackup() {
        val dbPath = context.getDatabasePath("my_db")
        appDatabase.close()
        try{
            storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
                .getFile(dbPath).await()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun getLastBackupDate(): Long? {
        return try{
            storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
                .metadata.await().updatedTimeMillis
        } catch(e: Exception){
            null
        }
    }

    fun hasBackup(): Flow<Boolean?> {
        val backupFoundState = MutableStateFlow<Boolean?>(null)
        storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
            .downloadUrl.addOnSuccessListener {
                backupFoundState.value = true
            }
            .addOnFailureListener {
                backupFoundState.value = false
            }
        return backupFoundState
    }

    fun scheduleBackup() {
        PeriodicWorkRequestBuilder<ExpeditedWorker>(24, TimeUnit.HOURS)
            .addTag("backup")
            .build()
    }

    fun cancelBackupSchedule(){
        WorkManager.getInstance(context).cancelAllWorkByTag("backup")
    }
}

class ExpeditedWorker @Inject constructor(appContext: Context, workerParams: WorkerParameters, private val appDatabase: AppDatabase):
    CoroutineWorker(appContext, workerParams) {

    private val storageReference = FirebaseStorage.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val dbPath = appContext.getDatabasePath("my_db")


    override suspend fun doWork(): Result {
        appDatabase.close()
        storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
            .putFile(dbPath.toUri()).await()
        return Result.success()
    }
}