package com.kilix.tbck.editor.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.function.Consumer;

public class Viewport extends JPanel {
	
	private final Consumer<Graphics2D> paintMethod;
	private final Color background = UIManager.getColor("Panel.background");
	
	private Point2D pointer = null;
	private double scale = 1.0;
	private int offsetX, offsetY;
	
	public Viewport(Consumer<Graphics2D> paintMethod, Consumer<ComponentEvent> eventHandler) {
		super(true);
		this.paintMethod = Objects.requireNonNullElse(paintMethod, g -> {});
		new ViewportHandler(eventHandler);
	}
	public void paint(Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.transform(getTransform());
		paintMethod.accept(g2d);
	}
	
	public Point2D component2viewport(Point2D point) {
		if (point == null) return null;
		try {
			return getTransform().inverseTransform(point, new Point());
		} catch (NoninvertibleTransformException e) { return null; }
	}
	public Point2D viewport2component(Point2D point) {
		if (point == null) return null;
		return getTransform().transform(point, new Point());
	}
	
	public double getScale() { return scale; }
	public int getOffsetX() { return offsetX; }
	public int getOffsetY() { return offsetY; }
	
	public AffineTransform getTransform() {
		AffineTransform transform = AffineTransform.getTranslateInstance((getWidth() / 2.0) + offsetX, (getHeight() / 2.0) + offsetY);
		transform.scale(scale, scale);
		return transform;
	}
	
	public class ViewportHandler implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
		private final Consumer<ComponentEvent> eventHandler;
		public ViewportHandler(Consumer<ComponentEvent> eventHandler) {
			this.eventHandler = eventHandler;
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
			addComponentListener(this);
		}
		
		public void componentResized(ComponentEvent e) { eventHandler.accept(e); }
		public void componentMoved(ComponentEvent e) { eventHandler.accept(e); }
		public void componentShown(ComponentEvent e) { eventHandler.accept(e); }
		public void componentHidden(ComponentEvent e) { eventHandler.accept(e); }
		public void mouseClicked(MouseEvent e) { eventHandler.accept(e); repaint(); }
		public void mousePressed(MouseEvent e) { eventHandler.accept(e); repaint(); }
		public void mouseReleased(MouseEvent e) { eventHandler.accept(e); repaint(); }
		public void mouseEntered(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseExited(MouseEvent e) { pointer = null; eventHandler.accept(e); repaint(); }
		public void mouseDragged(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseMoved(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseWheelMoved(MouseWheelEvent e) {
			scale = Math.max(0, scale - (e.getUnitsToScroll() / 30.0));
			eventHandler.accept(e);
			repaint();
		}
	}
	
}
