package com.rayllanderson.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.Role;
import com.rayllanderson.entities.User;
import com.rayllanderson.entities.enums.RoleType;
import com.rayllanderson.services.UserService;

@Controller
@RequestMapping("**/usuarios")
public class UserController {

    @Autowired
    private UserService service;

    private final String MAIN_VIEW_NAME = "pages/user";

    @GetMapping()
    public ModelAndView listAll() {
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "users", service.findAll());
	addEmptyUser(mv);
	return mv;
    }

    @PostMapping
    public ModelAndView save(@Valid User user, @RequestParam List<String> roles, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
	    return catchErrors(bindingResult, user);
	}
	roles.forEach(x -> user.getRoles().add(new Role(RoleType.valueOf(x))));
	service.save(user);
	return listAll();
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
	Optional<User> object = service.findById(id);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME);
	if (object.isPresent()) {
	    User user = object.get();
	    mv.addObject("user", user);
	} else {
	    addEmptyUser(mv);
	}
	return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	service.deleteById(id);
	return listAll();
    }

    @GetMapping("/search")
    public ModelAndView findByName(@RequestParam String username) {
	boolean usernameIsEmpty = username.trim().isEmpty() || username == null;
	if (usernameIsEmpty) {
	    return listAll();
	}
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "users", service.serachByUsername(username));
	addEmptyUser(mv);
	return mv;
    }

    private void addEmptyUser(ModelAndView mv) {
	mv.addObject("user", new User());
    }

    private ModelAndView catchErrors(BindingResult bindingResult, User user) {
	var mv = listAll();
	mv.addObject("user", user);
	List<String> erros = new ArrayList<>();
	bindingResult.getAllErrors().forEach(x -> erros.add(x.getDefaultMessage()));
	mv.addObject("msg", erros);
	return mv;
    }

}
