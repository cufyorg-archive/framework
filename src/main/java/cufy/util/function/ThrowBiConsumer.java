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

import java.util.function.BiConsumer;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <E> the exception
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @author lsafer
 * @version 0.1.3
 * @since 13-Feb-2020
 */
@FunctionalInterface
public interface ThrowBiConsumer<T, U, E extends Throwable> extends BiConsumer<T, U> {
	@Override
	default void accept(T t, U u) {
		try {
			this.accept0(t, u);
		} catch (Throwable e) {
			Reflection.<Error>ignite(e);
		}
	}

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t the first input argument
	 * @param u the second input argument
	 * @throws E the exception
	 */
	void accept0(T t, U u) throws E;
}
