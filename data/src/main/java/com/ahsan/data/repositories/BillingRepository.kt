package com.ahsan.data.repositories

import android.app.Activity
import com.ahsan.core.BillingObject
import com.ahsan.core.BillingObject.billingClient
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.queryProductDetails
import javax.inject.Inject

class BillingRepository @Inject constructor() {

    suspend fun queryProducts(): List<ProductDetails> {
        val billingClient = BillingObject.billingClient
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("monthly_subscription")
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)

        val productDetailsResult = billingClient.queryProductDetails(params.build())
        return productDetailsResult.productDetailsList ?: listOf()
    }

    fun launchBilling(activity: Activity, product: ProductDetails){
        val productDetailsParamsList = listOf(
            product.subscriptionOfferDetails?.get(0)?.let {
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setOfferToken(it.offerToken)
                    .setProductDetails(product)
                    .build()
            }
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        billingClient.launchBillingFlow(activity, billingFlowParams)
    }
}