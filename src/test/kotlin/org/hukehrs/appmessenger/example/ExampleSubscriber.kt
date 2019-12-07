package org.hukehrs.appmessenger.example

import kotlinx.coroutines.delay
import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IEventMessage
import org.hukehrs.appmessenger.ISubscriber

class ExampleSubscriber(private val messenger: AppMessenger,
                        private val name: String,
                        private val delay: Long,
                        private val throwException: Boolean): ISubscriber {

    init {
        messenger.subscribe<Message>(this)
    }

    override suspend fun receive(message: IEventMessage) {
        when(message) {
            is Message -> calculate()
        }
    }

    private suspend fun calculate() {
        val start = System.currentTimeMillis()
        delay(delay)
        if(throwException) {
            println("$name failed after ${System.currentTimeMillis() - start}ms")
            throw Exception("worker $name failed")
        } else {
            println("$name done after ${System.currentTimeMillis() - start}ms")
        }
    }

    override fun close() {
        messenger.deleteSubscriptions(this)
    }
}
