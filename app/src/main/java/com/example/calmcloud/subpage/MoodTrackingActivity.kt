package com.example.calmcloud.subpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R
import android.widget.Button
import android.widget.ImageButton


class MoodTrackingActivity : AppCompatActivity() {

    private lateinit var moodButtons: List<ImageButton>
    private val selectedMoods = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mood_tracking)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mood_tracking)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        moodButtons = listOf(
            findViewById(R.id.happyButton),
            findViewById(R.id.thankfulButton),
            findViewById(R.id.energeticButton),
            findViewById(R.id.calmButton),
            findViewById(R.id.indifferentButton),
            findViewById(R.id.tiredButton),
            findViewById(R.id.sadButton),
            findViewById(R.id.anxiousButton),
            findViewById(R.id.angryButton)
        )

        moodButtons.forEach { button ->
            button.setOnClickListener { toggleMoodSelection(button) }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener { resetAllSelections() }
    }

    private fun toggleMoodSelection(button: ImageButton) {
        if (selectedMoods.contains(button.id)) {
            selectedMoods.remove(button.id)
            button.alpha = 1.0f // Deselect: reset alpha to default
        } else {
            selectedMoods.add(button.id)
            button.alpha = 0.3f // Select: reduce alpha for visual feedback
        }
    }

    private fun resetAllSelections() {
        selectedMoods.clear()
        moodButtons.forEach { it.alpha = 1.0f }
    }

    private fun enableEdgeToEdge() {
        // Your implementation of enabling edge-to-edge mode
    }
}