package gr.uoi.cs.dbsea.columnsstylecheck;

import java.io.FileWriter;
import java.io.IOException;

import gr.uoi.cs.dbsea.generalchecks.CaseCheck;
import gr.uoi.cs.dbsea.generalchecks.GenericCheck;
import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;
import gr.uoi.cs.dbsea.statistics.ColumnCheckStatistics;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;
/**
 * @author Papamichail Aggelos
 * This class contains checks specific for columns.
 */
public class ColumnCheck extends GenericCheck {

	public ColumnCheckStatistics columnCheckStatistics;
	private String filePath;
	private FileWriter fw;

	public ColumnCheck() {
		columnCheckStatistics = new ColumnCheckStatistics();
	}

	public double TotalColumns() {
		return columnCheckStatistics.getTotalColumns();
	}
	public void clearStatistics() {
		columnCheckStatistics.ClearStatistics();
		genericCheckStatistics.ClearStatistics();
	}

	public void setStatisticsFile(String columnCheckStatisticsPath, String genericCheckStatisticsPath) {
		columnCheckStatistics.SetFile(columnCheckStatisticsPath);
		genericCheckStatistics.SetGenericFile(genericCheckStatisticsPath);
	}

	public void writeStatistics(String myFilePath) throws IOException {
		genericCheckStatistics.WriteStatisticsToGenericFileGood(myFilePath,
				(double) columnCheckStatistics.getTotalColumns(), dataSetName);
		columnCheckStatistics.WriteStatisticsToFileGood(myFilePath, dataSetName);
		genericCheckStatistics.WriteStatisticsToGenericBadFile(myFilePath,
				(double) columnCheckStatistics.getTotalColumns(), dataSetName);
		columnCheckStatistics.WriteStatisticsToFileBad(myFilePath, dataSetName);

	}

	public void IncreaseRevisionIndex(String myFilePath) throws IOException {

		FileWriter fw= new FileWriter(myFilePath,true);
//		tableCheckStatistics.DatasetRevision+=1;
		fw.append("\n");
		fw.close();
	}

	public void setFileTitle(String myFilePath) throws IOException {
		genericCheckStatistics.SetGenericFileTitleGood(myFilePath);
		columnCheckStatistics.SetFileTitleGood(myFilePath);
		genericCheckStatistics.SetGenericFileTitleBad(myFilePath);
		columnCheckStatistics.SetFileTitleBad(myFilePath);

	}

	public boolean isIdPrimaryIdentifier(String columnName) {
		if (columnName.toLowerCase().equals("id")) {
			columnCheckStatistics.addIdAsIdentifier();
			return true;
		} else {
			return false;
		}
	}

	public boolean containsSingularAndNotPlural(String columnName) {
		if (WordnetCheck.containsNoun(columnName, caseType)) {
			if (!WordnetCheck.containsPlural(columnName, caseType)
					&& WordnetCheck.containsSingular(columnName, caseType)) {
				columnCheckStatistics.addContainsSingularAndNotPlural();
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public boolean containsSingular(String columnName) {
		if (WordnetCheck.containsNoun(columnName, caseType)) {
			if (WordnetCheck.containsSingular(columnName, caseType) && !WordnetCheck.containsPlural(columnName, caseType)) {
				columnCheckStatistics.addContainsSingular();
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public boolean containsPlural(String columnName) {
		if (WordnetCheck.containsNoun(columnName, caseType)) {
			if (WordnetCheck.containsPlural(columnName, caseType)) {
				columnCheckStatistics.addContainsPlural();
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public boolean containsUniformSuffix(String columnName) {

		for (String suffix : UniformSuffixes.suffixes) {
			if (columnName.length() - suffix.length() > 0) {
				if (columnName.subSequence(columnName.length() - suffix.length(), columnName.length()).equals(suffix)) {
					columnCheckStatistics.addContainsUniformSuffix();
					return true;
				}
			}
		}
		return false;

	}

	public boolean isLowerCase(String columnName) {
		if (caseType == TypeOfCases.LowerCase) {
			columnCheckStatistics.addIsLowerCase();
			return true;
		} else {
			return false;
		}
	}

	public boolean columnNameSameAsTheTable(String columnName, String tableName) {
		if (columnName.equals(tableName)) {
			columnCheckStatistics.addColumnNameSameAsTheTable();
			return true;
		}
		return false;
	}

	public boolean columnNameContainsTheTableName(String columnName, String tableName) {
		if (columnName.contains(tableName)) {
			columnCheckStatistics.addColumnNameContainsTheTableName();
			return true;
		}
		return false;
	}

	public void runChecks(String columnName, String tableName) {
		try {
			columnCheckStatistics.addTotalColumns();
			super.runChecks(columnName);
			isIdPrimaryIdentifier(columnName);
			containsSingularAndNotPlural(columnName);
			containsSingular(columnName);
			containsPlural(columnName);
			containsUniformSuffix(columnName);
			isLowerCase(columnName);
			columnNameSameAsTheTable(columnName, tableName);
			columnNameContainsTheTableName(columnName, tableName);
		} catch (Exception ex) {
		System.out.println(ex.getMessage());		
		}
	}

}
