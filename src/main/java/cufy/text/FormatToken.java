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
import cufy.lang.Recurse;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A formatting instance that holds the variables of a formatting.
 *
 * @param <T> the type of the input
 * @author lsafer
 * @version 0.1.2
 * @since 30-Mar-2020
 */
public class FormatToken<T> {
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
	 * The input object.
	 */
	final public T input;
	/**
	 * The class that the input do have.
	 */
	final public Clazz<T> klazz;
	/**
	 * The writer to write the output to.
	 */
	final public Writer output;
	/**
	 * The formatting token for the formatting that required initializing this token.
	 */
	final public FormatToken parent;

	/**
	 * Construct a new formatting token.
	 *
	 * @param input  the input instance
	 * @param output the output to write to
	 * @param klazz  the clazz of the input
	 * @throws NullPointerException if the given 'klazz' or 'output' is null
	 */
	public FormatToken(T input, Writer output, Clazz klazz) {
		this(null, input, output, klazz);
	}

	/**
	 * Construct a new formatting token.
	 *
	 * @param parent the parent token
	 * @param input  the input instance
	 * @param output the output to write to
	 * @param klazz  the clazz of the input
	 * @throws NullPointerException if the given 'klazz' or 'output' is null
	 */
	protected FormatToken(FormatToken parent, T input, Writer output, Clazz klazz) {
		Objects.requireNonNull(output, "output");
		Objects.requireNonNull(klazz, "klazz");

		//recurse, depth detection
		for (FormatToken grand = parent; grand != null; grand = grand.parent)
			if (grand.input == input) {
				klazz = Clazz.ofz(Recurse.class, klazz);
				break;
			}

		this.parent = parent;
		this.input = input;
		this.output = output;
		this.klazz = klazz;

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
	 * @param input  the input instance
	 * @param output the output to write to
	 * @param klazz  the clazz of the input
	 * @param <U>    the type of the input in the sub token
	 * @return a sub token of this token
	 * @throws NullPointerException if the given 'klazz' or 'output' is null
	 */
	public <U> FormatToken<U> subToken(U input, Writer output, Clazz klazz) {
		return new FormatToken<>(this, input, output, klazz);
	}

	/**
	 * Get a sub token of this token with the given parameters.
	 * <br/>
	 * <ul>
	 *     The rules
	 *     <li>
	 *         subToken.klazz.klass
	 *         <ul>
	 *             <li>Without any exception, `klazz.klass` will be taken.</li>
	 *             <li>If `subClazz.klass` is assignable from `klazz.klass`, then `subClazz.klass` will be taken.</li>
	 *             <li>If `klazz` is null, then `subClazz.klass` will be taken.</li>
	 *             <li>If both `klazz` and `subClazz` are null, then `Object.class` will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         subToken.klazz.family
	 *         <ul>
	 *             <li>Without any exception, `subClazz.family` will be taken.</li>
	 *             <li>If `subClazz` is null, then `klazz.family` will be taken.</li>
	 *             <li>If both `subClazz` and `klazz` are null, then `Object.class` will be taken.</li>
	 *         </ul>
	 *     </li>
	 *     <li>
	 *         subToken.klazz.componentTypes
	 *         <ul>
	 *             <li>Without any exception, `klazz.componentTypes` will be taken.</li>
	 *             <li>If `klazz` is null, then `subClazz.componentTypes` will be taken.</li>
	 *             <li>If both `klazz` and `subClazz` are null, then no componentTypes will be taken.</li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 *
	 * @param input     the input instance
	 * @param output    the output to write to
	 * @param subClazz  the flavor clazz (see the rules above for more details)
	 * @param component the component index to get from the parent's clazzes to the clazzes of this
	 * @param <U>       the type of the input in the sub token
	 * @return a sub token of this token
	 * @throws NullPointerException     if the given 'output' or 'defaultClazz' is null
	 * @throws IllegalArgumentException if the given component index is less than 0
	 */
	public <U> FormatToken<U> subToken(U input, Writer output, Clazz subClazz, int component) {
		Objects.requireNonNull(output, "output");
		if (component < 0)
			throw new IllegalAccessError("component < 0");

		Clazz klazz = this.klazz.getComponentType(component);

		if (klazz == null)
			klazz = subClazz == null ?
					//OBJECT, OBJECT, OBJECT
					Clazz.of(Object.class) :
					//DEFAULT, DEFAULT, DEFAULT
					subClazz;
		else if (subClazz != null)
			klazz = klazz.isAssignableFrom(subClazz) ?
					//DEFAULT, DEFAULT, CLAZZ
					Clazz.ofz(subClazz, klazz.getComponentTypes()) :
					//DEFAULT, CLAZZ, CLAZZ
					Clazz.ofz(subClazz.getFamily(), klazz);
		//CLAZZ, CLAZZ, CLAZZ

		return this.subToken(input, output, klazz);
	}
}
