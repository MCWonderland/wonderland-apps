package org.mcwonderland.discord

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ChannelCacheTest {

    @Test
    fun shouldReturnNullIfNoChannelCached() {
        val cache = ChannelCache()
        assertNull(cache.getLastChannel())
    }

    @Test
    fun shouldCacheChannel() {
        val cache = ChannelCache()
        val channel = "channel_id"

        cache.cache(channel)

        assertEquals(channel, cache.getLastChannel())
    }

}