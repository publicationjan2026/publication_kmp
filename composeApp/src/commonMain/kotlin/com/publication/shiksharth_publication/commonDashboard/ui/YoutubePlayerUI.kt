package com.publication.shiksharth_publication.commonDashboard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.publication.shiksharth_publication.commonShimmerEffect.CircularBackButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun VideoScreen(youtubeUrl: String, onBackPress: () -> Unit) {
    var isToolbarVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var hideJob by remember { mutableStateOf<Job?>(null) }

    fun resetTimer() {
        hideJob?.cancel()
        isToolbarVisible = true

        hideJob = scope.launch {
            delay(3000)
            isToolbarVisible = false
        }
    }

    LaunchedEffect(Unit) {
        resetTimer()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = isToolbarVisible,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                CircularBackButton(
                    modifier = Modifier,
                   true
                ) {
                    onBackPress()
                }
            }



        }

    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                            resetTimer()
                        }
                    }
                }
        ) {
           /* YoutubePlayer(
                youtubeUrl = youtubeUrl,
                modifier = Modifier.fillMaxSize().background(Color.Red)
            )*/
        }
    }

}
