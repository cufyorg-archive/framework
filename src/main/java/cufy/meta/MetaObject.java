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

import cufy.convert.BaseConverter;
import cufy.convert.ConvertArguments;
import cufy.convert.Converter;
import cufy.lang.Clazz;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * A recipe to construct a value. Given the {@link #value()} is the source value. And the {@link #converter()} to convert that value. And {@link
 * #type()} is the type of that value.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 21-Nov-2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaObject {
	/**
	 * The reference to the converter to be used to construct the value.
	 *
	 * @return the reference to the  converter to be used to construct the value
	 */
	MetaReference converter() default @MetaReference(type = BaseConverter.class);

	/**
	 * The clazz of the object.
	 *
	 * @return the clazz of the object
	 */
	MetaClazz type() default @MetaClazz(String.class);

	/**
	 * The source string of the value.
	 *
	 * @return the source string of teh value
	 */
	String value() default "";

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
		 * Construct a value from the given {@link MetaObject}.
		 *
		 * @param object the value constructing recipe
		 * @param <O>    the type of the returned value
		 * @return a value from the given value constructing recipe
		 * @throws NullPointerException if the given 'object' is null
		 * @throws IllegalMetaException if ANY throwable get thrown while constructing it
		 */
		public static <O> O get(MetaObject object) {
			Objects.requireNonNull(object, "object");

			Converter converter = MetaReference.util.get(object.converter());
			Clazz<O> type = MetaClazz.util.get(object.type());
			String value = object.value();

			return converter.convert(new ConvertArguments<>(value, type));
		}
	}
}
