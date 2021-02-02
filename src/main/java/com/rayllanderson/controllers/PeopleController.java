package com.rayllanderson.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.People;
import com.rayllanderson.entities.enums.Gender;
import com.rayllanderson.reports.ReportUtil;
import com.rayllanderson.repositories.ProfessionRepository;
import com.rayllanderson.services.PeopleService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("**/pessoas")
public class PeopleController {

    @Autowired
    private PeopleService service;

    private final String MAIN_VIEW_NAME = "pages/people";
    
    @Autowired
    private ProfessionRepository professionRepository;
    
    @Autowired
    HttpServletRequest request;

    @Autowired
    private ReportUtil reportUtil;

    @GetMapping()
    public ModelAndView listAll() {
	List<People> peoples = service.findAll();
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "peoples", peoples);
	mv.addObject("professions", professionRepository.findAllWithoutPeoples());
	addEmptyPeople(mv);
	request.getSession().setAttribute("peoples", peoples);
	return mv;
    }

    @PostMapping()
    public ModelAndView save(@Valid People people, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
	    return catchErrors(bindingResult, people);
	}
	service.save(people);
	return listAll();
    }

    @GetMapping("/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
	Optional<People> object = service.findById(id);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME);
	if (object.isPresent()) {
	    mv.addObject("people", object.get());
	    mv.addObject("professions", professionRepository.findAllWithoutPeoples());
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
	List<People> peoples = service.findByName(name);
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "peoples", peoples);
	request.getSession().setAttribute("peoples", peoples);
	addEmptyPeople(mv);
	return mv;
    }

    @PostMapping("/search")
    @ResponseBody
    public List<People> findByGender(@RequestBody String gender, HttpServletRequest request) {
	List<People> peoples = new ArrayList<>();
	try {
	    peoples = service.findByGender(Gender.valueOf(gender));
	    return peoples;
	} catch (IllegalArgumentException e) {
	    peoples = service.findAll();
	    return peoples;
	} finally {
	    request.getSession().setAttribute("peoples", peoples);
	}
    }

    @GetMapping("/pdf-report-download")
    public void pdfReportDownload(HttpServletResponse response) throws JRException, IOException {
	@SuppressWarnings("unchecked")
	List<People> peoples = (List<People>) request.getSession().getAttribute("peoples");
	byte [] pdf = reportUtil.generateReport(peoples, "people", request.getServletContext());
	
	// tamanho do arquivo
	response.setContentLength(pdf.length);
	
	response.setContentType("application/pdf");
	
	String headerKey = "Content-Disposition";
	String headerValue = String.format("attachment; filename=\"%s\"", "relatório.pdf");
	response.setHeader(headerKey, headerValue);
	response.getOutputStream().write(pdf);
	response.getOutputStream().close();
    }

    private void addEmptyPeople(ModelAndView mv) {
	mv.addObject("people", new People());
    }

    private ModelAndView catchErrors(BindingResult bindingResult, People people) {
	var mv = listAll();
	mv.addObject("people", people);
	List<String> erros = new ArrayList<>();
	bindingResult.getAllErrors().forEach(x -> erros.add(x.getDefaultMessage()));
	mv.addObject("msg", erros);
	return mv;
    }

}
