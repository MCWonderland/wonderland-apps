package org.mcwonderland.discord

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.mongodb.client.MongoClient
import org.mcwonderland.access.MongoClientFactory
import org.mcwonderland.access.RegistrationRepositoryImpl
import org.mcwonderland.access.TeamRepositoryImpl
import org.mcwonderland.access.UserRepositoryImpl
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.repository.RegistrationRepository
import org.mcwonderland.domain.repository.TeamRepository
import org.mcwonderland.domain.repository.UserRepository

class DatabaseModule : AbstractModule() {

    @Provides
    fun mongoClient(config: Config): MongoClient {
        return MongoClientFactory.createClient(config.mongoConnection)
    }


    @Provides
    fun userRepository(mongoClient: MongoClient, config: Config): UserRepository {
        return UserRepositoryImpl(mongoClient, config)
    }

    @Provides
    fun teamRepository(mongoClient: MongoClient, config: Config): TeamRepository {
        return TeamRepositoryImpl(mongoClient, config)
    }

    @Provides
    fun registrationRepository(mongoClient: MongoClient, config: Config): RegistrationRepository {
        return RegistrationRepositoryImpl(mongoClient, config)
    }

}