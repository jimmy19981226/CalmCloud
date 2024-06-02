package com.example.calmcloud.subpage

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R
import com.example.calmcloud.entity.Sleep
import java.text.SimpleDateFormat
import java.util.*

class SleepPatternsActivity : AppCompatActivity() {
    private lateinit var startTimeSpinner: Spinner
    private lateinit var endTimeSpinner: Spinner
    private lateinit var sleepQuality: RatingBar
    private lateinit var sleepFactors: EditText
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button

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
        startTimeSpinner = findViewById(R.id.startTimeSpinner)
        endTimeSpinner = findViewById(R.id.endTimeSpinner)
        sleepQuality = findViewById(R.id.sleepQuality)
        sleepFactors = findViewById(R.id.sleepFactors)
        resetButton = findViewById(R.id.resetButton)
        saveButton = findViewById(R.id.saveButton)

        // Populate the spinners with time values
        val timeList = generateTimeList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        startTimeSpinner.adapter = adapter
        endTimeSpinner.adapter = adapter

        // Set up the reset button click listener
        resetButton.setOnClickListener {
            resetAllFields()
        }

        // Set up the save button click listener
        saveButton.setOnClickListener {
            saveSelections()
        }
    }

    private fun generateTimeList(): List<String> {
        val timeList = mutableListOf<String>()
        for (hour in 0..23) {
            for (minute in listOf("00", "30")) {
                val amPm = if (hour < 12) "AM" else "PM"
                val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
                timeList.add("$displayHour:$minute $amPm")
            }
        }
        return timeList
    }

    private fun resetAllFields() {
        startTimeSpinner.setSelection(0)
        endTimeSpinner.setSelection(0)
        sleepQuality.rating = 0f
        sleepFactors.text.clear()
    }

    private fun saveSelections() {
        val date = SimpleDateFormat("MM-dd-yyyy", Locale.US).format(Date())
        val startTime = startTimeSpinner.selectedItem.toString()
        val endTime = endTimeSpinner.selectedItem.toString()
        val quality = sleepQuality.rating.toInt()
        val factors = sleepFactors.text.toString()

        if (startTime.isNotBlank() && endTime.isNotBlank() && factors.isNotBlank()) {
            val sleep = Sleep(UUID.randomUUID().toString(), date, startTime, endTime, quality, factors)
            // Save the sleep object to your database or backend here
            // Example: database.saveSleep(sleep)

            val message = "Saved Sleep Data:\nDate: ${sleep.date}\nStart: ${sleep.sleepDurationStart}\nEnd: ${sleep.sleepDurationEnd}\nQuality: ${sleep.sleepQuality}\nFactors: ${sleep.sleepFactors}"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            resetAllFields()
        } else {
            Toast.makeText(this, "Please fill out all fields to save the data.", Toast.LENGTH_SHORT).show()
        }
    }
}
