package com.example.calmcloud.subpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.calmcloud.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DataVisualizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_visualization)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_mood -> {
                    loadFragment(MoodFragment())
                    true
                }
                R.id.navigation_stress -> {
                    loadFragment(StressFragment())
                    true
                }
                R.id.navigation_sleep -> {
                    loadFragment(SleepFragment())
                    true
                }
                else -> false
            }
        }

        // Load the default fragment
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_mood
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
