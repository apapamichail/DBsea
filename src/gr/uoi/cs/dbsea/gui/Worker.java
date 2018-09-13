/**
 * 
 */
package gr.uoi.cs.dbsea.gui;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import gr.uoi.cs.dbsea.sql.Schema;
import gr.uoi.cs.dbsea.stylecore.SchemaStyleAnalysis;

/**
 * @author apapamichail
 *
 */
public class Worker extends SwingWorker<SchemaStyleAnalysis, Integer> {
	
 	ProgressMonitor progressmonitor;
	File folder = null;
	MainWindow mainWindow;
	
 	
	public Worker(File folder) {
 		this.folder = folder;
	}
	

	
	protected SchemaStyleAnalysis doInBackground() throws Exception {
		SchemaStyleAnalysis differencesManager = new SchemaStyleAnalysis();
 		checkTheDifferencesInHistory(differencesManager);
		return differencesManager;
		
	}

	/**
	 * @param differencesManager
	 * @return
	 * @throws IOException
	 */
	private void checkTheDifferencesInHistory(SchemaStyleAnalysis differencesManager) throws IOException {

		//differencesManager.checkDifferencesInSchemataHistoryAndExport(folder);
		differencesManager.traversePaths(folder);

	}

	

 
}
