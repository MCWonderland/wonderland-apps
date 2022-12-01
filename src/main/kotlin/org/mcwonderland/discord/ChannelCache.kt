package org.mcwonderland.discord

class ChannelCache {

    private var lastChannelId: String? = null

    fun cache(channel: String) {
        lastChannelId = channel
    }

    fun getLastChannel(): String? {
        return lastChannelId
    }

}