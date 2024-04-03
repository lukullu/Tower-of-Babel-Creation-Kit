package net.aether.utils.utils.data;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * @param value
 * @param isEmpty
 * @param <T>
 * @author Kilix
 */
public record Maybe<T>(T value, boolean isEmpty) {
	
	/** lossy conversion to an {@link Optional} */
	public Optional<T> toOptional() { return Optional.ofNullable(value); }
	
	/** has a value */
	public static <E> Maybe<E> of(E t) { return new Maybe<>(t, false); }
	/** has an explicitly non-null value */
	public static <E> Maybe<E> ofNotNull(E t) { return new Maybe<>(Objects.requireNonNull(t, "invoked Maybe#ofNotNull with null value"), false); }
	/** has explicitly null as a value */
	public static <E> Maybe<E> ofNull() { return new Maybe<>(null, false); }
	/** does not have a value */
	public static <E> Maybe<E> empty() { return new Maybe<>(null, true); }
	
}
