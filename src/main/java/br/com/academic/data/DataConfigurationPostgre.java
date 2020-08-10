package br.com.academic.data;

import java.net.URI;
import java.net.URISyntaxException;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile("prod")
public class DataConfigurationPostgre {

	@Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
	
	public JpaVendorAdapter jpaVendorAdapter() {
		   HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		   adapter.setDatabase(Database.POSTGRESQL);
		   adapter.setShowSql(true);
		   adapter.setGenerateDdl(true);
		   adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		   adapter.setPrepareConnection(true);
		   return adapter;
		   }
	
}
