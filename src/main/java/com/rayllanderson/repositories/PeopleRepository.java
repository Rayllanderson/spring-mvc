package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
    
    List<People> findByNameContains(String name);
    
    @Query("SELECT p FROM People p JOIN FETCH p.telephones where p.id = :id")
    People findPeoplesWithPhones(@Param("id") Long id);
}
