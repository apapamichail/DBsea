package tests.gr.uoi.cs.dbsea.wordnetchecks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;

public class WordnetCheckTest {

	WordnetCheck wordnetCheck;

	@Before
	public void setUp() throws Exception {

		wordnetCheck = new WordnetCheck();

	}


	@Test
	public void testBrokeWordToPieces() {

		String[] upper= wordnetCheck.brokeWordToPieces("UPPERCASE", TypeOfCases.UpperCase);

		if(upper.length!=1) {
			fail("upper");
		}

		String[] CamelCase=wordnetCheck.brokeWordToPieces("camelCaseCamel", TypeOfCases.CamelCase);

		if(CamelCase.length!=3) {
			fail("CamelCase");
		}

		String[] PascalCase=wordnetCheck.brokeWordToPieces("PascalCasePascal", TypeOfCases.PascalCase);

		if(PascalCase.length!=3) {
			fail("PascalCase");
		}

		String[] UpperCaseWithUnderscore=wordnetCheck.brokeWordToPieces("UPPER_CASE", TypeOfCases.UpperCaseWithUnderscore);

		if(UpperCaseWithUnderscore.length!=2) {
			fail("UpperCaseWithUnderscore");
		}

		String[] LowerCaseWithUnderscore=wordnetCheck.brokeWordToPieces("LOWER_CASE", TypeOfCases.LowerCaseWithUnderscore);

		if(LowerCaseWithUnderscore.length!=2) {
			fail("LowerCaseWithUnderscore");
		}

		String[] LowerCase=wordnetCheck.brokeWordToPieces("lowercase", TypeOfCases.LowerCase);

		if(LowerCase.length!=1) {
			fail("LowerCase");
		}

		assert(true);
	}

	@Test
	public void testContainsSingular() {

		if(wordnetCheck.containsSingular("apples", TypeOfCases.LowerCase)) {
			fail("apples");			
		}

		if(!wordnetCheck.containsSingular("appleApples", TypeOfCases.CamelCase)) {
			fail("appleApples");			
		}

		if(!wordnetCheck.containsSingular("ApplesApple", TypeOfCases.PascalCase)) {
			fail("ApplesApple");			
		}

		if(!wordnetCheck.containsSingular("apple_apple", TypeOfCases.LowerCaseWithUnderscore)) {
			fail("apple_apples");			
		}

		if(!wordnetCheck.containsSingular("APPLES_APPLE", TypeOfCases.UpperCaseWithUnderscore)) {
			fail("APPLES_APPLE");			
		}

		if(wordnetCheck.containsSingular("APPLES", TypeOfCases.UpperCase)) {
			fail("APPLES");			
		}

		if(wordnetCheck.containsSingular("AAAAAAAAAA", TypeOfCases.UpperCase)) {
			fail("AAAAAAAAAA");			
		}
		assert(true);

	}

	@Test
	public void testContainsPlural() {
		
		if(wordnetCheck.containsPlural("apple", TypeOfCases.LowerCase)) {
			fail("apples");			
		}
		
		if(!wordnetCheck.containsPlural("appleApples", TypeOfCases.CamelCase)) {
			fail("appleApples");			
		}
		
		if(wordnetCheck.containsPlural("AppleApple", TypeOfCases.PascalCase)) {
			fail("AppleApple");			
		}
		
		if(!wordnetCheck.containsPlural("apple_apples", TypeOfCases.LowerCaseWithUnderscore)) {
			fail("apple_apples");			
		}
		
		if(!wordnetCheck.containsPlural("APPLES_APPLE", TypeOfCases.UpperCaseWithUnderscore)) {
			fail("APPLES_APPLE");			
		}
		
		if(!wordnetCheck.containsPlural("APPLES", TypeOfCases.UpperCase)) {
			fail("APPLES");			
		}
		
		if(wordnetCheck.containsPlural("AAAAAAAAAA", TypeOfCases.UpperCase)) {
			fail("AAAAAAAAAA");			
		}
		
		assert(true);

	}


	@Test
	public void testContainsVerb() {
		
		if(wordnetCheck.containsVerb("AAAAAAAAAA", TypeOfCases.UpperCase)) {
			fail("AAAAAAAAAA");			
		}
		if(!wordnetCheck.containsVerb("ManagerGet", TypeOfCases.PascalCase)) {
			fail("AAAAAAAAAA");			
		}

		if(wordnetCheck.containsVerb("ManagerGat", TypeOfCases.PascalCase)) {
			fail("AAAAAAAAAA");			
		}

		assert(true);

	}

}
