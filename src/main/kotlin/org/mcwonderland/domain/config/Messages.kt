package org.mcwonderland.domain.config

class Messages {

    fun membersCantBeEmpty(): String {
        return "Members can't be empty"
    }

    fun membersCouldNotFound(ids: List<String>): String {
        return "Members could not be found: ${ids.joinToString { ", " }}"
    }

    fun membersAlreadyInTeam(ids: List<String>): String {
        return "Members already in team: ${ids.joinToString { ", " }}"
    }

}