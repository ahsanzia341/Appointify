package com.ahsan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(@PrimaryKey(autoGenerate = true) val serviceId: Int = 0, val name: String = "", val price: Double = 0.0, val currencyId: Int = 1, var userId: String? = null)
