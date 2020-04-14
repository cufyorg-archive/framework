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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A classification instance that holds the variables of a classification.
 *
 * @param <T> the type of the output
 * @author lsafer
 * @version 0.1.3
 * @since 30-Mar-2020
 */
public class ClassifyToken<T> {
	/**
	 * A table of data to be copied from this token to it's sub-tokens.
	 */
	final public Map data;
	/**
	 * A table of data globally shared across this token and it's sub-tokens.
	 */
	final public Map data_global;
	/**
	 * The depth of this token form the first parent.
	 */
	final public int depth;
	/**
	 * The reader to read input from.
	 *
	 * @apiNote return it to it's original position after using it
	 */
	final public Reader input;
	/**
	 * The classifying token for the formatting that required initializing this token.
	 */
	final public ClassifyToken parent;
	/**
	 * The output of the classification. (could be changed several times!)
	 */
	public Clazz<T> output;

	/**
	 * Construct a new classifying token instance.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @throws NullPointerException if the given 'input' is null
	 */
	public ClassifyToken(Reader input, Clazz<T> output) {
		this(null, input, output);
	}

	/**
	 * Construct a new classifying token instance.
	 *
	 * @param parent the parent token
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @throws NullPointerException if the given 'input' is null
	 */
	protected ClassifyToken(ClassifyToken parent, Reader input, Clazz<T> output) {
		Objects.requireNonNull(input, "input");

		//NO RECURSION DETECTION

		this.parent = parent;
		this.input = input;
		this.output = output;

		if (parent == null) {
			this.depth = 0;
			this.data = new HashMap();
			this.data_global = new HashMap();
		} else {
			this.depth = parent.depth + 1;
			this.data = new HashMap(parent.data);
			this.data_global = parent.data_global;
		}
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 *
	 * @param input  the input to read from
	 * @param output the initial output instance
	 * @param <U>    the type of the output in the sub token
	 * @return a sub token of this token
	 * @throws NullPointerException if the given 'input' is null
	 */
	public <U> ClassifyToken<U> subToken(Reader input, Clazz<U> output) {
		return new ClassifyToken<>(this, input, output);
	}
}
