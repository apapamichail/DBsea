/**
 * 
 */
package gr.uoi.cs.dbsea.gui;

import java.io.File;
import java.io.IOException;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
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
		SchemaStyleAnalysis styleAnalyzer = new SchemaStyleAnalysis();
 		checkTheDifferencesInHistory(styleAnalyzer);
		return styleAnalyzer;
		
	}

	/**
	 * @param styleAnalyzer
	 * @return
	 * @throws IOException
	 */
	private void checkTheDifferencesInHistory(SchemaStyleAnalysis styleAnalyzer) throws IOException {

		styleAnalyzer.traversePaths(folder);
			
	}

	

 
}
