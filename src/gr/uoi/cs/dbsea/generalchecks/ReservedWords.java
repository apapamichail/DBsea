package gr.uoi.cs.dbsea.generalchecks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import gr.uoi.cs.dbsea.logger.Logger;

public final class ReservedWords {

	public static ArrayList<String> oracleReservedWords;
	public static ArrayList<String> microsoftReservedWords;
	static String classpath;

	public static void SetReservedWords() {
		setReservedWords();
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
			while ((reservedWord = bufferedReader.readLine()) != null) {
				oracleReservedWords.add(reservedWord);
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException e) {

			Logger.Log("Unable to open file '" + oracleSqlFilePath + "'", e);

		} catch (IOException e) {

			Logger.Log("Error reading file '" + oracleSqlFilePath + "'", e);

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
			while ((reservedWord = bufferedReader.readLine()) != null) {
				microsoftReservedWords.add(reservedWord);
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException e) {
		
			Logger.Log("Unable to open file '" + microsoftSqlFilePath + "'",e);
	
		} catch (IOException e) {
			
			Logger.Log("Error reading file '" + microsoftSqlFilePath + "'",e);
	
		}
	}

	public static boolean containsMicrosoftReservedWord(String name) {

		try {
			for (String string : microsoftReservedWords) {
				if (name.equals(string)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			
			Logger.Log(e);
			return false;
			
		}
	}

	public static boolean containsOracleReservedWord(String name) {

		try {
			for (String string : oracleReservedWords) {
				if (name.equals(string)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			
			Logger.Log(e);
			return false;
			
		}
	}
}
