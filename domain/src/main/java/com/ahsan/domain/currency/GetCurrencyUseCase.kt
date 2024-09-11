package com.ahsan.domain.currency

import com.ahsan.data.models.Currency
import com.ahsan.data.repositories.CurrencyRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke(): List<Currency>{
        return repository.getAll()
    }
}