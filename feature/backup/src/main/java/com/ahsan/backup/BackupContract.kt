package com.ahsan.backup

import java.util.Date

data class ViewState(val lastBackupDate: Date)

sealed class BackupEvent {
    data object BackupData: BackupEvent()
}