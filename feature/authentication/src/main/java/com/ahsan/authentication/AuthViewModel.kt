package com.ahsan.authentication

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.core.Validator
import com.ahsan.data.AuthUiState
import com.ahsan.domain.authentication.ForgotPasswordUseCase
import com.ahsan.domain.authentication.LoginUseCase
import com.ahsan.domain.authentication.RegisterUseCase
import com.ahsan.domain.setting.LoadBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
): BaseViewModel<ViewState, AuthEvent>() {

    override fun onTriggerEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.email, event.password)
            is AuthEvent.ForgotPassword -> forgotPassword(event.email)
            is AuthEvent.ValidateForLogin -> validateForLogin(event.context, event.email, event.password)
            is AuthEvent.ValidateForRegister -> validateForRegister(event.context, event.email, event.password)
        }
    }

    private fun forgotPassword(email: String) {
        if(!Validator.isValidEmail(email)){
            updateState(ViewState(emailValidationError = "Invalid Email"))
            return
        }
        updateState(ViewState(isLoading = true))
        viewModelScope.launch {
            forgotPasswordUseCase(email).collect {
                when (it) {
                    is AuthUiState.Failure -> {
                        updateState(
                            ViewState(
                                loginState = false,
                                error = it.error,
                                isLoading = false
                            )
                        )
                    }

                    AuthUiState.Success -> updateState(ViewState(loginState = true))
                    null -> {}
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        updateState(ViewState(isLoading = true))
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is AuthUiState.Failure -> updateState(
                        ViewState(
                            loginState = false,
                            error = it.error
                        )
                    )

                    AuthUiState.Success -> {
                        updateState(ViewState(loginState = true, error = null))
                    }
                    null -> {}
                }
            }
        }
    }
    private fun validateForLogin(context: Context, email: String, password: String) {
        var emailValidationError = ""
        var passwordValidationError = ""
        if(!Validator.isValidEmail(email)){
            emailValidationError = context.getString(com.ahsan.composable.R.string.email_error)
        }
        if(Validator.isPasswordEmpty(password)){
            passwordValidationError = context.getString(com.ahsan.composable.R.string.password_error)
        }
        if(passwordValidationError.isEmpty() && emailValidationError.isEmpty()){
            login(email, password)
        }
        else{
            updateState(ViewState(emailValidationError = emailValidationError, passwordValidationError = passwordValidationError))
        }
    }
    private fun validateForRegister(context: Context, email: String, password: String) {
        var emailValidationError = ""
        var passwordValidationError = ""
        if(!Validator.isValidEmail(email)){
            emailValidationError = context.getString(com.ahsan.composable.R.string.email_error)
        }
        if(!Validator.isValidPassword(password)){
            passwordValidationError = context.getString(com.ahsan.composable.R.string.password_error)
        }
        if(passwordValidationError.isEmpty() && emailValidationError.isEmpty()){
            register(email, password)
        }
        else{
            updateState(ViewState(emailValidationError = emailValidationError, passwordValidationError = passwordValidationError))
        }
    }

    private fun register(email: String, password: String) {
        updateState(ViewState(isLoading = true))
        viewModelScope.launch {
            registerUseCase(email, password).collect {
                when (it) {
                    is AuthUiState.Failure -> updateState(
                        ViewState(
                            loginState = false,
                            error = it.error
                        )
                    )

                    AuthUiState.Success -> updateState(
                        ViewState(
                            loginState = true,
                            error = null
                        )
                    )

                    null -> {}
                }
            }
        }
    }
}