package gr.uoi.cs.dbsea.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import gr.uoi.cs.dbsea.DBsea;

/**
 * A dialog displaying Hecates information
 * @author giskou
 *
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	
	private JLabel name;
	private JLabel author;
	private JLabel mail;
	private JButton close;
	private ImageIcon hecateIcon;

	public AboutDialog() {
		initialize();

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		add(Box.createRigidArea(new Dimension(0, 15)));

		name = new JLabel("      Database Style Extraction and Assessment (DBsea)     ");
		name.setFont(new Font("Serif", Font.BOLD, 15));
		name.setAlignmentX(CENTER_ALIGNMENT);
		add(name);
		
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		hecateIcon = new ImageIcon(DBsea.class.getResource("art/icon.png"));
		JLabel iconLabel = new JLabel(hecateIcon);
		iconLabel.setAlignmentX(0.5f);
		add(iconLabel);
		
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		author = new JLabel("         Papamichail Aggelos        ");
		author.setFont(new Font("Serif", Font.PLAIN, 13));
		author.setAlignmentX(CENTER_ALIGNMENT);
		add(author);
		mail = new JLabel("        papamichail.ang@gmail.com        ");
		mail.setFont(new Font("Serif", Font.PLAIN, 12));
		mail.setAlignmentX(CENTER_ALIGNMENT);
		add(mail);

		add(Box.createRigidArea(new Dimension(0, 15)));
		
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
		setTitle("About");
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

