/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.differencedetection;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.stylecore.SchemaStyleAnalysis;



/**
 * @author Aggelos Papamichail
 * To add more tests you have to change the the mispelled word insersion Skoulis 
 * uses in the transitions.xml to insertion
 */
public class SchemataDifferencesManagerTest {
	
	File schemata;
	// insert more if u like
	public String[] schemaFolder = { "DekiWiki", "biosql" };

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < schemaFolder.length; i++) {
			readSchemaHistory(schemaFolder[i]);
		}

	}

	public void readSchemaHistory(String schemaFolder) throws IOException {
		
		SchemaStyleAnalysis schemaManager = new SchemaStyleAnalysis();
		schemaManager.checkSchemaHistoryStyleByRuleAndExport(new File("tests/schemata/" + schemaFolder + "/schemata"));
		
	}

	@Test
	public void test() {
		try {
			String filesToCheck[] = { "all.csv", "metrics.csv", "table_del.csv", "table_ins.csv", "table_key.csv",
					"table_stats.csv", "table_type.csv", "tables.csv", "transitions.xml" };

			for (int i = 0; i < schemaFolder.length; i++) {
				for (int j = 0; j < filesToCheck.length; j++) {

					BufferedReader rightMetricsReader = new BufferedReader(
							new FileReader("tests/schemata/" + schemaFolder[i] + "/rightresults/" + filesToCheck[j]));
					BufferedReader producedMetricsReader = new BufferedReader(
							new FileReader("tests/schemata/" + schemaFolder[i] + "/results/" + filesToCheck[j]));

					String rightLine = "";
					String producedLine = "";
					String cvsSplitBy = ";";
					int line =0;
					while ((rightLine = rightMetricsReader.readLine()) != null) {
						line +=1;
						if ((producedLine = producedMetricsReader.readLine()) == null) {
							assertNull(producedLine);
						}
						// use comma as separator
						String[] rightMetrics = rightLine.split(cvsSplitBy);
						String[] producedMetrics = producedLine.split(cvsSplitBy);
						for (int i1 = 0; i1 < rightMetrics.length; i1++) {

							if (rightMetrics[i1].equals(producedMetrics[i1]) == false)
								fail("Expected :" + rightMetrics[i1] + ", Found :" + producedMetrics[i1] + " in "
										+ schemaFolder[i] +" line : "+line +" of file : "+filesToCheck[j]);

						}
					}
					rightMetricsReader.close();
					producedMetricsReader.close();

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("FileNotFoundException");

		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}

	public void checkSchemaResults(String schemaFolder) throws IOException {
		BufferedReader rightMetricsReader = new BufferedReader(
				new FileReader("tests/schemata/" + schemaFolder + "rightresults/metrics.csv"));
		BufferedReader producedMetricsReader = new BufferedReader(
				new FileReader("tests/schemata/" + schemaFolder + "/results/metrics.csv"));

		String rightLine = "";
		String producedLine = "";
		String cvsSplitBy = ";";
		while ((rightLine = rightMetricsReader.readLine()) != null) {
			if ((producedLine = producedMetricsReader.readLine()) == null) {
				assertNull(producedLine);
			}
			// use comma as separator
			String[] rightMetrics = rightLine.split(cvsSplitBy);
			String[] producedMetrics = producedLine.split(cvsSplitBy);
			for (int i = 0; i < rightMetrics.length; i++) {
//				System.out.println(rightMetrics[i]);
//				System.out.println(producedMetrics[i]);
				if (rightMetrics[i].equals(producedMetrics[i]) == false)
					fail("Expected :" + rightMetrics[i] + ", Found :" + producedMetrics[i]);

			}
		}

		rightMetricsReader.close();
		producedMetricsReader.close();

	}

}
