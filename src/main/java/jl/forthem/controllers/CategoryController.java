package jl.forthem.controllers;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class CategoryController {
	Logger logger = Logger.getLogger("CategoryController");
		
	@Autowired
	CategoryRepository repository;
	
	
	@PostMapping("/category")
	public Category addCategory(@RequestBody Category category) {
		logger.log(Level.ALL,"Category: "+category.toString(), new Object());
		return repository.save(category);
	}	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/categories")
	public List<Category> getCategories(){
		List<Category> list = repository.findAll();
		Collections.sort(list);
		return list;
	}
	
	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable("id") Integer id) {
		logger.log(Level.ALL,"Deletion of category: id:"+id, new Object());
		repository.deleteById(id);		
	}
	
	@GetMapping("/categories/{name}")
	public Category getCategoryByName(@PathVariable("name") String category_name) {
		return (repository.findByName(category_name)).get(0);
	}
	
	
	
	
	
}
