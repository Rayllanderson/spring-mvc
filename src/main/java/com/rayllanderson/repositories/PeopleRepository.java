package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;

public interface PeopleRepository extends JpaRepository<People, Long>{
    
    @Query("from People as p where p.user.username = :username")
    List<People> findAll(@Param("username") String username);
    
    @Query("from People as p where p.name like concat('%', :name, '%') and p.user.username = :username")
    List<People> findByName(@Param("name") String name, @Param("username") String username);
    
    @Query("from People as p where p.gender = :gender and p.user.username = :username")
    List<People> findByGender(@Param("gender") Gender gender, @Param("username") String username);
    
    @Transactional
    @Modifying
    @Query("delete from People as p where p.id = :peopleId and p.user.username = :username")
    void deleteById(@Param("peopleId") Long peopleId, @Param("username") String username);
}
