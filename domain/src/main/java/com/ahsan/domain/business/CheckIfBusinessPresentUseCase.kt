package com.ahsan.domain.business

import com.ahsan.data.repositories.AuthRepository
import javax.inject.Inject

class CheckIfBusinessPresentUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean{
        return !repository.isServerDataEmpty()
    }
}