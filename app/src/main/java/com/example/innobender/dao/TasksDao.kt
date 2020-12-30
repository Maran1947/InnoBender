package com.example.innobender.dao

import com.example.innobender.model.Tasks
import com.example.innobender.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TasksDao {
    val db = FirebaseFirestore.getInstance()
    val timelineCollection = db.collection("Tasks")
    val auth = Firebase.auth

    fun addTasks(tsk: Tasks) {

        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(Users::class.java)

            val currentTime = System.currentTimeMillis()
            val tasks = user?.let { Tasks(tsk.name, tsk.task, tsk.track, it, currentTime) }
            timelineCollection.document().set(tasks!!)
        }
    }
}