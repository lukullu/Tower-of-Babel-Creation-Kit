package net.aether.utils.utils.reflection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exposes {
	
	String EVERYTHING = "*";
	
	Exposes NOTHING = new Exposes() {
		private final String[] value = new String[0];
		public Class<? extends Annotation> annotationType() { return Exposes.class; }
		public String[] value() { return value; }
	};
	
	String[] value();

}
