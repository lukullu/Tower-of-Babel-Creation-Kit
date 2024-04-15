package net.aether.utils.utils.swing;

import net.aether.utils.utils.reflection.FieldAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class FieldButton<O, T> extends JButton implements ActionListener {
	
	
	/** "generates" a value by asking the user to enter one */
	@FunctionalInterface
	public interface ValueGenerator<T> { T getNewValue(Object oldValue); }
	public static final Map<Class<?>, ValueGenerator<?>> GENERATOR_REGISTRY = new HashMap<>(Map.ofEntries(
			Map.entry(String.class, new StringGenerator()),
			Map.entry(Integer.class, new NumberGenerator<>(Integer.class, Integer::parseInt)),
			Map.entry(Short.class, new NumberGenerator<>(Short.class, Short::parseShort)),
			Map.entry(Double.class, new NumberGenerator<>(Double.class, Double::parseDouble)),
			Map.entry(Float.class, new NumberGenerator<>(Float.class, Float::parseFloat)),
			Map.entry(Boolean.class, new BooleanToggleGenerator())
	));
	
	private final Runnable onChange;
	private final FieldAccess<O, T> field;
	private O subject;
	public FieldButton(FieldAccess<O, T> field) { this(field, null, null ); }
	public FieldButton(FieldAccess<O, T> field, O subject) { this(field, subject, null); }
	public FieldButton(FieldAccess<O, T> field, O subject, Runnable onChange) {
		this.field = field;
		this.onChange = onChange;
		this.addActionListener(this);
		setSubject(subject);
		update();
	}
	
	public void actionPerformed(ActionEvent e) {
		set();
		if (onChange != null) onChange.run();
		update();
	}
	
	public void setSubject(Object subject) {
		if (subject == null) { this.subject = null; update(); return; }
		if (! field.objectType().isAssignableFrom(subject.getClass()))
			throw new ClassCastException("setValue called with invalid type");
		this.subject = field.objectType().cast(subject);
		update();
	}
	
	public void update() {
		setEnabled(subject != null && field.hasSetter());
		if (subject == null) setText("unavailable");
		else if (! field.hasGetter()) setText(field.name());
		else setText(field.name() + ": " + field.getter().apply(subject));
	}
	@SuppressWarnings("unchecked")
	public void set() {
		Class<?> type = unPrimitivize(field.fieldType());
		T oldValue = field.hasGetter() ? field.getter().apply(subject) : null;
		
		if (type == void.class) {
			field.setter().accept(subject, null);
			return;
		}
		
		if (type.isEnum()) {
			T value = (T) JOptionPane.showInputDialog(null, "Input " + type.getSimpleName(), "Input", JOptionPane.QUESTION_MESSAGE, null, type.getEnumConstants(), oldValue);
			field.setter().accept(subject, value != null ? value : (T) oldValue);
			return;
		}
		
		ValueGenerator<?> generator = Objects.requireNonNull(GENERATOR_REGISTRY.get(type), "No generator for type " + type + " found.");
		T value = (T) generator.getNewValue(oldValue);
		field.setter().accept(subject, value);
	}
	
	/** converts primitive classes to object classes. Does <b>NOT</b> convert void.class to Void.clss */
	private Class<?> unPrimitivize(Class<?> type) {
		if (type == boolean.class) 	return Boolean.class;
		if (type == byte.class) 	return Byte.class;
		if (type == short.class) 	return Short.class;
		if (type == int.class) 		return Integer.class;
		if (type == long.class) 	return Long.class;
		if (type == float.class) 	return Float.class;
		if (type == double.class) 	return Double.class;
		return type;
	}
	
	public static class BooleanToggleGenerator implements ValueGenerator<Boolean> {
		public Boolean getNewValue(Object oldValue) {
			return ! (Boolean) oldValue;
		}
	}
	public static class BooleanGenerator implements ValueGenerator<Boolean> {
		private static final Boolean[] values = new Boolean[] { false, true };
		
		public Boolean getNewValue(Object oldValue) {
			int index = JOptionPane.showOptionDialog(null, "Input Boolean", "Input", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, values, oldValue);
			if (index == JOptionPane.CLOSED_OPTION) return (Boolean) oldValue;
			return values[index];
		}
	}
	public static class StringGenerator implements ValueGenerator<String> {
		public String getNewValue(Object oldValue) {
			return Objects.requireNonNullElse(JOptionPane.showInputDialog(null, "Input String", oldValue), String.valueOf(oldValue));
		}
	}
	public record NumberGenerator<T extends Number>(Class<T> type, Function<String, T> converter) implements ValueGenerator<T> {
		@SuppressWarnings("unchecked")
		public T getNewValue(Object oldValue) {
			T out = null;
			String value = String.valueOf(oldValue);
			do {
				value = JOptionPane.showInputDialog(null, "Input " + type.getSimpleName(), value);
				if (value == null) return (T) oldValue;
				try { out = converter.apply(value); }
				catch (Exception ignored) {}
			} while (out == null);
			return out;
		}
	}
	
	public void paint(Graphics g) {
		update();
		super.paint(g);
	}
}
