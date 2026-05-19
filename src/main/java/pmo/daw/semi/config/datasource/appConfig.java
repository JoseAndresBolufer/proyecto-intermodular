package pmo.daw.semi.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import pmo.daw.semi.transactionmanager.TransactionManager;

@Configuration
public class appConfig {

	@Bean(name = "javaDataSource")
	@ConfigurationProperties(prefix = "datasource")
	DataSource javaDataSource() {
		return DataSourceBuilder.create().build();
	}

	// TransactionManager singleton configurado con el DataSource
	@Bean
	TransactionManager transactionManager(@Qualifier("javaDataSource") DataSource dataSource) {
		TransactionManager tm = TransactionManager.getInstance();
		tm.setDataSource(dataSource);
		return tm;
	}

	// Carga de scripts después de que la app esté lista
	@Bean
	ApplicationRunner loadJavaScripts(@Qualifier("javaDataSource") DataSource dataSource) {
		return args -> {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.addScript(new ClassPathResource("schema-jdbc.sql"));
			populator.addScript(new ClassPathResource("data-jdbc.sql"));
			populator.execute(dataSource);
		};
	}
}