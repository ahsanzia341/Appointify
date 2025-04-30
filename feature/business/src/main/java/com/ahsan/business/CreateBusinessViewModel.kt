package com.ahsan.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ahsan.core.AppRoute
import com.ahsan.core.BaseViewModel
import com.ahsan.core.Constant
import com.ahsan.data.models.Business
import com.ahsan.domain.business.GetBusinessUseCase
import com.ahsan.domain.business.PostBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBusinessViewModel @Inject constructor(
    private val postBusinessUseCase: PostBusinessUseCase,
    private val getBusinessUseCase: GetBusinessUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<ViewState, CreateBusinessEvent>() {
    private val route = savedStateHandle.toRoute<AppRoute.CreateBusinessRoute>()
    override fun onTriggerEvent(event: CreateBusinessEvent) {
        when(event){
            is CreateBusinessEvent.CreateBusiness -> create(event.business)
        }
    }

    init {
        if(route.isEdit){
            viewModelScope.launch {
                updateState(ViewState(isLoading = false, isSubmitted = false, business = getBusinessUseCase()))
            }
        }
    }

    private fun create(business: Business){
        viewModelScope.launch {
            updateState(ViewState(isLoading = true, isSubmitted = true))
            updateState(ViewState(isLoading = false, isSuccess = postBusinessUseCase(business), isSubmitted = true))
        }
    }
}