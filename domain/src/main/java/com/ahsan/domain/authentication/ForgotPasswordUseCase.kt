package com.ahsan.domain.authentication

import com.ahsan.data.repositories.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(email: String){
        return repository.resetPassword(email)
    }
}