package com.ahsan.domain.billing

import com.ahsan.data.repositories.BillingRepository
import com.android.billingclient.api.ProductDetails
import javax.inject.Inject

class QueryBillingProductsUseCase @Inject constructor(
    private val repository: BillingRepository,
) {
    suspend operator fun invoke(): List<ProductDetails>{
        return repository.queryProducts()
    }
}