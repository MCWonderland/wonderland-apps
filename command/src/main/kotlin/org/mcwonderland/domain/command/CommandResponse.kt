package org.mcwonderland.domain.command

class CommandResponse(
    val status: CommandStatus,
    val messages: List<String>
) {
    val firstMessage = messages.firstOrNull() ?: ""
}

enum class CommandStatus {
    SUCCESS,
    MISSING_ARGUMENTS,
    FAILURE
}