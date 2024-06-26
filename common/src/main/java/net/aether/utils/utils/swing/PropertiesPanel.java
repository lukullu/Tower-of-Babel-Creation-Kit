package net.aether.utils.utils.swing;

import net.aether.utils.utils.data.SimpleCache;
import net.aether.utils.utils.reflection.FieldAccess;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A JPanel that automatically generates a form for an object.
 * @author Kilix
 * @param <T>
 */
public final class PropertiesPanel<T> extends JPanel {
	
	private static final HashMap<Class<?>, FieldAccess<?, ?>[]> accessCache = new SimpleCache<>(8);
	private final FieldAccess<T, ?>[] fields;
	private final ArrayList<FieldButton<T, ?>> components = new ArrayList<>();

	private T value;
	public PropertiesPanel(Class<T> type) { this(type, null); }
	public PropertiesPanel(Class<T> type, T value) {
		this((FieldAccess<T, ?>[]) accessCache.computeIfAbsent(type, key -> FieldAccess.createFieldAccess(Objects.requireNonNull(type, "Unable to generate fields as type is null."))), value);
	}
	private PropertiesPanel(FieldAccess<T, ?>[] fields, T value) {
		super(new GridLayout(fields.length, 1));
		this.fields = fields;
		for (FieldAccess<T, ?> field : fields)
			components.add((FieldButton<T, ?>) add(new FieldButton(field, null, this::repaint)));
		setValue(value);
	}
	
	public T getValue() { return value; }
	public void setValue(T value) {
		this.value = value;
		components.forEach(comp -> comp.setSubject(value));
	}

}
