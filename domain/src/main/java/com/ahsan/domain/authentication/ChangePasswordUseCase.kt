package com.ahsan.domain.authentication

import com.ahsan.data.repositories.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(newPassword: String){
        repository.changePassword(newPassword)
    }
}