package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class DataMatrixTest {
	Object[] col1 = {"first", "Integer", 10, 20, 30};
	Object[] col2 = {"second", "Boolean", true, false, true};
	Object[] col3 = {"third", "String", "abc", "def", "ghi"};
	Object[] col4 = {"fourth", "Double", 1.2, 5.3, -100.02};
	Object[] col5 = {"fifth", "Long", 1L, 5L, 140L};
	Object[][] data = {col1, col2, col3, col4, col5};
	DataMatrix dm;

	@Before
	public void init() {
		dm = new DataMatrix(data);
	}

	@Test(expected = TypeException.class)
	public void init_exception() {
		Object[] col1 = {"first", "Integer", 10, 20, 30};
		Object[] col2 = {"second", "Boolean", true, false, true};
		Object[] col3 = {"third", "String", "abc", "def", "ghi"};
		Object[] col4 = {"fourth", "Type", 1.2, 5.3, -100.02};
		Object[][] data = {col1, col2, col3, col4};
		dm = new DataFrame(data);
	}

	@Test
	public void initCSV() throws IOException {
		dm = new DataFrame("src/test/resources/df.csv");

		assertEquals("Get column number test", 3, dm.getNumCols());
		assertEquals("Get row number test", 4, dm.getNumRows());
		assertEquals("Get first column type test",
					 Integer.class,
					 dm.getColumnType("id")
		);
		assertEquals("Get second column type test",
					 String.class,
					 dm.getColumnType("name")
		);
		assertEquals("Get third column type test",
					 Float.class,
					 dm.getColumnType("sold")
		);
		assertEquals("Get first integer value test",
					 Integer.valueOf(1),
					 dm.getValue("id", 0, Integer.class)
		);
		assertEquals("Get second integer value test",
					 Integer.valueOf(2),
					 dm.getValue("id", 1, Integer.class)
		);
		assertEquals("Get third integer value test",
					 Integer.valueOf(3),
					 dm.getValue("id", 2, Integer.class)
		);
		assertEquals("Get fourth integer value test",
					 Integer.valueOf(4),
					 dm.getValue("id", 3, Integer.class)
		);
		assertEquals("Get first string value test",
					 "John",
					 dm.getValue("name", 0, String.class)
		);
		assertEquals("Get second string value test",
					 "Jane",
					 dm.getValue("name", 1, String.class)
		);
		assertEquals("Get third string value test",
					 "Bob",
					 dm.getValue("name", 2, String.class)
		);
		assertEquals("Get fourth string value test",
					 "Alice",
					 dm.getValue("name", 3, String.class)
		);
		assertEquals("Get first float value test",
					 Float.valueOf(5000.12f),
					 dm.getValue("sold", 0, Float.class)
		);
		assertEquals("Get second float value test",
					 Float.valueOf(6000.0f),
					 dm.getValue("sold", 1, Float.class)
		);
		assertEquals("Get third float value test",
					 Float.valueOf(4000.99f),
					 dm.getValue("sold", 2, Float.class)
		);
		assertEquals("Get fourth float value test",
					 Float.valueOf(-7000.45f),
					 dm.getValue("sold", 3, Float.class)
		);
	}

	@Test
	public void getNumCols() {
		assertEquals("Get column number test", 5, dm.getNumCols());
	}

	@Test
	public void getNumRows() {
		assertEquals("Get row number test", 3, dm.getNumRows());
	}

	@Test
	public void shape() {
		assertEquals("Get row number test", 3, dm.shape()[0]);
		assertEquals("Get row number test", 5, dm.shape()[1]);
	}

	@Test
	public void getColumnType() {
		assertEquals("Get first column type test",
					 Integer.class,
					 dm.getColumnType("first")
		);
		assertEquals("Get second column type test",
					 Boolean.class,
					 dm.getColumnType("second")
		);
		assertEquals("Get third column type test",
					 String.class,
					 dm.getColumnType("third")
		);
		assertEquals("Get fourth column type test",
					 Double.class,
					 dm.getColumnType("fourth")
		);
	}

	@Test
	public void getValue() {
		assertEquals("Get first integer value test",
					 Integer.valueOf(10),
					 dm.getValue("first", 0, Integer.class)
		);
		assertEquals("Get second integer value test",
					 Integer.valueOf(20),
					 dm.getValue("first", 1, Integer.class)
		);
		assertEquals("Get third integer value test",
					 Integer.valueOf(30),
					 dm.getValue("first", 2, Integer.class)
		);
		assertEquals("Get first boolean value test",
					 Boolean.TRUE,
					 dm.getValue("second", 0, Boolean.class)
		);
		assertEquals("Get second boolean value test",
					 Boolean.FALSE,
					 dm.getValue("second", 1, Boolean.class)
		);
		assertEquals("Get third boolean value test",
					 Boolean.TRUE,
					 dm.getValue("second", 2, Boolean.class)
		);
		assertEquals("Get first string value test",
					 "abc",
					 dm.getValue("third", 0, String.class)
		);
		assertEquals("Get second string value test",
					 "def",
					 dm.getValue("third", 1, String.class)
		);
		assertEquals("Get third string value test",
					 "ghi",
					 dm.getValue("third", 2, String.class)
		);
		assertEquals("Get first double value test",
					 Double.valueOf(1.2),
					 dm.getValue("fourth", 0, Double.class)
		);
		assertEquals("Get second double value test",
					 Double.valueOf(5.3),
					 dm.getValue("fourth", 1, Double.class)
		);
		assertEquals("Get third double value test",
					 Double.valueOf(-100.02),
					 dm.getValue("fourth", 2, Double.class)
		);
	}

	@Test
	public void getValueNoSuchElementException() {
		assertThrows("Wrong getting type test 1",
					 NoSuchElementException.class,
					 () -> dm.getValue("col_not_present", 0, Integer.class)
		);
	}

	@Test
	public void getValueTypeException() {
		assertThrows("Wrong getting type test 1",
					 TypeException.class,
					 () -> dm.getValue("first", 2, Boolean.class)
		);
		assertThrows("Wrong getting type test 2",
					 TypeException.class,
					 () -> dm.getValue("third", 3, Integer.class)
		);
		assertThrows("Wrong getting type test 3",
					 TypeException.class,
					 () -> dm.getValue("second", 1, Double.class)
		);
	}

	@Test
	public void addValue() {
		dm.addValue("first", 40);
		assertEquals("Add value test",
					 Integer.valueOf(40),
					 dm.getValue("first", 3, Integer.class)
		);
		assertNull("Add null", dm.getValue("second", 3, Boolean.class));
		assertNull("Add null", dm.getValue("third", 3, String.class));
	}

	@Test
	public void addValueNoSuchElementException() {
		assertThrows("No such label to add value",
					 NoSuchElementException.class,
					 () -> dm.addValue("column_not_pressent", 1)
		);
	}

	@Test
	public void addValueTypeException() {
		assertThrows("Wrong adding type test",
					 TypeException.class,
					 () -> dm.addValue("fourth", "test")
		);
	}

	@Test
	public void setValue() {
		dm.setValue("first", 1, 15);
		assertEquals("Set value test",
					 Integer.valueOf(15),
					 dm.getValue("first", 1, Integer.class)
		);
	}

	@Test
	public void setValueNoSuchElementException() {
		assertThrows("Wrong setting type test",
					 NoSuchElementException.class,
					 () -> dm.setValue("column_not_present", 0, "test")
		);
	}

	@Test
	public void setValueTypeException() {
		assertThrows("No such label to set value",
					 TypeException.class,
					 () -> dm.setValue("fourth", 0, "test")
		);
	}

	@Test
	public void replaceValue() {
		dm.replaceValue("first", 10, 15);
		assertEquals("Replace value test",
					 Integer.valueOf(15),
					 dm.getValue("first", 0, Integer.class)
		);
	}

	@Test
	public void replaceValueNoSuchElementExceptionLabel() {
		assertThrows("No such label to replace value",
					 NoSuchElementException.class,
					 () -> dm.replaceValue("column not present", 1.3, "test")
		);
	}

	@Test
	public void replaceValueNoSuchElementExceptionValue() {
		assertThrows("No such value to replace",
					 NoSuchElementException.class,
					 () -> dm.replaceValue("third", "old", "new")
		);
	}

	@Test
	public void replaceValueTypeException() {
		assertThrows("Wrong replacing type test",
					 TypeException.class,
					 () -> dm.replaceValue("fourth", 5.3, "test")
		);
	}

	@Test
	public void removeValue() {
		dm.removeValue("third", "def");
		assertEquals("Remove value test",
					 "ghi",
					 dm.getValue("third", 1, String.class)
		);
	}

	@Test
	public void removeValueNoSuchElementExceptionLabel() {
		assertThrows("No such label to remove value",
					 NoSuchElementException.class,
					 () -> dm.removeValue("column_not_present", "test")
		);
	}

	@Test
	public void removeValueNoSuchElementExceptionValue() {
		assertThrows("No such value to remove",
					 NoSuchElementException.class,
					 () -> dm.removeValue("third", "test")
		);
	}

	@Test
	public void removeValueTypeException() {
		assertThrows("Wrong removing type test",
					 TypeException.class,
					 () -> dm.removeValue("fourth", "test")
		);
	}

	@Test
	public void removeIdxValue() {
		dm.removeValue("first", 1);
		assertEquals("Set value test",
					 Integer.valueOf(30),
					 dm.getValue("first", 1, Integer.class)
		);
		assertNull("Last value", dm.getValue("first", 2, Integer.class));
	}

	@Test
	public void removeIdxValueNoSuchElementException() {
		assertThrows("No such label to remove value",
					 NoSuchElementException.class,
					 () -> dm.removeValue("column_not_present", 1)
		);
	}

	@Test
	public void removeIdxValueIdxOutOfBoundException() {
		assertThrows("No such label to remove value",
					 IndexOutOfBoundsException.class,
					 () -> dm.removeValue("first", 10)
		);
	}

	@Test
	public void getColumn() {
		assertEquals("Get first value of first column test",
					 Integer.valueOf(10),
					 dm.getColumn("first", Integer.class).get(0)
		);
		assertEquals("Get second value of first column test",
					 Integer.valueOf(20),
					 dm.getColumn("first", Integer.class).get(1)
		);
		assertEquals("Get third value of first column test",
					 Integer.valueOf(30),
					 dm.getColumn("first", Integer.class).get(2)
		);
		assertEquals("Get first value of second column test",
					 Boolean.TRUE,
					 dm.getColumn("second", Boolean.class).get(0)
		);
		assertEquals("Get second value of second column test",
					 Boolean.FALSE,
					 dm.getColumn("second", Boolean.class).get(1)
		);
		assertEquals("Get third value of second column test",
					 Boolean.TRUE,
					 dm.getColumn("second", Boolean.class).get(2)
		);
		assertEquals("Get first value of third column test",
					 "abc",
					 dm.getColumn("third", String.class).get(0)
		);
		assertEquals("Get second value of third column test",
					 "def",
					 dm.getColumn("third", String.class).get(1)
		);
		assertEquals("Get third value of third column test",
					 "ghi",
					 dm.getColumn("third", String.class).get(2)
		);
		assertEquals("Get first value of fourth column test",
					 Double.valueOf(1.2),
					 dm.getColumn("fourth", Double.class).get(0)
		);
		assertEquals("Get second value of fourth column test",
					 Double.valueOf(5.3),
					 dm.getColumn("fourth", Double.class).get(1)
		);
		assertEquals("Get third value of fourth column test",
					 Double.valueOf(-100.02),
					 dm.getColumn("fourth", Double.class).get(2)
		);
	}

	@Test
	public void getColumnNoSuchElementException() {
		assertThrows("No such label to get column",
					 NoSuchElementException.class,
					 () -> dm.getColumn("column_not_exist", Boolean.class)
		);
	}

	@Test
	public void getColumnTypeException() {
		assertThrows("Wrong getting type test 1",
					 TypeException.class,
					 () -> dm.getColumn("first", Boolean.class)
		);
		assertThrows("Wrong getting type test 2",
					 TypeException.class,
					 () -> dm.getColumn("third", Integer.class)
		);
		assertThrows("Wrong getting type test 3",
					 TypeException.class,
					 () -> dm.getColumn("second", Double.class)
		);
	}

	@Test
	public void addColumn() {
		Object[] col = {1, null, -23.265, 4.0};

		dm.addColumn("sixth", Double.class, col);

		assertEquals("Column number incremented test", 6, dm.getNumCols());
		assertEquals("Row number unmodified test", 4, dm.getNumRows());
		assertEquals("Added column type test",
					 Double.class,
					 dm.getColumnType("sixth")
		);
		assertEquals("Get first added value test",
					 Double.valueOf(1),
					 dm.getValue("sixth", 0, Double.class)
		);
		assertNull("Get second added value test",
				   dm.getValue("sixth", 1, Double.class)
		);
		assertEquals("Get third added value test",
					 Double.valueOf(-23.265),
					 dm.getValue("sixth", 2, Double.class)
		);
		assertNull("Get last value null, on first",
				   dm.getValue("first", 3, Integer.class)
		);
		assertNull("Get last value null, on forth",
				   dm.getValue("fourth", 3, Double.class)
		);
	}

	@Test
	public void addColumnArgumentException() {
		Object[] col = {1.3, 2.3, 18.0, 42.0};
		assertThrows("Label already exists, choose an other name",
					 IllegalArgumentException.class,
					 () -> dm.addColumn("first", Double.class, col)
		);
	}

	@Test
	public void addColumnTypeException() {
		Object[] col = {1.3, 2.3, "test", 42.0};
		assertThrows("Wrong type in added column",
					 TypeException.class,
					 () -> dm.addColumn("sixth", Double.class, col)
		);
	}

	@Test
	public void setColumn() {
		Double[] col = {1.3, null, -23.265, 42.0};

		dm.setColumn("first", Double.class, col);

		assertEquals("Column number unchanged test", 5, dm.getNumCols());
		assertEquals("Row number unchanged test", 3, dm.getNumRows());
		assertEquals("Set column type test",
					 Double.class,
					 dm.getColumnType("first")
		);
		assertEquals("Get first set value test",
					 Double.valueOf(1.3),
					 dm.getValue("first", 0, Double.class)
		);
		assertNull("Get second set value test",
				   dm.getValue("first", 1, Double.class)
		);
		assertEquals("Get third set value test",
					 Double.valueOf(-23.265),
					 dm.getValue("first", 2, Double.class)
		);
	}

	@Test
	public void setColumnNoSuchElementException() {
		Object[] col = {1.3, 2.3, 18.0, 42.0};
		assertThrows("No such label",
					 NoSuchElementException.class,
					 () -> dm.setColumn("column_not_present", Double.class, col)
		);
	}

	@Test
	public void setColumnTypeException() {
		Object[] col = {1.3, 2.3, "test", 42.0};
		assertThrows("Wrong type in set column",
					 TypeException.class,
					 () -> dm.setColumn("first", Double.class, col)
		);
	}

	@Test
	public void removeColumn() {
		dm.removeColumn("second");

		assertEquals("Column number decreased test", 4, dm.getNumCols());
		assertEquals("Row number unchanged test", 3, dm.getNumRows());
	}

	@Test
	public void removeColumnNoSuchElementException() {
		assertThrows("No such label",
					 NoSuchElementException.class,
					 () -> dm.removeColumn("column not present")
		);
	}

	@Test
	public void removeColumnMax() {
		dm.addValue("second", true);
		dm.addValue("second", true);
		dm.addValue("second", true);
		dm.addValue("second", true);
		assertEquals("Row number changed test", 7, dm.getNumRows());

		dm.removeColumn("second");

		assertEquals("Row number unchanged test", 3, dm.getNumRows());
		assertEquals("Column number decreased test", 4, dm.getNumCols());

		dm.addValue("first", 10);
		dm.addValue("third", "true");
		dm.addValue("third", "true");
		dm.addValue("third", "true");
		dm.addValue("third", "true");
		dm.addValue("third", "true");

		assertEquals("Row number changed test", 9, dm.getNumRows());

		/*dm.removeColumn("first");

		assertEquals("Row number unchanged test", 4, dm.getNumRows());
		assertEquals("Column number decreased test", 6, dm.getNumCols());*/
	}

	@Test
	public void testToString() {
		String msg = "third\tsecond\tfifth\tfourth\tfirst\t\n" +
				"abc\ttrue\t1\t1.2\t10\t\n" +
				"def\tfalse\t5\t5.3\t20\t\n" +
				"ghi\ttrue\t140\t-100.02\t30\t\n";

		assertEquals("Print all the DataFrame", msg, dm.toString());
	}
}
