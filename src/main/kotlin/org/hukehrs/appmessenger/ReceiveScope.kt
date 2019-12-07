package org.hukehrs.appmessenger

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlin.coroutines.CoroutineContext

/**
 * This scope has multiple threads since the actual message receivers do their work in this scope
 */
class ReceiveScope(messengerName: String) : CoroutineScope {
    @Suppress("EXPERIMENTAL_API_USAGE")
    override val coroutineContext: CoroutineContext = newFixedThreadPoolContext(4, "${messengerName}AppMessengerReceiveScope")
}
