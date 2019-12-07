package org.hukehrs.appmessenger.example

import org.hukehrs.appmessenger.IAppMessage

class GenericMessage<T: Any>(val data: T): IAppMessage
