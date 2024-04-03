package net.aether.utils.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record ClassAccess<T>(
		Class<T> type,
		FieldAccess<T, ?>[] fields
) {
	
	public enum MethodType { GETTER, SETTER, GENERIC }
	private record Getter(Class<?> returnType, Supplier<?> getter) {}
	private record Setter(Class<?> paramType, Consumer<?> setter) {}
	
	private static <T> FieldAccess<T, ?>[] createAccess(Class<T> type) {
		HashMap<String, Getter> getters = new HashMap<>();
		HashMap<String, Setter> setters = new HashMap<>();
		
		Exposes exposes = Objects.requireNonNullElse(type.getAnnotation(Exposes.class), Exposes.NOTHING);
		List<String> exposedFields = Arrays.asList(exposes.value());
		boolean everything = exposedFields.contains(Exposes.EVERYTHING);
		// collect fields
		for (Field field : type.getFields()) {
			Exposed exposed = getExposed(field, everything, exposedFields);
			if (exposed.hidden()) continue;
			String fieldName = getFieldName(field, exposed);
			Class<?> fieldType = field.getType();
			
			
			
			
		}
		// collect methods
		for (Method method : type.getMethods()) {
			Exposed exposed = getExposed(method);
			if (exposed.hidden()) continue;
			String fieldName = exposed.as();
			MethodType methodType = getMethodType(method);
			
			
			
		}
		
		
		List<FieldAccess<T, ?>> fieldAccessList = new ArrayList<>();
		return fieldAccessList.toArray(FieldAccess[]::new);
	}
	
	private static Exposed getExposed(Field field, boolean everything, List<String> exposedFields) {
		if (field.isAnnotationPresent(Exposed.class)) return field.getAnnotation(Exposed.class);
		if (everything) return Exposed.VISIBLE;
		if (exposedFields.contains(field.getName())) return Exposed.VISIBLE;
		return Exposed.HIDDEN;
	}
	
	private static Exposed getExposed(Method method) {
		if (method.isAnnotationPresent(Exposed.class)) {
			Exposed exposed = method.getAnnotation(Exposed.class);
			if (exposed.as() == null || exposed.as().isBlank()) return Exposed.HIDDEN;
			return exposed;
		}
		return Exposed.HIDDEN;
	}
	
	private static String getFieldName(Field field, Exposed exposed) {
		if (exposed.as() != null && !exposed.as().isBlank()) return exposed.as();
		return field.getName();
	}
	
	public static MethodType getMethodType(Method method) {
		if (method.getParameterCount() == 1) {
			return method.getReturnType() == Void.class
					? MethodType.SETTER
					: MethodType.GENERIC;
		}
		if (method.getParameterCount() == 0) {
			return method.getReturnType() == Void.class
					? MethodType.GENERIC
					: MethodType.GETTER;
		}
		return MethodType.GENERIC;
	}
	
}
