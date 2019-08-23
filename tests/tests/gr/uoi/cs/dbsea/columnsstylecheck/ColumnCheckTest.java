package tests.gr.uoi.cs.dbsea.columnsstylecheck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.columnsstylecheck.ColumnCheck;
import gr.uoi.cs.dbsea.columnsstylecheck.UniformSuffixes;
import gr.uoi.cs.dbsea.generalchecks.ReservedWords;

public class ColumnCheckTest {
	
	ColumnCheck check;

	@Before
	public void setUp() throws Exception {
		
		check = new ColumnCheck();
		ReservedWords.SetReservedWords();
		UniformSuffixes.SetUpListWithSuffixes();
		
	}

	@Test
	public void containsPluralCheck_IfPlular_AssertTrue() {
		
		String names[]= {"apples","apple","cousins","cousin"};
		check.setCaseType(names[0]);
		assert(check.containsPlural(names[0]));
		assert(!check.containsPlural(names[1]));
		assert(check.containsPlural(names[2]));
		assert(!check.containsPlural(names[3]));

	}

	@Test
	public void containsSingularCheck_IfSingular_AssertTrue() {

		String names[]= {"apples","apple","cousins","cousin","alias"};
		check.setCaseType(names[0]);

		assert(!check.containsSingular(names[0]));
		assert(check.containsSingular(names[1]));
		assert(!check.containsSingular(names[2]));
		assert(check.containsSingular(names[3]));
		assert(!check.containsSingular(names[4]));

	}

	@Test
	public void isIdPrimaryIdentifierTest_IDAsIdentifier_AssertsTrue() {

		String names[]= {"get","id","Id","getId"};
		assert(!check.isIdPrimaryIdentifier(names[0]));
		assert(check.isIdPrimaryIdentifier(names[1]));
		assert(check.isIdPrimaryIdentifier(names[2]));
		assert(!check.isIdPrimaryIdentifier(names[3]));

	}

	@Test
	public void containsSingularAndNotPluralTest_OnlySingular_AssertsTrue() {
	
		String names[]= {"get","apple","call","calls"};
		check.setCaseType(names[0]);

		assert(!check.containsSingularAndNotPlural(names[0]));
		check.setCaseType(names[1]);
		assert(check.containsSingularAndNotPlural(names[1]));
		check.setCaseType(names[2]);
		assert(check.containsSingularAndNotPlural(names[2]));
		check.setCaseType(names[3]);
		assert(!check.containsSingularAndNotPlural(names[3]));

	}

	@Test
	public void containsUniformSuffixTest_UniformSuffix_AssertsTrue() {

		String names[]= {"get","apple","call","calls","_id","apple_tally","abortion_date"};
		assert(!check.containsUniformSuffix(names[0]));
		assert(!check.containsUniformSuffix(names[1]));
		assert(!check.containsUniformSuffix(names[2]));
		assert(!check.containsUniformSuffix(names[3]));
		assert(check.containsUniformSuffix(names[4]));
		assert(check.containsUniformSuffix(names[5]));
		assert(check.containsUniformSuffix(names[6]));

	}
}
