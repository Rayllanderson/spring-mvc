package com.rayllanderson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayllanderson.entities.People;
import com.rayllanderson.services.PeopleService;

@Controller
@RequestMapping("/pessoas")
public class PeopleController {
    
    @Autowired
    private PeopleService service;
    
    @GetMapping()
    public String start() {
	return "register/people";
    }
    
    @PostMapping()
    public String register(People people) {
	System.out.println(people.getName());
	service.save(people);
	return "register/people";
    }
}
