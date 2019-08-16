/**
 * 
 */
package gr.uoi.cs.dbsea.generalchecks;

import gr.uoi.cs.dbsea.logger.Logger;
import gr.uoi.cs.dbsea.statistics.GenericCheckStatistics;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;

/**
 * @author angelo
 *
 */
public class GenericCheck {

	protected WordnetCheck WordnetCheck;
	private CaseCheck CaseCheck;
	private final int ProperNameLength = 30;
	protected TypeOfCases caseType;
	protected GenericCheckStatistics genericCheckStatistics;
	public String dataSetName;

	public GenericCheck() {

		CaseCheck = new CaseCheck();
		WordnetCheck = new WordnetCheck();
		genericCheckStatistics = new GenericCheckStatistics();

	}

	public TypeOfCases getCaseType() {
		return caseType;
	}

	public void setCaseType(String name) {

		try {
			caseType = CaseCheck.ReturnCaseType(name);
			if (caseType == TypeOfCases.CamelCase) {
				genericCheckStatistics.addIsCamelCase();

			} else if (caseType == TypeOfCases.LowerCase) {
				genericCheckStatistics.addIsLowerCase();

			} else if (caseType == TypeOfCases.LowerCaseWithUnderscore) {
				genericCheckStatistics.addIsUnderscoreCaseWithLowerCase();

			} else if (caseType == TypeOfCases.PascalCase) {
				genericCheckStatistics.addIsPascalCase();

			} else if (caseType == TypeOfCases.UpperCase) {
				genericCheckStatistics.addIsUpperCase();

			} else if (caseType == TypeOfCases.UpperCaseWithUnderscore) {
				genericCheckStatistics.addIsUnderscoreCaseWithUpperCase();

			} else if (caseType == TypeOfCases.OtherCaseWithUnderscore) {
				genericCheckStatistics.addIsUnderscoreCaseWithOtherCase();
			}
		} catch (Exception e) {

			Logger.Log(e);

		}
	}

	public boolean hasProperLength(String name) {

		try {
			if (name.length() < ProperNameLength) {
				genericCheckStatistics.addHasProperLength();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}

	}

	public boolean beginsWithLetter(String name) {

		try {
			char firstChar = name.toCharArray()[0];
			if ((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z')) {
				genericCheckStatistics.addBeginsWithLetter();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean endsWithLetterOrNumber(String name) {

		try {
			char lastChar = name.toCharArray()[name.length() - 1];
			if ((lastChar >= 'a' && lastChar <= 'z') || (lastChar >= 'A' && lastChar <= 'Z')
					|| (lastChar >= '0' && lastChar <= '9')) {
				genericCheckStatistics.addEndsWithLetterOrNumber();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	public boolean containsOnlyLettersNumbersUnderscores(String name) {

		try {
			char[] nameArray = name.toCharArray();
			for (char c : nameArray) {
				if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= '0' && c <= '9') && !(c == '_')) {
					return false;
				}
			}
			genericCheckStatistics.addContainsOnlyLettersNumbersUnderscores();
			return true;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containConsecutiveUnderscores(String name) {

		try {
			char[] nameArray = name.toCharArray();
			char prevChar = nameArray[0];
			for (int i = 1; i < nameArray.length; i++) {
				if (prevChar == nameArray[i] && nameArray[i] == '_') {
					genericCheckStatistics.addContainConsecutiveUnderscores();
					return true;
				}
				prevChar = nameArray[i];
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean isReservedAsKeyword(String name) {

		try {
			if (ReservedWords.containsMicrosoftReservedWord(name)) {
				genericCheckStatistics.addIsReservedAsKeyword();
				if (name.equals(name.toUpperCase())) {
					genericCheckStatistics.addIsReservedAsKeywordAndUppercase();

				}
				return true;
			}
			if (ReservedWords.containsOracleReservedWord(name)) {
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsSpace(String name) {

		try {
			if (name.contains(" ")) {
				genericCheckStatistics.addContainsSpace();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	public int countWordsInName(String name) {
		try {
			int w = WordnetCheck.wordsInName(name, caseType);
			genericCheckStatistics.addWordsInNames(w);
			return w;
		} catch (Exception e) {

			Logger.Log(e);
			return -1;
		}
	}

	public int countStringsInName(String name) {
	
		try {
			int s = WordnetCheck.stringsInName(name, caseType);
			genericCheckStatistics.addStringsInNames(s);
			return s;
		} catch (Exception e) {

			Logger.Log(e);
			return -1;
			}
	}

	public boolean containsDelimeters(String name) {
		
		try {
			if (name.contains("\"")) {
				genericCheckStatistics.addContainsDelimeters();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public boolean containsSpecialCharacter(String name) {
		
		try {
			char[] nameArray = name.toCharArray();

			for (char c : nameArray) {
				if ((!(c == '_') && !(c <= 'Z' && c >= 'A') && 
						!(c <= 'z' && c >= 'a') &&
						!(c >= '0' && c <= '9'))) {
					genericCheckStatistics.addContainsSpecialCharacter();
					return true;
				}
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public boolean containsVerb(String name) {
		
		try {
			if (WordnetCheck.containsVerb(name, caseType)) {
				genericCheckStatistics.addContainsVerb();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public boolean containsNoun(String name) {
		
		try {
			if (WordnetCheck.containsNoun(name, caseType)) {
				genericCheckStatistics.addContainsNoun();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public boolean containsAdjective(String name) {
		
		try {
			if (WordnetCheck.containsAdjective(name, caseType)) {
				genericCheckStatistics.addContainsAdjective();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public void runChecks(String name) {

		try {
			setCaseType(name);
			hasProperLength(name);
			beginsWithLetter(name);
			endsWithLetterOrNumber(name);
			containsOnlyLettersNumbersUnderscores(name);
			containConsecutiveUnderscores(name);
			isReservedAsKeyword(name);
			containsSpace(name);
			containsDelimeters(name);
			containsSpecialCharacter(name);
			containsVerb(name);
			containsNoun(name);
			containsAdjective(name);
			int w = countWordsInName(name);
			int s = countStringsInName(name);
			if (w > s - w) {
				genericCheckStatistics.wordsToString += 1;
			}
		} catch (Exception e) {

			Logger.Log(e);
 
		}

	}

}
