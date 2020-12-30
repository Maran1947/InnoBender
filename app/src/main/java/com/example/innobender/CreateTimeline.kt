package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.TextUtils

import android.widget.*

import com.example.innobender.dao.TimelineDao
import com.example.innobender.dao.UserDao
import com.example.innobender.model.Timeline

import com.google.firebase.auth.FirebaseAuth


class CreateTimeline : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    public var userMap = HashMap<String, Any>()

    private lateinit var etTimelineName: EditText
    private lateinit var etTimelineCode: EditText


    private lateinit var btnCreateTimeline: Button
    private lateinit var progressBar: ProgressBar

    val tlDao = TimelineDao()
    val userDao = UserDao()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timeline)

        title = "Create New Class"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        etTimelineName = findViewById(R.id.et_tl_name)
        etTimelineCode = findViewById(R.id.create_tl_code)
        btnCreateTimeline = findViewById(R.id.btn_create_tl)
        progressBar = findViewById(R.id.create_class_progress_bar)


        auth = FirebaseAuth.getInstance()


        btnCreateTimeline.setOnClickListener {

            userMap["Timeline/${tlDao.timelineCollection.id}/name"] = etTimelineName.text.toString()
            userMap["Timeline/${tlDao.timelineCollection.id}/status"] = etTimelineCode.text.toString()


            val timeline = Timeline( tlDao.timelineCollection.id,etTimelineName.text.toString(), etTimelineCode.text.toString())

            if (!TextUtils.isEmpty(userMap["Timeline/$tlDao.timelineCollection.id/name"].toString()) &&
                    !TextUtils.isEmpty(userMap["Timeline/$tlDao.timelineCollection.id/code"].toString()))  {

                tlDao.addTimeline(timeline)
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