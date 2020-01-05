package ru.cource.config;

import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

@Order(2)
/**
 * Used to activate DelegatingFilterProxy,and configure security filters
 * 
 * @author AlexanderM-O
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
}