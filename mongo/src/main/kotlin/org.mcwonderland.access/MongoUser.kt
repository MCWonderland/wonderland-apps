package org.mcwonderland.access

import org.bson.codecs.pojo.annotations.BsonIgnore
import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.model.McProfile
import org.mcwonderland.domain.model.User


class MongoUser(
    override var id: String = "",
    var mcId: String = "",
    var mcUsername: String = "",
    var discordId: String = "",
    var discordUsername: String = "",
    override var isAdmin: Boolean = false
) : User() {

    override var mcProfile: McProfile
        @BsonIgnore
        get() = McProfile(mcId, mcUsername)
        set(value) {}

    override var discordProfile: DiscordProfile
        @BsonIgnore
        get() = DiscordProfile(discordId, discordUsername)
        set(value) {}
}


fun User.toMongoUser() = MongoUser(
    id = id,
    mcId = mcProfile.uuid,
    mcUsername = mcProfile.username,
    discordId = discordProfile.id,
    discordUsername = discordProfile.username,
    isAdmin = isAdmin
)