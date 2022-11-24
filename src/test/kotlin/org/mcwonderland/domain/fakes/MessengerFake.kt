package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.Messenger

class MessengerFake : Messenger {

    val messages: MutableList<String> = mutableListOf()
    var lastMessage: String = ""
        private set

    override fun sendMessage(message: String) {
        messages.add(message)
        lastMessage = message
    }

}