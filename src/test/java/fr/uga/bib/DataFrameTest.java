package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class DataFrameTest {
    Object[][] data = {{"first", "second", "third", "fourth"}, {10, true, "abc", 1.2}, {20, false, "def", 5.3}, {30, true, "ghi", -100.02}};
    DataFrame df ;

    @Before
    public void init() throws IOException {
        df = new DataFrame(data);
    }

    @Test
    public void numCols() {
        assertEquals("Column number test", 4, df.numCols());
    }

    @Test
    public void numRows() {
        assertEquals("Row number test", 3, df.numRows());
    }

    @Test
    public void getType() {
        assertEquals("First column type test", Integer.class, df.getType("first"));
        assertEquals("Second column type test", Boolean.class, df.getType("second"));
        assertEquals("Third column type test", String.class, df.getType("third"));
        assertEquals("Fourth column type test", Double.class, df.getType("fourth"));
    }

    @Test
    public void getValue() {
        assertEquals("First integer value test", Integer.valueOf(10), df.getValue("first", 0, Integer.class));
        assertEquals("Second integer value test", Integer.valueOf(20), df.getValue("first", 1, Integer.class));
        assertEquals("Third integer value test", Integer.valueOf(30), df.getValue("first", 2, Integer.class));
        assertEquals("First boolean value test", Boolean.TRUE, df.getValue("second", 0, Boolean.class));
        assertEquals("Second boolean value test", Boolean.FALSE, df.getValue("second", 1, Boolean.class));
        assertEquals("Third boolean value test", Boolean.TRUE, df.getValue("second", 2, Boolean.class));
        assertEquals("First string value test", "abc", df.getValue("third", 0, String.class));
        assertEquals("Second string value test", "def", df.getValue("third", 1, String.class));
        assertEquals("Third string value test", "ghi", df.getValue("third", 2, String.class));
        assertEquals("First double value test", 1.2, df.getValue("fourth", 0, Double.class));
        assertEquals("Second double value test", 5.3, df.getValue("fourth", 1, Double.class));
        assertEquals("Third double value test", -100.02, df.getValue("fourth", 2, Double.class));
    }

    @Test(expected = RuntimeException.class)
    public void getValueWrongType1() {
        Boolean b = df.getValue("first", 2, Boolean.class);
    }

    @Test(expected = RuntimeException.class)
    public void getValueWrongType2() {
        Integer i = df.getValue("third", 3, Integer.class);
    }

    @Test(expected = RuntimeException.class)
    public void getValueWrongType3() {
        Double d = df.getValue("second", 1, Double.class);
    }

    @Test
    public void getColumn() {
        assertEquals("First value of first column test", Integer.valueOf(10), df.getColumn("first", Integer.class).get(0));
        assertEquals("Second value of first column test", Integer.valueOf(20), df.getColumn("first", Integer.class).get(1));
        assertEquals("Third value of first column test", Integer.valueOf(30), df.getColumn("first", Integer.class).get(2));
        assertEquals("First value of second column test", Boolean.TRUE, df.getColumn("second", Boolean.class).get(0));
        assertEquals("Second value of second column test", Boolean.FALSE, df.getColumn("second", Boolean.class).get(1));
        assertEquals("Third value of second column test", Boolean.TRUE, df.getColumn("second", Boolean.class).get(2));
        assertEquals("First value of third column test", "abc", df.getColumn("third", String.class).get(0));
        assertEquals("Second value of third column test", "def", df.getColumn("third", String.class).get(1));
        assertEquals("Third value of third column test", "ghi", df.getColumn("third", String.class).get(2));
        assertEquals("First value of fourth column test", 1.2, df.getColumn("fourth", Double.class).get(0));
        assertEquals("Second value of fourth column test", 5.3, df.getColumn("fourth", Double.class).get(1));
        assertEquals("Third value of fourth column test", -100.02, df.getColumn("fourth", Double.class).get(2));
    }

    @Test(expected = RuntimeException.class)
    public void getColumnWrongType1() {
        List<Boolean> b = df.getColumn("first", Boolean.class);
    }

    @Test(expected = RuntimeException.class)
    public void getColumnWrongType2() {
        List<Integer> i = df.getColumn("third", Integer.class);
    }

    @Test(expected = RuntimeException.class)
    public void getColumnWrongType3() {
        List<Double> d = df.getColumn("second", Double.class);
    }

    @Test
    public void addColumn() {
        Object[] column = {1, 2.3, "test", true};
        df.addColumn("fifth", column);
        assertEquals("Column number incremented test", 5, df.numCols());
        assertEquals("Row number unmodified test", 3, df.numRows());
        assertEquals("Added column type test", String.class, df.getType("fifth"));
        assertEquals("First added value test", "1", df.getValue("fifth", 0, String.class));
        assertEquals("Second added value test", "2.3", df.getValue("fifth", 1, String.class));
        assertEquals("Third added value test", "test", df.getValue("fifth", 2, String.class));
        assertEquals("Fourth added value test", "true", df.getValue("fifth", 0, String.class));
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