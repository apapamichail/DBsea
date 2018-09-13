package gr.uoi.cs.dbsea;
import java.io.IOException;

import gr.uoi.cs.dbsea.gui.MainWindow;

/**
 * This is the class that contains the main function
 * for DBsea.
 * @author Papamichail Aggelos
 */
public class DBsea{

	private static MainWindow mainWindow;
	/** A
	 * Creates and shows the main Window
	 * @param args 
	 */
	public static void main(String[] args)  {
		//test t =new test();
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
