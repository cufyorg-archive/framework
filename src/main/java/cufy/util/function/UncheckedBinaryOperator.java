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

import java.util.function.BinaryOperator;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <T> the type of the operands and result of the operator.
 * @param <E> the exception.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.02.13
 */
@FunctionalInterface
public interface UncheckedBinaryOperator<T, E extends Throwable> extends BinaryOperator<T> {
	@Override
	default T apply(T t, T t2) {
		//noinspection RedundantCast
		return ((UncheckedBinaryOperator<T, Error>) this)
				.apply0(t, t2);
	}

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t  the first function argument.
	 * @param t2 the second function argument.
	 * @return the function result.
	 * @throws E the exception.
	 * @since 0.1.0 ~2020.02.13
	 */
	T apply0(T t, T t2) throws E;
}
