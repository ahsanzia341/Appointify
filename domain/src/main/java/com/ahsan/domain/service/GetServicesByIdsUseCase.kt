package com.ahsan.domain.service

import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class GetServicesByIdsUseCase @Inject constructor(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(list: List<Int>): List<ServiceAndCurrency>{
        return repository.getByIds(list)
    }
}