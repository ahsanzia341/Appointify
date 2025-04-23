package com.ahsan.backup

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.setting.CancelScheduleBackupUseCase
import com.ahsan.domain.setting.LastBackupDateUseCase
import com.ahsan.domain.setting.LoadBackupUseCase
import com.ahsan.domain.setting.PostBackupUseCase
import com.ahsan.domain.setting.ScheduleBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupDateUseCase: PostBackupUseCase,
    private val loadBackupUseCase: LoadBackupUseCase,
    private val lastBackupDateUseCase: LastBackupDateUseCase,
    private val scheduleBackupUseCase: ScheduleBackupUseCase,
    private val cancelScheduleBackupUseCase: CancelScheduleBackupUseCase,
): BaseViewModel<ViewState, BackupEvent>() {
    override fun onTriggerEvent(event: BackupEvent) {
        when(event){
            BackupEvent.BackupData -> backupData()
            BackupEvent.LastBackupDate -> loadBackupDate()
            BackupEvent.LoadBackup -> loadBackup()
            BackupEvent.CancelScheduleBackup -> cancelScheduleBackup()
            BackupEvent.ScheduleBackup -> scheduleBackup()
        }
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
    private fun backupData() {
        viewModelScope.launch {
            backupDateUseCase()
            //getLastBackupDate()
        }
    }

    private fun loadBackupDate(){
        viewModelScope.launch {
            lastBackupDateUseCase()
        }
    }

    private fun loadBackup(){
        viewModelScope.launch {
            loadBackupUseCase()
        }
    }
}