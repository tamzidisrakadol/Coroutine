package com.example.androidcourutine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var text:String,
    var author:String
)
