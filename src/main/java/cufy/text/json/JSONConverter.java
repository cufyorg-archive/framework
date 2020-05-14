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
package cufy.text.json;

import cufy.convert.BaseConverter;
import cufy.convert.ConvertMethod;
import cufy.convert.ConvertToken;
import cufy.lang.Clazz;
import cufy.meta.Filter;
import cufy.meta.Where;
import cufy.text.ClassifyToken;
import cufy.text.FormatToken;
import cufy.text.ParseToken;

import java.io.IOError;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

/**
 * A converter that supports everything in the {@link BaseConverter}. This converter supports the conversion between {@link String} and other {@link
 * Object}s. Using {@link JSON}.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 25-Jan-2020
 */
public class JSONConverter extends BaseConverter {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@Where
	final public static JSONConverter global = new JSONConverter();

	/**
	 * Set the {@link ConvertToken#output} with a new {@link String} that holds the value of the given {@link ConvertToken#input}. Using
	 * {@link JSON}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'token' is null
	 * @throws IllegalArgumentException if the given 'outputClass' is not {@link String}
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = Object.class,
					include = {byte.class,
							   boolean.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}),
			output = @Filter(
					include = String.class
			))
	protected void objectToString(ConvertToken<Object, String> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		try {
			token.output = JSON.global.format(new FormatToken<>(
					token.input,
					new StringWriter(),
					token.inputClazz)
			).toString();
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Try to construct a new object of the value of the given {@link ConvertToken#input} with type of the {@link ConvertToken#outputClazz}.
	 * Using {@link JSON}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'token' or 'input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a string. Or if the 'inputClass' is not String.class.
	 */
	@ConvertMethod(
			input = @Filter(
					includeAll = String.class
			),
			output = @Filter(
					includeAll = Object.class,
					include = {boolean.class,
							   byte.class,
							   char.class,
							   double.class,
							   float.class,
							   int.class,
							   long.class,
							   short.class
					}))
	protected void stringToObject(ConvertToken<String, Object> token) {
		if (DEBUGGING) {
			Objects.requireNonNull(token, "token");
		}

		try {
			StringReader reader = new StringReader(token.input);
			token.output = JSON.global.parse(new ParseToken<>(
					reader,
					null,
					Clazz.ofz(JSON.global.classify(new ClassifyToken<>(reader, null)).getFamily(), token.outputClazz)
			));
		} catch (IOException e) {
			throw new IOError(e);
		}
	}
}
