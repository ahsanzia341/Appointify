package com.ahsan.domain.authentication

import com.ahsan.data.repositories.AuthRepository
import javax.inject.Inject

class IsEmailVerifiedUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(): Boolean{
        return repository.isEmailVerified()
    }
