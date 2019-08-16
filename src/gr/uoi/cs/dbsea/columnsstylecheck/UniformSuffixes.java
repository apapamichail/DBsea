package gr.uoi.cs.dbsea.columnsstylecheck;

import java.lang.reflect.Field;
import java.util.ArrayList;

import gr.uoi.cs.dbsea.logger.Logger;

public final class UniformSuffixes {

	public final String id="id";//—a unique identifier such as a column that is a primary key.
	public final String status="status";//—flag value or some other status of any type such as public finalation_status.
	public final String total="total";//—the total or sum of a collection of values.
	public final String num="num";//—denotes the field contains any kind of number.
	public final String name="name";//—signifies a name such as first_name.
	public final String seq="seq";//—contains a contiguous sequence of values.
	public final String date="date";//—denotes a column that contains the date of something.
	public final String tally="tally";//—a count.
	public final String size="size";//—the size of something such as a file size or clothing.
	public final String addr="addr";//—an address for the record could be physical or intangible such as ip_addr.
	public  static ArrayList<String> suffixes;
	
	public static void SetUpListWithSuffixes() {
		try {
			suffixes = new ArrayList<String>();
			Field[] fields = UniformSuffixes.class.getFields();
			for (Field variable : fields) {
				suffixes.add(variable.getName());
			}
		} catch (SecurityException e) {

			Logger.Log(e);

		}
	}


}
