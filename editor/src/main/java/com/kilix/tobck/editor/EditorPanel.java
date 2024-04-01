package com.kilix.tobck.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class EditorPanel extends JPanel implements MenuContext {

	protected final Dimension preferredSize = new Dimension(1080, 720);
	protected final Dimension minimumSize = new Dimension(1080, 720);
	protected final Dimension maximumSize = new Dimension(-1, -1);
	
	protected final JPanel editorView = new JPanel();
	protected final JPanel toolsView = new JPanel();
	private final JMenuBar menuBar = new JMenuBar();
	
	public EditorPanel() {
		super(new GridBagLayout());
		
		add(menuBar, new GridBagConstraints(
				0, 0,
				2, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		add(editorView, new GridBagConstraints(
				0, 1,
				1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		add(toolsView, new GridBagConstraints(
				1, 1,
				1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		for (JMenu menu : getContextMenus()) menuBar.add(menu);
		if (getContextMenus().length > 0) menuBar.add(new JSeparator(JSeparator.VERTICAL));
		menuBar.add(new SimpleMenuItem("Show Tools", KeyEvent.VK_T, e -> Main.TOOL_WINDOW.setVisible(true)));
		menuBar.add(new JSeparator(JSeparator.VERTICAL));
		menuBar.add(new SimpleMenuItem("Help", -1, e -> JOptionPane.showMessageDialog(this, "There is no help.")));
	}
	
	public Dimension getPreferredSize() { return preferredSize; }
	public Dimension getMinimumSize() { return minimumSize; }
	public Dimension getMaximumSize() { return maximumSize; }
	
	public void setPreferredSize(Dimension preferredSize) {}
	public void setMinimumSize(Dimension minimumSize) {}
	public void setMaximumSize(Dimension maximumSize) {}
	
}
