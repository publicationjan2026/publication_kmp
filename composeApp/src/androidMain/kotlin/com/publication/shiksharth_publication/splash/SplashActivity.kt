package com.publication.shiksharth_publication.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.publication.shiksharth_publication.dashboard.DashboardActivity

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        dashboardIntent()

    }

    fun dashboardIntent() {
        startActivity(
            Intent(this@SplashActivity, DashboardActivity::class.java)
        )
        finish()
    }
}


