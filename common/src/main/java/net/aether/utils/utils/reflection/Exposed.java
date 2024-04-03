package net.aether.utils.utils.reflection;

import java.lang.annotation.*;

/**
 * Fields annotated with Exposed will be picked up by {@link ClassAccess#forClass(Class)}.<br/>
 * Methods annotated with Exposed will be picked up by {@link ClassAccess#forClass(Class)} as getter or setter methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD })
public @interface Exposed {
	
	/** a static instance representing non-exposed members */
	Exposed HIDDEN = new Exposed() {
		public Class<? extends Annotation> annotationType() { return Exposed.class; }
		public boolean hidden() { return true; }
		public String as() { return ""; }
	};
	/** a static instance representing exposed members */
	Exposed VISIBLE = new Exposed() {
		public Class<? extends Annotation> annotationType() { return Exposed.class; }
		public boolean hidden() { return false; }
		public String as() { return ""; }
	};
	
	boolean hidden() default false;
	String as() default "";
	
}
