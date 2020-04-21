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

import java.util.Objects;

/**
 * A class that can convert instances to different classes. With just a simple gate method (for the caller).
 *
 * @author lsafer
 * @version 0.1.3
 * @since 31-Aug-2019
 */
public interface Converter {
	/**
	 * If the input is instance of the given clazz. The input will be returned. Otherwise the input will be converted. And foreach element in the
	 * input if it's instance of the components type of the given clazz the element will remain. Otherwise the element will be converted. And so on
	 * for the elements of the elements.
	 *
	 * @param object the input to apply type to
	 * @param type   the clazz to apply
	 * @param <I>    the type of the input
	 * @param <O>    the type of the output
	 * @return the input with the given clazz applied
	 * @throws NullPointerException if the given 'type' is null
	 * @throws ConvertException     if any converting exception occurred
	 */
	default <I, O> O apply(I object, Clazz<O> type) {
		return (O) this.convert(new ConvertToken<>(object, object, Clazz.ofi(object), type));
	}

	/**
	 * Deep clone the given object.
	 *
	 * @param object to be cloned
	 * @param <I>    the type of the object
	 * @param <O>    the type of the output
	 * @return a clone of the given object
	 * @throws ConvertException if any converting error occurred
	 */
	default <I, O> O clone(I object) {
		Clazz klazz = Clazz.ofi(object);
		return this.convert(new ConvertToken<>(object, null, klazz, klazz));
	}

	/**
	 * Convert the given input to the given output.
	 * <p>
	 * Note: the given output is the final output, so given a null output could throw an exception, since why there is a method converting things into
	 * null
	 *
	 * @param input  the input instance
	 * @param output the output instance
	 * @param <I>    the type of the input
	 * @param <O>    the type of the output
	 * @return the output
	 * @throws ConvertException if any conversion exception occurred
	 */
	default <I, O> O convert(I input, O output) {
		return this.convert(new ConvertToken<>(input, output, Clazz.ofi(input), Clazz.ofi(output)));
	}

	/**
	 * Convert the given input to the given clazz. Or override the given output if it's valid.
	 *
	 * @param input  the input instance
	 * @param output the initial output instance
	 * @param klazz  the clazz to convert the input to
	 * @param <I>    the type of the input
	 * @param <O>    the type of the output
	 * @return the output
	 * @throws NullPointerException if the given 'klazz' is null
	 * @throws ConvertException     if any convert exception occurs
	 */
	default <I, O> O convert(I input, O output, Clazz klazz) {
		Objects.requireNonNull(klazz, "klazz");
		return this.convert(new ConvertToken<>(input, output, Clazz.ofi(input), klazz));
	}

	/**
	 * Set the {@link ConvertToken#output} on the given token to a value of the {@link ConvertToken#input}, but as the class specified
	 * as in the {@link ConvertToken#outputClazz}.
	 *
	 * @param token the conversion instance that holds the variables of this conversion
	 * @param <O>   the {@link ConvertToken#output} of the given token after the converting process
	 * @param <I>   the {@link ConvertToken#input} of the given token
	 * @return the output
	 * @throws ConvertException     if any converting error occurred
	 * @throws NullPointerException if the given 'token' is null.
	 */
	<I, O> O convert(ConvertToken<I, O> token);
}
