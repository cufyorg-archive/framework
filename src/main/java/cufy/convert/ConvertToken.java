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
package cufy.convert;

import cufy.lang.Clazz;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A conversion instance that holds the variables of a conversion.
 * <p>
 * Combinations:
 * <ul>
 *     <li>
 *         To clone an object:
 *         <pre>
 * new ConvertToken(input, null, inputClazz, inputClazz)
 *         </pre>
 *     </li>
 *     <li>
 *         To apply a type to an object:
 *         <pre>
 * new ConvertToken(input, input, inputClazz, newClazz)
 *         </pre>
 *     </li>
 *     <li>
 *         To convert a subElement:
 *         <pre>
 * token.subToken(inputElement, outputElement, inputElementClazz, outputElement == null ? null : outputElementClazz, componentIndex)
 *         </pre>
 *     </li>
 * </ul>
 *
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author lsafer
 * @version 0.1.3
 * @since 30-Mar-2020
 */
public class ConvertToken<I, O> {
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
	public final I input;
	/**
	 * A table of data to be copied from this token to it is sub-tokens.
	 */
	public final Map linear;
	/**
	 * The convert-token for the conversion that required initializing this token.
	 */
	public final ConvertToken parent;
	/**
	 * A table of data globally shared across this token, and it is sub-tokens.
	 */
	public final Map tree;
	/**
	 * The class that the input do have.
	 */
	public Clazz<I> inputClazz;
	/**
	 * The output of the conversion. (could be changed several times!)
	 */
	public O output;
	/**
	 * The class that the output should have.
	 */
	public Clazz<O> outputClazz;

	/**
	 * Construct a new conversion token instance.
	 *
	 * @param input       the input instance.
	 * @param output      the initial output instance.
	 * @param inputClazz  the clazz of the input.
	 * @param outputClazz the clazz to be for the output.
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null.
	 */
	public ConvertToken(I input, O output, Clazz inputClazz, Clazz outputClazz) {
		Objects.requireNonNull(inputClazz, "inputClazz");
		Objects.requireNonNull(outputClazz, "outputClazz");

		this.parent = null;
		this.linear = new HashMap();
		this.tree = new HashMap();
		this.depth = 0;
		this.input = input;
		this.output = output;
		this.inputClazz = inputClazz;
		this.outputClazz = outputClazz;
	}

	/**
	 * Construct a new conversion token instance.
	 *
	 * @param parent      the parent converting-token.
	 * @param input       the input instance.
	 * @param output      the initial output instance.
	 * @param inputClazz  the clazz of the input.
	 * @param outputClazz the clazz to be for the output.
	 * @throws NullPointerException if the given 'parent' or 'inputClass' or 'outputClass' is null.
	 */
	protected ConvertToken(ConvertToken parent, I input, O output, Clazz inputClazz, Clazz outputClazz) {
		Objects.requireNonNull(parent, "parent");
		Objects.requireNonNull(inputClazz, "inputClazz");
		Objects.requireNonNull(outputClazz, "outputClazz");

		this.parent = parent;
		this.linear = new HashMap(parent.linear);
		this.tree = parent.tree;
		this.depth = parent.depth + 1;
		this.input = input;
		this.output = output;
		this.inputClazz = inputClazz;
		this.outputClazz = outputClazz;
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 *
	 * @param input       the input instance.
	 * @param output      the initial output instance.
	 * @param inputClazz  the clazz of the input.
	 * @param outputClazz the clazz to be for the output.
	 * @param <J>         the type of the input in the sub token.
	 * @param <Q>         the type of the output in the sub token.
	 * @return a sub token of this token.
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null.
	 */
	public <J, Q> ConvertToken<J, Q> subToken(J input, Q output, Clazz inputClazz, Clazz outputClazz) {
		return new ConvertToken<>(this, input, output, inputClazz, outputClazz);
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 * <p>
	 * The details about the {@code inputClazz} and {@code outputClazz} (mentioned as {@code elementClazz}) of the
	 * returned token:
	 * <ul>
	 *     <li>
	 *         {@code klass}
	 *         <ul>
	 *             <li>Without any exception, {@code componentClazz}'s will be taken.</li>
	 *             <li>If {@code componentClazz} is assignable from {@code elementClazz}, then {@code elementClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code elementClazz} will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code elementClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code family}
	 *         <ul>
	 *             <li>Without any exception, {@code elementClazz}'s will be taken.</li>
	 *             <li>If only {@code elementClazz} is null, then {@code componentClazz} will be taken.</li>
	 *             <li>If both {@code elementClazz} and {@code componentClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         {@code componentTree}
	 *         <ul>
	 *             <li>Without any exception, {@code componentClazz}'s will be taken.</li>
	 *             <li>If only {@code componentClazz} is null, then {@code elementClazz}'s will be taken.</li>
	 *             <li>If both {@code componentClazz} and {@code elementClazz} are null, then {@link Clazz#of(Map[]) Clazz.of()}'s will be taken.</li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 *
	 * @param input       the input instance.
	 * @param output      the initial output instance.
	 * @param inputClazz  the clazz of the given {@code input}.
	 * @param outputClazz the clazz of the given {@code output}.
	 * @param tree        the tree where the given {@code input} and {@code output} are located at their parent.
	 * @param key         the key of the given {@code input} and {@code output}.
	 * @param <V>         the type of the given {@code input}.
	 * @param <W>         the type of the given {@code output}.
	 * @return a sub token of this token.
	 */
	public <V, W> ConvertToken<V, W> subToken(V input, W output, Clazz inputClazz, Clazz outputClazz, int tree, Object key) {
		Clazz inputComponentClazz = this.inputClazz.getComponentClazz(tree, key);
		Clazz outputComponentClazz = this.outputClazz.getComponentClazz(tree, key);
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
				inputComponentClazz,
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
