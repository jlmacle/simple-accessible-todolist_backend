package jl.forthem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author 
 * Class used to configure the application
 */

@org.springframework.context.annotation.Configuration
@EnableWebMvc
public class Configuration {	
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);	
	public static final String CORS_ALLOWED = "http://localhost:4200";
	public static final String CORS_ALLOWED_ = "http://127.0.0.1:4200";
	public static final String CORS_ALLOWED_80 = "http://localhost";
	public static final String CORS_ALLOWED_80_ = "http://127.0.0.1";
	public static final String CORS_ALLOWED_Azure  = "https://test-atl.azurewebsites.net";
	
	// Addition for Grid 4 configuration. 
	// Use of environment variables to pass a code quality check
	//public static final String CORS_ALLOWED_2 = System.getenv("CORS_ALLOWED_2");//Angular server
	//public static final String CORS_ALLOWED_3 = System.getenv("CORS_ALLOWED_3");//Angular server
		
	/**
	 * Feeds some configuration information from environment variables.
	 * @return a DataSource object
	 * https://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html (Java 8)
	 */
	@Bean		
	public DataSource getDataSource() {	
		String dbURL = null;
		String dbUSERNAME = null ;
		String dbPASSWORD = null ;
		
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		if (System.getenv("POSTGRES_USER_FILE") != null) 
		{
			dbUSERNAME = extractDockerSecretFromFile(System.getenv("POSTGRES_USER_FILE"));			
		}
		else
		{
			dbUSERNAME = System.getenv("DB_USERNAME");
			
		}
		
		if (System.getenv("POSTGRES_PASSWORD_FILE") != null)
		{
			dbPASSWORD = extractDockerSecretFromFile(System.getenv("POSTGRES_PASSWORD_FILE"));			
		}
		else
		{
			dbPASSWORD = System.getenv("DB_PASSWORD");
		}
		
		if (System.getenv("POSTGRES_DB_FILE") != null && System.getenv("DB_JDBC_ROOT_FILE") != null) 
		{
			dbURL = extractDockerSecretFromFile(System.getenv("DB_JDBC_ROOT_FILE")) + extractDockerSecretFromFile(System.getenv("POSTGRES_DB_FILE"));		
						
		}
		else
		{
			dbURL =  System.getenv("DB_URL");			
		}
		
		logInfoEnabled(logger, "Valeur de DB_URL: %s",dbURL );
		logInfoEnabled(logger, "Valeur de DB_USERNAME: %s",dbUSERNAME);
		logInfoEnabled(logger, "Valeur de DB_PASSWORD: %s",dbPASSWORD);
		dataSourceBuilder.url(dbURL);	
		dataSourceBuilder.username(dbUSERNAME);
		dataSourceBuilder.password(dbPASSWORD);				
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
					
				//Grid 4 
				//String[] origins= {CORS_ALLOWED, CORS_ALLOWED_2, CORS_ALLOWED_3};
				String[] origins= {CORS_ALLOWED, CORS_ALLOWED_,CORS_ALLOWED_80,CORS_ALLOWED_80_,CORS_ALLOWED_Azure};
				
				//Mappings for the CategoryController
				registry.addMapping("/category").allowedOrigins(origins).allowedMethods("GET");
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
	
	public static String extractDockerSecretFromFile(String pathToSecret)
	{	String secret=null;
		try {			
			secret = Files.readString(Paths.get(pathToSecret));
			logInfoEnabled(logger,"Secret value extracted : %s",secret);
		} catch (IOException e) {
			logInfoEnabled(logger,"Caught an IOException in extractDockerSecretFromFile: %s",e.getLocalizedMessage());
			e.printStackTrace(); //suppressed to avoid a security hotspot.
		}		
		return secret;
	}
		
	public static void logInfoEnabled(Logger logger, String msg, String data)
	{
		if (logger.isInfoEnabled()) logger.info(String.format(msg, data));
	}

}
