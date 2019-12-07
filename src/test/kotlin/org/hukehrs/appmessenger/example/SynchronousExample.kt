package org.hukehrs.appmessenger.example

import kotlin.system.measureTimeMillis


fun main() {
    val (messenger, firstSubscriber, secondService, errorSubscriber) = setup()
    // async time measurement
    val asyncTime = measureTimeMillis {
        messenger.publishSync(Message())
    }
    println("sync publish done after ${asyncTime}ms")

    firstSubscriber.close()
    secondService.close()
    errorSubscriber.close()
}
