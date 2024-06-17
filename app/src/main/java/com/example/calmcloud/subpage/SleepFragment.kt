package com.example.calmcloud.subpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcloud.R
import com.example.calmcloud.api.RetrofitClient
import com.example.calmcloud.entity.Sleep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SleepFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val sleepData = mutableListOf<Sleep>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SleepAdapter(sleepData)
        fetchSleepData()
        return view
    }

    private fun fetchSleepData() {
        RetrofitClient.api.getSleepData().enqueue(object : Callback<List<Sleep>> {
            override fun onResponse(call: Call<List<Sleep>>, response: Response<List<Sleep>>) {
                if (response.isSuccessful) {
                    sleepData.clear()
                    response.body()?.let { sleepData.addAll(it) }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Log.e("SleepFragment", "Failed to fetch sleep data")
                }
            }

            override fun onFailure(call: Call<List<Sleep>>, t: Throwable) {
                Log.e("SleepFragment", "Error fetching sleep data: ${t.message}")
            }
        })
    }
}
