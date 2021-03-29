package jl.forthem.controllers;

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

import jl.forthem.dto.ItemDTO;
import jl.forthem.models.Item;
import jl.forthem.repositories.ItemRepository;

@RestController
public class ItemController {
	Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	ItemRepository repository;
	
	@PostMapping("/item/{id}")
	public Item addItem(@RequestBody ItemDTO itemDto, @PathVariable("id")Integer categoryId) {
		if (logger.isDebugEnabled()) {logger.debug(String.format("Adding the item: %s to the category id: %s",itemDto.toString(),categoryId));}		
		Item addedItem = new Item(itemDto.getName(), categoryId);	
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
		if (logger.isDebugEnabled()) logger.debug(String.format("Deleting the item with id: %s",id));
		repository.deleteById(id);		
	}

}
