package com.kilix.tbck.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class SimpleMenuItem extends JMenuItem {
	
	private boolean canGrow = false, canShrink = false;
	
	public SimpleMenuItem(String name) { super(name); }
	public SimpleMenuItem(String name, int mnemonic, Consumer<ActionEvent> action) {
		super(name, mnemonic);
		addActionListener(action::accept);
	}
	
	public SimpleMenuItem enabled(boolean enabled) { super.setEnabled(enabled); return this; }
	public SimpleMenuItem canGrow(boolean canGrow) {
		this.canGrow = canGrow;
		return this;
	}
	public SimpleMenuItem canShrink(boolean canShrink) {
		this.canShrink = canShrink;
		return this;
	}
	public Dimension getMaximumSize() { return canGrow ? new Dimension(-1, -1) : super.getPreferredSize(); }
	public Dimension getMinimumSize() { return canShrink ? new Dimension(-1, -1) : super.getPreferredSize(); }
	
}
