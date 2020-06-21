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

import cufy.util.Reflection;

import java.util.function.Function;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <E> the exception.
 * @param <T> the type of the input to the function.
 * @param <R> the type of the result of the function.
 * @author LSafer
 * @version 0.1.3
 * @since 0.1.0 ~2020.02.13
 */
@FunctionalInterface
public interface ThrowFunction<T, R, E extends Throwable> extends Function<T, R> {
	@Override
	default R apply(T t) {
		try {
			return this.apply0(t);
		} catch (Throwable e) {
			throw Reflection.ignite(e);
		}
	}

	/**
	 * Applies this function to the given argument.
	 *
	 * @param t the function argument.
	 * @return the function result.
	 * @throws E the exception.
	 */
	R apply0(T t) throws E;
}
