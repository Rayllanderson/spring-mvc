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

import com.rayllanderson.entities.Address;
import com.rayllanderson.entities.People;
import com.rayllanderson.services.AddressService;

@Controller
@RequestMapping("**/enderecos")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private PeopleController peopleController;

    @Autowired
    private PeopleInformationController infosController;

    private final String MAIN_VIEW_NAME = "pages/infos";

    @GetMapping
    public ModelAndView toPeoplePage() {
	return peopleController.listAll();
    }

    @PostMapping
    public ModelAndView save(@Valid Address address, Long peopleId, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
	    return catchErrors(address, peopleId, bindingResult);
	}
	address.setPeople(new People(peopleId, null));
	addressService.save(address);
	return listInfos(peopleId);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView setToEdit(@PathVariable("id") Long id) {
	Optional<Address> object = addressService.findById(id);
	if (object.isPresent()) {
	    var mv = new ModelAndView(MAIN_VIEW_NAME, "address", object.get());
	    addPeople(object.get().getPeople().getId(), mv);
	    infosController.addEmptyPhone(mv);
	    return mv;
	} else {
	    return toPeoplePage();
	}
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	Optional<Address> object = addressService.findById(id);
	if (object.isPresent()) {
	    Long peopleId = object.get().getPeople().getId();
	    addressService.deleteById(id);
	    return listInfos(peopleId);
	}
	return toPeoplePage();
    }

    private void addPeople(Long id, ModelAndView mv) {
	infosController.addPeople(id, mv);
    }

    private ModelAndView listInfos(Long id) {
	return infosController.listInfos(id);
    }

    private ModelAndView catchErrors(Address address, Long peopleId, BindingResult bindingResult) {
	var mv = listInfos(peopleId);
	mv.addObject("address", address);
	List<String> erros = new ArrayList<>();
	bindingResult.getAllErrors().forEach(x -> erros.add(x.getDefaultMessage()));
	mv.addObject("msg", erros);
	return mv;
    }
}
