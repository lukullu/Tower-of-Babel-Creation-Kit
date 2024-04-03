package net.aether.utils.utils.reflection;

import net.aether.utils.utils.data.Maybe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Represents a way to access a (possibly virtual) field.<br/>
 * While likely, it's not guaranteed for this to work with a "real" field.
 * @param fieldType the fields fieldType
 * @param setter a (possibly null) setter function (usually {@link java.lang.reflect.Field#set(Object, Object)})
 * @param getter a (possibly null) getter function (usually {@link java.lang.reflect.Field#get(Object)})
 * @param <T> the field fieldType, to remove the need for unchecked object casts
 */
public record FieldAccess<O, T>(
		Class<O> objectType,
		Class<T> fieldType,
		String name,
		BiConsumer<O, T> setter,
		Function<O, T> getter
) {
	
	public static final class Builder<O, T> {
		private final Class<O> objectType;
		private final Class<T> fieldType;
		private final String name;
		private BiConsumer<O, T> setter;
		private Function<O, T> getter;
		
		private Builder(Class<O> objectType, Class<T> fieldType, String name) {
			this.objectType = objectType;
			this.fieldType = fieldType;
			this.name = name;
		}
		public Builder<O, T> setSetter(BiConsumer<O, T> setter) { this.setter = setter; return this; }
		public Builder<O, T> setGetter(Function<O, T> getter) { this.getter = getter; return this; }
		/** puts a casting "layer" between your getter and the invocation. */
		public Builder<O, T> setAutocastGetter(Function<O, Object> getter) { this.getter = (obj) -> (T) getter.apply(obj); return this; }
		public FieldAccess<O, T> build() { return new FieldAccess<>(objectType, fieldType, name, setter, getter); }
	}
	public static <O, T> Builder<O, T> newBuilder(Class<O> objectType, Class<T> fieldType, String name) { return new Builder<>(objectType, fieldType, name); }
	
	public boolean hasGetter() { return getter != null; }
	public boolean hasSetter() { return setter != null; }
	
	/**
	 * Tries to update the value of the field.
	 * @apiNote a return value of true does not indicate that the value has changed in any way.<br/>
	 * It simply shows, that there was to problem executing the setter method.
	 * @param value the value to be set
	 * @return true if there was no problem setting the value.
	 */
	public boolean trySet(O object, T value) {
		if (!hasSetter()) return false;
		try {
			if (value == null) { setter.accept(object, null); return true; }
			// just to be extra safe
			if (! fieldType.isAssignableFrom(value.getClass())) return false;
			setter.accept(object, fieldType.cast(value));
			return true;
		} catch (Throwable ignore) {}
		return false;
	}
	/**
	 * Tries to get the value of the field.
	 * @return an optional containing the value which can be null
	 */
	public Maybe<T> tryGet(O object) {
		if (! hasGetter()) return Maybe.empty();
		try {
			Object value = getter.apply(object);
			if (value == null) return Maybe.ofNull();
			if (! fieldType.isAssignableFrom(value.getClass())) return Maybe.empty();
			return Maybe.of(fieldType.cast(value));
		} catch (Throwable ignore) {}
		return Maybe.empty();
	}
	
}
