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
	public CommandLineRunner createMiscCategoryAtStartup(CategoryRepository repository) {
		return args -> {
			Category miscCategory = new Category(1, "Misc.", null);
			repository.save(miscCategory);
		};
	}
	
	

}
