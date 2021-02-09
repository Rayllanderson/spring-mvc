package com.rayllanderson.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

    @RequestMapping
    public String start() {
	return "index.html";
    }
    
    @RequestMapping("/login")
    public String login() {
	return "/login";
    }
}
