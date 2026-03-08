package com.publication.shiksharth_publication.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.getValue


@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
  //  private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

       /* splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }*/

        dashboardIntent()

    }

    fun dashboardIntent() {
      /*  lifecycleScope.launch {
            viewModel.isLoading.filter { !it }.first()
            startActivity(
                Intent(this@SplashActivity, DashboardActivity::class.java)
            )
            finish()
        }
*/
    }
}


