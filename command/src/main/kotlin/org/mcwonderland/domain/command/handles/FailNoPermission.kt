package org.mcwonderland.domain.command.handles

import org.mcwonderland.domain.exceptions.PermissionDeniedException

interface FailNoPermission {
    fun failPermissionDenied(e: PermissionDeniedException)
}