package gr.uoi.cs.dbsea;


import gr.uoi.cs.dbsea.gui.MainWindow;
import gr.uoi.cs.dbsea.logger.Logger;

/**
 * This is the class that contains the main function
 * for Hecate.
 * @author giskou
 */
public class DBsea{

	private static MainWindow mainWindow;
	/** A
	 * Creates and shows the main Window
	 * @param args 
	 */
	public static void main(String[] args)  {
		
		Logger.Initialize();
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
	}
}
