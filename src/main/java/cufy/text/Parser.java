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
package cufy.text;

import cufy.lang.Clazz;

import java.io.IOException;
import java.io.Reader;

/**
 * A class that can parse a text from a reader to an object. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Mar-2020
 */
public interface Parser {
	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param input       the input to read from
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' or 'input' is null
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O parse(Reader input, O output, Clazz inputClazz, Clazz outputClazz) throws IOException {
		return this.parse(new ParseArguments<>(input, output, inputClazz, outputClazz));
	}

	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param input       the input to read from
	 * @param output      the initial output instance
	 * @param outputClazz the clazz to be for the output (also for inputClazz)
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O parse(Reader input, O output, Clazz outputClazz) throws IOException {
		return this.parse(new ParseArguments<>(input, output, outputClazz));
	}

	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance (source for inputClazz and outputClazz)
	 * @param <O>    the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O parse(Reader input, O output) throws IOException {
		return this.parse(new ParseArguments<>(input, output));
	}

	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param input       the input to read from
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O parse(Reader input, Clazz inputClazz, Clazz outputClazz) throws IOException {
		return this.parse(new ParseArguments<>(input, inputClazz, outputClazz));
	}

	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param input       the input to read from
	 * @param outputClazz the clazz to be for the output (also for inputClass)
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O parse(Reader input, Clazz outputClazz) throws IOException {
		return this.parse(new ParseArguments<>(input, outputClazz));
	}

	/**
	 * Parse the text read from the {@link ParseArguments#input} to an object with the type of {@link ParseArguments#outputClazz} and store it at
	 * {@link ParseArguments#output}.
	 *
	 * @param arguments the parsing instance that holds the variables of this parsing
	 * @param <O>       the type of the parsed object
	 * @return the parsed object
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'arguments' is null
	 * @throws ParseException       if any parsing exception occurs
	 */
	<O> O parse(ParseArguments<?, O> arguments) throws IOException;
}
