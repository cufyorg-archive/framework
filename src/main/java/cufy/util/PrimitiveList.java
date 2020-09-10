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

import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

/**
 * A list specialized for primitive values.
 *
 * @param <E> the type of the elements.
 * @param <C> the type of the consumer.
 * @param <D> the type of the toDoubleFunction.
 * @param <I> the type of the toIntFunction.
 * @param <L> the type of the toLongFunction.
 * @param <U> the type of the unary operator.
 * @param <P> the type of the predicate.
 * @param <T> the ype of the comparator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveList
		<E, C, D, I, L, U, P, T extends PrimitiveComparator<E, D, I, L, U, T>>
		extends
		PrimitiveCollection<E, C, P>,
		List<E> {
	@Override
	PrimitiveListIterator<E, C> listIterator();

	@Override
	PrimitiveListIterator<E, C> listIterator(int index);

	@Override
	Spliterator.OfPrimitive<E, C, ?> spliterator();

	@Override
	PrimitiveList<E, C, D, I, L, U, P, T> subList(int beginIndex, int endIndex);

	/**
	 * Replaces each element of this list with the result of applying the operator to that element.
	 * Errors or runtime exceptions thrown by the operator are relayed to the caller.
	 *
	 * @param operator the operator to apply to each element.
	 * @throws UnsupportedOperationException if this list is unmodifiable. Implementations may throw
	 *                                       this exception if an element cannot be replaced or if,
	 *                                       in general, modification is not supported.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	void replaceAll(U operator);

	/**
	 * Sorts this list according to the order induced by the specified {@link Comparator}.
	 *
	 * @param comparator the {@code Comparator} used to compare list elements. A {@code null} value
	 *                   indicates that the elements' natural ordering should be used.
	 * @throws UnsupportedOperationException if this list is unmodifiable.
	 * @throws IllegalArgumentException      (optional) if the comparator is found to violate the
	 *                                       {@link Comparator} contract.
	 * @since 0.1.5 ~2020.09.01
	 */
	void sort(T comparator);

	//void addPrimitive(int index, primitive element)
	//primitive getPrimitive(int index)
	//int indexOf(primitive element)
	//int lastIndexOf(primitive element)
	//primitive removePrimitiveAt(int index)
	//primitive setPrimitive(int index, primitive element)
}
//
//	/**
//	 * Inserts all of the elements in the specified collection into this list at the specified
//	 * position (optional operation). Shifts the element currently at that position (if any) and any
//	 * subsequent elements to the right (increases their indices). The new elements will appear in
//	 * this list in the order that they are returned by the specified collection's iterator. The
//	 * behavior of this operation is undefined if the specified collection is modified while the
//	 * operation is in progress. (Note that this will occur if the specified collection is this
//	 * list, and it's nonempty.)
//	 *
//	 * @param index      index at which to insert the first element from the specified collection.
//	 * @param collection collection containing elements to be added to this list.
//	 * @return {@code true} if this list changed as a result of the call.
//	 * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by
//	 *                                       this list.
//	 * @throws NullPointerException          if the given {@code collection} is null.
//	 * @throws IllegalArgumentException      if some property of an element of the specified
//	 *                                       collection prevents it from being added to this list.
//	 * @throws IndexOutOfBoundsException     if the index is out of range ({@code index < 0 || index
//	 *                                       > size()}).
//	 * @since 0.1.5 ~2020.09.01
//	 */
//	boolean addAll(int index, COLLECTION collection);
