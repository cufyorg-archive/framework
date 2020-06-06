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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * A reference to a static field with a specific type. That field should have {@link Where} annotated to it.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 31-Mar-2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Where.Array.class)
public @interface Where {
	/**
	 * The id of that field.
	 *
	 * @return the id of that field
	 */
	String id() default "";
	/**
	 * The class that the targeted field is at.
	 *
	 * @return the class that the targeted field is at
	 */
	Class value() default Util.class;

	/**
	 * Array of meta-reference.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@interface Array {
		/**
		 * The array of the meta-references.
		 *
		 * @return the array of meta-references
		 */
		Where[] value();
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
				if (field.isAnnotationPresent(Array.class))
					for (Where where1 : field.getAnnotation(Array.class).value()) {
						if (where1.id().equals(where.id()))
							return field;
					}
				else if (field.isAnnotationPresent(Where.class) &&
						 field.getAnnotation(Where.class).id().equals(where.id()))
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
