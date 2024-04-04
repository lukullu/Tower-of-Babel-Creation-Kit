package net.aether.utils.utils.data;

import java.util.Objects;
import java.util.Optional;

/**
 * Similar to {@link Optional} but allows for both "empty" state and a "filled" state with null
 * @author Kilix
 */
public record Maybe<T>(T value, boolean isEmpty) {
	
	/** lossy conversion to an {@link Optional} */
	public Optional<T> toOptional() { return Optional.ofNullable(value); }
	
	/** has a value */
	public static <E> Maybe<E> of(E value) { return new Maybe<>(value, false); }
	/** has an explicitly non-null value */
	public static <E> Maybe<E> ofNotNull(E value) { return new Maybe<>(Objects.requireNonNull(value, "invoked Maybe#ofNotNull with null value"), false); }
	/** has an explicitly non-null value, if value is null fallback will be used */
	public static <E> Maybe<E> ofNotNullElse(E value, E fallback) { return new Maybe<>(Objects.requireNonNullElse(value, Objects.requireNonNull(fallback, "invoked Maybe#ofNotNullElse with null fallback.")), false); }
	/** has explicitly null as a value */
	public static <E> Maybe<E> ofNull() { return new Maybe<>(null, false); }
	/** does not have a value */
	public static <E> Maybe<E> empty() { return new Maybe<>(null, true); }
	
}
