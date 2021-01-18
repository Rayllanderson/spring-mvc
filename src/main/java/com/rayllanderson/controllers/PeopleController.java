package com.rayllanderson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
import com.rayllanderson.services.PeopleService;

@Controller
@RequestMapping("/pessoas")
public class PeopleController {
    
    @Autowired
    private PeopleService service;
    
    @GetMapping()
    public ModelAndView listAll() {
	ModelAndView mv = new ModelAndView("pages/people");
	mv.addObject("peoples", service.findAll());
	return mv;
    }
    
    @PostMapping()
    public String save(People people) {
	service.save(people);
	return "redirect:/pessoas";
    }
}
