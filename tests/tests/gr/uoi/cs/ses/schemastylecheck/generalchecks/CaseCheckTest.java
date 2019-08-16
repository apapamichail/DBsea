package tests.gr.uoi.cs.ses.schemastylecheck.generalchecks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.generalchecks.CaseCheck;

public class CaseCheckTest {
	CaseCheck check;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void CheckCase_AllCasesCheck_AreCorrect() {
		check = new CaseCheck();
		
		if (!check.isLowerCase("lowercase")) {
			fail("lowercase");
		}
		
		if (check.isLowerCase("lower_case")) {
			fail("lower_case");
		}
		
		if (check.isLowerCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (check.isLowerCase("camelCase")) {
			fail("UPPERCASE");
		}
		
		if (check.isLowerCase("PascalCase")) {
			fail("UPPERCASE");
		}
		
		// ---- camel Case
		if (check.isCamelCase("lowercase")) {
			fail("lowercase");
		}
		
		if (check.isCamelCase("lower_case")) {
			fail("lower_case");
		}
		if (check.isCamelCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (!check.isCamelCase("camelCase")) {
			fail("camelCase");
		}
		
		if (check.isCamelCase("PascalCase")) {
			fail("PascalCase");
		}
		// ---- Pascal Case
		if (check.isPascalCase("lowercase")) {
			fail("lowercase");
		}
		
		if (check.isPascalCase("lower_case")) {
			fail("lower_case");
		}
		
		if (check.isPascalCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (check.isPascalCase("camelCase")) {
			fail("camelCase");
		}
		
		if (!check.isPascalCase("PascalCase")) {
			fail("PascalCase");
		}
		
		// ---- UpperCase
		if (check.isUpperCase("lowercase")) {
			fail("lowercase");
		}
		
		if (check.isUpperCase("lower_case")) {
			fail("lower_case");
		}
		
		if (!check.isUpperCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (check.isUpperCase("camelCase")) {
			fail("camelCase");
		}
		
		if (check.isUpperCase("PascalCase")) {
			fail("PascalCase");
		}
		
		
		// ---- Lower Case With underscore
		if (check.isUnderscoreCaseWithLowerCase("lowercase")) {
			fail("lowercase");
		}
		
		if (!check.isUnderscoreCaseWithLowerCase("lower_case")) {
			fail("lower_case");
		}
		
		if (check.isUnderscoreCaseWithLowerCase("UPPER_CASE")) {
			fail("lower_case");
		}
		
		if (check.isUnderscoreCaseWithLowerCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (check.isUnderscoreCaseWithLowerCase("camelCase")) {
			fail("camelCase");
		}
		
		if (check.isUnderscoreCaseWithLowerCase("PascalCase")) {
			fail("PascalCase");
		}
		
		// ---- UPPER CASE With underscore
		if (check.isUnderscoreCaseWithUpperCase("lowercase")) {
			fail("lowercase");
		}
		
		if (check.isUnderscoreCaseWithUpperCase("lower_case")) {
			fail("lower_case");
		}
		
		if (!check.isUnderscoreCaseWithUpperCase("UPPER_CASE")) {
			fail("lower_case");
		}
		
		if (check.isUnderscoreCaseWithUpperCase("UPPERCASE")) {
			fail("UPPERCASE");
		}
		
		if (check.isUnderscoreCaseWithUpperCase("camelCase")) {
			fail("camelCase");
		}
		
		if (check.isUnderscoreCaseWithUpperCase("PascalCase")) {
			fail("PascalCase");
		}
		assert(true);
	}

}
