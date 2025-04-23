package com.ahsan.setting

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.authentication.DeleteUseCase
import com.ahsan.domain.authentication.LoggedInUseCase
import com.ahsan.domain.authentication.LogoutUseCase
import com.ahsan.domain.billing.LaunchBillingUseCase
import com.ahsan.domain.billing.QueryBillingProductsUseCase
import com.ahsan.domain.business.CheckIfBusinessPresentUseCase
import com.android.billingclient.api.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val isLoggedInUseCase: LoggedInUseCase,
    private val checkIfBusinessPresentUseCase: CheckIfBusinessPresentUseCase,
    private val deleteUseCase: DeleteUseCase, private val billingUseCase: QueryBillingProductsUseCase,
    private val launchBillingUseCase: LaunchBillingUseCase): BaseViewModel<ViewState, SettingEvent>() {

    override fun onTriggerEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.Logout -> logout()
            SettingEvent.IsLoggedIn -> isLoggedIn()
            SettingEvent.DeleteAccount -> deleteAccount()
            is SettingEvent.LaunchBillingFlow -> launchBilling(event.activity, event.productDetails)
        }
    }

    private fun launchBilling(activity: Activity, productDetails: ProductDetails){
        viewModelScope.launch {
            launchBillingUseCase(activity, productDetails)
        }
    }

    private fun logout() {
        logoutUseCase()
        updateState(ViewState(settings, email = null,  isLoading = false))
    }

    private fun deleteAccount(){
        deleteUseCase()
        updateState(ViewState(settings, email = null,  isLoading = false))
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            updateState(
                ViewState(
                    settings,
                    email = isLoggedInUseCase(),
                    isLoading = false,
                    isBusinessPresent = checkIfBusinessPresentUseCase(),
                    billingProducts = billingUseCase()
                )
            )
        }
    }
}