package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class SignInActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnDontHave: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_signin)
        btnDontHave= findViewById(R.id.btn_dontHave)
        progressBar = findViewById(R.id.pb_loader)

        auth = FirebaseAuth.getInstance()

        btnSignIn.setOnClickListener {
            logInUser()
        }

        btnDontHave.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    private fun logInUser() {
        val email = email.text.toString()
        val password = password.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty()) {


            btnSignIn.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkLoggedInState() {
        if(auth.currentUser == null) {
//            Toast.makeText(applicationContext, "You are not logged in", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.GONE
            btnSignIn.visibility = View.VISIBLE
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}