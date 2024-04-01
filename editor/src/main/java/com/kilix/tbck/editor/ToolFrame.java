package com.kilix.tbck.editor;

import com.tbck.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ToolFrame extends JFrame {
	
	private static final Rectangle NINTH_OF_SCREEN;
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		NINTH_OF_SCREEN = new Rectangle(
				screenSize.width / 3, screenSize.height / 3,
				screenSize.width / 3, screenSize.height / 3
		);
	}
	
	private final String toolName;
	public ToolFrame(String toolName, EditorPanel content) {
		super(Constants.WINDOW_TITLE + " - " + toolName);
		setContentPane(Objects.requireNonNull(content, "Content must not be null!"));
		this.toolName = Objects.requireNonNull(toolName, "Tool name must not be null!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setType(Window.Type.NORMAL);
		setVisible(true);
		setMinimumSize(new Dimension(300, 300));
		setBounds(NINTH_OF_SCREEN);
		requestFocus();
	}
	
	protected void setContext(String context) {
		if (context == null) setTitle(Constants.WINDOW_TITLE + " - " + toolName);
		else setTitle(Constants.WINDOW_TITLE + " - " + toolName + " - " + context);
	}
	
}
