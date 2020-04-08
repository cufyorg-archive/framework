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
import java.util.Objects;

/**
 * A formatting instance that holds the variables of a formatting.
 *
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author lsafer
 * @version 0.1.2
 * @since 30-Mar-2020
 */
public class FormatArguments<I, O> {
	/**
	 * The depth of this arguments form the first parent.
	 */
	final public int depth;
	/**
	 * The input object.
	 */
	final public I input;
	/**
	 * The class that the input do have.
	 */
	final public Clazz<I> inputClazz;
	/**
	 * The writer to write the output to.
	 */
	final public Writer output;
	/**
	 * The class that the output should have.
	 */
	final public Clazz<O> outputClazz;
	/**
	 * The formatting-arguments for the formatting that required initializing this arguments.
	 */
	final public FormatArguments parent;

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param parent      the parent arguments
	 * @param input       the input instance
	 * @param output      the output to write to
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' or 'output' is null
	 */
	public FormatArguments(FormatArguments parent, I input, Writer output, Clazz inputClazz, Clazz outputClazz) {
		Objects.requireNonNull(output, "output");
		Objects.requireNonNull(inputClazz, "inputClazz");
		Objects.requireNonNull(outputClazz, "outputClazz");

		int depth = 0;

		//recurse, depth detection
		for (FormatArguments grand = parent; grand != null; grand = grand.parent, depth++)
			if (grand.input == input)
				inputClazz = Clazz.of(Recurse.class, inputClazz.getKlass(), inputClazz.getComponentTypes());

		this.parent = parent;
		this.input = input;
		this.output = output;
		this.inputClazz = inputClazz;
		this.outputClazz = outputClazz;
		this.depth = depth;
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param parent the parent arguments
	 * @param input  the input instance (source for inputClazz and outputClazz)
	 * @param output the output to write to
	 * @throws NullPointerException if the given 'output' is null
	 */
	public FormatArguments(FormatArguments parent, I input, Writer output) {
		this(parent, input, output, Clazz.of(input), Clazz.of(input));
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param input       the input instance
	 * @param output      the output to write to
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' or 'output' is null
	 */
	public FormatArguments(I input, Writer output, Clazz inputClazz, Clazz outputClazz) {
		this(null, input, output, inputClazz, outputClazz);
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param input       the input instance (source of inputClazz)
	 * @param output      the output to write to
	 * @param outputClazz the clazz to be for the output
	 * @throws NullPointerException if the given 'outputClass' or 'output' is null
	 */
	public FormatArguments(I input, Writer output, Clazz outputClazz) {
		this(null, input, output, Clazz.of(input), outputClazz);
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param input  the input instance (source of inputClazz and outputClazz)
	 * @param output the output to write to
	 * @throws NullPointerException if the given 'output' is null
	 */
	public FormatArguments(I input, Writer output) {
		this(null, input, output, Clazz.of(input), Clazz.of(input));
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param parent         the parent arguments
	 * @param input          the input instance
	 * @param output         the output to write to
	 * @param inputAltClazz  the inputClazz if the inputComponentType clazz does not exist
	 * @param outputAltClazz the outputClazz if the outputComponentType clazz does not exist
	 * @param component      the component index to get from the parent's clazzes to the clazzes of this
	 * @throws NullPointerException     if the given 'parent' or 'inputAltClazz' or 'outputAltClazz' or 'output' is null
	 * @throws IllegalArgumentException if the given component index is less than 0
	 */
	public FormatArguments(FormatArguments parent, I input, Writer output, Clazz inputAltClazz, Clazz outputAltClazz, int component) {
		Objects.requireNonNull(parent, "parent");
		Objects.requireNonNull(output, "output");
		Objects.requireNonNull(inputAltClazz, "inputAltClazz");
		Objects.requireNonNull(outputAltClazz, "outputAltClazz");
		if (component < 0)
			throw new IllegalArgumentException("component < 0");

		//clazzes declarations
		Clazz<I> inputClazz = parent.inputClazz.getComponentType(component);
		Clazz<O> outputClazz = parent.outputClazz.getComponentType(component);

		//replace to alt if needed
		if (inputClazz == null)
			inputClazz = inputAltClazz;
		if (outputClazz == null)
			outputClazz = outputAltClazz;

		int depth = 0;

		//recurse, depth detection
		for (FormatArguments grand = parent; grand != null; grand = grand.parent, depth++)
			if (grand.input == input)
				inputClazz = Clazz.of(Recurse.class, inputClazz.getKlass(), inputClazz.getComponentTypes());

		this.parent = parent;
		this.input = input;
		this.output = output;
		this.inputClazz = inputClazz;
		this.outputClazz = outputClazz;
		this.depth = depth;
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param parent    the parent arguments
	 * @param input     the input instance (alt source of inputClazz and outputClazz)
	 * @param output    the output to write to
	 * @param component the component index to get from the parent's clazzes to the clazzes of this
	 * @throws NullPointerException     if the given 'parent' or 'output' is null
	 * @throws IllegalArgumentException if the given component index is less than 0
	 */
	public FormatArguments(FormatArguments parent, I input, Writer output, int component) {
		this(parent, input, output, Clazz.of(input), Clazz.of(input), component);
	}

	/**
	 * Construct a new formatting arguments instance.
	 *
	 * @param parent    the parent arguments
	 * @param input     the input instance (alt source of inputClazz and outputClazz)
	 * @param component the component index to get from the parent's clazzes to the clazzes of this
	 * @throws NullPointerException     if the given 'parent' is null
	 * @throws IllegalArgumentException if the given component index is less than 0
	 */
	public FormatArguments(FormatArguments parent, I input, int component) {
		this(parent, input, parent.output, Clazz.of(input), Clazz.of(input), component);
	}
}
