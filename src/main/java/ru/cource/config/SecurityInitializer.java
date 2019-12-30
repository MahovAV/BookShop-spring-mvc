package ru.cource.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
@Order(2)
/**
 * Used to activate DelegatingFilterProxy
 * @author user
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}