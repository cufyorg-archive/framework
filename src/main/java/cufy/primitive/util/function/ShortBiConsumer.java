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
package cufy.primitive.util.function;

import cufy.util.Objects;

/**
 * Represents an operation that accepts two {@code short}-valued arguments and returns no result.
 * This is the primitive type specialization of {@link java.util.function.BiConsumer} for {@code
 * short}. Unlike most other functional interfaces, {@code ShortBiConsumer} is expected to operate
 * via side-effects.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #accept(short, short)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
@FunctionalInterface
public interface ShortBiConsumer {
	/**
	 * Return a composed {@code ShortBiConsumer} that performs, in sequence, this operation followed
	 * by the {@code after} operation. If performing either operation throws an exception, it is
	 * relayed to the caller of the composed operation. If performing this operation throws an
	 * exception, the {@code after} operation will not be performed.
	 *
	 * @param after the operation to perform after this operation.
	 * @return a composed {@code ShortBiConsumer} that performs in sequence this operation followed
	 * 		by the {@code after} operation.
	 * @throws NullPointerException if the given {@code after} is null.
	 * @since 0.1.5 ~2020.09.03
	 */
	default ShortBiConsumer andThen(ShortBiConsumer after) {
		Objects.requireNonNull(after, "after");
		return (v, o) -> {
			this.accept(v, o);
			after.accept(v, o);
		};
	}

	/**
	 * Perform this operation on the given arguments.
	 *
	 * @param value the first input argument.
	 * @param other the second input argument.
	 * @since 0.1.5 ~2020.09.03
	 */
	void accept(short value, short other);
}
