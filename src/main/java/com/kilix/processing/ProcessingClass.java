package com.kilix.processing;

import processing.core.PFont;
import processing.core.PImage;

import java.awt.*;
import java.io.*;

/**
 * Any class that needs access to the standard Processing methods, can simply extend this interface<br>
 * and be provided with that capability
 * @author Kilix
 */
public interface ProcessingClass extends HelperMethods {

	final class Wrapper<T> { T value; }
	Wrapper<ExtendedPApplet> appletWrapper = new Wrapper<>();
	
	// - RECT - \\
	default void rect(float x, float y, float width, float height) {
		appletWrapper.value.rect(x, y, width, height);
	}
	default void rect(float x, float y, float width, float height, float cornerRadius) {
		appletWrapper.value.rect(x, y, width, height, cornerRadius);
	}
	default void rect(float x, float y, float width, float height, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
		appletWrapper.value.rect(x, y, width, height, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
	}
	default void rectMode(int mode) {
		appletWrapper.value.rectMode(mode);
	}
	
	// - ELLIPSE - \\
	default void ellipse(float x, float y, float width, float height) {
		appletWrapper.value.ellipse(x, y,width, height);
	}
	default void ellipseMode(int mode) {
		appletWrapper.value.ellipseMode(mode);
	}
	
	// - TEXT - \\
	default void text(char text, float x, float y) {
		appletWrapper.value.text(text, x, y);
	}
	default void text(char text, float x, float y, float z) {
		appletWrapper.value.text(text, x, y, z);
	}
	default void text(Object text, float x, float y) {
		appletWrapper.value.text(String.valueOf(text), x, y);
	}
	default void text(Object text, float x, float y, float z) {
		appletWrapper.value.text(String.valueOf(text), x, y, z);
	}
	default void text(Object text, int start, int stop, float x, float y) {
		appletWrapper.value.text(String.valueOf(text).toCharArray(), start, stop, x, y);
	}
	default void text(Object text, int start, int stop, float x, float y, float z) {
		appletWrapper.value.text(String.valueOf(text).toCharArray(), start, stop, x, y, z);
	}
	default void text(Object text, float x1, float y1, float x2, float y2) {
		appletWrapper.value.text(String.valueOf(text), x1, y1, x2, y2);
	}
	default void textAlign(int alignX) {
		appletWrapper.value.textAlign(alignX);
	}
	default void textAlign(int alignX, int alignY) {
		appletWrapper.value.textAlign(alignX, alignY);
	}
	default void textFont(PFont font) {
		appletWrapper.value.textFont(font);
	}
	default void textFont(PFont font, float size) {
		appletWrapper.value.textFont(font, size);
	}
	default void textMode(int mode) {
		appletWrapper.value.textMode(mode);
	}
	default void textSize(float size) {
		appletWrapper.value.textSize(size);
	}
	default void textLeading(float leading) {
		appletWrapper.value.textLeading(leading);
	}
	default float textWidth(char charToMeasure) {
		return appletWrapper.value.textWidth(charToMeasure);
	}
	default float textWidth(String stringToMeasure) {
		return appletWrapper.value.textWidth(stringToMeasure);
	}
	default float textWidth(Object text, int start, int end) {
		return appletWrapper.value.textWidth(String.valueOf(text).toCharArray(), start, end);
	}
	default float textAscent() {
		return appletWrapper.value.textAscent();
	}
	default float textDescent() {
		return appletWrapper.value.textDescent();
	}
	default PFont createFont(String name, float size) {
		return appletWrapper.value.createFont(name, size);
	}
	default PFont createFont(String name, float size, boolean smooth) {
		return appletWrapper.value.createFont(name, size, smooth);
	}
	default PFont creatFont(String name, float size, boolean smooth, char[] charset) {
		return appletWrapper.value.createFont(name, size, smooth, charset);
	}
	default PFont loadFont(String filename) {
		return appletWrapper.value.loadFont(filename);
	}
	
	// - Fill - \\
	default void fill(int rgb) {
		appletWrapper.value.fill(rgb);
	}
	default void fill(int rgb, float alpha) {
		appletWrapper.value.fill(rgb, alpha);
	}
	default void fill(float gray) {
		appletWrapper.value.fill(gray);
	}
	default void fill(float gray, float alpha) {
		appletWrapper.value.fill(gray, alpha);
	}
	default void fill(float redOrHue, float greenOrSaturation, float blueOrBrightness) {
		appletWrapper.value.fill(redOrHue, greenOrSaturation, blueOrBrightness);
	}
	default void fill(float redOrHue, float greenOrSaturation, float blueOrBrightness, float alpha) {
		appletWrapper.value.fill(redOrHue, greenOrSaturation, blueOrBrightness, alpha);
	}
	default void noFill() {
		appletWrapper.value.noFill();
	}
	
	// - STROKE - \\
	default void stroke(int rgb) {
		appletWrapper.value.stroke(rgb);
	}
	default void stroke(int rgb, float alpha) {
		appletWrapper.value.stroke(rgb, alpha);
	}
	default void stroke(float gray) {
		appletWrapper.value.stroke(gray);
	}
	default void stroke(float gray, float alpha) {
		appletWrapper.value.stroke(gray, alpha);
	}
	default void stroke(float redOrHue, float greenOrSaturation, float blueOrBrightness) {
		appletWrapper.value.stroke(redOrHue, greenOrSaturation, blueOrBrightness);
	}
	default void stroke(float redOrHue, float greenOrSaturation, float blueOrBrightness, float alpha) {
		appletWrapper.value.stroke(redOrHue, greenOrSaturation, blueOrBrightness, alpha);
	}
	default void noStroke() {
		appletWrapper.value.noStroke();
	}
	
	// - BACKGROUND - \\
	default void background(int rgb) {
		appletWrapper.value.background(rgb);
	}
	default void background(int rgb, float alpha) {
		appletWrapper.value.background(rgb, alpha);
	}
	default void background(float gray) {
		appletWrapper.value.background(gray);
	}
	default void background(float gray, float alpha) {
		appletWrapper.value.background(gray, alpha);
	}
	default void background(float redOrHue, float greenOrSaturation, float blueOrBrightness) {
		appletWrapper.value.background(redOrHue, greenOrSaturation, blueOrBrightness);
	}
	default void background(float redOrHue, float greenOrSaturation, float blueOrBrightness, float alpha) {
		appletWrapper.value.background(redOrHue, greenOrSaturation, blueOrBrightness, alpha);
	}
	default void background(PImage image) {
		appletWrapper.value.background(image);
	}
	default void background(Image image) {
		appletWrapper.value.background(new PImage(image));
	}
	
	// - MSC DRAW - \\
	default void translate(float x, float y) {
		appletWrapper.value.translate(x, y);
	}
	default void translate(float x, float y, float z) {
		appletWrapper.value.translate(x, y, z);
	}
	default void rotate(float angle) {
		appletWrapper.value.rotate(angle);
	}
	default void rotateX(float angle) {
		appletWrapper.value.rotateX(angle);
	}
	default void rotateY(float angle) {
		appletWrapper.value.rotateY(angle);
	}
	default void rotateZ(float angle) {
		appletWrapper.value.rotateZ(angle);
	}
	default void scale(float scalePercentage) {
		appletWrapper.value.scale(scalePercentage);
	}
	default void scale(float xPercentage, float yPercentage) {
		appletWrapper.value.scale(xPercentage, yPercentage);
	}
	default void scale(float xPercentage, float yPercentage, float zPercentage) {
		appletWrapper.value.scale(xPercentage, yPercentage, zPercentage);
	}
	default void pushMatrix() {
		appletWrapper.value.pushMatrix();
	}
	default void popMatrix() {
		appletWrapper.value.popMatrix();
	}
	default void arc(float ellipseX, float ellipseY, float ellipseWidth, float ellipseHeight, float startAngle, float stopAngle) {
		appletWrapper.value.arc(ellipseX, ellipseY, ellipseWidth, ellipseHeight, startAngle, stopAngle);
	}
	default void arc(float ellipseX, float ellipseY, float ellipseWidth, float ellipseHeight, float startAngle, float stopAngle, int mode) {
		appletWrapper.value.arc(ellipseX, ellipseY, ellipseWidth, ellipseHeight, startAngle, stopAngle, mode);
	}
	
	// - LINE - \\
	default void line(float x1, float y1, float x2, float y2) {
		appletWrapper.value.line(x1, y1, x2, y2);
	}
	default void line(float x1, float y1, float z1, float x2, float y2, float z2) {
		appletWrapper.value.line(x1, y1, z1, x2, y2, z2);
	}
	
	// - POINT - \\
	default void point(float x, float y) {
		appletWrapper.value.point(x, y);
	}
	default void point(float x, float y, float z) {
		appletWrapper.value.point(x, y, z);
	}
	
	// - KEYBOARD - \\
	default char getKey() {
		return appletWrapper.value.key;
	}
	default int getKeyCode() {
		return appletWrapper.value.keyCode;
	}
	default boolean getKeyPressed() {
		return appletWrapper.value.keyPressed;
	}
	
	// - MOUSE - \\
	default int getMouseButton() {
		return appletWrapper.value.mouseButton;
	}
	default int getMouseX() {
		return appletWrapper.value.mouseX;
	}
	default int getMouseY() {
		return appletWrapper.value.mouseY;
	}
	default boolean getMousePressed() {
		return appletWrapper.value.mousePressed;
	}
	
	default BufferedReader createReader(String fileName) {
		try {
			File file = new File(fileName);
			if (! file.exists()) file.getParentFile().mkdirs();
			return new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Unable to create reader", e);
		}
	}
	default PrintWriter createWriter(String fileName) {
		try {
			File file = new File(fileName);
			if (! file.exists()) file.getParentFile().mkdirs();
			file.createNewFile();
			return new PrintWriter(file);
		} catch (IOException e) {
			throw new RuntimeException("Unable to create writer", e);
		}
	}
	default File dataFile(String where) {
		return new File("./data/" + where);
	}
	
	default int getWidth() {
		return appletWrapper.value.width;
	}
	default int getHeight() {
		return appletWrapper.value.height;
	}
	
	default float random(Number upperBound) {
		return appletWrapper.value.random(upperBound.floatValue());
	}
	default float random(Number lowerBound, Number upperBound) {
		return appletWrapper.value.random(lowerBound.floatValue(), upperBound.floatValue());
	}
	default void beginShape(){ appletWrapper.value.beginShape(); }
	default void endShape(){ appletWrapper.value.endShape(); }
	default void vertex( float x, float y){ appletWrapper.value.vertex(x,y); }
}
