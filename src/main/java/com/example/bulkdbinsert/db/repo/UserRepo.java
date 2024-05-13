package com.example.bulkdbinsert.db.repo;

import com.example.bulkdbinsert.db.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mysql")
public interface UserRepo extends JpaRepository<User, Long> {
}
