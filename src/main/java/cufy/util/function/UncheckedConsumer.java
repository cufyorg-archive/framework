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

import java.util.function.Consumer;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <E> the exception.
 * @param <T> the type of the input to the operation.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.0 ~2020.02.13
 */
@FunctionalInterface
public interface UncheckedConsumer<T, E extends Throwable> extends Consumer<T> {
	@Override
	default void accept(T t) {
		//noinspection RedundantCast
		((UncheckedConsumer<T, Error>) this)
				.accept0(t);
	}

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument.
	 * @throws E the exception that this runnable throws.
	 * @since 0.1.0 ~2020.02.13
	 */
	void accept0(T t) throws E;
}
