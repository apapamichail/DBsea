package tests.gr.uoi.cs.dbsea.stylecore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import gr.uoi.cs.dbsea.stylecore.SchemaStyleAnalysis;

public class SchemaStyleAnalysisTest {
	// Changing the name conventions in SchemaStyleAnalysis could
	// break this test

	SchemaStyleAnalysis analyzer;

	@Before
	public void SetUp() throws IOException {

		analyzer = new SchemaStyleAnalysis();
		File clearResults = new File("Results\\RuleLevelAnalysis\\ColumnStatistics");

		for (String file : clearResults.list()) {
			Files.delete(GetPath(clearResults, file));
		}

		clearResults = new File("Results\\RuleLevelAnalysis\\TableStatistics");
		for (String file : clearResults.list()) {
			Files.delete(GetPath(clearResults, file));
		}

		clearResults = new File("Results\\SchemaLevelAnalysis\\ColumnStatistics");

		for (String file : clearResults.list()) {
			Files.delete(GetPath(clearResults, file));
		}

		clearResults = new File("Results\\SchemaLevelAnalysis\\TableStatistics");
		for (String file : clearResults.list()) {
			Files.delete(GetPath(clearResults, file));
		}

		File folder = new File("tests\\tests\\gr\\uoi\\cs\\dbsea\\stylecore\\XOOPS\\schemata");
		try {

			analyzer.checkSchemaHistoryStyleByRuleAndExport(folder);
			analyzer.checkSchemaHistoryStyleByTableAndExport(folder);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void RunAnalysisOnXOOPS_CompareResultsWithExistingOnes_AreEqual() throws FileNotFoundException {

		ResultsFilesAreEqual("Results\\RuleLevelAnalysis\\ColumnStatistics\\",
				"tests\\tests\\gr\\uoi\\cs\\dbsea\\stylecore\\RuleLevelAnalysis\\ColumnStatistics");

		ResultsFilesAreEqual("Results\\RuleLevelAnalysis\\TableStatistics\\",
				"tests\\tests\\gr\\uoi\\cs\\dbsea\\stylecore\\RuleLevelAnalysis\\TableStatistics");

		ResultsFilesAreEqual("Results\\SchemaLevelAnalysis\\ColumnStatistics\\",
				"tests\\tests\\gr\\uoi\\cs\\dbsea\\stylecore\\SchemaLevelAnalysis\\ColumnStatistics");

		ResultsFilesAreEqual("Results\\SchemaLevelAnalysis\\TableStatistics\\",
				"tests\\tests\\gr\\uoi\\cs\\dbsea\\stylecore\\SchemaLevelAnalysis\\TableStatistics");

	}

	private Path GetPath(File clearResults, String file) {
		return (new File(clearResults.getAbsolutePath() + "\\" + file)).toPath();
	}

	private void ResultsFilesAreEqual(String path, String pathStable) throws FileNotFoundException {

		File underTestResultsColumns = new File(path);
		File stableVersion = new File(pathStable);

		String xoopsFileName = null;
		String xoopsFileNameStable = null;

		xoopsFileName = GetFileName(underTestResultsColumns, "XOOPS");

		assertNotNull(xoopsFileName);

		File underTestVersion = new File(path + "\\" + xoopsFileName);

		xoopsFileNameStable = GetFileName(stableVersion, "XOOPS");

		stableVersion = new File(pathStable + "\\" + xoopsFileNameStable);

		assertNotNull(xoopsFileNameStable);
		assertEquals(underTestVersion.length(), stableVersion.length());

		AssertThroughScanners(stableVersion, underTestVersion);

		// assert the statistics derived from the schemata
		underTestResultsColumns = new File(path);
		stableVersion = new File(pathStable);

		xoopsFileName = GetFileName(underTestResultsColumns, "Dataset");

		assertNotNull(xoopsFileName);

		underTestVersion = new File(path + "\\" + xoopsFileName);

		xoopsFileNameStable = GetFileName(stableVersion, "Dataset");

		stableVersion = new File(pathStable + "\\" + xoopsFileNameStable);
		assertNotNull(xoopsFileNameStable);
		assertEquals(underTestVersion.length(), stableVersion.length());
		AssertThroughScanners(stableVersion, underTestVersion);

	}

	private void AssertThroughScanners(File stableVersion, File underTestVersion) throws FileNotFoundException {
		Scanner scannerStable = new Scanner(stableVersion);
		Scanner scannerUnderTest = new Scanner(underTestVersion);

		while (scannerStable.hasNext()) {
			assertEquals(scannerStable.next(), scannerUnderTest.next());
		}

		scannerStable.close();
		scannerUnderTest.close();
	}

	private String GetFileName(File stableVersion, String keyWord) {
		String fileName = null;
		for (String file : stableVersion.list()) {
			if (file.contains(keyWord)) {
				fileName = file;
				break;
			}
		}
		return fileName;
	}
}
