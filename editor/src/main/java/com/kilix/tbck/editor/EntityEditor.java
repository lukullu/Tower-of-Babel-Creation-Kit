package com.kilix.tbck.editor;

import com.tbck.data.entity.SegmentData;
import com.tbck.data.entity.SegmentDataManager;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Locale;

import static com.tbck.Constants.*;

public class EntityEditor extends EditorPanel {
	
	static {
		try {
			Files.createDirectories(ENTITY_TEMPLATE_STORAGE_DIRECTORY);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private static final FileFilter FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.getPath().toLowerCase(Locale.ROOT).endsWith(ENTITY_TEMPLATE_FILE_EXTENSION); }
		public String getDescription() { return ENTITY_TEMPLATE_FILE_TYPE_DESCRIPTION; }
	};
	
	private File templateFile = null;
	private ArrayList<SegmentData> entityTemplate = new ArrayList<>();
	
	public JMenu[] getContextMenus() {
		final JMenu fileMenu = new JMenu(new SimpleAction("File"));
		fileMenu.add(new JMenuItem(new SimpleAction("New", event -> newTemplate())));
		fileMenu.add(new JMenuItem(new SimpleAction("Open", event -> openTemplate(null))));
		fileMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		fileMenu.add(new JMenuItem(new SimpleAction("Save", event -> saveTemplate())));
		fileMenu.add(new JMenuItem(new SimpleAction("Save as", event -> saveTemplate(null))));
		fileMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		fileMenu.add(new JMenuItem(new SimpleAction("Close", event -> rootFrame.dispose())));
		
		final JMenu editMenu = new JMenu(new SimpleAction("Edit"));
		
		return new JMenu[] {
				fileMenu,
				editMenu
		};
	}
	
	private void updateTitle() {
		rootFrame.setContext(
				templateFile != null
						? templateFile.toPath().getFileName().toString()
						: null
		);
	}
	
	// -. actions .- \\
	public void newTemplate() {
		this.templateFile = null;
		this.entityTemplate = new ArrayList<>();
		updateTitle();
	}
	public void openTemplate(File file) {
		if (file == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setVisible(true);
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(FILE_FILTER);
			chooser.setCurrentDirectory(ENTITY_TEMPLATE_STORAGE_DIRECTORY.toFile());
			
			if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
			file = chooser.getSelectedFile();
			if (! file.toString().endsWith(ENTITY_TEMPLATE_FILE_EXTENSION)) file = new File(file + ENTITY_TEMPLATE_FILE_EXTENSION);
		}
		
		try {
			this.entityTemplate = SegmentDataManager.loadExternal(file);
			this.templateFile = file;
			updateTitle();
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
			chooser.setCurrentDirectory(ENTITY_TEMPLATE_STORAGE_DIRECTORY.toFile());
			
			if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
			file = chooser.getSelectedFile();
			if (! file.toString().endsWith(ENTITY_TEMPLATE_FILE_EXTENSION)) file = new File(file + ENTITY_TEMPLATE_FILE_EXTENSION);
		}
		
		try {
			SegmentDataManager.saveExternal(file, this.entityTemplate);
			this.templateFile = file;
			updateTitle();
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

