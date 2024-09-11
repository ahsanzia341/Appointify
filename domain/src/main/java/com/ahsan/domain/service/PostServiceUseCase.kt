package com.ahsan.domain.service

import com.ahsan.data.models.Service
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class PostServiceUseCase @Inject constructor(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(service: Service){
        return repository.insert(service)
    }
}