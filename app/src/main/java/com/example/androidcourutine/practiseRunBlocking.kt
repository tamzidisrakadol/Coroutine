package com.example.androidcourutine

import kotlinx.coroutines.*

fun main()= runBlocking{
    //runblocking -> Blocking Coroutine
    //launch -> Standalone coroutine
    //launch(child) -> Standalone coroutine but in different thread pool
    //async -> Deferred Coroutine
    //Each Coroutine has its own coroutine Scope
    // if we want to use scope then we have to use "this" keyword
    //we can also use coroutine context by CoroutineContext



//    println("runblocing $this")
//
//    launch {
//        println("launch $this")
//
//        launch {
//            println("Child $this")
//        }
//    }
//
//    async {
//        println("async $this")
//    }

    println("R1: ${Thread.currentThread().name}")



    launch {
        //without parameter its known as confined dispatcher \\ it directly inherits from main coroutine and run on it
        println("C1: ${Thread.currentThread().name}")
        delay(2000L)
        //after suspending function it will run on same main thread
        println("C1 after delay:  ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default){
        //it similar as GlobalScope.Launch
        println("C2: ${Thread.currentThread().name}")
        delay(1000L)
        //after any suspending function it will run on same coroutine or any other coroutine
        println("C2 after Delay: ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Unconfined) {
        //it will inherit from main run blocking thats why it will run on main Thread
        println("C3: ${Thread.currentThread().name} ")
        delay(1000L)
        //after suspending function it will run on some other background thread
        println("C3 after delay:  ${Thread.currentThread().name}")

        //it will run same last executed coroutine ^^^^
        launch(coroutineContext) {
            println("C3 child: ${Thread.currentThread().name}")
            delay(2000L)
            //after suspending function it will run on same main thread
            println("C3 child after delay:  ${Thread.currentThread().name}")
        }

    }



    launch(coroutineContext) {
        // it directly inherits from main coroutine and run on it
        println("C4: ${Thread.currentThread().name}")
        delay(2000L)
        //after suspending function it will run on same main thread
        println("C4 after delay:  ${Thread.currentThread().name}")
    }

    println("Completed")


}

