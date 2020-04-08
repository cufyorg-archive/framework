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
 * A class that can classify sequences from a reader. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Mar-2020
 */
public interface Classifier {
	/**
	 * Return the proper clazz for the text in the given 'input'.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @param <O>    the component-type of the clazz returned
	 * @return the proper clazz for the text in the given reader
	 * @throws NullPointerException if the given 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ClassifyException    if any classifying exception occurs
	 */
	default <O> Clazz<O> classify(Reader input, Clazz output) throws IOException {
		return this.classify(new ClassifyArguments<>(input, output));
	}

	/**
	 * Return the proper clazz for the text in the given 'input'.
	 *
	 * @param input the input to read from
	 * @param <O>   the component-type of the clazz returned
	 * @return the proper clazz for the text in the given reader
	 * @throws NullPointerException if the given 'input' is null
	 * @throws IOException          if any I/O exception occurs
	 * @throws ClassifyException    if any classifying exception occurs
	 */
	default <O> Clazz<O> classify(Reader input) throws IOException {
		return this.classify(new ClassifyArguments<>(input));
	}

	/**
	 * Set the {@link ClassifyArguments#output} on the given arguments to the proper clazz for the text in the {@link ClassifyArguments#input}.
	 *
	 * @param arguments the classifying instance that holds the variables of this classification
	 * @param <O>       the component-type of the clazz returned
	 * @return the proper clazz for the text in the given reader
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given 'arguments' is null
	 * @throws ClassifyException    if any classifying exception occurs
	 */
	<O> Clazz<O> classify(ClassifyArguments<?, O> arguments) throws IOException;
}
