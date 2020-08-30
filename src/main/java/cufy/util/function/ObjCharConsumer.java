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

import java.util.function.BiConsumer;

/**
 * Represents an operation that accepts an object-valued and a {@code char}-valued argument, and
 * returns no result.  This is the {@code (reference, char)} specialization of {@link BiConsumer}.
 * Unlike most other functional interfaces, {@code ObjCharConsumer} is expected to operate via
 * side-effects.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #accept(Object, char)}.
 *
 * @param <T> the type of the object argument to the operation.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.30
 */
@FunctionalInterface
public interface ObjCharConsumer<T> {
	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t     the first input argument.
	 * @param value the second input argument.
	 * @since 0.1.5 ~2020.08.30
	 */
	void accept(T t, char value);
}
