package com.ahsan.currency

import com.ahsan.data.models.Currency

data class ViewState(val currencies: List<Currency>, val defaultCurrency: Int)

sealed class CurrencySettingEvent{
    data class SelectCurrency(val id: Int): CurrencySettingEvent()
}