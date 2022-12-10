package org.mcwonderland.domain.exceptions

import org.mcwonderland.domain.model.User

class AccountAlreadyLinkedException(val linkedId: String) : RuntimeException()
class MemberCantBeEmptyException : RuntimeException()
class MCAccountNotFoundException(val searchStr: String) : RuntimeException()
class MCAccountLinkedByOthersException(val ign: String) : RuntimeException()
class PermissionDeniedException : RuntimeException()
class RequireLinkedAccountException : RuntimeException()
class TeamNotFoundException(val teamId: String) : RuntimeException()
class UserNotFoundException(val id: String) : RuntimeException()
class UserAlreadyInTeamException(val user: User) : RuntimeException()
class UserNotInTeamException(val user: User) : RuntimeException()
class UsersNotFoundException(val ids: List<String>) : RuntimeException()
class UsersAlreadyInTeamException(val users: List<User>) : RuntimeException()
class UsersNotLinkedException(val users: List<User>) : RuntimeException()