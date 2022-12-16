package org.mcwonderland.domain.command

class CommandProcessorImpl(
    private val commands: List<Command>,
) : CommandProcessor {

    override fun onCommand(context: CommandContext) {
        commands.find { it.label == context.label }?.execute(context)
    }

}