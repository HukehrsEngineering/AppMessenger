package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.ISubscriber

data class MessengerSubscriberTuple(val appMessenger: AppMessenger, val subscriber1: ISubscriber, val subscriber2: ISubscriber, val subscriber3: ISubscriber)