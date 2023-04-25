package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DataFrameTest {
	Object[] col1 = {"first", "Integer", 10, 20, 30};
	Object[] col2 = {"second", "Boolean", true, false, true};
	Object[] col3 = {"third", "String", "abc", "def", "ghi"};
	Object[] col4 = {"fourth", "Double", 1.2, 5.3, -100.02};
	Object[] col5 = {"eighth", "Float", 1.2f, 5.3f, -100.02f};
	Object[] col6 = {"ninth", "Long", 1L, 5L, 9L};
	Object[] col7 = {"fifth", "Long", 1L, 5L, 140L};
	Object[][] data = {col1, col2, col3, col4, col5, col6, col7};
	Object[][] dataCopy = {{"first", "Integer", 10, 20, 30, 40, 90, 100}};
	DataFrame dfCopy;
	DataFrame df;

	@Before
	public void init() throws TypeException {
		df = new DataFrame(data);
		dfCopy = new DataFrame(dataCopy);
	}

	@Test
	public void testGetMinInteger() {
		assertEquals("the result is the minimum value in the list",
					 Integer.valueOf(10),
					 df.getMin("first")
		);
	}

	@Test
	public void testGetMinLong() {
		assertEquals("the result is the minimum value in the list",
					 Long.valueOf(1L),
					 df.getMin("ninth")
		);
	}

	@Test
	public void testGetMinDouble() {
		assertEquals("the result is the minimum value in the list",
					 -100.02,
					 df.getMin("fourth"),
					 0.0
		);
	}

	@Test
	public void testGetMinFloat() {
		assertEquals("the result is the minimum value in the list",
					 -100.02f,
					 df.getMin("eighth"),
					 0.0
		);
	}

	@Test
	public void testGetMaxInteger() {
		assertEquals("the result is the max value in the list",
					 Integer.valueOf(30),
					 df.getMax("first")
		);
	}

	@Test
	public void testGetMaxLong() {
		assertEquals("the result is the max value in the list",
					 Long.valueOf(9L),
					 df.getMax("ninth")
		);
	}

	@Test
	public void testGetMaxDouble() {
		assertEquals("the result is the max value in the list",
					 5.3,
					 df.getMax("fourth"),
					 0.0
		);
	}

	@Test
	public void testGetMaxFloat() {
		assertEquals("the result is the max value in the list",
					 5.3f,
					 df.getMax("eighth"),
					 0.0
		);
	}

	@Test
	public void testGetMeanInteger() {
		assertEquals("the result is the mean value in the list",
					 20,
					 df.getMean("first"),
					 0.0
		);
	}

	@Test
	public void testGetMeanLong() {
		assertEquals("the result is the mean value in the list",
					 5L,
					 df.getMean("ninth"),
					 0.0
		);
	}

	@Test
	public void testGetMeanDouble() {
		assertEquals("the result is the mean value in the list",
					 -31.173333333333332,
					 df.getMean("fourth"),
					 0.0
		);
	}

	@Test
	public void testGetMeanFloat() {
		assertEquals("the result is the mean value in the list",
					 -31.17333221435547f,
					 df.getMean("eighth"),
					 0.0
		);
	}

	@Test
	public void testGetMedianInteger() {
		assertEquals("the result is the median value in the list",
					 20,
					 df.getMedian("first"),
					 0.0
		);
	}

	@Test
	public void testGetMedianLong() {
		assertEquals("the result is the median value in the list",
					 5L,
					 df.getMedian("ninth"),
					 0.0
		);
	}

	@Test
	public void testGetMedianLong2() {
		assertEquals("the result is the median value in the list",
					 55,
					 dfCopy.getMedian("first"),
					 0.0
		);
	}

	@Test
	public void testGetMedianDouble() {
		assertEquals("the result is the median value in the list",
					 1.2,
					 df.getMedian("fourth"),
					 0.0
		);
	}

	@Test
	public void testGetMedianFloat() {
		assertEquals("the result is the median value in the list",
					 1.2000000476837158f,
					 df.getMedian("eighth"),
					 0.0
		);
	}

	@Test
	public void testColumnInvalidType() {
		assertThrows("type not supported",
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
		assertThrows("invalid column label: does not exist",
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
	public void printLastLines() {
		Object[] col1 = {"first",
						 "Integer",
						 10,
						 20,
						 30,
						 40,
						 50,
						 60,
						 70,
						 80,
						 90,
						 100
		};
		Object[] col2 = {"second",
						 "Boolean",
						 true,
						 false,
						 true,
						 true,
						 true,
						 false,
						 false,
						 false,
						 false,
						 true
		};
		Object[] col3 = {"third",
						 "String",
						 "abc",
						 "def",
						 "ghi",
						 "kmp",
						 "MB",
						 "MF",
						 "SW:D",
						 "SW:¨P",
						 "DP",
						 "MS"
		};
		Object[] col4 = {"fourth",
						 "Double",
						 1.2,
						 5.3,
						 -100.02,
						 4.5,
						 6.021,
						 8.7,
						 639.0,
						 589.0,
						 7.12,
						 10.0
		};
		Object[] col5 = {"eighth",
						 "Float",
						 1.2f,
						 5.3f,
						 -100.02f,
						 1.3f,
						 58.2f,
						 89.7f,
						 null,
						 -100.02f,
						 1.3f,
						 58.2f
		};
		Object[] col6 = {"ninth",
						 "Long",
						 1L,
						 5L,
						 9L,
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[] col7 = {"fifth",
						 "Long",
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[][] data = {col1, col2, col3, col4, col5, col6, col7};
		DataFrame df = new DataFrame(data);

		String msg = "third\tsecond\tfifth\tfourth\tfirst\tninth\teighth\t\n" +
					 "SW:D\tfalse\t45\t639.0\t70\t1\tnull\t\n" +
					 "SW:¨P\tfalse\t1\t589.0\t80\t5\t-100.02\t\n" +
					 "DP\tfalse\t5\t7.12\t90\t140\t1.3\t\n" +
					 "MS\ttrue\t140\t10.0\t100\t45\t58.2\t\n" +
					 "null\tnull\t45\tnull\tnull\tnull\tnull\t\n";

		assertEquals("Print message test", msg, df.tail(5));
	}

	@Test
	public void printFirstLines() {
		Object[] col1 = {"first",
						 "Integer",
						 10,
						 20,
						 30,
						 40,
						 50,
						 60,
						 70,
						 80,
						 90,
						 100
		};
		Object[] col2 = {"second",
						 "Boolean",
						 true,
						 false,
						 true,
						 true,
						 true,
						 false,
						 false,
						 false,
						 false,
						 true
		};
		Object[] col3 = {"third",
						 "String",
						 "abc",
						 "def",
						 "ghi",
						 "kmp",
						 "MB",
						 "MF",
						 "SW:D",
						 "SW:¨P",
						 "DP",
						 "MS"
		};
		Object[] col4 = {"fourth",
						 "Double",
						 1.2,
						 5.3,
						 -100.02,
						 4.5,
						 6.021,
						 8.7,
						 639.0,
						 589.0,
						 7.12,
						 10.0
		};
		Object[] col5 = {"eighth",
						 "Float",
						 1.2f,
						 5.3f,
						 -100.02f,
						 1.3f,
						 58.2f,
						 89.7f,
						 null,
						 -100.02f,
						 1.3f,
						 58.2f
		};
		Object[] col6 = {"ninth",
						 "Long",
						 1L,
						 5L,
						 9L,
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[] col7 = {"fifth",
						 "Long",
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[][] data = {col1, col2, col3, col4, col5, col6, col7};
		DataFrame df = new DataFrame(data);

		String msg = "third\tsecond\tfifth\tfourth\tfirst\tninth\teighth\t\n" +
					 "abc\ttrue\t1\t1.2\t10\t1\t1.2\t\n" +
					 "def\tfalse\t5\t5.3\t20\t5\t5.3\t\n" +
					 "ghi\ttrue\t140\t-100.02\t30\t9\t-100.02\t\n" +
					 "kmp\ttrue\t1\t4.5\t40\t1\t1.3\t\n" +
					 "MB\ttrue\t5\t6.021\t50\t5\t58.2\t\n";

		assertEquals("Print message test", msg, df.head(5));
	}

	@Test
	public void printNFirstLines() {
		Object[] col1 = {"first",
						 "Integer",
						 10,
						 20,
						 30,
						 40,
						 50,
						 60,
						 70,
						 80,
						 90,
						 100
		};
		Object[] col2 = {"second",
						 "Boolean",
						 true,
						 false,
						 true,
						 true,
						 true,
						 false,
						 false,
						 false,
						 false,
						 true
		};
		Object[] col3 = {"third",
						 "String",
						 "abc",
						 "def",
						 "ghi",
						 "kmp",
						 "MB",
						 "MF",
						 "SW:D",
						 "SW:¨P",
						 "DP",
						 "MS"
		};
		Object[] col4 = {"fourth",
						 "Double",
						 1.2,
						 5.3,
						 -100.02,
						 4.5,
						 6.021,
						 8.7,
						 639.0,
						 589.0,
						 7.12,
						 10.0
		};
		Object[] col5 = {"eighth",
						 "Float",
						 1.2f,
						 5.3f,
						 -100.02f,
						 1.3f,
						 58.2f,
						 89.7f,
						 null,
						 -100.02f,
						 1.3f,
						 58.2f
		};
		Object[] col6 = {"ninth",
						 "Long",
						 1L,
						 5L,
						 9L,
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[] col7 = {"fifth",
						 "Long",
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[][] data = {col1, col2, col3, col4, col5, col6, col7};
		DataFrame df = new DataFrame(data);

		String msg = "third\tsecond\tfifth\tfourth\tfirst\tninth\teighth\t\n" +
					 "abc\ttrue\t1\t1.2\t10\t1\t1.2\t\n" +
					 "def\tfalse\t5\t5.3\t20\t5\t5.3\t\n" +
					 "ghi\ttrue\t140\t-100.02\t30\t9\t-100.02\t\n";

		assertEquals("Print message test", msg, df.head(3));
	}

	@Test
	public void printNLastLines() {
		Object[] col1 = {"first",
						 "Integer",
						 10,
						 20,
						 30,
						 40,
						 50,
						 60,
						 70,
						 80,
						 90,
						 100
		};
		Object[] col2 = {"second",
						 "Boolean",
						 true,
						 false,
						 true,
						 true,
						 true,
						 false,
						 false,
						 false,
						 false,
						 true
		};
		Object[] col3 = {"third",
						 "String",
						 "abc",
						 "def",
						 "ghi",
						 "kmp",
						 "MB",
						 "MF",
						 "SW:D",
						 "SW:¨P",
						 "DP",
						 "MS"
		};
		Object[] col4 = {"fourth",
						 "Double",
						 1.2,
						 5.3,
						 -100.02,
						 4.5,
						 6.021,
						 8.7,
						 639.0,
						 589.0,
						 7.12,
						 10.0
		};
		Object[] col5 = {"eighth",
						 "Float",
						 1.2f,
						 5.3f,
						 -100.02f,
						 1.3f,
						 58.2f,
						 89.7f,
						 null,
						 -100.02f,
						 1.3f,
						 58.2f
		};
		Object[] col6 = {"ninth",
						 "Long",
						 1L,
						 5L,
						 9L,
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[] col7 = {"fifth",
						 "Long",
						 1L,
						 5L,
						 140L,
						 1L,
						 5L,
						 140L,
						 45L,
						 1L,
						 5L,
						 140L,
						 45L
		};
		Object[][] data = {col1, col2, col3, col4, col5, col6, col7};
		DataFrame df = new DataFrame(data);

		String msg = "third\tsecond\tfifth\tfourth\tfirst\tninth\teighth\t\n" +
					 "DP\tfalse\t5\t7.12\t90\t140\t1.3\t\n" +
					 "MS\ttrue\t140\t10.0\t100\t45\t58.2\t\n" +
					 "null\tnull\t45\tnull\tnull\tnull\tnull\t\n";

		assertEquals("Print message test", msg, df.tail(3));
	}
}