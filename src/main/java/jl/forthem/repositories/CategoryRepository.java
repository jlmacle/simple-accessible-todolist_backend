package jl.forthem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jl.forthem.models.Category;

/**
 * @author 
 * Repository interface used to access the category related data
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public List<Category> findByName(String name);

}
