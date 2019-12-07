package org.hukehrs.appmessenger

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

/**
 * This scope is single threaded, since it only task is to start coroutines in the ReceiveScope
 */
class SendScope(messengerName: String) : CoroutineScope {
    @Suppress("EXPERIMENTAL_API_USAGE")
    override val coroutineContext: CoroutineContext = newSingleThreadContext("${messengerName}AppMessengerSendScope")
}
