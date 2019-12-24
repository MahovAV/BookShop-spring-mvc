package ru.cource.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import ru.cource.config.WebAppInitializer;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerAspect {
	private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

	@Around("execution(* ru.cource.model.dao.*.*(..))")
	public Object logDaoOperation(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("start operation " + pjp.getSignature().getName());
		long start = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		logger.info("end operation " + pjp.getSignature().getName() + " with " + (System.currentTimeMillis() - start)
				+ " msec.");

		// return same object to runtime environment object
		return retVal;
	}

	@After("execution(* ru.cource.model.validation.BookValidator.*(..))")
	public void logValidation(JoinPoint joinPoint) throws Throwable {
		Errors errors = (Errors) joinPoint.getArgs()[1];
		if (errors.hasErrors()) {
			logger.info("there are some errors in inputed value");
		} else
			logger.info("validation succesfully ended");
	}

}
