package com.ahsan.data.repositories

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ahsan.core.Constant
import com.ahsan.core.FirestoreConstant.APPOINTMENT_COLLECTION
import com.ahsan.core.FirestoreConstant.BACKUP_DATE_COLLECTION
import com.ahsan.core.FirestoreConstant.BUSINESS_COLLECTION
import com.ahsan.core.FirestoreConstant.CLIENT_COLLECTION
import com.ahsan.core.FirestoreConstant.SERVICE_COLLECTION
import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Business
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingRepository @Inject constructor(@ApplicationContext private val context: Context,
    private val appDatabase: AppDatabase) {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun backupData() {
        val batch = firestore.batch()
        val appointments = appDatabase.getAppointmentDao().getAllUnSynchronized()
        val clients = appDatabase.getClientDao().getAllUnSynchronized()
        val services = appDatabase.getServiceDao().getAllUnSynchronized()
        val appointmentsRef = firestore.collection(APPOINTMENT_COLLECTION).document()
        val servicesRef = firestore.collection(SERVICE_COLLECTION).document()
        val clientsRef = firestore.collection(CLIENT_COLLECTION).document()
        val businessRef =
            firestore.collection(BUSINESS_COLLECTION).whereEqualTo("ownerId", currentUser?.uid)
                .get().await()
        val backupDatesRef = firestore.collection(BACKUP_DATE_COLLECTION)
        if (currentUser == null) {
            return
        }
        businessRef.documents.forEach { business ->
            batch.set(
                backupDatesRef.document(currentUser.uid),
                hashMapOf("lastBackupDate" to Date())
            )
            appointments.forEach {
                if (it.businessId == null)
                    it.businessId = business.id
                batch.set(appointmentsRef, it)
            }
            services.forEach {
                if (it.businessId == null) {
                    it.businessId = currentUser.uid
                }
                batch.set(servicesRef, it)
            }
            clients.forEach {
                if (it.businessId == null) {
                    it.businessId = currentUser.uid
                }
                batch.set(clientsRef, it)
            }
        }
        batch.commit()
        /*appDatabase.close()
        storageReference.child("DbBackups/${currentUser?.email}/user_db.db")
            .putFile(dbPath.toUri()).await()*/
    }

    suspend fun loadBackup() {
        val businessCollection = firestore.collection(BUSINESS_COLLECTION).whereEqualTo("ownerId", currentUser?.uid).get().await().toObjects(Business::class.java)
        businessCollection.forEach {
            val appointments = firestore.collection(APPOINTMENT_COLLECTION).whereEqualTo("businessId", it.id).get().await().toObjects(Appointment::class.java)
            val clients = firestore.collection(CLIENT_COLLECTION).whereEqualTo("businessId", it.id).get().await().toObjects(Client::class.java)
            val services = firestore.collection(SERVICE_COLLECTION).whereEqualTo("businessId", it.id).get().await().toObjects(Service::class.java)
            appDatabase.getAppointmentDao().insertAll(appointments)
            appDatabase.getServiceDao().insertAll(services)
            appDatabase.getClientDao().insertAll(clients)
        }

    }

    suspend fun getLastBackupDate(): Long? {
        if (currentUser != null)
            return try {
                firestore.collection(BACKUP_DATE_COLLECTION).document(currentUser.uid).get().await()
                    .get("lastBackupDate", Date::class.java)?.time
            } catch (e: Exception) {
                null
            }
        return null
    }

    fun setDefaultCurrency(currencyId: Int){
        val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        sharedPref.edit { putInt(Constant.CURRENCY_ID, currencyId) }
    }

    fun getDefaultCurrency(): Int{
        val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPref.getInt(Constant.CURRENCY_ID, 1)
    }

    fun scheduleBackup() {
        val request = PeriodicWorkRequestBuilder<ExpeditedWorker>(1, TimeUnit.DAYS)
            .addTag("backup")
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun cancelBackupSchedule(){
        WorkManager.getInstance(context).cancelAllWorkByTag("backup")
    }
}

class ExpeditedWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        createNotificationChannel()
        createNotification()
        val appDatabase = Room.databaseBuilder(
            context = applicationContext,
            AppDatabase::class.java,
            "my_db"
        ).createFromAsset("my_db.db").build()

        SettingRepository(context = applicationContext, appDatabase).backupData()
        return Result.success()
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(applicationContext, "0")
            .setSmallIcon(com.ahsan.composable.R.mipmap.ic_launcher)
            .setContentTitle("Backup")
            .setContentText("Daily backup has been started")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(0, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "New Channel"
            val descriptionText = "New Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}