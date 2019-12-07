package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import kotlin.system.measureTimeMillis


fun main() {
    // choose one
    val messenger = AppMessenger("Example")
    //val messenger = GlobalAppMessenger()

    messenger.debug = true
    // will warn in log, due to missing subscribers
    messenger.publishSync(Message())

    val firstSubscriber = ExampleSubscriber(messenger, "first-service", 1600, false)
    val secondService = ExampleSubscriber(messenger, "second-service", 800, false)
    val errorSubscriber = ExampleSubscriber(messenger, "error-service", 200, true)

    // async time measurement
    // first call is relatively slow, this is no indicator for actual runtime performance.
    // Subsequent publishes are a lot quicker
    val asyncTime = measureTimeMillis {
        messenger.publishAsync(Message())
    }
    println("async publish done after ${asyncTime}ms")
    println("press any key to continue")
    readLine()

    // sync time measurement
    val syncTime = measureTimeMillis {
        messenger.publishSync(Message())
    }
    println("sync publish done after ${syncTime}ms")

    firstSubscriber.close()
    secondService.close()
    errorSubscriber.close()
}
