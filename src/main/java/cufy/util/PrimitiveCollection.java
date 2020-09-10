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
package cufy.util;

import cufy.lang.PrimitiveIterable;

import java.util.Collection;
import java.util.Spliterator;

/**
 * A collection specialized for primitive values.
 *
 * @param <E> the type of the elements.
 * @param <C> the type of the consumer.
 * @param <P> the type of the predicate.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
public interface PrimitiveCollection
		<E, C, P>
		extends
		PrimitiveIterable<E, C>,
		Collection<E> {
	@Override
	Spliterator.OfPrimitive<E, C, ?> spliterator();

	/**
	 * Removes all of the elements of this collection that satisfy the given predicate. Errors or
	 * runtime exceptions thrown during iteration or by the predicate are relayed to the caller.
	 *
	 * @param predicate a predicate which returns {@code true} for elements to be removed.
	 * @return {@code true} if any elements were removed.
	 * @throws NullPointerException          if the given {@code predicate} is null.
	 * @throws UnsupportedOperationException if elements cannot be removed from this collection.
	 *                                       Implementations may throw this exception if a matching
	 *                                       element cannot be removed or if, in general, removal is
	 *                                       not supported.
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean removeIf(P predicate);

	//default PrimitiveStream primitiveStream()
	//default parallelPrimitiveStream parallelPrimitiveStream()
	//boolean addPrimitive(primitive element)
	//boolean contains(primitive element)
	//boolean removePrimitive(primitive element)
	//primitive[] toPrimitiveArray()
	//primitive[] toPrimitiveArray(primitive[] array)
}

