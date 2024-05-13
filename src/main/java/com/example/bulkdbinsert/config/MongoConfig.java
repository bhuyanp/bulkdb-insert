package com.example.bulkdbinsert.config;

import com.example.bulkdbinsert.documentdb.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.example.bulkdbinsert.documentdb"})
@Profile("mongo")
public class MongoConfig {

}