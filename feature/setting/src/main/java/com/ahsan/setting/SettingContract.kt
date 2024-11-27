package com.ahsan.setting

import java.util.Date

data class ViewState(val isLoading: Boolean = true, val email: String? = null, val lastBackupDate: Date? = null)

sealed class SettingEvent{
    data object BackupData: SettingEvent()
    data object Logout: SettingEvent()
    data object IsLoggedIn: SettingEvent()
    data object ScheduleBackup: SettingEvent()
    data object CancelScheduleBackup: SettingEvent()
    data object LoadBackup: SettingEvent()
    data object DeleteAccount: SettingEvent()
}