package com.ahsan.backup

import java.util.Date

data class ViewState(val lastBackupDate: Date)

sealed class BackupEvent {
    data object BackupData: BackupEvent()
    data object LastBackupDate: BackupEvent()
    data object LoadBackup: BackupEvent()
    data object ScheduleBackup: BackupEvent()
    data object CancelScheduleBackup: BackupEvent()
}