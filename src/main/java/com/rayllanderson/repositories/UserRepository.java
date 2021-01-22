package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rayllanderson.entities.User;

public interface UserRepository extends JpaRepository<User, String>{
    
    List<User> findByUsernameContains(String username);
}
