package com.makemark.config

import com.makemark.config.property.MongoProperty
import com.makemark.repository.UserRepository
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.bson.UuidRepresentation
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [UserRepository::class])
class MongoConfig(
    property: MongoProperty
) : AbstractReactiveMongoConfiguration() {

    val connect = with(property) {
        ConnectionString("mongodb://$userName:$password@$host:$port/$database?authSource=admin")
    }

    override fun getDatabaseName(): String = "mmark"

    override fun reactiveMongoTemplate(
        databaseFactory: ReactiveMongoDatabaseFactory,
        mongoConverter: MappingMongoConverter
    ): ReactiveMongoTemplate = ReactiveMongoTemplate(databaseFactory, mongoConverter)

    override fun reactiveMongoClient(): MongoClient =
        MongoClients.create(
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(connect)
                .build()
        )

    override fun autoIndexCreation(): Boolean = true
}