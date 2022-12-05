package org.mcwonderland.domain.exceptions

import org.mcwonderland.domain.model.User

class AccountAlreadyLinkedException(val linkedId: String) : RuntimeException()
class MemberCantBeEmptyException : RuntimeException()
class MCAccountNotFoundException(val searchStr: String) : RuntimeException()
class MCAccountLinkedByOthersException(val ign: String) : RuntimeException()
class PermissionDeniedException : RuntimeException()
class UserNotFoundException(val ids: List<String>) : RuntimeException()
class UsersAlreadyInTeamException(val users: List<User>) : RuntimeException()
class UsersNotLinkedException(val users: List<User>) : RuntimeException()