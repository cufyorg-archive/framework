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
import cufy.convert.ConvertArguments;
import cufy.convert.ConvertMethod;
import cufy.meta.MetaFamily;
import cufy.meta.MetaReference;

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
 * @version 0.1.2
 * @since 25-Jan-2020
 */
public class JSONConverter extends BaseConverter {
	/**
	 * The global instance to avoid unnecessary instancing.
	 */
	@MetaReference
	final public static JSONConverter global = new JSONConverter();

	/**
	 * Object => String
	 * <br/>
	 *
	 * Set the {@link ConvertArguments#output} with a new {@link String} that holds the value of the given {@link ConvertArguments#input}. Using
	 * {@link JSON}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' is null
	 * @throws IllegalArgumentException if the given 'outputClass' is not {@link String}
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = Object.class,
					in = {byte.class,
						  boolean.class,
						  char.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}),
			output = @MetaFamily(
					in = String.class
			))
	protected void object_string(ConvertArguments<Object, String> arguments) {
		try {
			if (DEBUGGING) {
				Objects.requireNonNull(arguments, "arguments");
			}

			arguments.output = JSON.global.format(arguments.input, new StringWriter(), arguments.inputClazz, arguments.outputClazz).toString();
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * String => Object
	 * <br/>
	 * Try to construct a new object of the value of the given {@link ConvertArguments#input} with type of the {@link ConvertArguments#outputClazz}.
	 * Using {@link JSON}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @throws NullPointerException     if the given 'arguments' or 'input' is null
	 * @throws IllegalArgumentException if the given 'input' is not a string. Or if the 'inputClass' is not String.class.
	 */
	@ConvertMethod(
			input = @MetaFamily(
					subIn = String.class
			),
			output = @MetaFamily(
					subIn = Object.class,
					in = {boolean.class,
						  byte.class,
						  char.class,
						  double.class,
						  float.class,
						  int.class,
						  long.class,
						  short.class
					}))
	protected void string_object(ConvertArguments<String, Object> arguments) {
		try {
			if (DEBUGGING) {
				Objects.requireNonNull(arguments, "arguments");
			}

			arguments.output = JSON.global.cparse(new StringReader(arguments.input), arguments.outputClazz);
		} catch (IOException e) {
			throw new IOError(e);
		}
	}
}
