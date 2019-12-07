package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger

fun main() {
    val messenger = AppMessenger("NoSubscriberExample")

    messenger.debug = true
    // will warn in log, due to missing subscribers
    messenger.publishSync(Message())
}