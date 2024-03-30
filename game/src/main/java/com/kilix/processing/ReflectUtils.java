package com.kilix.processing;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtils {
	
	public static <ReturnType> ReturnType callMethod(String methodName, Object obj, Class<ReturnType> expectedReturnType, Object... args) throws Exception {
		Class<?> _expectedReturnType = expectedReturnType != null ? expectedReturnType : Void.class;
		
		assert methodName != null && ! methodName.isBlank();
		assert obj != null;
		
		Class<?>[] types = Arrays
				.stream(args)
				.map(Object::getClass)
				.toList().toArray(new Class<?>[0]);
		
		List<Method> validMethods = findMethodsRecursively(methodName,obj.getClass(), types);
		if (validMethods.size() <= 0) throw new NoSuchMethodException("Unable to find method on object of type " + obj.getClass().getSimpleName());
		Method method = validMethods.get(0); // we want the first, because we always want the overridden version
		
		method.setAccessible(true);
		
		Object result = method.invoke(obj, args);
		
		return result != null ? expectedReturnType.cast(result) : null;
	}
	
	public static List<Method> findMethodsRecursively(String methodName, Class<?> clazz, Class<?>... paramTypes) {
		assert methodName != null && ! methodName.isBlank();
		assert clazz != null;
		
		List<Method> out = new ArrayList<>();
		
		for (Method m : clazz.getDeclaredMethods()) {
			if (methodName.equals(m.getName())) {
				if (m.getParameterTypes().length != paramTypes.length) continue;
				boolean acceptable = true;
				
				int i = 0;
				for (Class<?> type : m.getParameterTypes())
					acceptable = acceptable && type.isAssignableFrom(paramTypes[i++]);
				
				if (acceptable) out.add(m);
			}
		}
		
		if (clazz.getSuperclass() != null)
			out.addAll(findMethodsRecursively(methodName, clazz.getSuperclass(),paramTypes));
		
		return out;
	}
	
	public static List<Field> findFieldsRecursively(String fieldName, Class<?> clazz, Class<?> fieldType) {
		assert fieldName != null && ! fieldName.isBlank();
		assert clazz != null;
		assert fieldType != null;
		
		List<Field> out = new ArrayList<>();
		
		for (Field m : clazz.getDeclaredFields()) {
			if (fieldName.equals(m.getName()) && isAssignableFrom(m.getType(), fieldType)) {
				out.add(m);
			}
		}
		
		if (clazz.getSuperclass() != null)
			out.addAll(findFieldsRecursively(fieldName, clazz.getSuperclass(), fieldType));
		
		return out;
	}
	
	public static void setField(String fieldName, Object obj, Object newValue) throws Exception {
		assert fieldName != null && ! fieldName.isBlank();
		
		List<Field> fields = findFieldsRecursively(fieldName, obj.getClass(), newValue.getClass());
		if (fields.size() == 0) throw new NoSuchFieldException();
		
		Field field = fields.get(0);
		field.setAccessible(true);
		
		field.set(obj, field.getType().isPrimitive() ? getPrimitive(newValue) : newValue);
	}
	
	public static boolean isAssignableFrom(Class<?> a, Class<?> b) {
		if (a.isPrimitive()) {
			return          (a == int.class     && b == Integer.class)   ||
							(a == double.class  && b == Double.class)    ||
							(a == long.class    && b == Long.class)      ||
							(a == float.class   && b == Float.class)     ||
							(a == char.class    && b == Character.class) ||
							(a == byte.class    && b == Byte.class)      ||
							(a == short.class   && b == Short.class);
		} else return a.isAssignableFrom(b);
	}
	
	public static int getPrimitive(Integer val)     { return val; }
	public static double getPrimitive(Double val)   { return val; }
	public static long getPrimitive(Long val)       { return val; }
	public static float getPrimitive(Float val)     { return val; }
	public static char getPrimitive(Character val)  { return val; }
	public static byte getPrimitive(Byte val)       { return val; }
	public static short getPrimitive(Short val)     { return val; }
	
	public static Object getPrimitive(Object val) {
		if (val.getClass().isPrimitive()) return val;
		
		if (val instanceof Integer i)   return (int) i;
		if (val instanceof Double d)    return (double) d;
		if (val instanceof Long l)      return (long) l;
		if (val instanceof Float f)     return (float) f;
		if (val instanceof Character c) return (char) c;
		if (val instanceof Byte b)      return (byte) b;
		if (val instanceof Short s)     return (short) s;
		
		return val;
	}
	
}
