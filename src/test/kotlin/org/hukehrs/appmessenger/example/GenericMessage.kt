package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.IEventMessage

class GenericMessage<T: Any>(val data: T): IEventMessage
