package com.publication.shiksharth_publication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView



@SuppressLint("ClickableViewAccessibility")
@Composable
actual fun YoutubePlayer(
    videoId: String,
    modifier: Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val ids = remember { extractYouTubeIds(videoId) }
    ForceLandscape()
    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                this.enableAutomaticInitialization = false

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        ids.videoId?.let { videoId ->
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                        super.onError(youTubePlayer, error)
                        Log.e("YoutubePlayer", "Error: ${error.ordinal}")
                    }
                })
            }
        }
    )
}

@Composable
fun ForceLandscape() {
    val context = LocalContext.current
    val activity = context as Activity

    DisposableEffect(Unit) {
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        onDispose {
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}

fun extractYouTubeIds(url: String): YouTubeIds {
    val uri = url.toUri()

    return YouTubeIds(
        videoId = uri.getQueryParameter("v"),
        playlistId = uri.getQueryParameter("list")
    )
}

data class YouTubeIds(
    val videoId: String?,
    val playlistId: String?
)