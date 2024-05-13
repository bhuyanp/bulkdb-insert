package com.example.bulkdbinsert.config;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@Profile("mysql")
public class JpaConfig {

}
