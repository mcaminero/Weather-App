package com.example.weatherapp.core.presentation.baseUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    scaffoldState: ScaffoldState,
    viewModel: BaseViewModel,
    onError: () -> Unit,
    content: @Composable () -> Unit
) {
    val uiState by viewModel.baseUi.observeAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        uiState?.apply {
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                if (hasError) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = onError
                    ) {
                        Text(text = "Reload City")
                    }
                    LaunchedEffect(true) {
                        error?.let {
                            scaffoldState.snackbarHostState.showSnackbar(it.message!!, "Accept")
                        }
                    }
                } else {
                    content()
                }
            }
        }
    }
}