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

import cufy.util.Reflectionu;

import java.util.function.BiPredicate;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @param <E> the exception
 * @author lsafer
 * @version 0.1.2
 * @since 13-Feb-2020
 */
@FunctionalInterface
public interface ThrowBiPredicate<T, U, E extends Throwable> extends BiPredicate<T, U> {
	@Override
	default boolean test(T t, U u) {
		try {
			return this.test0(t, u);
		} catch (Throwable e) {
			throw Reflectionu.<Error>ignite(e);
		}
	}

	/**
	 * Evaluates this predicate on the given arguments.
	 *
	 * @param t the first input argument
	 * @param u the second input argument
	 * @return true if the input arguments match the predicate, otherwise false
	 * @throws E the exception
	 */
	boolean test0(T t, U u) throws E;
}
