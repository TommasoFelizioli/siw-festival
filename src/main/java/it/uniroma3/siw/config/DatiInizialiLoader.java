package it.uniroma3.siw.config;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DatiInizialiLoader implements CommandLineRunner {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public DatiInizialiLoader(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) {
		Long festival = jdbcTemplate.queryForObject("select count(*) from festival", Long.class);
		if (festival == 0) {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
					new ClassPathResource("dati-iniziali.sql"));
			populator.setSqlScriptEncoding("UTF-8");
			populator.execute(dataSource);
		}
	}
}