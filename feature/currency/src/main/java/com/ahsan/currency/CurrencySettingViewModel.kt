package com.ahsan.currency

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.currency.GetCurrencyUseCase
import com.ahsan.domain.currency.GetDefaultCurrencyUseCase
import com.ahsan.domain.currency.SetDefaultCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencySettingViewModel @Inject constructor(private val currencyUseCase: GetCurrencyUseCase,
    private val setDefaultCurrencyUseCase: SetDefaultCurrencyUseCase,
    private val getDefaultCurrencyUseCase: GetDefaultCurrencyUseCase): BaseViewModel<ViewState, CurrencySettingEvent>()  {
    override fun onTriggerEvent(event: CurrencySettingEvent) {
        when(event){
            is CurrencySettingEvent.SelectCurrency -> selectCurrency(event.id)
        }
    }

    init {
        getAll()
    }

    private fun getAll(){
        viewModelScope.launch {
            updateState(ViewState(currencies = currencyUseCase(), defaultCurrency = getDefaultCurrencyUseCase()))
        }
    }

    private fun selectCurrency(id: Int){
        setDefaultCurrencyUseCase(id)
    }
}