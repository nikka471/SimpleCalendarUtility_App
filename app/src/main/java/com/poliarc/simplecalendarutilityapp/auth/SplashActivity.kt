package com.poliarc.simplecalendarutilityapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.poliarc.simplecalendarutilityapp.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val splashDuration = 4000L // 4000ms = 4 seconds
    private var splashJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Launch coroutine to wait 4 seconds and then navigate
        splashJob = CoroutineScope(Dispatchers.Main).launch {
            delay(splashDuration)
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // so that splash activity is removed from back stack
    }

    override fun onDestroy() {
        super.onDestroy()
        splashJob?.cancel() // Cancel coroutine if activity is destroyed early
    }
}
