package org.mcwonderland.domain.config

import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

class MessagesStub : Messages {

    override fun membersCantBeEmpty(): String = "請輸入隊伍成員"
    override fun membersCouldNotFound(ids: List<String>): String =
        "無法找到以下成員的資料，他們或許還沒綁定: ${
            ids.map { userTag(it) }
                .joinToString(", ")
        }"

    override fun membersAlreadyInTeam(ids: List<User>): String =
        "以下成員已經在隊伍當中了: ${
            ids.map { userTag(it.discordId) }.joinToString(", ")
        }"

    override fun accountAlreadyLinked(id: String): String = "你的帳號已經連結過了"
    override fun mcAccountWithIgnNotFound(s: String): String = "找不到這個帳號"
    override fun targetAccountAlreadyLink(s: String): String = "目標帳號已經連結過了"
    override fun invalidArg(argName: String): String = "缺少或是無效的參數: $argName"
    override fun teamCreated(team: Team): String =
        "隊伍已經建立，成員: ${
            team.members.map { userTag(it.discordId) }
                .joinToString(", ")
        }"

    private fun userTag(id: String): String = "<@${id}>"
    override fun teamList(teams: List<Team>): String = teams.mapIndexed { index, team ->
        "隊伍 ${index}, 成員: ${team.members.map { userTag(it.discordId) }.joinToString(", ")}"
    }.joinToString("\n")

    override fun noPermission(): String = "你沒有權限執行這個操作"
    override fun userNotFound(targetId: String): String = "找不到使用者的數據: ${userTag(targetId)}"

    override fun userNotInTeam(target: User): String = "使用者 ${userTag(target.discordId)} 不在任何隊伍當中"

    override fun userRemovedFromTeam(expectTeam: Team): String = "使用者已經從隊伍中移除"
    override fun membersNotLinked(listOf: List<User>): String {
        return "以下成員尚未連結帳號: ${
            listOf.map { userTag(it.discordId) }
                .joinToString(", ")
        }"
    }

    override fun requireLinkedAccount(): String {
        return "你的帳號尚未連結"
    }

    override fun linked(foundedUser: User): String {
        return "已經連結帳號: ${foundedUser.mcId}"
    }

    override fun registered(): String {
        return "已經註冊"
    }

    override fun unRegistered(): String {
        return "已經取消註冊"
    }

    override fun listRegistrations(users: Collection<User>): String {
        return "已經註冊的玩家: ${
            users.map { it.mcId }
                .joinToString(", ")
        }"
    }

    override fun unHandledCommandError(exceptionClassName: String): String {
        return "未處理的錯誤: $exceptionClassName"
    }

    override fun addedUserToTeam(result: AddToTeamResult): String {
        return "已將使用者加入隊伍"
    }

    override fun userAlreadyInTeam(user: User): String {
        return "使用者已經在隊伍當中"
    }

    override fun teamNotFound(teamId: String): String {
        return "找不到隊伍: $teamId"
    }

    override fun teamDeleted(teamId: String): String {
        return "已經刪除隊伍: $teamId"
    }

    override fun registrationsCleared(): String {
        return "已經清除所有註冊"
    }

    override fun nowAcceptRegistrations(): String {
        return "現在開放註冊"
    }

    override fun noLongerAcceptRegistrations(): String {
        return "現在不開放註冊"
    }

    override fun notAllowRegistrations(): String {
        return "目前暫不開放報名"
    }

}