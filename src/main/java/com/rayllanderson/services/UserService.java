package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.User;
import com.rayllanderson.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User save(User people) {
	return repository.save(people);
    }

    public List<User> findAll() {
	return repository.findAll();
    }

    public boolean deleteById(String username) {
	try {
	    repository.deleteById(username);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<User> user = repository.findById(username);
	if (user.isEmpty()) {
	    throw new UsernameNotFoundException("Usuário não encontrado.");
	}
	return user.get();
    }
}
