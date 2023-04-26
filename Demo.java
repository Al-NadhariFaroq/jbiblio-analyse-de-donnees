import fr.uga.bib.DataFrame;
import java.io.*;
public class Demo {
    public static void main(String[] args) {
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
        DataFrame df = new DataFrame(data);
        df.getMean("int");
    }
}
