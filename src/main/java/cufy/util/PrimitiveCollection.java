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
//	/**
//	 * Adds all of the elements in the specified collection to this collection (optional operation).
//	 * The behavior of this operation is undefined if the specified collection is modified while the
//	 * operation is in progress. (This implies that the behavior of this call is undefined if the
//	 * specified collection is this collection, and this collection is nonempty.)
//	 *
//	 * @param collection collection containing elements to be added to this collection.
//	 * @return {@code true} if this collection changed as a result of the call.
//	 * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by
//	 *                                       this collection.
//	 * @throws NullPointerException          if the given {@code collection} is null.
//	 * @throws IllegalArgumentException      if some property of an element of the specified
//	 *                                       collection prevents it from being added to this
//	 *                                       collection.
//	 * @throws IllegalStateException         if not all the elements can be added at this time due
//	 *                                       to insertion restrictions.
//	 * @since 0.1.5 ~2020.09.01
//	 */
//	boolean addAll(COLLECTION collection);

//	/**
//	 * Returns {@code true} if this collection contains all of the elements in the specified
//	 * collection.
//	 *
//	 * @param collection collection to be checked for containment in this collection.
//	 * @return {@code true} if this collection contains all of the elements in the specified
//	 * 		collection.
//	 * @throws NullPointerException if the given {@code collection} is null.
//	 * @since 0.1.5 ~2020.09.01
//	 */
//	boolean containsAll(COLLECTION collection);

//	/**
//	 * Removes all of this collection's elements that are also contained in the specified collection
//	 * (optional operation).  After this call returns, this collection will contain no elements in
//	 * common with the specified collection.
//	 *
//	 * @param collection collection containing elements to be removed from this collection.
//	 * @return {@code true} if this collection changed as a result of the call.
//	 * @throws UnsupportedOperationException if the {@code removeAll} method is not supported by
//	 *                                       this collection.
//	 * @throws NullPointerException          if the given {@code collection} is null.
//	 * @since 0.1.5 ~2020.09.01
//	 */
//	boolean removeAll(COLLECTION collection);

//
//	/**
//	 * Retains only the elements in this collection that are contained in the specified collection
//	 * (optional operation). In other words, removes from this collection all of its elements that
//	 * are not contained in the specified collection.
//	 *
//	 * @param collection collection containing elements to be retained in this collection.
//	 * @return {@code true} if this collection changed as a result of the call.
//	 * @throws UnsupportedOperationException if the {@code retainAll} operation is not supported by
//	 *                                       this collection.
//	 * @throws NullPointerException          if the given {@code collection} is null.
//	 * @since 0.1.5 ~2020.09.01
//	 */
//	boolean retainAll(COLLECTION collection);
