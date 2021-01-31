package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rayllanderson.entities.Profession;

public interface ProfessionRepository extends JpaRepository<Profession, Long>{
    
    @Query("select NEW Profession(p.id, p.name) from Profession p")
    public List<Profession> findAllWithoutPeoples();
}
