/**
 * 
 */
package gr.uoi.cs.dbsea.generalchecks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import gr.uoi.cs.dbsea.columnsstylecheck.UniformSuffixes;
import gr.uoi.cs.dbsea.statistics.GenericCheckStatistics;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;

/**
 * @author Papamichail Aggelos
 * Contains checks common in table names and columns.
 */
public class GenericCheck {

	protected TypeOfCases caseType;
	protected GenericCheckStatistics genericCheckStatistics;
	private final int ProperNameLength = 30;
	public String dataSetName;

	public GenericCheck() {
		genericCheckStatistics = new GenericCheckStatistics();
	}

	public TypeOfCases getCaseType() {
		return caseType;
	}

	public void setCaseType(String name) {

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
	}

	public boolean hasProperLength(String name) {

		if (name.length() < ProperNameLength) {
			genericCheckStatistics.addHasProperLength();
			return true;
		}
		return false;

	}

	public boolean beginsWithLetter(String name) {
		char firstChar = name.toCharArray()[0];
		if ((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z')) {
			genericCheckStatistics.addBeginsWithLetter();
			return true;
		}
		return false;

	}

	public boolean endsWithLetterOrNumber(String name) {

		char lastChar = name.toCharArray()[name.length() - 1];
		if ((lastChar >= 'a' && lastChar <= 'z') || (lastChar >= 'A' && lastChar <= 'Z')
				|| (lastChar >= '0' && lastChar <= '9')) {
			genericCheckStatistics.addEndsWithLetterOrNumber();
			return true;
		}
		return false;
	}

	public boolean containsOnlyLettersNumbersUnderscores(String name) {
		char[] nameArray = name.toCharArray();
		for (char c : nameArray) {
			if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= '0' && c <= '9') && !(c == '_')) {
				return false;
			}
		}
		genericCheckStatistics.addContainsOnlyLettersNumbersUnderscores();
		return true;

	}

	public boolean containConsecutiveUnderscores(String name) {
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

	}

	public boolean isReservedAsKeyword(String name) {

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

	}

	public boolean containsSpace(String name) {
		if (name.contains(" ")) {
			genericCheckStatistics.addContainsSpace();
			return true;
		}
		return false;
	}

	public int countWordsInName(String name) {
		int w=WordnetCheck.wordsInName(name, caseType);
		genericCheckStatistics.addWordsInNames(w);
		return w;
	}

	public int countStringsInName(String name) {
		int s=WordnetCheck.stringsInName(name, caseType);
		genericCheckStatistics.addStringsInNames(s);
		return s;
	}

	public boolean containsDelimeters(String name) {
		if (name.contains("\"")) {
			genericCheckStatistics.addContainsDelimeters();
			return true;
		}
		return false;
	}

	public boolean containsSpecialCharacter(String name) {
		char[] nameArray = name.toCharArray();

		for (char c : nameArray) {
			if (!((c == '_') || !(c <= 'Z' && c >= 'A') || !(c <= 'z' && c >= 'a') || !(c>='0' && c<='9'))) {
				genericCheckStatistics.addContainsSpecialCharacter();
				return true;
			}
		}
		return false;
	}

	public boolean containsVerb(String name) {
		if (WordnetCheck.containsVerb(name, caseType)) {
			genericCheckStatistics.addContainsVerb();
			return true;
		}
		return false;
	}

	public boolean containsNoun(String name) {
		if (WordnetCheck.containsNoun(name, caseType)) {
			genericCheckStatistics.addContainsNoun();
			return true;
		}
		return false;
	}

	public boolean containsAdjective(String name) {
		if (WordnetCheck.containsAdjective(name, caseType)) {
			genericCheckStatistics.addContainsAdjective();
			return true;
		}
		return false;
	}

	public void runChecks(String name) {
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
		int w =countWordsInName(name);
		int s =countStringsInName(name);
		if(w>s-w) {
			genericCheckStatistics.wordsToString+=1;
		}

	}

}
