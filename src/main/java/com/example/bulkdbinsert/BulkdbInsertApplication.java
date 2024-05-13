package com.example.bulkdbinsert;

import com.example.bulkdbinsert.db.model.User;
import com.example.bulkdbinsert.db.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BulkdbInsertApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulkdbInsertApplication.class, args);
    }



}
