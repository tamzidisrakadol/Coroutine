package com.example.androidcourutine

import android.provider.ContactsContract.CommonDataKinds.Email


data class Comment(
    val body: String,
    val id: Int,
    val email:String,
    val name:String,
    val postId:Int
)