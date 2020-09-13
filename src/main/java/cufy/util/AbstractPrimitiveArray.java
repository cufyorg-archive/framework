package cufy.util;

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
public abstract class AbstractPrimitiveArray
		<A, E, C, R, O, D, I, L, U, B, P, T extends PrimitiveComparator<E, D, I, L, U, T>>
		extends
		AbstractArray<A, E>
		implements
		PrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -34642923193977244L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	protected AbstractPrimitiveArray(A array) {
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
	 * @since 0.1.5 ~2020.08.05
	 */
	protected AbstractPrimitiveArray(A array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	@Override
	public abstract AbstractPrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> clone();

	@Override
	public abstract AbstractPrimitiveArrayIterator iterator();

	@Override
	public abstract AbstractPrimitiveArrayList list();

	@Override
	public abstract AbstractPrimitiveArrayListIterator listIterator();

	@Override
	public abstract AbstractPrimitiveArrayMap map();

	@Override
	public abstract AbstractPrimitiveArraySpliterator spliterator();

	@Override
	public abstract AbstractPrimitiveArray<A, E, C, R, O, D, I, L, U, B, P, T> sub(int beginThumb, int endThumb);

	/**
	 * An array iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class AbstractPrimitiveArrayIterator
			extends
			AbstractArrayIterator
			implements
			PrimitiveArrayIterator<E, C> {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArrayIterator(int beginThumb) {
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
	public abstract class AbstractPrimitiveArrayList
			extends
			AbstractArrayList
			implements
			PrimitiveArrayList<E, C, D, I, L, U, P, T> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1724226230312629400L;

		@Override
		public void forEach(C consumer) {
			AbstractPrimitiveArray.this.forEach(consumer);
		}

		@Override
		public void sort(T comparator) {
			AbstractPrimitiveArray.this.sort(comparator);
		}

		@Override
		public abstract AbstractPrimitiveArrayList clone();

		@Override
		public abstract AbstractPrimitiveArrayIterator iterator();

		@Override
		public abstract AbstractPrimitiveArrayListIterator listIterator();

		@Override
		public abstract AbstractPrimitiveArrayListIterator listIterator(int beginThumb);

		@Override
		public abstract AbstractPrimitiveArraySpliterator spliterator();
	}

	/**
	 * An array list iterator specialized for primitive values.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class AbstractPrimitiveArrayListIterator
			extends
			AbstractArrayListIterator
			implements
			PrimitiveArrayListIterator<E, C> {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArrayListIterator(int beginThumb) {
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
	public abstract class AbstractPrimitiveArrayMap
			extends
			AbstractArrayMap<E, E>
			implements
			PrimitiveArrayMap<E, C, R, B, P> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4122079841255100420L;

		@Override
		public abstract AbstractPrimitiveArrayEntrySet entrySet();

		@Override
		public abstract AbstractPrimitiveArrayKeySet keySet();

		@Override
		public abstract AbstractPrimitiveArrayValues values();

		/**
		 * An array entry specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class AbstractPrimitiveArrayEntry
				extends
				AbstractArrayEntry
				implements
				PrimitiveArrayEntry<E> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -2094074057417992544L;

			/**
			 * Construct a new entry backed by a range from {@code thumb} to {@code thumb + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the thumb to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code thumb < 0} or {@code thumb + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayEntry(int thumb) {
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
		public abstract class AbstractPrimitiveArrayEntryIterator
				extends
				AbstractArrayEntryIterator
				implements
				PrimitiveArrayEntryIterator<E> {
			/**
			 * Construct a new iterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayEntryIterator() {
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayEntryIterator(int beginThumb) {
				super(beginThumb);
			}

			@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
			@Override
			public abstract AbstractPrimitiveArrayEntry next();
		}

		/**
		 * An array entry set specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class AbstractPrimitiveArrayEntrySet
				extends
				AbstractArrayEntrySet
				implements
				PrimitiveArrayEntrySet<E> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 6615182231692559451L;

			@Override
			public abstract AbstractPrimitiveArrayEntryIterator iterator();

			@Override
			public abstract AbstractPrimitiveArrayEntrySpliterator spliterator();
		}

		/**
		 * An array entry spliterator specialized for primitive values.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class AbstractPrimitiveArrayEntrySpliterator
				extends
				AbstractArrayEntrySpliterator
				implements
				PrimitiveArrayEntrySpliterator<E> {
			/**
			 * Construct a new spliterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayEntrySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayEntrySpliterator(int beginThumb) {
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
		public abstract class AbstractPrimitiveArrayKeyIterator
				extends
				AbstractArrayKeyIterator
				implements
				PrimitiveArrayKeyIterator<E, C> {
			/**
			 * Construct a new iterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayKeyIterator() {
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayKeyIterator(int beginThumb) {
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
		public abstract class AbstractPrimitiveArrayKeySet
				extends
				AbstractArrayKeySet
				implements
				PrimitiveArrayKeySet<E, C, P> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 8070846428602355913L;

			@Override
			public abstract AbstractPrimitiveArrayKeyIterator iterator();

			@Override
			public abstract AbstractPrimitiveArrayKeySpliterator spliterator();
		}

		/**
		 * An array key spliterator specialized for primitive values.
		 *
		 * @param <S> the type of the spliterator.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class AbstractPrimitiveArrayKeySpliterator
				<S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				AbstractArrayKeySpliterator
				implements
				PrimitiveArrayKeySpliterator<E, C, S> {
			/**
			 * Construct a new spliterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayKeySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayKeySpliterator(int beginThumb) {
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
		public abstract class AbstractPrimitiveArrayValueIterator
				extends
				AbstractArrayValueIterator
				implements
				PrimitiveArrayValueIterator<E, C> {
			/**
			 * Construct a new iterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayValueIterator() {
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayValueIterator(int beginThumb) {
				super(beginThumb);
			}
		}

		/**
		 * An array value spliterator specialized for primitive values.
		 *
		 * @param <S> the type of the spliterator.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.09.03
		 */
		public abstract class AbstractPrimitiveArrayValueSpliterator
				<S extends Spliterator.OfPrimitive<E, C, S>>
				extends
				AbstractArrayValueSpliterator
				implements
				PrimitiveArrayValueSpliterator<E, C, S> {
			/**
			 * Construct a new spliterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayValueSpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected AbstractPrimitiveArrayValueSpliterator(int beginThumb) {
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
		public abstract class AbstractPrimitiveArrayValues
				extends
				AbstractArrayValues
				implements
				PrimitiveArrayValues<E, C, P> {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 3968204735183358515L;

			@Override
			public abstract AbstractPrimitiveArrayValueIterator iterator();

			@Override
			public abstract AbstractPrimitiveArrayValueSpliterator spliterator();
		}
	}

	/**
	 * An array spliterator specialized for primitive values.
	 *
	 * @param <S> the type of the spliterator.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.03
	 */
	public abstract class AbstractPrimitiveArraySpliterator
			<S extends Spliterator.OfPrimitive<E, C, S>>
			extends
			AbstractArraySpliterator
			implements
			PrimitiveArraySpliterator<E, C, S> {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected AbstractPrimitiveArraySpliterator(int beginThumb) {
			super(beginThumb);
		}
	}
}
