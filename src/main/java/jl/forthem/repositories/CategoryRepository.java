package jl.forthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jl.forthem.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
