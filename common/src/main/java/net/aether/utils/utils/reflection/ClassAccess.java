package net.aether.utils.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public record ClassAccess<T>(
		Class<T> type,
		FieldAccess<T, ?>[] fields
) {
	
	public enum MethodType { GETTER, SETTER, GENERIC }
	
	public static <T> ClassAccess<T> forClass(Class<T> type) {
		return new ClassAccess<>(type, createFieldAccess(type));
	}
	
	private static <T> FieldAccess<T, ?>[] createFieldAccess(Class<T> type) {
		HashMap<String, FieldAccess.Builder<T, ?>> fields = new HashMap<>();
		
		Exposes exposes = Objects.requireNonNullElse(type.getAnnotation(Exposes.class), Exposes.NOTHING);
		List<String> exposedFields = Arrays.asList(exposes.value());
		boolean everything = exposedFields.contains(Exposes.EVERYTHING);
		// collect fields
		for (Field field : type.getFields()) {
			Exposed exposed = getExposed(field, everything, exposedFields);
			if (exposed.hidden()) continue;
			String fieldName = getFieldName(field, exposed);
			Class<?> fieldType = field.getType();
			
			fields.put(fieldName,
					FieldAccess.newBuilder(type, fieldType, fieldName)
					.setSetter((obj, val) -> { try { field.set(obj, val); } catch (Exception e) { throw new RuntimeException("Unable to set field", e); }})
					.setAutocastGetter((obj) -> { try { return field.get(obj); } catch (Exception e) { throw new RuntimeException("Unable to get field", e); }})
			);
			
		}
		// collect methods
		for (Method method : type.getMethods()) {
			Exposed exposed = getExposed(method);
			if (exposed.hidden()) continue;
			String fieldName = exposed.as();
			MethodType methodType = getMethodType(method);
			
			// if the method has a setter-like signature
			if (methodType == MethodType.SETTER)
				// get an existing field or create a new one
				fields.computeIfAbsent(fieldName, key -> FieldAccess.newBuilder(type, method.getParameterTypes()[0], fieldName))
						// and set the setter
						.setSetter((obj, val) -> { try { method.invoke(obj, val); } catch (Exception e) { throw new RuntimeException("Unable to invoke setter", e); }});
			// if the method has a getter-like signature
			if (methodType == MethodType.GETTER)
				// get an existing field or create a new one
				fields.computeIfAbsent(fieldName, key -> FieldAccess.newBuilder(type, method.getParameterTypes()[0], fieldName))
						// and set the getter
						.setAutocastGetter((obj) -> { try { return method.invoke(obj); } catch (Exception e) { throw new RuntimeException("Unable to invoke setter", e); }});
			
		}
		
		return fields.values().stream().map(FieldAccess.Builder::build).toArray(FieldAccess[]::new);
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
