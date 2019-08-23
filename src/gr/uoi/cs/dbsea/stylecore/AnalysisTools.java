package gr.uoi.cs.dbsea.stylecore;

import java.io.IOException;

import gr.uoi.cs.dbsea.columnsstylecheck.ColumnCheck;
import gr.uoi.cs.dbsea.logger.Logger;
import gr.uoi.cs.dbsea.tablestylecheck.TableCheck;

public class AnalysisTools {
	private String path;
	private TableCheck tableCheck;
	private ColumnCheck columnCheck;

	public AnalysisTools(String path) {
		tableCheck = new TableCheck();
		columnCheck = new ColumnCheck();
		this.setPath(path);
	}

	public AnalysisTools() {
		tableCheck = new TableCheck();
		columnCheck = new ColumnCheck();
	}

	public ColumnCheck getColumnCheck() {
		return columnCheck;
	}

	public void setColumnCheck(ColumnCheck columnCheck) {
		this.columnCheck = columnCheck;
	}

	public TableCheck getTableCheck() {
		return tableCheck;
	}

	public void setTableCheck(TableCheck tableCheck) {
		this.tableCheck = tableCheck;
	}

	public String getPath() {
		return path;
	}

	public void clearStatistics() {
		try {

			tableCheck.clearStatistics();
			columnCheck.clearStatistics();

		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void SetFileSettings(String tablePath, String columnPath) {
		try {
			
			SetFileSettingsForTables(tablePath);
			SetFileSettingsForColumns(columnPath);
			
		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	public void SetFileSettingsForTables(String tablePath) {
		try {

			columnCheck.setStatisticsFile(tablePath, tablePath);
			columnCheck.setFileTitle(tablePath);
			
		} catch (IOException e) {
			Logger.Log(e);
		}

	}

	public void SetFileSettingsForColumns(String columnPath) {
		try {

			columnCheck.setStatisticsFile(columnPath, columnPath);
			columnCheck.setFileTitle(columnPath);
		} catch (IOException e) {
			Logger.Log(e);
		}

	}

}