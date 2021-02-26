package jl.forthem.controllers;

import jl.forthem.Configuration;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

/*
 Application that needed a rule in the firewall for the application to be accessed from 
 another computer on the local network (with IP 192.168.1.101): 
 ..../sts-4.8.1.RELEASE\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.0.v20201014-1246
   \jre\bin\javaw.exe
*/ 
@CrossOrigin(origins=Configuration.FRONTEND_HOMEPAGE)

@RestController
public class CategoryController {
	Logger logger = LoggerFactory.getLogger(CategoryController.class);	
		
	@Autowired
	CategoryRepository repository;
	
	
	@PostMapping("/category")
	public Category addCategory(@RequestBody Category category) {
		logger.info("Adding category: "+category.toString());
		return repository.save(category);
	}	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/categories")
	public List<Category> getCategories(){
		List<Category> list = repository.findAll();
		Collections.sort(list);
		return list;
	}
	
	@DeleteMapping("/category/{id}")
	public void deleteCategory(@PathVariable("id") Integer id) {
		logger.info("Deletion of category with id :"+id);
		repository.deleteById(id);		
	}
	
	@GetMapping("/category/{name}")
	public Category getCategoryByName(@PathVariable("name") String categoryName) {
		return repository.findByName(categoryName).get(0);
	}
	
	
	
	
	
}
