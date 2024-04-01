package com.kilix.tbck.editor;

import javax.swing.*;
import java.util.Map;

public class EntityEditor extends EditorPanel {
	
	public JMenu[] getContextMenus() {
		return new JMenu[] {
				new JMenu("File", true),
				new JMenu("Edit", true)
		};
	}
}

