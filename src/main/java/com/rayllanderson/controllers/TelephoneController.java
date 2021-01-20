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
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
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

    @Autowired
    private PeopleController peopleController;

    private final String MAIN_VIEW_NAME = "pages/telephone";

    @GetMapping
    public ModelAndView toPeoplePage() {
	return peopleController.listAll();
    }

    @GetMapping("/{id}")
    public ModelAndView listAll(@PathVariable("id") Long id) {
	List<Telephone> phones = telephoneService.findPhonesByPeopleId(id);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "phones", phones);
	addPeople(id, mv);
	addEmptyPhone(mv);
	return mv;
    }

    @PostMapping
    public ModelAndView save(@Valid Telephone phone, Long peopleId) {
	boolean phoneIsInvalid = (phone != null && phone.getNumber().trim().isEmpty()) || phone.getNumber() == null || phone.getType() == null;
	if (phoneIsInvalid) {
	    return catchErrors(phone, peopleId);
	}
	phone.setPeople(new People(peopleId, null));
	telephoneService.save(phone);
	return listAll(peopleId);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
	Optional<Telephone> object = telephoneService.findById(id);
	if (object.isPresent()) {
	    var mv = new ModelAndView(MAIN_VIEW_NAME, "phone", object.get());
	    addPeople(object.get().getPeople().getId(), mv);
	    return mv;
	} else {
	    return toPeoplePage();
	}
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	Optional<Telephone> object = telephoneService.findById(id);
	if (object.isPresent()) {
	    Long peopleId = object.get().getPeople().getId();
	    telephoneService.deleteById(id);
	    return listAll(peopleId);
	}
	return toPeoplePage();
    }

    @PostMapping("/search")
    public ModelAndView findByName(Telephone phone, Long peopleId) {
	var mv = new ModelAndView(MAIN_VIEW_NAME, "phones", telephoneService.findByNumber(phone.getNumber(), peopleId));
	addPeople(peopleId, mv);
	addEmptyPhone(mv);
	return mv;
    }

    private void addEmptyPhone(ModelAndView mv) {
	mv.addObject("phone", new Telephone());
    }

    private void addPeople(Long id, ModelAndView mv) {
	mv.addObject("people", peopleService.findById(id).get());
    }
    
    
    private ModelAndView catchErrors(Telephone phone, Long peopleId) {
	var mv = listAll(peopleId);
	mv.addObject("phone", phone);
	mv.addObject("msg", "Número vazio ou tipo inválido");
	return mv;
    }
}
