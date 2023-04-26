package fr.uga.bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Here for all helper methods that are not available for users
 */
public class DataMatrix {
	protected final Hashtable<String, List<Object>> dataFrame;
	protected final Hashtable<String, String> typeFrame;

	protected final String noExistentColLbl
			= "Invalid column label '%s': does not exist";
	protected final String existingColLbl
			= "Invalid column label '%s': already exist";
	protected final String noExistentValue
			= "Invalid value '%s': does not exist in column '%s'";
	protected final String typeNoMatchColType
			= "Invalid type '%s': does not match the column type '%s'";
	protected final String valNoMatchColType
			= "Invalid value type '%s': does not match the column type '%s'";

	/**
	 * Create a DataMatrix for a table of values.
	 *
	 * @param inputData an array of arrays of values.
	 * @throws TypeException if a value type is not supported.
	 */
	public DataMatrix(Object[][] inputData) throws TypeException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		initialize(inputData);
	}

	/**
	 * Create a DataMatrix from a CSV file.
	 *
	 * @param filename the CSV file name where to read the data.
	 * @throws IOException   if the file is unreadable.
	 * @throws TypeException if a value type is not supported.
	 */
	public DataMatrix(String filename) throws TypeException, IOException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		List<String[]> inputRead;
		inputRead = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(",");
			inputRead.add(fields);
		}
		if (inputRead.size() < 2) {
			throw new IOException("Invalid SCV file: no label or type");
		}

		int numCols = inputRead.get(0).length;
		int numRows = inputRead.size();

		Object[][] inputData = new Object[numCols][numRows];
		for (int i = 0; i < numCols; i++) {
			inputData[i][0] = inputRead.get(0)[i];
			inputData[i][1] = inputRead.get(1)[i];
			Class<?> type = Type.checkType(inputRead.get(1)[i]);
			for (int j = 2; j < numRows; j++) {
				inputData[i][j] = Type.convertString(type, inputRead.get(j)[i]);
			}
		}

		initialize(inputData);
		br.close();
	}

	/**
	 * Fill the dataFrame with de given data.
	 *
	 * @param inputData an array of arrays of values.
	 * @throws TypeException if a value type is not supported.
	 */
	private void initialize(Object[][] inputData) throws TypeException {
		for (Object[] column : inputData) {
			String label = String.valueOf(column[0]);
			String typeName = String.valueOf(column[1]);
			Class<?> type = Type.checkType(typeName);
			Object[] data = Arrays.copyOfRange(column, 2, column.length);
			addColumn(label, type, data);
		}
	}

	/**
	 * Returns the number of columns in the dataFrame.
	 *
	 * @return the number of columns.
	 */
	public int getNumCols() {
		return dataFrame.size();
	}

	/**
	 * Returns the number of rows in the dataFrame.
	 *
	 * @return the number of rows.
	 */
	public int getNumRows() {
		if (getNumCols() == 0) {
			return 0;
		}
		return dataFrame.elements().nextElement().size();
	}

	/**
	 * Returns the shape of the dataFrame as an integer array.
	 *
	 * @return an integer array of length 2, where the first element is the
	 * number of rows and the second element is the number of columns.
	 */
	public int[] shape() {
		return new int[]{getNumRows(), getNumCols()};
	}

	/**
	 * Return the type of the given column.
	 *
	 * @param label the label of a column.
	 * @return the type of the column.
	 */
	public Class<?> getColumnType(String label) {
		return Type.checkType(typeFrame.get(label));
	}

	/**
	 * Return the value at the given index on the given column.
	 *
	 * @param label the label of the column.
	 * @param idx   the index in the column.
	 * @param type  the desired return type.
	 * @return the value at the given index on the given column.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the given return type does not match
	 *                                the column type.
	 */
	public <T> T getValue(String label, int idx, Class<T> type) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		if (!type.equals(colType)) {
			String msg = String.format(typeNoMatchColType,
									   type.getSimpleName(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
		}

		Object value = dataFrame.get(label).get(idx);
		return Type.convert(type, value);
	}

	/**
	 * Add a value at the end of the given column.
	 * <p>
	 * Add null values at end of all other columns.
	 *
	 * @param label the label of the column.
	 * @param value the value to add.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the given return type does not match
	 *                                the column type.
	 */
	public <T> void addValue(String label, T value) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		try {
			dataFrame.get(label).add(Type.convert(colType, value));
		} catch (TypeException e) {
			String msg = String.format(valNoMatchColType,
									   value.getClass(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
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
	 * Set the value at the given index on the given column.
	 *
	 * @param label the label of the column.
	 * @param idx   the index in the column.
	 * @param value the value to set.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the new value type does not match the
	 *                                column type.
	 */
	public <T> void setValue(String label, int idx, T value) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		try {
			dataFrame.get(label).set(idx, Type.convert(colType, value));
		} catch (TypeException e) {
			String msg = String.format(valNoMatchColType,
									   value.getClass(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
		}
	}

	/**
	 * Replace a value in a column by another.
	 *
	 * @param label    the label of the column.
	 * @param oldValue the value to replace.
	 * @param newValue the value to set instead.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the new value type does not match the
	 *                                column type.
	 */
	public <T> void replaceValue(String label, T oldValue, T newValue) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		try {
			int idx = dataFrame.get(label).indexOf(oldValue);
			if (idx < 0) {
				String msg = String.format(noExistentValue,
										   oldValue.toString(),
										   label
				);
				throw new NoSuchElementException(msg);
			}
			dataFrame.get(label).set(idx, Type.convert(colType, newValue));
		} catch (TypeException e) {
			String msg = String.format(valNoMatchColType,
									   newValue.getClass().getSimpleName(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
		}
	}

	/**
	 * Remove the value from the column.
	 * <p>
	 * Add a null value to the end of the column.
	 *
	 * @param label the label of the column.
	 * @param value the value to remove.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the value type does not match the
	 *                                column type.
	 */
	public <T> void removeValue(String label, T value) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		try {
			boolean rm = dataFrame.get(label)
								  .remove(Type.convert(colType, value));
			if (!rm) {
				String msg = String.format(noExistentValue,
										   value.toString(),
										   label
				);
				throw new NoSuchElementException(msg);
			}
			dataFrame.get(label).add(null);
		} catch (TypeException e) {
			String msg = String.format(valNoMatchColType,
									   value.getClass().getSimpleName(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
		}
	}

	/**
	 * Remove the value at the given index on the given column.
	 *
	 * @param label the label of the column.
	 * @param idx   the index of the value to remove.
	 * @throws NoSuchElementException    if the label correspond to no column.
	 * @throws IndexOutOfBoundsException if the given index is too big.
	 */
	public void removeValue(String label, int idx) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		dataFrame.get(label).remove(idx);
		dataFrame.get(label).add(null);
	}

	/**
	 * Return the values of the given column.
	 *
	 * @param label the label of the column.
	 * @param type  the desired return type.
	 * @return an array with the values of the column.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if the given type does not match the
	 *                                column type.
	 */
	public <T> List<T> getColumn(String label, Class<T> type) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		Class<?> colType = getColumnType(label);
		if (!type.equals(colType)) {
			String msg = String.format(typeNoMatchColType,
									   type.getSimpleName(),
									   colType.getSimpleName()
			);
			throw new TypeException(msg);
		}

		List<T> out = new ArrayList<>();
		for (Object value : dataFrame.get(label)) {
			out.add(Type.convert(type, value));
		}
		return out;
	}

	/**
	 * Add a column in dataFrame.
	 * <p>
	 * Add null values in the new column or in other ones.
	 *
	 * @param label the label of the new column.
	 * @param type  the type of the values of the new columns.
	 * @param data  the values in this new column.
	 * @throws IllegalArgumentException if the label correspond to an existent
	 *                                  column.
	 * @throws TypeException            if a value type does not match the given
	 *                                  column type.
	 */
	public <T> void addColumn(String label, Class<T> type, Object[] data) {
		if (dataFrame.containsKey(label)) {
			String msg = String.format(existingColLbl, label);
			throw new IllegalArgumentException(msg);
		}

		String typeName = Type.checkType(type);
		int currentLength = getNumRows();

		List<Object> colData = new ArrayList<>();
		for (Object value : data) {
			colData.add(Type.convert(type, value));
		}

		dataFrame.put(label, colData);
		typeFrame.put(label, typeName);

		if (dataFrame.size() != 1) {
			int dataLength = data.length;
			int numNull = abs(dataLength - currentLength);

			if (dataLength > currentLength) {
				Iterator<String> labelIt = dataFrame.keys().asIterator();
				while (labelIt.hasNext()) {
					String lb = labelIt.next();
					if (!lb.equals(label)) {
						for (int i = 0; i < numNull; i++) {
							dataFrame.get(lb).add(null);
						}
					}
				}
			} else if (dataLength < currentLength) {
				for (int i = 0; i < numNull; i++) {
					dataFrame.get(label).add(null);
				}
			}
		}
	}

	/**
	 * Set new values in the given column.
	 *
	 * @param label the label of the column.
	 * @param type  the type of the new values.
	 * @param data  the new values to set.
	 * @throws NoSuchElementException if the label correspond to no column.
	 * @throws TypeException          if a value type does not match the given
	 *                                column type.
	 */
	public <T> void setColumn(String label, Class<T> type, Object[] data) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		String typeName = Type.checkType(type);

		for (Object value : data) {
			try {
				Type.convert(type, value);
			} catch (TypeException e) {
				String msg = String.format(valNoMatchColType,
										   value.getClass().getSimpleName(),
										   typeName
				);
				throw new TypeException(msg);
			}
		}

		dataFrame.replace(label, new ArrayList<>(Arrays.asList(data)));
		typeFrame.put(label, typeName);
	}

	/**
	 * Remove the given column from dataFrame.
	 *
	 * @param label the label of the column.
	 * @throws NoSuchElementException if the label correspond to no column.
	 */
	public void removeColumn(String label) {
		if (!dataFrame.containsKey(label)) {
			String msg = String.format(noExistentColLbl, label);
			throw new NoSuchElementException(msg);
		}

		dataFrame.remove(label);

		int nbRows = getNumRows() - 1;
		int j = nbRows;
		int max = 0;
		Iterator<String> labelIt = dataFrame.keys().asIterator();
		while (labelIt.hasNext()) {
			String lb = labelIt.next();
			Class<?> type = getColumnType(lb);
			while (getValue(lb, j, type) == null && j > 0) {
				j--;
			}
			if (j == getNumRows()) {
				return;
			} else if (j > max) {
				max = j;
			}
		}

		labelIt = dataFrame.keys().asIterator();
		while (labelIt.hasNext()) {
			String lb = labelIt.next();
			if (nbRows >= max + 1) {
				dataFrame.get(lb).subList(max + 1, nbRows + 1).clear();
			}
			labelIt.next();
		}
	}
}
