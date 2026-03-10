package com.publication.shiksharth_publication.commonDashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.publication.shiksharth.dashboard.ui.DashboardSearchScreen
import com.publication.shiksharth_publication.commonDashboard.ui.DashboardScreenUI
import com.publication.shiksharth_publication.commonDashboard.ui.VideoScreen
import com.publication.shiksharth_publication.commonDashboard.viewModel.DashboardViewModel

@Composable
fun DashboardNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: DashboardViewModel
) {
    NavHost(
        navController = navController,
        startDestination = DashboardRoute.DashboardScreen,
        modifier = Modifier
    ) {
        mainGraph(navController, viewModel)
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    viewModel: DashboardViewModel
) {
    composable<DashboardRoute.DashboardScreen> {
        DashboardScreenUI(
            viewModel = viewModel,
            listOfChapter = viewModel.chapterList,
            isDataExist = viewModel.isDataFilled,
            listOfClasses = viewModel.fetchAllClass,
            listOfSubjects = viewModel.subjectList,
            selectedClass = viewModel.selectedClass,
            selectedSubject = viewModel.selectedSubject,
            selectedSubjectName = viewModel.selectedSubjectName,
            onClassClick = { className ->
                viewModel.setSelectedClass(className)
            },
            onSubjectClick = { subjectName ->
                viewModel.setSelectedSubject(subjectName)

            },
            onVideoBtnClick = { url ->
                //  navController.navigate(DashboardRoute.YoutubePlayer(url))

            },
            onFlipBookClick = { flipBookUrl ->
                // FlipBookActivity.start(activity,0, flipBookUrl)
            },
            onSearchClick = {
                navController.navigate(DashboardRoute.DashboardSearchScreen)
            }
        )
    }

    composable<DashboardRoute.DashboardSearchScreen> {
        DashboardSearchScreen(
            vm = viewModel,
            onVideoBtnClick = { url ->
                navController.navigate(DashboardRoute.YoutubePlayer(url))

            },
            onFlipBookClick = { flipBookUrl ->
                // FlipBookActivity.start(activity,0, flipBookUrl)
            },
            onBackPress = {
                navController.popBackStack()
            },
        )
    }



    composable<DashboardRoute.YoutubePlayer> {
        val url = it.toRoute<DashboardRoute.YoutubePlayer>().url
        VideoScreen(url, onBackPress = {
            navController.popBackStack()
        })
    }


}


@Serializable
sealed class DashboardRoute {
    @Serializable
    data object DashboardScreen : DashboardRoute()

    @Serializable
    data object DashboardSearchScreen : DashboardRoute()

    @Serializable
    data class YoutubePlayer(
        val url: String
    ) : DashboardRoute()
}






