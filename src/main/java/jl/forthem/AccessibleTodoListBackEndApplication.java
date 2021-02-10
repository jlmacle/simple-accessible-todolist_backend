package jl.forthem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jl.forthem.models.Category;
import jl.forthem.repositories.CategoryRepository;

@SpringBootApplication
public class AccessibleTodoListBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessibleTodoListBackEndApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner create_misc_category_at_startup(CategoryRepository repository) {
		return (args) -> {
			Category misc_category = new Category(1, "Uncategorized", null);
			repository.save(misc_category);
		};
	}
	
	

}
