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
package cufy.util.booleans.function;

import java.util.function.BinaryOperator;

/**
 * Represents an operation upon two {@code boolean}-valued operands and producing a {@code
 * boolean}-valued result. This is the primitive type specialization of {@link BinaryOperator} for
 * {@code boolean}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #applyAsBoolean(boolean,
 * boolean)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface BooleanBinaryOperator {
	/**
	 * Applies this operator to the given operands.
	 *
	 * @param left  the first operand.
	 * @param right the second operand.
	 * @return the operator result.
	 * @since 0.1.5 ~2020.08.30
	 */
	boolean applyAsBoolean(boolean left, boolean right);
}
