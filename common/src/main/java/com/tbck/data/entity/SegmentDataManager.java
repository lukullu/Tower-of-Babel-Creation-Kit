package com.tbck.data.entity;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class SegmentDataManager {
	
	private static final Map<File, ArrayList<SegmentData>> DATA_CACHE = new HashMap<>(8);
	
	@SuppressWarnings("unchecked")
	/** load SegmentData from an external file */
	public static ArrayList<SegmentData> loadExternal(File file) throws IOException {
		Objects.requireNonNull(file, "file must not be null!");
		ArrayList<SegmentData> data = DATA_CACHE.computeIfAbsent(file, key -> {
			try (ObjectInputStream is = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
				Object o = is.readObject();
				if (o instanceof ArrayList<?> list)
					return (ArrayList<SegmentData>) list;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						null,
						"ClassNotFoundException: " + e.getLocalizedMessage(),
						"Error loading SegmentData.",
						JOptionPane.ERROR_MESSAGE
				);
			}
			return null;
		});
		if (data == null) return null;
		return new ArrayList<>(data.stream().map(SegmentData::new).toList()); // only give out copies of the template
	}
	/** load SegmentData from an internal resource */
	public static ArrayList<SegmentData> loadInternal(String resourceName) {
		try {
			Objects.requireNonNull(resourceName, "resourceName must not be null!");
			URL resourceURL = SegmentDataManager.class.getResource(resourceName);
			return loadExternal(new File(Objects.requireNonNull(resourceURL, "Unable to find resource " + resourceName).toURI()));
		} catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); } // should not happen?
	}
	/** save SegmentData to an external file */
	public static void saveExternal(File file, List<SegmentData> segments) throws IOException {
		Objects.requireNonNull(file, "file must not be null!");
		Objects.requireNonNull(segments, "segments must not be null!");
		
		Files.createDirectories(file.toPath().getParent());
		try (ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(file.toPath(), StandardOpenOption.CREATE))) {
			if (segments instanceof ArrayList<SegmentData>) os.writeObject(segments);
			else os.writeObject(new ArrayList<>(segments));
			invalidate(file);
		}
	}
	/** invalidate cache for file (force re-read on next load call) */
	public static void invalidate(File file) { DATA_CACHE.remove(file); }

}
