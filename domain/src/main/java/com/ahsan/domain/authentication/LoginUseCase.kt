package com.ahsan.domain.authentication

import com.ahsan.data.AuthUiState
import com.ahsan.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(email: String, password: String): Flow<AuthUiState?>{
        return repository.login(email, password)
    }
}