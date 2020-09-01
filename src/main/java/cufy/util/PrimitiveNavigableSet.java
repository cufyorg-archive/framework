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

import java.util.NavigableSet;
import java.util.PrimitiveIterator;
import java.util.Spliterator;

/**
 * A navigable set specialized for primitive values.
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
 * @param <COLLECTION>    the type of the collection.
 * @param <SET>           the type of the set.
 * @param <SORTED_SET>    the type of the sorted set.
 * @param <NAVIGABLE_SET> the type of the navigable set.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveNavigableSet<
		E,
		CONSUMER,
		PREDICATE,
		UNARY,
		TO_DOUBLE,
		TO_INT,
		TO_LONG,
		COMPARATOR extends PrimitiveComparator<E, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR>,
		ITERATOR extends PrimitiveIterator<E, CONSUMER>,
		SPLITERATOR extends Spliterator.OfPrimitive<E, CONSUMER, SPLITERATOR>,
		COLLECTION extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>,
		SET extends PrimitiveSet<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION, SET>,
		SORTED_SET extends PrimitiveSortedSet<E, CONSUMER, PREDICATE, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, SPLITERATOR, COLLECTION, SET, SORTED_SET>,
		NAVIGABLE_SET extends PrimitiveNavigableSet<E, CONSUMER, PREDICATE, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, SPLITERATOR, COLLECTION, SET, SORTED_SET, NAVIGABLE_SET>
		> extends PrimitiveSortedSet<E, CONSUMER, PREDICATE, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, SPLITERATOR, COLLECTION, SET, SORTED_SET>, NavigableSet<E> {
	@Override
	ITERATOR descendingIterator();

	@Override
	NAVIGABLE_SET descendingSet();

	@Override
	NAVIGABLE_SET headSet(E endElement, boolean inclusive);

	@Override
	NAVIGABLE_SET subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive);

	@Override
	NAVIGABLE_SET tailSet(E beginElement, boolean inclusive);
}
