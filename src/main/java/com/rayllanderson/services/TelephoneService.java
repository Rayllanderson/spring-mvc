package com.rayllanderson.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.Telephone;
import com.rayllanderson.repositories.TelephoneRepository;

@Service
public class TelephoneService {

    @Autowired
    private TelephoneRepository repository;

    @Transactional
    public Telephone save(Telephone people) {
	return repository.save(people);
    }

    //@Transactional(r )
    public Optional<Telephone> findById(Long id) {
	return repository.findById(id);
    }

    public boolean deleteById(Long id) {
	try {
	    repository.deleteById(id);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }
}
