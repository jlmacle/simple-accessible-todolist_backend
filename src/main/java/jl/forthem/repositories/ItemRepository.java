package jl.forthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jl.forthem.models.Item;


/**
 * @author 
 * Repository interface used to access the item related data
 */
public interface ItemRepository extends JpaRepository<Item, Integer>{
	
}
