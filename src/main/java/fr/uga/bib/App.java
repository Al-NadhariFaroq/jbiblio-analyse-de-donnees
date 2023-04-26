package fr.uga.bib;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) throws TypeException {

		Object[] col1 = {"first", "Integer", 10, 20, 30};
		Object[] col2 = {"second", "Boolean", true, false, true};
		Object[] col3 = {"third", "String", "abc", "def", "ghi"};
		Object[] col4 = {"fourth", "Double", 1.2, 5.3, -100.02};
		Object[] col5 = {"eighth", "Float", 1.2f, 5.3f, -100.02f};
		Object[] col6 = {"ninth", "Long", 1L, 5L, 9L};
		Object[] col7 = {"fifth", "Long", 1L, 5L, 140L};
		Object[][] data = {col1, col2, col3, col4, col5, col6, col7};

		DataFrame df = new DataFrame(data);

		DataFrame sdf = df.iloc(1,2);

		//Object[] row= {false,554};
		//df.addRow(row);

		System.out.println(sdf);
	}
}
