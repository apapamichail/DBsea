package gr.uoi.cs.dbsea.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;
import org.netbeans.swing.outline.TreePathSupport;

import gr.uoi.cs.dbsea.sql.Schema;
import gr.uoi.cs.dbsea.sql.Table;

/**
 * This Panel contains two panes with the original and the modified
 * version of the generated trees of the schemas.
 * @author Papamichail Aggelos
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	/**
	 * Default Constructor.
	 */
	public MainPanel() {
		setLayout(new GridLayout(1,2));
	}

	/**
	 * Creates and draws a tree of the schema <code>s</code> at side
	 * <code>side</code>.
	 * @param s the schema to be drawn.
	 * @param side the side of the Panel the schema will be drawn.
	 * Accepted values are <code>"old"</code> for the left side and
	 * <code>"new"</code> for the right side.
	 */
	 
	
}
