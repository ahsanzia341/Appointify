package com.ahsan.businessdetail

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Business
import com.ahsan.domain.business.GetBusinessUseCase
import com.ahsan.domain.business.PostBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessDetailViewModel @Inject constructor(
    private val getBusinessUseCase: GetBusinessUseCase
): BaseViewModel<ViewState, BusinessDetailEvent>() {
    override fun onTriggerEvent(event: BusinessDetailEvent) {

    }

    init {
        get()
    }

    private fun get() {
        viewModelScope.launch {
            updateState(ViewState(business = getBusinessUseCase(), isLoading = false))
        }
    }
}