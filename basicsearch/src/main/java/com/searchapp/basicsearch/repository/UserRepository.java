package com.searchapp.basicsearch.repository;

import com.searchapp.basicsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
