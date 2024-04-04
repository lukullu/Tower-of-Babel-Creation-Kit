package net.aether.utils.utils.reflection;

import java.lang.reflect.Method;

public enum MethodType {
	/** () -> object */
	GETTER,
	/** object -> void */
	SETTER,
	/** () -> void */
	ACTION,
	/**
	 * Anything that is not one of:
	 * <ul>
	 *     <li>{@link MethodType#GETTER}</li>
	 *     <li>{@link MethodType#SETTER}</li>
	 *     <li>{@link MethodType#ACTION}</li>
	 * </ul>
	 */
	GENERIC;
	
	/** Detects the type of method */
	public static MethodType getMethodType(Method method) {
		MethodType out = switch (method.getParameterCount()) {
			case 0 -> method.getReturnType() == void.class ? ACTION : GETTER;
			case 1 -> method.getReturnType() == void.class ? SETTER : GENERIC;
			default -> GENERIC;
		};
		return out;
	}
}
