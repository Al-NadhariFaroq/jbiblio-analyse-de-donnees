package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class DataFrameTest {
	Object[] col1 = {"first", "java.lang.Integer", 10, 20, 30};
	Object[] col2 = {"second", "java.lang.Boolean", true, false, true};
	Object[] col3 = {"third", "java.lang.String", "abc", "def", "ghi"};
	Object[] col4 = {"fourth", "java.lang.Double", 1.2, 5.3, -100.02};
	Object[][] data = {col1, col2, col3, col4};

	DataFrame df;

	@Before
	public void init() throws ClassNotFoundException {
		df = new DataFrame(data);
	}

	@Test
	public void numCols() {
		assertEquals("Get column number test", 4, df.numCols());
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
		assertThrows("Wrong setting type test",
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
		Double[] column1 = {1.3, null, -23.265, 42.0};
		Object[] column2 = {1.3, 2.3, "test", 42.0};

		df.addColumn("fifth", Double.class, column1);

		assertEquals("Column number incremented test", 5, df.numCols());
		assertEquals("Row number unmodified test", 3, df.numRows());
		assertEquals("Added column type test",
					 Double.class,
					 df.getType("fifth")
		);
		assertEquals("Get first added value test",
					 Double.valueOf(1.3),
					 df.getValue("fifth", 0, Double.class)
		);
		assertNull("Get second added value test",
				   df.getValue("fifth", 1, Double.class)
		);
		assertEquals("Get third added value test",
					 Double.valueOf(-23.265),
					 df.getValue("fifth", 2, Double.class)
		);
		assertThrows("Wrong type in added column",
					 ClassCastException.class,
					 () -> df.addColumn("sixth", Double.class, column2)
		);
	}

	@Test
	public void setColumn() {
		Double[] column1 = {1.3, null, -23.265, 42.0};
		Object[] column2 = {1.3, 2.3, "test", 42.0};

		df.setColumn("first", Double.class, column1);

		assertEquals("Column number unchanged test", 4, df.numCols());
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
		assertThrows("Wrong type in set column",
					 ClassCastException.class,
					 () -> df.setColumn("first", Double.class, column2)
		);
	}

	@Test
	public void removeColumn() {
		df.removeColumn("second");

		assertEquals("Column number decreased test", 3, df.numCols());
		assertEquals("Row number unchanged test", 3, df.numRows());
	}

	@Test
	public void print() {
		String msg = "third\tsecond\tfourth\tfirst\t\n" +
					 "abc\ttrue\t1.2\t10\t\n" +
					 "def\tfalse\t5.3\t20\t\n" +
					 "ghi\ttrue\t-100.02\t30\t\n";
		assertEquals("Print message test", msg, df.toString());
	}
}