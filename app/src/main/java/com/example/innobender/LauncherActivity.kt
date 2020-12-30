package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LauncherActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        auth = FirebaseAuth.getInstance()

        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    private fun checkLoggedInState() {
        if(auth.currentUser == null) {
//            Toast.makeText(applicationContext, "You are not logged in", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}