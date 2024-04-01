package com.kilix.tbck.editor;

import com.kilix.tbck.editor.components.LabeledSlider;
import com.tbck.Constants;
import com.tbck.data.entity.SegmentData;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.tbck.Constants.*;

public class EntityEditor extends EditorPanel {
	
	static {
		try {
			Files.createDirectories(ENTITY_TEMPLATE_DIRECTORY);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private static final FileFilter FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.getPath().toLowerCase(Locale.ROOT).endsWith(ENTITY_TEMPLATE_FILE_EXTENSION); }
		public String getDescription() { return ENTITY_TEMPLATE_FILE_TYPE_DESCRIPTION; }
	};
	
	private File templateFile = null;
	private ArrayList<SegmentData> entityTemplate = new ArrayList<>();
	private boolean unsavedChanges = false;
	
	private int polygonPoints = 8;
	private JPanel segmentPanel;
	private final TitledBorder segmentBorder = new TitledBorder(
			new MatteBorder(1, 0, 0, 0, UIManager.getColor("TitledBorder.titleColor")),
			NO_SEGMENT_TITLE, TitledBorder.CENTER, TitledBorder.TOP
	);
	
	private SegmentData selectedSegment = null;
	
	public JMenu[] getContextMenus() {
		final JMenu fileMenu = new JMenu(new SimpleAction("File"));
		fileMenu.add(new JMenuItem(new SimpleAction("New", event -> newTemplate())));
		fileMenu.add(new JMenuItem(new SimpleAction("Open", event -> openTemplate(null))));
		fileMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		fileMenu.add(new JMenuItem(new SimpleAction("Save", event -> saveTemplate())));
		fileMenu.add(new JMenuItem(new SimpleAction("Save as", event -> saveTemplate(null))));
		fileMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		fileMenu.add(new JMenuItem(new SimpleAction("Close", event -> {
			if (confirmExit("exit")) rootFrame.dispose();
		})));
		
		final JMenu editMenu = new JMenu(new SimpleAction("Edit"));
		
		return new JMenu[] {
				fileMenu,
				editMenu
		};
	}
	
