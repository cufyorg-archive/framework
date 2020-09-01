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
import java.util.PrimitiveIterator;
import java.util.Spliterator;

/**
 * A list specialized for primitive values.
 *
 * @param <E>             the type of the elements.
 * @param <CONSUMER>      the type of the consumer.
 * @param <PREDICATE>     the type of the predicate.
 * @param <UNARY>         the type of the unary operator.
 * @param <TO_INT>        the type of the toIntFunction.
 * @param <TO_DOUBLE>     the type of the toDoubleFunction.
 * @param <TO_LONG>       the type of the toLongFunction.
 * @param <COMPARATOR>    the type of the comparator.
 * @param <ITERATOR>      the type of the iterator.
 * @param <SPLITERATOR>   the type of the spliterator.
 * @param <LIST_ITERATOR> the type of the list iterator.
 * @param <COLLECTION>    the type of the collection.
 * @param <LIST>          the type of the list.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveList<
		E,
		CONSUMER,
		PREDICATE,
		UNARY,
		TO_DOUBLE,
		TO_INT,
		TO_LONG,
		COMPARATOR extends PrimitiveComparator<E, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR>,
		ITERATOR extends PrimitiveIterator<E, CONSUMER>,
		LIST_ITERATOR extends PrimitiveListIterator<E, CONSUMER>,
		SPLITERATOR extends Spliterator.OfPrimitive<E, CONSUMER, SPLITERATOR>,
		COLLECTION extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>,
		LIST extends PrimitiveList<E, CONSUMER, PREDICATE, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, LIST_ITERATOR, SPLITERATOR, COLLECTION, LIST>
		> extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>, List<E> {
	@Override
	LIST_ITERATOR listIterator();

	@Override
	LIST_ITERATOR listIterator(int index);

	@Override
	SPLITERATOR spliterator();

	@Override
	LIST subList(int fromIndex, int toIndex);

	/**
	 * Inserts all of the elements in the specified collection into this list at the specified
	 * position (optional operation). Shifts the element currently at that position (if any) and any
	 * subsequent elements to the right (increases their indices). The new elements will appear in
	 * this list in the order that they are returned by the specified collection's iterator. The
	 * behavior of this operation is undefined if the specified collection is modified while the
	 * operation is in progress. (Note that this will occur if the specified collection is this
	 * list, and it's nonempty.)
	 *
	 * @param index      index at which to insert the first element from the specified collection.
	 * @param collection collection containing elements to be added to this list.
	 * @return {@code true} if this list changed as a result of the call.
	 * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by
	 *                                       this list.
	 * @throws NullPointerException          if the given {@code collection} is null.
	 * @throws IllegalArgumentException      if some property of an element of the specified
	 *                                       collection prevents it from being added to this list.
	 * @throws IndexOutOfBoundsException     if the index is out of range ({@code index < 0 || index
	 *                                       > size()}).
	 * @since 0.1.5 ~2020.09.01
	 */
	boolean addAll(int index, COLLECTION collection);

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
	void replaceAll(UNARY operator);

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
	void sort(COMPARATOR comparator);
}
