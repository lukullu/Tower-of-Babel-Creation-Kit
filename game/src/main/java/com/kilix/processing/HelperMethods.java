package com.kilix.processing;

import processing.core.PConstants;

import java.util.Arrays;

public interface HelperMethods extends PConstants {

	default float radians(float degrees) { return DEG_TO_RAD * degrees; }
	static float degrees(float radians) { return RAD_TO_DEG * radians; }
	default void print(Object o)         { System.out.print(o); }
	default void println(Object o)       { System.out.println(o); }
	default <T> void printArray(T[] array) { printArray(array, false); }
	default <T> void printArray(T[] array, boolean processingStyle) {
		if (! processingStyle) System.out.println(Arrays.asList(array));
		for (int i = 0; i < array.length; i++) System.out.println(array[i]);
	}

}
