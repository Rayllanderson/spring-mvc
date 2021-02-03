package com.rayllanderson.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.Address;
import com.rayllanderson.entities.File;
import com.rayllanderson.entities.Telephone;
import com.rayllanderson.repositories.AddressRepository;
import com.rayllanderson.repositories.FileRepository;
import com.rayllanderson.repositories.PeopleRepository;
import com.rayllanderson.repositories.TelephoneRepository;
import com.rayllanderson.util.FileUtil;

@Controller
@RequestMapping("**/infos")
public class PeopleInformationController {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Long peopleId = null;

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
	} catch (Exception e) {
	    return new ModelAndView("pages/people");
	}
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

    @GetMapping("/download/curriculum")
    public void downloadCurriculum(HttpServletResponse response) {
	try {
	    File file = fileRepository.findById(peopleId).get();
	    FileUtil.setToDownload(response, file.getBytes(), "currículo", "pdf");
	    response.setStatus(200);
	} catch (NoSuchElementException e) {
	    response.setStatus(400);
	}
    }
    
    private void addCurriculumText(ModelAndView mv) {
	try{
	    fileRepository.findById(peopleId).get();
	    mv.addObject("hasCurriculum", "Sim");
	}catch (Exception e) {
	    mv.addObject("hasCurriculum", "Não");
	}
    }
}
