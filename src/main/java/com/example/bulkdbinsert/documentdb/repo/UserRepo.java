package com.example.bulkdbinsert.documentdb.repo;

import com.example.bulkdbinsert.documentdb.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongo")
public interface UserRepo extends MongoRepository<User, String> {
}
