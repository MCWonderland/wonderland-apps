package org.mcwonderland.domain

interface MessageSender {
    fun sendMessage(userId: String, message: String)
}