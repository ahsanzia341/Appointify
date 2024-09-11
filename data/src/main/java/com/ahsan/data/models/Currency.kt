package com.ahsan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val symbol: String)
