package com.ahsan.changepassword

data class ViewState(val isLoading: Boolean = false, val isSubmitted: Boolean = false)

sealed class ChangePasswordEvent {
    data class SubmitNewPassword(val newPassword: String): ChangePasswordEvent()
}