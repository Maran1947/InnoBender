package com.example.innobender.model


data class Tasks(
        var name:String = "",
        var task:String = "",
        var track:String = "",
        var createdBy:Users = Users(),
        var createdAt:Long = 0L,
)