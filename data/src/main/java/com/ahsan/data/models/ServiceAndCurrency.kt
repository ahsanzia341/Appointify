package com.ahsan.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ServiceAndCurrency(@Embedded val service: Service,
                              @Relation(entity = Currency::class, parentColumn = "currencyId", entityColumn = "id")
                              val currency: Currency
)
