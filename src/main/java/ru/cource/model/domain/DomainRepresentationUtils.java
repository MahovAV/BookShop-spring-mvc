package ru.cource.model.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 * 
 * @author AlexanderM-O
 *
 */
public class DomainRepresentationUtils {
	/**
	 * Used to get error message for following validation.If there is no error
	 * return null
	 * 
	 * @param s
	 * @return error message
	 */
	public static String validCommaSeparated(String s) {
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == ',') {
				if (i == s.length() - 1 || s.charAt(i + 1) == ',') {
					return "invalid string";
				}
			}
		}
		List<String> listWithDuplicates = Arrays.asList(s.split(",")).stream().collect(Collectors.toList());
		List<String> listWithoutDuplicates = listWithDuplicates.stream().distinct().collect(Collectors.toList());
		if (listWithDuplicates.size() != listWithoutDuplicates.size()) {
			return "there are dublicates in string";
		}

		return null;
	}
}
