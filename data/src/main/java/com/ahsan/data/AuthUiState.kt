package com.ahsan.data

sealed class AuthUiState {
    data object Success: AuthUiState()
    data object PartialSuccess: AuthUiState()
    data class Failure(val error: String): AuthUiState()
}