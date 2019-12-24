package ru.cource.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Configure DataBase and Entity.Needed for Hibernate
 */

@Configuration
@ComponentScan(basePackages="ru.cource.model.dao")
@EnableTransactionManagement
public class DataBaseConfig {
    private static final String url="jdbc:mysql://localhost:3306/BASE?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String driverClassName="com.mysql.cj.jdbc.Driver";
    private static final String userName="root";
    private static final String password="1234";
    
    @Bean
    public DataSource dataSource() {
    	BasicDataSource ds=new BasicDataSource();
    	ds.setUrl(url);
    	ds.setDriverClassName(driverClassName);
    	ds.setUsername(userName);
    	ds.setPassword(password);
    	System.out.println("DataSource");
		return ds;
    	
    }
    
	@Bean
    public Flyway flyway() {
		//prepare our data base
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource());
            flyway.migrate();
            System.out.println("Flyway");
            return flyway;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
	
	@Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory() throws IOException {
    	//configure session factory which will be shared with all dao
		LocalSessionFactoryBean sessionFactoryBean=new LocalSessionFactoryBean();
		sessionFactoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));
		sessionFactoryBean.setPackagesToScan("ru.cource.model.domain");
		sessionFactoryBean.afterPropertiesSet();
		System.out.println("SessionFactory");
        return sessionFactoryBean.getObject();
        
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {		//Exactly same name
    	
    	//manager allow us to use @Transactional 
    	HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    	transactionManager.setDataSource(dataSource());
    	transactionManager.setSessionFactory(sessionFactory());
    	return transactionManager; 
    }
}
