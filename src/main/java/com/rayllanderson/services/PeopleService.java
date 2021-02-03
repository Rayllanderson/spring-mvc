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

    public List<People> findAll() {
	return repository.findAll();
    }

    public Optional<People> findById(Long id) {
	return repository.findById(id);
    }

    @Transactional
    public boolean deleteById(Long id) {
	try {
	    fileService.deleteById(id);
	    repository.deleteById(id);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }
    
    public List<People> findByName(String name){
	return repository.findByNameContains(name);
    }
    
    public List<People> findByGender(Gender gender){
	return repository.findByGender(gender);
    }
}
