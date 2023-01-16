package com.example.androidcourutine

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis


class MainActivity3 : AppCompatActivity() {

    //creating channel
    val channel = Channel<Int>()
    val TAG:String ="Flows"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

     /*   suspend function use cases-> storing values in database, network call, Doing task that return single value
        Stream function use case -> Video Streaming, Fm Radio, Mobile sending audio signals to bluetooth speaker
        kotlin has asynchronous stream support using channels and flow
        * Channel -> Send & Receive -> Channels are hot-> Channels will continuously produce data if there is no consumer exist ex:Cinema hall
        * FLows -> Emit  and Collect -> Flows are Cold -> Flows will only product the data when there is consumer ex: netflix
        **/
        producer()
        consumer()

    }

    fun producer(){ //producing data
        //channel.send will run on Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
        }
    }

    fun consumer(){ //receiving data
        //channel.Receive will run on Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG,channel.receive().toString())
            Log.d(TAG,channel.receive().toString())
        }
    }

}