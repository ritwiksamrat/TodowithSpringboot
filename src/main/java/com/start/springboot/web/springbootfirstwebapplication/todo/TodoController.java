package com.start.springboot.web.springbootfirstwebapplication.todo;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

//@Controller
@SessionAttributes("username") // @SessionAttribute annotation retrieve the existing attribute from the session. 
//This annotation allows you to tell Spring which of your model attributes will also be copied 
//to HttpSession before rendering the view
public class TodoController {

	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		
		String username =getLoggedInUsername(model);
		List<Todo> todos= todoService.findByUsername(username);
		model.addAttribute("todos", todos);
		return "listTodos";
		
		
	}

	private String getLoggedInUsername(ModelMap model) {
		
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getName();
		
	}
	
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username= getLoggedInUsername(model);
		Todo todo = new Todo(0, "ritwik", "",LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
		
	}
	
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if (result.hasErrors()) {
			return "todo";
		}
		String username= getLoggedInUsername(model);
		todoService.addTodo(username, todo.getDescription(),todo.getTargetDate(), false);
		return "redirect:list-todos";
		
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		
		todoService.deleteTodoService(id);
		return "redirect:list-todos";
		
		
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		
		Todo todo= todoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if (result.hasErrors()) {
			return "todo";
		}
		String username= getLoggedInUsername(model);
		todo.setUsername(username);
		todoService.updateTodo(todo);
		return "redirect:list-todos";
		
	}
	
	
	
	
}
