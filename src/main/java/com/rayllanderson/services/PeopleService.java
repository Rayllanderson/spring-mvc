package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;
import com.rayllanderson.repositories.AddressRepository;
import com.rayllanderson.repositories.PeopleRepository;
import com.rayllanderson.repositories.TelephoneRepository;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository repository;
    
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private TelephoneService telephoneService;
    
    @Autowired
    private TelephoneRepository telephoneRepository;
    
    @Autowired
    private FileService fileService;

    @Transactional
    public People save(People people) {
	return repository.save(people);
    }

    public List<People> findAll(Long userId) {
	return repository.findAll(userId);
    }

    public Optional<People> findById(Long id) {
	return repository.findById(id);
    }

    public boolean deleteById(Long peopleId, Long userId) {
	try {
	    addressRepository.addressesIdByPeopleId(peopleId).forEach(x -> addressService.deleteById(x.getId()));
	    telephoneRepository.phonesIdByPeopleId(peopleId).forEach(x -> telephoneService.deleteById(x.getId()));
	    repository.deleteById(peopleId, userId);
	    fileService.deleteById(peopleId);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }
    
    public List<People> findByName(String name, Long userId){
	return repository.findByName(name, userId);
    }
    
    public List<People> findByGender(Gender gender, Long userId){
	return repository.findByGender(gender, userId);
    }
}
