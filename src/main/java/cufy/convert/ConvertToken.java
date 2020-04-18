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
 *
 * <ul>
 *     Combinations:
 *     <li>
 *         To clone an object:
 *         <pre>
 * new ConvertToken<>(input, null, inputClazz, inputClazz)
 *         </pre>
 *     </li>
 *     <li>
 *         To apply a type to an object:
 *         <pre>
 * new ConvertToken<>(input, input, inputClazz, newClazz)
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
	final public Map data = new HashMap();
	/**
	 * The input object.
	 */
	final public I input;
	/**
	 * A table of data to be copied from this token to it's sub-tokens.
	 */
	final public Map linear;
	/**
	 * The convert-token for the conversion that required initializing this token.
	 */
	final public ConvertToken parent;
	/**
	 * A table of data globally shared across this token and it's sub-tokens.
	 */
	final public Map tree;
	/**
	 * The depth of this token form the first parent.
	 */
	final int depth;
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
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
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
	 * @param parent      the parent converting-token
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'parent' or 'inputClass' or 'outputClass' is null
	 * @implSpec dejaVu autodetect!
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

		return new ConvertToken<>(this, input, output, inputClazz, outputClazz);
	}
}
