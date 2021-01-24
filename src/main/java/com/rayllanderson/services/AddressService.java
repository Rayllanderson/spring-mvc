package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.Address;
import com.rayllanderson.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Transactional
    public Address save(Address people) {
	return repository.save(people);
    }

    public Optional<Address> findById(Long id) {
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
    
    public List<Address> findAddressesByPeopleId(Long peopleId){
	return repository.findAddressesByPeopleId(peopleId);
    }
}
