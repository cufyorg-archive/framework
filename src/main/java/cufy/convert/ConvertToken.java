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
import cufy.lang.Recurse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A conversion instance that holds the variables of a conversion.
 *
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author lsafer
 * @version 0.1.2
 * @since 30-Mar-2020
 */
public class ConvertToken<I, O> {
	/**
	 * A table of data to be copied from this token to it's sub-tokens.
	 */
	final public Map data;
	/**
	 * A table of data globally shared across this token and it's sub-tokens.
	 */
	final public Map data_global;
	/**
	 * The input object.
	 */
	final public I input;
	/**
	 * The class that the input do have.
	 */
	final public Clazz<I> inputClazz;
	/**
	 * The class that the output should have.
	 */
	final public Clazz<O> outputClazz;
	/**
	 * The convert-token for the conversion that required initializing this token.
	 */
	final public ConvertToken parent;
	/**
	 * The depth of this token form the first parent.
	 */
	final int depth;
	/**
	 * The output of the conversion. (could be changed several times!)
	 */
	public O output;

	/**
	 * Construct a new conversion token instance.
	 *
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
	 */
	public ConvertToken(I input, O output, Clazz inputClazz, Clazz outputClazz) {
		this(null, input, output, inputClazz, outputClazz);
	}

	/**
	 * Construct a new conversion token instance.
	 *
	 * @param parent      the parent converting-token
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
	 */
	protected ConvertToken(ConvertToken parent, I input, O output, Clazz inputClazz, Clazz outputClazz) {
		Objects.requireNonNull(inputClazz, "inputClazz");
		Objects.requireNonNull(outputClazz, "outputClazz");

		//recurse, depth detection
		for (ConvertToken grand = parent; grand != null; grand = grand.parent)
			if (grand.input == input) {
				inputClazz = Clazz.ofz(Recurse.class, inputClazz);
				break;
			}

		this.input = input;
		this.output = output;
		this.inputClazz = inputClazz;
		this.outputClazz = outputClazz;
		this.parent = parent;

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
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @param <J>         the type of the input in the sub token
	 * @param <Q>         the type of the output in the sub token
	 * @return a sub token of this token
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
	 */
	public <J, Q> ConvertToken<J, Q> subToken(J input, Q output, Clazz inputClazz, Clazz outputClazz) {
		return new ConvertToken<>(this, input, output, inputClazz, outputClazz);
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
	 * @param input          the input instance
	 * @param output         the initial output instance
	 * @param subInputClazz  the flavor inputClazz (see the rules above for more details)
	 * @param subOutputClazz the flavor outputClazz (see the rules above for more details)
	 * @param component      the component index to pass to the subToken
	 * @param <J>            the type of the input in the sub token
	 * @param <Q>            the type of the output in the sub token
	 * @return a sub token of this token
	 * @throws IllegalArgumentException if the given component index is less than 0
	 */
	public <J, Q> ConvertToken<J, Q> subToken(J input, Q output, Clazz subInputClazz, Clazz subOutputClazz, int component) {
		if (component < 0)
			throw new IllegalArgumentException("component < 0");

		Clazz inputClazz = this.inputClazz.getComponentType(component);
		Clazz outputClazz = this.outputClazz.getComponentType(component);

		if (inputClazz == null)
			inputClazz = subInputClazz == null ?
						 //OBJECT, OBJECT, OBJECT
						 Clazz.of(Object.class) :
						 //DEFAULT, DEFAULT, DEFAULT
						 subInputClazz;
		else if (subInputClazz != null)
			inputClazz = inputClazz.isAssignableFrom(subInputClazz) ?
						 //DEFAULT, DEFAULT, CLAZZ
						 Clazz.ofz(subInputClazz, inputClazz.getComponentTypes()) :
						 //DEFAULT, CLAZZ, CLAZZ
						 Clazz.ofz(subInputClazz.getFamily(), inputClazz);
		//CLAZZ, CLAZZ, CLAZZ

		if (outputClazz == null)
			outputClazz = subOutputClazz == null ?
						  //OBJECT, OBJECT, OBJECT
						  Clazz.of(Object.class) :
						  //DEFAULT, DEFAULT, DEFAULT
						  subOutputClazz;
		else if (subOutputClazz != null)
			outputClazz = outputClazz.isAssignableFrom(subOutputClazz) ?
						  //DEFAULT, DEFAULT, CLAZZ
						  Clazz.ofz(subOutputClazz, outputClazz.getComponentTypes()) :
						  //DEFAULT, CLAZZ, CLAZZ
						  Clazz.ofz(subOutputClazz.getFamily(), outputClazz);
		//CLAZZ, CLAZZ, CLAZZ

		return this.subToken(input, output, inputClazz, outputClazz);
	}
}
