package com.example.androidcourutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG:String = "KotlinFun"
    lateinit var counterText:TextView
    var counterNum:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterText = findViewById(R.id.textView)
        Log.d(TAG, "Current Thread name is ${Thread.currentThread().name}")

    }

    fun fCounter(view: View) {
        counterNum++
        Log.d(TAG, "Current Thread name is ${Thread.currentThread().name}")
        counterText.text=counterNum.toString()

        CoroutineScope(Dispatchers.IO).launch {
            //suspending function can only run in coroutine
            task1()
            task2()
        }
    }

    fun doAction(view: View) {
        /*Coroutine run on top of thread & its lightweight
        * Dispatchers is a way to define threads on which Coroutines are executed
        * Predefined dispatchers - dispatchers.io for i/o operation \\ dispatchers.Main for running on main thread \\ dispatchers.default for default operation
        * CoroutineScope -> lifetime of Coroutine
        * GlobalScope -> attached with whole application
        * MainScope -> attached with MainActivity*/

       CoroutineScope(Dispatchers.IO).launch {
           Log.d(TAG,"1-${Thread.currentThread().name}")
       }

        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG,"2-${Thread.currentThread().name}")
        }

        MainScope().launch(Dispatchers.Default) {
            Log.d(TAG,"3-${Thread.currentThread().name}")
        }
    }


    private fun executeLongRunningTask(){
        for ( i in 1.. 1000000000L){
           Log.d(TAG,i.toString())
        }
    }


    //suspending function for coroutine -> yield(), delay() method use in suspend function
    suspend fun task1(){
        Log.d(TAG,"Starting task1")
        yield()
        Log.d(TAG,"Ending task1")
    }
    suspend fun task2(){
        Log.d(TAG,"Starting task2")
        delay(1000)
        Log.d(TAG,"Ending task2")
    }
}