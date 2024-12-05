package com.ahsan.domain.service

import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class GetServiceCountUseCase @Inject constructor(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(): Int {
        return repository.getServiceCount()
    }
}