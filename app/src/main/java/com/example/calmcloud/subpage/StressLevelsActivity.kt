package com.example.calmcloud.subpage

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R
import com.example.calmcloud.entity.StressLevel
import com.example.calmcloud.entity.StressLevelType
import java.text.SimpleDateFormat
import java.util.*

class StressLevelsActivity : AppCompatActivity() {
    private val selectedStressLevels = mutableSetOf<StressLevelType>()

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

        // Set OnCheckedChangeListener for the RadioGroup
        stressLevelsList.setOnCheckedChangeListener { _, checkedId ->
            val stressLevel = when (checkedId) {
                R.id.noStress -> StressLevelType.NO_STRESS
                R.id.veryLowStress -> StressLevelType.VERY_LOW_STRESS
                R.id.lowStress -> StressLevelType.LOW_STRESS
                R.id.moderateStress -> StressLevelType.MODERATE_STRESS
                R.id.highStress -> StressLevelType.HIGH_STRESS
                R.id.veryHighStress -> StressLevelType.VERY_HIGH_STRESS
                R.id.extremeStress -> StressLevelType.EXTREME_STRESS
                else -> null
            }
            stressLevel?.let { selectedStressLevels.add(it) }
        }

        // Set OnClickListener for the Save Button
        findViewById<Button>(R.id.saveButton).setOnClickListener { saveSelections() }

        // Set OnClickListener for the Reset Button
        findViewById<Button>(R.id.resetButton).setOnClickListener { resetAllSelections(stressLevelsList) }
    }

    private fun saveSelections() {
        if (selectedStressLevels.isNotEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            val stressLevel = StressLevel(UUID.randomUUID().toString(), date, selectedStressLevels.toList())

            // Show a toast with the details
            val message = selectedStressLevels.joinToString(separator = "\n") { it.name }
            Toast.makeText(this, "Saved Stress Levels:\n$message", Toast.LENGTH_LONG).show()

            // Save the stress level object to your database or backend here
            // Example: database.saveStressLevel(stressLevel)

            resetAllSelections(findViewById(R.id.stressLevelsList))
        } else {
            Toast.makeText(this, "No stress levels selected to save.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetAllSelections(stressLevelsList: RadioGroup) {
        selectedStressLevels.clear()
        stressLevelsList.clearCheck()
    }
}
