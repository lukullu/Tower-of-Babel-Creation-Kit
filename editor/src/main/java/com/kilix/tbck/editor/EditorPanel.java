package com.kilix.tbck.editor;

import com.kilix.tbck.editor.components.Viewport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class EditorPanel extends JPanel implements MenuContext {

	protected final Dimension preferredSize = new Dimension(1080, 720);
	protected final Dimension minimumSize = new Dimension(1080, 720);
	protected final Dimension maximumSize = new Dimension(-1, -1);
	
	protected final Viewport viewport = new Viewport(this::paintViewport, this::eventHandler);
	protected final JPanel toolsPanel = new JPanel(new GridBagLayout());
	
	protected ToolFrame rootFrame;
	
	public EditorPanel() {
		super(new GridBagLayout());
		
		JMenuBar menuBar = new JMenuBar();
		
		for (JMenu menu : getContextMenus()) menuBar.add(menu);
		if (getContextMenus().length > 0) menuBar.add(new JSeparator(JSeparator.VERTICAL));
		menuBar.add(new SimpleMenuItem("").enabled(false).canGrow(true));
		menuBar.add(new SimpleMenuItem("Show Tools", KeyEvent.VK_T, e -> ToolsPopup.show()));
		menuBar.add(new SimpleMenuItem("Help", KeyEvent.VK_H, e -> JOptionPane.showMessageDialog(this, "There is no help.")));
		
		add(menuBar, new GridBagConstraints(
				0, 0,
				1, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		toolsPanel.setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 0,
				UIManager.getColor("TitledBorder.titleColor")
		));
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				true, viewport, toolsPanel
		);
		splitPane.setResizeWeight(1);
		add(splitPane, new GridBagConstraints(
				0, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		postLayout();
	}
	
	protected void paintViewport(Graphics2D g) {}
	protected void eventHandler(ComponentEvent event) {}
	protected void postLayout() {}
	
	void setRootFrame(ToolFrame frame) { this.rootFrame = frame; }
	
	public Dimension getPreferredSize() { return preferredSize; }
	public Dimension getMinimumSize() { return minimumSize; }
	public Dimension getMaximumSize() { return maximumSize; }
	
	public void setPreferredSize(Dimension preferredSize) {}
	public void setMinimumSize(Dimension minimumSize) {}
	public void setMaximumSize(Dimension maximumSize) {}
	
	
	
	
}
