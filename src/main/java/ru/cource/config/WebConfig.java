package ru.cource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by user on 12.11.2019.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer  {
    private static Logger logger= LoggerFactory.getLogger(WebConfig.class);


    @Bean
    public ViewResolver getViewResolver(){
        // интерфейс, реализуемый объектами,
        // которые способны находить представления View по имени View Name.
        logger.info("getting freeMarkerViewResolver");
        FreeMarkerViewResolver freeMarkerViewResolver =new FreeMarkerViewResolver();
        freeMarkerViewResolver.setOrder(1);
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setPrefix("");
        //суффикрс по которому будет искать файлы
        return freeMarkerViewResolver;
    }
    @Bean
    public FreeMarkerConfigurer getFreeMarkerConfigurer(){
        //настраиваем наш freemarker resolver
        logger.info("getting freemarkerConfigurer");
        FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
        freemarkerConfigurer.setTemplateLoaderPaths("/","WEB-INF/view","WEB-INF/view/changeBook","WEB-INF/view/createBook","WEB-INF/view/deleteBook");
        return freemarkerConfigurer;
    }

}
