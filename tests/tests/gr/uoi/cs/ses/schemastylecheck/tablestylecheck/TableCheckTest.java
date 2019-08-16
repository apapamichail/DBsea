package tests.gr.uoi.cs.ses.schemastylecheck.tablestylecheck;


import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.tablestylecheck.TableCheck;
import gr.uoi.cs.dbsea.tablestylecheck.TablePrefixes;

public class TableCheckTest {
	TableCheck check;
	@Before
	public void setUp() throws Exception {
		
		check=new TableCheck();
		String name="tbl_apples";
		check.setCaseType(name);
		TablePrefixes.setUpListWithPrefixes();
	}

	@Test
	public void test() {
		
		
		 
	}
	
	@Test
	public void containsPrefix_AssertsTrue() {
		
		String tables[]= {"Managertbl","ProTbl","etc","DADA","tblDADA","tbl_DADA"};
		assert(!check.containsPrefix(tables[0]));
		assert(!check.containsPrefix(tables[1]));
		assert(!check.containsPrefix(tables[2]));
		assert(!check.containsPrefix(tables[3]));
		assert(check.containsPrefix(tables[4]));
		assert(check.containsPrefix(tables[5]));

	}
	
	@Test
	public void  startsWithCapital_AssertsTrue() {
		
		String tables[]= {"Manager","Pro","etc"};
		assert(check.startWithCapital( tables[0]));
		assert(check.startWithCapital( tables[1]));
		assert(!check.startWithCapital( tables[2]));
	}
	
	@Test
	public void containsVerb_AssertsTrue() {
		
		String tables[]= {"Manager","get","have","apple"};
		assert(!check.containsVerb( tables[0]));
		assert(check.containsVerb( tables[1]));
		assert(check.containsVerb( tables[2]));
		assert(!check.containsVerb( tables[3]));

	}
}
