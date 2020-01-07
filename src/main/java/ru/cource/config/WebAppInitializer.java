package ru.cource.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * 
 * @author AlexanderM-O
 *
 */
@Configuration
@ComponentScan(basePackages = { "ru.cource.controller" })
@Order(1)
public class WebAppInitializer implements WebApplicationInitializer {
	private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored

	private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
	                                                   // Beyond that size spring will throw exception.
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
	
	private MultipartConfigElement getMultipartConfigElement() {

	MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
	            LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
	    return multipartConfigElement;
	}
	
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	    registration.setMultipartConfig(getMultipartConfigElement());
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(CoreConfig.class, WebConfig.class);
		context.setServletContext(servletContext);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(context));
		customizeRegistration(dispatcher);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
	}
}
