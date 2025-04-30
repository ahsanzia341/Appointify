package com.ahsan.feedback

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun FeedbackScreen(navController: NavController){
    val viewModel = hiltViewModel<FeedbackViewModel>()
    val viewState by viewModel.viewState.collectAsState()
}