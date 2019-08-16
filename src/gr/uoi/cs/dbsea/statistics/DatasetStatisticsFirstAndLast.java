package gr.uoi.cs.dbsea.statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.util.Precision;

import gr.uoi.cs.dbsea.logger.Logger;

public class DatasetStatisticsFirstAndLast {

	private String TablePath;
	private String TableTitle;
	private String[] TableTitleRow;
	private int TableTitleSize;
	private int TableMeasurementSize;
	private String ColumnTitle;
	private String[] ColumnTitleRow;

	private int ColumnTitleSize;
	private int ColumnMeasurementSize;
	private double TableMeasurements[][];
	private double ColumnMeasurements[][];
	private String ColumnPath;
	private List<String> tableRecords = new ArrayList<String>();
	private List<String> columnRecords = new ArrayList<String>();
	private FileWriter TableWriter;
	private FileWriter ColumnWriter;

	// statistics
	private double SkewnessTables[];
	private double SkewnessColumns[];

	private double STDTables[];// descriptive
	private double STDColumns[];

	private double MeanTables[];// descriptive
	private double MeanColumns[];

	private double MaxTables[];// rank
	private double MinTables[];

	private double MaxColumns[];// rank
	private double MinColumns[];

	private double SumTables[];// rank
	private double SumColumns[];

	private double AverageTables[];// rank
	private double AverageColumns[];

	private double ModeTables[];// rank user frequency for this one stat.Frequency
	private double ModeColumns[];
	private Double nan;
	DecimalFormat newFormat = new DecimalFormat("#.##");

	public DatasetStatisticsFirstAndLast(String tablePath, String columnPath) {
		TablePath = tablePath;
		ColumnPath = columnPath;
		nan = new Double(Double.NaN);
	}

