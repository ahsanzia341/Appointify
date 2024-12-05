package com.ahsan.changepassword

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.authentication.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val changePasswordUseCase: ChangePasswordUseCase): BaseViewModel<ViewState, ChangePasswordEvent>() {
    override fun onTriggerEvent(event: ChangePasswordEvent) {
        when(event){
            is ChangePasswordEvent.SubmitNewPassword -> newPassword(event.newPassword)
        }
    }

    private fun newPassword(newPassword: String){
        updateState(ViewState(isLoading = true, isSubmitted = false))
        viewModelScope.launch {
            changePasswordUseCase(newPassword)
            updateState(ViewState(isLoading = false, isSubmitted = true))
        }
    }
}