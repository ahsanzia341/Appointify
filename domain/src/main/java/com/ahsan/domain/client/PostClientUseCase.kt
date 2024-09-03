package com.ahsan.domain.client

import com.ahsan.data.models.Client
import com.ahsan.data.repositories.ClientRepository
import javax.inject.Inject

class PostClientUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(client: Client){
        return repository.insert(client)
    }
}