package com.kilix.tbck.editor;

import com.tbck.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ToolsPopup {
	
	private static final JFrame FRAME = new JFrame();
	
	static {
		JPanel panel = new JPanel(new GridBagLayout());
		
		int line = 0;
		panel.add(new JButton(new SimpleAction("Level Editor", event -> { newLevelEditor(); hide(); })), new GridBagConstraints(
				0, line++,
				3, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		panel.add(new JButton(new SimpleAction("Entity Editor", event -> { newEntityEditor(); hide(); })), new GridBagConstraints(
				0, line++,
				3, 1,
				1, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		// spacer
		panel.add(new JPanel(), new GridBagConstraints(0, line, 2, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		// cancel button
		panel.add(new JButton(new SimpleAction("Cancel", event -> hide())), new GridBagConstraints(
				2, line,
				1, 1,
				0, 0,
				GridBagConstraints.PAGE_END,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0
		));
		
		FRAME.setContentPane(panel);
		FRAME.setTitle(Constants.WINDOW_TITLE + " - Tool Selection");
		FRAME.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		FRAME.setType(Window.Type.POPUP);
		FRAME.setPreferredSize(new Dimension(300, 150));
		FRAME.setResizable(false);
		FRAME.pack();
		FRAME.setVisible(true);
	}
	
	public static void hide() { FRAME.setVisible(false); }
	public static void show() {
		FRAME.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = FRAME.getSize();
		
		FRAME.setBounds(
			 (screenSize.width - frameSize.width) / 2,
			 (screenSize.height - frameSize.height) / 2,
			 frameSize.width, frameSize.height
		);
		FRAME.setVisible(true);
	 }
	
	
	public static void newLevelEditor() {
		// TODO
	}
	
	public static void newEntityEditor() {
		new ToolFrame("Entity Editor", new EntityEditor());
	}
	
	
}