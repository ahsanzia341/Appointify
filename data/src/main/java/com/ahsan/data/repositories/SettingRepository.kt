package com.ahsan.data.repositories

import android.content.Context
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ahsan.core.Constant
import com.ahsan.core.FirestoreConstant.APPOINTMENT_COLLECTION
import com.ahsan.core.FirestoreConstant.CLIENT_COLLECTION
import com.ahsan.core.FirestoreConstant.SERVICE_COLLECTION
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context,
    private val appDatabase: AppDatabase) {

    //private val dbPath = context.getDatabasePath("my_db")
    private val storageReference = FirebaseStorage.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun backupData() {
        val batch = firestore.batch()
        val appointments = appDatabase.getAppointmentDao().getAll()
        val clients = appDatabase.getClientDao().getAll()
        val services = appDatabase.getServiceDao().getAll()
        val appointmentsRef = firestore.collection(APPOINTMENT_COLLECTION)
        val servicesRef = firestore.collection(SERVICE_COLLECTION)
        val clientsRef = firestore.collection(CLIENT_COLLECTION)
        appointmentsRef.get().await().documents.forEach {
            batch.delete(it.reference)
        }
        servicesRef.get().await().documents.forEach {
            batch.delete(it.reference)
        }
        clientsRef.get().await().documents.forEach {
            batch.delete(it.reference)
        }
        appointmentsRef.add(appointments.map { it.userId = currentUser?.uid })
        servicesRef.add(services.map { it.userId = currentUser?.uid })
        clientsRef.add(clients.map { it.userId = currentUser?.uid })
        batch.commit()
        /*appDatabase.close()
        storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
            .putFile(dbPath.toUri()).await()*/


    }

    suspend fun loadBackup() {
        /*val dbPath = context.getDatabasePath("my_db")
        appDatabase.close()
        try{
            storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
                .getFile(dbPath).await()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        appDatabase.getAppointmentDao().deleteAll()
        appDatabase.getServiceDao().deleteAll()
        appDatabase.getClientDao().deleteAll()*/

        val appointments = firestore.collection(APPOINTMENT_COLLECTION).whereEqualTo("userId", currentUser?.uid).get().await().toObjects(Appointment::class.java)
        val clients = firestore.collection(CLIENT_COLLECTION).whereEqualTo("userId", currentUser?.uid).get().await().toObjects(Client::class.java)
        val services = firestore.collection(SERVICE_COLLECTION).whereEqualTo("userId", currentUser?.uid).get().await().toObjects(Service::class.java)
        appDatabase.getAppointmentDao().insertAll(appointments)
        appDatabase.getServiceDao().insertAll(services)
        appDatabase.getClientDao().insertAll(clients)
    }

    suspend fun getLastBackupDate(): Long? {
        return try{
            storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
                .metadata.await().updatedTimeMillis
        } catch(e: Exception){
            null
        }
    }



    fun setDefaultCurrency(currencyId: Int){
        val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        sharedPref.edit().putInt(Constant.CURRENCY_ID, currencyId).apply()
    }

    fun getDefaultCurrency(): Int{
        val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPref.getInt(Constant.CURRENCY_ID, 1)
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
        val request = PeriodicWorkRequestBuilder<ExpeditedWorker>(24, TimeUnit.MINUTES)
            .addTag("backup")
            .build()
        WorkManager.getInstance(context).enqueue(request)
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