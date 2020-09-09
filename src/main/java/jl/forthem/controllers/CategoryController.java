package jl.forthem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

@CrossOrigin("localhost:4200")
@RestController
public class CategoryController {
	@Autowired
	CategoryRepository repository;
	
	@PostMapping("/category")
	public Category addCategory(Category category) {
		return repository.save(category);
	}
	
	
	
}
