package org.mcwonderland.access

import com.mongodb.client.MongoClient
import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.Defaults
import de.flapdoodle.embed.mongo.config.MongoCmdOptions
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.config.RuntimeConfig
import de.flapdoodle.embed.process.config.process.ProcessOutput
import de.flapdoodle.embed.process.io.Processors
import de.flapdoodle.embed.process.runtime.Network
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll


open class MongoDBTest {

    companion object {
        internal val dbName = "database"

        lateinit var mongoClient: MongoClient

        lateinit var mongodExecutable: MongodExecutable
        lateinit var mongoProcess: MongodProcess

        @JvmStatic
        @BeforeAll
        fun setupMongo() {
            val port = 27018
            val config: MongodConfig = MongodConfig.builder()
                .version(Version.Main.V4_0)
                .net(Net(port, Network.localhostIsIPv6()))
                .cmdOptions(
                    MongoCmdOptions.builder()
                        .useNoJournal(false)
                        .build()
                )
                .build()

            mongodExecutable = doGetExecutable(config)
            mongodExecutable.start()

            mongoClient = MongoClientFactory.createClient("mongodb://localhost:$port")
        }

        @JvmStatic
        @AfterAll
        fun stopMongo() {
            mongodExecutable.stop()
        }

        private fun doGetExecutable(config: MongodConfig): MongodExecutable {
            val runtimeConfig: RuntimeConfig = Defaults.runtimeConfigFor(Command.MongoD)
                .processOutput(
                    ProcessOutput.builder()
                        .output(Processors.silent())
                        .error(Processors.silent())
                        .commands(Processors.silent())
                        .build()
                )
                .build()
            return MongodStarter.getInstance(runtimeConfig).prepare(config)
        }

    }

    @AfterEach
    fun cleanUp() {
        mongoClient.getDatabase(dbName).drop()
    }


    protected fun getDB() = mongoClient.getDatabase(dbName)

}