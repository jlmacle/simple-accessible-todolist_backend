package jl.forthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jl.forthem.models.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
}
