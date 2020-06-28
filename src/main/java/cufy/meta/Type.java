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

import cufy.lang.Clazz;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * A recipe to pass information about a clazz in the annotations' environment.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.03.31
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Type {
	/**
	 * The {@code componentClasses} of the trees of this clazz (in order).
	 *
	 * @return the {@code componentClazz} of this clazz.
	 */
	Class[] components() default {};

	/**
	 * The family of this clazz. How this clazz should be treated.
	 *
	 * @return the family of this clazz. How this clazz should be treated.
	 * @throws IllegalMetaException if the array's length is more than 1
	 * @see Clazz#family
	 */
	Class[] family() default {};

	/**
	 * The class represented by this clazz.
	 *
	 * @return the class represented by this clazz.
	 * @see cufy.lang.Clazz#klass
	 */
	Class value();

	/**
	 * Utilities for this annotation. Since static methods are illegal in annotations.
	 */
	final class Util {
		/**
		 * This is an util class and must not be instanced as an object.
		 *
		 * @throws AssertionError when called.
		 */
		private Util() {
			throw new AssertionError("No instance for you!");
		}

		/**
		 * Get a clazz from the given meta-clazz.
		 *
		 * @param type the meta-clazz to get a clazz from.
		 * @param <T>  the component-type of the returned clazz.
		 * @return a clazz represents the same class that the given meta-clazz is representing.
		 * @throws NullPointerException if the given {@code type} is null.
		 */
		public static <T> Clazz<T> get(Type type) {
			Objects.requireNonNull(type, "type");

			Class<T> klass = type.value();
			Class[] family = type.family();
			Class[] components = type.components();

			if (family.length > 1)
				throw new IllegalMetaException("Type.family().length > 1");

			return Clazz.as(
					klass,
					family.length == 0 ? klass : family[0],
					components
			);
		}
	}
}
