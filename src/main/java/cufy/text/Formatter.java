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
import java.io.StringWriter;
import java.io.Writer;

/**
 * A class that can format objects and write it. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.3
 * @since 31-Mar-2020
 */
public interface Formatter {
	/**
	 * Format the given object.
	 *
	 * @param object to be formatted into text
	 * @param <T>    the type of the given object
	 * @return a text from the given object
	 * @throws FormatException when any formatting errors occurs
	 */
	default <T> String format(T object) {
		try {
			return this.format(new FormatToken(object, new StringWriter(), Clazz.ofi(object))).toString();
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Format the given object and write the text to the given writer.
	 *
	 * @param writer to write to
	 * @param object to be formatted
	 * @param <T>    the type of the given object
	 * @return the given writer
	 * @throws IOException          if any IO exception occurs
	 * @throws FormatException      when any formatting errors occurs
	 * @throws NullPointerException if the given 'writer' is null
	 */
	default <T> Writer format(T object, Writer writer) throws IOException {
		return this.format(new FormatToken<>(object, writer, Clazz.ofi(object)));
	}

	/**
	 * Write the text outputted from formatting {@link FormatToken#input} to the {@link FormatToken#output}.
	 *
	 * @param token the formatting instance that holds the variables of this formatting
	 * @param <T>   the type of the input
	 * @return the writer presented in the token
	 * @throws IOException          if any I/O exception occurs
	 * @throws FormatException      if any formatting exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 */
	<T> Writer format(FormatToken<T> token) throws IOException;
}
