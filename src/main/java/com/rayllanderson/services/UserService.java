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
import com.rayllanderson.util.SessionUtil;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    public User save(User user) throws UsernameExistsException {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	return repository.save(user);
    }

    @Transactional
    public User register(User user) throws UsernameExistsException {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	user.checkIfUsernameExists(repository);
	user.getRoles().add(new Role(RoleType.ROLE_DEFAULT));
	return repository.save(user);
    }

    public User updateUsername(User user) throws UsernameNotFoundException, UsernameExistsException {
	if (user.usernameIsValid()) {
	    Long userId = SessionUtil.getUserId(request);
	    Optional<User> userFromDatabase = repository.findById(userId);
	    userFromDatabase.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	    String oldUsername = userFromDatabase.get().getUsername();
	    boolean hasChangedUsername = !(oldUsername.equals(user.getUsername()));
	    if (hasChangedUsername) {
		user.checkIfUsernameExists(repository);
		user.setId(userId);
		user.getRoles().add(new Role(RoleType.ROLE_DEFAULT));
		user.setPassword(userFromDatabase.get().getPassword());
		return repository.save(user);
	    }
	    return user;
	}
	throw new IllegalArgumentException("Invalid Username");
    }

    public List<User> findAll() {
	return repository.findAll();
    }

    public Optional<User> findById(Long id) {
	return repository.findById(id);
    }

    public List<User> serachByUsername(String username) {
	return repository.findByUsernameContains(username);
    }

    public boolean deleteById(Long id) {
	try {
	    repository.deleteById(id);
	    return true;
	} catch (IllegalArgumentException | EmptyResultDataAccessException e) {
	    return false;
	}
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<User> userFromDataBase = repository.findByUsername(username);
	userFromDataBase.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
	User user = userFromDataBase.get();
	request.getSession().setAttribute("userId", user.getId());
	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
		true, true, true, user.getAuthorities());
    }
}

