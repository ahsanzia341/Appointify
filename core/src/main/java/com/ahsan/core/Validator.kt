package com.ahsan.core

import android.text.TextUtils
import android.util.Patterns


object Validator {
    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPassword(text: String): Boolean {
        return text.isNotEmpty() && text.length > 5
    }
}