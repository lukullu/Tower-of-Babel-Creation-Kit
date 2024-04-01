package com.kilix.tobck.editor;

import com.kilix.tobck.editor.SimpleAction;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ToolsPanel extends JPanel {
	
	public enum SelectedTool {
		NONE, LEVEL_EDITOR, ENTITY_EDITOR;
	}
	
	public Insets getInsets() { return new Insets(20, 20, 20, 20); }
	public ToolsPanel(Consumer<SelectedTool> onToolSelected) {
		super(new GridBagLayout());
		
		int line = 0;
		add(new JButton(new SimpleAction("Level Editor", event -> onToolSelected.accept(SelectedTool.LEVEL_EDITOR))), new GridBagConstraints(
				0, line++,
				3, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		add(new JButton(new SimpleAction("Entity Editor", event -> onToolSelected.accept(SelectedTool.ENTITY_EDITOR))), new GridBagConstraints(
				0, line++,
				3, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		// spacer
		add(new JPanel(), new GridBagConstraints(0, line, 2, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		// cancel button
		add(new JButton(new SimpleAction("Close", event -> onToolSelected.accept(SelectedTool.NONE))), new GridBagConstraints(
				2, line,
				1, 1,
				0, 0,
				GridBagConstraints.PAGE_END,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
	}
	
}