	public void FillMeasurements() {
		try {

			ReadMeasurements();
			TableTitle = tableRecords.get(0).replace(",,", ",");
			TableTitleRow = TableTitle.split(",");
			ColumnTitle = columnRecords.get(0).replace(",,", ",");

			ColumnTitleRow = ColumnTitle.split(",");

			TableTitleSize = TableTitle.split(",").length - 1;
			TableMeasurements = new double[tableRecords.size() - 1][TableTitleSize];

			ColumnTitleSize = ColumnTitle.split(",").length - 1;
			ColumnMeasurements = new double[columnRecords.size() - 1][ColumnTitleSize];
			TableMeasurementSize = TableMeasurements.length;
			ColumnMeasurementSize = ColumnMeasurements.length;
			for (int i = 1; i < tableRecords.size(); i++) {
				String[] rowMeasurements = tableRecords.get(i).split(",");
				for (int j = 0; j < rowMeasurements.length - 1; j++) {

					TableMeasurements[i - 1][j] = Precision
							.round(Double.parseDouble(rowMeasurements[j].replace(" ", "")), 2);
				}
			}

			for (int i = 1; i < columnRecords.size(); i++) {
				String[] rowMeasurements = columnRecords.get(i).split(",");
				for (int j = 0; j < rowMeasurements.length - 1; j++) {
					ColumnMeasurements[i - 1][j] = Precision
							.round(Double.parseDouble(rowMeasurements[j].replace(" ", "")), 2);
				}
			}

			WriteMeasurementsToFile();
			TableWriter.flush();
			ColumnWriter.flush();

		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	private void CreateStatisticTables(int tablesLength, int columnsLengths) {
		SkewnessTables = new double[tablesLength];
		SkewnessColumns = new double[columnsLengths];

		STDTables = new double[tablesLength];// descriptive
		STDColumns = new double[columnsLengths];

		MeanTables = new double[tablesLength];// descriptive
		MeanColumns = new double[columnsLengths];

		MaxTables = new double[tablesLength];// rank
		MinTables = new double[tablesLength];// rank
		MaxColumns = new double[columnsLengths];// rank
		MinColumns = new double[columnsLengths];

		SumTables = new double[tablesLength];// rank
		SumColumns = new double[columnsLengths];

		AverageTables = new double[tablesLength];// rank
		AverageColumns = new double[columnsLengths];

		ModeTables = new double[tablesLength];// rank user frequency for this one stat.Frequency
		ModeColumns = new double[columnsLengths];

	}

	private void WriteMeasurementsToFile() {

		try {
			CreateStatisticTables(TableTitleSize, ColumnTitleSize);

			TableWriter = new FileWriter(TablePath.replace(".csv", "_Dataset_StasticsFirstAndLast.csv"));
			ColumnWriter = new FileWriter(ColumnPath.replace(".csv", "_Dataset_StasticsFirstAndLast.csv"));

			WriteCorrelations();

			WriteDescriptive();

			WriteMode();

			WriteAverage();

			WriteMax();

			WriteMin();

		} catch (IOException e) {

			Logger.Log(e);

		}

	}

	private void WriteCorrelations() throws IOException {

		try {
			KendallsCorrelation tableKendall = new KendallsCorrelation(TableMeasurements);

			KendallsCorrelation columnKendall = new KendallsCorrelation(ColumnMeasurements);
			SpearmansCorrelation tableSpearman = new SpearmansCorrelation();
			SpearmansCorrelation columnSpearman = new SpearmansCorrelation();

			double[][] tableK = tableKendall.getCorrelationMatrix().getData();
			double[][] columnK = columnKendall.getCorrelationMatrix().getData();

			double[][] tableS = tableSpearman.computeCorrelationMatrix(TableMeasurements).getData();
			double[][] columnS = columnSpearman.computeCorrelationMatrix(ColumnMeasurements).getData();
			TableWriter.append("Kendall\n");
			TableWriter = writeCorrelation(tableK, TableTitle.replace(",Database Name", "").split(","), TableWriter);
			TableWriter.append("Spearman\n");
			TableWriter = writeCorrelation(tableS, TableTitle.replace(",Database Name", "").split(","), TableWriter);
			ColumnWriter.append("Kendall\n");
			ColumnWriter = writeCorrelation(columnK, ColumnTitle.replace(",Database Name", "").split(","),
					ColumnWriter);
			ColumnWriter.append("Spearman\n");
			ColumnWriter = writeCorrelation(columnS, ColumnTitle.replace(",Database Name", "").split(","),
					ColumnWriter);

			// TableWriter.append("Kendall," + TableTitle.replace(",Database Name", ""));
			// ColumnWriter.append("\n");
			// ColumnWriter.append("Kendall," + ColumnTitle.replace(",Database Name", ""));
			// ColumnWriter.append("\n");
			//
			// if (tableK.length != TableTitleSize) {
			// System.out.println("tableK.length!=TableTitleSize");
			// System.exit(9);
			// }
			// TableWriter.append("\n");
			//
			// for (int i = 0; i < tableK.length; i++) {
			// TableWriter.append(TableTitleRow[i] + ",");
			// for (int j = 0; j < tableK[i].length; j++) {
			// if (i >= j) {
			// continue;
			// }
			// if (nan.equals(tableK[i][j])) {
			//
			// } else {
			// TableWriter.append(Double.toString(Precision.round(tableK[i][j], 2)));
			// }
			// if (j < tableK[i].length - 1) {
			// TableWriter.append(",");
			// }
			// }
			// TableWriter.append("\n");
			// }
			// for (int i = 0; i < columnK.length; i++) {
			// ColumnWriter.append(ColumnTitleRow[i] + ",");
			// for (int j = 0; j < columnK[i].length; j++) {
			// if (i >= j) {
			// continue;
			// }
			// if (Double.toString(columnK[i][j]).equals("NaN")) {
			// } else {
			// ColumnWriter.append(Double.toString(Precision.round(columnK[i][j], 2)));
			//
			// }
			// if (j < columnK[i].length - 1) {
			// ColumnWriter.append(",");
			// }
			// }
			// ColumnWriter.append("\n");
			// }
			//
			// /// Spearman
			// TableWriter.append("Spearman," + TableTitle.replace(",Database Name", ""));
			// TableWriter.append("\n");
			//
			// for (int i = 0; i < tableS.length; i++) {
			// TableWriter.append(TableTitleRow[i] + ",");
			// for (int j = 0; j < tableS[i].length; j++) {
			// if (i >= j) {
			// continue;
			// }
			// if (Double.toString(tableS[i][j]).equals("NaN")) {
			// } else {
			// TableWriter.append(Double.toString(Precision.round(tableS[i][j], 2)));
			// }
			// if (j < tableS[i].length - 1) {
			// TableWriter.append(",");
			// }
			// }
			// TableWriter.append("\n");
			//
			// }
			// TableWriter.append("\n");
			//
			// ColumnWriter.append("\n");
			// ColumnWriter.append("Spearman," + ColumnTitle.replace(",Database Name", ""));
			// ColumnWriter.append("\n");
			//
			// for (int i = 0; i < columnS.length; i++) {
			// ColumnWriter.append(ColumnTitleRow[i] + ",");
			//
			// for (int j = 0; j < columnS[i].length; j++) {
			// if (i >= j) {
			// continue;
			// }
			// if (nan.equals(columnS[i][j])) {
			//
			// } else {
			// ColumnWriter.append(Double.toString(Precision.round(columnS[i][j], 2)));
			// }
			// if (j < columnS[i].length - 1) {
			// ColumnWriter.append(",");
			// }
			// }
			// ColumnWriter.append("\n");
			//
			// }
			//
			TableWriter.append("\n");
			ColumnWriter.append("\n");
		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	// private void WriteCorrelations() throws IOException {
	//
	// try {
	// KendallsCorrelation tableKendall = new
	// KendallsCorrelation(TableMeasurements);
	//
	// KendallsCorrelation columnKendall = new
	// KendallsCorrelation(ColumnMeasurements);
	// SpearmansCorrelation tableSpearman = new SpearmansCorrelation();
	// SpearmansCorrelation columnSpearman = new SpearmansCorrelation();
	//
	// double[][] tableK = tableKendall.getCorrelationMatrix().getData();
	// double[][] columnK = columnKendall.getCorrelationMatrix().getData();
	//
	// double[][] tableS =
	// tableSpearman.computeCorrelationMatrix(TableMeasurements).getData();
	// double[][] columnS =
	// columnSpearman.computeCorrelationMatrix(ColumnMeasurements).getData();
	//
	// TableWriter.append("Kendall," + TableTitle.replace(",Database Name", ""));
	// ColumnWriter.append("\n");
	// ColumnWriter.append("Kendall," + ColumnTitle.replace(",Database Name", ""));
	// ColumnWriter.append("\n");
	//
	// if (tableK.length != TableTitleSize) {
	// System.out.println("tableK.length!=TableTitleSize");
	// System.exit(9);
	// }
	// TableWriter.append("\n");
	//
	// for (int i = 0; i < tableK.length; i++) {
	// TableWriter.append(TableTitleRow[i] + ",");
	// for (int j = 0; j < tableK[i].length; j++) {
	// if (i >= j) {
	// continue;
	// }
	// if (nan.equals(tableK[i][j])) {
	//
	// } else {
	// TableWriter.append(Double.toString(Precision.round(tableK[i][j], 2)));
	// }
	// if (j < tableK[i].length - 1) {
	// TableWriter.append(",");
	// }
	// }
	// TableWriter.append("\n");
	// }
	// for (int i = 0; i < columnK.length; i++) {
	// ColumnWriter.append(ColumnTitleRow[i] + ",");
	// for (int j = 0; j < columnK[i].length; j++) {
	// if (i >= j) {
	// continue;
	// }
	// if (Double.toString(columnK[i][j]).equals("NaN")) {
	// } else {
	// ColumnWriter.append(Double.toString(Precision.round(columnK[i][j], 2)));
	//
	// }
	// if (j < columnK[i].length - 1) {
	// ColumnWriter.append(",");
	// }
	// }
	// ColumnWriter.append("\n");
	// }
	//
	// /// Spearman
	// TableWriter.append("Spearman," + TableTitle.replace(",Database Name", ""));
	// TableWriter.append("\n");
	//
	// for (int i = 0; i < tableS.length; i++) {
	// TableWriter.append(TableTitleRow[i] + ",");
	// for (int j = 0; j < tableS[i].length; j++) {
	// if (i >= j) {
	// continue;
	// }
	// if (Double.toString(tableS[i][j]).equals("NaN")) {
	// } else {
	// TableWriter.append(Double.toString(Precision.round(tableS[i][j], 2)));
	// }
	// if (j < tableS[i].length - 1) {
	// TableWriter.append(",");
	// }
	// }
	// TableWriter.append("\n");
	//
	// }
	// TableWriter.append("\n");
	//
	// ColumnWriter.append("\n");
	// ColumnWriter.append("Spearman," + ColumnTitle.replace(",Database Name", ""));
	// ColumnWriter.append("\n");
	//
	// for (int i = 0; i < columnS.length; i++) {
	// ColumnWriter.append(ColumnTitleRow[i] + ",");
	//
	// for (int j = 0; j < columnS[i].length; j++) {
	// if (i >= j) {
	// continue;
	// }
	// if (nan.equals(columnS[i][j])) {
	//
	// } else {
	// ColumnWriter.append(Double.toString(Precision.round(columnS[i][j], 2)));
	// }
	// if (j < columnS[i].length - 1) {
	// ColumnWriter.append(",");
	// }
	// }
	// ColumnWriter.append("\n");
	//
	// }
	//
	// TableWriter.append("\n");
	// ColumnWriter.append("\n");
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// }

	private FileWriter writeCorrelation(double[][] correlationValues, String title[], FileWriter writer) {
		try {

			Map<Integer, Boolean> valuesIndexesNotNaN = new HashMap();
			for (int i = 0; i < correlationValues.length; i++) {
				int nanCounter = 0;
				for (int j = 0; j < correlationValues[i].length; j++) {
					if (Double.toString(correlationValues[i][j]).equals("NaN")) {
						nanCounter += 1;
					}
				}
				for (int j = 0; j < correlationValues[i].length; j++) {
					if (Double.toString(correlationValues[j][i]).equals("NaN")) {
						nanCounter += 1;
					}
				}
				if (nanCounter == correlationValues[i].length * 2 - 2) {
					valuesIndexesNotNaN.put(i, false);
				} else {
					valuesIndexesNotNaN.put(i, true);
				}
			}
			writer.append("Rules,");

			for (int i = 0; i < correlationValues.length; i++) {
				if (valuesIndexesNotNaN.get(i)) {
					writer.append(title[i]);
				}
				if (i < correlationValues.length - 1 && valuesIndexesNotNaN.get(i)) {
					writer.append(",");
				}
			}
			writer.append("\n");
			for (int i = 0; i < correlationValues.length; i++) {
				if (valuesIndexesNotNaN.get(i)) {
					writer.append(title[i] + ",");
				} else {
					continue;
				}
				for (int j = 0; j < correlationValues[i].length; j++) {
					// if (j<i) {
					// writer.append(",");
					// continue;
					// }
					if (i <= j) {
						writer.append(",");
						continue;
					}
					if (valuesIndexesNotNaN.get(j)) {
						if (Double.toString(correlationValues[j][i]).equals("NaN")) {
							writer.append("0");
						} else {
							writer.append(Double.toString(Precision.round(correlationValues[i][j], 2)));
						}

						if (j < correlationValues[i].length - 1 && valuesIndexesNotNaN.get(i)) {
							writer.append(",");
						}
					}
				}
				writer.append("\n");
			}
		} catch (IOException e) {

			Logger.Log(e);
		}
		return writer;

	}

	private void WriteDescriptive() throws IOException {

		try {
			TableWriter.append("Measurement," + TableTitle.replace(",Database Name", "") + "\n");
			ColumnWriter.append("Measurement," + ColumnTitle.replace(",Database Name", "") + "\n");
			WriteSkewness();
			WriteMean();
			WriteSTD();
		} catch (Exception e) {

			Logger.Log(e);
			
		}

	}

	private void WriteSkewness() {
		try {
			Skewness skewness = new Skewness();

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}

				SkewnessTables[i] = skewness.evaluate(temp);

			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}
				SkewnessColumns[i] = skewness.evaluate(temp);

			}
			TableWriter.append("Skewness,");
			for (int i = 0; i < TableTitleSize; i++) {
				if (nan.equals(SkewnessTables[i])) {
					TableWriter.append("0");
				} else {
					TableWriter.append(Double.toString(Precision.round(SkewnessTables[i], 2)));
				}
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			ColumnWriter.append("Skewness,");

			for (int i = 0; i < ColumnTitleSize; i++) {
				if (nan.equals(SkewnessColumns[i])) {
					ColumnWriter.append("0");
				} else {
					ColumnWriter.append(Double.toString(Precision.round(SkewnessColumns[i], 2)));
				}
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			
			Logger.Log(e);
			
		}
	}

	private void AppendColumnComma(int i) {
		if (i < ColumnTitleSize - 1) {
			try {
				ColumnWriter.append(",");
			} catch (IOException e) {
				Logger.Log(e);
			}
		}
	}

	private void AppendTableComma(int i) {
		if (i < TableTitleSize - 1) {
			try {
				TableWriter.append(",");
			} catch (IOException e) {
				Logger.Log(e);
			}
		}
	}

	private void WriteMean() {

		try {
			Mean mean = new Mean();
			ColumnWriter.append("Mean,");
			TableWriter.append("Mean,");

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}
				MeanTables[i] = mean.evaluate(temp);

			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}
				MeanColumns[i] = mean.evaluate(temp);
			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(MeanTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(MeanColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	private void WriteSTD() {
		try {
			ColumnWriter.append("Standard Deviation,");
			TableWriter.append("Standard Deviation,");

			StandardDeviation std = new StandardDeviation();

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}

				STDTables[i] = std.evaluate(temp);
			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}

				STDColumns[i] = std.evaluate(temp);
			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(STDTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(STDColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	private void WriteAverage() {
		try {
			Sum sum = new Sum();
			ColumnWriter.append("Average,");
			TableWriter.append("Average,");

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}
				AverageTables[i] = sum.evaluate(temp) / TableMeasurementSize;
			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}
				AverageColumns[i] = sum.evaluate(temp) / ColumnMeasurementSize;
			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(AverageTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(AverageColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}
	}

	private void WriteMax() {

		try {
			Max max = new Max();
			ColumnWriter.append("Max,");
			TableWriter.append("Max,");

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}

				MaxTables[i] = max.evaluate(temp);
			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}

				MaxColumns[i] = max.evaluate(temp);

			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(MaxTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(MaxColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}

	}

	private void WriteMin() {

		try {
			Min min = new Min();
			TableWriter.append("Min,");
			ColumnWriter.append("Min,");

			for (int i = 0; i < TableTitleSize; i++) {
				double temp[] = new double[TableMeasurementSize];
				for (int j = 0; j < TableMeasurementSize; j++) {
					temp[j] = TableMeasurements[j][i];
				}
				MinTables[i] = min.evaluate(temp);

			}
			for (int i = 0; i < ColumnTitleSize; i++) {
				double temp[] = new double[ColumnMeasurementSize];
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					temp[j] = ColumnMeasurements[j][i];
				}
				MinColumns[i] = min.evaluate(temp);

			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(MinTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(MinColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}

	}

	private void WriteMode() {
		try {
			Frequency max = new Frequency();

			ColumnWriter.append("Mode,");
			TableWriter.append("Mode,");
			for (int i = 0; i < TableTitleSize; i++) {

				Map<Double, Integer> frequencyOfValues = new HashMap();
				for (int j = 0; j < TableMeasurementSize; j++) {
					if (frequencyOfValues.containsKey(TableMeasurements[j][i])) {
						frequencyOfValues.replace(TableMeasurements[j][i],
								frequencyOfValues.get(TableMeasurements[j][i]) + 1);
					} else {
						frequencyOfValues.put(TableMeasurements[j][i], (Integer) 0);
					}
				}
				int maxFrequency = 0;
				for (int j = 0; j < TableMeasurementSize; j++) {
					if (frequencyOfValues.getOrDefault(TableMeasurements[j][i], 1) > maxFrequency) {
						maxFrequency = frequencyOfValues.get(TableMeasurements[j][i]);
					}
				}
				ModeTables[i] = maxFrequency;
				// StatUtils.mode(temp, 0, 1)[0];
			}
			for (int i = 0; i < ColumnTitleSize; i++) {

				Map<Double, Integer> frequencyOfValues = new HashMap();
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					if (frequencyOfValues.containsKey(ColumnMeasurements[j][i])) {
						frequencyOfValues.replace(ColumnMeasurements[j][i],
								frequencyOfValues.get(ColumnMeasurements[j][i]) + 1);
					} else {
						frequencyOfValues.put(ColumnMeasurements[j][i], 0);
					}
				}
				int maxFrequency = 0;
				for (int j = 0; j < ColumnMeasurementSize; j++) {
					if (frequencyOfValues.get(ColumnMeasurements[j][i]) > maxFrequency) {
						maxFrequency = frequencyOfValues.get(ColumnMeasurements[j][i]);
					}
				}
				ModeColumns[i] = maxFrequency;
			}

			for (int i = 0; i < TableTitleSize; i++) {
				TableWriter.append(Double.toString(Precision.round(ModeTables[i], 2)));
				AppendTableComma(i);
			}
			TableWriter.append("\n");

			for (int i = 0; i < ColumnTitleSize; i++) {
				ColumnWriter.append(Double.toString(Precision.round(ModeColumns[i], 2)));
				AppendColumnComma(i);
			}
			ColumnWriter.append("\n");

		} catch (Exception e) {
			Logger.Log(e);
		}

	}

	private void ReadMeasurements() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(TablePath));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("\n")) {
					break;
				}
				tableRecords.add(line);
			}
			reader.close();

			reader = new BufferedReader(new FileReader(ColumnPath));

			while ((line = reader.readLine()) != null) {
				if (line.equals("\n")) {
					break;
				}
				columnRecords.add(line);
			}
			reader.close();

		} catch (Exception e) {
			
			Logger.Log("Exception occurred trying to read",e);

		}
	}
}
