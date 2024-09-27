package com.ahsan.core

import android.text.TextUtils
import android.util.Patterns


object Validator {
    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPassword(text: String): Boolean {
        return !isPasswordEmpty(text) && text.length > 5
    }

    fun isPasswordEmpty(text: String): Boolean{
        return text.isEmpty()
    }
}