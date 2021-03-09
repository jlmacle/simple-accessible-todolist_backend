package jl.forthem;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 
 * Class used to configure the application
 */
/**
 * @author 
 *
 */
@org.springframework.context.annotation.Configuration
@EnableWebMvc
public class Configuration {	
	
	public static final String CORS_ALLOWED = "http://localhost:4200";
	
	//Grid 4 configuration
	public static final String CORS_ALLOWED_2 = "http://192.168.1.15:4200";//hub
		
	/**
	 * Feeds some configuration information from environment variables.
	 * @return a DataSource object
	 * https://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html (Java 8)
	 */
	@Bean		
	public DataSource getDataSource() {		
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		dataSourceBuilder.url(System.getenv("DB_URL"));
		dataSourceBuilder.password(System.getenv("DB_PASSWORD"));
		dataSourceBuilder.username(System.getenv("DB_USERNAME"));
		return dataSourceBuilder.build();
		
	}
	//https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() 
		{	
			@Override
			public void addCorsMappings(CorsRegistry registry)
			{	
				//String[] origins= {CORS_ALLOWED};
				
				//Grid 4
				String[] origins= {CORS_ALLOWED, CORS_ALLOWED_2};
				
				//Mappings for the CategoryController
				registry.addMapping("/category").allowedOrigins(origins);
				registry.addMapping("/categories").allowedOrigins(origins).allowedMethods("GET");
				registry.addMapping("/category/{id}").allowedOrigins(origins).allowedMethods("DELETE");
				registry.addMapping("/category/{name}").allowedOrigins(origins);
				
				//Mappings for the ItemController
				registry.addMapping("/item/{id}").allowedOrigins(origins).allowedMethods("POST","DELETE");
				registry.addMapping("/items").allowedOrigins(origins);			
				
			}
		};
	}
	
	
	
	

}
