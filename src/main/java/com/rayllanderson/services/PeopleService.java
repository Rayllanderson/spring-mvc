package com.rayllanderson.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayllanderson.entities.People;
import com.rayllanderson.repositories.PeopleRepository;

@Service
public class PeopleService {
    
    @Autowired
    private PeopleRepository repository;
    
    public People save (People people) {
	return repository.save(people);
    }

}
