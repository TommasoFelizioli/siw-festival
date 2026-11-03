package it.uniroma3.siw.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final DataSource dataSource;

	public SecurityConfiguration(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		manager.setUsersByUsernameQuery(
				"SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
		manager.setAuthoritiesByUsernameQuery(
				"SELECT username, role FROM credentials WHERE username=?");
		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/admin/**").hasAuthority(Credentials.ADMIN_ROLE);
			authorize.requestMatchers("/error").permitAll();
			authorize.requestMatchers(HttpMethod.GET, "/", "/css/**", "/festival", "/festival/**",
					"/film", "/film/**", "/attori", "/attori/**", "/register", "/login",
					"/api/**", "/app", "/app/**", "/immagini/**").permitAll();
			authorize.requestMatchers(HttpMethod.POST, "/register").permitAll();
			authorize.anyRequest().authenticated();
		});

		httpSecurity.formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/");
			form.failureUrl("/login?error=true");
		});

		httpSecurity.logout(logout -> {
			logout.logoutUrl("/logout");
			logout.logoutSuccessUrl("/");
			logout.permitAll();
		});

		return httpSecurity.build();
	}
}