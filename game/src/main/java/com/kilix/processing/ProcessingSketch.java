package com.kilix.processing;

import processing.awt.PSurfaceAWT;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class ProcessingSketch<T extends ExtendedPApplet> extends JFrame {

	private ProcessingSketch() {}
	
	public static <E extends ExtendedPApplet> ProcessingSketchBuilder<E> builder(Class<E> clazz) {
		return new ProcessingSketchBuilder<>(clazz);
	}
	
	public static class ProcessingSketchBuilder<T extends ExtendedPApplet> {
		private final Class<T> clazz;
		private int width = 500, height = 500;
		private boolean fullscreen = false;
		
		private ProcessingSketchBuilder(Class<T> clazz) {
			assert clazz != null;
			this.clazz = clazz;
		}
		
		public ProcessingSketchBuilder<T> size(int width, int height) {
			if (fullscreen) return this;
			this.width = width;
			this.height = height;
			return this;
		}
		public ProcessingSketchBuilder<T> fullScreen() {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			width = screenDim.width;
			height = screenDim.height;
			this.fullscreen = true;
			return this;
		}
		
		
		public ProcessingSketch<T> build() {
			assert ProcessingClass.appletWrapper.value == null;
			
			ProcessingSketch<T> frame = new ProcessingSketch<>();
			
			frame.setUndecorated(fullscreen);
			frame.setResizable(false);
			frame.setTitle(clazz.getSimpleName());
			frame.getContentPane().setPreferredSize(new Dimension(width, height));
			frame.pack();
			frame.setVisible(true);
			
			frame.getContentPane().setBackground(Color.black);
			
			try {
				T sketch = clazz.getConstructor().newInstance();
				ProcessingClass.appletWrapper.value = sketch;
				sketch.frame = frame;
				sketch.setSize(width, height);
				PSurfaceAWT surface = ReflectUtils.callMethod("initSurface", sketch, PSurfaceAWT.class);
				ReflectUtils.callMethod("startSurface", sketch, Void.class);
				
				Field canvasField = PSurfaceAWT.class.getDeclaredField("canvas");
				canvasField.setAccessible(true);
				Canvas canvas = (Canvas) canvasField.get(surface);
				frame.getContentPane().add(canvas);
				
				canvas.setBounds(0, 0, width, height);
			} catch (Exception e) {
				System.err.println("Unable to create processing Sketch for class " + clazz.getSimpleName());
				e.printStackTrace();
				System.exit(-1);
			}
			
			return frame;
		}
	}
	
	
	
}
