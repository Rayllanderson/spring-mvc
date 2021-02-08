package com.rayllanderson.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;

public interface PeopleRepository extends JpaRepository<People, Long>{
    
    @Query("from People as p where p.user.id = :id")
    List<People> findAll(@Param("id") Long userId);
    
    @Query("from People as p where p.name like concat('%', :name, '%') and p.user.id = :id")
    List<People> findByName(@Param("name") String name, @Param("id") Long userId);
    
    @Query("from People as p where p.id = :id and p.user.id = :userId")
    Optional <People> findById(@Param("id") Long id, @Param("userId") Long userId);
    
    @Query("from People as p where p.gender = :gender and p.user.id = :id")
    List<People> findByGender(@Param("gender") Gender gender, @Param("id") Long userId);
    
    @Transactional
    @Modifying
    @Query("delete from People as p where p.id = :peopleId and p.user.id = :id ")
    void deleteById(@Param("peopleId") Long peopleId, @Param("id") Long userId);
}
