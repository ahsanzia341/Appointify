package com.ahsan.accountsettings

import androidx.annotation.StringRes
import com.ahsan.composable.R

data class ViewState(val settings: List<SettingRow>)

sealed class AccountSettingEvent{

}

val settings = listOf(
    SettingRow(R.string.change_password, true),
    SettingRow(R.string.delete_account))

data class SettingRow(@StringRes val title: Int, val isNextPage: Boolean = false, val onClick: () -> Unit = {})