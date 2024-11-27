package com.ahsan.domain.currency

import com.ahsan.data.repositories.SettingRepository
import javax.inject.Inject

class SetDefaultCurrencyUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    operator fun invoke(currencyId: Int){
        return repository.setDefaultCurrency(currencyId)
    }
}