package com.ahsan.setting

data class ViewState(val setting: String, val email: String? = null)

sealed class SettingEvent{
    data object BackupData: SettingEvent()
    data object Logout: SettingEvent()
    data object IsLoggedIn: SettingEvent()
}