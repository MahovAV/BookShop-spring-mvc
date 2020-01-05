package ru.cource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 
 * @author AlexanderM-O
 *
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
public class WebConfig implements WebMvcConfigurer {
	@Value( "${upload.path}" )
	String uploadPath;
	
	@Bean
	public ViewResolver getViewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setOrder(1);
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setPrefix("");
		return freeMarkerViewResolver;
	}

	@Bean
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
		freemarkerConfigurer.setTemplateLoaderPaths("WEB-INF/view");
		return freemarkerConfigurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/images/**")
			.addResourceLocations("static/images/","file:///"+uploadPath+"/");
		
	}
}
