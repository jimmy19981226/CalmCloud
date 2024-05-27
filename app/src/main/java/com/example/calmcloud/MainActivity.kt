package com.example.calmcloud

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.subpage.MoodTrackingActivity
import com.example.calmcloud.subpage.StressLevelsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Create button
        val moodTrackingButton: ImageButton = findViewById(R.id.moodTrackingButton)
        val stressLevelsButton: ImageButton = findViewById(R.id.stressLevelsButton)
        val sleepPatternsButton: ImageButton = findViewById(R.id.sleepPatternsButton)
        val dataVisualizationButton: ImageButton = findViewById(R.id.dataVisualizationButton)

        //Add listener to button to start new activity
        moodTrackingButton.setOnClickListener {
            val intent = Intent(this, MoodTrackingActivity::class.java)
            startActivity(intent)
        }

        stressLevelsButton.setOnClickListener {
            val intent = Intent(this, StressLevelsActivity::class.java)
            startActivity(intent)
        }
//
//        sleepPatternsButton.setOnClickListener {
//            val intent = Intent(this, SleepPatternsActivity::class.java)
//            startActivity(intent)
//        }
//
//        dataVisualizationButton.setOnClickListener {
//            val intent = Intent(this, DataVisualizationActivity::class.java)
//            startActivity(intent)
//        }
    }
}