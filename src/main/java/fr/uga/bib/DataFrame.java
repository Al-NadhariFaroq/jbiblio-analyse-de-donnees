package fr.uga.bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

public class DataFrame {
	private final Hashtable<String, List<Object>> dataFrame;
	private final Hashtable<String, String> typeFrame;

	private final String noExistentColLbl = "Invalid column label '%s': does not exist";
	private final String existingColLbl	= "Invalid column label '%s': already exist";
	private final String invalidType = "Invalid type '%s': does not exist";
	private final String typeNoMatchColType	= "Invalid type '%s': does not match the column type '%s'";
	private final String valNoMatchColType = "Invalid value type '%s': does not match the column type '%s'";

	public DataFrame(Object[][] inputData) throws ClassNotFoundException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		for (Object[] column : inputData) {
			String label = String.valueOf(column[0]);
			String typeName = String.valueOf(column[1]);
			Object[] data = Arrays.copyOfRange(column, 2, column.length);
			try {
				Class<?> type = Class.forName(typeName);
				addColumn(label, type, data);
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException(String.format(invalidType,
															   typeName
				));
			}
		}
	}

	public DataFrame(String filename) throws IOException {
		dataFrame = new Hashtable<>();
		typeFrame = new Hashtable<>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line = br.readLine();
		if (line == null) {
			throw new IOException("File is empty");
		}

        /*String[] colNames = line.split(",");
        for (String colName : colNames) {
            columnNames.add(colName);
            data.add(new ArrayList<>());
        }

        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) {
                Object fieldValue = parseFieldValue(fields[i]);
                data.get(i).add(fieldValue);
            }
        }*/

		br.close();
	}

	private Object parseFieldValue(String fieldValue) {
		try {
			return Integer.parseInt(fieldValue);
		} catch (NumberFormatException e) {
			try {
				return Double.parseDouble(fieldValue);
			} catch (NumberFormatException e2) {
				return fieldValue;
			}
		}
	}

	public int numCols() {
		return dataFrame.size();
	}

	public int numRows() {
		if (numCols() == 0) {
			return 0;
		}
		return dataFrame.elements().nextElement().size();
	}

	public Class<?> getType(String label) {
		try {
			return Class.forName(typeFrame.get(label));
		} catch (ClassNotFoundException e) {
			return null; // never append
		}
	}

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

		if(value != null && value.getClass().equals(Integer.class)){
			if(type.equals(Float.class))
				value =  ((Integer) value).floatValue();
			else if(type.equals(Double.class))
				value =  ((Integer) value).doubleValue();
			else if(type.equals(Long.class))
				value =  ((Integer) value).longValue();
		}

		return type.cast(value);
	}

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
			throw new ClassCastException(String.format(valNoMatchColType,
													   value.getClass(),
													   colType
			));
		}

		Iterator<String> labelIt = dataFrame.keys().asIterator();
		while (labelIt.hasNext()) {
			String lb =labelIt.next();
			if(!lb.equals(label))
				dataFrame.get(lb).add(colType.cast(null));
		}
	}

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
			throw new ClassCastException(String.format(valNoMatchColType,
													   value.getClass(),
													   colType
			));
		}
	}

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
			throw new ClassCastException(String.format(valNoMatchColType,
													   newValue.getClass(),
													   colType
			));
		}
	}

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
			throw new ClassCastException(String.format(valNoMatchColType,
													   value.getClass(),
													   colType
			));
		}
		dataFrame.get(label).add(colType.cast(null));
	}

	public void removeValue(String label, int idx) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
		}
		dataFrame.get(label).remove(idx);
		dataFrame.get(label).add(dataFrame.get(label).size(), null);
	}

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

	public <T> void addColumn(String label, Class<T> type, Object[] data) {
		int lengthActual = numRows();
		if (dataFrame.containsKey(label)) {
			throw new IllegalArgumentException(String.format(existingColLbl,
															 label
			));
		}
		for (Object value : data) {
			try {
				if(value != null && value.getClass().equals(Integer.class)){
					if(type.equals(Float.class))
						value =  ((Integer) value).floatValue();
					else if(type.equals(Double.class))
						value =  ((Integer) value).doubleValue();
					else if(type.equals(Long.class))
						value =  ((Integer) value).longValue();
				}
				type.cast(value);
			} catch (ClassCastException e) {
				throw new ClassCastException(String.format(valNoMatchColType,
														   value.getClass(),
														   type.getName()
				));
			}
		}
		dataFrame.put(label, new ArrayList<>(Arrays.asList(data)));
		typeFrame.put(label, type.getName());

		if(dataFrame.size() != 1){
			int lengthData = data.length;
			int numNull = abs(lengthData - lengthActual);
			System.out.println(numNull);
			Class<?> colType = getType(label);

			if(lengthData > lengthActual){
				Iterator<String> labelIt = dataFrame.keys().asIterator();
				while (labelIt.hasNext()) {
					String lb = labelIt.next();
					if(!lb.equals(label)){
						for(int i = 0; i < numNull; i++){
							dataFrame.get(lb).add(colType.cast(null));
						}
					}
				}
			} else if(lengthData < lengthActual){
				for(int i = 0; i < numNull; i++){
					dataFrame.get(label).add(colType.cast(null));
				}
			}
		}
	}

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
				throw new ClassCastException(String.format(valNoMatchColType,
														   value.getClass(),
														   type.getName()
				));
			}
		}
		dataFrame.replace(label, new ArrayList<>(Arrays.asList(data)));
		typeFrame.put(label, type.getName());
	}

	public void removeColumn(String label) {
		if (!dataFrame.containsKey(label)) {
			throw new NoSuchElementException(String.format(noExistentColLbl,
														   label
			));
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
}
