package com.ahsan.setting

import com.ahsan.core.BaseViewModel
import com.ahsan.domain.setting.PostBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val backupUseCase: PostBackupUseCase): BaseViewModel<ViewState, SettingEvent>() {
    override fun onTriggerEvent(event: SettingEvent) {
        when(event){
            SettingEvent.BackupData -> backupData()
        }
    }

    private fun backupData(){
        backupUseCase()
    }
}