package org.hukehrs.appmessenger.example

import kotlin.system.measureTimeMillis


fun main() {
    val (messenger, firstSubscriber, secondService, errorSubscriber) = setup()
    // async time measurement
    val asyncTime = measureTimeMillis {
        messenger.publishAsync(Message())
    }
    println("async publish done after ${asyncTime}ms")
    println("press any key to continue")
    readLine()

    firstSubscriber.close()
    secondService.close()
    errorSubscriber.close()
}
