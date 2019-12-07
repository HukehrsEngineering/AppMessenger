package org.hukehrs.appmessenger

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hukehrs.extensions.printableStacktrace
import org.slf4j.LoggerFactory
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

@Suppress("MemberVisibilityCanBePrivate")
open class AppMessenger(val name: String,
                        private val sendScope: CoroutineScope = SendScope(name),
                        private val receiveScope: CoroutineScope = ReceiveScope(name)) {

    companion object {
        val log = LoggerFactory.getLogger(AppMessenger::class.java)!!
    }

    private val subscribers = mutableMapOf<Class<*>, MutableList<ISubscriber>>()

    private val lock = ReentrantReadWriteLock()

    var debug = false

    fun <T: IAppMessage>subscribe(subscriber: ISubscriber, cls: Class<T>)
    {
        lock.write {
            if(!subscribers.containsKey(cls))
            {
                subscribers[cls] = mutableListOf()
            }

            subscribers[cls]!!.add(subscriber)
        }
    }

    inline fun <reified T: IAppMessage>subscribe(subscriber: ISubscriber)
    {
        subscribe(subscriber, T::class.java)
    }

    fun publishAsync(message: IAppMessage) {
        publish(message, true)
    }

    fun publishSync(message: IAppMessage) {
        publish(message, false)
    }

    private fun publish(message: IAppMessage, async: Boolean)
    {
        val stacktrace = if(debug) {
            val trace = Thread.currentThread().stackTrace.drop(1)
            log.trace("[{}AppMessenger]: publishing text {} from {}", name, message, trace[2])
            trace
        } else { null }
        val currentSubscribers = getSubscribers(message.javaClass)

        if (currentSubscribers.isEmpty()) {
            log.warn("[{}AppMessenger]: no subscribers for message of type {}", name, message::class.java.name)
        } else {
            if (async) {
                sendScope.launch {
                    publishToSubscribers(currentSubscribers, message, true, stacktrace)
                }
            } else {
                runBlocking {
                    publishToSubscribers(currentSubscribers, message, false, stacktrace)
                }
            }
        }
    }

    fun <T> getSubscribers(messageClass: Class<T>): List<ISubscriber> {
        return lock.read {
            subscribers.filter { it.key.isAssignableFrom(messageClass) }.values.flatten()
        }
    }

    private suspend fun publishToSubscribers(
        currentSubscribers: List<ISubscriber>,
        message: IAppMessage,
        async: Boolean,
        stacktrace: List<StackTraceElement>?
    ) {
        currentSubscribers.forEach {
            if(async) {
                receiveScope.launch {
                    publishToSubscriber(it, message, stacktrace)
                }
            } else {
                runBlocking {
                    publishToSubscriber(it, message, stacktrace)
                }
            }
        }
    }

    private suspend fun publishToSubscriber(it: ISubscriber, message: IAppMessage, stacktrace: List<StackTraceElement>?) {
        try {
            it.receive(message)
        } catch (e: Exception) {
            if (debug) {
                log.debug("[{}AppMessenger]: exception while publishing event: {} {}\n{}\nreceived from\n" +
                        "{}",
                    name, e::class.java.simpleName, e.message, e.stackTrace.printableStacktrace(1), stacktrace?.printableStacktrace())
            } else {
                throw e
            }
        }
    }

    fun deleteSubscriptions(subscriber: ISubscriber) {
        lock.write {
            subscribers.values.forEach {
                it.remove(subscriber)
            }
        }
    }
}
