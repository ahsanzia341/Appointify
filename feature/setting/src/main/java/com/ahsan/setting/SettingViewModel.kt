package com.ahsan.setting

import com.ahsan.core.BaseViewModel
import com.ahsan.domain.authentication.LoggedInUseCase
import com.ahsan.domain.authentication.LogoutUseCase
import com.ahsan.domain.setting.PostBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val backupUseCase: PostBackupUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isLoggedInUseCase: LoggedInUseCase): BaseViewModel<ViewState, SettingEvent>() {
    override fun onTriggerEvent(event: SettingEvent) {
        when(event){
            SettingEvent.BackupData -> backupData()
            SettingEvent.Logout -> logout()
            SettingEvent.IsLoggedIn -> isLoggedIn()
        }
    }

    private fun backupData(){
        backupUseCase()
    }

    private fun logout(){
        logoutUseCase()
        updateState(ViewState(email = null, setting = ""))
    }

    private fun isLoggedIn(){
        updateState(ViewState(email = isLoggedInUseCase(), setting = ""))
    }
}