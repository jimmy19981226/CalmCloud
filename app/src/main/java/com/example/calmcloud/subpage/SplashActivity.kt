package com.example.calmcloud.subpage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcloud.subpage.MoodTrackingActivity
import com.example.calmcloud.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Load the fade-in animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        findViewById<ImageView>(R.id.logo).startAnimation(fadeIn)
        findViewById<TextView>(R.id.app_name).startAnimation(fadeIn)

        // Duration for the splash screen to be displayed (e.g., 3 seconds)
        val splashDuration = 3000L

        // Navigate to the main activity after the splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MoodTrackingActivity::class.java))
            finish()
        }, splashDuration)
    }
}