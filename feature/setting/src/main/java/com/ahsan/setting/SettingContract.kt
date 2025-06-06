package com.ahsan.setting

import android.app.Activity
import androidx.annotation.StringRes
import com.ahsan.composable.R
import com.android.billingclient.api.ProductDetails
import java.util.Date

data class ViewState(val settings: List<SettingRow>, val isLoading: Boolean = true, val email: String? = null,
                     val billingProducts: List<ProductDetails>? = null, val isBusinessPresent: Boolean? = false)

sealed class SettingEvent{
    data object Logout: SettingEvent()
    data object IsLoggedIn: SettingEvent()
    data object DeleteAccount: SettingEvent()
    data class LaunchBillingFlow(val activity: Activity, val productDetails: ProductDetails): SettingEvent()
}

val settings = listOf(
    SettingRow(R.string.login, isNextPage = true, anonymousRow = true),
    SettingRow(R.string.go_pro, isNextPage = true),
    SettingRow(R.string.business, isNextPage = true, loginRequired = true),
    SettingRow(R.string.teams, isNextPage = true, loginRequired = true),
    SettingRow(R.string.account_settings, isNextPage = true, loginRequired = true),
    SettingRow(R.string.backup_data, loginRequired = true, isNextPage = true),
    SettingRow(R.string.automatic_daily_backup, isSwitch = true, loginRequired = true),
    SettingRow(R.string.currency, isNextPage = true),
    SettingRow(R.string.privacy_policy, isNextPage = true),
    SettingRow(R.string.feedback, isNextPage = true, loginRequired = true),
    SettingRow(R.string.logout, loginRequired = true),
)
data class SettingRow(@StringRes val title: Int, val isNextPage: Boolean = false, val isSwitch: Boolean = false,
                      val loginRequired: Boolean = false,
                      val anonymousRow: Boolean = false, val onSwitchCheckChange: (Boolean) -> Unit = {},
                      val onClick: () -> Unit = {})