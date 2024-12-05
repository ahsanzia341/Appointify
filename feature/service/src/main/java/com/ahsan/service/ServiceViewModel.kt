package com.ahsan.service

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.domain.currency.GetCurrencyUseCase
import com.ahsan.domain.currency.GetDefaultCurrencyUseCase
import com.ahsan.domain.service.DeleteServiceUseCase
import com.ahsan.domain.service.FindServiceByIdUseCase
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
    private val findServiceByIdUseCase: FindServiceByIdUseCase,
    private val deleteServiceUseCase: DeleteServiceUseCase,
    private val getDefaultCurrencyUseCase: GetDefaultCurrencyUseCase
): BaseViewModel<ViewState, ServiceEvent>() {

    private val selectedServices = ArrayList<ServiceAndCurrency>()

    override fun onTriggerEvent(event: ServiceEvent) {
        when (event) {
            is ServiceEvent.PostService -> postService(event.service)
            ServiceEvent.GetCurrencies -> getCurrencies()
            ServiceEvent.GetServices -> getServices()
            is ServiceEvent.SelectService -> selectService(event.service)
            is ServiceEvent.FindById -> findById(event.id)
            is ServiceEvent.Delete -> delete(event.service)
        }
    }
    init {
        updateState(ViewState(defaultCurrency = getDefaultCurrencyUseCase()))
    }

    private fun postService(service: Service) {
        viewModelScope.launch {
            postServiceUseCase(service)
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(service = findServiceByIdUseCase(id), defaultCurrency = getDefaultCurrencyUseCase()))
        }
    }

    private fun delete(service: Service){
        viewModelScope.launch {
            deleteServiceUseCase(service)
            val services = viewState.value?.services
            services?.mapNotNull {
                if(it.service.serviceId == service.serviceId){
                    null
                }
                else{
                    it.copy()
                }
            }
            updateState(ViewState(services = services))
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