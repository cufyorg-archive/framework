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

import java.util.PrimitiveIterator;
import java.util.Spliterator;

/**
 * An array specialized for primitive values.
 *
 * @param <A> the type of the array.
 * @param <E> the type of the elements.
 * @param <C> the type of the consumer.
 * @param <R> the type of the bi-consumer.
 * @param <O> the type of the intTo function.
 * @param <D> the type of the toDouble function.
 * @param <I> the type of the toInt function.
 * @param <L> the type of the toLong function.
 * @param <U> the type of the unary operator.
 * @param <B> the type of the binary operator.
 * @param <P> the type of the predicate.
 * @param <T> the type of the comparator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveArray
		<A, E, C, R, O, D, I, L, U, B, P, T extends PrimitiveComparator<E, D, I, L, U, T>>
		extends
		PrimitiveIterable<E, C>,
		Array<A, E> {
	@Override
	PrimitiveList<E, C, D, I, L, U, P, T> list();

	@Override
	PrimitiveListIterator<E, C> listIterator();

	@Override
	PrimitiveMap<E, E, R, B> map();

	@Override
	PrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> sub(int beginThumb, int endThumb);

	/**
	 * Cumulates, in parallel, each element of this array in place, using the supplied function. For
	 * example if this array initially holds [2, 1, 0, 3] and the operation performs addition, then
	 * upon return this array holds [2, 3, 3, 6]. Parallel prefix computation is usually more
	 * efficient than sequential loops for large arrays.
	 *
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	void parallelPrefix(B operator);

	/**
	 * In parallel, assign each element of this array to the value returned from invoking the given
	 * {@code function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	void parallelSetAll(O function);

	/**
	 * Sorts this array according to the order induced by the specified {@code comparator}. This
	 * sort is guaranteed to be stable: equal elements will not be reordered as a result of the
	 * sort.
	 *
	 * @param comparator the comparator to determine the order of this array. A null value indicates
	 *                   that the elements' natural ordering should be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	void parallelSort(T comparator);

	/**
	 * Assign each element of this array to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the {@code function} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	void setAll(O function);

	/**
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	void sort(T comparator);

	//public int binarySearch(primitive element)
	//public int binarySearch(primitive element, PrimitiveComparator comparator)
	//public void fill(primitive element)
	//public primitive getPrimitive(int thumb)
	//public PrimitiveStream primitiveStream()
	//public PrimitiveStream parallelPrimitiveStream()
	//public void setPrimitive(int thumb, primitive element)
	//protected boolean eq(Object element, primitive e)
	//protected boolean eq(primitive element, primitive e)
	//protected int hash(primitive e)

	/**
	 * An array iterator specialized for primitive values.
	 *
	 * @param <E> the type of the elements.
	 * @param <C> the type of the consumer.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	interface PrimitiveArrayIterator
			<E, C>
			extends
			PrimitiveIterator<E, C>,
			ArrayIterator<E> {
	}

	/**
	 * An array list specialized for primitive values.
	 *
	 * @param <E> the type of the elements.
	 * @param <C> the type of the consumer.
	 * @param <D> the type of the toDouble function.
	 * @param <I> the type of the toInt function.
	 * @param <L> the type of the toLong function.
	 * @param <U> the type of the unary operator.
	 * @param <P> the type of the predicate.
	 * @param <T> the type of the comparator.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	interface PrimitiveArrayList
			<E, C, D, I, L, U, P, T extends PrimitiveComparator<E, D, I, L, U, T>>
			extends
			PrimitiveList<E, C, D, I, L, U, P, T>,
			ArrayList<E> {
		@Override
		default boolean removeIf(P predicate) {
			throw new UnsupportedOperationException("removeIf");
		}
	}

	/**
	 * An array list iterator specialized for primitive values.
	 *
	 * @param <E> the type of the elements.
	 * @param <C> the type of the consumer.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	interface PrimitiveArrayListIterator
			<E, C>
			extends
			PrimitiveListIterator<E, C>,
			ArrayListIterator<E> {
	}

	/**
	 * An array map specialized for primitive values.
	 *
	 * @param <E> the type of the elements.
	 * @param <C> the type of the consumer.
	 * @param <R> the type of the bi-consumer.
	 * @param <B> the type of the binary operator.
	 * @param <P> the type of the predicate.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	interface PrimitiveArrayMap
			<E, C, R, B, P>
			extends
			PrimitiveMap<E, E, R, B>,
			ArrayMap<E, E, E> {
		@Override
		PrimitiveSet<E, C, P> keySet();

		@Override
		PrimitiveCollection<E, C, P> values();

		/**
		 * An array entry specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayEntry
				<E>
				extends
				PrimitiveEntry<E, E>,
				ArrayEntry<E, E, E> {
		}

		/**
		 * An array entry iterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayEntryIterator
				<E>
				extends
				ArrayEntryIterator<E, E, E> {
			@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
			@Override
			PrimitiveEntry<E, E> next();
		}

		/**
		 * An array entry set specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayEntrySet
				<E>
				extends
				ArrayEntrySet<E, E, E> {
		}

		/**
		 * An array entry spliterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayEntrySpliterator
				<E>
				extends
				ArrayEntrySpliterator<E, E, E> {
		}

		/**
		 * A primitive array key iterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayKeyIterator
				<E, C>
				extends
				PrimitiveIterator<E, C>,
				ArrayKeyIterator<E, E> {
		}

		/**
		 * An array key set specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @param <P> the type of the predicate.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayKeySet
				<E, C, P>
				extends
				PrimitiveSet<E, C, P>,
				ArrayKeySet<E, E> {
			@Override
			default boolean removeIf(P predicate) {
				throw new UnsupportedOperationException("removeIf");
			}
		}

		/**
		 * An array key spliterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @param <S> the type of the spliterator.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayKeySpliterator
				<E, C, S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				Spliterator.OfPrimitive<E, C, S>,
				ArrayKeySpliterator<E, E> {
		}

		/**
		 * An array value iterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayValueIterator
				<E, C>
				extends
				PrimitiveIterator<E, C>,
				ArrayValueIterator<E, E> {
		}

		/**
		 * An array value spliterator specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @param <S> the type of the spliterator.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayValueSpliterator
				<E, C, S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				Spliterator.OfPrimitive<E, C, S>,
				ArrayValueSpliterator<E, E> {
		}

		/**
		 * An array values specialized for primitive values.
		 *
		 * @param <E> the type of the elements.
		 * @param <C> the type of the consumer.
		 * @param <P> the type of the predicate.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		interface PrimitiveArrayValues
				<E, C, P>
				extends
				PrimitiveCollection<E, C, P>,
				ArrayValues<E, E> {
			@Override
			default boolean removeIf(P predicate) {
				throw new UnsupportedOperationException("removeIf");
			}
		}
	}

	/**
	 * An array spliterator specialized for primitive values.
	 *
	 * @param <E> the type of the elements.
	 * @param <C> the type of the consumer.
	 * @param <S> the type of the spliterator.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	interface PrimitiveArraySpliterator
			<E, C, S extends Spliterator.OfPrimitive<E, C, S>>
			extends
			Spliterator.OfPrimitive<E, C, S>,
			ArraySpliterator<E> {
	}
}
