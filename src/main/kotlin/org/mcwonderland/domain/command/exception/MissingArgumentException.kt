package org.mcwonderland.domain.command.exception

class MissingArgumentException(override val message: String) : RuntimeException(message)
