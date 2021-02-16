package jl.forthem;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author 
 * Class used to configure the application
 */
/**
 * @author 
 *
 */
@org.springframework.context.annotation.Configuration
public class Configuration {	
	
	//public static final String FRONTEND_HOMEPAGE = "http://192.168.1.100:4200";
	public static final String FRONTEND_HOMEPAGE = "http://localhost:4200";
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
	
	

}
