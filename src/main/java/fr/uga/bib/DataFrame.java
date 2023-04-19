package fr.uga.bib;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataFrame {
    private final Hashtable<String, Pair<Class<?>, List<Object>>> dataFrame;

    public DataFrame(Object[][] inputData) {
        dataFrame = new Hashtable<>();

        int numRows = inputData.length;
        int numCols = inputData[0].length;

        for (int j = 0; j < numCols; j++) {
            String colLabel = (String) inputData[0][j];
            Object[] colData = new Object[numRows];
            for (int i = 0; i < numRows; i++) {
                colData[i] = inputData[i][j];
            }
            addColumn(colLabel, colData);
        }
    }

    public DataFrame(String filename) throws IOException {
        dataFrame = new Hashtable<>();

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

    private Class<?> parseColumn(List<Object> column) {
        Class<?> colClass = null;
        for (Object value : column) {
            if (value instanceof String) {
                return value.getClass();
            } else if (colClass == null && value instanceof Boolean) {
                colClass = value.getClass();
            } else if ((colClass == null || colClass.equals(Boolean.class)) && value instanceof Integer) {
                colClass = value.getClass();
            } else if ((colClass == null || colClass.equals(Boolean.class) || colClass.equals(Integer.class)) && value instanceof Float) {
                colClass = value.getClass();
            } else if ((colClass == null || colClass.equals(Boolean.class) || colClass.equals(Integer.class) || colClass.equals(Float.class)) && value instanceof Double) {
                colClass = value.getClass();
            }
        }
        if (colClass == null)
            colClass = String.class;
        return colClass;
    }

    public int numCols() {
        return dataFrame.size();
    }

    public int numRows() {
        if (dataFrame.size() == 0) {
            return 0;
        }
        return dataFrame.values().iterator().next().getValue().size();
    }

    public Class<?> getType(String label) {
        return dataFrame.get(label).getKey();
    }

    public <T> T getValue(String label, int idx, Class<T> type) {
        if (!dataFrame.get(label).getKey().equals(type))
            throw new RuntimeException("The given type doesn't correspond to the column type.");
        return type.cast(dataFrame.get(label).getValue().get(idx));
    }

    public <T> List<T> getColumn(String label, Class<T> type) {
        if (!dataFrame.get(label).getKey().equals(type))
            throw new RuntimeException("The given type doesn't correspond to the column type.");

        List<T> column = new ArrayList<>();
        for(Object value : dataFrame.get(label).getValue()) {
            column.add(type.cast(value));
        }
        return column;
    }

    public void addColumn(String label, Object[] colData) {
        List<Object> colList = new ArrayList<>(Arrays.asList(colData));
        Class<?> colClass = parseColumn(colList);
        dataFrame.put(label, new MutablePair<>(colClass, colList));
    }

    public void setValue(String label, int idx, Object value) {
        dataFrame.get(label).getValue().set(idx, value);
    }

    public void setColumn(String label, Object[] colData) {
        dataFrame.get(label).setValue(new ArrayList<>(Arrays.asList(colData)));
    }

    public void removeColumn(String label) {
        dataFrame.remove(label);
    }

    public void print() {
        Iterator<String> labelIt = dataFrame.keys().asIterator();
        while (labelIt.hasNext()) {
            System.out.print(labelIt.next() + "\t");
        }
        System.out.println();

        for (int i = 0; i < numRows(); i++) {
            labelIt = dataFrame.keys().asIterator();
            while (labelIt.hasNext()) {
                String label = labelIt.next();
                System.out.print(getValue(label, i, dataFrame.get(label).getKey()) + "\t");
            }
            System.out.println();
        }
    }

}

