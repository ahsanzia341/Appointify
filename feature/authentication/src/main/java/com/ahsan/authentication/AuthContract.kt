package com.ahsan.authentication

import android.content.Context

data class ViewState(val loginState: Boolean = false, val isLoading: Boolean = false,
                     val error: String? = null, val message: String? = null)

sealed class AuthEvent {
    data class Login(val email: String, val password: String): AuthEvent()
    data class Register(val email: String, val password: String): AuthEvent()
    data class SignInWithGoogle(val context: Context): AuthEvent()
    data class ForgotPassword(val email: String): AuthEvent()
    data class ValidateForLogin(val context: Context, val email: String, val password: String): AuthEvent()
    data class ValidateForRegister(val context: Context, val email: String, val password: String): AuthEvent()

}