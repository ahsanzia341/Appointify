package com.ahsan.authentication

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.core.Validator
import com.ahsan.data.AuthUiState
import com.ahsan.domain.authentication.ForgotPasswordUseCase
import com.ahsan.domain.authentication.LoginUseCase
import com.ahsan.domain.authentication.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): BaseViewModel<ViewState, AuthEvent>() {

    override fun onTriggerEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.email, event.password)
            is AuthEvent.ForgotPassword -> forgotPassword(event.email)
        }
    }

    private fun forgotPassword(email: String){
        forgotPasswordUseCase(email)
    }
    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is AuthUiState.Failure -> ViewState(loginState = false, error = it.error)
                    AuthUiState.Success -> ViewState(loginState = true, error = null)
                    null -> {}
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        if(Validator.isValidEmail(email) && Validator.isValidPassword(password)) {
            viewModelScope.launch {
                registerUseCase(email, password).collect {
                    when (it) {
                        is AuthUiState.Failure -> ViewState(loginState = false, error = it.error)
                        AuthUiState.Success -> ViewState(loginState = true, error = null)
                        null -> {}
                    }
                }
            }
        }
    }
}