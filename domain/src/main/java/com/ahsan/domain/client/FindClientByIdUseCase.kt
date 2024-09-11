package com.ahsan.domain.client

import com.ahsan.data.models.Client
import com.ahsan.data.repositories.ClientRepository
import javax.inject.Inject

class FindClientByIdUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(id: Int): Client {
        return repository.findById(id)
    }
}