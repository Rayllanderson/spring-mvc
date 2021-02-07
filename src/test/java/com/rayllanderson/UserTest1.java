package com.rayllanderson;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.rayllanderson.entities.User;
import com.rayllanderson.exceptions.UsernameExistsException;
import com.rayllanderson.repositories.UserRepository;
import com.rayllanderson.services.UserService;

@SpringBootTest
class UserTest1 {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private UserService service;
    
    @Test
    public void usernameValidTest() {
	User user = new User("     ");
	User user2 = new User("");
	User user4 = new User("Ray");
	assertEquals(user.usernameIsValid(), false);
	assertEquals(user2.usernameIsValid(), false);
	assertEquals(user4.usernameIsValid(), true);
    }
    
    @Test
    public void existisUsername() {
	assertEquals(repository.existsByUsername("ray"), true);
	assertEquals(repository.existsByUsername("joao"), false);
    }
    
    @Test
    public void login() {
	Optional<User> userFromDataBase = repository.findByUsername("ray");
	User user = userFromDataBase.get();
	System.out.println(user);
    }
    
    @Test
    public void register() {
	System.out.println(service.register(new User(null, "joao1", "123")));
    }
    
    @Test
    public void updateUsername() {
	System.out.println(service.updateUsername(new User(null, "joao1", "123")));
    }
    
}
