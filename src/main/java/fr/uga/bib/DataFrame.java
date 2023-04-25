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

public class DataFrame {
	private final Hashtable<String, List<Object>> dataFrame;
	private final Hashtable<String, String> typeFrame;

	private final String noExistentColLbl = "Invalid column label '%s': does not exist";
	private final String existingColLbl = "Invalid column label '%s': already exist";
	private final String unsupportedType = "Unsupported type '%s'";
	private final String unableNbConversion = "Unable to convert '%s' to '%s'";
	private final String typeNoMatchColType = "Invalid type '%s': does not match the column type '%s'";
	private final String valNoMatchColType= "Invalid value type '%s': does not match the column type '%s'";


	/**
	 *
	 * @param inputData
	 * @throws ClassNotFoundException
	 */
	public DataFrame(Object[][] inputData) throws ClassNotFoundException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		initialize(inputData);
	}

	/**
	 *
	 * @param filename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public DataFrame(String filename)throws IOException, ClassNotFoundException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		List<String[]> inputRead = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(",");
			inputRead.add(fields);
		}

		int numCols = inputRead.get(0).length;
		int numRows = inputRead.size();

		Object[][] inputData = new Object[numCols][numRows];
		for (int i = 0; i < numCols; i++) {
			inputData[i][0] = inputRead.get(0)[i];
			inputData[i][1] = inputRead.get(1)[i];
			Class<?> type = verifyType(inputRead.get(1)[i]);
			for (int j = 2; j < numRows; j++) {
				inputData[i][j] = convertString(type, inputRead.get(j)[i]);
			}
		}

		initialize(inputData);
		br.close();
	}

	/**
	 *
	 * @param inputData
	 */
	private void initialize(Object[][] inputData) {
		for (Object[] column : inputData) {
			String label = String.valueOf(column[0]);
			String typeName = String.valueOf(column[1]);
			Class<?> type = verifyType(typeName);
			Object[] data = Arrays.copyOfRange(column, 2, column.length);
			addColumn(label, type, data);
		}
	}

	/**
	 *
	 * @param type
	 * @param value
	 * @return
	 * @param <T>
	 */
	private <T> T convert(Class<T> type, Object value) {
		if (value == null) {
			return null;
		}

		String valueTypeName = value.getClass().getSimpleName();
		return (T) switch (valueTypeName.toLowerCase()) {
			case "boolean", "bool" -> convertBoolean(type, (Boolean) value);
			case "byte" -> convertNumber(type, Byte.class, (Byte) value);
			case "short" -> convertNumber(type, Short.class, (Short) value);
			case "integer", "int" ->
					convertNumber(type, Integer.class, (Integer) value);
			case "long" -> convertNumber(type, Long.class, (Long) value);
			case "float" -> convertNumber(type, Float.class, (Float) value);
			case "double" -> convertNumber(type, Double.class, (Double) value);
			case "character", "char" ->
					convertCharacter(type, (Character) value);
			case "string" -> convertString(type, (String) value);
			default -> {
				String msg = String.format(unsupportedType, valueTypeName);
				throw new IllegalArgumentException(msg);
			}
		};
	}

	/**
	 *
	 * @param type
	 * @param value
	 * @return
	 * @param <T>
	 */
	private <T> T convertBoolean(Class<T> type, Boolean value) {
		String typeName = type.getSimpleName();
		T convertValue = (T) switch (typeName.toLowerCase()) {
			case "boolean", "bool" -> value;
			case "byte" -> value ? getOne(Byte.class) : getZero(Byte.class);
			case "short" -> value ? getOne(Short.class) : getZero(Short.class);
			case "integer" ->value ? getOne(Integer.class) : getZero(Integer.class);
			case "long" -> value ? getOne(Long.class) : getZero(Long.class);
			case "float" -> value ? getOne(Float.class) : getZero(Float.class);
			case "double" ->value ? getOne(Double.class) : getZero(Double.class);
			case "character", "char" -> value ? '1' : '0';
			case "string" -> value ? "true" : "false";
			default -> null;
		};
		if (convertValue == null) {
			String msg = String.format(unsupportedType, typeName);
			throw new IllegalArgumentException(msg);
		}
		return convertValue;
	}

	/**
	 *
	 * @param convertType
	 * @param valueType
	 * @param value
	 * @return
	 * @param <T>
	 * @param <U>
	 */
	private <T, U extends Number> T convertNumber(Class<T> convertType,Class<U> valueType, U value) {
		String convertTypeName = convertType.getSimpleName();
		T convertValue = (T) switch (convertTypeName.toLowerCase()) {
			case "boolean", "bool" -> value.equals(getOne(valueType));
			case "byte" -> value.byteValue();
			case "short" -> value.shortValue();
			case "integer" -> value.intValue();
			case "long" -> value.longValue();
			case "float" -> value.floatValue();
			case "double" -> value.doubleValue();
			case "string" -> String.valueOf(value);
			default -> null;
		};
		if (convertValue == null) {
			String msg = String.format(unsupportedType, convertTypeName);
			throw new IllegalArgumentException(msg);
		}
		return convertValue;
	}

	/**
	 *
	 * @param type
	 * @param value
	 * @return
	 * @param <T>
	 */
	private <T> T convertCharacter(Class<T> type, Character value) {
		String typeName = type.getSimpleName();
		if (typeName.equalsIgnoreCase("string")) {
			return (T) String.valueOf((char) value);
		}
		String msg = String.format(unsupportedType, typeName);
		throw new IllegalArgumentException(msg);
	}

	/**
	 *
	 * @param type
	 * @param value
	 * @return
	 * @param <T>
	 */
	private <T> T convertString(Class<T> type, String value) {
		String typeName = type.getSimpleName();
		try {
			return (T) switch (typeName.toLowerCase()) {
				case "boolean", "bool" -> Boolean.valueOf(value);
				case "byte" -> Byte.valueOf(value);
				case "short" -> Short.valueOf(value);
				case "integer", "int" -> Integer.valueOf(value);
				case "long" -> Long.valueOf(value);
				case "float" -> Float.valueOf(value);
				case "double" -> Double.valueOf(value);
				case "character", "char" -> value.charAt(0);
				case "string" -> value;
				default -> {
					String msg = String.format(unsupportedType, typeName);
					throw new IllegalArgumentException(msg);
				}
			};
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			return null; // char case if value is null or empty
		} catch (NumberFormatException e) {
			String msg = String.format(unableNbConversion, value, typeName);
			throw new NumberFormatException(msg);
		}
	}

	/**
	 *
	 * @param type
	 * @return
	 * @param <T>
	 */
	private <T extends Number> T getZero(Class<T> type) {
		String typeName = type.getSimpleName();
		T zero = (T) switch (typeName.toLowerCase()) {
			case "byte" -> (byte) 0;
			case "short" -> (short) 0;
			case "integer" -> 0;
			case "long" -> 0L;
			case "float" -> 0.0f;
			case "double" -> 0.0;
			default -> null;
		};
		if (zero == null) {
			String msg = String.format(unsupportedType, typeName);
			throw new IllegalArgumentException(msg);
		}
		return zero;
	}

	/**
	 *
	 * @param type
	 * @return
	 * @param <T>
	 */
	private <T extends Number> T getOne(Class<T> type) {
		String typeName = type.getSimpleName();
		T one = (T) switch (typeName.toLowerCase()) {
			case "byte" -> (byte) 1;
			case "short" -> (short) 1;
			case "integer" -> 1;
			case "long" -> 1L;
			case "float" -> 1.0f;
			case "double" -> 1.0;
			default -> null;
		};
		if (one == null) {
			String msg = String.format(unsupportedType, typeName);
			throw new IllegalArgumentException(msg);
		}
		return one;
	}

	/**
	 *
	 * @param typeName
	 * @return
	 */
	private Class<?> verifyType(String typeName) {
		return switch (typeName.toLowerCase()) {
			case "boolean", "bool" -> Boolean.class;
			case "byte" -> Byte.class;
			case "short" -> Short.class;
			case "integer", "int" -> Integer.class;
			case "long" -> Long.class;
			case "float" -> Float.class;
			case "double" -> Double.class;
			case "character", "char" -> Character.class;
			case "string" -> String.class;
			default -> {
				String msg = String.format(unsupportedType, typeName);
				throw new IllegalArgumentException(msg);
			}
		};
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	private String verifyType(Class<?> type) {
		if (type.equals(Boolean.class)) {
			return "Boolean";
		} else if (type.equals(Byte.class)) {
			return "Byte";
		} else if (type.equals(Short.class)) {
			return "Short";
		} else if (type.equals(Integer.class)) {
			return "Integer";
		} else if (type.equals(Long.class)) {
			return "Long";
		} else if (type.equals(Float.class)) {
			return "Float";
		} else if (type.equals(Double.class)) {
			return "Double";
		} else if (type.equals(Character.class)) {
			return "Character";
		} else if (type.equals(String.class)) {
			return "String";
		} else {
			String msg = String.format(unsupportedType, type.getSimpleName());
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Returns the number of columns in the dataFrame.
	 * @return The number of columns.
	 */
	public int numCols() {
		return dataFrame.size();
	}

	/**
	 * Returns the number of rows in the dataFrame.
	 * @return The number of rows.
	 */
	public int numRows() {
		if (numCols() == 0) {
			return 0;
		}
		return dataFrame.elements().nextElement().size();
	}

	/**
	 * Returns the shape of the dataFrame as an integer array.
	 * @return An integer array of length 2, where the first element is
	 * 		   the number of rows and the second element is the number of columns.
	 */
	public int[] shape(){
		return new int[]{numRows(),numCols()};
	}


	/**
	 *
	 * @param label
	 * @return
	 */
	public Class<?> getType(String label) {
		try {
			return Class.forName(typeFrame.get(label));
		} catch (ClassNotFoundException e) {
			return null; // never append
		}
	}

	/**
	 *
	 * @param label
	 * @param idx
	 * @param type
	 * @return
	 * @param <T>
	 */
	public <T> T getValue(String label, int idx, Class<T> type) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		if (!type.equals(colType)) {
			throw new ClassCastException(String.format(typeNoMatchColType,
													   type,
													   colType
			));
		}
		Object value = dataFrame.get(label).get(idx);

		if (value != null && value.getClass().equals(Integer.class)) {
			if (type.equals(Float.class)) {
				value = ((Integer) value).floatValue();
			} else if (type.equals(Double.class)) {
				value = ((Integer) value).doubleValue();
			} else if (type.equals(Long.class)) {
				value = ((Integer) value).longValue();
			}
		}

		return type.cast(value);
	}

	/**
	 *
	 * @param label
	 * @param value
	 * @param <T>
	 */
	public <T> void addValue(String label, T value) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		try {
			dataFrame.get(label).add(colType.cast(value));
		} catch (ClassCastException e) {
			throw new ClassCastException(String.format(valNoMatchColType,value.getClass(),colType));
		}

		Iterator<String> labelIt = dataFrame.keys().asIterator();
		while (labelIt.hasNext()) {
			String lb = labelIt.next();
			if (!lb.equals(label)) {
				dataFrame.get(lb).add(colType.cast(null));
			}
		}
	}

	/**
	 *
	 * @param label
	 * @param idx
	 * @param value
	 * @param <T>
	 */
	public <T> void setValue(String label, int idx, T value) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		try {
			dataFrame.get(label).set(idx, colType.cast(value));
		} catch (ClassCastException e) {
			throw new ClassCastException(String.format(valNoMatchColType, value.getClass(),colType));
		}
	}

	/**
	 *
	 * @param label
	 * @param oldValue
	 * @param newValue
	 * @param <T>
	 */
	public <T> void replaceValue(String label, T oldValue, T newValue) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		try {
			int idx = dataFrame.get(label).indexOf(oldValue);
			dataFrame.get(label).set(idx, colType.cast(newValue));
		} catch (ClassCastException e) {
			throw new ClassCastException(String.format(valNoMatchColType,newValue.getClass(), colType));
		}
	}

	/**
	 *
	 * @param label
	 * @param value
	 * @param <T>
	 */
	public <T> void removeValue(String label, T value) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		try {
			dataFrame.get(label).remove(colType.cast(value));
		} catch (ClassCastException e) {
			throw new ClassCastException(String.format(valNoMatchColType, value.getClass(),colType));
		}
		dataFrame.get(label).add(colType.cast(null));
	}

	/**
	 *
	 * @param label
	 * @param idx
	 */
	public void removeValue(String label, int idx) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		dataFrame.get(label).remove(idx);
		dataFrame.get(label).add(dataFrame.get(label).size(), null);
	}

	/**
	 *
	 * @param label
	 * @param type
	 * @return
	 * @param <T>
	 */
	public <T> List<T> getColumn(String label, Class<T> type) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		Class<?> colType = getType(label);
		if (!type.equals(colType)) {
			throw new ClassCastException(String.format(typeNoMatchColType,
													   type,
													   colType
			));
		}
		List<T> out = new ArrayList<>();
		for (Object value : dataFrame.get(label)) {
			out.add(type.cast(value));
		}
		return out;
	}

	/**
	 *
	 * @param label
	 * @param type
	 * @param data
	 * @param <T>
	 */
	public <T> void addColumn(String label, Class<T> type, Object[] data) {
		int lengthActual = numRows();
		if (dataFrame.containsKey(label)) {
			throw new IllegalArgumentException(String.format(existingColLbl, label));
		}
		List<Object> colData = new ArrayList<>();
		for (Object value : data) {
			colData.add(convert(type, value));
		}
		dataFrame.put(label, colData);
		typeFrame.put(label, type.getName());

		if (dataFrame.size() != 1) {
			int lengthData = data.length;
			int numNull = abs(lengthData - lengthActual);
			Class<?> colType = getType(label);

			if (lengthData > lengthActual) {
				Iterator<String> labelIt = dataFrame.keys().asIterator();
				while (labelIt.hasNext()) {
					String lb = labelIt.next();
					if (!lb.equals(label)) {
						for (int i = 0; i < numNull; i++) {
							dataFrame.get(lb).add(colType.cast(null));
						}
					}
				}
			} else if (lengthData < lengthActual) {
				for (int i = 0; i < numNull; i++) {
					dataFrame.get(label).add(colType.cast(null));
				}
			}
		}
	}

	/**
	 *
	 * @param label
	 * @param type
	 * @param data
	 * @param <T>
	 */
	public <T> void setColumn(String label, Class<T> type, Object[] data) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		for (Object value : data) {
			try {
				type.cast(value);
			} catch (ClassCastException e) {
				throw new ClassCastException(String.format(valNoMatchColType,value.getClass(),type.getName()));
			}
		}
		dataFrame.replace(label, new ArrayList<>(Arrays.asList(data)));
		typeFrame.put(label, type.getName());
	}

	/**
	 *
	 * @param label
	 */
	public void removeColumn(String label) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl, label));
		}
		dataFrame.remove(label);
	}


	@Override
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
	/*********************************** Display *************************************/
	/**********************************************************************************/

	/**
	 * Prints the first n rows of the dataFrame.
	 * @param n The number of rows to print from the start of the dataFrame.
	 */
	public void head(int n) {

	}

	/**
	 * Prints the first 5 rows of the dataFrame.
	 */
	public void head() {
		head(5);
	}

	/**
	 * Prints the last n rows of the dataFrame.
	 * @param n The number of rows to print from the end of the dataFrame.
	 */
	public void tail(int n) {

	}

	/**
	 * Prints the last 5 rows of the dataFrame.
	 */
	public void tail() {
		tail(5);
	}

	/**
	 * Prints the entire dataFrame.
	 */
	public void print() {

	}


	/**********************************************************************************/
	/********************************* SUB DATAFRAMES *********************************/
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
	/********************************* STATISTICS *************************************/
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
				case "java.lang.Integer" ->
						sum += ((Integer) value).doubleValue();
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

	/**
	 * Returns the median of a numeric column.
	 * @param colName The name of the column.
	 * @return The median value of the column as a Number.
	 * @throws IllegalArgumentException if the column type is not numeric.
	 */
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


	/**********************************************************************************/
	/********************************* PROTECTED METHODS ******************************/
	/**********************************************************************************/
	/**
	 *
	 * @param colName
	 * @return
	 */
	protected List<?> checkColumn(String colName) {
		List<?> val = new ArrayList<>();
		if (this.dataFrame.containsKey(colName)) {
			String type = this.typeFrame.get(colName);
			try {
				if (type.equals("java.lang.Integer") ||
					type.equals("java.lang.Long") ||
					type.equals("java.lang.Float") ||
					type.equals("java.lang.Double")) {
					val = this.getColumn(colName, Class.forName(type));
				} else {
					String msg = String.format(unsupportedType, type);
					throw new IllegalArgumentException(msg);
				}
			} catch (ClassNotFoundException e) {
				String msg = String.format(unsupportedType, type);
				throw new IllegalArgumentException(msg);
			}
		} else {
			String msg = String.format(noExistentColLbl, colName);
			throw new NoSuchElementException(msg);
		}
		return val;
	}

	public String showLastLines(){
		if(numRows() < 5){
			return toString();
		} else {
			StringBuilder txt = new StringBuilder();

			Iterator<String> labelIt = dataFrame.keys().asIterator();
			while (labelIt.hasNext()) {
				txt.append(labelIt.next()).append("\t");
			}
			txt.append("\n");
			int j = numRows() - 5;
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

	public String showFirstLines(){
		if(numRows() < 5){
			return toString();
		} else {
			StringBuilder txt = new StringBuilder();

			Iterator<String> labelIt = dataFrame.keys().asIterator();
			while (labelIt.hasNext()) {
				txt.append(labelIt.next()).append("\t");
			}
			txt.append("\n");
			int nbRows = numRows();
			for (int i = 0; i < 5; i++) {
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
	 *
	 * @param type
	 * @param value
	 * @return
	 * @param <T>
	 */
	protected <T> T returnType(String type, double value) {
		return switch (type) {
			case "java.lang.Integer" -> (T) Integer.valueOf((int) value);
			case "java.lang.Long" -> (T) Long.valueOf((long) value);
			case "java.lang.Float" -> (T) Float.valueOf((float) value);
			case "java.lang.Double" -> (T) Double.valueOf(value);
			default -> {
				String msg = String.format(unsupportedType, type);
				throw new IllegalArgumentException(msg);
			}
		};
	}
}
