package com.rayllanderson.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
import com.rayllanderson.services.PeopleService;

@Controller
@RequestMapping("**/pessoas")
public class PeopleController {

    @Autowired
    private PeopleService service;

    private final String MAIN_VIEW_NAME = "pages/people";

    @GetMapping()
    public ModelAndView listAll() {
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "peoples", service.findAll());
	addEmptyPeople(mv);
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
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME);
	if (object.isPresent()) {
	    mv.addObject("people", object.get());
	} else {
	    addEmptyPeople(mv);
	}
	return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	service.deleteById(id);
	return listAll();
    }

    @GetMapping("/search")
    public ModelAndView findByName(@RequestParam String name) {
	boolean nameIsEmpty = name.trim().isEmpty() || name == null;
	if (nameIsEmpty) {
	    return listAll();
	}
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "peoples", service.findByName(name));
	addEmptyPeople(mv);
	return mv;
    }

    private void addEmptyPeople(ModelAndView mv) {
	mv.addObject("people", new People());
    }

}
