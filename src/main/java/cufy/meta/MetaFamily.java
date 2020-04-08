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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * Class ranging annotation. A way to specify a range of classes.
 *
 * <ul>
 *     The levels of Overriding:
 *     <li>{@link #out()}</li>
 *     <li>{@link #in()}</li>
 *     <li>{@link #value()}</li>
 *     <li>{@link #subOut()}</li>
 *     <li>{@link #subIn()}</li>
 * </ul>
 *
 * @author lsafer
 * @version 0.1.2
 * @since 21-Nov-2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaFamily {
	/**
	 * Classes in range (subclasses NOT included).
	 *
	 * @return absolute classes in range
	 * @apiNote this will override {@link #subOut()}
	 */
	Class<?>[] in() default {};

	/**
	 * Classes not in range (subclasses NOT included).
	 *
	 * @return absolute classes not in range
	 * @apiNote this will override {@link #in()}, {@link #subIn()}
	 */
	Class<?>[] out() default {};

	/**
	 * Classes in range (subclasses included).
	 *
	 * @return super classes in range
	 */
	Class<?>[] subIn() default {};

	/**
	 * Classes not in range (subclasses included).
	 *
	 * @return super classes not in range
	 * @apiNote this will override {@link #subIn()}
	 */
	Class<?>[] subOut() default {};

	/**
	 * Classes in range (subclasses NOT included).
	 *
	 * @return absolute classes in range
	 * @apiNote this will override {@link #subOut()}
	 */
	Class<?>[] value() default {};

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
		 * Check whether the given class is in the given family or not.
		 *
		 * @param family to check if the class is in
		 * @param klass  to be checked
		 * @return whether the given class is in the given family or not
		 * @throws NullPointerException if the given 'family' or 'klass' is null
		 */
		public static boolean test(MetaFamily family, Class klass) {
			Objects.requireNonNull(family, "family");
			Objects.requireNonNull(klass, "klass");

			for (Class<?> exclude : family.out())
				if (exclude == klass)
					return false;

			for (Class<?> include : family.in())
				if (include == klass)
					return true;

			for (Class<?> include : family.value())
				if (include == klass)
					return true;

			for (Class<?> exclude : family.subOut())
				if (exclude.isAssignableFrom(klass))
					return false;

			for (Class<?> include : family.subIn())
				if (include.isAssignableFrom(klass))
					return true;

			return false;
		}
	}
}
