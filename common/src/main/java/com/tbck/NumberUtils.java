package com.tbck;

public class NumberUtils {
	
	public static boolean isNumeric(String str) {
		if (str == null) return false;
		try { Double.parseDouble(str); }
		catch(NumberFormatException e) { return false; }
		return true;
	}
	
	public static boolean isNumericOrNull(String str) {
		if (str == null) return true;
		try { Double.parseDouble(str); }
		catch(NumberFormatException e) { return false; }
		return true;
	}
	
}
