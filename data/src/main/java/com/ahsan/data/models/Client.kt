package com.ahsan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String = "", val phoneNumber: String = "",
                  var businessId: String? = null, var isSynchronized: Boolean = false)