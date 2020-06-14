/*
 *	Copyright 2020 Cufy
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package cufy.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * A reference to a static field with a specific type. That field should have {@link Target} annotated to it.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 31-Mar-2020
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
	/**
	 * The id of that field.
	 *
	 * @return the id of that field
	 */
	String name() default "";
	/**
	 * The class that the targeted field is at.
	 *
	 * @return the class that the targeted field is at
	 */
	Class value();

	/**
	 * A flag makes a field be a target for the annotation {@link Where}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@java.lang.annotation.Target(ElementType.FIELD)
	@Repeatable(Target.Array.class)
	@interface Target {
		/**
		 * The id of the field.
		 *
		 * @return the id of the field
		 */
		String name() default "";

		/**
		 * Array of meta-reference.
		 */
		@java.lang.annotation.Target(ElementType.FIELD)
		@Retention(RetentionPolicy.RUNTIME)
		@interface Array {
			/**
			 * The array of the meta-references.
			 *
			 * @return the array of meta-references
			 */
			Target[] value();
		}
	}

	/**
	 * Utilities for this annotation. Since static methods are illegal in annotations.
	 */
	final class Util {
		/**
		 * This is a util class. And shall not be instanced as an object.
		 *
		 * @throws AssertionError when called
		 */
		private Util() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * Get the field represented by the given where.
		 *
		 * @param where to get the field of
		 * @return the field represented by the given where
		 */
		public static Field get(Where where) {
			Objects.requireNonNull(where, "reference");

			for (Field field : where.value().getDeclaredFields())
				if (field.isAnnotationPresent(Where.Target.Array.class))
					for (Where.Target target : field.getAnnotation(Where.Target.Array.class).value()) {
						if (target.name().equals(where.name()))
							return field;
					}
				else if (field.isAnnotationPresent(Where.Target.class) &&
						 field.getAnnotation(Where.Target.class).name().equals(where.name()))
					return field;

			throw new IllegalMetaException("No such field at " + where);
		}

		/**
		 * Get the static value stored at the reference given.
		 *
		 * @param where where the value stored
		 * @param <O>   the type of the returned value
		 * @return the value stored at the given reference
		 */
		public static <O> O getValue(Where where) {
			try {
				return (O) get(where).get(null);
			} catch (IllegalAccessException e) {
				throw new IllegalMetaException(e);
			}
		}

		/**
		 * Get the value stored at the reference given in the given instance.
		 *
		 * @param where    where the value stored
		 * @param instance to get the value from
		 * @param <O>      the type of the returned value
		 * @return the value stored at the given reference in the given instance
		 */
		public static <O> O getValue(Where where, Object instance) {
			try {
				return (O) get(where).get(instance);
			} catch (IllegalAccessException e) {
				throw new IllegalMetaException(e);
			}
		}
	}
}
