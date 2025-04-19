package com.ahsan.setting

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.authentication.DeleteUseCase
import com.ahsan.domain.authentication.LoggedInUseCase
import com.ahsan.domain.authentication.LogoutUseCase
import com.ahsan.domain.billing.LaunchBillingUseCase
import com.ahsan.domain.billing.QueryBillingProductsUseCase
import com.ahsan.domain.setting.CancelScheduleBackupUseCase
import com.ahsan.domain.setting.LastBackupDateUseCase
import com.ahsan.domain.setting.LoadBackupUseCase
import com.ahsan.domain.setting.PostBackupUseCase
import com.ahsan.domain.setting.ScheduleBackupUseCase
import com.android.billingclient.api.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val backupUseCase: PostBackupUseCase,
    private val logoutUseCase: LogoutUseCase, private val lastBackupDateUseCase: LastBackupDateUseCase,
    private val isLoggedInUseCase: LoggedInUseCase, private val scheduleBackupUseCase: ScheduleBackupUseCase,
    private val cancelScheduleBackupUseCase: CancelScheduleBackupUseCase, private val loadBackupUseCase: LoadBackupUseCase,
    private val deleteUseCase: DeleteUseCase, private val billingUseCase: QueryBillingProductsUseCase,
    private val launchBillingUseCase: LaunchBillingUseCase): BaseViewModel<ViewState, SettingEvent>() {
    override fun onTriggerEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.Logout -> logout()
            SettingEvent.IsLoggedIn -> isLoggedIn()
            SettingEvent.CancelScheduleBackup -> cancelScheduleBackup()
            SettingEvent.ScheduleBackup -> scheduleBackup()
            SettingEvent.LoadBackup -> loadBackup()
            SettingEvent.DeleteAccount -> deleteAccount()
            is SettingEvent.LaunchBillingFlow -> launchBilling(event.activity, event.productDetails)
        }
    }

    private fun launchBilling(activity: Activity, productDetails: ProductDetails){
        viewModelScope.launch {
            launchBillingUseCase(activity, productDetails)
        }
    }

    private fun loadBackup(){
        viewModelScope.launch {
            updateState(ViewState(settings = settings, isLoading = true))
            loadBackupUseCase()
            updateState(ViewState(settings = settings, isLoading = true, lastBackupDate = Date(lastBackupDateUseCase()!!)))
        }
    }

    private fun getLastBackupDate() {
        viewModelScope.launch {
            updateState(
                ViewState(
                    settings,
                    email = isLoggedInUseCase(),
                    lastBackupDate = Date(lastBackupDateUseCase()!!),
                    isLoading = false
                )
            )
        }
    }

    private fun logout() {
        logoutUseCase()
        updateState(ViewState(settings, email = null, lastBackupDate = null, isLoading = false))
    }

    private fun scheduleBackup(){
        viewModelScope.launch {
            scheduleBackupUseCase()
        }
    }

    private fun cancelScheduleBackup(){
        viewModelScope.launch {
            cancelScheduleBackupUseCase()
        }
    }

    private fun deleteAccount(){
        deleteUseCase()
        updateState(ViewState(settings, email = null, lastBackupDate = null, isLoading = false))
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            val lastBackupDate =
                if (lastBackupDateUseCase() == null) null else Date(lastBackupDateUseCase()!!)
            updateState(
                ViewState(
                    settings,
                    email = isLoggedInUseCase(),
                    lastBackupDate = lastBackupDate,
                    isLoading = false,
                    billingProducts = billingUseCase()
                )
            )
        }
    }
}