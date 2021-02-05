package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;
import com.rayllanderson.repositories.PeopleRepository;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository repository;
    
    @Autowired
    private FileService fileService;

    @Transactional
    public People save(People people) {
	return repository.save(people);
    }

    public List<People> findAll(String username) {
	return repository.findAll(username);
    }

    public Optional<People> findById(Long id) {
	return repository.findById(id);
    }

    public boolean deleteById(Long peopleId, String username) {
	try {
	    fileService.deleteById(peopleId);
	    repository.deleteById(peopleId, username);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }
    
    public List<People> findByName(String name, String username){
	return repository.findByName(name, username);
    }
    
    public List<People> findByGender(Gender gender, String username){
	return repository.findByGender(gender, username);
    }
}
