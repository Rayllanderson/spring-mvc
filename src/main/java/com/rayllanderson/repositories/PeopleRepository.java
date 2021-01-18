package com.rayllanderson.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rayllanderson.entities.People;

public interface PeopleRepository extends JpaRepository<People, Long>{

}
