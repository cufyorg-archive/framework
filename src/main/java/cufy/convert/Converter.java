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

/**
 * A class that can convert instances to different classes. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.2
 * @since 31-Aug-2019
 */
public interface Converter {
	/**
	 * Output the value of the 'input', but as the 'outputClazz'.
	 *
	 * @param <I>         the type of the input
	 * @param <O>         the type of the output
	 * @param input       the input instance
	 * @param output      the initial output instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @return the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
	 * @throws ConvertException     if any converting error occurred
	 */
	default <I, O> O convert(I input, O output, Clazz inputClazz, Clazz outputClazz) {
		return this.convert(new ConvertArguments<>(input, output, inputClazz, outputClazz));
	}

	/**
	 * Output the value of the 'input', but as the 'outputClazz'.
	 *
	 * @param input       the input instance (source of inputClazz)
	 * @param output      the initial output instance
	 * @param outputClazz the clazz to be for the output
	 * @param <I>         the type of the input
	 * @param <O>         the type of the output
	 * @return the output
	 * @throws NullPointerException if the given 'outputClass' is null
	 * @throws ConvertException     if any converting error occurred
	 */
	default <I, O> O convert(I input, O output, Clazz outputClazz) {
		return this.convert(new ConvertArguments<>(input, output, outputClazz));
	}

	/**
	 * Convert the given 'output' to the value of the 'input'. Then return the results.
	 *
	 * @param <I>    the type of the input
	 * @param <O>    the type of the output
	 * @param input  the input instance (source of inputClazz)
	 * @param output the initial output instance (source of outputClazz)
	 * @return the output
	 * @throws ConvertException if any converting error occurred
	 * @apiNote null output may produce confusion issues
	 */
	default <I, O> O convert(I input, O output) {
		return this.convert(new ConvertArguments<>(input, output));
	}

	/**
	 * Output the value of the 'input', but as the 'outputClazz'.
	 *
	 * @param input       the input instance
	 * @param inputClazz  the clazz of the input
	 * @param outputClazz the clazz to be for the output
	 * @param <I>         the type of the input
	 * @param <O>         the type of the output
	 * @return the output
	 * @throws NullPointerException if the given 'inputClass' or 'outputClass' is null
	 * @throws ConvertException     if any converting error occurred
	 */
	default <I, O> O convert(I input, Clazz inputClazz, Clazz outputClazz) {
		return this.convert(new ConvertArguments<>(input, inputClazz, outputClazz));
	}

	/**
	 * Output the value of the 'input', but as the 'outputClazz'.
	 *
	 * @param input       the input instance (source of inputClazz)
	 * @param outputClazz the clazz to be for the output
	 * @param <O>         the type of the output
	 * @param <I>         the type of the input
	 * @return the output
	 * @throws NullPointerException if the given 'outputClass' is null
	 * @throws ConvertException     if any converting error occurred
	 */
	default <I, O> O convert(I input, Clazz outputClazz) {
		return this.convert(new ConvertArguments<>(input, outputClazz));
	}

	/**
	 * Clone the given input.
	 *
	 * @param input the input instance (source of inputClazz and outputClazz)
	 * @param <I>   the type of the input
	 * @param <O>   the type of the output
	 * @return the output
	 * @throws ConvertException if any converting error occurred
	 */
	default <I, O> O convert(I input) {
		return this.convert(new ConvertArguments<>(input));
	}

	/**
	 * Set the {@link ConvertArguments#output} on the given arguments to a value of the {@link ConvertArguments#input}, but as the class specified
	 * as in the {@link ConvertArguments#outputClazz}.
	 *
	 * @param arguments the conversion instance that holds the variables of this conversion
	 * @param <O>       the {@link ConvertArguments#output} of the given arguments after the converting process
	 * @return the output
	 * @throws ConvertException     if any converting error occurred
	 * @throws NullPointerException if the given 'arguments' is null.
	 */
	<O> O convert(ConvertArguments<?, O> arguments);
}
