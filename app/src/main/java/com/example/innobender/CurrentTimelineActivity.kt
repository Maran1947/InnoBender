package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innobender.adapters.CurrentTimelineAdapter
import com.example.innobender.dao.TasksDao
import com.example.innobender.dao.TimelineDao
import com.example.innobender.model.Tasks
import com.example.innobender.model.Timeline
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query


class CurrentTimelineActivity : AppCompatActivity() {

    private lateinit var taskDao: TasksDao
    private lateinit var adapter: CurrentTimelineAdapter
    private lateinit var fab: FloatingActionButton

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_timeline)

        fab = findViewById(R.id.fab)

        fab.setOnClickListener{
            val intent = Intent(this, TimelineTasksActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        taskDao = TasksDao()

        val timelineCollections = taskDao.timelineCollection

        val query = timelineCollections.orderBy("createdAt", Query.Direction.ASCENDING)

        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Tasks>().setQuery(query, Tasks::class.java).build()

        adapter = CurrentTimelineAdapter(recyclerViewOptions)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}
