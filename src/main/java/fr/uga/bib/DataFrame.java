package fr.uga.bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;


/**
 * Here all methods available for user
 */
public class DataFrame extends DataMatrix {

	/**********************************************************************************/
	/****************************** Creation of DataFrame *****************************/
	/**********************************************************************************/
	/**
	 *
	 * @param inputData
	 * @throws ClassNotFoundException
	 */
	public DataFrame(Object[][] inputData) throws ClassNotFoundException {
		super(inputData);
	}

	/**
	 *
	 * @param filename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public DataFrame(String filename) throws IOException, ClassNotFoundException {
		super(filename);
	}

	/**********************************************************************************/
	/***************************** Display of DataFrame *******************************/
	/**********************************************************************************/

	/**
	 * Prints the first n rows of the dataFrame.
	 * @param n The number of rows to print from the start of the dataFrame.
	 */
	public String head(int n) {
		if(numRows() < n){
			return toString();
		} else {
			StringBuilder txt = new StringBuilder();

			Iterator<String> labelIt = dataFrame.keys().asIterator();
			while (labelIt.hasNext()) {
				txt.append(labelIt.next()).append("\t");
			}
			txt.append("\n");
			for (int i = 0; i < n; i++) {
				labelIt = dataFrame.keys().asIterator();
				while (labelIt.hasNext()) {
					String label = labelIt.next();
					Class<?> type = getType(label);
					txt.append(getValue(label, i, type)).append("\t");
				}
				txt.append("\n");
			}
			return txt.toString();
		}
	}

	/**
	 * Prints the first 5 rows of the dataFrame.
	 */
	public String head() {
		return head(5);
	}

	/**
	 * Prints the last n rows of the dataFrame.
	 * @param n The number of rows to print from the end of the dataFrame.
	 */
	public String tail(int n) {
		if(numRows() < n){
			return toString();
		} else {
			StringBuilder txt = new StringBuilder();

			Iterator<String> labelIt = dataFrame.keys().asIterator();
			while (labelIt.hasNext()) {
				txt.append(labelIt.next()).append("\t");
			}
			txt.append("\n");
			int j = numRows() - n;
			for (int i = j; i < numRows(); i++) {
				labelIt = dataFrame.keys().asIterator();
				while (labelIt.hasNext()) {
					String label = labelIt.next();
					Class<?> type = getType(label);
					txt.append(getValue(label, i, type)).append("\t");
				}
				txt.append("\n");
			}
			return txt.toString();
		}
	}

	/**
	 * Prints the last 5 rows of the dataFrame.
	 */
	public String tail() {
		return tail(5);
	}

	/**
	 * Prints the entire dataFrame.
	 */
	public String toString() {
		StringBuilder txt = new StringBuilder();

		Iterator<String> labelIt = dataFrame.keys().asIterator();
		while (labelIt.hasNext()) {
			txt.append(labelIt.next()).append("\t");
		}
		txt.append("\n");

		for (int i = 0; i < numRows(); i++) {
			labelIt = dataFrame.keys().asIterator();
			while (labelIt.hasNext()) {
				String label = labelIt.next();
				Class<?> type = getType(label);
				txt.append(getValue(label, i, type)).append("\t");
			}
			txt.append("\n");
		}
		return txt.toString();
	}



	/**********************************************************************************/
	/***************************** Selection in DatFrame ******************************/
	/**********************************************************************************/

	/**
	 * loc : Returns a sub-DataFrame containing all rows of the specified columns.
	 * @param colNames an array of column names to include in the sub-DataFrame
	 * @return a sub-DataFrame containing all rows of the specified columns
	 * @throws ClassNotFoundException if a column's data type cannot be determined
	 */
	public DataFrame loc(String... colNames) throws ClassNotFoundException {
		Object[][] data = {};
		DataFrame subFrame = new DataFrame(data);

		for (String colName : colNames) {
			Class<?> colType = getType(colName);
			Object[] colData = {getColumn(colName, colType)};
			subFrame.addColumn(colName, colType, colData);
		}

		return subFrame;
	}


	/**
	 * iloc : Returns a sub-DataFrame containing all columns and rows between the specified indices.
	 * @param startLine the index of the first row to include in the sub-DataFrame
	 * @param endLine the index of the last row to include in the sub-DataFrame
	 * @return a sub-DataFrame containing all columns and rows between the specified indices
	 * @throws ClassNotFoundException if a column's data type cannot be determined
	 */
	public DataFrame iloc(int startLine, int endLine) throws ClassNotFoundException {
		Object[][] data = {};
		DataFrame subFrame = new DataFrame(data);

		/*for (int i = startLine; i <= endLine; i++) {

			}

			subFrame.addRow(rowData);*/

		return subFrame;
	}

	/**
	 * Returns a DataFrame that satisfies the given condition on a specific column.
	 * @param columnName The name of the column to filter.
	 * @param condition The condition to apply on the values of the column.
	 * @param value The value to compare the column values against.
	 * @return A DataFrame containing the rows that satisfy the given condition.
	 * @throws ClassNotFoundException If the specified column is not found in the DataFrame.
	 */
	public DataFrame locWhere(String columnName, String condition, Object value) throws ClassNotFoundException {
		Object[][] data = {};
		DataFrame subFrame = new DataFrame(data);


		return subFrame;
	}


	/**********************************************************************************/
	/*************************** Statistics on DataFrame ******************************/
	/**********************************************************************************/

	/**
	 * Returns the mean of a numeric column.
	 * @param colName The name of the column.
	 * @return The mean value of the column as a Double.
	 * @throws IllegalArgumentException if the column type is not numeric.
	 */
	public <T> T getMean(String colName) {
		List<?> values = this.checkColumn(colName);
		String type = this.typeFrame.get(colName);
		double sum = 0.0;
		for (Object value : values) {
			switch (type) {
				case "java.lang.Integer" -> sum += ((Integer) value).doubleValue();
				case "java.lang.Long" -> sum += ((Long) value).doubleValue();
				case "java.lang.Float" -> sum += ((Float) value).doubleValue();
				case "java.lang.Double" -> sum += (Double) value;
				default -> {
					String msg = String.format(unsupportedType, type);
					throw new IllegalArgumentException(msg);
				}
			}
		}
		double mean = sum / values.size();
		return this.returnType(type, mean);
	}

	public <T extends Number> T getMedian(String colName) {
		List values = this.checkColumn(colName);
		String type = this.typeFrame.get(colName);
		double result;
		Collections.sort(values);

		int n = values.size();
		if (n % 2 == 0) {
			int middleIndex1 = n / 2;
			int middleIndex2 = middleIndex1 - 1;
			result = ((Number) values.get(middleIndex1)).doubleValue() +
					((Number) values.get(middleIndex2)).doubleValue() / 2.0;
		} else {
			int middleIndex = n / 2;
			result = ((Number) values.get(middleIndex)).doubleValue();
		}
		return this.returnType(type, result);
	}

	/**

	 Returns the maximum value of a column.
	 @param colName The name of the column.
	 @return The maximum value of the column.
	 */
	public <T> T getMax(String colName) {
		List values = this.checkColumn(colName);
		T result;
		Collections.sort(values);
		result = (T) values.get(values.size() - 1);
		return result;
	}

	/**
	 * Returns the minmum value of a column.
	 * @param colName The name of the column.
	 * @return The minmum value of the column.
	 */
	public <T> T getMin(String colName) {
		List values = this.checkColumn(colName);
		T result;
		Collections.sort(values);
		result = (T) values.get(0);
		return result;
	}

}
