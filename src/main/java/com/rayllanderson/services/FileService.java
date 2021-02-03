package com.rayllanderson.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.File;
import com.rayllanderson.repositories.FileRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository repository;

    @Transactional
    public File save(File people) {
	return repository.save(people);
    }

    public Optional<File> findById(Long peopleId) {
	return repository.findById(peopleId);
    }

    public boolean deleteById(Long peopleId) {
	try {
	    repository.deleteById(peopleId);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }
}
