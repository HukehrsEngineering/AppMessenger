package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IEventMessage
import org.hukehrs.appmessenger.ISubscriber


class OtherMessage: IEventMessage

class AllSubscriber(private val messenger: AppMessenger): ISubscriber {

    var messageCount = 0

    init {
        messenger.subscribeAll(this)
    }

    override suspend fun receive(message: IEventMessage) {
        println("received message of type ${message::class.java.name}")
        messageCount++
    }

    override fun close() {
        messenger.deleteSubscriptions(this)
    }
}

fun main() {
    val messenger = AppMessenger("AllSubscriberTest")
    val subscriber = AllSubscriber(messenger)

    messenger.publishSync(Message())
    messenger.publishSync(OtherMessage())

    println("subscriber got ${subscriber.messageCount} messages")
}