package com.kilix.tobck.editor;

import javax.swing.*;
import java.awt.*;

public abstract class EditorPanel extends JPanel implements MenuContext {

	protected final Dimension preferredSize = new Dimension(1080, 720);
	protected final Dimension minimumSize = new Dimension(1080, 720);
	protected final Dimension maximumSize = new Dimension(-1, -1);
	
	protected final JPanel editorView = new JPanel();
	protected final JPanel toolsView = new JPanel();
	
	public EditorPanel() {
		super(new GridBagLayout());
		
		add(editorView, new GridBagConstraints(
				0, 0,
				1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 5
		));
		
	}
	
	public Dimension getPreferredSize() { return preferredSize; }
	public Dimension getMinimumSize() { return minimumSize; }
	public Dimension getMaximumSize() { return maximumSize; }
	
	public void setPreferredSize(Dimension preferredSize) {}
	public void setMinimumSize(Dimension minimumSize) {}
	public void setMaximumSize(Dimension maximumSize) {}
	
}
