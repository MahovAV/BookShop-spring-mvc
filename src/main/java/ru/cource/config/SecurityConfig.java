package ru.cource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.cource.security.AuthProviderImpl;

/**
 * 
 * @author AlexanderM-O
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ru.cource.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Autowired
	AuthProviderImpl authProwided;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
					.antMatchers("/signUp", "/","/images/*").permitAll()
					.antMatchers("/Home", "/getAll").hasRole("USER")
					.antMatchers("/DeleteBook/**,/DeleteBook/**,/DeleteBook/**").hasRole("ADMIN")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/signIn")
					.loginProcessingUrl("/signIn/process")
					.defaultSuccessUrl("/Home", true)
					// send as parametr error value and display it in user
					.failureUrl("/signIn?error=true")
					.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProwided);
	}

}
