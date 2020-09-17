package jl.forthem.controllers;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class CategoryController {
	//Logger logger = Logger.getLogger("CategoryController");
		
	@Autowired
	CategoryRepository repository;
	
	
	@PostMapping("/category")
	public Category addCategory(@RequestBody Category category) {
		System.out.println("Category: "+category.toString());
		return repository.save(category);
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/categories")
	public List<Category> getCategories(){
		List<Category> list = repository.findAll();
		Collections.sort(list);
		return list;
	}
	//TODO: CHECK ON SYNTAX
	@DeleteMapping("/categories/{id}")
	public void deleteCategory(Integer id) {
		System.out.println("");
		repository.deleteById(id);
	}
	
	
	
}
