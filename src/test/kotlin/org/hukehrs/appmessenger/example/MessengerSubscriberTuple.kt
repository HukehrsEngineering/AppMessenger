package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.AppMessenger
import org.hukehrs.appmessenger.IAppMessage
import org.hukehrs.appmessenger.ISubscriber

data class MessengerSubscriberTuple(val appMessenger: AppMessenger<IAppMessage>, val subscriber1: ISubscriber, val subscriber2: ISubscriber, val subscriber3: ISubscriber)