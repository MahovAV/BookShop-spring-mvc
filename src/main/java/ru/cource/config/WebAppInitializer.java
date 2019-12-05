package ru.cource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by user on 12.11.2019.
 */
@Configuration
public class WebAppInitializer implements WebApplicationInitializer {
    private static Logger logger= LoggerFactory.getLogger(WebAppInitializer.class);
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Где WebApplicationInitializer — интерфейс, предоставляемый Spring MVC
        //  который гарантирует инициализацию при старте контейнера.
    	logger.info("Start initialization");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        logger.info("register "+SpringConfig.class.getName() + " , "+WebConfig.class.getName());
        context.register(SpringConfig.class,WebConfig.class);
        //ЗАРЕГИСТРИРОВАЛИ СВОИСТВА
        context.setServletContext(servletContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        logger.info("adding dispatcher servlet");
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("dispatcher servlet is ready to handle / mapping");
    }
}
