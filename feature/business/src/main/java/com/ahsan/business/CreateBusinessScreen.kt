package com.ahsan.business

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.EmailTextField
import com.ahsan.composable.InfoDialog
import com.ahsan.composable.PhoneTextField
import com.ahsan.composable.R
import com.ahsan.composable.RequiredTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeImageButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.data.models.Business

@Composable
fun CreateBusinessScreen(navController: NavController) {
    val viewModel = hiltViewModel<CreateBusinessViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    CreateBusinessUI(viewState?.business, viewState?.isLoading == true, viewState?.isSuccess, {
        viewModel.onTriggerEvent(CreateBusinessEvent.CreateBusiness(it))
        navController.navigate(AppRoute.BusinessDetailRoute)
    }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateBusinessUI(businessParam: Business? = null, isLoading: Boolean, isSuccess: Boolean?, onSubmit: (Business) -> Unit,
                     onBackPress: () -> Unit){
    var logo by remember {
        mutableStateOf<Uri?>(null)
    }
    var business by remember {
        mutableStateOf(businessParam ?: Business())
    }
    LaunchedEffect(businessParam) {
        business = businessParam ?: Business()
    }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            logo = uri
        }
    }
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.create_business), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var showInfo by remember { mutableStateOf(false) }
        Column(modifier = Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeImageButton(Modifier.align(Alignment.CenterHorizontally), logo){
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            RequiredTextField(label = stringResource(id = R.string.name), value = business.name) {
                business = business.copy(name = it)
            }
            PhoneTextField(business.phoneNumber) {
                business = business.copy(phoneNumber = it)
            }
            EmailTextField(business.email) {
                business = business.copy(email = it)
            }
            ThemeTextField(label = stringResource(id = R.string.address), value = business.address) {
                business = business.copy(address = it)
            }
            ThemeTextField(label = stringResource(id = R.string.description), value = business.description) {
                business = business.copy(description = it)
            }
            ThemeTextField(label = stringResource(id = R.string.website), keyboardType = KeyboardType.Uri, imeAction = ImeAction.Done) {
                business = business.copy(website = it)
            }
            ThemeButton(text = stringResource(id = R.string.submit), enabled = !isLoading) {
                onSubmit(business)
            }
        }
        if(isSuccess != null){
            if(isSuccess){
                InfoDialog(
                    "Info",
                    text = stringResource(R.string.business_created_successfully)
                ) {
                    showInfo = false
                }
            }
            else{
                InfoDialog(
                    "Info",
                    text = stringResource(R.string.failed_to_create_business)
                ) {
                    showInfo = false
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        CreateBusinessUI(null, true, true, {}){}
    }
}