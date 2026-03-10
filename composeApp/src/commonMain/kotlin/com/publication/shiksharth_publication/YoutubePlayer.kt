package com.publication.shiksharth_publication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
expect fun YoutubePlayer(
    videoId: String,
    modifier: Modifier = Modifier
)