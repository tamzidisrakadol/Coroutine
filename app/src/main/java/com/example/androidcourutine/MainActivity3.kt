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
           * LiveData -> Transformation on main thread, operators, LifeCycle Dependent
           *StateFlow -> run on any thread, multiple operator, LifeCycle independent
           **/
        producer()
        consumer()
//
//        lifecycleScope.launch {
//            //this "data" variable  is a consumer \\ if there is no consumer then producer not produce data
//            val data: Flow<Int> = listProducer()
//            val time = measureTimeMillis {
//                data.collect {
//                    Log.d(TAG, "1st consumer ${it.toString()}")
//                }
//            }
//            Log.d(TAG, "time taken $time")
//        }
//
//        lifecycleScope.launch {
//            //can create multiple consumer -> there will be not waste of data for delay
//
//            val data2 = listProducer()
//            delay(2000L)
//            //event of consumer
//
//                data2.map {
//                    Log.d(TAG,"map thread name:  ${Thread.currentThread().name}")
//                    it * 2
//                }
//                    .filter {
//                        it < 200
//                    }
//                    .buffer(3)
//                    .onStart {
//                        //can add value on start
//                        emit(-2)
//                        Log.d(TAG,"started")
//                    }
//                    .onCompletion {
//                        //can also add value just before complete
//                        emit(200)
//                        Log.d(TAG,"completed")
//                    }
//                    .onEach {
//                        Log.d(TAG,"About to emit $it")
//                    }
//                    .flowOn(Dispatchers.IO) // consumer can switch the coroutine context by telling it to flowOn
//                    .collect {
//                        Log.d(TAG,"Thread name = ${Thread.currentThread().name}")
//                        Log.d(TAG, "2nd consumer ${it.toString()}")
//                    }
//
//        }
//
//        lifecycleScope.launch {
//            try {
//                listProducer().collect{
//                    Log.d(TAG,it.toString())
//                }
//            }catch (e:Exception){
//                Log.d(TAG,e.message.toString())
//            }
//        }
       /* lifecycleScope.launch {
            val data4 = flowListUser()
            data4.collect{
                Log.d(TAG,"data4 ITem- $it")
            }
        }

        lifecycleScope.launch {
            val data5 = flowListUser()
            delay(4000L)
            data5.collect{
                Log.d(TAG,"data5 ITem- $it")
            }
        }
*/

        lifecycleScope.launch {
            val data6 = stateFowListUser()
            delay(6000L)
            data6.collect{
                Log.d(TAG,"data6 ITem- $it")
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
            throw Exception("Error in list producing ")
        }
    }.catch {
        Log.d(TAG,"Emmit catch ${it.message}")
    }


    //SharedFlow ->another types of flow which are hot in nature-> it can be only SharedFlow or MutableSharedFlow
    //it can also store value for slow consumer
    //flowListUser function is encapsulated by mutableSharedFlow
    private fun flowListUser(): Flow<Int> {
        val mutableFlow = MutableSharedFlow<Int>(2)
        lifecycleScope.launch{
            val list = listOf<Int>(12,23,34,45,56,67,78,89,90)
            list.forEach {
                delay(1000L)
                mutableFlow.emit(it)

            }
        }
        return mutableFlow
    }


//stateFlow -> Hot
    private fun stateFowListUser():Flow<Int>{
        val mutableStateFlow = MutableStateFlow(10)
        lifecycleScope.launch{
            delay(2000L)
            mutableStateFlow.emit(200)
            delay(2000L)
            mutableStateFlow.emit(400)
        }
        return mutableStateFlow
    }

}