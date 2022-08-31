package com.start.springboot.web.springbootfirstwebapplication.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	
	private static List<Todo> todos = new ArrayList<>();
	private static int todoscount = 0;
	static {
		
		todos.add(new Todo(++todoscount, "ritwik", "Learn AWS", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todoscount, "ritwik", "DevOps", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++todoscount, "ritwik", "Full Stack", LocalDate.now().plusYears(3), false));
	}

	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate= todo-> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
		
		
	}
	
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
	
		Todo todo = new Todo(++todoscount, username, description, targetDate, done);
		todos.add(todo);
	}
	
	public void deleteTodoService(int id) {
		
		
		Predicate<? super Todo> predicate= todo-> todo.getId()==id;
		todos.removeIf(predicate);
	}


	public Todo findById(int id) {
		// TODO Auto-generated method stub
		
		Predicate<? super Todo> predicate= todo-> todo.getId()==id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
		
	}


	public void updateTodo(@Valid Todo todo) {
		// TODO Auto-generated method stub
		deleteTodoService(todo.getId());
		todos.add(todo);
	}
	
	
	
}
