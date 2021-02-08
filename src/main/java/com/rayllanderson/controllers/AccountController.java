package com.rayllanderson.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rayllanderson.entities.User;
import com.rayllanderson.exceptions.UsernameExistsException;
import com.rayllanderson.exceptions.WrongPasswordException;
import com.rayllanderson.services.UserService;
import com.rayllanderson.util.SessionUtil;

@Controller
@RequestMapping("**/account")
public class AccountController {

    @Autowired
    private UserService service;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    private final String MAIN_VIEW_NAME = "pages/account";

    @GetMapping
    public ModelAndView list() {
	Optional<User> user = service.findById(SessionUtil.getUserId(request));
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME, "user", user.get());
	return mv;
    }
    
    @GetMapping("/**")
    public ModelAndView reloadPage() {
	return list();
    }

    @PostMapping("/update-username")
    public ModelAndView updateUsername(@RequestParam String username) {
	ModelAndView mv = new ModelAndView(MAIN_VIEW_NAME);
	User user = new User(username);
	try{
	    user = service.updateUsername(user);
	    request.getSession().setAttribute("username", username);
	    reponse("Username atualizado com sucesso!", 201, mv);
	}catch (UsernameExistsException e) {
	    reponse("Já existe um usuário utilizando esse username. Por favor, tente outro", 409, mv);
	}catch (UsernameNotFoundException | IllegalArgumentException e) {
	    reponse("Ocorreu um erro. Atualize a página ou Faça login novamente", 400, mv);
	}finally {
	    mv.addObject("user", user);
	}
	return mv;
    }
    
    @ResponseBody
    @PostMapping("change-password")
    public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
	try {
	    service.changePassword(currentPassword, newPassword);
	    return reponse("senha alterada com sucesso", 200);
	}catch (IllegalArgumentException e) {
	    return reponse("um ou mais campos estão vazios", 400);
	}catch (WrongPasswordException e) {
	    return reponse("a atual senha digitada não corresponde com a atual senha registrada", 400);
	}
    }
    
    private ModelAndView reponse(String message, int code, ModelAndView mv) {
	boolean success = code >= 200 && code < 300;
	if (success) 
	    mv.addObject("alertClass", "alert-success");
	else
	    mv.addObject("alertClass", "alert-danger");
	mv.addObject("msg", message);
	 return mv;
    }
    
    private String reponse(String message, int code) {
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	response.setStatus(code);
	return message;
    }

}
