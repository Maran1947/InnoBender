package com.example.innobender

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.innobender.dao.TasksDao
import com.example.innobender.dao.TimelineDao
import com.example.innobender.dao.UserDao
import com.example.innobender.model.Tasks
import com.example.innobender.model.Timeline
import com.google.firebase.auth.FirebaseAuth

class TimelineTasksActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var userMap = HashMap<String, Any>()

    private lateinit var etTimelineName: EditText
    private lateinit var etTask: EditText
    private lateinit var etTrack: EditText

    private lateinit var btnCreateTask: Button

    val taskDao = TasksDao()
    val userDao = UserDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_tasks)
        setSupportActionBar(findViewById(R.id.toolbar))

        title = "Create New Class"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        etTimelineName = findViewById(R.id.et_tl_name)
        etTask = findViewById(R.id.et_task)
        etTrack = findViewById(R.id.et_track)

        btnCreateTask = findViewById(R.id.btn_create_task)

        btnCreateTask.setOnClickListener {

            userMap["Timeline/${taskDao.timelineCollection.id}/name"] = etTimelineName.text.toString()
            userMap["Timeline/${taskDao.timelineCollection.id}/taskOne"] = etTask.text.toString()
            userMap["Timeline/${taskDao.timelineCollection.id}/track"] = etTrack.text.toString()

            val task = Tasks( etTimelineName.text.toString(),
                etTask.text.toString(), etTrack.text.toString(),
                )

            if (!TextUtils.isEmpty(userMap["Timeline/$taskDao.timelineCollection.id/name"].toString()) &&
                !TextUtils.isEmpty(userMap["Timeline/$taskDao.timelineCollection.id/task"].toString()) &&
                !TextUtils.isEmpty(userMap["Timeline/$taskDao.timelineCollection.id/track"].toString()))  {

                taskDao.addTasks(task)
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CurrentTimelineActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_LONG).show()
            }

        }
    }
}