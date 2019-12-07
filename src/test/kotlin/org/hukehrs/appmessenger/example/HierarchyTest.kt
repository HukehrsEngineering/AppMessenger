package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IAppMessage
import org.hukehrs.appmessenger.ISubscriber

open class ParentMessage: IAppMessage

class ChildMessage: ParentMessage()

class HierarchySubscriber(private val messenger: AppMessenger<ParentMessage>): ISubscriber {

    var messageCount = 0

    init {
        messenger.subscribe<ParentMessage>(this)

        //following will not work
        //messenger.subscribe<Message>()
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
    val messenger = AppMessenger<ParentMessage>("AllSubscriberTest")
    val subscriber = HierarchySubscriber(messenger)

    messenger.publishSync(ParentMessage())
    messenger.publishSync(ChildMessage())

    println("subscriber got ${subscriber.messageCount} of 2 expected messages")
}