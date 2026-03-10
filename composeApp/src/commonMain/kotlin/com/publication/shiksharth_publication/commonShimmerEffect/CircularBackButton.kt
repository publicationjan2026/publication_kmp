package com.publication.shiksharth_publication.commonShimmerEffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircularBackButton(
    modifier: Modifier,
    showButton: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .statusBarsPadding()
    ){
        if (showButton){
            Surface(
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp)
                    .size(48.dp)
                    .align(Alignment.TopStart),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp,
                onClick = onClick
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}