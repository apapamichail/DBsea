package gr.uoi.cs.dbsea.statistics;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math3.util.Precision;


public class ColumnCheckStatistics {

	private double totalColumns = 0;
	private int idAsIdentifier = 0;
	private int sameNameAsTable = 0;
	private int containsOnlySingular = 0;
	private int containsUniformSuffix = 0;
	private int isLowerCase = 0;
	private int columnNameSameAsTheTable = 0;
	private int columnNameContainsTheTable = 0;
	private int containsPlural;
	private int containsSingular;

	public String filePath;
	public String filePathTables;
	
	private FileWriter fw;
//	public int DatasetRevision=1;

	public void SetFile(String path) {
		try {
			fw = new FileWriter(path, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.filePath = path;
	
	}

	public void ClearStatistics() {

		totalColumns = 0;
		idAsIdentifier = 0;
		sameNameAsTable = 0;
 		containsOnlySingular = 0;
		containsUniformSuffix = 0;
		isLowerCase = 0;
		columnNameSameAsTheTable = 0;
		columnNameContainsTheTable  = 0;
		containsPlural = 0;
		containsSingular = 0;

	}

	public void SetFileTitleGood(String myFilePath) throws IOException {
	//	DatasetRevision=1;

		fw = new FileWriter(myFilePath, true);
//		fw.append("Informative,");
//		fw.append(",");
//		fw.append(",");
//		fw.append(",");
//		fw.append("Good,");
//		fw.append("Bad,");
//		fw.append(",");
//		fw.append("\n");
//		fw.append("Total Columns,");
//		fw.append("Name contains Plural,");
//		fw.append("Name contains Singular and not plural,");
		fw.append("USP,");//Uniform Postfix
		fw.append("CIS,");//Singular

		fw.close();
	}
	public void SetFileTitleBad(String myFilePath) throws IOException {
	
		fw = new FileWriter(myFilePath, true);
		fw.append("AII,");//Equals \"id\"
		fw.append("DNC,");//Column name same as table
		fw.append("NBP,");//Name defined by place
 		fw.append("Database Name\n");
 		
		fw.close();

	}

		
	public void WriteStatisticsToFileGood(String myFilePath,String datasetName) throws IOException {

		fw = new FileWriter(myFilePath, true);
//		fw.append(totalColumns + ",");
		fw.append(GetValueWithDoublePrecision(containsUniformSuffix/totalColumns)+",");
		fw.append(GetValueWithDoublePrecision(containsSingular/totalColumns)+",");
		fw.close();
	}
	private double GetValueWithDoublePrecision(double value) {
		return 	Precision.round(value, 2);

	}

	public void WriteStatisticsToFileBad(String myFilePath,String datasetName) throws IOException {
		
		fw = new FileWriter(myFilePath, true);

		fw.append(GetValueWithDoublePrecision(Math.abs((idAsIdentifier/totalColumns)-1))+",");
		fw.append(GetValueWithDoublePrecision(Math.abs((columnNameSameAsTheTable/totalColumns)-1))+",");
		fw.append(GetValueWithDoublePrecision(Math.abs((columnNameContainsTheTable /totalColumns)-1))+",");
//		fw.append(containsPlural/totalColumns+",");
//		fw.append(containsSingularAndNotPlural/totalColumns+",");
		fw.append(datasetName+"\n");
 	//	fw.append(DatasetRevision+"\n");
		fw.close();
		
	}
	
	public ColumnCheckStatistics() {

		this.totalColumns = 0;
		this.idAsIdentifier = 0;
		this.sameNameAsTable = 0;
		this.containsOnlySingular = 0;
		this.containsUniformSuffix = 0;
		this.isLowerCase = 0;
		this.columnNameSameAsTheTable = 0;
		this.columnNameContainsTheTable = 0;
		this.containsPlural = 0;
		this.containsSingular = 0;
	
	}

	public double getTotalColumns() {
		return totalColumns;
	}

	public void addTotalColumns() {
		totalColumns += 1;
	}

	public int getIdAsIdentifier() {
		return idAsIdentifier;
	}

	public void addIdAsIdentifier() {
		this.idAsIdentifier += 1;
	}

	public int getSameNameAsTable() {
		return sameNameAsTable;
	}

	public void addSameNameAsTable() {
		this.sameNameAsTable += 1;
	}

	public int getContainsSingularAndNotPlural() {
		return containsOnlySingular;
	}

	public void addContainsSingularAndNotPlural() {
		this.containsOnlySingular += 1;
	}

	public void addContainsSingular() {
		this.containsSingular += 1;
	}

	public void addContainsPlural() {
		this.containsPlural += 1;
	}

	public int getContainsSingular() {
		return this.containsSingular;
	}

	public int getContainsPlural() {
		return this.containsPlural;
	}

	public int getContainsUniformSuffix() {
		return containsUniformSuffix;
	}

	public void addContainsUniformSuffix() {
		this.containsUniformSuffix += 1;
	}

	public int getIsLowerCase() {
		return isLowerCase;
	}

	public void addIsLowerCase() {
		this.isLowerCase += 1;
	}

	public int getColumnNameSameAsTheTable() {
		return columnNameSameAsTheTable;
	}
	public int getColumnNameContainsTheTableName() {
		return columnNameContainsTheTable ;
	}

	
	public void addColumnNameSameAsTheTable() {
		this.columnNameSameAsTheTable  += 1;
	}
	public void addColumnNameContainsTheTableName() {
		this.columnNameContainsTheTable  += 1;
	}


	public void writeNextLine(String myFilePath) throws IOException {
		fw = new FileWriter(myFilePath, true);
		fw.append("\n");
		fw.close();		
		
	}

}
