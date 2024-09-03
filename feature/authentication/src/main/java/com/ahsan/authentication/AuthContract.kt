package com.ahsan.authentication

data class ViewState(val loginState: Boolean = false, val isLoading: Boolean = false,
                     val error: String? = null)

sealed class AuthEvent {
    data class Login(val email: String, val password: String): AuthEvent()
    data class Register(val email: String, val password: String): AuthEvent()
    data class ForgotPassword(val email: String): AuthEvent()
}