package com.ahsan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(@PrimaryKey(autoGenerate = true) val serviceId: Int, val name: String, val price: Double, val currencyId: Int)
