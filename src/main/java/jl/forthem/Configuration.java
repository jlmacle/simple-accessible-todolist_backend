package jl.forthem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
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
	public static final String CORS_LOCALHOST_4200 = "http://localhost:4200"; 
	// For Angular launch with default port and default host	
	public static final String CORS_LOCALHOST_NO_PORT = "http://127.0.0.1";
	// For the nginx server ran on local Docker images
		
	public static final String CORS_LOOPBACK_ADDRESS_4200 = "http://127.0.0.1:4200"; 
	// For Angular launched with the default port and loopback address
	
	// Different 
	public static final String CORS_AZURE_FRONTEND_HOSTNAME = "http://frontend";
	public static final String CORS_AZURE_FRONTEND_HOSTNAME_HTTPS = "https://frontend";
	public static final String CORS_AZURE_NO_PORT  = "https://test-atl.azurewebsites.net";
	public static final String CORS_AZURE_80= "https://test-atl.azurewebsites.net:80";
	
	// Addition for Grid 4 configuration. 
	// Use of environment variables to pass a code quality check
	public static final String CORS_ALLOWED_STATIC_12 = System.getenv("CORS_ALLOWED_STATIC_12");
	// With a static IP, the default port used is 4200
	public static final String CORS_ALLOWED_STATIC_15 = System.getenv("CORS_ALLOWED_STATIC_15");
	public static final String CORS_ALLOWED_STATIC_16 = System.getenv("CORS_ALLOWED_STATIC_16");
	
	public static final String CORS_ALLOWED_EXTERNAL_IP= "http://109.211.65.230";
	
		
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
				String[] origins= {CORS_LOCALHOST_NO_PORT, CORS_LOCALHOST_4200, CORS_LOOPBACK_ADDRESS_4200,
						CORS_AZURE_NO_PORT, CORS_AZURE_80,CORS_AZURE_FRONTEND_HOSTNAME,CORS_AZURE_FRONTEND_HOSTNAME_HTTPS,
						CORS_ALLOWED_STATIC_12,CORS_ALLOWED_STATIC_15,CORS_ALLOWED_STATIC_16,
						CORS_ALLOWED_EXTERNAL_IP};

				
				//Mappings for the CategoryController				
				registry.addMapping("/category").allowedOrigins(origins).allowedMethods("*");	
				//registry.addMapping("/category").allowedOrigins(origins).allowedMethods("POST","OPTIONS");	
				// Added OPTIONS for the preflight request.
				// https://developer.mozilla.org/fr/docs/Web/HTTP/Methods/OPTIONS
				//registry.addMapping("/categories").allowedOrigins(origins).allowedMethods("GET","OPTIONS");
				registry.addMapping("/categories").allowedOrigins(origins).allowedMethods("*");
				registry.addMapping("/category/{id}").allowedOrigins(origins).allowedMethods("DELETE");
				registry.addMapping("/category/{name}").allowedOrigins(origins);
				
				//Mappings for the ItemController
				registry.addMapping("/item/{id}").allowedOrigins(origins).allowedMethods("POST","DELETE");
				registry.addMapping("/items").allowedOrigins(origins).allowedMethods("GET","OPTIONS");		
				
			}
		};
	}
	
	public static String extractDockerSecretFromFile(String pathToSecret)
	{	String secret=null;
		try {			
			secret = Files.readString(Paths.get(pathToSecret));
			logInfoEnabled(logger,"Path to secret : %s",pathToSecret);
			logInfoEnabled(logger,"Secret value extracted : %s",secret);
		} catch (IOException e) {
			logInfoEnabled(logger,"*** Caught an IOException in extractDockerSecretFromFile: %s",e.getLocalizedMessage());
			//e.printStackTrace //suppressed to avoid a security hotspot.
		}		
		return secret;
	}
	
	// Anish B.
	// https://stackoverflow.com/questions/66064081/spring-webflux-2-4-2-404-on-actuator-auditevents-httptrace-integrationgraph
	@Bean
	public HttpTraceRepository htttpTraceRepository() {
	  return new InMemoryHttpTraceRepository();
	}
		
	public static void logInfoEnabled(Logger logger, String msg, String data)
	{
		if (logger.isInfoEnabled()) logger.info(String.format(msg, data));
	}

}
