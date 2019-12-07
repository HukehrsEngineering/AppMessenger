package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IAppMessage

fun main() {
    val messenger = AppMessenger<IAppMessage>("NoSubscriberExample")

    messenger.debug = true
    // will warn in log, due to missing subscribers
    messenger.publishSync(Message())
}