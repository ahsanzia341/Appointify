package com.ahsan.domain.service

import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class FindServiceByIdUseCase @Inject constructor(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(id: Int): ServiceAndCurrency {
        return repository.findById(id)
    }
}