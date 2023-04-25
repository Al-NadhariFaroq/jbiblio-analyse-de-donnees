package fr.uga.bib;

import com.sun.jdi.InvalidTypeException;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class DataFrameTest {
    Object[] col1 = {"first", "Integer", 10, 20, 30};
    Object[] col2 = {"second", "Boolean", true, false, true};
    Object[] col3 = {"third", "String", "abc", "def", "ghi"};
    Object[] col4 = {"fourth", "Double", 1.2, 5.3, -100.02};
    Object[] col5 = {"eighth", "Float", 1.2f, 5.3f, -100.02f};
    Object[] col6 = {"ninth", "Long", 1L, 5L, 9L};
	Object[] col7 = {"fifth", "Long", 1L, 5L, 140L};
	Object[][] data = {col1, col2, col3, col4, col5, col6,col7};
    Object[][] dataCopy = {{"first", "Integer", 10, 20, 30,40, 90, 100}};
    DataFrame dfCopy;
    DataFrame df;

    @Before
    public void init() throws ClassNotFoundException {
        df = new DataFrame(data);
        dfCopy = new DataFrame(dataCopy);
    }

	@Test(expected = IllegalArgumentException.class)
	public void init_exception() throws ClassNotFoundException{
		Object[] col1 = {"first", "java.lang.Intege", 10, 20, 30};
		Object[] col2 = {"second", "java.lang.Boolean", true, false, true};
		Object[] col3 = {"third", "java.lang.String", "abc", "def", "ghi"};
		Object[] col4 = {"fourth", "java.lang.Double", 1.2, 5.3, -100.02};
		Object[][] data = {col1, col2, col3, col4};
		DataFrame df2 = new DataFrame(data);
	}

	@Test
	public void initCSV() throws IOException, ClassNotFoundException {
		df = new DataFrame("src/test/resources/df.csv");

		assertEquals("Get column number test", 3, df.numCols());
		assertEquals("Get row number test", 4, df.numRows());
		assertEquals("Get first column type test",
				Integer.class,
				df.getType("id")
		);
		assertEquals("Get second column type test",
				String.class,
				df.getType("name")
		);
		assertEquals("Get third column type test",
				Float.class,
				df.getType("sold")
		);
		assertEquals("Get first integer value test",
				Integer.valueOf(1),
				df.getValue("id", 0, Integer.class)
		);
		assertEquals("Get second integer value test",
				Integer.valueOf(2),
				df.getValue("id", 1, Integer.class)
		);
		assertEquals("Get third integer value test",
				Integer.valueOf(3),
				df.getValue("id", 2, Integer.class)
		);
		assertEquals("Get fourth integer value test",
				Integer.valueOf(4),
				df.getValue("id", 3, Integer.class)
		);
		assertEquals("Get first string value test",
				"John",
				df.getValue("name", 0, String.class)
		);
		assertEquals("Get second string value test",
				"Jane",
				df.getValue("name", 1, String.class)
		);
		assertEquals("Get third string value test",
				"Bob",
				df.getValue("name", 2, String.class)
		);
		assertEquals("Get fourth string value test",
				"Alice",
				df.getValue("name", 3, String.class)
		);
		assertEquals("Get first float value test",
				Float.valueOf(5000.12f),
				df.getValue("sold", 0, Float.class)
		);
		assertEquals("Get second float value test",
				Float.valueOf(6000.0f),
				df.getValue("sold", 1, Float.class)
		);
		assertEquals("Get third float value test",
				Float.valueOf(4000.99f),
				df.getValue("sold", 2, Float.class)
		);
		assertEquals("Get fourth float value test",
				Float.valueOf(-7000.45f),
				df.getValue("sold", 3, Float.class)
		);
		assertThrows("Wrong getting type test 1",
				ClassCastException.class,
				() -> df.getValue("id", 2, Boolean.class)
		);
		assertThrows("Wrong getting type test 2",
				ClassCastException.class,
				() -> df.getValue("name", 1, Double.class)
		);
		assertThrows("Wrong getting type test 3",
				ClassCastException.class,
				() -> df.getValue("sold", 3, Integer.class)
		);
	}

	@Test
	public void numCols() {
		assertEquals("Get column number test", 7, df.numCols());
	}

	@Test
	public void numRows() {
		assertEquals("Get row number test", 3, df.numRows());
	}

	@Test
	public void getType() {
		assertEquals("Get first column type test",
					 Integer.class,
					 df.getType("first")
		);
		assertEquals("Get second column type test",
					 Boolean.class,
					 df.getType("second")
		);
		assertEquals("Get third column type test",
					 String.class,
					 df.getType("third")
		);
		assertEquals("Get fourth column type test",
					 Double.class,
					 df.getType("fourth")
		);
	}

	@Test
	public void getValue() {
		assertEquals("Get first integer value test",
					 Integer.valueOf(10),
					 df.getValue("first", 0, Integer.class)
		);
		assertEquals("Get second integer value test",
					 Integer.valueOf(20),
					 df.getValue("first", 1, Integer.class)
		);
		assertEquals("Get third integer value test",
					 Integer.valueOf(30),
					 df.getValue("first", 2, Integer.class)
		);
		assertEquals("Get first boolean value test",
					 Boolean.TRUE,
					 df.getValue("second", 0, Boolean.class)
		);
		assertEquals("Get second boolean value test",
					 Boolean.FALSE,
					 df.getValue("second", 1, Boolean.class)
		);
		assertEquals("Get third boolean value test",
					 Boolean.TRUE,
					 df.getValue("second", 2, Boolean.class)
		);
		assertEquals("Get first string value test",
					 "abc",
					 df.getValue("third", 0, String.class)
		);
		assertEquals("Get second string value test",
					 "def",
					 df.getValue("third", 1, String.class)
		);
		assertEquals("Get third string value test",
					 "ghi",
					 df.getValue("third", 2, String.class)
		);
		assertEquals("Get first double value test",
					 Double.valueOf(1.2),
					 df.getValue("fourth", 0, Double.class)
		);
		assertEquals("Get second double value test",
					 Double.valueOf(5.3),
					 df.getValue("fourth", 1, Double.class)
		);
		assertEquals("Get third double value test",
					 Double.valueOf(-100.02),
					 df.getValue("fourth", 2, Double.class)
		);
	}

	@Test
	public void getValue_element_exception() {
		assertThrows("Wrong getting type test 1",
				NoSuchElementException.class,
				() -> df.getValue("col_not_present", 0, Integer.class)
		);
	}

	@Test
	public void getValue_cast_exception() {
		assertThrows("Wrong getting type test 1",
				ClassCastException.class,
				() -> df.getValue("first", 2, Boolean.class)
		);
		assertThrows("Wrong getting type test 2",
				ClassCastException.class,
				() -> df.getValue("third", 3, Integer.class)
		);
		assertThrows("Wrong getting type test 3",
				ClassCastException.class,
				() -> df.getValue("second", 1, Double.class)
		);

	}

	@Test
	public void addValue() {
		df.addValue("first", 40);
		assertEquals("Add value test",
					 Integer.valueOf(40),
					 df.getValue("first", 3, Integer.class)
		);
		assertNull("Add null",
				df.getValue("second", 3, Boolean.class)
		);
		assertNull("Add null",
				df.getValue("third", 3, String.class)
		);
	}

	@Test
	public void addValue_element_exception() {
		assertThrows("No such label to add value",
				NoSuchElementException.class,
				() -> df.addValue("column_not_pressent", 1)
		);
	}
	@Test
	public void addValue_cast_exception() {
		assertThrows("Wrong adding type test",
				ClassCastException.class,
				() -> df.addValue("second", "test")
		);
	}

	@Test
	public void setValue() {
		df.setValue("first", 1, 15);
		assertEquals("Set value test",
					 Integer.valueOf(15),
					 df.getValue("first", 1, Integer.class)
		);
	}

	@Test
	public void setValue_element_exception(){
		assertThrows("Wrong setting type test",
				NoSuchElementException.class,
				() -> df.setValue("column_not_present", 0, "test")
		);
	}

	@Test
	public void setValue_cast_exception(){
		assertThrows("No such label to set value",
				ClassCastException.class,
				() -> df.setValue("second", 0, "test")
		);
	}

	@Test
	public void replaceValue() {
		df.replaceValue("first", 10, 15);
		assertEquals("Replace value test",
					 Integer.valueOf(15),
					 df.getValue("first", 0, Integer.class)
		);
	}

	@Test
	public void replaceValue_element_exception() {
		assertThrows("No such label to replace value",
				NoSuchElementException.class,
				() -> df.replaceValue("column not present", 1.3, "test")
		);
	}

	@Test
	public void replaceValue_cast_exception() {
		assertThrows("Wrong replacing type test",
				ClassCastException.class,
				() -> df.replaceValue("second", 1.3, "test")
		);
	}

	@Test
	public void removeValue() {
		df.removeValue("third", "def");
		assertEquals("Remove value test",
					 "ghi",
					 df.getValue("third", 1, String.class)
		);
	}
	@Test
	public void removeValue_element_exception() {
		assertThrows("No such label to remove value",
				NoSuchElementException.class,
				() -> df.removeValue("column_not_present", "test")
		);
	}
	@Test
	public void removeValue_cast_exception() {
		assertThrows("Wrong removing type test",
				ClassCastException.class,
				() -> df.removeValue("second", "test")
		);
	}

	@Test
	public void removeIdxValue() {
		df.removeValue("first", 1);
		assertEquals("Set value test",
					 Integer.valueOf(30),
					 df.getValue("first", 1, Integer.class)
		);
		assertNull("Last value",
				df.getValue("first", 2, Integer.class)
		);
	}

	@Test
	public void removeIdxValue_element_exception() {
		assertThrows("No such label to remove value",
				NoSuchElementException.class,
				() -> df.removeValue("column_not_present", 1)
		);
	}

	@Test
	public void removeIdxValue_idx_exception() {
		assertThrows("No such label to remove value",
				IndexOutOfBoundsException.class,
				() -> df.removeValue("first", 10)
		);
	}


	@Test
	public void getColumn() {
		assertEquals("Get first value of first column test",
					 Integer.valueOf(10),
					 df.getColumn("first", Integer.class).get(0)
		);
		assertEquals("Get second value of first column test",
					 Integer.valueOf(20),
					 df.getColumn("first", Integer.class).get(1)
		);
		assertEquals("Get third value of first column test",
					 Integer.valueOf(30),
					 df.getColumn("first", Integer.class).get(2)
		);
		assertEquals("Get first value of second column test",
					 Boolean.TRUE,
					 df.getColumn("second", Boolean.class).get(0)
		);
		assertEquals("Get second value of second column test",
					 Boolean.FALSE,
					 df.getColumn("second", Boolean.class).get(1)
		);
		assertEquals("Get third value of second column test",
					 Boolean.TRUE,
					 df.getColumn("second", Boolean.class).get(2)
		);
		assertEquals("Get first value of third column test",
					 "abc",
					 df.getColumn("third", String.class).get(0)
		);
		assertEquals("Get second value of third column test",
					 "def",
					 df.getColumn("third", String.class).get(1)
		);
		assertEquals("Get third value of third column test",
					 "ghi",
					 df.getColumn("third", String.class).get(2)
		);
		assertEquals("Get first value of fourth column test",
					 Double.valueOf(1.2),
					 df.getColumn("fourth", Double.class).get(0)
		);
		assertEquals("Get second value of fourth column test",
					 Double.valueOf(5.3),
					 df.getColumn("fourth", Double.class).get(1)
		);
		assertEquals("Get third value of fourth column test",
					 Double.valueOf(-100.02),
					 df.getColumn("fourth", Double.class).get(2)
		);
	}

	@Test
	public void getColumn_element_exception() {
		assertThrows("No such label to get column",
				NoSuchElementException.class,
				() -> df.getColumn("column_not_exist", Boolean.class)
		);
	}

	@Test
	public void getColumn_cast_exception() {
		assertThrows("Wrong getting type test 1",
				ClassCastException.class,
				() -> df.getColumn("first", Boolean.class)
		);
		assertThrows("Wrong getting type test 2",
				ClassCastException.class,
				() -> df.getColumn("third", Integer.class)
		);
		assertThrows("Wrong getting type test 3",
				ClassCastException.class,
				() -> df.getColumn("second", Double.class)
		);
	}

	@Test
	public void addColumn() {
		Object[] column1 = {1, null, -23.265, 4.0};

		df.addColumn("sixth", Double.class, column1);

		assertEquals("Column number incremented test", 8, df.numCols());
		assertEquals("Row number unmodified test", 4, df.numRows());
		assertEquals("Added column type test",
					 Double.class,
					 df.getType("sixth")
		);
		assertEquals("Get first added value test",
					 Double.valueOf(1),
					 df.getValue("sixth", 0, Double.class)
		);
		assertNull("Get second added value test",
				   df.getValue("sixth", 1, Double.class)
		);
		assertEquals("Get third added value test",
					 Double.valueOf(-23.265),
					 df.getValue("sixth", 2, Double.class)
		);
		assertNull("Get last value null, on first",
				df.getValue("first", 3, Integer.class)
		);
		assertNull("Get last value null, on forth",
				df.getValue("fourth", 3, Double.class)
		);
	}

	@Test
	public void addColumn_argument_exception() {
		Object[] column2 = {1.3, 2.3, 18.0, 42.0};
		assertThrows("Label already exists, choose an other name",
				IllegalArgumentException.class,
				() -> df.addColumn("first", Double.class, column2)
		);
	}

	@Test
	public void addColumn_cast_exception() {
		Object[] column2 = {1.3, 2.3, "test", 42.0};
		assertThrows("Wrong type in added column",
				NumberFormatException.class,
				() -> df.addColumn("sixth", Double.class, column2)
		);
	}


	@Test
	public void setColumn() {
		Double[] column1 = {1.3, null, -23.265, 42.0};

		df.setColumn("first", Double.class, column1);

		assertEquals("Column number unchanged test", 7, df.numCols());
		assertEquals("Row number unchanged test", 3, df.numRows());
		assertEquals("Set column type test", Double.class, df.getType("first"));
		assertEquals("Get first set value test",
					 Double.valueOf(1.3),
					 df.getValue("first", 0, Double.class)
		);
		assertNull("Get second set value test",
				   df.getValue("first", 1, Double.class)
		);
		assertEquals("Get third set value test",
					 Double.valueOf(-23.265),
					 df.getValue("first", 2, Double.class)
		);
	}

	@Test
	public void setColumn_element_exception() {
		Object[] column2 = {1.3, 2.3, 18.0, 42.0};
		assertThrows("No such label",
				NoSuchElementException.class,
				() -> df.setColumn("column_not_present", Double.class, column2)
		);
	}

	@Test
	public void setColumn_cast_exception() {
		Object[] column2 = {1.3, 2.3, "test", 42.0};
		assertThrows("Wrong type in set column",
				ClassCastException.class,
				() -> df.setColumn("first", Double.class, column2)
		);
	}

	@Test
	public void removeColumn() {
		df.removeColumn("second");

        assertEquals("Column number decreased test", 6, df.numCols());
        assertEquals("Row number unchanged test", 3, df.numRows());
    }

    @Test
    public void print() {
        String msg = "third\tsecond\tfifth\tfourth\tfirst\tninth\teighth\t\n" +
                "abc\ttrue\t1\t1.2\t10\t1\t1.2\t\n" +
                "def\tfalse\t5\t5.3\t20\t5\t5.3\t\n" +
                "ghi\ttrue\t140\t-100.02\t30\t9\t-100.02\t\n";
        assertEquals("Print message test", msg, df.toString());
    }

    @Test
    public void testGetMinInteger() throws InvalidTypeException {

        // Call the getMin method with the column name
        int result = df.getMin("first");

        // Assert that the result is the minimum value in the list
        assertEquals(10, result);
    }

    @Test
    public void testGetMinLong() throws InvalidTypeException {

        // Call the getMin method with the column name
        long result = df.getMin("ninth");

        // Assert that the result is the minimum value in the list
        assertEquals(1L, result);
    }

    @Test
    public void testGetMinDouble() throws InvalidTypeException {

        // Call the getMin method with the column name
        double result = df.getMin("fourth");

        // Assert that the result is the minimum value in the list
        assertEquals(-100.02, result, 0.0);
    }


    @Test
    public void testGetMinFloat() throws InvalidTypeException {

        // Call the getMin method with the column name
        float result = df.getMin("eighth");

        // Assert that the result is the minimum value in the list
        assertEquals(-100.02f, result, 0.0);
    }

    @Test
    public void testGetMaxInteger() throws InvalidTypeException {

        // Call the getMax method with the column name
        int result = df.getMax("first");

        // Assert that the result is the max value in the list
        assertEquals(30, result);
    }

    @Test
    public void testGetMaxLong() throws InvalidTypeException {

        // Call the getMax method with the column name
        long result = df.getMax("ninth");

        // Assert that the result is the max value in the list
        assertEquals(9L, result);
    }

    @Test
    public void testGetMaxDouble() throws InvalidTypeException {

        // Call the getMax method with the column name
        double result = df.getMax("fourth");

        // Assert that the result is the max value in the list
        assertEquals(5.3, result, 0.0);
    }

    @Test
    public void testGetMaxFloat() throws InvalidTypeException {

        // Call the getMin method with the column name
        float result = df.getMax("eighth");

        // Assert that the result is the max value in the list
        assertEquals(5.3f, result, 0.0);
    }

    @Test
    public void testGetMeanInteger() throws InvalidTypeException {

        // Call the getMean method with the column name
        int result = df.getMean("first");

        // Assert that the result is the mean value in the list
        assertEquals(20.0, result, 0.0);
    }

    @Test
    public void testGetMeanLong() throws InvalidTypeException {

        // Call the getMean method with the column name
        long result = df.getMean("ninth");

        // Assert that the result is the mean value in the list
        assertEquals(5.0, result, 0.0);
    }

    @Test
    public void testGetMeanDouble() throws InvalidTypeException {

        // Call the getMean method with the column name
        double result = df.getMean("fourth");

        // Assert that the result is the mean value in the list
        assertEquals(-31.173333333333332, result, 0.0);
    }


    @Test
    public void testGetMeanFloat() throws InvalidTypeException {

        // Call the getMean method with the column name
        float result = df.getMean("eighth");

        // Assert that the result is the mean value in the list
        assertEquals(-31.17333221435547, result, 0.0);
    }

    @Test
    public void testGetMedianInteger() throws InvalidTypeException {

        // Call the getMedian method with the column name
        int result = df.getMedian("first");

        // Assert that the result is the median value in the list
        assertEquals(20.0, result, 0.0);
    }

    @Test
    public void testGetMedianLong() throws InvalidTypeException {

        // Call the getMedian method with the column name
        long result = df.getMedian("ninth");

        // Assert that the result is the median value in the list
        assertEquals(5.0, result, 0.0);
    }
    @Test
    public void testGetMedianLong2() throws InvalidTypeException {

        // Call the getMedian method with the column name
        int result = dfCopy.getMedian("first");

        // Assert that the result is the median value in the list
        assertEquals(55.0, result, 0.0);
    }

    @Test
    public void testGetMedianDouble() throws InvalidTypeException {

        // Call the getMedian method with the column name
        double result = df.getMedian("fourth");

        // Assert that the result is the median value in the list
        assertEquals(1.2, result, 0.0);
    }


    @Test
    public void testGetMedianFloat() throws InvalidTypeException {

        // Call the getMedian method with the column name
        float result = df.getMedian("eighth");

        // Assert that the result is the median value in the list
        assertEquals(1.2000000476837158, result, 0.0);
    }

    @Test
    public void testColumnInvalidType() {

        assertThrows("Type not supported. Only accepts integer, long, double or float",
                IllegalArgumentException.class,
                () -> {
                    df.getMax("second");
                    df.getMin("second");
                    df.getMean("second");
                    df.getMedian("second");
                }
        );
    }

	@Test
    public void testColumnInvalid() {

        assertThrows("Invalid column label '%s': does not exist",
                NoSuchElementException.class,
                () -> {
                    df.getMax("invalid");
                    df.getMax("invalid");
                    df.getMean("invalid");
                    df.getMedian("invalid");
                }
        );
    }

	@Test
	public void removeColumn_element_exception() {
		assertThrows("No such label",
				NoSuchElementException.class,
				() -> df.removeColumn("column not present")
		);
	}

}