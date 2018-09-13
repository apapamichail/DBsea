package gr.uoi.cs.dbsea.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.antlr.v4.runtime.RecognitionException;

import gr.uoi.cs.dbsea.DBsea;

  
/**
 * 
 * @author Papamichail Aggelos
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem fileFOpen;
	private JMenuItem fileClose;
	private JMenu help;
	private JMenuItem helpAbout;
	private JMenuItem Instructions;
	private InstructionsDialog instructionsDialog;
	private OpenFolderDialog openFolderDialog;
	private AboutDialog aboutDialog;
	private Image Icon;
	
 	
	/**
	 * Default Constructor
	 */
	public MainWindow(){
		initialize();
		
		createMenu();
//		mainPanel = new MainPanel();
//		getContentPane().add(mainPanel);
		
		draw();
	}
	
	public void ShowFinishMessage() {
		  JOptionPane.showMessageDialog(this, "Style Analylis has finished"); 
	}
	
	/**
	 * Sets
	 * <li>the look and feel of the application
	 * <li>the window title
	 * <li>the window icon
	 * <li>the default close operation of the window
	 */
	private void initialize() {
		// look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Schemata Style Extraction Tool");
		Icon = new ImageIcon(DBsea.class.getResource("art/icon.png")).getImage();
		this.setIconImage(Icon);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the windows menu
	 */
	private void createMenu() {
		menuBar = new JMenuBar();
		// File
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		//File->Open Folder
		fileFOpen = new JMenuItem("Open Folder...");
		fileFOpen.setMnemonic(KeyEvent.VK_F);
		fileFOpen.setToolTipText("Create diff tree from multiple files");
		fileFOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (openFolderDialog == null) {
					openFolderDialog = new OpenFolderDialog();
				}
				openFolderDialog.setVisible(true);
				if (openFolderDialog.getStatus() != 0) {
					String path = openFolderDialog.getFolder();
					File dir = new File(path);
					try {
						Worker task = new Worker(dir);
						task.execute();
						task.get();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
					ShowFinishMessage();

				}
			}
		});
		
		
		file.add(fileFOpen);
		// File->Close
		fileClose = new JMenuItem("Close");
		fileClose.setMnemonic(KeyEvent.VK_C);
		
		fileClose.setToolTipText("Exit application");
		fileClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		file.add(fileClose);
		menuBar.add(file);

		// Help
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		// Help->About
		helpAbout = new JMenuItem("About");
		helpAbout.setMnemonic(KeyEvent.VK_A);
		helpAbout.setToolTipText("About Hecate");
		helpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				aboutDialog = new AboutDialog();
				aboutDialog.setVisible(true);
			}
			
		});
		// Help->Instructions
				Instructions = new JMenuItem("Instructions");
				Instructions.setMnemonic(KeyEvent.VK_A);
				Instructions.setToolTipText("Instructions");
				Instructions.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						instructionsDialog = new InstructionsDialog();
						instructionsDialog.setVisible(true);
					}
					});
		help.add(Instructions);
		help.add(helpAbout);
		menuBar.add(help);
		
		setJMenuBar(menuBar);
	}
	
	/**
	 * Sets the size of the window and centers it to the screen
	 */
	private void draw() {
		setMinimumSize(new Dimension(400, 300));
		pack();
		// center on screen
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
		            size.height/2 - getHeight()/2);
	}
	
}
