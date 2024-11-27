package com.ahsan.setting

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.authentication.DeleteUseCase
import com.ahsan.domain.authentication.LoggedInUseCase
import com.ahsan.domain.authentication.LogoutUseCase
import com.ahsan.domain.setting.CancelScheduleBackupUseCase
import com.ahsan.domain.setting.LastBackupDateUseCase
import com.ahsan.domain.setting.LoadBackupUseCase
import com.ahsan.domain.setting.PostBackupUseCase
import com.ahsan.domain.setting.ScheduleBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val backupUseCase: PostBackupUseCase,
    private val logoutUseCase: LogoutUseCase, private val lastBackupDateUseCase: LastBackupDateUseCase,
    private val isLoggedInUseCase: LoggedInUseCase, private val scheduleBackupUseCase: ScheduleBackupUseCase,
    private val cancelScheduleBackupUseCase: CancelScheduleBackupUseCase, private val loadBackupUseCase: LoadBackupUseCase,
    private val deleteUseCase: DeleteUseCase): BaseViewModel<ViewState, SettingEvent>() {
    override fun onTriggerEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.BackupData -> backupData()
            SettingEvent.Logout -> logout()
            SettingEvent.IsLoggedIn -> isLoggedIn()
            SettingEvent.CancelScheduleBackup -> cancelScheduleBackup()
            SettingEvent.ScheduleBackup -> scheduleBackup()
            SettingEvent.LoadBackup -> loadBackup()
            SettingEvent.DeleteAccount -> deleteAccount()
        }
    }

    private fun backupData() {
        viewModelScope.launch {
            updateState(ViewState(isLoading = true))
            backupUseCase()
            getLastBackupDate()
        }
    }

    private fun loadBackup(){
        viewModelScope.launch {
            updateState(ViewState(isLoading = true))
            loadBackupUseCase()
            updateState(ViewState(isLoading = true, lastBackupDate = Date(lastBackupDateUseCase()!!)))
        }
    }

    private fun getLastBackupDate() {
        viewModelScope.launch {
            updateState(
                ViewState(
                    email = isLoggedInUseCase(),
                    lastBackupDate = Date(lastBackupDateUseCase()!!),
                    isLoading = false
                )
            )
        }
    }

    private fun logout() {
        logoutUseCase()
        updateState(ViewState(email = null, lastBackupDate = null, isLoading = false))
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
        updateState(ViewState(email = null, lastBackupDate = null, isLoading = false))
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            val lastBackupDate =
                if (lastBackupDateUseCase() == null) null else Date(lastBackupDateUseCase()!!)
            updateState(
                ViewState(
                    email = isLoggedInUseCase(),
                    lastBackupDate = lastBackupDate,
                    isLoading = false
                )
            )
        }
    }
}