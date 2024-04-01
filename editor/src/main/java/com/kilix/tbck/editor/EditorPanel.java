package com.kilix.tbck.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class EditorPanel extends JPanel implements MenuContext {

	protected final Dimension preferredSize = new Dimension(1080, 720);
	protected final Dimension minimumSize = new Dimension(1080, 720);
	protected final Dimension maximumSize = new Dimension(-1, -1);
	
	protected final JPanel editorView = new JPanel();
	protected final JPanel toolsView = new JPanel();
	
	protected ToolFrame rootFrame;
	
	public EditorPanel() {
		super(new GridBagLayout());
		
		JMenuBar menuBar = new JMenuBar();
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
		menuBar.add(new SimpleMenuItem("Show Tools", KeyEvent.VK_T, e -> ToolsPopup.show()));
		menuBar.add(new JSeparator(JSeparator.VERTICAL));
		menuBar.add(new SimpleMenuItem("Help", KeyEvent.VK_H, e -> JOptionPane.showMessageDialog(this, "There is no help.")));
	}
	
	void setRootFrame(ToolFrame frame) { this.rootFrame = frame; }
	
	public Dimension getPreferredSize() { return preferredSize; }
	public Dimension getMinimumSize() { return minimumSize; }
	public Dimension getMaximumSize() { return maximumSize; }
	
	public void setPreferredSize(Dimension preferredSize) {}
	public void setMinimumSize(Dimension minimumSize) {}
	public void setMaximumSize(Dimension maximumSize) {}
	
}
