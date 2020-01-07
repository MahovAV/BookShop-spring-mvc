package ru.cource.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author AlexanderM-O
 *
 */
public class ControllerUtils {
	static Map<String, String> getErrors(BindingResult bindingResult) {
		// take only first error as the most important

		Set<String> errors = new HashSet<String>();

		Predicate<FieldError> oneFeildCannotHaveTwoErrors = (fieldError) -> {
			if (errors.contains(fieldError.getField())) {
				return false;
			} else {
				errors.add(fieldError.getField());
				return true;
			}
		};
		Collector<FieldError, ?, Map<String, String>> collector = Collectors
				.toMap(fieldError -> fieldError.getField() + "Error", FieldError::getDefaultMessage);
		return bindingResult.getFieldErrors().stream().filter(oneFeildCannotHaveTwoErrors).collect(collector);
	}
	
	static String insertFileWithoutCollisions(MultipartFile file,String uploadPath) throws IllegalStateException, IOException {
		String uniqueIdOfFile=UUID.randomUUID().toString();
        String resultFilename = uniqueIdOfFile + "." + file.getOriginalFilename();            
        file.transferTo(new File(uploadPath + "\\" + resultFilename));
        return resultFilename;
	}
	
	static void deleteFileIfExists(String fileName,String uploadPath) {
		if(fileName!=null) {
    		File file=new File(uploadPath + "\\"+fileName);
    		file.delete();
		}
	}
}
