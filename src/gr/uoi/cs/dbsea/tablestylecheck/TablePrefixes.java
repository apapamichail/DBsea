package gr.uoi.cs.dbsea.tablestylecheck;

import java.lang.reflect.Field;
import java.util.ArrayList;

/** 
 * 
 * @author Papamichail Aggelos
 * 
 * Here you can add your prefixes.
 *
 */
public final class TablePrefixes {

	public final String tbl="tbl";

	public  static ArrayList<String> prefixes;
	public static void SetUpListWithSuffixes() {
		prefixes = new ArrayList<String>();
		Field[] fields = TablePrefixes.class.getFields();
		for (Field variable : fields) {
			prefixes.add(variable.getName());
		}
	}


}
