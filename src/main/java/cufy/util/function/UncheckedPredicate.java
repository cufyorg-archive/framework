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

import java.util.function.Predicate;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <T> the type of the first argument to the predicate.
 * @param <E> the exception.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.08.30
 */
@FunctionalInterface
public interface UncheckedPredicate<T, E extends Throwable> extends Predicate<T> {
	@Override
	default boolean test(T t) {
		//noinspection RedundantCast
		return ((UncheckedPredicate<T, Error>) this)
				.test0(t);
	}

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param t the input argument
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
	 * @throws E the exception.
	 * @since 0.1.5 ~2020.08.30
	 */
	boolean test0(T t) throws E;
}
