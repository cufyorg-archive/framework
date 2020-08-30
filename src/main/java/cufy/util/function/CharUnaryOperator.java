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

import java.util.function.UnaryOperator;

/**
 * Represents an operation on a single {@code char}-valued operand that produces a {@code
 * char}-valued result. This is the primitive type specialization of {@link UnaryOperator} for
 * {@code char}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #applyAsChar(char)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface CharUnaryOperator {
	/**
	 * Returns a unary operator that always returns its input argument.
	 *
	 * @return a unary operator that always returns its input argument.
	 * @since 0.1.5 ~2020.08.30
	 */
	static CharUnaryOperator identity() {
		return value -> value;
	}

	/**
	 * Returns a composed operator that first applies this operator to its input, and then applies
	 * the {@code after} operator to the result. If evaluation of either operator throws an
	 * exception, it is relayed to the caller of the composed operator.
	 *
	 * @param after the operator to apply after this operator is applied.
	 * @return a composed operator that first applies this operator and then applies the {@code
	 * 		after} operator.
	 * @throws NullPointerException if the given {@code after} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	default CharUnaryOperator andThen(CharUnaryOperator after) {
		Objects.requireNonNull(after, "after");
		return value -> after.applyAsChar(this.applyAsChar(value));
	}

	/**
	 * Returns a composed operator that first applies the {@code before} operator to its input, and
	 * then applies this operator to the result. If evaluation of either operator throws an
	 * exception, it is relayed to the caller of the composed operator.
	 *
	 * @param before the operator to apply before this operator is applied.
	 * @return a composed operator that first applies the {@code before} operator and then applies
	 * 		this operator.
	 * @throws NullPointerException if the given {@code before} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	default CharUnaryOperator compose(CharUnaryOperator before) {
		Objects.requireNonNull(before, "before");
		return value -> this.applyAsChar(before.applyAsChar(value));
	}

	/**
	 * Applies this operator to the given operand.
	 *
	 * @param value the operand.
	 * @return the operator result.
	 * @since 0.1.5 ~2020.08.30
	 */
	char applyAsChar(char value);
}
