package gr.uoi.cs.dbsea.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

	private static FileWriter fw;
	private static DateFormat dateFormat;
	private static Date date;

	public static void Initialize() {

		try {

			fw = new FileWriter(new File("Log.txt"), true);
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void Log(String message) {

		try {
			date = new Date();
			
			fw.append(dateFormat.format(date) + ", Message : " + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void Log(String error, Exception ex) {

		try {
			date = new Date();

			String message = dateFormat.format(date) + ", Message : " + error + " \nStacktrace : " + ex.fillInStackTrace()
					+ "\nException Message : " + ex.getMessage();
			fw.append(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void Log(Exception ex) {

		try {
			date = new Date();

			String message = dateFormat.format(date) + "\nException Message : "
					+ ex.getMessage()+"\n";
			fw.append(message);
			System.out.println(message);
			ex.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
