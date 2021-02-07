package com.rayllanderson.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    List<User> findByUsernameContains(String username);
    
    @Query("select count(u)>0 from User u where u.username = :username")
    boolean existsByUsername(@Param("username") String username);
    
    
    @Query("from User as u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
