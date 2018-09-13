package gr.uoi.cs.dbsea.generalchecks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/** 
 * 
 * @author Papamichail Aggelos
 *
 * The class that manipulates and populates the Reserved words 
 * as defined in oracle sql and ms sql.
 */
public  final class ReservedWords {
	
	public static ArrayList<String> oracleReservedWords;
	public static ArrayList<String> microsoftReservedWords;
	static String classpath;
	
	public static void SetReservedWords() {
		setReservedWords() ;
		setOracleReservedWords();
		setMicrosoftReservedWords();
	}
	public static void setReservedWords() {
		classpath = System.getProperty("java.class.path");

	}
	
	public static void setOracleReservedWords() {
		String oracleSqlFilePath = classpath.split(";")[0] + "\\..\\src\\resources\\reservedWordsSqlOracle.txt";

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(oracleSqlFilePath);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			oracleReservedWords = new ArrayList<String>();
			String reservedWord;
			while ((reservedWord= bufferedReader.readLine()) != null) {
				oracleReservedWords.add(reservedWord);
				}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + oracleSqlFilePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + oracleSqlFilePath + "'");

		}
	}
	public static void setMicrosoftReservedWords() {
	
		String microsoftSqlFilePath = classpath.split(";")[0] + "\\..\\src\\resources\\reservedWordsTransactSql.txt";
		try {

			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(microsoftSqlFilePath);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			microsoftReservedWords = new ArrayList<String>();
			String reservedWord;
			while ((reservedWord= bufferedReader.readLine()) != null) {
				microsoftReservedWords.add(reservedWord);
				}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + microsoftSqlFilePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + microsoftSqlFilePath + "'");

		}
	}
	
	public static boolean containsMicrosoftReservedWord(String name) {
	
		for (String string : microsoftReservedWords) {
			if(name.equals(string)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsOracleReservedWord(String name) {
		
		for (String string :oracleReservedWords) {
			if(name.equals(string)) {
				return true;
			}
		}
		return false;
	}
}
