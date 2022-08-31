package com.start.springboot.web.springbootfirstwebapplication.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller // @Controller annotation indicates that a particular class serves the role of a controller
@SessionAttributes("username")
public class WelcomeController {
	
// DispatcherServlet maps RequestMapping string to its respective methods... "/login" maps to LoginMessage method. After that dispatcherservlet starts find view(kind of jsp or any page to display.)
//	@RequestMapping("/login") // @RequestMapping Annotation which is used to map HTTP requests to handler methods of MVC and REST controllers
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String gotoWelcomePage(ModelMap model) { // ModelMap is used for pass the value of na
		
//		model.put("input", name);
//		System.out.println(name); // NOT Recommended in Production level coding

		model.put("username", getLoggedInUsername());
		
		return "welcome";
	}
	
	
	private String getLoggedInUsername() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getName();
		
	}

}
