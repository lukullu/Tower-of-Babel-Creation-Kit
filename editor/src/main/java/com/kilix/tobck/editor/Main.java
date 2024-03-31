package com.kilix.tobck.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.kilix.tobck.Constants;

import javax.swing.*;
import java.awt.*;

public class Main {
	
	public static void main(String[] args) {
		FlatDarculaLaf.setup();
		openToolWindow();
	}
	
	private static void openToolWindow() {
		JFrame frame = new JFrame(Constants.WINDOW_TITLE + " - Tool selection");
		
		frame.setContentPane(new ToolsPanel(tool -> {
			switch (tool) {
				case NONE -> frame.dispose();
				case LEVEL_EDITOR -> new ToolFrame("Level Editor", null);
				case ENTITY_EDITOR -> new ToolFrame("Entity Editor", new EntityEditor());
			}
		}));
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setType(Window.Type.POPUP);
		frame.setPreferredSize(new Dimension(300, 150));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
}
