package gr.uoi.cs.dbsea.columnsstylecheck;

import java.io.FileWriter;
import java.io.IOException;

import gr.uoi.cs.dbsea.generalchecks.GenericCheck;
import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;
import gr.uoi.cs.dbsea.logger.Logger;
import gr.uoi.cs.dbsea.statistics.ColumnCheckStatistics;
import gr.uoi.cs.dbsea.wordnetchecks.WordnetCheck;

public class ColumnCheck extends GenericCheck {

	public ColumnCheckStatistics columnCheckStatistics;

	public ColumnCheck() {

		columnCheckStatistics = new ColumnCheckStatistics();

	}

	public double TotalColumns() {

		return columnCheckStatistics.getTotalColumns();

	}

	public void clearStatistics() {

		try {
			columnCheckStatistics.ClearStatistics();
			genericCheckStatistics.ClearStatistics();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.Log(e);
		}

	}

	public void setStatisticsFile(String columnCheckStatisticsPath, String genericCheckStatisticsPath) {

		try {
			columnCheckStatistics.SetFile(columnCheckStatisticsPath);
			genericCheckStatistics.SetGenericFile(genericCheckStatisticsPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.Log(e);
		}

	}

	public void writeStatistics(String myFilePath) throws IOException {

		try {
			genericCheckStatistics.WriteStatisticsToGenericFileGood(myFilePath,
					(double) columnCheckStatistics.getTotalColumns(), dataSetName);

			columnCheckStatistics.WriteStatisticsToFileGood(myFilePath, dataSetName);

			genericCheckStatistics.WriteStatisticsToGenericBadFile(myFilePath,
					(double) columnCheckStatistics.getTotalColumns(), dataSetName);
			columnCheckStatistics.WriteStatisticsToFileBad(myFilePath, dataSetName);
		} catch (Exception e) {

			Logger.Log(e);
			
		}

	}

	public void IncreaseRevisionIndex(String myFilePath) throws IOException {

		FileWriter fw = new FileWriter(myFilePath, true);
		fw.append("\n");
		fw.close();
	}

	public void setFileTitle(String myFilePath) throws IOException {

		try {
			genericCheckStatistics.SetGenericFileTitleGood(myFilePath);

			columnCheckStatistics.SetFileTitleGood(myFilePath);

			genericCheckStatistics.SetGenericFileTitleBad(myFilePath);

			columnCheckStatistics.SetFileTitleBad(myFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.Log(e);
		}

	}

	public boolean isIdPrimaryIdentifier(String columnName) {

		try {
			if (columnName.toLowerCase().equals("id")) {
				columnCheckStatistics.addIdAsIdentifier();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}

	}

	public boolean containsSingularAndNotPlural(String columnName) {

		try {
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
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}
	}

	public boolean containsSingular(String columnName) {

		try {
			if (WordnetCheck.containsNoun(columnName, caseType)) {
				if (WordnetCheck.containsSingular(columnName, caseType)
						&& !WordnetCheck.containsPlural(columnName, caseType)) {
					columnCheckStatistics.addContainsSingular();
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

	public boolean containsPlural(String columnName) {

		try {
			if (WordnetCheck.containsNoun(columnName, caseType)) {
				if (WordnetCheck.containsPlural(columnName, caseType)) {
					columnCheckStatistics.addContainsPlural();
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

	public boolean containsUniformSuffix(String columnName) {

		try {
			for (String suffix : UniformSuffixes.suffixes) {
				if (columnName.length() - suffix.length() > 0) {
					if (columnName.subSequence(columnName.length() - suffix.length(), columnName.length())
							.equals(suffix)) {
						columnCheckStatistics.addContainsUniformSuffix();
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

	public boolean isLowerCase(String columnName) {

		try {
			if (caseType == TypeOfCases.LowerCase) {
				columnCheckStatistics.addIsLowerCase();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			Logger.Log(e);
			return false;
			
		}

	}

	public boolean columnNameSameAsTheTable(String columnName, String tableName) {
		try {
			if (columnName.equals(tableName)) {
				columnCheckStatistics.addColumnNameSameAsTheTable();
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	public boolean columnNameContainsTheTableName(String columnName, String tableName) {

		try {
			if (columnName.contains(tableName)) {
				columnCheckStatistics.addColumnNameContainsTheTableName();
				return true;
			}

			return false;
		} catch (Exception e) {
		
			Logger.Log(e);
			return false;
	
		}

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

		} catch (Exception e) {

			Logger.Log(e);
		
		}
	}

}
