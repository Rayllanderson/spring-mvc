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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.File;
import com.rayllanderson.entities.People;
import com.rayllanderson.entities.User;
import com.rayllanderson.entities.enums.Gender;
import com.rayllanderson.reports.ReportUtil;
import com.rayllanderson.repositories.ProfessionRepository;
import com.rayllanderson.services.FileService;
import com.rayllanderson.services.PeopleService;
import com.rayllanderson.util.SessionUtil;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("**/contatos")
public class PeopleController implements HandlerExceptionResolver {

    @Autowired
    private PeopleService service;
    
    @Autowired
    private FileService fileService;

    private final String MAIN_VIEW_NAME = "pages/contact";

    @Autowired
    private ProfessionRepository professionRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ReportUtil reportUtil;
    
    @GetMapping
    public ModelAndView listAll() {
	List<People> peoples = service.findAll(getUserId());
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "peoples", peoples);
	mv.addObject("professions", professionRepository.findAllWithoutPeoples());
	addEmptyPeople(mv);
	request.getSession().setAttribute("peoples", peoples);
	return mv;
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ModelAndView save(@Valid People people, BindingResult bindingResult, MultipartFile file)
	    throws IOException {
	if (bindingResult.hasErrors()) {
	    return catchErrors(bindingResult, people);
	}
	people.setUser(new User(getUserId()));
	people = service.save(people);
	fileService.save(file, people.getId());
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
	service.deleteById(id, getUserId());
	return listAll();
    }

    @GetMapping("/search")
    public ModelAndView findByName(@RequestParam String name) {
	boolean nameIsEmpty = name.trim().isEmpty() || name == null;
	if (nameIsEmpty) {
	    return listAll();
	}
	List<People> peoples = service.findByName(name, getUserId());
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
	    peoples = service.findByGender(Gender.valueOf(gender), getUserId());
	    return peoples;
	} catch (IllegalArgumentException e) {
	    peoples = service.findAll(getUserId());
	    return peoples;
	} finally {
	    request.getSession().setAttribute("peoples", peoples);
	}
    }

    @GetMapping("/pdf-report-download")
    public void pdfReportDownload(HttpServletResponse response) throws JRException, IOException {
	@SuppressWarnings("unchecked")
	List<People> peoples = (List<People>) request.getSession().getAttribute("peoples");
	byte[] pdf = reportUtil.generateReport(peoples, "people", request.getServletContext());
	new File(null, pdf, "relatório", "application/pdf").download(response);
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

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
	    Exception ex) {
	if (ex instanceof MaxUploadSizeExceededException) {
	    return listAll().addObject("msg", "Limite de upload Excedido. Máximo 50KB");
	}
	return null;
    }
    
    private Long getUserId() {
	return SessionUtil.getUserId(request);
    }
}
