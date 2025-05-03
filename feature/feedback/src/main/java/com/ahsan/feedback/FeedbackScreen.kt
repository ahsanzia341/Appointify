package com.ahsan.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.RequiredTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeDropDown
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.data.models.Feedback
import com.ahsan.data.models.FeedbackCategory

@Composable
fun FeedbackScreen(navController: NavController){
    val viewModel = hiltViewModel<FeedbackViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    FeedbackUI(viewState?.feedbackCategories ?: listOf(), viewState?.isLoading == true, {
        viewModel.onTriggerEvent(FeedbackEvent.Submit(it))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun FeedbackUI(categories: List<FeedbackCategory>, isLoading: Boolean,
               onSubmitPress: (Feedback) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.feedback), onClickNavIcon = onBackPress)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var feedback by remember {
            mutableStateOf(Feedback())
        }
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeDropDown(stringResource(R.string.category)+"*", categories.map { it.name }) {
                feedback.categoryId = it
            }
            RequiredTextField(stringResource(R.string.description)) {
                feedback.description = it
            }
            ThemeButton(text = stringResource(R.string.submit), enabled = !isLoading) {
                onSubmitPress(feedback)
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        FeedbackUI(listOf(), false, {}){}
    }
}