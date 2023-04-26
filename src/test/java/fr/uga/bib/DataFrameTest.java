package fr.uga.bib;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DataFrameTest {
    Object[] col1 = {"bool", "Boolean", true, false, true, true, false, false};
    Object[] col2 = {"byte", "Byte", (byte) 1, (byte) 5, (byte) -100, (byte) -10, (byte) 42, (byte) -33};
    Object[] col3 = {"short", "Short", (short) 1, (short) 5, (short) -100, (short) -10, (short) 42, (short) -33};
    Object[] col4 = {"int", "Integer", 10, 20, 30, 40, 50, 60};
    Object[] col5 = {"long", "Long", 10L, 20L, 30L, 40L, 50L, 60L};
    Object[] col6 = {"float", "Float", 1.2f, 5.3f, -100.02f, 3.1415f, -42.0f, 666.66f};
    Object[] col7 = {"double", "Double", 1.2, 5.3, -100.02, 3.1415, -42.0, 666.66};
    Object[] col8 = {"char", "Character", 'a', 'b', '?', '+', '&', '9'};
    Object[] col9 = {"string", "String", "abc", "def", "ghi", "jkl", "", "mno"};
    Object[][] data = {col1, col2, col3, col4, col5, col6, col7, col8, col9};
    DataFrame df;

    @Before
    public void init() throws TypeException {
        df = new DataFrame(data);
    }

    @Test
    public void initCSV() throws IOException {
        df = new DataFrame("src/test/resources/df1.csv");
        String msg = "id\tname\tsold\t\n" +
                "1\tJohn\t5000.12\t\n" +
                "2\tJane\t6000.0\t\n" +
                "3\tBob\t4000.99\t\n" +
                "4\tAlice\t-7000.45\t\n";

        assertEquals("Get column number test", 3, df.shape()[1]);
        assertEquals("Get row number test", 4, df.shape()[0]);
        assertEquals("Get first column type test",
                Integer.class,
                df.getColumnType("id")
        );
        assertEquals("Get second column type test",
                String.class,
                df.getColumnType("name")
        );
        assertEquals("Get third column type test",
                Float.class,
                df.getColumnType("sold")
        );
        assertEquals("Print all the DataFrame", msg, df.toString());
    }

    @Test
    public void head() {
        String msg = "bool\tbyte\tshort\tint\tlong\tfloat\tdouble\tchar\tstring\t\n" +
                "true\t1\t1\t10\t10\t1.2\t1.2\ta\tabc\t\n" +
                "false\t5\t5\t20\t20\t5.3\t5.3\tb\tdef\t\n" +
                "true\t-100\t-100\t30\t30\t-100.02\t-100.02\t?\tghi\t\n";

        assertEquals("Print n first lines", msg, df.head(3));
    }

    @Test
    public void head5() {
        String msg = "bool\tbyte\tshort\tint\tlong\tfloat\tdouble\tchar\tstring\t\n" +
                "true\t1\t1\t10\t10\t1.2\t1.2\ta\tabc\t\n" +
                "false\t5\t5\t20\t20\t5.3\t5.3\tb\tdef\t\n" +
                "true\t-100\t-100\t30\t30\t-100.02\t-100.02\t?\tghi\t\n" +
                "true\t-10\t-10\t40\t40\t3.1415\t3.1415\t+\tjkl\t\n" +
                "false\t42\t42\t50\t50\t-42.0\t-42.0\t&\t\t\n";

        assertEquals("Print 5 first lines", msg, df.head());
    }

    @Test
    public void tail() {
        String msg = "bool\tbyte\tshort\tint\tlong\tfloat\tdouble\tchar\tstring\t\n" +
                "true\t-10\t-10\t40\t40\t3.1415\t3.1415\t+\tjkl\t\n" +
                "false\t42\t42\t50\t50\t-42.0\t-42.0\t&\t\t\n" +
                "false\t-33\t-33\t60\t60\t666.66\t666.66\t9\tmno\t\n";

        assertEquals("Print last lines", msg, df.tail(3));
    }

    @Test
    public void tail5() {
        String msg = "bool\tbyte\tshort\tint\tlong\tfloat\tdouble\tchar\tstring\t\n" +
                "false\t5\t5\t20\t20\t5.3\t5.3\tb\tdef\t\n" +
                "true\t-100\t-100\t30\t30\t-100.02\t-100.02\t?\tghi\t\n" +
                "true\t-10\t-10\t40\t40\t3.1415\t3.1415\t+\tjkl\t\n" +
                "false\t42\t42\t50\t50\t-42.0\t-42.0\t&\t\t\n" +
                "false\t-33\t-33\t60\t60\t666.66\t666.66\t9\tmno\t\n";

        assertEquals("Print 5 last lines", msg, df.tail());
    }

    @Test
    public void loc() {
        DataFrame subFrame = df.loc("int", "long", "string");
        String msg = "int\tlong\tstring\t\n" +
                "10\t10\tabc\t\n" +
                "20\t20\tdef\t\n" +
                "30\t30\tghi\t\n" +
                "40\t40\tjkl\t\n" +
                "50\t50\t\t\n" +
                "60\t60\tmno\t\n";

        assertEquals("Check the sub DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locNoSuchElementException() {
        assertThrows("Non-existent column test",
                NoSuchElementException.class,
                () -> df.loc("int", "test", "float")
        );
    }

    @Test
    public void iloc() {
        DataFrame subFrame = df.iloc(2, 5);
        String msg = "bool\tbyte\tshort\tint\tlong\tfloat\tdouble\tchar\tstring\t\n" +
                "true\t-100\t-100\t30\t30\t-100.02\t-100.02\t?\tghi\t\n" +
                "true\t-10\t-10\t40\t40\t3.1415\t3.1415\t+\tjkl\t\n" +
                "false\t42\t42\t50\t50\t-42.0\t-42.0\t&\t\t\n" +
                "false\t-33\t-33\t60\t60\t666.66\t666.66\t9\tmno\t\n";

        assertEquals("Check the sub DataFrame", msg, subFrame.tail());
        assertEquals("Check the sub DataFrame", msg, subFrame.head());
    }

    @Test
    public void locWhereGTH() {
        DataFrame subFrame = df.locWhere("float", Float.class, Operator.GTH, 1.9f);
        String msg = "float\t\n5.3\t\n3.1415\t\n666.66\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereLTH() {
        DataFrame subFrame = df.locWhere("string", String.class, Operator.LTH, "j");
        String msg = "string\t\nabc\t\ndef\t\nghi\t\n\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereGEQ() {
        DataFrame subFrame = df.locWhere("short", Short.class, Operator.GEQ, (short) 5);
        String msg = "short\t\n5\t\n42\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereLEQ() {
        DataFrame subFrame = df.locWhere("double", Double.class, Operator.LEQ, 1.2);
        String msg = "double\t\n1.2\t\n-100.02\t\n-42.0\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereEQ() {
        DataFrame subFrame = df.locWhere("int", Integer.class, Operator.EQU, 10);
        String msg = "int\t\n10\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereDIF() {
        DataFrame subFrame = df.locWhere("char", Character.class, Operator.DIF, '?');
        String msg = "char\t\na\t\nb\t\n+\t\n&\t\n9\t\n";

        assertEquals("Check the filtered DataFrame", msg, subFrame.toString());
    }

    @Test
    public void locWhereNoSuchElementException() {
        assertThrows("Non-existent column test",
                NoSuchElementException.class,
                () -> df.locWhere("test", Boolean.class, Operator.GTH, true)
        );
    }

    @Test
    public void locWhereTypeException() {
        assertThrows("Given type does not match the column type",
                TypeException.class,
                () -> df.locWhere("int", Character.class, Operator.EQU, 'c')
        );
    }

    @Test
    public void getMin() {
        assertEquals("the result is the minimum value in the list",
                Boolean.FALSE,
                df.getMin("bool")
        );
        assertEquals("the result is the minimum value in the list",
                Byte.valueOf((byte) -100),
                df.getMin("byte")
        );
        assertEquals("the result is the minimum value in the list",
                Short.valueOf((short) -100),
                df.getMin("short")
        );
        assertEquals("the result is the minimum value in the list",
                Integer.valueOf(10),
                df.getMin("int")
        );
        assertEquals("the result is the minimum value in the list",
                Long.valueOf(10L),
                df.getMin("long")
        );
        assertEquals("the result is the minimum value in the list",
                Float.valueOf(-100.02f),
                df.getMin("float")
        );
        assertEquals("the result is the minimum value in the list",
                -100.02,
                df.getMin("double"),
                0.0
        );
        assertEquals("the result is the minimum value in the list",
                Character.valueOf('&'),
                df.getMin("char")
        );
        assertEquals("the result is the minimum value in the list",
                "",
                df.getMin("string")
        );
    }

    @Test
    public void getMax() {
        assertEquals("the result is the max value in the list",
                Boolean.TRUE,
                df.getMax("bool")
        );
        assertEquals("the result is the max value in the list",
                Byte.valueOf((byte) 42),
                df.getMax("byte")
        );
        assertEquals("the result is the max value in the list",
                Short.valueOf((short) 42),
                df.getMax("short")
        );
        assertEquals("the result is the max value in the list",
                Integer.valueOf(60),
                df.getMax("int")
        );
        assertEquals("the result is the max value in the list",
                Long.valueOf(60L),
                df.getMax("long")
        );
        assertEquals("the result is the max value in the list",
                Float.valueOf(666.66f),
                df.getMax("float")
        );
        assertEquals("the result is the max value in the list",
                666.66,
                df.getMax("double"),
                0.0
        );
        assertEquals("the result is the max value in the list",
                Character.valueOf('b'),
                df.getMax("char")
        );
        assertEquals("the result is the max value in the list",
                "mno",
                df.getMax("string")
        );
    }

    @Test
    public void getMean() {
        assertEquals("the result is the mean value in the list",
                Boolean.FALSE,
                df.getMean("bool")
        );
        assertEquals("the result is the mean value in the list",
                Byte.valueOf((byte) -15),
                df.getMean("byte")
        );
        assertEquals("the result is the mean value in the list",
                Short.valueOf((short) -15),
                df.getMean("short")
        );
        assertEquals("the result is the mean value in the list",
                Integer.valueOf(35),
                df.getMean("int")
        );
        assertEquals("the result is the mean value in the list",
                Long.valueOf(35L),
                df.getMean("long")
        );
        assertEquals("the result is the mean value in the list",
                Float.valueOf(89.04691f),
                df.getMean("float")
        );
        assertEquals("the result is the mean value in the list",
                89.04691,
                df.getMean("double"),
                0.00001
        );
        assertThrows("the result is the mean value in the list",
                TypeException.class,
                () -> df.getMean("char")
        );
        assertThrows("the result is the mean value in the list",
                TypeException.class,
                () -> df.getMean("string")
        );

        data = new Object[][]{{"int", "Integer", 10, 20, 30}};
        df = new DataFrame(data);
        assertEquals("the result is the mean value in the list",
                Integer.valueOf(20),
                df.getMean("int")
        );
    }

    @Test
    public void getMedian() {
        assertEquals("the result is the median value in the list",
                Boolean.TRUE,
                df.getMedian("bool")
        );
        assertEquals("the result is the median value in the list",
                Byte.valueOf((byte) -4),
                df.getMedian("byte")
        );
        assertEquals("the result is the median value in the list",
                Short.valueOf((short) -4),
                df.getMedian("short")
        );
        assertEquals("the result is the median value in the list",
                Integer.valueOf(55),
                df.getMedian("int")
        );
        assertEquals("the result is the median value in the list",
                Long.valueOf(55L),
                df.getMedian("long")
        );
        assertEquals("the result is the median value in the list",
                Float.valueOf(3.7415f),
                df.getMedian("float")
        );
        assertEquals("the result is the median value in the list",
                3.7415,
                df.getMedian("double"),
                0.00001
        );
        assertThrows("the result is the median value in the list",
                TypeException.class,
                () -> df.getMedian("char")
        );
        assertThrows("the result is the median value in the list",
                TypeException.class,
                () -> df.getMedian("string")
        );

        data = new Object[][]{{"int", "int", 1, 2, 3, 4, 5}};
        df = new DataFrame(data);
        assertEquals("the result is the median value in the list",
                Integer.valueOf(3),
                df.getMedian("int")
        );
    }

    @Test
    public void testColumnInvalidType() {
        assertThrows("type not supported",
                NoSuchElementException.class,
                () -> df.getMax("second")
        );
        assertThrows("type not supported",
                NoSuchElementException.class,
                () -> df.getMin("second")
        );
        assertThrows("type not supported",
                NoSuchElementException.class,
                () -> df.getMean("second")
        );
        assertThrows("type not supported",
                NoSuchElementException.class,
                () -> df.getMedian("second")
        );
    }

    @Test
    public void testColumnInvalid() {
        assertThrows("invalid column label: does not exist",
                NoSuchElementException.class,
                () -> df.getMax("invalid")
        );
        assertThrows("invalid column label: does not exist",
                NoSuchElementException.class,
                () -> df.getMax("invalid")
        );
        assertThrows("invalid column label: does not exist",
                NoSuchElementException.class,
                () -> df.getMean("invalid")
        );
        assertThrows("invalid column label: does not exist",
                NoSuchElementException.class,
                () -> df.getMedian("invalid")
        );
    }
}