package com.example.androidcourutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private val TAG:String = "KotlinFun"
    lateinit var counterText:TextView
    var counterNum:Int =0
    lateinit var mainViewModel:MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterText = findViewById(R.id.textView)


        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java] // using index operator instead of get

        CoroutineScope(Dispatchers.Main).launch {
            //executeFun()
            executeTask()
        }



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

    private suspend fun getFbFollowers():Int{
        delay(1000)
        return 100
    }
    private suspend fun getInstaFollowers():Int{
        delay(1000)
        return 154
    }


     fun followerCounter(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            printFollowers()
        }
    }

    private suspend fun printFollowers() {
      /*  kotlin job helps to execute coroutine \\ job.join() will hold the function until it get the following output
      * if a we expect result/output from coroutine then we can use async.Async returns Deferred object . And Deferred is generics type  and it means postpone*/

        var fbFollower = 0
        val job = CoroutineScope(Dispatchers.IO).launch {
            fbFollower = getFbFollowers()
        }
        val insta = CoroutineScope(Dispatchers.IO).async {
            getInstaFollowers()
        }
        job.join()
        Log.d(TAG, fbFollower.toString()+" "+insta.await())

        //another way to write above function ^^^^
//        CoroutineScope(Dispatchers.IO).launch {
//            var fb = async { getFbFollowers() }
//            var instaf = async { getInstaFollowers() }
//            Log.d(TAG,"fb= ${fb.await()} insta= ${instaf.await()}")
//        }
    }

    private suspend fun executeFun(){
        // job hierarchy + we can create multiple job in single coroutine \\ in this function parent job will start and then child job.Parent job will ended until the child job end
        // we can cancel the job if parent job get cancel all the child job will also cancel



        val parentJob = GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG,"parent job started")

            var childJob = launch(Dispatchers.IO) {
                Log.d(TAG,"child job started")
                delay(5000)
                Log.d(TAG,"child job ended")
            }
            delay(3000)
            Log.d(TAG,"parent job ended")
        }
        parentJob.join()
        Log.d(TAG,"parent job completed")
    }
    private suspend fun executeTask(){
        Log.d(TAG,"Before starting")
        //globalScope is non blocking nature but with context is blocking nature it will suspend the function complete the task then move forward and withContext run with context

        withContext(Dispatchers.IO){
            delay(2000L)
            Log.d(TAG,"Inside")
        }
        Log.d(TAG,"After")
    }

    fun intentAction(view: View) {

        runBlocking {
            lifecycleScope.launch {
                delay(1000)
                val intent= Intent(this@MainActivity,MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}