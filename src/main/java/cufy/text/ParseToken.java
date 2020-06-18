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
 * A parsing instance that holds the variables of a parsing.
 *
 * @param <T> the type of the output
 * @author lsafer
 * @version 0.1.3
 * @since 30-Mar-2020
 */
public class ParseToken<T> {
	/**
	 * The data of THIS token.
	 */
	public final Map data = new HashMap();
	/**
	 * The depth of this token form the first parent.
	 */
	public final int depth;
	/**
	 * The reader to read the input from.
	 */
	public final Reader input;
	/**
	 * A table of data to be copied from this token to it is sub-tokens.
	 */
	public final Map linear;
	/**
	 * The parsing-token for the parsing that required initializing this token.
	 */
	public final ParseToken parent;
	/**
	 * A table of data globally shared across this token, and it is sub-tokens.
	 */
	public final Map tree;
	/**
	 * The class that the output should have.
	 */
	public Clazz<T> klazz;
	/**
	 * The output of the parsing. (could be changed several times!)
	 */
	public T output;

	/**
	 * Construct a new parsing token instance.
	 *
	 * @param input  the input to read from.
	 * @param output the initial output instance.
	 * @param klazz  the clazz to be for the output.
	 * @throws NullPointerException if the given {@code input} or {@code klazz} is null.
	 */
	public ParseToken(Reader input, T output, Clazz klazz) {
		Objects.requireNonNull(input, "input");
		Objects.requireNonNull(klazz, "klazz");
		this.parent = null;
		this.linear = new HashMap();
		this.tree = new HashMap();
		this.depth = 0;
		this.input = input;
		this.output = output;
		this.klazz = klazz;
	}

	/**
	 * Construct a new parsing token instance.
	 *
	 * @param parent the parent token.
	 * @param input  the input to read from.
	 * @param output the initial output instance.
	 * @param klazz  the clazz to be for the output.
	 * @throws NullPointerException if the given {@code parent} or {@code input} or {@code klazz} is null.
	 */
	protected ParseToken(ParseToken parent, Reader input, T output, Clazz klazz) {
		Objects.requireNonNull(parent, "parent");
		Objects.requireNonNull(input, "input");
		Objects.requireNonNull(klazz, "klazz");
		this.parent = parent;
		this.linear = new HashMap(parent.linear);
		this.tree = parent.tree;
		this.depth = parent.depth + 1;
		this.input = input;
		this.output = output;
		this.klazz = klazz;
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 *
	 * @param input  the input to read from.
	 * @param output the initial output instance.
	 * @param klazz  the clazz to be for the output.
	 * @param <U>    the type of the output in the sub token.
	 * @return a sub token of this token.
	 * @throws NullPointerException if the given {@code input} or {@code klazz} is null.
	 */
	public <U> ParseToken<U> subToken(Reader input, U output, Clazz<U> klazz) {
		return new ParseToken(this, input, output, klazz);
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 * <br>
	 * The details about the {@code klazz} of the returned token:
	 * <ul>
	 *     <li>
	 *         {@code klass}
	 *         <ul>
	 *             <li>Without any exception, {@code componentClazz}'s will be taken.</li>
	 *             <li>If {@code componentClazz} is assignable from {@code outputClazz}, then {@code outputClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code outputClazz} will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code outputClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code family}
	 *         <ul>
	 *             <li>Without any exception, {@code outputClazz}'s will be taken.</li>
	 *             <li>If only {@code outputClazz} is null, then {@code componentClazz} will be taken.</li>
	 *             <li>If both {@code outputClazz} and {@code componentClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code componentTree}
	 *         <ul>
	 *             <li>Without any exception, {@code componentClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code outputClazz}'s will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code outputClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 *
	 * @param input       the input to read from.
	 * @param output      the initial output instance.
	 * @param outputClazz the clazz of the given {@code output}.
	 * @param tree        the tree where the given {@code output} is located at its parent.
	 * @param key         the key of the given {@code output}.
	 * @param <U>         the type of the given {@code output}.
	 * @return a sub token of this token.
	 * @throws NullPointerException if the given {@code input} is null.
	 */
	public <U> ParseToken<U> subToken(Reader input, U output, Clazz outputClazz, int tree, Object key) {
		Objects.requireNonNull(input, "input");
		Clazz outputComponentClazz = this.klazz.getComponentClazz(tree, key);
		return this.subToken(input, output,
				//OUTPUT -------------------------------------------------------
				outputComponentClazz == null ?
				outputClazz == null ?
				//if both outputComponentClazz and outputClazz are null
				Clazz.of() :
				//if outputComponentClazz is null
				outputClazz :
				outputClazz != null ?
				outputComponentClazz.isAssignableFrom(outputClazz) ?
				//if outputComponentClazz is assignable from outputClazz
				Clazz.ofz(outputClazz, outputClazz, outputComponentClazz) :
				//if outputComponentClazz isn't assignable from outputClazz
				Clazz.ofz(outputComponentClazz, outputClazz, outputComponentClazz) :
				//if outputClazz is null
				outputComponentClazz
		);
	}
}
