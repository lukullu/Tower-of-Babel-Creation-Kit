package com.kilix.tbck.editor.components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class LabeledSlider extends JPanel {
	
	private final JSlider slider;
	
	public LabeledSlider(int min, int max, int value) {
		super(new GridBagLayout());
		JLabel label = new JLabel(String.valueOf(value));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setMinimumSize(new Dimension(30, -1));
		slider = new JSlider(min, max, value);
		
		add(slider, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0
		));
		add(label, new GridBagConstraints(
				1, 0, 1, 1, 0.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 0), 0, 0
		));
		addChangeListener(event -> label.setText(String.valueOf(getValue())));
	}
	
	public void addChangeListener(ChangeListener listener) { slider.addChangeListener(listener); }
	public int getValue() { return slider.getValue(); }
	
}
