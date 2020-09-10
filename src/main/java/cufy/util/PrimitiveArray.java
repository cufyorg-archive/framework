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
 * @param <R> the type of the bi-consumer.
 * @param <C> the type of the consumer.
 * @param <P> the type of the predicate.
 * @param <U> the type of the unary operator.
 * @param <B> the type of the binary operator.
 * @param <O> the type of the intTo function.
 * @param <D> the type of the toDouble function.
 * @param <I> the type of the toInt function.
 * @param <L> the type of the toLong function.
 * @param <T> the type of the comparator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public abstract class PrimitiveArray
		<A, E, C, R, O, D, I, L, U, B, P, T extends PrimitiveComparator<E, D, I, L, U, T>>
		extends
		Array<A, E>
		implements
		PrimitiveIterable<E, C> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -5497455737667076730L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	protected PrimitiveArray(A array) {
		super(array);
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range
	 * starts at the given {@code beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.09.01
	 */
	protected PrimitiveArray(A array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	@Override
	public abstract PrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> clone();

	@Override
	public abstract PrimitiveArrayIterator iterator();

	@Override
	public abstract PrimitiveArrayList list();

	@Override
	public abstract PrimitiveArrayListIterator listIterator();

	@Override
	public abstract PrimitiveArrayMap map();

	@Override
	public abstract PrimitiveArraySpliterator spliterator();

	@Override
	public abstract PrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> sub(int beginThumb, int endThumb);

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
	public abstract void parallelPrefix(B operator);

	/**
	 * In parallel, assign each element of this array to the value returned from invoking the given
	 * {@code function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	public abstract void parallelSetAll(O function);

	/**
	 * Sorts this array according to the order induced by the specified {@code comparator}. This
	 * sort is guaranteed to be stable: equal elements will not be reordered as a result of the
	 * sort.
	 *
	 * @param comparator the comparator to determine the order of this array. A null value indicates
	 *                   that the elements' natural ordering should be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract void parallelSort(T comparator);

	/**
	 * Assign each element of this array to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the {@code function} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	public abstract void setAll(O function);

	/**
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract void sort(T comparator);

	/**
	 * An array iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayIterator
			extends
			ArrayIterator
			implements
			PrimitiveIterator<E, C> {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArrayIterator(int beginThumb) {
			super(beginThumb);
		}
	}

	/**
	 * An array list specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayList
			extends
			ArrayList
			implements
			PrimitiveList<E, C, D, I, L, U, P, T> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 418303433386295161L;

		@Override
		public void forEach(C consumer) {
			PrimitiveArray.this.forEach(consumer);
		}

		@Override
		public boolean removeIf(P predicate) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public void sort(T comparator) {
			PrimitiveArray.this.sort(comparator);
		}

		@Override
		public abstract PrimitiveArrayList clone();

		@Override
		public abstract PrimitiveArrayIterator iterator();

		@Override
		public abstract PrimitiveArrayListIterator listIterator(int index);

		@Override
		public abstract PrimitiveArrayListIterator listIterator();

		@Override
		public abstract PrimitiveArraySpliterator spliterator();
	}

	/**
	 * An array list iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayListIterator
			extends
			ArrayListIterator
			implements
			PrimitiveListIterator<E, C> {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArrayListIterator(int beginThumb) {
			super(beginThumb);
		}
	}

	/**
	 * An array map specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayMap
			extends
			ArrayMap<E, E>
			implements PrimitiveMap<E, E, R, B> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1133073948012271653L;

		@Override
		public abstract PrimitiveArrayMap clone();

		@Override
		public abstract PrimitiveArrayEntrySet entrySet();

		@Override
		public abstract PrimitiveArrayKeySet keySet();

		@Override
		public abstract PrimitiveArrayValues values();

		/**
		 * An array entry specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntry
				extends
				ArrayEntry
				implements
				PrimitiveEntry<E, E> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 8628913046510894541L;

			/**
			 * Construct a new entry backed by a range from {@code thumb} to {@code thumb + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the thumb to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code thumb < 0} or {@code thumb + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayEntry(int thumb) {
				super(thumb);
			}

			@Override
			public abstract PrimitiveArrayEntry clone();
		}

		/**
		 * An array entry iterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntryIterator
				extends
				ArrayEntryIterator {
			/**
			 * Construct a new iterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayEntryIterator() {
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayEntryIterator(int beginThumb) {
				super(beginThumb);
			}

			@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
			@Override
			public abstract PrimitiveArrayEntry next();
		}

		/**
		 * An array entry set specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntrySet
				extends
				ArrayEntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 8532857328866783185L;

			@Override
			public abstract PrimitiveArrayEntrySet clone();

			@Override
			public abstract PrimitiveArrayEntryIterator iterator();

			@Override
			public abstract PrimitiveArrayEntrySpliterator spliterator();
		}

		/**
		 * An array entry spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntrySpliterator
				extends
				ArrayEntrySpliterator {
			/**
			 * Construct a new spliterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayEntrySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayEntrySpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public abstract PrimitiveArrayEntrySpliterator trySplit();
		}

		/**
		 * A primitive array key iterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayKeyIterator
				extends
				ArrayKeyIterator
				implements
				PrimitiveIterator<E, C> {
			/**
			 * Construct a new iterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayKeyIterator() {
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayKeyIterator(int beginThumb) {
				super(beginThumb);
			}
		}

		/**
		 * An array key set specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayKeySet
				extends
				ArrayKeySet
				implements
				PrimitiveSet<E, C, P> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4407052114591931652L;

			@Override
			public boolean removeIf(P predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public abstract PrimitiveArrayKeySet clone();

			@Override
			public abstract PrimitiveArrayKeyIterator iterator();

			@Override
			public abstract PrimitiveArrayKeySpliterator spliterator();
		}

		/**
		 * An array key spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayKeySpliterator
				<S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				ArrayKeySpliterator
				implements
				Spliterator.OfPrimitive<E, C, S> {
			/**
			 * Construct a new spliterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayKeySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayKeySpliterator(int beginThumb) {
				super(beginThumb);
			}
		}

		/**
		 * An array value iterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayValueIterator
				extends
				ArrayValueIterator
				implements
				PrimitiveIterator<E, C> {
			/**
			 * Construct a new iterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayValueIterator() {
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayValueIterator(int beginThumb) {
				super(beginThumb);
			}
		}

		/**
		 * An array value spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayValueSpliterator
				<S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				ArrayValueSpliterator
				implements
				Spliterator.OfPrimitive<E, C, S> {
			/**
			 * Construct a new spliterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayValueSpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.09.03
			 */
			protected PrimitiveArrayValueSpliterator(int beginThumb) {
				super(beginThumb);
			}
		}

		/**
		 * An array values specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayValues
				extends
				ArrayValues
				implements
				PrimitiveCollection<E, C, P> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4299379063812585731L;

			@Override
			public boolean removeIf(P predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public abstract PrimitiveArrayValues clone();

			@Override
			public abstract PrimitiveArrayValueIterator iterator();

			@Override
			public abstract PrimitiveArrayValueSpliterator spliterator();
		}
	}

	/**
	 * An array spliterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArraySpliterator
			<S extends Spliterator.OfPrimitive<E, C, S>>
			extends
			ArraySpliterator
			implements
			Spliterator.OfPrimitive<E, C, S> {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.09.03
		 */
		protected PrimitiveArraySpliterator(int beginThumb) {
			super(beginThumb);
		}
	}
}
