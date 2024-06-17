package com.example.calmcloud.subpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcloud.R
import com.example.calmcloud.entity.Sleep

class SleepAdapter(private val sleepData: List<Sleep>) : RecyclerView.Adapter<SleepAdapter.SleepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sleep, parent, false)
        return SleepViewHolder(view)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val sleep = sleepData[position]
        holder.dateTextView.text = sleep.date
        holder.startTimeTextView.text = sleep.sleepDurationStart
        holder.endTimeTextView.text = sleep.sleepDurationEnd
        holder.qualityRatingBar.rating = sleep.sleepQuality.toFloat()
        holder.factorsTextView.text = sleep.sleepFactors
    }

    override fun getItemCount() = sleepData.size

    class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val startTimeTextView: TextView = itemView.findViewById(R.id.startTimeTextView)
        val endTimeTextView: TextView = itemView.findViewById(R.id.endTimeTextView)
        val qualityRatingBar: RatingBar = itemView.findViewById(R.id.qualityRatingBar)
        val factorsTextView: TextView = itemView.findViewById(R.id.factorsTextView)
    }
}
