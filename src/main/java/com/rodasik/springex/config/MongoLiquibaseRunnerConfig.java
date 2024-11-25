package com.rodasik.springex.config;

import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoLiquibaseRunnerConfig {
    public final String url =
            "mongodb://localhost:27017/test?socketTimeoutMS=5000&connectTimeoutMS=10000&serverSelectionTimeoutMS=10000";

    @Bean
    public MongoLiquibaseDatabase mongoLiquibaseDatabase() throws DatabaseException {
        return (MongoLiquibaseDatabase) DatabaseFactory
                .getInstance()
                .openDatabase(url, null,null,null,null);
    }

    @Bean
    public MongoLiquibaseRunner mongoLiquibaseRunner(final MongoLiquibaseDatabase database) {
        return new MongoLiquibaseRunner(database);
    }
}
