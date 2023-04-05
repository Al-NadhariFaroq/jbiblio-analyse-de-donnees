package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;


public class DataFrameTest {
    DataFrame df ;

    @Before
    public void init() throws IOException {
       df = new DataFrame("target/test-classes/df.csv");
    }

    @Test
    public void addColumn() {
    }

    @Test
    public void numRows() {
        assertEquals(4, df.numRows());
    }

    @Test
    public void numCols() {
    }

    @Test
    public void getValue() {
    }

    @Test
    public void getColumn() {
    }

    @Test
    public void setValue() {
    }

    @Test
    public void setColumn() {
    }

    @Test
    public void removeColumn() {
    }


}