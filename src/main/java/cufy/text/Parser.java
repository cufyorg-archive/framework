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

import java.io.IOError;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;

/**
 * A class that can parse a text from a reader to an object. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.3
 * @since 31-Mar-2020
 */
public interface Parser {
	/**
	 * Parse the given text to the given object.
	 *
	 * @param <T>    the type of the returned object
	 * @param text   to be parsed
	 * @param object to parse to
	 * @return the parsed object
	 * @throws ParseException       if any parsing exception occurs
	 * @throws NullPointerException if the given text is null
	 */
	default <T> T parse(CharSequence text, T object) {
		Objects.requireNonNull(text, "text");
		try {
			return this.parse(new ParseToken<>(new StringReader(text.toString()), object, Clazz.ofi(object)));
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Parse the text on the given reader to the given object.
	 *
	 * @param <T>    the type of the returned object
	 * @param reader to parse the text on it
	 * @param object to parse to
	 * @return the parsed object
	 * @throws ParseException       if any parsing exception occurs
	 * @throws NullPointerException if the given reader is null
	 * @throws IOException          if any IO exception occurs
	 */
	default <T> T parse(Reader reader, T object) throws IOException {
		Objects.requireNonNull(reader, "reader");
		return this.parse(new ParseToken<>(reader, object, Clazz.ofi(object)));
	}

	/**
	 * Parse the text read from the {@link ParseToken#input} to an object with the type of {@link ParseToken#klazz} and store it at
	 * {@link ParseToken#output}.
	 *
	 * @param token the parsing instance that holds the variables of this parsing
	 * @param <T>   the type of the parsed object
	 * @return the parsed object
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws ParseException       if any parsing exception occurs
	 */
	<T> T parse(ParseToken<T> token) throws IOException;
}
