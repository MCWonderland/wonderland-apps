package org.mcwonderland.web

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
data class Config(
    @ConfigProperty(name = "quarkus.mongodb.db-name")
    val mongoDbName: String
)