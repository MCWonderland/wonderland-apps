package org.mcwonderland.domain.command.handles

import org.mcwonderland.domain.command.CommandContext

interface FailWithUsage<Context : CommandContext> {
    fun failWithUsage(context: Context, usage: String)
}