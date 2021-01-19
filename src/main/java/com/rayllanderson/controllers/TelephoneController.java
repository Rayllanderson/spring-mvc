package com.rayllanderson.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.Telephone;
import com.rayllanderson.services.PeopleService;
import com.rayllanderson.services.TelephoneService;

@Controller
@RequestMapping("**/telefones")
public class TelephoneController {

    @Autowired
    private PeopleService peopleService;
    
    @Autowired
    private TelephoneService telephoneService;

    private final String MAIN_VIEW_NAME = "pages/telephone";

    @GetMapping("/{id}")
    public ModelAndView listAll(@PathVariable("id") Long id) {
	List<Telephone> phones = telephoneService.findPhonesByPeopleId(id);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME);
	mv.addObject("phones", phones);
	addEmptyPhone(mv);
	return mv;
    }

   /* @PostMapping()
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
*/
    private void addEmptyPhone(ModelAndView mv) {
	mv.addObject("phone", new Telephone());
    }

}
