package jl.forthem.controllers;

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

import jl.forthem.Configuration;
import jl.forthem.models.Item;
import jl.forthem.repositories.ItemRepository;

// Application that needed a rule in the firewall for the application to be accessed from 
// another computer on the local network (with IP 192.168.1.101): 
// ..../sts-4.8.1.RELEASE\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.0.v20201014-1246\jre\bin\javaw.exe
@CrossOrigin(origins=Configuration.FRONTEND_HOMEPAGE)
@RestController
public class ItemController {
	Logger logger = LoggerFactory.getLogger(ItemController.class);
		
	@Autowired
	ItemRepository repository;
	
	@PostMapping("/item/{id}")
	public Item addItem(@RequestBody Item item, @PathVariable("id")Integer categoryId) {
		logger.info("Adding the item: "+item.toString()+" to the category id: "+categoryId);
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
		logger.info("Deleting the item with id: "+id);
		repository.deleteById(id);		
	}

}
