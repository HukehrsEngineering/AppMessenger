package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger

fun setup(): MessengerSubscriberTuple {
    // choose one
    val messenger = AppMessenger("Example")
    //val messenger = GlobalAppMessenger()

    val firstSubscriber = ExampleSubscriber(messenger, "first-service", 1600, false)
    val secondService = ExampleSubscriber(messenger, "second-service", 800, false)
    val errorSubscriber = ExampleSubscriber(messenger, "error-service", 200, true)

    return MessengerSubscriberTuple(messenger, firstSubscriber, secondService, errorSubscriber)
}