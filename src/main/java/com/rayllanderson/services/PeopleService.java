package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayllanderson.entities.People;
import com.rayllanderson.repositories.PeopleRepository;

@Service
public class PeopleService {
    
    @Autowired
    private PeopleRepository repository;
    
    @Transactional
    public People save (People people) {
	return repository.save(people);
    }

    public List<People> findAll() {
	return repository.findAll();
    }
    
    public Optional<People> findById(Long id) {
	return repository.findById(id);
    }

}