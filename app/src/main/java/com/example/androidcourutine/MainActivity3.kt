package com.example.androidcourutine

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis


class MainActivity3 : AppCompatActivity() {

    //creating channel
    val channel = Channel<Int>()
    val TAG: String = "Flows"

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

        lifecycleScope.launch {
            //this "data" variable  is a consumer \\ if there is no consumer then producer not produce data
            val data: Flow<Int> = listProducer()
            val time = measureTimeMillis {
                data.collect {
                    Log.d(TAG, "1st consumer ${it.toString()}")
                }
            }
            Log.d(TAG, "time taken $time")
        }

        lifecycleScope.launch {
            //can create multiple consumer -> there will be not waste of data for delay

            val data2 = listProducer()
            delay(2000L)
            //event of consumer

                data2.map {
                    it * 2
                }
                    .filter {
                        it < 200
                    }
                    .buffer(3)
                    .onStart {
                        //can add value on start
                        emit(-2)
                        Log.d(TAG,"started")
                    }
                    .onCompletion {
                        //can also add value just before complete
                        emit(200)
                        Log.d(TAG,"completed")
                    }
                    .onEach {
                        Log.d(TAG,"About to emit $it")
                    }
                    .collect {
                        Log.d(TAG, "2nd consumer ${it.toString()}")
                    }




        }

    }

    fun producer() { //producing data
        //channel.send will run on Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
        }
    }

    fun consumer() { //receiving data
        //channel.Receive will run on Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, channel.receive().toString())
            Log.d(TAG, channel.receive().toString())
        }
    }

    //listProducer is a flowing suspending function->it will producing data in every 1000s
    fun listProducer() = flow<Int> {
        val list = listOf<Int>(11, 22, 33, 44, 55, 66, 77, 88, 99)
        list.forEach {
            delay(1000L)
            emit(it)
        }
    }

}