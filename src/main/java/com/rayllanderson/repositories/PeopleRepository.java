package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rayllanderson.entities.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
    
    List<People> findByNameContains(String name);
}
