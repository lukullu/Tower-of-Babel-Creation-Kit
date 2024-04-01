package com.kilix.tobck.editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class SimpleMenuItem extends JMenuItem {
	
	public SimpleMenuItem(String name, int mnemonic, Consumer<ActionEvent> action) {
		super(name, mnemonic);
		addActionListener(action::accept);
	}
	
}
