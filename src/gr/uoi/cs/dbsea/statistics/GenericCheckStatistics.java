package gr.uoi.cs.dbsea.statistics;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math3.util.Precision;


public class GenericCheckStatistics {

	private int setCaseType;
	private int hasProperLength;
	private int beginsWithLetter;
	private int endsWithLetterOrNumber;
	private int containsOnlyLettersNumbersUnderscores;
	private int containConsecutiveUnderscores;
	private int isReservedAsKeyword;
	private int containsSpace;
	private int containsSpecialCharacter;
	private int containsVerb;
	private int containsSingular;
	private int containsPlural;
	private int stringsInNames;
	private int wordsInNames;

	private int isLowerCase;
	private int isUpperCase;
	private int isCamelCase;
	private int isPascalCase;
	private int isUnderscoreCaseWithLowerCase;
	private int isUnderscoreCaseWithUpperCase;
	private int UnderscoreCaseWithOtherCase;

	private int containsDelimeters;
	private int containsAdjective;
	private int containsNoun;
	private int containsPluralAndNotSingular;

	protected String fileGenericPath;
	protected FileWriter fw;
	private int IsReservedAsKeywordAndUppercase;
	public int wordsToString;

	public void SetGenericFile(String path) {
		this.fileGenericPath = path;
		try {
			fw = new FileWriter(fileGenericPath, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GenericCheckStatistics() {

		this.setCaseType = 0;
		this.hasProperLength = 0;
		this.beginsWithLetter = 0;
		this.endsWithLetterOrNumber = 0;
		this.containsOnlyLettersNumbersUnderscores = 0;
		this.containConsecutiveUnderscores = 0;
		this.isReservedAsKeyword = 0;
		this.containsSpace = 0;
		this.containsSpecialCharacter = 0;
		this.containsVerb = 0;
		this.containsSingular = 0;
		this.containsPlural = 0;
		this.stringsInNames = 0;
		this.wordsInNames = 0;
		this.isLowerCase = 0;
		this.isUpperCase = 0;
		this.isCamelCase = 0;
		this.isPascalCase = 0;
		this.isUnderscoreCaseWithLowerCase = 0;
		this.isUnderscoreCaseWithUpperCase = 0;
		this.containsDelimeters = 0;
		this.containsAdjective = 0;
		this.containsNoun = 0;
		this.containsPluralAndNotSingular = 0;
		this.UnderscoreCaseWithOtherCase = 0;
		this.IsReservedAsKeywordAndUppercase = 0;
		wordsToString = 0;
	}

	public void ClearStatistics() {
		//
		this.setCaseType = 0;
		this.hasProperLength = 0;
		this.beginsWithLetter = 0;
		this.endsWithLetterOrNumber = 0;
		this.containsOnlyLettersNumbersUnderscores = 0;
		this.containConsecutiveUnderscores = 0;
		this.isReservedAsKeyword = 0;
		this.containsSpace = 0;
		this.containsSpecialCharacter = 0;
		this.containsVerb = 0;
		this.containsSingular = 0;
		this.containsPlural = 0;
		this.stringsInNames = 0;
		this.wordsInNames = 0;
		this.isLowerCase = 0;
		this.isUpperCase = 0;
		this.isCamelCase = 0;
		this.isPascalCase = 0;
		this.isUnderscoreCaseWithLowerCase = 0;
		this.isUnderscoreCaseWithUpperCase = 0;
		this.containsDelimeters = 0;
		this.containsAdjective = 0;
		this.containsNoun = 0;
		this.containsPluralAndNotSingular = 0;
		this.UnderscoreCaseWithOtherCase = 0;
		this.IsReservedAsKeywordAndUppercase = 0;
		wordsToString = 0;
	}

	public void closeStream() throws IOException {
		fw.close();
	}

	public void SetGenericFileTitleBad(String myFilePath) throws IOException {
		fw = new FileWriter(myFilePath, true);
		fw.append("ACC,");//CamelCase
		fw.append("ARW,");//Is reserved keyword
		fw.append("ACU,");//Consecutive underscores
		fw.append("AUS,");//Space
		fw.append("ASC,");//Special character
		fw.append("AUD,");//Delimeters
		fw.append("AUV,");//Verb
		fw.close();

	}

	public void SetGenericFileTitleGood(String myFilePath) throws IOException {
		fw = new FileWriter(myFilePath);
		// fw.append("Informative,");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append("Good,");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append("Bad,");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append("\n");
		fw.append("Total objects,");
		// fw.append("Name contains Verb,");
		// fw.append("Name contains Noun,");
		// fw.append("Name contains Adjective,");
		// fw.append("Number of strings in Name,");
		// fw.append("containsSingular,");
		// fw.append("containsPlural,");
		// fw.append("Number of words in Name,");
		fw.append("Lower case,");
		fw.append("Upper case,");
		fw.append("Pascal case,");
		fw.append("Underscore case with lower case,");
		fw.append("Underscore Case with upper case,");
		fw.append("UnderscoreCase with some other case,");
		fw.append("UTC,");
		// fw.append("Name contains Plural and not Singular,");
		fw.append("UPL,");
		fw.append("SWL,");
		fw.append("EWL,");
		fw.append("UMW,");

		// fw.append("datasetName\n");

		fw.close();
	}

	public void WriteStatisticsToGenericBadFile(String myFilePath, double totalObjects, String datasetName)
			throws IOException {
		fw = new FileWriter(myFilePath, true);
		if (isCamelCase > 0) {
			int a = 1;
		}
		fw.append(GetValueWithDoublePrecision(Math.abs((isCamelCase / totalObjects)-1) )+ ",");
		fw.append(GetValueWithDoublePrecision(Math.abs((isReservedAsKeyword / totalObjects)-1) )+ ",");
		fw.append(GetValueWithDoublePrecision(Math.abs((containConsecutiveUnderscores / totalObjects)-1))+ ",");
		fw.append(GetValueWithDoublePrecision(Math.abs(((containsSpace / totalObjects)-1))) + ",");
		fw.append(GetValueWithDoublePrecision(Math.abs(((containsSpecialCharacter / totalObjects )-1)))+ ",");
		fw.append(GetValueWithDoublePrecision(Math.abs(((containsDelimeters / totalObjects)-1))) + ",");
		fw.append(GetValueWithDoublePrecision(Math.abs(((containsVerb / totalObjects)-1))) + ",");
		fw.close();

	}

	public void WriteStatisticsToGenericFileGood(String myFilePath, double totalObjects, String datasetName)
			throws IOException {

		// fw = new BufferedWriter(fw);
		fw = new FileWriter(myFilePath, true);
		fw.append(totalObjects + ",");
		
		fw.append(GetValueWithDoublePrecision(isLowerCase / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(isUpperCase / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(isPascalCase / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(isUnderscoreCaseWithLowerCase / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(isUnderscoreCaseWithUpperCase / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(UnderscoreCaseWithOtherCase / totalObjects) + ",");

		double universalTypeOfcase = GetUniversalTypeOfCase(totalObjects);
		if (universalTypeOfcase < 1.0) {
			int a = 1;
		}
		fw.append(GetValueWithDoublePrecision(universalTypeOfcase)+ ",");
		fw.append(GetValueWithDoublePrecision(hasProperLength / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(beginsWithLetter / totalObjects )+ ",");
		fw.append(GetValueWithDoublePrecision(endsWithLetterOrNumber / totalObjects) + ",");
		fw.append(GetValueWithDoublePrecision(wordsToString / totalObjects) + ",");
		fw.close();

	}
	private double GetValueWithDoublePrecision(double value) {
		return 	Precision.round(value, 2);

	}
	// public void WriteStatisticsToGenericFile(double totalObjects,String
	// datasetName) throws IOException {
	//
	// // fw = new BufferedWriter(fw);
	// fw = new FileWriter(fileGenericPath, true);
	// fw.append(totalObjects+",");
	// fw.append(containsVerb/totalObjects+",");
	// fw.append(containsNoun/totalObjects+",");
	// fw.append(containsAdjective/totalObjects+",");
	// fw.append(stringsInNames/totalObjects+",");
	//// fw.append(containsSingular/totalObjects+",");
	//// fw.append(containsPlural/totalObjects+",");
	// fw.append(wordsInNames/totalObjects+",");
	// fw.append(isLowerCase/totalObjects+",");
	// fw.append(isUpperCase/totalObjects+",");
	// fw.append(isPascalCase/totalObjects+",");
	// fw.append(isUnderscoreCaseWithLowerCase/totalObjects+",");
	// fw.append(isUnderscoreCaseWithUpperCase/totalObjects+",");
	// fw.append(UnderscoreCaseWithOtherCase/totalObjects+",");
	// fw.append(containsPluralAndNotSingular/totalObjects+",");
	// fw.append(hasProperLength/totalObjects+",");
	// fw.append(beginsWithLetter/totalObjects+",");
	// fw.append(endsWithLetterOrNumber/totalObjects+",");
	// fw.append(containsOnlyLettersNumbersUnderscores/totalObjects+",");
	// fw.append(containConsecutiveUnderscores/totalObjects+",");
	// fw.append(isReservedAsKeyword/totalObjects+",");
	// fw.append(containsSpace/totalObjects+",");
	// fw.append(containsSpecialCharacter/totalObjects+",");
	// fw.append(containsDelimeters/totalObjects+",");
	// fw.append(isCamelCase/totalObjects+",");
	// fw.append(datasetName+"\n");
	// fw.close();
	//
	// }
	// public void WriteStatisticsToGenericFile(int totalObjects) throws IOException
	// {
	//
	// // fw = new BufferedWriter(fw);
	// fw = new FileWriter(fileGenericPath, true);
	// fw.append(totalObjects+",");
	// fw.append(containsVerb+",");
	// fw.append(containsNoun+",");
	// fw.append(containsAdjective+",");
	// fw.append(stringsInNames+",");
	//// fw.append(containsSingular+",");
	//// fw.append(containsPlural+",");
	// fw.append(wordsInNames+",");
	// fw.append(isLowerCase+",");
	// fw.append(isUpperCase+",");
	// fw.append(isPascalCase+",");
	// fw.append(isUnderscoreCaseWithLowerCase+",");
	// fw.append(isUnderscoreCaseWithUpperCase+",");
	// fw.append(UnderscoreCaseWithOtherCase+",");
	// fw.append(containsPluralAndNotSingular+",");
	// fw.append(hasProperLength+",");
	// fw.append(beginsWithLetter+",");
	// fw.append(endsWithLetterOrNumber+",");
	// fw.append(containsOnlyLettersNumbersUnderscores+",");
	// fw.append(containConsecutiveUnderscores+",");
	// fw.append(isReservedAsKeyword+",");
	// fw.append(containsSpace+",");
	// fw.append(containsSpecialCharacter+",");
	// fw.append(containsDelimeters+",");
	// fw.append(isCamelCase+"\n");
	// fw.close();
	//
	// }

	private double GetUniversalTypeOfCase(double totalObjects) {
		double universalTypeOfcase = 0;
		double lower = (isLowerCase / totalObjects) + (isUnderscoreCaseWithLowerCase / totalObjects);
		double upper = (isUpperCase / totalObjects) + (isUnderscoreCaseWithUpperCase / totalObjects);

		if (lower < upper) {
			universalTypeOfcase = upper;
		} else {
			universalTypeOfcase = lower;
		}

		if (universalTypeOfcase < isCamelCase / totalObjects) {
			universalTypeOfcase = isCamelCase / totalObjects;
		}
		if (universalTypeOfcase < isPascalCase / totalObjects) {
			universalTypeOfcase = isPascalCase / totalObjects;
		}

		if (universalTypeOfcase < UnderscoreCaseWithOtherCase / totalObjects) {
			universalTypeOfcase = UnderscoreCaseWithOtherCase / totalObjects;
		}
		if (totalObjects > 50) {
			int b = 1;
		}
		if (universalTypeOfcase != 1
				&& (isCamelCase + isPascalCase + isLowerCase + isUpperCase + isUnderscoreCaseWithUpperCase
						+ isUnderscoreCaseWithLowerCase + UnderscoreCaseWithOtherCase) != (int) totalObjects) {

			return 1;
		}
		return universalTypeOfcase;
	}

	public int getContainsPluralAndNotSingular() {
		return containsPluralAndNotSingular;
	}

	public void addContainsOnlySingular() {
		containsPluralAndNotSingular += 1;
	}

	public int getContainsDelimeters() {
		return containsDelimeters;
	}

	public void addContainsDelimeters() {
		containsDelimeters += 1;
	}

	public int getIsLowerCase() {
		return isLowerCase;
	}

	public void addIsLowerCase() {
		this.isLowerCase += 1;
	}

	public int getIsUpperCase() {
		return isUpperCase;
	}

	public void addIsUpperCase() {
		this.isUpperCase += 1;
	}

	public int getIsCamelCase() {
		return isCamelCase;
	}

	public void addIsCamelCase() {
		this.isCamelCase += 1;
	}

	public int getIsPascalCase() {
		return isPascalCase;
	}

	public void addIsPascalCase() {
		this.isPascalCase += 1;
	}

	public int getIsUnderscoreCaseWithLowerCase() {
		return isUnderscoreCaseWithLowerCase;
	}

	public void addIsUnderscoreCaseWithLowerCase() {
		this.isUnderscoreCaseWithLowerCase += 1;
	}

	public int getIsUnderscoreCaseWithUpperCase() {
		return isUnderscoreCaseWithUpperCase;
	}

	public void addIsUnderscoreCaseWithUpperCase() {
		this.isUnderscoreCaseWithUpperCase += 1;
	}

	public void addIsUnderscoreCaseWithOtherCase() {
		this.UnderscoreCaseWithOtherCase += 1;
	}

	public int getIsUnderscoreCaseWithOtherCase() {
		return UnderscoreCaseWithOtherCase;
	}

	public int getSetCaseType() {
		return setCaseType;
	}

	public void addSetCaseType() {
		setCaseType += 1;
	}

	public int getHasProperLength() {
		return hasProperLength;
	}

	public void addHasProperLength() {
		this.hasProperLength += 1;
	}

	public int getBeginsWithLetter() {
		return beginsWithLetter;
	}

	public void addBeginsWithLetter() {
		this.beginsWithLetter += 1;
	}

	public int getEndsWithLetterOrNumber() {
		return endsWithLetterOrNumber;
	}

	public void addEndsWithLetterOrNumber() {
		this.endsWithLetterOrNumber += 1;
	}

	public int getContainsOnlyLettersNumbersUnderscores() {
		return containsOnlyLettersNumbersUnderscores;
	}

	public void addContainsOnlyLettersNumbersUnderscores() {
		this.containsOnlyLettersNumbersUnderscores += 1;
	}

	public int getDoesNotContainConsecutiveUnderscores() {
		return containConsecutiveUnderscores;
	}

	public void addContainConsecutiveUnderscores() {
		this.containConsecutiveUnderscores += 1;
	}

	public int getIsReservedAsKeyword() {
		return isReservedAsKeyword;
	}

	public void addIsReservedAsKeyword() {
		this.isReservedAsKeyword += 1;
	}

	public void addIsReservedAsKeywordAndUppercase() {
		this.IsReservedAsKeywordAndUppercase += 1;

	}

	public int getContainsSpace() {
		return containsSpace;
	}

	public void addContainsSpace() {
		this.containsSpace += 1;
	}

	public int getContainsSpecialCharacter() {
		return containsSpecialCharacter;
	}

	public void addContainsSpecialCharacter() {
		this.containsSpecialCharacter += 1;
	}

	public int getContainsVerb() {
		return containsVerb;
	}

	public void addContainsVerb() {
		this.containsVerb += 1;
	}

	public int getContainsNoun() {
		return containsNoun;
	}

	public void addContainsNoun() {
		this.containsNoun += 1;
	}

	public int getContainAdjective() {
		return containsAdjective;
	}

	public void addContainsAdjective() {
		this.containsAdjective += 1;
	}

	// public int getIsSingular() {
	// return isSingular;
	// }
	//
	// public void addIsSingular() {
	// this.isSingular += 1;
	// }
	//
	// public int getIsPlural() {
	// return isPlural;
	// }
	//
	// public void addIsPlural() {
	// this.isPlural += 1;
	// }
	public void addContainsSingular() {
		this.containsSingular += 1;
	}

	public void addContainsPlural() {
		this.containsPlural += 1;
	}

	public int getContainsSingular() {
		return this.containsSingular;
	}

	public int getContainsPlural() {
		return this.containsPlural;
	}

	public int getStringsInNames() {
		return stringsInNames;
	}

	public void addStringsInNames(int stringsInNames) {
		this.stringsInNames += stringsInNames;
	}

	public void addStringsInNames() {
		this.stringsInNames += 1;
	}

	public int getWordsInNames() {
		return wordsInNames;
	}

	public void addWordsInNames(int wordsInNames) {
		this.wordsInNames += wordsInNames;
	}

	public void addWordsInNames() {
		this.wordsInNames += 1;

	}

	public void writeNextLine(String myFilePath) throws IOException {
		fw = new FileWriter(myFilePath, true);
		fw.append("\n");
		fw.close();
	}

}
