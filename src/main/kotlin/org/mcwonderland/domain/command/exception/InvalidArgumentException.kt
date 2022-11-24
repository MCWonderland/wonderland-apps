package org.mcwonderland.domain.command.exception

class InvalidArgumentException(message: String) : RuntimeException("Invalid argument: $message") {

}
