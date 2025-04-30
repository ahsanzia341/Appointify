package com.ahsan.domain.business

import com.ahsan.data.models.Business
import com.ahsan.data.repositories.BusinessRepository
import javax.inject.Inject

class GetBusinessUseCase @Inject constructor(
    private val repository: BusinessRepository,
) {
    suspend operator fun invoke(): Business{
        return repository.get()
    }
}