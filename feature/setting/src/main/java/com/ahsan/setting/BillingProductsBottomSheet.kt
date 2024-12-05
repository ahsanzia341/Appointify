package com.ahsan.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahsan.composable.ThemeText
import com.android.billingclient.api.ProductDetails

@Composable
fun BillingProductsBottomSheet(productDetails: List<ProductDetails>, onClick: (ProductDetails) -> Unit) {

    ProductRow(product = productDetails[0]) {
        onClick(productDetails[0])
    }
}

@Composable
fun ProductRow(product: ProductDetails, onClick: () -> Unit){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.clickable {
        onClick()
    }) {
        ThemeText(text = product.productType)
        ThemeText(text = product.title)
        ThemeText(text = product.name)
        ThemeText(text = product.description)
    }
}