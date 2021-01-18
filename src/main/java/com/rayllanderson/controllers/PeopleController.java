package com.rayllanderson.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
import com.rayllanderson.services.PeopleService;

@Controller
@RequestMapping("**/pessoas")
public class PeopleController {

    @Autowired
    private PeopleService service;

    @GetMapping()
    public ModelAndView listAll() {
	ModelAndView mv = new ModelAndView("pages/people");
	mv.addObject("peoples", service.findAll());
	mv.addObject("people", new People());
	return mv;
    }

    @PostMapping()
    public ModelAndView save(People people) {
	service.save(people);
	return listAll();
    }

    @GetMapping("/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
	Optional<People> object = service.findById(id);
	ModelAndView mv = new ModelAndView("pages/people");
	if (object.isPresent()) {
	    mv.addObject("people", object.get());
	} else {
	    mv.addObject("people", new People());
	}
	return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	service.deleteById(id);
	return listAll();
    }

}
