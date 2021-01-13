package jl.forthem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jl.forthem.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public List<Category> findByName(String name);

}
