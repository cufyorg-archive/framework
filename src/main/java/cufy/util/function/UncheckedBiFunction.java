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

import java.util.function.BiFunction;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <E> the exception.
 * @param <T> the type of the first argument to the function.
 * @param <U> the type of the second argument to the function.
 * @param <R> the type of the result of the function.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.02.13
 */
@FunctionalInterface
public interface UncheckedBiFunction<T, U, R, E extends Throwable> extends BiFunction<T, U, R> {
	@Override
	default R apply(T t, U u) {
		//noinspection RedundantCast
		return ((UncheckedBiFunction<T, U, R, Error>) this)
				.apply0(t, u);
	}

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t the first function argument.
	 * @param u the second function argument.
	 * @return the function result.
	 * @throws E the exception.
	 * @since 0.1.0 ~2020.02.13
	 */
	R apply0(T t, U u) throws E;
}
