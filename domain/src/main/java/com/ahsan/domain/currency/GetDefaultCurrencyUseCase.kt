package com.ahsan.domain.currency

import com.ahsan.data.repositories.SettingRepository
import javax.inject.Inject

class GetDefaultCurrencyUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    operator fun invoke(): Int {
        return repository.getDefaultCurrency()
    }
}