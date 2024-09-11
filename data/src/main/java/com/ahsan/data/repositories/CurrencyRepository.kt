package com.ahsan.data.repositories

import com.ahsan.data.AppDatabase
import com.ahsan.data.models.Currency
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getAll(): List<Currency> {
        return db.getCurrencyDao().getAll()
    }
}