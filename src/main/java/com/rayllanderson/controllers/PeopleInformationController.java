package com.rayllanderson.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.Address;
import com.rayllanderson.entities.Telephone;
import com.rayllanderson.repositories.AddressRepository;
import com.rayllanderson.repositories.PeopleRepository;
import com.rayllanderson.repositories.TelephoneRepository;

@Controller
@RequestMapping("**/infos")
public class PeopleInformationController {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private AddressRepository addressRepository;

    private final String MAIN_VIEW_NAME = "pages/infos";

    @GetMapping("/{id}")
    public ModelAndView listInfos(@PathVariable("id") Long id) {
	List<Telephone> phones = telephoneRepository.findPhonesByPeopleId(id);
	List<Address> addresses = addressRepository.findAddressesByPeopleId(id);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "phones", phones);
	mv.addObject("addresses", addresses);
	addPeople(id, mv);
	addEmptyPhone(mv);
	addEmptyAddress(mv);
	return mv;
    }

    public void addEmptyPhone(ModelAndView mv) {
	mv.addObject("phone", new Telephone());
    }

    public void addEmptyAddress(ModelAndView mv) {
	mv.addObject("address", new Address());
    }

    public void addPeople(Long id, ModelAndView mv) {
	mv.addObject("people", peopleRepository.findById(id).get());
    }
}
