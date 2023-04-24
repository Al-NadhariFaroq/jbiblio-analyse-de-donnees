package fr.uga.bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame {

    private List<List<Object>> data; // le tableau de données
    private List<String> columns; // les noms de colonnes

    public DataFrame(Object[][] inputData) {
        data = new ArrayList<>();
        columns = new ArrayList<>();
        int numRows = inputData.length;
        int numCols = inputData[0].length;
        for (int j = 0; j < numCols; j++) {
            Object[] colData = new Object[numRows];
            for (int i = 0; i < numRows; i++) {
                colData[i] = inputData[i][j];
            }
            addColumn(colData);
        }
    }

    public DataFrame(String filename) throws IOException {
        data = new ArrayList<>();
        columns = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        if (line == null) {
            throw new IOException("File is empty");
        }
        String[] colNames = line.split(",");
        for (String colName : colNames) {
            columns.add(colName);
            data.add(new ArrayList<>());
        }
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) {
                Object fieldValue = parseFieldValue(fields[i]);
                data.get(i).add(fieldValue);
            }
        }
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

    public void addColumn(Object[] colData) {
        List<Object> colList = new ArrayList<>(Arrays.asList(colData));
        columns.add(colList.get(0).toString());
        data.add(colList.subList(1,colList.size()));
    }

    public int numRows() {
        if (data.size() == 0) {
            return 0;
        }
        return data.get(0).size();
    }

    public int numCols() {
        return columns.size();
    }

    public Object getValue(int row, int col) {
        return data.get(col).get(row);
    }

    public Object[] getColumn(int col) {
        return data.get(col).toArray();
    }

    public void setValue(int row, int col, Object value) {
        data.get(col).set(row, value);
    }

    public void setColumn(int col, Object[] colData) {
        data.set(col, new ArrayList<>(Arrays.asList(colData)));
    }

    public void removeColumn(int col) {
        columns.remove(col);
        data.remove(col);
    }


    public void print() {
        for (String colName : columns) {
            System.out.print(colName + "\t");
        }
        System.out.println();

        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                System.out.print(getValue(i,j) + "\t");
            }
            System.out.println();
        }
    }


    /** 
     * @param colName
     * @return Double
     */
    public Double getMean(String colName){
        return 0.0;
    }
    public Double getMedian(String colName){
        return 0.0;
    }

    public Double getMax(String colName){
        return 0.0;
    }

    public Double getMin(String colName){
        return 0.0;
    }

}

