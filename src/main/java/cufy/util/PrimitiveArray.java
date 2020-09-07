package cufy.util;

import cufy.lang.PrimitiveIterable;

import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;

/**
 * An array specialized for primitive values.
 *
 * @param <A>             the type of the array.
 * @param <E>             the type of the elements.
 * @param <BI_CONSUMER>   the type of the bi-consumer.
 * @param <CONSUMER>      the type of the consumer.
 * @param <PREDICATE>     the type of the predicate.
 * @param <UNARY>         the type of the unary operator.
 * @param <BINARY>        the type of the binary operator.
 * @param <INT_TO>        the type of the intTo function.
 * @param <TO_DOUBLE>     the type of the toDouble function.
 * @param <TO_INT>        the type of the toInt function.
 * @param <TO_LONG>       the type of the toLong function.
 * @param <COMPARATOR>    the type of the comparator.
 * @param <ITERATOR>      the type of the iterator.
 * @param <LIST_ITERATOR> the type of the list iterator.
 * @param <SPLITERATOR>   the type of the spliterator.
 * @param <COLLECTION>    the type of the collection.
 * @param <SET>           the type of the set.
 * @param <LIST>          the type of the list.
 * @param <MAP>           the type of the map.
 * @param <ARRAY>         the type of the wrapper array.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public abstract class PrimitiveArray<
		A,
		E,
		BI_CONSUMER,
		CONSUMER,
		PREDICATE,
		UNARY,
		BINARY,
		INT_TO,
		TO_DOUBLE,
		TO_INT,
		TO_LONG,
		COMPARATOR extends PrimitiveComparator<E, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR>,
		ITERATOR extends PrimitiveIterator<E, CONSUMER>,
		LIST_ITERATOR extends PrimitiveListIterator<E, CONSUMER>,
		SPLITERATOR extends Spliterator.OfPrimitive<E, CONSUMER, SPLITERATOR>,
		COLLECTION extends PrimitiveCollection<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION>,
		SET extends PrimitiveSet<E, CONSUMER, PREDICATE, ITERATOR, SPLITERATOR, COLLECTION, SET>,
		LIST extends PrimitiveList<E, CONSUMER, PREDICATE, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, LIST_ITERATOR, SPLITERATOR, COLLECTION, LIST>,
		MAP extends PrimitiveMap<E, E, BI_CONSUMER, BINARY, Set<Map.Entry<E, E>>, SET, COLLECTION, MAP>,
		ARRAY extends PrimitiveArray<A, E, BI_CONSUMER, CONSUMER, PREDICATE, UNARY, BINARY, INT_TO, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR, ITERATOR, LIST_ITERATOR, SPLITERATOR, COLLECTION, SET, LIST, MAP, ARRAY>
		> extends Array<A, E> implements PrimitiveIterable<E, CONSUMER, ITERATOR, SPLITERATOR> {
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

	@SuppressWarnings("CloneReturnsClassType")
	@Override
	public abstract ARRAY clone();

	@Override
	public abstract ITERATOR iterator();

	@Override
	public abstract LIST list();

	@Override
	public abstract LIST_ITERATOR listIterator();

	@Override
	public abstract MAP map();

	@Override
	public abstract SPLITERATOR spliterator();

	@Override
	public abstract ARRAY sub(int beginThumb, int endThumb);

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
	public abstract void parallelPrefix(BINARY operator);

	/**
	 * In parallel, assign each element of this array to the value returned from invoking the given
	 * {@code function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	public abstract void parallelSetAll(INT_TO function);

	/**
	 * Sorts this array according to the order induced by the specified {@code comparator}. This
	 * sort is guaranteed to be stable: equal elements will not be reordered as a result of the
	 * sort.
	 *
	 * @param comparator the comparator to determine the order of this array. A null value indicates
	 *                   that the elements' natural ordering should be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract void parallelSort(COMPARATOR comparator);

	/**
	 * Assign each element of this array to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the {@code function} is null.
	 * @since 0.1.5 ~2020.08.30
	 */
	public abstract void setAll(INT_TO function);

	/**
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract void sort(COMPARATOR comparator);

	/**
	 * An array iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayIterator extends ArrayIterator implements PrimitiveIterator<
			E,
			CONSUMER
			> {
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
	public abstract class PrimitiveArrayList extends ArrayList implements PrimitiveList<
			E,
			CONSUMER,
			PREDICATE,
			UNARY,
			TO_DOUBLE,
			TO_INT,
			TO_LONG,
			COMPARATOR,
			ITERATOR,
			LIST_ITERATOR,
			SPLITERATOR,
			COLLECTION,
			LIST
			> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 418303433386295161L;

		@Override
		public boolean addAll(COLLECTION collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean addAll(int index, COLLECTION collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void forEach(CONSUMER consumer) {
			PrimitiveArray.this.forEach(consumer);
		}

		@Override
		public boolean removeAll(COLLECTION collection) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		public boolean removeIf(PREDICATE predicate) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public boolean retainAll(COLLECTION collection) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public void sort(COMPARATOR comparator) {
			PrimitiveArray.this.sort(comparator);
		}
	}

	/**
	 * An array list iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArrayListIterator extends ArrayListIterator implements PrimitiveListIterator<
			E,
			CONSUMER
			> {
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
	public abstract class PrimitiveArrayMap extends ArrayMap<E, E> implements PrimitiveMap<
			E,
			E,
			BI_CONSUMER,
			BINARY,
			Set<Map.Entry<E, E>>,
			SET,
			COLLECTION,
			MAP
			> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1133073948012271653L;

		/**
		 * An array entry specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntry extends ArrayEntry implements PrimitiveEntry<
				E,
				E
				> {
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
		}

		/**
		 * An array entry iterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntryIterator extends ArrayEntryIterator {
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
		}

		/**
		 * An array entry set specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntrySet extends ArrayEntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 8532857328866783185L;
		}

		/**
		 * An array entry spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayEntrySpliterator extends ArrayEntrySpliterator {
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
		}

		/**
		 * A primitive array key iterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayKeyIterator extends ArrayKeyIterator implements PrimitiveIterator<
				E,
				CONSUMER
				> {
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
		public abstract class PrimitiveArrayKeySet extends ArrayKeySet implements PrimitiveSet<
				E,
				CONSUMER,
				PREDICATE,
				ITERATOR,
				SPLITERATOR,
				COLLECTION,
				SET
				> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4407052114591931652L;

			@Override
			public boolean addAll(COLLECTION collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public boolean removeAll(COLLECTION collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean removeIf(PREDICATE predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean retainAll(COLLECTION collection) {
				throw new UnsupportedOperationException("retainAll");
			}
		}

		/**
		 * An array key spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class PrimitiveArrayKeySpliterator extends ArrayKeySpliterator implements Spliterator.OfPrimitive<
				E,
				CONSUMER,
				SPLITERATOR
				> {
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
		public abstract class PrimitiveArrayValueIterator extends ArrayValueIterator implements PrimitiveIterator<
				E,
				CONSUMER
				> {
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
		public abstract class PrimitiveArrayValueSpliterator extends ArrayValueSpliterator implements Spliterator.OfPrimitive<
				E,
				CONSUMER,
				SPLITERATOR
				> {
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
		public abstract class PrimitiveArrayValues extends ArrayValues implements PrimitiveCollection<
				E,
				CONSUMER,
				PREDICATE,
				ITERATOR,
				SPLITERATOR,
				COLLECTION
				> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4299379063812585731L;

			@Override
			public boolean addAll(COLLECTION collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public boolean removeAll(COLLECTION collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean removeIf(PREDICATE predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean retainAll(COLLECTION collection) {
				throw new UnsupportedOperationException("retainAll");
			}
		}
	}

	/**
	 * An array spliterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class PrimitiveArraySpliterator extends ArraySpliterator implements Spliterator.OfPrimitive<
			E,
			CONSUMER,
			SPLITERATOR
			> {
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
