package com.ahsan.backup

data class ViewState(val isLoading: Boolean = false, val isSubmitted: Boolean = false)

sealed class BackupEvent {
}