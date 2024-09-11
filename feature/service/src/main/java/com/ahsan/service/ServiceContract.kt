package com.ahsan.service

import com.ahsan.data.models.Currency
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

data class ViewState(val services: List<ServiceAndCurrency>? = null,
                     val currencies: List<Currency>? = null, val selectedServices: List<ServiceAndCurrency>? = null)

sealed class ServiceEvent {
    data class PostService(val service: Service) : ServiceEvent()
    data object GetServices : ServiceEvent()
    data object GetCurrencies : ServiceEvent()
    data class SelectService(val service: ServiceAndCurrency) : ServiceEvent()
}