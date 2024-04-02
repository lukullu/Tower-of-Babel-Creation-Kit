package com.kilix.tbck.editor.components;

import com.tbck.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class Viewport extends JPanel {
	
	private static final ImageIcon RECENTER_ICON;
	private static final ImageIcon UNZOOM_ICON;
	
	static {
		BufferedImage empty = new BufferedImage(48, 48, BufferedImage.TYPE_4BYTE_ABGR);
		Image recenter = empty, unzoom = empty;
		try {
			recenter = ImageIO.read(Objects.requireNonNull(
					Viewport.class.getResourceAsStream("/icons/recenter.png"),
					"Icon /icon/recenter.png not found"
			));
			unzoom = ImageIO.read(Objects.requireNonNull(
					Viewport.class.getResourceAsStream("/icons/unzoom.png"),
					"Icon /icon/unzoom.png not found"
			));
		} catch (NullPointerException | IOException e) {
			System.err.println("Unable to load viewport icons: " + e.getLocalizedMessage());
		}
		RECENTER_ICON = new ImageIcon(recenter);
		UNZOOM_ICON = new ImageIcon(unzoom);
	}
	
	private final JButton recenterButton = new JButton(RECENTER_ICON);
	private final JButton unzoomButton = new JButton(UNZOOM_ICON);
	private int buttonSize = 32;
	
	private final Consumer<Graphics2D> paintMethod;
	private final Color background = UIManager.getColor("Panel.background");
	private final Color focusColor = UIManager.getColor("Component.focusedBorderColor");
	
	public double zoomSpeed = 0.1;
	
	private Point2D pointer = null;
	private double scale = 1.0;
	private int offsetX = 0, offsetY = 0;
	
	public Viewport(Consumer<Graphics2D> paintMethod, Consumer<ComponentEvent> eventHandler) {
		super(true);
		setFocusable(true);
		this.paintMethod = Objects.requireNonNullElse(paintMethod, g -> {});
		new ViewportHandler(eventHandler);
		
		unzoomButton.addActionListener(event -> { scale = 1.0; repaint(); });
		unzoomButton.setFocusable(false);
		add(unzoomButton);
		recenterButton.addActionListener(event -> { offsetX = 0; offsetY = 0; repaint(); });
		recenterButton.setFocusable(false);
		add(recenterButton);
	}
	public void paint(Graphics g) {
		g.setColor(focusColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		int border = hasFocus() ? 2 : 0;
		g.setColor(background);
		g.fillRect(border, border, getWidth() - border * 2, getHeight() - border * 2);
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.transform(getTransform());
		paintMethod.accept(g2d);
		
		int x = getWidth() - 5;
		boolean showUnzoom = scale < 0.95 || scale > 1.05, showRecenter = offsetX != 0 || offsetY != 0;
		unzoomButton.setEnabled(showUnzoom);
		unzoomButton.setVisible(showUnzoom);
		recenterButton.setEnabled(showRecenter);
		recenterButton.setVisible(showRecenter);
		
		if (showUnzoom) unzoomButton.setBounds(x -= (buttonSize + 5), 5, buttonSize, buttonSize);
		if (showRecenter) recenterButton.setBounds(x -= (buttonSize + 5), 5, buttonSize, buttonSize);
		
		super.paintChildren(g);
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
	
	public class ViewportHandler implements
			MouseListener,
			MouseMotionListener,
			MouseWheelListener,
			ComponentListener,
			KeyListener,
			FocusListener {
		
		private final Consumer<ComponentEvent> eventHandler;
		public ViewportHandler(Consumer<ComponentEvent> eventHandler) {
			this.eventHandler = eventHandler;
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
			addComponentListener(this);
			addKeyListener(this);
			addFocusListener(this);
		}
		
		public void componentResized(ComponentEvent e) { eventHandler.accept(e); }
		public void componentMoved(ComponentEvent e) { eventHandler.accept(e); }
		public void componentShown(ComponentEvent e) { eventHandler.accept(e); }
		public void componentHidden(ComponentEvent e) { eventHandler.accept(e); }
		public void mouseClicked(MouseEvent e) { grabFocus(); eventHandler.accept(e); repaint(); }
		public void mousePressed(MouseEvent e) { grabFocus(); eventHandler.accept(e); repaint(); }
		public void mouseReleased(MouseEvent e) { eventHandler.accept(e); repaint(); }
		public void mouseEntered(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseExited(MouseEvent e) { pointer = null; eventHandler.accept(e); repaint(); }
		public void mouseDragged(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseMoved(MouseEvent e) { pointer = e.getPoint(); eventHandler.accept(e); repaint(); }
		public void mouseWheelMoved(MouseWheelEvent e) {
			scale = Math.max(0.01, scale + (zoomSpeed * (e.getUnitsToScroll() / -3.0) * scale));
			eventHandler.accept(e);
			repaint();
		}
		public void keyTyped(KeyEvent e) {  }
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A -> offsetX -= 10;
				case KeyEvent.VK_D -> offsetX += 10;
				case KeyEvent.VK_W -> offsetY -= 10;
				case KeyEvent.VK_S -> offsetY += 10;
			}
			eventHandler.accept(e);
			repaint();
		}
		public void keyReleased(KeyEvent e) { eventHandler.accept(e); }
		public void focusGained(FocusEvent e) { repaint(); }
		public void focusLost(FocusEvent e) { repaint(); }
	}
	
}
