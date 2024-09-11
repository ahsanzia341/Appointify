package com.ahsan.domain.service

import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(): List<ServiceAndCurrency>{
        return repository.getAll()
    }
}