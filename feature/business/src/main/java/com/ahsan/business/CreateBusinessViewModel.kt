package com.ahsan.business

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Business
import com.ahsan.domain.business.PostBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class CreateBusinessViewModel(
    private val postBusinessUseCase: PostBusinessUseCase
): BaseViewModel<ViewState, CreateBusinessEvent>() {
    override fun onTriggerEvent(event: CreateBusinessEvent) {
        when(event){
            is CreateBusinessEvent.CreateBusiness -> create(event.business)
        }
    }

    private fun create(business: Business){
        viewModelScope.launch {
            postBusinessUseCase(business)
        }
    }
}