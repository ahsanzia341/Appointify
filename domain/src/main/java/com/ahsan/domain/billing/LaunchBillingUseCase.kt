package com.ahsan.domain.billing

import android.app.Activity
import com.ahsan.data.repositories.BillingRepository
import com.android.billingclient.api.ProductDetails
import javax.inject.Inject

class LaunchBillingUseCase @Inject constructor(
    private val repository: BillingRepository,
) {
    operator fun invoke(activity: Activity, productDetails: ProductDetails){
        repository.launchBilling(activity, productDetails)
    }
}