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
package cufy.primitive.util.function;

import java.util.function.Function;

/**
 * Represents a function that accepts a char-valued argument and produces an int-valued result. This
 * is the {@code char}-to-{@code int} primitive specialization for {@link Function}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #applyAsInt(char)}.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface CharToIntFunction {
	/**
	 * Applies this function to the given argument.
	 *
	 * @param value the function argument.
	 * @return the function result.
	 * @since 0.1.5 ~2020.08.30
	 */
	int applyAsInt(char value);
}
