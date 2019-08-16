package gr.uoi.cs.dbsea.gui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

 
public class InstructionsDialog extends JDialog {
	private JButton close;
	private JLabel generalInformation;

	public InstructionsDialog() {
		initialize();
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 15)));
		generalInformation = new JLabel("<html> This software extracts the style of <br/>the selected schemata, "
										+ " you can choose the parent folder which contains <br/>the schemata and "
										+ " a depth first approach will export the information<br/> about the styles"
										+ " inside the folder you are running dbsea</html>");
		generalInformation.setFont(new Font("Calibri", Font.BOLD, 14));
		generalInformation.setAlignmentX(CENTER_ALIGNMENT);
		add(generalInformation);
	 
		
		close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		close.setAlignmentX(CENTER_ALIGNMENT);
		add(close);
		
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		draw();
	}
	
	private void initialize() {
		setTitle("Instructions");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void draw() {
		pack();
		setResizable(false);
		// center on screen
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
		            size.height/2 - getHeight()/2);
	}
}
