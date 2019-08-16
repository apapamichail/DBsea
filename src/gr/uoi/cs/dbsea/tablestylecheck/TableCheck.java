/**
 * 
 */
package gr.uoi.cs.dbsea.tablestylecheck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import gr.uoi.cs.dbsea.generalchecks.GenericCheck;
import gr.uoi.cs.dbsea.logger.Logger;
import gr.uoi.cs.dbsea.statistics.TableCheckStatistics;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;

/**
 * @author angelo
 *
 */
public class TableCheck extends GenericCheck {

	public TableCheckStatistics tableCheckStatistics;

	public TableCheck() {

		tableCheckStatistics = new TableCheckStatistics();

	}

	public double TotalTables() {

		return tableCheckStatistics.getTotalTables();

	}

	public void clearStatistics() {

		tableCheckStatistics.ClearStatistics();
		genericCheckStatistics.ClearStatistics();

	}

	public void setStatisticsFile(String tableCheckStatisticsPath, String genericCheckStatisticsPath) {

		tableCheckStatistics.SetFile(tableCheckStatisticsPath);
		genericCheckStatistics.SetGenericFile(genericCheckStatisticsPath);
	}

	public void writeStatistics(String myFilePath) throws IOException {

		genericCheckStatistics.WriteStatisticsToGenericFileGood(myFilePath,
				(double) tableCheckStatistics.getTotalTables(), dataSetName);
		tableCheckStatistics.WriteStatisticsToFileGood(myFilePath, dataSetName);
		genericCheckStatistics.WriteStatisticsToGenericBadFile(myFilePath,
				(double) tableCheckStatistics.getTotalTables(), dataSetName);
		tableCheckStatistics.WriteStatisticsToFileBad(myFilePath, dataSetName);

	}

	public void IncreaseRevisionIndex(String myFilePath) throws IOException {

		FileWriter fw = new FileWriter(myFilePath, true);
//		tableCheckStatistics.DatasetRevision+=1;
		fw.append("\n");
		fw.close();
	}

	public void SetFileTitle(String myFilePath) throws IOException {

		genericCheckStatistics.SetGenericFileTitleGood(myFilePath);
		tableCheckStatistics.SetFileTitleGood(myFilePath);
		genericCheckStatistics.SetGenericFileTitleBad(myFilePath);
		tableCheckStatistics.SetFileTitleBad(myFilePath);

	}

	public void nameConcatenation(ArrayList<String> tablesInSchema) {

		try {
			for (String table : tablesInSchema) {
				for (String table2 : tablesInSchema) {
					if (!table.equals(table2)) {
						if (table.contains(table2)) {
							tableCheckStatistics.addNameConcatenation();

						}
					}
				}
			}
		} catch (Exception e) {

			Logger.Log(e);

		}
	}

	public boolean containsPrefix(String tableName) {

		try {
			for (String prefix : TablePrefixes.prefixes) {
				if (tableName.length() > prefix.length()) {
					if (tableName.subSequence(0, prefix.length()).equals(prefix)) {
						tableCheckStatistics.addContainsPrefix();
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsOnlySingular(String tableName) {
		
		try {
			if (WordnetCheck.containsNoun(tableName, caseType)) {

				if (!WordnetCheck.containsPlural(tableName, caseType)
						&& WordnetCheck.containsSingular(tableName, caseType)) {
					tableCheckStatistics.addContainsOnlySingular();
					genericCheckStatistics.addContainsOnlySingular();
					return true;
				}
			} else {

			}
			return false;
		} catch (Exception e) {
			
			Logger.Log(e);
			return false;
			
		}

	}

	public boolean containsSingular(String columnName) {
		
		try {
		
			if (WordnetCheck.containsNoun(columnName, caseType)) {
				if (WordnetCheck.containsSingular(columnName, caseType)) {
					tableCheckStatistics.addContainsSingular();
					return true;
				}
			} else {
				return true;
			}
			return false;
			
		} catch (Exception e) {
			
			Logger.Log(e);
			return false;
	
		}
	}

	public boolean containsPlural(String name) {

		try {
			if (WordnetCheck.containsNoun(name, caseType)) {
				if (WordnetCheck.containsPlural(name, caseType) && !WordnetCheck.containsSingular(name, caseType)) {
					tableCheckStatistics.addContainsPlural();
					return true;
				}
			} else {
				return true;
			}
			return false;
		} catch (Exception e) {
			
			Logger.Log(e);
			return false;
			
		}

	}

	public boolean startWithCapital(String tableName) {

		try {
			if (tableName.toCharArray()[0] >= 'A' && tableName.toCharArray()[0] <= 'Z') {
				tableCheckStatistics.addStartWithCapital();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsVerb(String tableName) {

		try {
			
			if (WordnetCheck.containsVerb(tableName, caseType)) {
				tableCheckStatistics.addContainsVerb();
				return true;
			}
			return false;
			
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	public void runChecks(String tableName) {

		try {
			
			tableCheckStatistics.addTotalTables();
			super.runChecks(tableName);
			containsPrefix(tableName);
			containsOnlySingular(tableName);
			containsSingular(tableName);
			containsPlural(tableName);
			startWithCapital(tableName);
			
		} catch (Exception e) {
			
			Logger.Log(e);
			
		}

		// containsVerb(tableName);
	}

}
