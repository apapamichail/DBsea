/**
 * 
 */
package gr.uoi.cs.dbsea.generalchecks;

import gr.uoi.cs.dbsea.statistics.GenericCheckStatistics;

/**
 * @author Papamichail Aggelos
 * All the methods that describe the type of case
 * for a given string
 */
public final class CaseCheck {

 
	public static boolean isLowerCase(String name) {

		if(name.contains("_")) {
			return false;
		}

		if(name.equals(name.toLowerCase())) {
			return true;
		}
		else {
			return false;
		}

	}
	public static boolean isUpperCase(String name) {

		if(name.contains("_")) {
			return false;
		}

		if(name.equals(name.toUpperCase())) {
			return true;
		}
		else {
			return false;
		}

	}
	public static boolean isCamelCase(String name) {
		if(name.contains("_")) {
			return false;
		}
		char[] nameArray = name.toCharArray();
		int imCamel=0;
		if(nameArray[0] >= 'a' && nameArray[0] <='z') {
			imCamel=0;
			for(int i=1; i < nameArray.length; i++) {
				if(nameArray[i] >'A' && nameArray[i]<='Z') {
					imCamel+=1;
				}
			}
			if(imCamel>0) {
				return true;
			}

		}
		else {
			return false;
		}

		return false;

	}
	public static boolean isPascalCase(String name) {
		if(name.contains("_")) {
			return false;
		}
		char[] nameArray = name.toCharArray();
		int imPascal=0;
		if(nameArray[0] > 'A' && nameArray[0] <='Z') {
			imPascal=0;
			for(int i=1; i < nameArray.length; i++) {
				if(nameArray[i] >='a' && nameArray[i]<='z') {
					imPascal+=1;
				}
			}
			if(imPascal>0) {
				return true;
			}

		}
		else {
			return false;
		}

		return false;
	}
	public static boolean isUnderscoreCaseWithLowerCase(String name) {
		if(name.contains("_")) {
			if(name.replace("_", "").toString().equals(name.replace("_", "").toString().toLowerCase().toString()) ){
				return true;

			}
		}
		return false;

	}
	
	public static boolean isOtherCaseWithUnderscore(String name) {
		if(name.contains("_")) {
			if(name.replace("_", "").toString().toLowerCase().equals(name.replace("_", "").toString().toLowerCase().toString()) ){
				return true;

			}
		}
		return false;

	}
	
	public static boolean isUnderscoreCaseWithUpperCase(String name) {
		if(name.contains("_")) {
			if(name.replace("_", "").toString().equals(name.replace("_", "").toString().toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	
	 public static TypeOfCases ReturnCaseType(String name) {
		 try {
		 if(isLowerCase(name)) {
 			 return TypeOfCases.LowerCase;
		 }
		 else if(isCamelCase(name)) {
			 return TypeOfCases.CamelCase;
		 }
		 else if(isUnderscoreCaseWithLowerCase(name)) {
			 return TypeOfCases.LowerCaseWithUnderscore;
		 }
		 else if(isUnderscoreCaseWithUpperCase(name)) {
			 return TypeOfCases.UpperCaseWithUnderscore;
		 }
		 else if(isPascalCase(name)) {
			 return TypeOfCases.PascalCase;
		 }
		 else if(isUpperCase(name)) {
			 return TypeOfCases.UpperCase;
		 }
		 else {
			 return TypeOfCases.OtherCaseWithUnderscore;
		 }
//		 else {
//			 return TypeOfCases.OtherCaseWithRandomCharacter;
//		 }
		 }
		 catch(Exception ex) {
			 System.out.println("ERROR : "+ex.getMessage());
		 }
		return null;
	 }
}
