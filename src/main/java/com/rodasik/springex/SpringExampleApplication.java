package com.rodasik.springex;

import com.rodasik.springex.dal.repositories.TemplateReactiveMongoRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(repositoryBaseClass = TemplateReactiveMongoRepositoryImpl.class)
public class SpringExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringExampleApplication.class, args);
	}

}
