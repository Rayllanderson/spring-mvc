package com.rayllanderson.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pessoas")
public class PeopleController {
    
    @GetMapping("/cadastro")
    public String register() {
	return "register/people";
    }
}
