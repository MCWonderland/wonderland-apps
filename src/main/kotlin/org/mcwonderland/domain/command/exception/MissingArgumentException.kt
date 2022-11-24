package org.mcwonderland.domain.command.exception

class MissingArgumentException(message: String) : RuntimeException("Missing argument: $message")
