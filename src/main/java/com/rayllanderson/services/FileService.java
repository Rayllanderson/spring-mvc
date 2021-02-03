package com.rayllanderson.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rayllanderson.entities.File;
import com.rayllanderson.repositories.FileRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository repository;

    @Transactional
    public File save(File file) {
	return repository.save(file);
    }

    public Optional<File> findById(Long peopleId) {
	return repository.findById(peopleId);
    }

    @Transactional
    public boolean deleteById(Long peopleId) {
	try {
	    repository.deleteById(peopleId);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }

    @Transactional
    public void save(MultipartFile file, Long peopleId) throws IOException {
	boolean hasFile = !file.isEmpty() || file.getSize() == 0;
	if (hasFile) {
	    repository.save(new File(file.getBytes(), peopleId));
	}
    }
}
