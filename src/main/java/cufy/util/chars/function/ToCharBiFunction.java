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
package cufy.util.chars.function;

import java.util.function.BiFunction;

/**
 * Represents a function that accepts two arguments and produces a char-valued result. This is the
 * {@code char}-producing primitive specialization for {@link BiFunction}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #applyAsChar(Object,
 * Object)}.
 *
 * @param <T> the type of the first argument to the function.
 * @param <U> the type of the second argument to the function.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface ToCharBiFunction<T, U> {
	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t the first function argument.
	 * @param u the second function argument.
	 * @return the function result.
	 * @since 0.1.5 ~2020.08.30
	 */
	char applyAsChar(T t, U u);
}