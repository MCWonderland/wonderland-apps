package org.mcwonderland.domain.command

import java.util.*

private typealias Args = List<String>

fun Args.getUuid(index: Int): UUID? {
    return try {
        return this.getOrNull(index)?.let { UUID.fromString(it) }
    } catch (e: IllegalArgumentException) {
        null
    }
}