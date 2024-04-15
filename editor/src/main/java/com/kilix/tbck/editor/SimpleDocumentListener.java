package com.kilix.tbck.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public record SimpleDocumentListener(Consumer<DocumentEvent> handler) implements DocumentListener {
	
	public void insertUpdate(DocumentEvent e) { handler.accept(e); }
	public void removeUpdate(DocumentEvent e) { handler.accept(e); }
	public void changedUpdate(DocumentEvent e) { handler.accept(e); }
}
