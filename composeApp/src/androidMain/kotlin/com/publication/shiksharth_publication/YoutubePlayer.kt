package com.publication.shiksharth_publication

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun YoutubePlayer(
    videoId: String,
    modifier: Modifier
) {

    val updatedVideoId = extractYoutubeVideoId(videoId)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                loadUrl("https://www.youtube.com/embed/$updatedVideoId")
            }
        }
    )
}

fun extractYoutubeVideoId(url: String): String? {
    val regex = Regex(
        "(?:youtube\\.com/(?:.*v=|.*/)|youtu\\.be/)([a-zA-Z0-9_-]{11})"
    )
    return regex.find(url)?.groupValues?.get(1)
}
