package com.rayllanderson.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.Telephone;
import com.rayllanderson.services.TelephoneService;

@Controller
@RequestMapping("**/telefones")
public class TelephoneController extends PeopleInformationController{

    @Autowired
    private TelephoneService telephoneService;
    
    private final String MAIN_VIEW_NAME = "pages/infos";

    @PostMapping
    public ModelAndView save(@Valid Telephone phone, Long peopleId) {
	boolean phoneIsInvalid = (phone != null && phone.getNumber().trim().isEmpty()) || phone.getNumber() == null
		|| phone.getType() == null;
	if (phoneIsInvalid) {
	    return catchErrors(phone, peopleId);
	}
	phone.setPeople(new People(peopleId, null));
	telephoneService.save(phone);
	return listInfos(peopleId);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView setToEdit(@PathVariable("id") Long id) {
	Optional<Telephone> object = telephoneService.findById(id);
	if (object.isPresent()) {
	    var mv = new ModelAndView(MAIN_VIEW_NAME, "phone", object.get());
	    addPeople(object.get().getPeople().getId(), mv);
	    addEmptyAddress(mv);
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
	    return listInfos(peopleId);
	}
	return super.toPeoplePage();
    }

    @PostMapping("/search")
    public ModelAndView search(Telephone phone, Long peopleId) {
	var mv = new ModelAndView(MAIN_VIEW_NAME, "phones", telephoneService.findByNumber(phone.getNumber(), peopleId));
	addPeople(peopleId, mv);
	addEmptyPhone(mv);
	addEmptyAddress(mv);
	return mv;
    }
    
    private ModelAndView catchErrors(Telephone phone, Long peopleId) {
	var mv = listInfos(peopleId);
	mv.addObject("phone", phone);
	mv.addObject("msg", "Número vazio ou tipo inválido");
	return mv;
    }
}
