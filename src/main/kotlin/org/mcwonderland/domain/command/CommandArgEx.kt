package org.mcwonderland.domain.command

import org.mcwonderland.domain.command.exception.InvalidArgumentException
import org.mcwonderland.domain.command.exception.MissingArgumentException
import java.util.UUID

private typealias Args = List<String>

fun Args.getUuid(index: Int): UUID {
    val uuid = this.getOrNull(index) ?: throw MissingArgumentException("UUID")

    return try {
        UUID.fromString(uuid)
    } catch (e: IllegalArgumentException) {
        throw InvalidArgumentException("UUID")
    }
}