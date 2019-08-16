package gr.uoi.cs.dbsea.tablestylecheck;

import java.lang.reflect.Field;
import java.util.ArrayList;

import gr.uoi.cs.dbsea.logger.Logger;

public final class TablePrefixes {

	public final String tbl="tbl";

	public  static ArrayList<String> prefixes;
	
	public static void setUpListWithPrefixes() {
		try {
			
			prefixes = new ArrayList<String>();
			Field[] fields = TablePrefixes.class.getFields();
			for (Field variable : fields) {
				prefixes.add(variable.getName());
			}
			
		} catch (SecurityException e) {
			
			Logger.Log(e);
			
 		}
	}


}
