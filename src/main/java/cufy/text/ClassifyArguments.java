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

import java.io.Reader;
import java.util.Objects;

/**
 * A classification instance that holds the variables of a classification.
 *
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author lsafer
 * @version 0.1.2
 * @since 30-Mar-2020
 */
public class ClassifyArguments<I, O> {
	/**
	 * The reader to read input from.
	 *
	 * @apiNote return it to it's original position after using it
	 */
	final public Reader input;
	/**
	 * The output of the classification. (could be changed several times!)
	 */
	public Clazz<O> output;

	/**
	 * Construct a new classifying arguments instance.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @throws NullPointerException if the given 'input' is null
	 */
	public ClassifyArguments(Reader input, Clazz output) {
		Objects.requireNonNull(input, "input");
		this.input = input;
		this.output = output;
	}

	/**
	 * Construct a new classifying arguments instance.
	 *
	 * @param input the input to read from
	 * @throws NullPointerException if the given 'input' is null
	 */
	public ClassifyArguments(Reader input) {
		this(input, null);
	}
}
