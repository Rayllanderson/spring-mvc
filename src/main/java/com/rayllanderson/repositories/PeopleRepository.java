package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;

public interface PeopleRepository extends JpaRepository<People, Long>{
    
    List<People> findByNameContains(String name);
    
    @Query("from People as p where CAST(p.gender as string) like concat('%', :gender, '%')")
    List<People> findByGender(@Param("gender") Integer gender);
    
    @Query("from People as p where p.gender = :gender")
    List<People> findByGender(@Param("gender") Gender gender);
}
