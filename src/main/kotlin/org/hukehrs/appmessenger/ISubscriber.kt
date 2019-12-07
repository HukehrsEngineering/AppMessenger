package org.hukehrs.appmessenger

/**
 * Use the init {} method to subscribe to AppMessenger instances
 *
 * When implementing a receiver remember to delete subscriptions on ALL messenger instances you subscribed to in
 * the close() method
 */
interface ISubscriber : AutoCloseable {
    suspend fun receive(message: IEventMessage)
}
