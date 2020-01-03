package ru.cource.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ru.cource.model.domain.Role;
import ru.cource.model.domain.User;
import ru.cource.model.service.UserServiceInterface;

/**
 * 
 * @author AlexanderM-O
 *
 */
@Component
public class AuthProviderImpl implements AuthenticationProvider {

	@Autowired
	UserServiceInterface service;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		User user = service.findUserByName(name);
		if (user == null) {
			throw new UsernameNotFoundException("No user with such name");
		}
		String password = (String) authentication.getCredentials();
		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role r : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + r.toString()));
		}
		// Encapsulate password and user and authorities into token
		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
