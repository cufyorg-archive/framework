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
 * A class that is a {@link Formatter} and {@link Parser} and {@link Classifier} at the same time.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 15-Nov-2019
 */
public interface Format extends Formatter, Parser, Classifier {
	/**
	 * Classify then parse the text read from the 'reader' to an object with the type of the 'outputClazz' and then return it.
	 *
	 * @param input       the input to read from
	 * @param output      the initial output instance
	 * @param outputClazz the clazz to be for the output
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O cparse(Reader input, O output, Clazz outputClazz) throws IOException {
		return this.parse(input, output, this.classify(input), outputClazz);
	}

	/**
	 * Classify then parse the text read from the 'reader' to an object with the type of the 'outputClazz' and then return it.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @param <O>    the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O cparse(Reader input, O output) throws IOException {
		return this.parse(input, output, this.classify(input));
	}

	/**
	 * Classify then parse the text read from the 'reader' to an object with the type of the 'outputClazz' and then return it.
	 *
	 * @param input       the input to read from
	 * @param outputClazz the clazz to be for the output
	 * @param <O>         the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O cparse(Reader input, Clazz outputClazz) throws IOException {
		return this.parse(input, this.classify(input), outputClazz);
	}

	/**
	 * Classify then parse the text read from the 'reader' to an object with the type of the 'outputClazz' and then return it.
	 *
	 * @param input the input to read from
	 * @param <O>   the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'outputClass' or 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <O> O cparse(Reader input) throws IOException {
		return this.parse(input, this.classify(input));
	}
}
