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

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A formatting instance that holds the variables of a formatting.
 *
 * @param <T> the type of the input
 * @author lsafer
 * @version 0.1.3
 * @since 30-Mar-2020
 */
public class FormatToken<T> {
	/**
	 * The data of THIS token.
	 */
	public final Map data = new HashMap();
	/**
	 * The depth of this token form the first parent.
	 */
	public final int depth;
	/**
	 * The input object.
	 */
	public final T input;
	/**
	 * A table of data to be copied from this token to it is sub-tokens.
	 */
	public final Map linear;
	/**
	 * The writer to write the output to.
	 */
	public final Writer output;
	/**
	 * The formatting token for the formatting that required initializing this token.
	 */
	public final FormatToken parent;
	/**
	 * A table of data globally shared across this token, and it is sub-tokens.
	 */
	public final Map tree;
	/**
	 * The class that the input do have.
	 */
	public Clazz<T> klazz;

	/**
	 * Construct a new formatting token.
	 *
	 * @param input  the input instance.
	 * @param output the output to write to.
	 * @param klazz  the clazz of the input.
	 * @throws NullPointerException if the given 'klazz' or 'output' is null.
	 */
	public FormatToken(T input, Writer output, Clazz klazz) {
		Objects.requireNonNull(output, "output");

		this.parent = null;
		this.linear = new HashMap();
		this.tree = new HashMap();
		this.depth = 0;
		this.input = input;
		this.output = output;
		this.klazz = klazz;
	}

	/**
	 * Construct a new formatting token.
	 *
	 * @param parent the parent token.
	 * @param input  the input instance.
	 * @param output the output to write to.
	 * @param klazz  the clazz of the input.
	 * @throws NullPointerException if the given 'parent' or 'output' or 'klazz' is null.
	 */
	public FormatToken(FormatToken parent, T input, Writer output, Clazz klazz) {
		Objects.requireNonNull(parent, "parent");
		Objects.requireNonNull(output, "output");
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
	 * @param input  the input instance.
	 * @param output the output to write to.
	 * @param klazz  the clazz of the input.
	 * @param <U>    the type of the input in the sub token.
	 * @return a sub token of this token.
	 * @throws NullPointerException if the given 'klazz' or 'output' is null.
	 */
	public <U> FormatToken<U> subToken(U input, Writer output, Clazz klazz) {
		return new FormatToken<>(this, input, output, klazz);
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
	 *             <li>If {@code componentClazz} is assignable from {@code inputClazz}, then {@code inputClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code inputClazz} will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code inputClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code family}
	 *         <ul>
	 *             <li>Without any exception, {@code inputClazz}'s will be taken.</li>
	 *             <li>If only {@code inputClazz} is null, then {@code componentClazz} will be taken.</li>
	 *             <li>If both {@code inputClazz} and {@code componentClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code componentTree}
	 *         <ul>
	 *             <li>Without any exception, {@code componentClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code inputClazz}'s will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code inputClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 *
	 * @param input      the input instance.
	 * @param output     the output to write to.
	 * @param inputClazz the clazz of the given {@code input}.
	 * @param tree       the tree where the given {@code input} is located at its parent.
	 * @param key        the key of the given {@code input}.
	 * @param <U>        the type of the given {@code input}.
	 * @return a sub token of this token.
	 * @throws NullPointerException if the given {@code output} is null.
	 */
	public <U> FormatToken<U> subToken(U input, Writer output, Clazz inputClazz, int tree, Object key) {
		Objects.requireNonNull(output, "output");
		Clazz inputComponentClazz = this.klazz.getComponentClazz(tree, key);
		return this.subToken(input, output,
				//INPUT --------------------------------------------------------
				inputComponentClazz == null ?
				inputClazz == null ?
				//if both inputComponentClazz and inputClazz are null
				Clazz.of() :
				//if inputComponentClazz is null
				inputClazz :
				inputClazz != null ?
				inputComponentClazz.isAssignableFrom(inputClazz) ?
				//if inputComponentClazz is assignable from inputClazz
				Clazz.ofz(inputClazz, inputClazz, inputComponentClazz) :
				//if inputComponentClazz isn't assignable from inputClazz
				Clazz.ofz(inputComponentClazz, inputClazz, inputComponentClazz) :
				//if inputClazz is null
				inputComponentClazz
		);
	}
}
