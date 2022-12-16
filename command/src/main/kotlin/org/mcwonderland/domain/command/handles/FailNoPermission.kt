package org.mcwonderland.domain.command.handles

import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.exceptions.PermissionDeniedException

interface FailNoPermission<Context : CommandContext> {
    fun failPermissionDenied(context: Context, e: PermissionDeniedException)
}