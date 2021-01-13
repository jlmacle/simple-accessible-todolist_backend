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
	
	//  Sample of potential category list returned after translation in JSON
	//  [{"id":30,"name":"Joy","items":[]},{"id":31,"name":"Love","items":[]},{"id":1,"name":"Misc.","items":[{"name":"Item","id":2,"categoryId":1},{"name":"Item","id":3,"categoryId":1},{"name":"Item","id":4,"categoryId":1},{"name":"Item","id":5,"categoryId":1},{"name":"Item","id":6,"categoryId":1},{"name":"Item","id":7,"categoryId":1},{"name":"Item","id":8,"categoryId":1},{"name":"Item","id":9,"categoryId":1},{"name":"Item","id":10,"categoryId":1},{"name":"Item","id":11,"categoryId":1},{"name":"Item","id":12,"categoryId":1},{"name":"Item","id":13,"categoryId":1},{"name":"Item","id":14,"categoryId":1},{"name":"Item","id":15,"categoryId":1},{"name":"Item","id":16,"categoryId":1},{"name":"Item","id":22,"categoryId":1},{"name":"Item","id":23,"categoryId":1},{"name":"Item","id":24,"categoryId":1},{"name":"Item","id":25,"categoryId":1},{"name":"Item","id":26,"categoryId":1},{"name":"Item","id":27,"categoryId":1},{"name":"Item","id":28,"categoryId":1},{"name":"Item","id":29,"categoryId":1}]},{"id":17,"name":"Peace","items":[{"name":"Item","id":18,"categoryId":17},{"name":"Item","id":19,"categoryId":17},{"name":"Item","id":20,"categoryId":17},{"name":"Love","id":21,"categoryId":17}]}]
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
