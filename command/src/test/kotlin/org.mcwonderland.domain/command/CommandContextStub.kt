package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

data class CommandContextStub(
    override val sender: User,
    override val label: String,
    override val args: List<String>
) : CommandContext