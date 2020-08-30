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
package cufy.util.function;

import cufy.util.Objects;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single {@code boolean}-valued argument and returns no
 * result. This is the primitive type specialization of {@link Consumer} for {@code boolean}. Unlike
 * most other functional interfaces, {@code BooleanConsumer} is expected to operate via
 * side-effects.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #accept(boolean)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface BooleanConsumer {
	/**
	 * Returns a composed {@code BooleanConsumer} that performs, in sequence, this operation
	 * followed by the {@code after} operation. If performing either operation throws an exception,
	 * it is relayed to the caller of the composed operation.  If performing this operation throws
	 * an exception, the {@code after} operation will not be performed.
	 *
	 * @param after the operation to perform after this operation
	 * @return a composed {@code BooleanConsumer} that performs in sequence this operation followed
	 * 		by the {@code after} operation
	 * @throws NullPointerException if the given {@code after} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	default BooleanConsumer andThen(BooleanConsumer after) {
		Objects.requireNonNull(after, "after");
		return value -> {
			this.accept(value);
			after.accept(value);
		};
	}

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param value the input argument.
	 * @since 0.1.5 ~2020.08.30
	 */
	void accept(boolean value);
}
