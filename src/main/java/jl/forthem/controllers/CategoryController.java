package jl.forthem.controllers;

import jl.forthem.dto.CategoryDTO;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

@RestController
public class CategoryController {
	Logger logger = LoggerFactory.getLogger(CategoryController.class);	
	
	@Autowired
	CategoryRepository repository;
	
	@GetMapping("/")
	public String getIndex()
	{
		return "Index page. <br>Do you try to reach <a href=\"/categories\">the categories</a>,"
				+ "<br> or  <a href=\"/items\"> the items</a>?";
	}
	
	
	@PostMapping("/category")
	public Category addCategory(@RequestBody CategoryDTO categoryDto) 
	{
		
		if (logger.isDebugEnabled()) 
		{
			String data = categoryDto.toString();
			data = data.replaceAll("[\n\r]","_");
			logger.debug(String.format("Adding category: %s ",data));
		}
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		return repository.save(category);
	}	
	
	@GetMapping("/categories")
	public List<Category> getCategories(){
		List<Category> list = repository.findAll();
		Collections.sort(list);
		return list;
	}
	
	@DeleteMapping("/category/{id}")
	public void deleteCategory(@PathVariable("id") Integer id) {
		
		if (logger.isDebugEnabled()) logger.debug(String.format("Deletion of category with id: %s",id));
		repository.deleteById(id);		
	}
	
	@GetMapping("/category/{name}")
	public Category getCategoryByName(@PathVariable("name") String categoryName) {
		return repository.findByName(categoryName).get(0);
	}
	
	
	
	
	
}
