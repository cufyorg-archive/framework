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
package cufy.util.shorts.function;

import java.util.function.BinaryOperator;

/**
 * Represents an operation upon two {@code short}-valued operands and producing a {@code
 * short}-valued result. This is the primitive type specialization of {@link BinaryOperator} for
 * {@code short}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #applyAsShort(short,
 * short)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface ShortBinaryOperator {
	/**
	 * Applies this operator to the given operands.
	 *
	 * @param left  the first operand.
	 * @param right the second operand.
	 * @return the operator result.
	 * @since 0.1.5 ~2020.08.30
	 */
	short applyAsShort(short left, short right);
}
