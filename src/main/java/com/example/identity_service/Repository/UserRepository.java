package com.example.identity_service.Repository;

import com.example.identity_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByName(String userName);
    Optional<User> findByName(String userName);
}
