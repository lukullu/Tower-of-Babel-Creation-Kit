package com.tbck.data.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class SegmentDataManager {

	private static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.setLenient()
			.registerTypeAdapter(Vec2.class, Vec2.TYPE_ADAPTER)
			.registerTypeAdapter(Polygon.class, Polygon.TYPE_ADAPTER)
			.create();
	private static final Map<String, ArrayList<SegmentData>> DATA_CACHE = new HashMap<>(8);
	private static Type LIST_TYPE = new TypeToken<ArrayList<SegmentData>>(){}.getType();
	
	private static ArrayList<SegmentData> load(InputStream stream) throws IOException {
		try (ObjectInputStream is = new ObjectInputStream(stream)) {
			Object o = is.readObject();
			if (o instanceof ArrayList<?> list)
				return (ArrayList<SegmentData>) list;
		} catch (ClassNotFoundException e) { throw new IOException("Unable to parse data: ", e); }
		return null;
	}
	
	@SuppressWarnings("unchecked")
	/** load SegmentData from an external file */
	public static ArrayList<SegmentData> loadExternal(File file) throws IOException {
		Objects.requireNonNull(file, "file must not be null!");
		ArrayList<SegmentData> data = DATA_CACHE.computeIfAbsent("file:" + file, key -> {
			try {
				return load(Files.newInputStream(file.toPath()));
			} catch (IOException e) {
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
		return new ArrayList<>(data.stream().map(SegmentData::new).toList());
	}
	/** load SegmentData from an internal resource */
	public static ArrayList<SegmentData> loadInternal(String resourceName) {
		try {
			Objects.requireNonNull(resourceName, "resourceName must not be null!");
			InputStream resource = Objects.requireNonNull(SegmentDataManager.class.getResourceAsStream(resourceName), "Unable to find resource " + resourceName);
			return DATA_CACHE.computeIfAbsent("resource:" + resourceName, key -> {
				try {
					return load(resource);
				} catch (IOException e) { throw new RuntimeException(e); }
			});
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
			invalidate("file:" + file);
		}
	}
	/** invalidate cache for file (force re-read on next load call) */
	public static void invalidate(String file) { DATA_CACHE.remove(file); }

	public static ArrayList<SegmentData> importJson(File file) throws IOException {
		Objects.requireNonNull(file, "file must not be null!");
		ArrayList<SegmentData> data = DATA_CACHE.computeIfAbsent("json:" + file, key -> {
			try (FileReader reader = new FileReader(file)) {
				return new ArrayList<>(GSON.fromJson(reader, LIST_TYPE));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						null,
						"ClassNotFoundException: " + e.getLocalizedMessage(),
						"Error importing SegmentData.",
						JOptionPane.ERROR_MESSAGE
				);
			}
			return null;
		});
		if (data == null) return null;
		return new ArrayList<>(data.stream().map(SegmentData::new).toList());
	}
	
	public static void exportJson(File file, List<SegmentData> segments) throws IOException {
		Objects.requireNonNull(file, "file must not be null!");
		Objects.requireNonNull(segments, "segments must not be null!");
		Files.createDirectories(file.toPath().getParent());
		try (FileWriter writer = new FileWriter(file)) { GSON.toJson(segments, writer); }
		invalidate("json:" + file);
	}

	// create new instances of everything
	public static ArrayList<SegmentData> copyFromResource(ArrayList<SegmentData> input)
	{
		return new ArrayList<>(input.stream().map(SegmentData::new).toList());
	}

	public static ArrayList<Vec2> copyFromVertices(ArrayList<Vec2> input)
	{
		return new ArrayList<>(input.stream().map(Vec2::new).toList());
	}
	
}
