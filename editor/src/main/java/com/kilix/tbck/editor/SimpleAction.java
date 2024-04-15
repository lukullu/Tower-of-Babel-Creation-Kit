package com.kilix.tbck.editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SimpleAction implements Action {
	
	public SimpleAction() { this(null, null); }
	public SimpleAction(String name) { this(name, null); }
	public SimpleAction(Consumer<ActionEvent> onActionPerformed) { this(null, onActionPerformed); }
	public SimpleAction(String name, Consumer<ActionEvent> onActionPerformed) {
		setName(name);
		setOnActionPerformed(onActionPerformed);
	}
	
	// enabled \\
	protected boolean enabled = true;
	public final void setEnabled(boolean b) { enabled = b; }
	public final boolean isEnabled() { return enabled; }
	public SimpleAction enabled(boolean enabled) { this.enabled = enabled; return this; }
	
	// property change listeners \\
	protected final ArrayList<PropertyChangeListener> propertyChangeListeners = new ArrayList<>(1);
	public final void addPropertyChangeListener(PropertyChangeListener listener) { propertyChangeListeners.add(listener); }
	public final void removePropertyChangeListener(PropertyChangeListener listener) { propertyChangeListeners.remove(listener); }
	protected final void propertyChanged(Object source, String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
		propertyChangeListeners.forEach(it -> it.propertyChange(event));
	}
	
	// other values \\
	protected String name;
	protected final Map<String, Object> customValues = new HashMap<>(0);
	public final Object getValue(String key) {
		return switch (key) {
			case Action.NAME -> getName();
			case Action.SHORT_DESCRIPTION -> getShortDescription();
			case Action.LONG_DESCRIPTION -> getLongDescription();
			case Action.SMALL_ICON -> getSmallIcon();
			case Action.LARGE_ICON_KEY -> getLargeIconKey();
			default -> customValues.get(key);
		};
	}
	public final void putValue(String key, Object value) {
	
	}
	
	public String getName() { return name; }
	public void setName(String name) {
		this.name = Objects.requireNonNullElse(name, "unnamed");
	}
	String getShortDescription() { return ""; }
	String getLongDescription() { return getShortDescription(); }
	Icon getSmallIcon() { return null; }
	String getLargeIconKey() { return null; }
	
	// Action Event \\
	protected Consumer<ActionEvent> onActionPerformed;
	public void setOnActionPerformed(Consumer<ActionEvent> onActionPerformed) {
		this.onActionPerformed = Objects.requireNonNullElse(onActionPerformed, event -> {});
	}
	public void actionPerformed(ActionEvent event) { onActionPerformed.accept(event); }
	
}
