package com.ahsan.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ahsan.data.models.Currency

@Dao
interface CurrencyDao {
    @Query("Select * from currency order by name")
    suspend fun getAll(): List<Currency>
}