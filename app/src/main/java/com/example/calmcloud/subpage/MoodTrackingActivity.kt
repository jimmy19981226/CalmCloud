package com.example.calmcloud.subpage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcloud.R
import android.widget.Button
import android.widget.ImageButton
import com.example.calmcloud.api.RetrofitClient
import com.example.calmcloud.entity.Mood
import com.example.calmcloud.entity.MoodType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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

        findViewById<Button>(R.id.saveButton).setOnClickListener { saveSelections() }
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

    private fun saveSelections() {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val savedMoods = mutableListOf<Mood>()
        selectedMoods.forEach { id ->
            val moodType = when (id) {
                R.id.happyButton -> MoodType.HAPPY
                R.id.thankfulButton -> MoodType.THANKFUL
                R.id.energeticButton -> MoodType.ENERGETIC
                R.id.calmButton -> MoodType.CALM
                R.id.indifferentButton -> MoodType.INDIFFERENT
                R.id.tiredButton -> MoodType.TIRED
                R.id.sadButton -> MoodType.SAD
                R.id.anxiousButton -> MoodType.ANXIOUS
                R.id.angryButton -> MoodType.ANGRY
                else -> return@forEach
            }
            val mood = Mood(UUID.randomUUID().toString(), date, moodType)
            savedMoods.add(mood)

            // Save the mood object to your backend using Retrofit
            RetrofitClient.api.createMood(mood).enqueue(object : Callback<Mood> {
                override fun onResponse(call: Call<Mood>, response: Response<Mood>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MoodTrackingActivity, "Mood data saved successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MoodTrackingActivity, "Failed to save mood", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Mood>, t: Throwable) {
                    Toast.makeText(this@MoodTrackingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Show the mood entity in a toast
//        if (savedMoods.isNotEmpty()) {
//            val message = savedMoods.joinToString(separator = "\n") { "Mood: ${it.moodType}, Date: ${it.date}" }
//            Toast.makeText(this, "Saved Moods:\n$message", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "No moods selected to save.", Toast.LENGTH_SHORT).show()
//        }

        resetAllSelections()
    }

    private fun enableEdgeToEdge() {
        // Your implementation of enabling edge-to-edge mode
    }
}
