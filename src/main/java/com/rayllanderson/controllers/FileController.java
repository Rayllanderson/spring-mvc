package com.rayllanderson.controllers;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.File;
import com.rayllanderson.services.FileService;

@Controller
@RequestMapping("**/files")
public class FileController extends PeopleInformationController {

    @Autowired
    private FileService fileService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ModelAndView save(Long peopleId, MultipartFile file) throws IOException {
	fileService.save(file, peopleId);
	return super.listInfos(peopleId);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
	Optional<File> object = fileService.findById(id);
	if (object.isPresent()) {
	    fileService.deleteById(id);
	    return listInfos(peopleId);
	}
	return toPeoplePage();
    }
    
    @GetMapping("/download/curriculum")
    public void downloadCurriculum(HttpServletResponse response, @RequestParam Long peopleId) {
	try {
	    System.out.println(peopleId);
	    fileRepository.findById(peopleId).get().download(response);
	    response.setStatus(200);
	} catch (NoSuchElementException e) {
	    response.setStatus(400);
	}catch (Exception e) {
	    e.printStackTrace();
	    response.setStatus(500);
	}
    }

}
