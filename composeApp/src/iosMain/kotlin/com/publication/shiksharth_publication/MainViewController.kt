package com.publication.shiksharth_publication

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.publication.shiksharth_publication.commonDashboard.navigation.DashboardNavHost
import com.publication.shiksharth_publication.commonDashboard.repo.PublicationRepo
import com.publication.shiksharth_publication.commonDashboard.viewModel.DashboardViewModel

fun MainViewController() = ComposeUIViewController {
    val repo = remember { PublicationRepo() }
    val viewModel = remember {
        DashboardViewModel(
            repo
        )
    }
    DashboardNavHost(viewModel = viewModel)
}