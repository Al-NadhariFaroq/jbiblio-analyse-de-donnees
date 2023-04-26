package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DataFrameTest {
    Object[] col0 = {"bool", "Boolean", true, false, true};
    Object[] col1 = {"byte", "Byte", (byte) 1, (byte) 5, (byte) -100};
    Object[] col2 = {"short", "Short", (short) 1, (short) 5, (short) -100};
    Object[] col3 = {"int", "Integer", 10, 20, 30};
    Object[] col4 = {"long1", "Long", 1L, 5L, 9L};
    Object[] col5 = {"long2", "Long", 1L, 5L, 140L};
    Object[] col6 = {"float", "Float", 1.2f, 5.3f, -100.02f};
    Object[] col7 = {"double", "Double", 1.2, 5.3, -100.02};
    Object[] col8 = {"char", "Character", 'a', 'b', '?'};
    Object[] col9 = {"string", "String", "abc", "def", "ghi"};
    Object[][] data1 = {col0, col1, col2, col3, col4, col5, col6, col7, col8, col9};
    Object[][] data2 = {{"int", "Integer", 10, 20, 30, 40, 90, 100}};
    DataFrame df1;
    DataFrame df2;

    @Before
    public void init() throws TypeException {
        df1 = new DataFrame(data1);
        df2 = new DataFrame(data2);
    }

    @Test
    public void testGetMinInteger() {
        assertEquals("the result is the minimum value in the list",
                Boolean.FALSE,
                df1.getMin("bool")
        );
        assertEquals("the result is the minimum value in the list",
                Byte.valueOf((byte) -100),
                df1.getMin("byte")
        );
        assertEquals("the result is the minimum value in the list",
                Short.valueOf((short) -100),
                df1.getMin("short")
        );
        assertEquals("the result is the minimum value in the list",
                Integer.valueOf(10),
                df1.getMin("int")
        );
        assertEquals("the result is the minimum value in the list",
                Long.valueOf(1L),
                df1.getMin("long1")
        );
        assertEquals("the result is the minimum value in the list",
                Float.valueOf(-100.02f),
                df1.getMin("float")
        );
        assertEquals("the result is the minimum value in the list",
                -100.02,
                df1.getMin("double"),
                0.0
        );
        assertEquals("the result is the minimum value in the list",
                Character.valueOf('?'),
                df1.getMin("char")
        );
        assertEquals("the result is the minimum value in the list",
                "abc",
                df1.getMin("string")
        );
    }

    @Test
    public void testGetMaxInteger() {
        assertEquals("the result is the max value in the list",
                Boolean.TRUE,
                df1.getMax("bool")
        );
        assertEquals("the result is the max value in the list",
                Byte.valueOf((byte) 5),
                df1.getMax("byte")
        );
        assertEquals("the result is the max value in the list",
                Short.valueOf((short) 5),
                df1.getMax("short")
        );
        assertEquals("the result is the max value in the list",
                Integer.valueOf(30),
                df1.getMax("int")
        );
        assertEquals("the result is the max value in the list",
                Long.valueOf(9L),
                df1.getMax("long1")
        );
        assertEquals("the result is the max value in the list",
                Float.valueOf(5.3f),
                df1.getMax("float")
        );
        assertEquals("the result is the max value in the list",
                5.3,
                df1.getMax("double"),
                0.0
        );
        assertEquals("the result is the max value in the list",
                Character.valueOf('b'),
                df1.getMax("char")
        );
        assertEquals("the result is the max value in the list",
                "ghi",
                df1.getMax("string")
        );
    }

    @Test
    public void testGetMeanInteger() {
        assertEquals("the result is the mean value in the list",
                Boolean.FALSE,
                df1.getMean("bool")
        );
        assertEquals("the result is the mean value in the list",
                Byte.valueOf((byte) -31),
                df1.getMean("byte")
        );
        assertEquals("the result is the mean value in the list",
                Short.valueOf((short) -31),
                df1.getMean("short")
        );
        assertEquals("the result is the mean value in the list",
                Integer.valueOf(20),
                df1.getMean("int")
        );
        assertEquals("the result is the mean value in the list",
                Long.valueOf(5L),
                df1.getMean("long1")
        );
        assertEquals("the result is the mean value in the list",
                Float.valueOf(-31.17333221435547f),
                df1.getMean("float")
        );
        assertEquals("the result is the mean value in the list",
                -31.173333333333332,
                df1.getMean("double"),
                0.0
        );
        assertThrows("the result is the mean value in the list",
                TypeException.class,
                () -> df1.getMean("char")
        );
        assertThrows("the result is the mean value in the list",
                TypeException.class,
                () -> df1.getMean("string")
        );
    }

    @Test
    public void testGetMedianInteger() {
        assertEquals("the result is the median value in the list",
                Boolean.TRUE,
                df1.getMedian("bool")
        );
        assertEquals("the result is the median value in the list",
                Byte.valueOf((byte) 1),
                df1.getMedian("byte")
        );
        assertEquals("the result is the median value in the list",
                Short.valueOf((short) 1),
                df1.getMedian("short")
        );
        assertEquals("the result is the median value in the list",
                Integer.valueOf(20),
                df1.getMedian("int")
        );
        assertEquals("the result is the median value in the list",
                Long.valueOf(5L),
                df1.getMedian("long1")
        );
        assertEquals("the result is the median value in the list",
                Float.valueOf(1.2000000476837158f),
                df1.getMedian("float")
        );
        assertEquals("the result is the median value in the list",
                1.2,
                df1.getMedian("double"),
                0.0
        );
        assertThrows("the result is the median value in the list",
                TypeException.class,
                () -> df1.getMedian("char")
        );
        assertThrows("the result is the median value in the list",
                TypeException.class,
                () -> df1.getMedian("string")
        );

        assertEquals("the result is the median value in the list",
                Integer.valueOf(55),
                df2.getMedian("int")
        );
    }

    @Test
    public void testColumnInvalidType() {
        assertThrows("type not supported",
                NoSuchElementException.class,
                () -> {
                    df1.getMax("second");
                    df1.getMin("second");
                    df1.getMean("second");
                    df1.getMedian("second");
                }
        );
    }

    @Test
    public void testColumnInvalid() {
        assertThrows("invalid column label: does not exist",
                NoSuchElementException.class,
                () -> {
                    df1.getMax("invalid");
                    df1.getMax("invalid");
                    df1.getMean("invalid");
                    df1.getMedian("invalid");
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