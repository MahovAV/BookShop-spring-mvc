package ru.cource.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtils {
     static Map<String, String> getErrors(BindingResult bindingResult) {
    	 //take only first error as the most important

        Set<String> errors=new HashSet<String>();
    	 
    	Predicate<FieldError> oneFeildCannotHaveTwoErrors=(fieldError)->{
    		if(errors.contains(fieldError.getField())){
    			return false;
    		}else {
    			errors.add(fieldError.getField());
    			return true;
    		}
    	};
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream()
        								     .filter(oneFeildCannotHaveTwoErrors)
        									 .collect(collector);
    }
}
