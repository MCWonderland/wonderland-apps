package org.mcwonderland.access

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.model.User

fun MongoDatabase.getUserCollection(): MongoCollection<User> = getCollection("user", User::class.java)
fun MongoDatabase.getTeamCollection(): MongoCollection<DBTeam> = getCollection("team", DBTeam::class.java)