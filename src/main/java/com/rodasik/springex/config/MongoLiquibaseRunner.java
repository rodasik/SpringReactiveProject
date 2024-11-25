package com.rodasik.springex.config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.CommandLineRunner;

public class MongoLiquibaseRunner implements CommandLineRunner {
    public final MongoLiquibaseDatabase database;

    public MongoLiquibaseRunner(MongoLiquibaseDatabase database) {
        this.database = database;
    }

    public void run(String... args) throws Exception {
        Liquibase liquibase =
                new Liquibase("db/changelog/mongo-changelog-000.json",
                        new ClassLoaderResourceAccessor(),
                        database);
        liquibase.update(new Contexts());
    }
}
