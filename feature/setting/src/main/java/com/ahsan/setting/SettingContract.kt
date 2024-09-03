package com.ahsan.setting

data class ViewState(val setting: String, val isLoggedIn: Boolean = false)

sealed class SettingEvent{
    data object BackupData: SettingEvent()
}