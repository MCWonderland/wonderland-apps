package org.mcwonderland.domain.exceptions

import org.mcwonderland.domain.model.User

class PermissionDeniedException : RuntimeException()
class MemberCantBeEmptyException : RuntimeException()
class UserNotFoundException(val ids: List<String>) : RuntimeException()
class UsersAlreadyInTeamException(val users: List<User>) : RuntimeException()
class UsersNotLinkedException(val users: List<User>) : RuntimeException()