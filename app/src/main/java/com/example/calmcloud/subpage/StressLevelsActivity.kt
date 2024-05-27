package com.example.calmcloud.subpage

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R

class StressLevelsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stress_levels)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Reference to the RadioGroup and Reset Button
        val stressLevelsList = findViewById<RadioGroup>(R.id.stressLevelsList)
        val resetButton = findViewById<Button>(R.id.resetButton)

        // Set OnClickListener for the Reset Button
        resetButton.setOnClickListener {
            stressLevelsList.clearCheck()
        }
    }
}
