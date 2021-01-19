package com.rayllanderson.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rayllanderson.entities.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long>{
    
}
