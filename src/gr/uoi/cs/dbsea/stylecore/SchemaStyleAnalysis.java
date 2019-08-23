package gr.uoi.cs.dbsea.stylecore;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import gr.uoi.cs.dbsea.columnsstylecheck.UniformSuffixes;
import gr.uoi.cs.dbsea.generalchecks.ReservedWords;
import gr.uoi.cs.dbsea.logger.Logger;
import gr.uoi.cs.dbsea.parser.SESParser;
import gr.uoi.cs.dbsea.sql.Attribute;
import gr.uoi.cs.dbsea.sql.Schema;
import gr.uoi.cs.dbsea.sql.Table;
import gr.uoi.cs.dbsea.statistics.DatasetStatistics;
import gr.uoi.cs.dbsea.tablestylecheck.TablePrefixes;

public class SchemaStyleAnalysis {

	/**
	 * @param result
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	public void checkSchemaHistoryStyleByRuleAndExport(File folder) throws IOException {
		try {

			AnalysisTools analysisTools = new AnalysisTools(folder.getAbsolutePath());
			// Schema Evolution Suite

			String[] folders = folder.list();

			String pattern = Pattern.quote(System.getProperty("file.separator"));
			String columnPath = "Results\\SchemaLevelAnalysis\\ColumnStatistics\\ColumnsStatistics-"
					+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
					+ "-.csv";
			String tablePath = "Results\\SchemaLevelAnalysis\\TableStatistics\\TableStatistics-"
					+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
					+ "-.csv";

			analysisTools.SetFileSettings(tablePath, columnPath);

			TablePrefixes.setUpListWithPrefixes();
			UniformSuffixes.SetUpListWithSuffixes();

			java.util.Arrays.sort(folders);

			ReservedWords.SetReservedWords();
			for (int i = 0; i < folders.length - 1; i++) {

				System.out.println(analysisTools.getPath() + File.separator + folders[i]);
				Schema schemaA = getSchema(analysisTools.getPath() + File.separator + folders[i]);

				ArrayList<String> tablesInSchema = new ArrayList<String>();

				try {
					for (Entry<String, Table> e : schemaA.getTables().entrySet()) {

						String tableName = e.getKey();
						for (Attribute columnName : e.getValue().getAttrs().values()) {

							try {
								analysisTools.getColumnCheck().runChecks(columnName.getName(), tableName);
							} catch (Exception ex) {
								ex.fillInStackTrace();
								ex.getStackTrace();
								System.out.println("EXCEPTION : " + ex.getMessage());
							}
						}
						analysisTools.getTableCheck().runChecks(tableName);
						tablesInSchema.add(tableName);
					}
				} catch (Exception e) {

					Logger.Log(e);

				}
				analysisTools.getTableCheck().nameConcatenation(tablesInSchema);
				analysisTools.getTableCheck().dataSetName = analysisTools.getPath()
						.split(pattern)[analysisTools.getPath().split(pattern).length - 2];
				analysisTools.getColumnCheck().dataSetName = analysisTools.getPath()
						.split(pattern)[analysisTools.getPath().split(pattern).length - 2];
				analysisTools.getColumnCheck().writeStatistics(columnPath);
				analysisTools.getTableCheck().writeStatistics(tablePath);
				analysisTools.clearStatistics();

			}
			analysisTools.clearStatistics();

			DatasetStatistics a = new DatasetStatistics(tablePath, columnPath);
			a.FillMeasurements();

			System.out.println("Finished!");
		} catch (Exception e) {

			Logger.Log(e);

		}
	}

	public void checkSchemaHistoryStyleByTableAndExport(File folder) throws IOException {
		try {
			AnalysisTools analysisTools = new AnalysisTools(folder.getAbsolutePath());

			String[] folders = folder.list();
			String pattern = Pattern.quote(System.getProperty("file.separator"));

			String tablePath = "Results\\RuleLevelAnalysis\\TableStatistics\\TableStatistics-"
					+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
					+ "-.csv";

			String columnPath = "Results\\RuleLevelAnalysis\\ColumnStatistics\\ColumnsStatistics-"
					+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
					+ "-.csv";

			analysisTools.SetFileSettings(tablePath, columnPath);

			TablePrefixes.setUpListWithPrefixes();
			
			UniformSuffixes.SetUpListWithSuffixes();

			java.util.Arrays.sort(folders);

			ReservedWords.SetReservedWords();
			
			for (int i = 0; i < folders.length - 1; i++) {
				// result.clear();

				System.out.println(analysisTools.getPath() + File.separator + folders[i]);
				Schema schemaA = getSchema(analysisTools.getPath() + File.separator + folders[i]);

				ArrayList<String> tablesInSchema = new ArrayList<String>();

				try {
					for (Entry<String, Table> e : schemaA.getTables().entrySet()) {

						String tableName = e.getKey();
						for (Attribute columnName : e.getValue().getAttrs().values()) {

							try {
								analysisTools.getColumnCheck().runChecks(columnName.getName(), tableName);
							} catch (Exception ex) {
								ex.fillInStackTrace();
								ex.getStackTrace();
								System.out.println("EXCEPTION : " + ex.getMessage());
							}
						}
						analysisTools.getTableCheck().runChecks(tableName);
						tablesInSchema.add(tableName);
						analysisTools.getTableCheck().nameConcatenation(tablesInSchema);
						analysisTools.getTableCheck().dataSetName = tableName;
						analysisTools.getColumnCheck().dataSetName = tableName;

						analysisTools.getColumnCheck()
								.writeStatistics("Results\\RuleLevelAnalysis\\ColumnStatistics\\ColumnsStatistics-"
										+ analysisTools.getPath()
												.split(pattern)[analysisTools.getPath().split(pattern).length - 2]
										+ "-.csv");
						analysisTools.getColumnCheck().clearStatistics();
						analysisTools.getTableCheck()
								.writeStatistics("Results\\RuleLevelAnalysis\\TableStatistics\\TableStatistics-"
										+ analysisTools.getPath()
												.split(pattern)[analysisTools.getPath().split(pattern).length - 2]
										+ "-.csv");
						analysisTools.getTableCheck().clearStatistics();

					}
				} catch (Exception e) {
					e.fillInStackTrace();
					e.getStackTrace();
				}

				analysisTools.getColumnCheck()
						.IncreaseRevisionIndex("Results\\RuleLevelAnalysis\\ColumnStatistics\\ColumnsStatistics-"
								+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length
										- 2]
								+ "-.csv");
				analysisTools.getTableCheck()
						.IncreaseRevisionIndex("Results\\RuleLevelAnalysis\\TableStatistics\\TableStatistics-"
								+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length
										- 2]
								+ "-.csv");
			}

			analysisTools.clearStatistics();

			DatasetStatistics a = new DatasetStatistics(
					"Results\\RuleLevelAnalysis\\TableStatistics\\TableStatistics-"
							+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
							+ "-.csv",
					"Results\\RuleLevelAnalysis\\ColumnStatistics\\ColumnsStatistics-"
							+ analysisTools.getPath().split(pattern)[analysisTools.getPath().split(pattern).length - 2]
							+ "-.csv");
			a.FillMeasurements();

			System.out.println("Finished!");
		} catch (Exception e) {

			Logger.Log(e);

		}
	}

	public void traversePaths(File folder) throws IOException {
		String[] folders = folder.list();
		String path = folder.getAbsolutePath();
		if (folders == null) {
			return;
		}
		String fileName = "Results";

		Path resultsPath = Paths.get(fileName);

		if (!Files.isDirectory(resultsPath)) {
			Files.createDirectory(resultsPath);

			Path statistics = Paths.get("Results\\SchemaLevelAnalysis");
			Files.createDirectories(statistics);

			statistics = Paths.get("Results\\SchemaLevelAnalysis\\ColumnStatistics");
			Files.createDirectories(statistics);

			statistics = Paths.get("Results\\SchemaLevelAnalysis\\TableStatistics");
			Files.createDirectories(statistics);

			statistics = Paths.get("Results\\RuleLevelAnalysis\\ColumnStatistics");
			Files.createDirectories(statistics);

			statistics = Paths.get("Results\\RuleLevelAnalysis\\TableStatistics");
			Files.createDirectories(statistics);

		}

		// Where the GUI is constructed:

		for (int i = 0; i < folders.length; i++) {

			if (folders[i].equals("schemata")) {

				File myFile = new File(path + File.separator + folders[i]);

				// checkDifferencesInSchemataHistoryAndExport(myFile);
				checkSchemaHistoryStyleByTableAndExport(myFile);
				checkSchemaHistoryStyleByRuleAndExport(myFile);
			} else {
				File myFile = new File(path + File.separator + folders[i]);

				traversePaths(myFile);
			}
		}
	}

	/**
	 * @param csv
	 * @param xml
	 * @param metrics
	 * @throws IOException
	 */

	public Schema getSchema(String path) {
		return SESParser.parse(path);
	}
}
