package com.ahsan.service

import android.view.View
import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.domain.currency.GetCurrencyUseCase
import com.ahsan.domain.currency.GetDefaultCurrencyUseCase
import com.ahsan.domain.service.GetServicesUseCase
import com.ahsan.domain.service.PostServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val postServiceUseCase: PostServiceUseCase,
    private val getServicesUseCase: GetServicesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    getDefaultCurrencyUseCase: GetDefaultCurrencyUseCase
): BaseViewModel<ViewState, ServiceEvent>() {

    private val selectedServices = ArrayList<ServiceAndCurrency>()

    override fun onTriggerEvent(event: ServiceEvent) {
        when (event) {
            is ServiceEvent.PostService -> postService(event.service)
            ServiceEvent.GetCurrencies -> getCurrencies()
            ServiceEvent.GetServices -> getServices()
            is ServiceEvent.SelectService -> selectService(event.service)
        }
    }
    init {
        ViewState(defaultCurrency = getDefaultCurrencyUseCase())
    }

    private fun postService(service: Service) {
        viewModelScope.launch {
            postServiceUseCase(service)
        }
    }

    private fun getServices() {
        viewModelScope.launch {
            updateState(ViewState(services = getServicesUseCase()))
        }
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            updateState(ViewState(currencies = getCurrencyUseCase()))
        }
    }

    private fun selectService(service: ServiceAndCurrency){
        if(!selectedServices.removeIf { service.service.serviceId == it.service.serviceId }){
            selectedServices.add(service)
        }
        updateState(viewState.value?.copy(selectedServices = ArrayList(selectedServices))!!)
    }
}