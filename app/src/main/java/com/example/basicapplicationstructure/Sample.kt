package com.example.basicapplicationstructure

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    //sequential execution
    launch {
        val ans = sample()
        val ans2 = sample2()

        println(ans + ans2)
    }


    //parallel execution
    val job1 = async {
        sample()
    }

    val job2 = async {
        sample2()
    }

    println(job1.await() + job2.await())
}

suspend fun sample(): Int {
    delay(10000)
    return 10
}

suspend fun sample2(): Int {
    delay(20000)
    return 20
}
