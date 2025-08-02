package com.fitnesstracker.fitnesssessionauth.repository;



import com.fitnesstracker.fitnesssessionauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
