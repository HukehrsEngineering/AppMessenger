package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IAppMessage
import org.hukehrs.appmessenger.ISubscriber


class OtherMessage: IAppMessage

class AllSubscriber(private val messenger: AppMessenger<IAppMessage>): ISubscriber {

    var messageCount = 0

    init {
        messenger.subscribe<IAppMessage>(this)
    }

    override suspend fun receive(message: IAppMessage) {
        println("received message of type ${message::class.java.name}")
        messageCount++
    }

    override fun close() {
        messenger.deleteSubscriptions(this)
    }
}

fun main() {
    val messenger = AppMessenger<IAppMessage>("AllSubscriberTest")
    val subscriber = AllSubscriber(messenger)

    messenger.publishSync(Message())
    messenger.publishSync(OtherMessage())

    println("subscriber got ${subscriber.messageCount} of 2 messages")
}