	private static GridBagConstraints lineConstraints(final int line) {
		return new GridBagConstraints(
				0, line, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5),
				0, 0
		);
	}
	protected void postLayout() {
		int line = 0;
		
		LabeledSlider slider = new LabeledSlider(3, 24, 8);
		slider.addChangeListener(event -> {
			polygonPoints = slider.getValue();
		});
		
		toolsPanel.add(slider, lineConstraints(line++));
		toolsPanel.add(new JButton(new SimpleAction("create regular polygon", event -> {
			unsavedChanges = true;
			repaint();
			entityTemplate.add(new SegmentData(generateRegularPolygon(polygonPoints, 50)));
		})), lineConstraints(line++));
		
		segmentPanel = new JPanel(new GridBagLayout());
		segmentPanel.setBorder(segmentBorder);
		segmentPanel.add(new JButton("Click Me"));
		
		toolsPanel.add(segmentPanel, lineConstraints(line++));
		
		// spacer
		toolsPanel.add(new JPanel(), new GridBagConstraints(
				0, line, 1, 1, 1.0, 1.0,
				GridBagConstraints.PAGE_END, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
		));
	}
	
	// © ChatGPT
	public static ArrayList<Vec2> generateRegularPolygon(int sides, double radius) {
		ArrayList<Vec2> polygonVertices = new ArrayList<>();
		double angleIncrement = 2 * Math.PI / sides;
		
		for (int i = 0; i < sides; i++) {
			double x = radius * Math.cos((i * angleIncrement) - (Math.PI / 2));
			double y = radius * Math.sin((i * angleIncrement) - (Math.PI / 2));
			polygonVertices.add(new Vec2(x, y));
		}
		
		return polygonVertices;
	}
	
	private void selectSegment(SegmentData segment) {
		if (selectedSegment == segment) return;
		selectedSegment = segment;
		
		final boolean hasSegment = segment != null;
		
		segmentBorder.setTitle(hasSegment ? SEGMENT_TITLE(segment) : NO_SEGMENT_TITLE);
		toolsPanel.repaint();
	}
	private void update() {
		rootFrame.setContext(
				templateFile != null
						? templateFile.toPath().getFileName().toString() + (unsavedChanges ? "*" : "")
						: null
		);
		viewport.repaint();
	}
	
	protected void eventHandler(ComponentEvent event) {
		if (event instanceof MouseEvent mouseEvent && mouseEvent.getID() == MouseEvent.MOUSE_RELEASED && mouseEvent.getButton() == MouseEvent.BUTTON1) {
			Point2D pointer = viewport.component2viewport(mouseEvent.getPoint());
			SegmentData[] segments = entityTemplate.stream()
					.filter(poly -> poly.asNative().contains(pointer))
					.toArray(SegmentData[]::new);
			
			selectSegment(switch (segments.length) {
				case 0 -> null;
				case 1 -> segments[0];
				default -> (SegmentData) JOptionPane.showInputDialog(
						this,
						"Which segment should be selected?",
						"Multiple segments",
						JOptionPane.PLAIN_MESSAGE, null,
						segments, segments[0]);
			});
		}
	}
	private void paintHighlight(Graphics2D g) {
		Point2D pointer = viewport.component2viewport(viewport.getMousePosition());
		if (pointer == null) return;
		
		g.setColor(SHAPE_HOVER);
		entityTemplate.stream()
				.map(Polygon::asNative)
				.filter(poly -> poly.contains(pointer))
				.forEach(g::fill);
		
	}
	protected void paintViewport(Graphics2D g) {
		paintHighlight(g);
		
		if (selectedSegment != null) {
			g.setColor(SHAPE_SELECTED);
			g.fill(selectedSegment.asNative());
		}
		
		g.setColor(Color.black);
		entityTemplate.stream()
				.map(Polygon::asNative)
				.forEach(g::drawPolygon);
	}
	
	/**
	 * returns true if it's safe to exit (or the user wants to discord the changes)
	 */
	private boolean confirmExit(String action) {
		if (!unsavedChanges) return true;
		
		return JOptionPane.showConfirmDialog(
				this,
				"You have unsaved changed, which would be lost.",
				"Are you sure you want to " + action + "?",
				JOptionPane.OK_CANCEL_OPTION
		) == JOptionPane.OK_OPTION;
		
	}
	
	// -. actions .- \\
	public void newTemplate() {
		if (!confirmExit("create a new file")) return;
		this.templateFile = null;
		this.entityTemplate = new ArrayList<>();
		unsavedChanges = false;
		update();
	}
	public void openTemplate(File file) {
		if (!confirmExit("open a file")) return;
		if (file == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setVisible(true);
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(FILE_FILTER);
			chooser.setCurrentDirectory(ENTITY_TEMPLATE_DIRECTORY.toFile());
			
			if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
			file = chooser.getSelectedFile();
			if (! file.toString().endsWith(ENTITY_TEMPLATE_FILE_EXTENSION)) file = new File(file + ENTITY_TEMPLATE_FILE_EXTENSION);
		}
		
		try {
			this.entityTemplate = SegmentDataManager.loadExternal(file);
			this.templateFile = file;
			this.unsavedChanges = false;
			update();
			System.out.println(entityTemplate.size() + " segments loaded.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null,
					"IOException:\n" + e.getLocalizedMessage(),
					"Error opening entity template",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
	}
	public final void saveTemplate() { saveTemplate(this.templateFile); }
	public void saveTemplate(File file) {
		if (file == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setVisible(true);
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(FILE_FILTER);
			chooser.setCurrentDirectory(ENTITY_TEMPLATE_DIRECTORY.toFile());
			
			if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
			file = chooser.getSelectedFile();
			if (! file.toString().endsWith(ENTITY_TEMPLATE_FILE_EXTENSION)) file = new File(file + ENTITY_TEMPLATE_FILE_EXTENSION);
		}
		try {
			SegmentDataManager.saveExternal(file, this.entityTemplate);
			this.templateFile = file;
			this.unsavedChanges = false;
			update();
			System.out.println(entityTemplate.size() + " segments saved.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null,
					"IOException:\n" + e.getLocalizedMessage(),
					"Error saving entity template",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
}

