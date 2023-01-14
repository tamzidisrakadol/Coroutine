package com.example.androidcourutine

import kotlinx.coroutines.*

fun main(){
    runBlocking {
        launch {
            delay(1000)
            println("This is 2ND work")
        }
        println("this is 1st work")
    }



}

