package com.example.innobender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.innobender.R
import com.example.innobender.model.Tasks
import com.example.innobender.model.Timeline
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CurrentTimelineAdapter(options: FirestoreRecyclerOptions<Tasks>) :
    FirestoreRecyclerAdapter<Tasks, CurrentTimelineAdapter.CurrentTimelineViewHolder>(options) {

    class CurrentTimelineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv_blue:TextView = itemView.findViewById(R.id.tv_blue)
        val tv_brown:TextView = itemView.findViewById(R.id.tv_brown)
        val view_top:View = itemView.findViewById(R.id.view_top)
        val view_circle:TextView = itemView.findViewById(R.id.view_circle_middle)
        val view_down:View = itemView.findViewById(R.id.view_down)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentTimelineViewHolder {
        return CurrentTimelineViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.single_timeline_layout, parent, false))
    }

    override fun onBindViewHolder(
        holder: CurrentTimelineViewHolder,
        position: Int,
        model: Tasks
    ) {
        holder.tv_blue.text = model.task
        holder.tv_brown.text = model.task
        holder.view_circle.text = model.track.toString()
    }

}