package com.example.calmcloud.subpage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcloud.R
import com.example.calmcloud.api.RetrofitClient
import com.example.calmcloud.entity.Mood
import com.example.calmcloud.entity.MoodType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataVisualizationActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private val moodData = mutableListOf<Mood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_visualization)

        barChart = findViewById(R.id.barChart)

        fetchMoodData()
    }

    private fun fetchMoodData() {
        RetrofitClient.api.getMoods().enqueue(object : Callback<List<Mood>> {
            override fun onResponse(call: Call<List<Mood>>, response: Response<List<Mood>>) {
                if (response.isSuccessful) {
                    moodData.clear()
                    response.body()?.let { moodData.addAll(it) }
                    updateBarChart(calculateMoodCounts())
                } else {
                    Log.e("DataVisualization", "Failed to fetch mood data")
                }
            }

            override fun onFailure(call: Call<List<Mood>>, t: Throwable) {
                Log.e("DataVisualization", "Error fetching mood data: ${t.message}")
            }
        })
    }

    private fun calculateMoodCounts(): Map<MoodType, Int> {
        val moodCountMap = mutableMapOf<MoodType, Int>()
        moodData.forEach { mood ->
            moodCountMap[mood.moodType] = moodCountMap.getOrDefault(mood.moodType, 0) + 1
        }
        return moodCountMap
    }

    private fun updateBarChart(moodCountMap: Map<MoodType, Int>) {
        val entries = moodCountMap.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        val dataSet = BarDataSet(entries, "Moods")

        dataSet.colors = moodCountMap.keys.map { moodType ->
            when (moodType) {
                MoodType.HAPPY -> getColor(R.color.green)
                MoodType.THANKFUL -> getColor(R.color.yellow)
                MoodType.ENERGETIC -> getColor(R.color.orange)
                MoodType.CALM -> getColor(R.color.blue)
                MoodType.INDIFFERENT -> getColor(R.color.gray)
                MoodType.TIRED -> getColor(R.color.darkgray)
                MoodType.SAD -> getColor(R.color.purple)
                MoodType.ANXIOUS -> getColor(R.color.red)
                MoodType.ANGRY -> getColor(R.color.darkred)
            }
        }

        val data = BarData(dataSet)
        data.barWidth = 0.9f // Set bar width to make space between bars
        barChart.data = data

        val moodTypeLabels = moodCountMap.keys.map { it.name }
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(moodTypeLabels)

        val yAxis = barChart.axisLeft
        yAxis.granularity = 1f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = (moodCountMap.values.maxOrNull() ?: 0) + 1.toFloat()
        yAxis.valueFormatter = IntegerValueFormatter()

        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.invalidate()

        Log.d("DataVisualization", "Bar chart updated with mood data")
    }

    class IntegerValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }
}
