package com.publication.shiksharth_publication.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

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


