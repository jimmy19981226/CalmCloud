package com.example.calmcloud.subpage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R

class SleepPatternsActivity : AppCompatActivity() {
    private lateinit var sleepDuration: EditText
    private lateinit var sleepQuality: RatingBar
    private lateinit var sleepFactors: EditText
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sleep_patterns)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the views
        sleepDuration = findViewById(R.id.sleepDuration)
        sleepQuality = findViewById(R.id.sleepQuality)
        sleepFactors = findViewById(R.id.sleepFactors)
        resetButton = findViewById(R.id.resetButton)

        // Set up the reset button click listener
        resetButton.setOnClickListener {
            resetAllFields()
        }
    }

    private fun resetAllFields() {
        sleepDuration.text.clear()
        sleepQuality.rating = 0f
        sleepFactors.text.clear()
    }
}
