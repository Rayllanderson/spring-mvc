package com.rayllanderson.services;

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

}
