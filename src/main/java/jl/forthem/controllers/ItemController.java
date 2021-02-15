package jl.forthem.controllers;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jl.forthem.models.Item;
import jl.forthem.repositories.ItemRepository;

@CrossOrigin(origins="http://192.168.1.100:4200")
//@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ItemController {
	Logger logger = Logger.getLogger("Item");
		
	@Autowired
	ItemRepository repository;
	
	@PostMapping("/item/{id}")
	public Item addItem(@RequestBody Item item, @PathVariable("id")Integer categoryId) {
		System.out.println("** Entering the addItem method. Adding an item:"+item.getName()+ " in relation to category id: "+categoryId);
		Item addedItem = new Item(item.getId(), item.getName(), categoryId);		
		return repository.save(addedItem);
	}
	
	@GetMapping("/items")
	public List<Item> getItems(){
		List<Item> list = repository.findAll();
		Collections.sort(list);
		return list;
	}
	
	@DeleteMapping("/item/{id}")
	public void deleteItem(@PathVariable("id") Integer id) {
		logger.log(Level.ALL,"Deletion of item: id:"+id, new Object());
		repository.deleteById(id);		
	}

}
