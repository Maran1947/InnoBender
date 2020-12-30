package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.innobender.dao.UserDao
import com.example.innobender.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnAlreadyHave: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_signup)
        btnAlreadyHave = findViewById(R.id.btn_alreadyHave)
        progressBar = findViewById(R.id.pb_loader)


        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            registerUser()
        }

        btnAlreadyHave.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    private fun registerUser() {
        val email = email.text.toString()
        val password = password.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty()) {

            btnSignUp.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {

                        val user = auth.uid?.let { Users(it, email) }
                        val userDao = UserDao()
                        userDao.addUser(user)

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
            btnSignUp.visibility = View.VISIBLE
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}