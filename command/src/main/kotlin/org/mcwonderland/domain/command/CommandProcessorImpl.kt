package org.mcwonderland.domain.command

class CommandProcessorImpl(
    private val commands: List<Command<out CommandContext>>,
) : CommandProcessor {

    override fun onCommand(context: CommandContext) {
        (commands.find { it.label == context.label } as Command<CommandContext>).execute(context)
    }

}