package org.hukehrs.appmessenger

/**
 * If your project is very small this messenger will do the job just fine, especially if you have no DI.
 *
 * Most of the time you may want to inject this messenger to your DI framework (or create your own)
 *
 * If you have many different worker contexts with tasks in parallel you might want to create additional Messengers
 * This way each messenger should represent one context
 */
object GlobalAppMessenger: AppMessenger<IAppMessage>("Global")
