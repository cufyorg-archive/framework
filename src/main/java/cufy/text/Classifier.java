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
 * A class that can classify sequences from a reader. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Mar-2020
 */
public interface Classifier {
	/**
	 * Get the clazz of the text given.
	 *
	 * @param text to be classified
	 * @param <T>  the type of the returned clazz
	 * @return the clazz of the given text
	 * @throws NullPointerException if the given 'text' is null
	 * @throws ClassifyException    if any classification exception occurs
	 */
	default <T> Clazz<T> classify(CharSequence text) {
		Objects.requireNonNull(text, "text");
		try {
			return this.classify(new ClassifyToken<>(new StringReader(text.toString()), null));
		} catch (IOException e) {
			throw new IOError(e);
		}
	}

	/**
	 * Get the clazz of the text on the given reader.
	 *
	 * @param reader to classify the text that is in it
	 * @param <T>    the type of the returned clazz
	 * @return the clazz of the text on the given reader
	 * @throws IOException          if any IO exception occurs
	 * @throws ClassifyException    if any classification exception occurs
	 * @throws NullPointerException if the given 'reader' is null
	 */
	default <T> Clazz<T> classify(Reader reader) throws IOException {
		Objects.requireNonNull(reader, "reader");
		return this.classify(new ClassifyToken<>(reader, null));
	}

	/**
	 * Set the {@link ClassifyToken#output} on the given token to the proper clazz for the text in the {@link ClassifyToken#input}.
	 *
	 * @param token the classifying instance that holds the variables of this classification
	 * @param <T>   the component-type of the clazz returned
	 * @return the proper clazz for the text in the given reader
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'token' is null
	 * @throws ClassifyException    if any classifying exception occurs
	 */
	<T> Clazz<T> classify(ClassifyToken<T> token) throws IOException;
}
