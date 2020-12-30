package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.innobender.dao.TimelineDao
import com.example.innobender.dao.UserDao
import com.example.innobender.model.Timeline
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class JoinTimeline : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var etTimelineName: EditText
    private lateinit var etTimelineCode: EditText

    private lateinit var btnJoinTimeline: Button
    private lateinit var progressBar: ProgressBar

    val tlDao = TimelineDao()
    val userDao = UserDao()


    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_timeline)

        title = "Join Timeline"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            return
        }

        etTimelineName = findViewById(R.id.et_tl_name)
        etTimelineCode = findViewById(R.id.tl_code)
        btnJoinTimeline = findViewById(R.id.btn_join_timeline)


        btnJoinTimeline.setOnClickListener {

            val docRef = db.collection("timelines").document("lfmd2UR69ukrOpmf8llL")
            docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d("TAG", "DocumentSnapshot data: ${document.data?.get("name")}")
                            Log.d("TAG", "DocumentSnapshot data: ${document.data?.get("code")}")

                            if (document.data?.get("name") == etTimelineName.text.toString() &&
                                    document.data?.get("code") == etTimelineCode.text.toString()) {
                                Toast.makeText(this, "Join Successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, CurrentTimelineActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Log.d("TAG", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "get failed with ", exception)
                    }

        }
    }
}