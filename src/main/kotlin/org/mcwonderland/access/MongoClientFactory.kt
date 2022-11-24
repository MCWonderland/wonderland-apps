package org.mcwonderland.access

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

object MongoClientFactory {

    fun createClient(connectionString: String): MongoClient {
        return MongoClients.create(
            MongoClientSettings.builder()
                .codecRegistry(
                    CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
                    )
                )
                .applyConnectionString(ConnectionString(connectionString))
                .build()
        )
    }
}