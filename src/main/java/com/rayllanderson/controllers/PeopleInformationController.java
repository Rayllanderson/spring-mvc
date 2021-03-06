package com.rayllanderson.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.Address;
import com.rayllanderson.entities.Telephone;
import com.rayllanderson.repositories.AddressRepository;
import com.rayllanderson.repositories.FileRepository;
import com.rayllanderson.repositories.PeopleRepository;
import com.rayllanderson.repositories.TelephoneRepository;
import com.rayllanderson.util.SessionUtil;

@Controller
@RequestMapping("**/infos")
public class PeopleInformationController {

    @Autowired
    private PeopleRepository peopleRepository;
    
    @Autowired
    private PeopleController peopleController;

    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    HttpServletRequest request;

    protected Long peopleId = null;

    private final String MAIN_VIEW_NAME = "pages/infos";

    @GetMapping("/{id}")
    public ModelAndView listInfos(@PathVariable("id") Long id) {
	try {
	    this.peopleId = id;
	    List<Telephone> phones = telephoneRepository.findPhonesByPeopleId(id);
	    List<Address> addresses = addressRepository.findAddressesByPeopleId(id);
	    ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "phones", phones);
	    mv.addObject("addresses", addresses);
	    addCurriculumText(mv);
	    addPeople(id, mv);
	    addEmptyPhone(mv);
	    addEmptyAddress(mv);
	    return mv;
	} catch (NoSuchElementException e) {
	    return toPeoplePage().addObject("msg", "Pessoa não encontrada nos seus contatos");
	}catch (Exception e) {
	    return peopleController.listAll();
	}
    }

    @GetMapping
    public ModelAndView toPeoplePage() {
	return peopleController.listAll();
    }
    
    public void addEmptyPhone(ModelAndView mv) {
	mv.addObject("phone", new Telephone());
    }

    public void addEmptyAddress(ModelAndView mv) {
	mv.addObject("address", new Address());
    }

    public void addPeople(Long id, ModelAndView mv) {
	mv.addObject("people", peopleRepository.findById(id, SessionUtil.getUserId(request)).get());
    }

    private void addCurriculumText(ModelAndView mv) {
	try{
	    fileRepository.findById(peopleId).get();
	    mv.addObject("hasCurriculum", "Sim");
	}catch (Exception e) {
	    mv.addObject("hasCurriculum", "Não");
	}
    }

    public Long getPeopleId() {
        return peopleId;
    }
}
