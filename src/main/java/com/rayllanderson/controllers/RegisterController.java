package com.rayllanderson.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rayllanderson.entities.User;
import com.rayllanderson.exceptions.UsernameExistsException;
import com.rayllanderson.services.UserService;

@Controller
@RequestMapping("**/register")
public class RegisterController {

    @Autowired
    private UserService service;

    @Autowired
    private HttpServletResponse response;

    @PostMapping
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String password2)
	    throws IOException {
	if (usernameIsNotValid(username)) return setResponseBody("Username não pode ser vazio", HttpServletResponse.SC_BAD_REQUEST);
	if (passwordIsNotValid(password, password2)) return setResponseBody("As senhas não correspondem.", HttpServletResponse.SC_BAD_REQUEST);
	try {
	    User user = new User(username);
	    user.setPassword(password);
	    service.register(user);
	    return setResponseBody("Cadastrado com sucesso! Faça Login para continuar", HttpServletResponse.SC_CREATED);
	} catch (UsernameExistsException e) {
	    return setResponseBody("Já existe um usuário utilizando esse username. Por favor, tente outro", HttpServletResponse.SC_CONFLICT);
	}
    }

    private boolean passwordIsNotValid(String pass1, String pass2) {
	return !(pass1.equals(pass2));
    }

    private boolean usernameIsNotValid(String username) {
	return username == null || username.isBlank() || username.isEmpty();
    }

    private String setResponseBody(String message, int code) throws IOException {
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	response.setStatus(code);
	return message;
    }
}
