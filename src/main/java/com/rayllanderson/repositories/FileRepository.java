package com.rayllanderson.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rayllanderson.entities.File;

public interface FileRepository extends JpaRepository<File, Long>{
    
}
