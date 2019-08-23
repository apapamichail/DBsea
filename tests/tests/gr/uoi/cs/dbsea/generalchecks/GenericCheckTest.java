package tests.gr.uoi.cs.dbsea.generalchecks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.generalchecks.GenericCheck;
import gr.uoi.cs.dbsea.generalchecks.ReservedWords;
import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;

public class GenericCheckTest {

	GenericCheck check;
	@Before
	public void setUp() throws Exception {
		
		check = new GenericCheck();
		ReservedWords.SetReservedWords();
		
	}

	@Test
	public void beginsWithLetterTest_AssertsTrue() {
		
		String names[]= {"12","_","get","Get"};
		assert(!check.beginsWithLetter(names[0]));
		assert(!check.beginsWithLetter(names[1]));
		assert(check.beginsWithLetter(names[2]));
		assert(check.beginsWithLetter(names[3]));
		
	}
	
	@Test
	public void endsWithLetterOrNumber_AssertsTrue() {
		
		String names[]= {"12","_","get","Get"};
		assert(check.endsWithLetterOrNumber(names[0]));
		assert(!check.endsWithLetterOrNumber(names[1]));
		assert(check.endsWithLetterOrNumber(names[2]));
		assert(check.endsWithLetterOrNumber(names[3]));
	}
	
	@Test
	public void containsOnlyLettersNumbersUnderscores_AssertsTrue() {
		
		String names[]= {"12","_","get","Get","ada_@","f+"};
		assert(check.endsWithLetterOrNumber(names[0]));
		assert(!check.endsWithLetterOrNumber(names[1]));
		assert(check.endsWithLetterOrNumber(names[2]));
		assert(check.endsWithLetterOrNumber(names[3]));
		assert(!check.endsWithLetterOrNumber(names[4]));
		assert(!check.endsWithLetterOrNumber(names[5]));
	}
	
	@Test
	public void doesNotContainConsecutiveUnderscores_AssertsTrue() {
		
		String names[]= {"12","_","get","Get","ada_@","f+","-dad__","a__a"};
		assert(!check.containConsecutiveUnderscores(names[0]));
		assert(!check.containConsecutiveUnderscores(names[1]));
		assert(!check.containConsecutiveUnderscores(names[2]));
		assert(!check.containConsecutiveUnderscores(names[3]));
		assert(!check.containConsecutiveUnderscores(names[4]));
		assert(!check.containConsecutiveUnderscores(names[5]));
		assert(check.containConsecutiveUnderscores(names[6]));
		assert(check.containConsecutiveUnderscores(names[7]));

	}
	
	@Test
	public void isReservedAsKeyword_AssertsTrue() {
		
		String names[]= {"CLUSTER","COLUMN","COMMENT"};
		assert(check.isReservedAsKeyword(names[0]));
		assert(check.isReservedAsKeyword(names[1]));
		assert(check.isReservedAsKeyword(names[2]));
	
	}
	
	@Test
	public void containsSpace_AssertsTrue() {
		
		String names[]= {"CLUSTER  ","COLU MN","COLUMN_VALUE"};
		assert(check.containsSpace(names[0]));
		assert(check.containsSpace(names[1]));
		assert(!check.containsSpace(names[2]));

	}
	
	@Test
	public void containsDelimeters_AssertsTrue() {
		
		String names[]= {"CLUSTER","COLU MN","COLUMN_VALUE"};
		assert(check.containsDelimeters(names[0]+'"'));
		assert(!check.containsDelimeters(names[1]));
		assert(!check.containsDelimeters(names[2]));
		
	}
	
	@Test
	public void containsSpecialCharacter_AssertsTrue() {
		
		String names[]= {"313!#$","COLU$N","COLUMN VALUE","COLUMNVALUE"};
		assert(check.containsSpecialCharacter(names[0]+'"'));
		assert(check.containsSpecialCharacter(names[1]));
		assert(check.containsSpecialCharacter(names[2]));
		assert(!check.containsSpecialCharacter(names[3]));
		
	}
	
}
