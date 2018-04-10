package com.sivalabs.techbuzz.repository;

import com.sivalabs.techbuzz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}

