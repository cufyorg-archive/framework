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

import java.io.IOError;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;

/**
 * A class that is a {@link Formatter} and {@link Parser} and {@link Classifier} at the same time.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 15-Nov-2019
 */
public interface Format extends Formatter, Parser, Classifier {
	/**
	 * Parse the given text to an object.
	 *
	 * @param text to be parsed
	 * @param <T>  the type of the returned object
	 * @return an object parsed from the given text
	 * @throws NullPointerException if the given 'text' is null
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws ParseException       when any parsing exception occurs
	 */
	default <T> T cparse(CharSequence text) {
		try {
			Objects.requireNonNull(text, "text");
			Reader reader = new StringReader(text.toString());
			return this.parse(new ParseToken<>(reader, null, this.classify(new ClassifyToken<>(reader, null))));
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Parse the given text to the given object.
	 *
	 * @param text   to be parsed
	 * @param object the object to parse to
	 * @param <T>    the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'text' is null
	 * @throws ClassifyException    when any classification exception occurs
	 * @throws ParseException       when any parsing exception occurs
	 */
	default <T> T cparse(CharSequence text, T object) {
		try {
			Objects.requireNonNull(text, "text");
			Reader reader = new StringReader(text.toString());
			return this.parse(new ParseToken<>(reader, object, this.classify(new ClassifyToken<>(reader, null))));
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Classify then parse the text read from the 'reader' to an object with the type of the 'outputClazz' and then return it.
	 *
	 * @param reader the reader to read from
	 * @param <T>    the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'reader' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <T> T cparse(Reader reader) throws IOException {
		return this.parse(new ParseToken<>(reader, null, this.classify(new ClassifyToken<>(reader, null))));
	}

	/**
	 * Classify then parse the text read from the 'reader' to the given object and then return it.
	 *
	 * @param reader the reader to read from
	 * @param object the object to parse to
	 * @param <T>    the type of the parsed object
	 * @return the parsed object
	 * @throws NullPointerException if the given 'reader' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ParseException       if any parsing exception occurs
	 */
	default <T> T cparse(Reader reader, T object) throws IOException {
		return this.parse(new ParseToken<>(reader, object, this.classify(new ClassifyToken<>(reader, null))));
	}
}
