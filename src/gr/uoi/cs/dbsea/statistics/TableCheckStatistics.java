package gr.uoi.cs.dbsea.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math3.util.Precision;
 
public class TableCheckStatistics {
	public String filePath;
	public String filePathTables;
	private FileWriter fw;

	private double totalTables;
	private int sameNameAsTable;
	private int commonSuffix;
	private int containsPrefix;
	private int containsVerb;
	private int nameConcatenation;
	private int containsPlural;
	private int startWithCapital;
	private int containsSingular;
	private int containsOnlySingular;
	public String datasetName;
	//public int DatasetRevision=1;

	public void SetFile(String path) {
		this.filePath = path;
		try {
			fw = new FileWriter(filePath, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ClearStatistics() {

		this.totalTables = 0;
		this.sameNameAsTable = 0;
		this.commonSuffix = 0;
		this.containsPrefix = 0;
		this.containsVerb = 0;
		this.nameConcatenation = 0;
		this.containsPlural = 0;
		this.startWithCapital = 0;
		this.containsSingular = 0;
		this.containsOnlySingular = 0;
	}

	public void SetFileTitleGood(String myfilePath) throws IOException {
		// super.SetFileTitle();
	//	DatasetRevision=1;
		fw = new FileWriter(myfilePath, true);
		// fw.append("Informative,");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append(",");
		// fw.append("Good,");
		// fw.append(",");
		// fw.append("Bad,");
		// fw.append(",");
		// fw.append("\n");
		// fw.append("Total tables,");
		// fw.append("Name contains prefix,");
		// fw.append("Name contains singular,");
		fw.append("TIP,");//Plural
		// fw.append("Name contains plural and not singular,");
		fw.append("SWC,");//Starts with capital

		fw.close();
	}

	public void SetFileTitleBad(String myfilePath) throws IOException {
		fw = new FileWriter(myfilePath, true);
		fw.append("ACN,");//table name concatenation
		//	fw.append("Concatenation of table’s  in another table’s name,");
		
		fw.append("Database Name\n");
		fw.close();
	}

	public void WriteStatisticsToFileGood(String myfilePath,String Dataset) throws IOException {
		datasetName = Dataset;
		// super.WriteStatisticsToFile();
		// fw = new BufferedWriter(fw);
		fw = new FileWriter(myfilePath, true);
		// fw.append(totalTablestotalTables+",");
		// fw.append(containsPrefix/totalTables+",");
		// fw.append(containsSingular/totalTables+",");
		fw.append(GetValueWithDoublePrecision(containsPlural / totalTables )+ ",");
		// fw.append(containsPluralAndNotSingular+",");
		fw.append(GetValueWithDoublePrecision(startWithCapital / totalTables )+ ",");
		fw.close();

	}

	public void WriteStatisticsToFileBad(String myfilePath,String Dataset) throws IOException {

		fw = new FileWriter(myfilePath, true);
		fw.append(GetValueWithDoublePrecision(Math.abs((nameConcatenation / totalTables)-1) )+ ",");
//		fw.append(GetValueWithDoublePrecision(containsOnlySingular / totalTables )+ ",");
		fw.append(datasetName+"\n");
 		//fw.append(DatasetRevision+"\n");
		fw.close();
	}
	private double GetValueWithDoublePrecision(double value) {
		return 	Precision.round(value, 2);

	}

	// public void WriteStatisticsToFile() throws IOException {
	//
	// // super.WriteStatisticsToFile();
	// // fw = new BufferedWriter(fw);
	// fw = new FileWriter(filePath, true);
	// fw.append(totalTables + ",");
	// fw.append(containsPrefix/totalTables + ",");
	// fw.append(containsSingular/totalTables + ",");
	// fw.append(containsPlural/totalTables + ",");
	// fw.append(containsPluralAndNotSingular/totalTables+",");
	// fw.append(startWithCapital/totalTables + ",");
	// fw.append(nameConcatenation/totalTables +",");
	// fw.append(datasetName+"\n");
	// fw.close();
	// }
	// public void WriteStatisticsToFile() throws IOException {
	//
	// // super.WriteStatisticsToFile();
	// // fw = new BufferedWriter(fw);
	// fw = new FileWriter(filePath, true);
	// fw.append(totalTables + ",");
	// fw.append(containsPrefix + ",");
	// fw.append(containsSingular + ",");
	// fw.append(containsPlural + ",");
	// fw.append(containsPluralAndNotSingular+",");
	// fw.append(startWithCapital + ",");
	// fw.append(nameConcatenation + "\n");
	// fw.close();
	// }
	public int getContainsPluralAndNotSingular() {
		return containsOnlySingular;
	}

	public void addContainsOnlySingular() {
		containsOnlySingular += 1;
	}

	public int getContainsSingular() {
		return containsSingular;
	}

	public void addContainsSingular() {
		containsSingular += 1;
	}

	public int getContainsPlural() {
		return containsPlural;
	}

	public void addContainsPlural() {
		containsPlural += 1;
	}

	public int getNameConcatenation() {
		return nameConcatenation;
	}

	public void addNameConcatenation() {
		nameConcatenation += 1;
	}

	public double getTotalTables() {
		return totalTables;
	}

	public void addTotalTables() {
		totalTables += 1;
	}

	public int getSameNameAsTable() {
		return sameNameAsTable;
	}

	public void addSameNameAsTable() {
		this.sameNameAsTable += 1;
	}

	public int getCommonSuffix() {
		return commonSuffix;
	}

	public void addCommonSuffix() {
		this.commonSuffix += 1;
	}

	public int getContainsPrefix() {
		return containsPrefix;
	}

	public void addContainsPrefix() {
		this.containsPrefix += 1;
	}

	public int getContainsVerb() {
		return containsVerb;
	}

	public void addContainsVerb() {
		this.containsVerb += 1;
	}

	public int getStartWithCapital() {
		return startWithCapital;
	}

	public void addStartWithCapital() {
		this.startWithCapital += 1;
	}

	public TableCheckStatistics() {
		this.totalTables = 0;
		this.sameNameAsTable = 0;
		this.containsPrefix = 0;
		this.commonSuffix = 0;
		this.containsVerb = 0;
		this.containsPlural = 0;
		this.nameConcatenation = 0;
		this.startWithCapital = 0;
		this.containsPlural = 0;
		this.containsSingular = 0;

	}

	public void writeNextLine(String myFilePath) throws IOException {
		// TODO Auto-generated method stub
		fw = new FileWriter(myFilePath, true);
		fw.append("\n");
		fw.close();
	}

}
