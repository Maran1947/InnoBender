package com.example.innobender.dao

import com.example.innobender.model.Timeline
import com.example.innobender.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TimelineDao {
    val db = FirebaseFirestore.getInstance()
    val timelineCollection = db.collection("timelines")
    val auth = Firebase.auth

    fun addTimeline(tl: Timeline) {

        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(Users::class.java)

            val currentTime = System.currentTimeMillis()
            val timeline = user?.let { Timeline( tl.id,tl.name, tl.code, it, currentTime) }
            timelineCollection.document().set(timeline!!)
        }
    }
}