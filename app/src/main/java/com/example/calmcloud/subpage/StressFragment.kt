package com.example.calmcloud.subpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.calmcloud.R
import com.example.calmcloud.api.RetrofitClient
import com.example.calmcloud.entity.StressLevel
import com.example.calmcloud.entity.StressLevelType
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

class StressFragment : Fragment() {

    private lateinit var barChart: BarChart
    private val stressData = mutableListOf<StressLevel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stress, container, false)
        barChart = view.findViewById(R.id.barChart)
        fetchStressData()
        return view
    }

    private fun fetchStressData() {
        RetrofitClient.api.getStressLevels().enqueue(object : Callback<List<StressLevel>> {
            override fun onResponse(call: Call<List<StressLevel>>, response: Response<List<StressLevel>>) {
                if (response.isSuccessful) {
                    stressData.clear()
                    response.body()?.let { stressData.addAll(it) }
                    updateBarChart(calculateStressCounts())
                } else {
                    Log.e("StressFragment", "Failed to fetch stress data")
                }
            }

            override fun onFailure(call: Call<List<StressLevel>>, t: Throwable) {
                Log.e("StressFragment", "Error fetching stress data: ${t.message}")
            }
        })
    }

    private fun calculateStressCounts(): Map<StressLevelType, Int> {
        val stressCountMap = mutableMapOf<StressLevelType, Int>()
        stressData.forEach { stressLevel ->
            stressLevel.stressLevels.forEach { stressLevelType ->
                stressCountMap[stressLevelType] = stressCountMap.getOrDefault(stressLevelType, 0) + 1
            }
        }
        return stressCountMap
    }

    private fun updateBarChart(stressCountMap: Map<StressLevelType, Int>) {
        val entries = stressCountMap.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        val dataSet = BarDataSet(entries, "Stress Levels")

        dataSet.colors = stressCountMap.keys.map { stressLevelType ->
            when (stressLevelType) {
                StressLevelType.NO_STRESS -> resources.getColor(R.color.green)
                StressLevelType.VERY_LOW_STRESS -> resources.getColor(R.color.yellow)
                StressLevelType.LOW_STRESS -> resources.getColor(R.color.orange)
                StressLevelType.MODERATE_STRESS -> resources.getColor(R.color.blue)
                StressLevelType.HIGH_STRESS -> resources.getColor(R.color.gray)
                StressLevelType.VERY_HIGH_STRESS -> resources.getColor(R.color.darkgray)
                StressLevelType.EXTREME_STRESS -> resources.getColor(R.color.red)
            }
        }

        val data = BarData(dataSet)
        data.barWidth = 0.9f
        barChart.data = data

        val stressTypeLabels = stressCountMap.keys.map { it.name }
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(stressTypeLabels)

        val yAxis = barChart.axisLeft
        yAxis.granularity = 1f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = (stressCountMap.values.maxOrNull() ?: 0) + 1.toFloat()
        yAxis.valueFormatter = IntegerValueFormatter()

        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.invalidate()

        Log.d("StressFragment", "Bar chart updated with stress data")
    }

    class IntegerValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }
}
