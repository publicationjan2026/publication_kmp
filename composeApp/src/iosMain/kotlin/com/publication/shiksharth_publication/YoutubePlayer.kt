package com.publication.shiksharth_publication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.Foundation.NSURL
import platform.WebKit.WKWebView
import platform.WebKit.*
import platform.UIKit.*


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun YoutubePlayer(
    videoId: String,
    modifier: Modifier
) {

    // 👇 ADD HERE
    LaunchedEffect(Unit) {
        forceLandscape()
    }

    DisposableEffect(Unit) {
        onDispose {
            forcePortrait()
        }
    }


    val updatedVideoId = extractYoutubeVideoId(videoId)

    val configuration = WKWebViewConfiguration().apply {
        allowsInlineMediaPlayback = true
        mediaTypesRequiringUserActionForPlayback = WKAudiovisualMediaTypeNone
        preferences.javaScriptEnabled = true
    }

    val webView = WKWebView(
        frame = platform.CoreGraphics.CGRectZero.readValue(),
        configuration = configuration
    )

    val html = """
        <!doctype html>
        <html lang="en">
        <head>
          <meta charset="utf-8" />
          <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover" />
          <meta name="referrer" content="strict-origin-when-cross-origin" />
          <title>YouTube Embed Relay</title>
          <style>
            html, body { height: 100%; margin: 0; background: #000; }
            iframe { width: 100%; height: 100%; border: 0; display: block; }
          </style>
        </head>
        <body>
          <iframe
            id="player"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; fullscreen"
            allowfullscreen
            referrerpolicy="strict-origin-when-cross-origin"
          ></iframe>
          
          <script>
            const v = "$updatedVideoId";
            const base = 'https://www.youtube-nocookie.com/embed/' + encodeURIComponent(v);
            const params = new URLSearchParams({
              enablejsapi: '1',
              rel: '0',
              modestbranding: '1',
              playsinline: '1',
              autoplay: '0',
              origin: location.origin
            });

            document.getElementById('player').src = base + "?" + params;
          </script>
        </body>
        </html>
    """.trimIndent()

    webView.loadHTMLString(
        html,
        baseURL = NSURL.URLWithString("https://your-youtube-embed.pages.dev")
    )

    UIKitView(
        factory = { webView },
        modifier = modifier
    )
}

fun extractYoutubeVideoId(url: String): String? {
    val regex = Regex(
        "(?:youtube\\.com/(?:.*v=|.*/)|youtu\\.be/)([a-zA-Z0-9_-]{11})"
    )
    return regex.find(url)?.groupValues?.get(1)
}



fun forceLandscape() {
    val windowScene = UIApplication.sharedApplication.connectedScenes
        .firstOrNull() as? UIWindowScene ?: return

    val preferences = UIWindowSceneGeometryPreferencesIOS(
        interfaceOrientations = UIInterfaceOrientationMaskLandscape
    )

    windowScene.requestGeometryUpdateWithPreferences(
        preferences,
        errorHandler = { error ->
            println("Orientation error: $error")
        }
    )
}

fun forcePortrait() {
    val windowScene = UIApplication.sharedApplication.connectedScenes
        .firstOrNull() as? UIWindowScene ?: return

    val preferences = UIWindowSceneGeometryPreferencesIOS(
        interfaceOrientations = UIInterfaceOrientationMaskPortrait
    )

    windowScene.requestGeometryUpdateWithPreferences(
        preferences,
        errorHandler = { error ->
            println("Orientation error: $error")
        }
    )
}