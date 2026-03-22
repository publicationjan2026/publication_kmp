package com.publication.shiksharth_publication.dashboard

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.publication.shiksharth_publication.commonDashboard.navigation.DashboardNavHost
import com.publication.shiksharth_publication.commonDashboard.repo.PublicationRepo
import com.publication.shiksharth_publication.commonDashboard.viewModel.DashboardViewModel

class DashboardActivity  : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(window)

        setContent {
            val repo = remember { PublicationRepo() }
            val viewModel = remember {
                DashboardViewModel(
                    repo
                )
            }
            MaterialTheme (
                colorScheme = lightColorScheme(),
            ){
                DashboardNavHost(viewModel = viewModel)
            }
        }
    }
}

fun enableEdgeToEdge(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = android.graphics.Color.TRANSPARENT
    window.navigationBarColor = android.graphics.Color.TRANSPARENT
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.isAppearanceLightStatusBars = true
    controller.isAppearanceLightNavigationBars = true
}