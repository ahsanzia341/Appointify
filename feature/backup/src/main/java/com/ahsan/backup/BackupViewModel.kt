package com.ahsan.backup

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.setting.LastBackupDateUseCase
import com.ahsan.domain.setting.PostBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupDateUseCase: PostBackupUseCase
): BaseViewModel<ViewState, BackupEvent>() {
    override fun onTriggerEvent(event: BackupEvent) {
        when(event){
            BackupEvent.BackupData -> backupData()
        }
    }

    private fun backupData() {
        viewModelScope.launch {
            backupDateUseCase()
            //getLastBackupDate()
        }
    }
}