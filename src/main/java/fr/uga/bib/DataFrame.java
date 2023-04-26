package fr.uga.bib;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Here all methods available for user
 */
public class DataFrame extends DataMatrix {

    /* CONSTRUCTORS */

    /**
     * Create a DataFrame from a table of values.
     *
     * @param inputData an array of arrays of values.
     * @throws TypeException if a value type is not supported.
     */
    public DataFrame(Object[][] inputData) throws TypeException {
        super(inputData);
    }

    /**
     * Create a DataFrame from a SCV file.
     *
     * @param filename the SCV file name where to read the data.
     * @throws IOException   if the file is unreadable.
     * @throws TypeException if a value type is not supported.
     */
    public DataFrame(String filename) throws TypeException, IOException {
        super(filename);
    }

    /* DISPLAY */

    /**
     * Prints the first n rows of the dataFrame.
     *
     * @param n the number of rows to print from the start of the DataFrame.
     */
    public String head(int n) {
        if (getNumRows() < n) {
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
                    Class<?> type = getColumnType(label);
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
     *
     * @param n the number of rows to print from the end of the dataFrame.
     */
    public String tail(int n) {
        if (getNumRows() < n) {
            return toString();
        } else {
            StringBuilder txt = new StringBuilder();

            Iterator<String> labelIt = dataFrame.keys().asIterator();
            while (labelIt.hasNext()) {
                txt.append(labelIt.next()).append("\t");
            }
            txt.append("\n");
            int j = getNumRows() - n;
            for (int i = j; i < getNumRows(); i++) {
                labelIt = dataFrame.keys().asIterator();
                while (labelIt.hasNext()) {
                    String label = labelIt.next();
                    Class<?> type = getColumnType(label);
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
    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();

        Iterator<String> labelIt = dataFrame.keys().asIterator();
        while (labelIt.hasNext()) {
            txt.append(labelIt.next()).append("\t");
        }
        txt.append("\n");

        for (int i = 0; i < getNumRows(); i++) {
            labelIt = dataFrame.keys().asIterator();
            while (labelIt.hasNext()) {
                String label = labelIt.next();
                Class<?> type = getColumnType(label);
                txt.append(getValue(label, i, type)).append("\t");
            }
            txt.append("\n");
        }
        return txt.toString();
    }

    /* SELECTION */

    /**
     * Returns a sub-DataFrame containing all rows of the specified columns.
     *
     * @param colNames an array of column names to include in the
     *                 sub-DataFrame.
     * @return a sub-DataFrame containing all rows of the specified columns.
     */
    public DataFrame loc(String... colNames) {
        Object[][] data = {};
        DataFrame subFrame = new DataFrame(data);

        for (String colName : colNames) {
            Class<?> colType = getColumnType(colName);
            Object[] colData = getColumn(colName, colType).toArray();
            subFrame.addColumn(colName, colType, colData);
        }

        return subFrame;
    }

    /**
     * Returns a sub-DataFrame containing all columns and rows between the
     * specified indices.
     *
     * @param startLine the index of the first row to include in the
     *                  sub-DataFrame
     * @param endLine   the index of the last row to include in the
     *                  sub-DataFrame
     * @return a sub-DataFrame containing all columns and rows between the
     * specified indices
     */
    public DataFrame iloc(int startLine, int endLine) {
        Object[][] data = {};
        DataFrame subFrame = new DataFrame(data);

        Iterator<String> labelIt = dataFrame.keys().asIterator();
        while (labelIt.hasNext()) {
            String colName = labelIt.next();
            Class<?> colType = getColumnType(colName);
            List<?> subCol = getColumn(colName, colType).subList(startLine, endLine + 1);
            subFrame.addColumn(colName, colType, subCol.toArray());
        }

        return subFrame;
    }

    /**
     * Returns a DataFrame that satisfies the given condition on a specific
     * column.
     *
     * @param columnName the name of the column to filter.
     * @param condition  the condition to apply on the values of the column.
     * @param value      the value to compare the column values against.
     * @return a DataFrame containing the rows that satisfy the given condition.
     */
    public DataFrame locWhere(String columnName, int condition, Object value) {
        Object[][] data = {};
        DataFrame subFrame = new DataFrame(data);


        return subFrame;
    }

    /* STATISTICS */

    /**
     * Returns the mean of the given column.
     *
     * @param label the label of the column.
     * @return the mean value of the column as a Double.
     * @throws NoSuchElementException if the label correspond to no column.
     * @throws TypeException          if a value can not be converted to a
     *                                numeric value.
     */
    public <T> T getMean(String label) {
        if (!dataFrame.containsKey(label)) {
            String msg = String.format(noExistentColLbl, label);
            throw new NoSuchElementException(msg);
        }

        Class<?> type = getColumnType(label);
        List<?> values = getColumn(label, type);

        double sum = 0.0;
        for (Object value : values) {
            sum += Type.convert(Double.class, value);
        }
        double mean = sum / values.size();

        //noinspection unchecked
        return (T) Type.convert(type, mean);
    }

    /**
     * Return the median of the given column.
     *
     * @param label the label of the column.
     * @return the median value of the column.
     * @throws NoSuchElementException if the label correspond to no column.
     * @throws TypeException          if a value can not be converted to a
     *                                numeric value.
     */
    public <T> T getMedian(String label) {
        if (!dataFrame.containsKey(label)) {
            String msg = String.format(noExistentColLbl, label);
            throw new NoSuchElementException(msg);
        }

        Class<?> type = getColumnType(label);
        List values = getColumn(label, type);

        double result;
        Collections.sort(values);

        int n = values.size();
        if (n % 2 == 0) {
            int middleIndex1 = n / 2;
            int middleIndex2 = middleIndex1 - 1;
            result = Type.convert(Double.class, values.get(middleIndex1)) +
                    Type.convert(Double.class, values.get(middleIndex2)) / 2.0;
        } else {
            int middleIndex = n / 2;
            result = Type.convert(Double.class, values.get(middleIndex));
        }

        //noinspection unchecked
        return (T) Type.convert(type, result);
    }

    /**
     * Returns the maximum value of the given column.
     *
     * @param label the label of the column.
     * @return the maximum value of the column.
     * @throws NoSuchElementException if the label correspond to no column.
     * @throws TypeException          if a value can not be converted to a
     *                                numeric value.
     */
    public <T> T getMax(String label) {
        if (!dataFrame.containsKey(label)) {
            String msg = String.format(noExistentColLbl, label);
            throw new NoSuchElementException(msg);
        }

        Class<?> type = getColumnType(label);
        List values = getColumn(label, type);
        Collections.sort(values);
        //noinspection unchecked
        return (T) Type.convert(type, values.get(values.size() - 1));
    }

    /**
     * Returns the minimum value of the given column.
     *
     * @param label the label of the column.
     * @return the minimum value of the column.
     * @throws NoSuchElementException if the label correspond to no column.
     * @throws TypeException          if a value can not be converted to a
     *                                numeric value.
     */
    public <T> T getMin(String label) {
        if (!dataFrame.containsKey(label)) {
            String msg = String.format(noExistentColLbl, label);
            throw new NoSuchElementException(msg);
        }

        Class<?> type = getColumnType(label);
        List values = getColumn(label, type);
        Collections.sort(values);
        //noinspection unchecked
        return (T) Type.convert(type, values.get(0));
    }
}
