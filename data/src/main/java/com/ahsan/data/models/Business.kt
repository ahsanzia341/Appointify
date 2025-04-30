package com.ahsan.data.models

import android.net.Uri

data class Business(val id: String = "", val name: String = "", val logo: Uri? = null, val email: String = "", val address: String = "",
                    val description: String = "", val phoneNumber: String = "", val website: String = "", var ownerId: String = "")