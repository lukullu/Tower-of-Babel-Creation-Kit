//package net.aether.utils.utils.swing;
//
//import net.aether.utils.utils.data.SimpleCache;
//import net.aether.utils.utils.reflection.ClassAccess;
//import net.aether.utils.utils.reflection.FieldAccess;
//
//import javax.swing.*;
//import java.util.HashMap;
//import java.util.Objects;
//
///**
// * A JPanel that automatically generates a form for an object.
// * @author Kilix
// * @param <T>
// */
//public final class PropertiesPanel<T> extends JPanel {
//
//	private static final HashMap<Class<?>, ClassAccess<?>> accessCache = new SimpleCache<>(8);
//	private final ClassAccess<T> classAccess;
//	private FieldAccess<T, ?>[] fields;
//
//	private T value;
//	public PropertiesPanel(Class<T> typeClass) { this(typeClass, null); }
//	public PropertiesPanel(Class<T> typeClass, T value) {
//		Objects.requireNonNull(typeClass, "Unable to generate fields as typeClass is null.");
//
//		setValue(value);
//	}
//
//	public T getValue() { return value; }
//	public void setValue(T value) {
//		this.value = value;
//		fields = classAccess.fields().apply(value);
//	}
//
//}
