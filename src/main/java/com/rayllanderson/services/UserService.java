package com.rayllanderson.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.Role;
import com.rayllanderson.entities.User;
import com.rayllanderson.entities.enums.RoleType;
import com.rayllanderson.exceptions.UsernameExistsException;
import com.rayllanderson.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HttpServletRequest request;

    @Transactional
    public User save(User user) throws UsernameExistsException{
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	return repository.save(user);
    }
    
    @Transactional
    public User register(User user) throws UsernameExistsException{
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	checkIfUsernameExists(user.getUsername());
	user.getRoles().add(new Role(RoleType.ROLE_DEFAULT));
	return repository.save(user);
    }
    
//    public User update(User user) {
//	return repository.update();
//    }
    
    public List<User> findAll() {
	return repository.findAll();
    }

    public Optional<User> findById(String username) {
	return repository.findById(username);
    }

    public List<User> serachByUsername(String username) {
	return repository.findByUsernameContains(username);
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
	Optional<User> userFromDataBase = repository.findById(username);
	request.getSession().setAttribute("username", username);
	if (userFromDataBase.isEmpty()) {
	    throw new UsernameNotFoundException("Usuário não encontrado.");
	}
	User user = userFromDataBase.get();
	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
		true, true, true, user.getAuthorities());
    }
    
    private void checkIfUsernameExists(String username) throws UsernameExistsException {
	if(repository.existsById(username)) {
	    throw new UsernameExistsException("There's already an User using the username" + username);
	}
    }
}
