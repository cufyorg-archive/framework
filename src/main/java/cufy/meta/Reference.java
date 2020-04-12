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
 * A reference to a static field with a specific type. That field should have {@link Reference} annotated to it.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Mar-2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Reference.Array.class)
public @interface Reference {
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
	Class value() default util.class;

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
		Reference[] value();
	}

	/**
	 * Utilities for this annotation. Since static methods are illegal in annotations.
	 */
	final class util {
		/**
		 * This is a util class. And shall not be instanced as an object.
		 *
		 * @throws AssertionError when called
		 */
		private util() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * Get the field represented by the given fieldm.
		 *
		 * @param reference to get the field of
		 * @return the field represented by the given fieldm
		 */
		public static Field get(Reference reference) {
			Objects.requireNonNull(reference, "reference");

			for (Field field : reference.value().getDeclaredFields())
				if (field.isAnnotationPresent(Array.class))
					for (Reference reference1 : field.getAnnotation(Array.class).value()) {
						if (reference1.id().equals(reference.id()))
							return field;
					}
				else if (field.isAnnotationPresent(Reference.class))
					if (field.getAnnotation(Reference.class).id().equals(reference.id()))
						return field;

			throw new IllegalMetaException("No such field at " + reference);
		}

		/**
		 * Get the static value stored at the reference given.
		 *
		 * @param reference where the value stored
		 * @param <O>       the type of the returned value
		 * @return the value stored at the given reference
		 */
		public static <O> O getValue(Reference reference) {
			try {
				return (O) get(reference).get(null);
			} catch (IllegalAccessException e) {
				throw new IllegalMetaException(e);
			}
		}

		/**
		 * Get the value stored at the reference given in the given instance.
		 *
		 * @param reference where the value stored
		 * @param instance  to get the value from
		 * @param <O>       the type of the returned value
		 * @return the value stored at the given reference in the given instance
		 */
		public static <O> O getValue(Reference reference, Object instance) {
			try {
				return (O) get(reference).get(instance);
			} catch (IllegalAccessException e) {
				throw new IllegalMetaException(e);
			}
		}
	}
}
