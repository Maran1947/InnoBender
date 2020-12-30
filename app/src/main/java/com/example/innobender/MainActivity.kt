package com.example.innobender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    final var map = HashMap<String, Any>()

   private lateinit var drawerLayout: DrawerLayout
   private lateinit var toolbar: Toolbar
   private lateinit var navigationView: NavigationView

   private lateinit var btnCreateTimeline: Button
   private lateinit var btnJoinTimeline: Button

   private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigation_view)
        btnCreateTimeline = findViewById(R.id.btn_create_timeline)
        btnJoinTimeline = findViewById(R.id.btn_join_timeline)

        auth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)

        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_new_timeline -> {
                    Toast.makeText(this,"New Timeline",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CreateTimeline::class.java)
                    startActivity(intent)

                }

                R.id.log_out -> {
                    Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
            }

        btnCreateTimeline.setOnClickListener {
            val intent = Intent(this, CreateTimeline::class.java)
            startActivity(intent)
        }

        btnJoinTimeline.setOnClickListener {
            val intent = Intent(this, JoinTimeline::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Log.d("TAG", "back")
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            Log.d("TAG", "back ")
            super.onBackPressed()
        }
    }
}