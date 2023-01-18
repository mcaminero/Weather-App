package com.example.weatherapp.presentation.splashScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.utils.THREE_SECONDS_WAITING_TIME
import com.example.weatherapp.utils.TWO_SECONDS_WAITING_TIME
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    var isVisible by remember {
        mutableStateOf(true)
    }
    SplashAnimation(isVisible)
    LaunchedEffect(key1 = isVisible) {
        delay(THREE_SECONDS_WAITING_TIME)
        isVisible = false
        onAnimationFinished()
    }
}

@Composable
fun SplashAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut(tween(TWO_SECONDS_WAITING_TIME))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = "Weather \n App",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.content_description_for_splash_logo)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashAnimation(isVisible = true)
}