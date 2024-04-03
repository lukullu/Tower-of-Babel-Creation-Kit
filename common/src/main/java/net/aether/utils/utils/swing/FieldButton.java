package net.aether.utils.utils.swing;

import net.aether.utils.utils.reflection.FieldAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FieldButton<O, T> extends JButton implements ActionListener {
	
	
	@FunctionalInterface
	public interface ValueGenerator<T> {
		default String getDisplayString(T object) { return String.valueOf(object); }
		T getNewValue(T oldValue);
	}
	public static final Map<Class<?>, ValueGenerator<?>> GENERATOR_REGISTRY = new HashMap<>();
	
	private final FieldAccess<O, T> field;
	private O value;
	public FieldButton(FieldAccess<O, T> field) { this(field, null); }
	public FieldButton(FieldAccess<O, T> field, O value) {
		this.field = field;
		setValue(value);
		update();
	}
	
	public void actionPerformed(ActionEvent e) { set(); update(); }
	
	public void setValue(Object value) {
		if (value == null) { this.value = null; update(); return; }
		if (! field.objectType().isAssignableFrom(value.getClass()))
			throw new ClassCastException("setValue called with invalid type");
		this.value = field.objectType().cast(value);
		update();
	}
	
	public void update() {
		setEnabled(value != null);
		if (value == null) setText("unavailable");
		else setText(field.name() + ": " + field.getter().apply(value));
	}
	public void set() {
		Class<T> type = field.fieldType();
		if (type.equals(String.class)) ;
	}
	
}
