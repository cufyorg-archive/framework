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
package cufy.util.array;

import cufy.util.Objects;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

/**
 * A holder for an array of {@link Object}s.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class LongArray extends Array<long[], Long> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3201994039505608491L;

	/**
	 * Construct a new array backed by a new actual array that have the given {@code length}.
	 *
	 * @param length the length of the new actual array backing the construct array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public LongArray(int length) {
		super(new long[length]);
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public LongArray(long[] array) {
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
	public LongArray(long[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Construct a new array backed by a new array from the given {@code collection} using {@link
	 * #from(Collection)}.
	 *
	 * @param collection the collection to construct a new array from to be backing the constructed
	 *                   array.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public LongArray(java.util.Collection<Long> collection) {
		super(LongArray.from(collection));
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map} using {@link
	 * #from(java.util.Map)}.
	 *
	 * @param map the map to construct a new array from to be backing the constructed array.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public LongArray(java.util.Map<Long, Long> map) {
		super(LongArray.from(map));
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(long[], long[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(long[] array, long[] other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				long element = other[i];
				long e = array[i];

				if (element == e)
					continue;

				return false;
			}

		return false;
	}

	/**
	 * Get an array from the given {@code collection}.
	 *
	 * @param collection the collection to get an array from it.
	 * @return an array from the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @throws ArrayStoreException  if an item in the given {@code collection} can not be stored in
	 *                              the product array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static long[] from(java.util.Collection<Long> collection) {
		Objects.requireNonNull(collection, "collection");
		long[] array = new long[collection.size()];

		java.util.Iterator iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			Object element = iterator.next();

			if (element instanceof Long)
				array[i] = (long) element;
			else
				throw new ArrayStoreException();
		}

		return array;
	}

	/**
	 * Get an array from the given {@code map}.
	 *
	 * @param map the map to get an array from it.
	 * @return an array from the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @throws ArrayStoreException  if an item in the given {@code map} can not be stored in the
	 *                              product array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static long[] from(java.util.Map<Long, Long> map) {
		Objects.requireNonNull(map, "map");
		long[] array = new long[map.size() << 1];

		java.util.Iterator<? extends java.util.Map.Entry> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key instanceof Long && value instanceof Long) {
				array[i] = (long) key;
				array[i + 1] = (long) value;
			} else
				throw new ArrayStoreException();
		}

		return array;
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(long[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(long[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			long e = array[i];
			hashCode = 31 * hashCode + Long.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(long[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(long[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			long e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public long[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		long[] array = new long[length];

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				Math.min(this.length(), length)
		);

		return array;
	}

	@Override
	public void arraycopy(long[] array, int pos) {
		Objects.requireNonNull(array, "array");

		int length = this.length();

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length < array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")");

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				pos,
				length
		);
	}

	@Override
	public int binarySearch(Long element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;

		while (low <= high) {
			int mid = low + high >>> 1;
			long midVal = this.array[mid];

			if (midVal < element)
				low = mid + 1;
			else if (midVal > element)
				high = mid - 1;
			else
				return this.thumb(mid); // key found
		}
		return this.thumb(-(low + 1));  // key not found.
	}

	@Override
	public LongArray clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new LongArray(this.array());
	}

	@Override
	public void copy(Object array, int pos) {
		Objects.requireNonNull(array, "array");

		if (array instanceof long[])
			this.arraycopy((long[]) array, pos);
		else if (array instanceof Object[])
			this.hardcopy((Object[]) array, pos);
		else
			throw new ArrayStoreException(
					"copy: type mismatch: can not copy long[] into " +
					array.getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof LongArray) {
			//same class
			LongArray array = (LongArray) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					long e = this.array[j];

					if (this.eq(element, e))
						continue;

					return false;
				}

				//all elements are equal
				return true;
			}
		}

		//not equal
		return false;
	}

	@Override
	public void fill(Long element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super Long> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			long e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Long get(int thumb) {
		return this.array[this.index(thumb)];
	}

	@Override
	public void hardcopy(Object[] array, int pos) {
		Objects.requireNonNull(array, "array");

		int length = this.length();

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length > array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")");

		try {
			for (int i = this.beginIndex, j = pos; i < this.endIndex; i++, j++)
				array[j] = this.array[i];
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = this.beginIndex; i < this.endIndex; i++) {
			long e = this.array[i];

			hashCode = 31 * hashCode + Long.hashCode(e);
		}

		return hashCode;
	}

	@Override
	public Iterator iterator() {
		return new Iterator();
	}

	@Override
	public List list() {
		return new List();
	}

	@Override
	public ListIterator listIterator() {
		return new ListIterator();
	}

	@Override
	public Map map() {
		return new Map();
	}

	@Override
	public void parallelPrefix(BinaryOperator<Long> operator) {
		//manual
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator::apply
		);
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Long> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(i -> this.array[i] = function.apply(this.thumb(i)));
	}

	@Override
	public void parallelSort() {
		//manual
		java.util.Arrays.parallelSort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public void set(int thumb, Long element) {
		this.array[this.index(thumb)] = element;
	}

	@Override
	public void setAll(IntFunction<? extends Long> function) {
		Objects.requireNonNull(function, "function");
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = function.apply(j);
	}

	@Override
	public void sort() {
		//manual
		java.util.Arrays.sort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public Spliterator spliterator() {
		return new Spliterator();
	}

	@Override
	public LongArray sub(int beginThumb, int endThumb) {
		this.range(beginThumb, endThumb);
		return new LongArray(
				this.array,
				this.beginIndex + beginThumb,
				this.beginIndex + endThumb
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.beginIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			long e = this.array[i];

			builder.append(e);

			i++;
			if (i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	/**
	 * Get the element at the given {@code thumb} in this array.
	 *
	 * @param thumb the thumb to get the element from.
	 * @return the element at the given {@code thumb} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @see java.lang.reflect.Array#getLong(Object, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public long getLong(int thumb) {
		return this.array[this.index(thumb)];
	}

	/**
	 * Get a {@link LongStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(long[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public LongStream longStream() {
		return StreamSupport.longStream(this.spliterator(), false);
	}

	/**
	 * Get a parallel {@link LongStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(long[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public LongStream parallelLongStream() {
		return StreamSupport.longStream(this.spliterator(), true);
	}

	/**
	 * Set the element at the given {@code thumb} in this array to the given {@code element}.
	 *
	 * @param thumb   the thumb to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the
	 *                                        array.
	 * @see java.lang.reflect.Array#setLong(Object, int, long)
	 * @since 0.1.5 ~2020.08.13
	 */
	public void setLong(int thumb, long element) {
		this.array[this.index(thumb)] = element;
	}

	/**
	 * Determine if the given two elements are equal or not. This is the base equality check (for
	 * object-primitive) in this class and it should be for its subclasses.
	 *
	 * @param element the first element.
	 * @param e       the second element.
	 * @return true, if the given {@code element} equals the given {@code e} in this class's
	 * 		standard.
	 * @since 0.1.5 ~2020.08.18
	 */
	protected boolean eq(Object element, long e) {
		return element != null && element.equals(e);
	}

	/**
	 * Determine if the given two elements are equal or not. This is the base equality check (for
	 * primitive-primitive) in this class and it should be for its subclasses.
	 *
	 * @param element the first element.
	 * @param e       the second element.
	 * @return true, if the given {@code element} equals the given {@code e} in this class's
	 * 		standard.
	 * @since 0.1.5 ~2020.08.18
	 */
	protected boolean eq(long element, long e) {
		return element == e;
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<long[], Long>.Iterator implements PrimitiveIterator.OfLong {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public Iterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Iterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super Long> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = LongArray.this.endIndex;

			for (int i = index; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(LongConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = LongArray.this.endIndex;

			for (int i = index; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Long next() {
			int index = this.index;

			if (index < LongArray.this.endIndex) {
				this.index++;

				return LongArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public long nextLong() {
			int index = this.index;

			if (index < LongArray.this.endIndex) {
				this.index++;

				return LongArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class List extends Array<long[], Long>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		@Override
		public boolean contains(Object object) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				if (LongArray.this.eq(object, e))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof java.util.List) {
				//same class
				java.util.List list = (java.util.List) object;

				if (list.size() == this.size()) {
					//same length

					int i = LongArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < LongArray.this.endIndex) {
							//still same length
							long e = LongArray.this.array[i++];

							if (LongArray.this.eq(element, e))
								continue;
						}

						return false;
					}

					//all elements are equal
					return true;
				}
			}

			//not equal
			return false;
		}

		@Override
		public Long get(int thumb) {
			return LongArray.this.array[LongArray.this.index(thumb)];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				hashCode = 31 * hashCode + Long.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				if (LongArray.this.eq(object, e))
					return i - LongArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = LongArray.this.endIndex - 1;
				 i >= LongArray.this.beginIndex; i--) {
				long e = LongArray.this.array[i];

				if (LongArray.this.eq(object, e))
					return i - LongArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int beginThumb) {
			return new ListIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Long> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Long> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];
				long n = operator.apply(e);

				LongArray.this.array[i] = n;
			}
		}

		@Override
		public Long set(int thumb, Long element) {
			Objects.requireNonNull(element, "element");
			int i = LongArray.this.index(thumb);
			long old = LongArray.this.array[i];
			LongArray.this.array[i] = element;
			return old;
		}

		@Override
		public void sort(Comparator<? super Long> comparator) {
			//manual
			if (comparator == null)
				LongArray.this.sort();

			Long[] temp = new Long[this.size()];

			for (int i = LongArray.this.beginIndex, j = 0; i < LongArray.this.endIndex; i++, j++)
				temp[j] = LongArray.this.array[i];

			java.util.Arrays.sort(temp, comparator);

			for (int i = LongArray.this.beginIndex, j = 0; i < LongArray.this.endIndex; i++, j++)
				LongArray.this.array[i] = temp[j];
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<long[], Long>.ListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ListIterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super Long> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = LongArray.this.endIndex;
			this.last = LongArray.this.endIndex - 1;

			for (int i = index; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Long next() {
			int index = this.index;

			if (index < LongArray.this.endIndex) {
				this.index++;
				this.last = index;

				return LongArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Long previous() {
			int index = this.index - 1;

			if (index >= LongArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return LongArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Long element) {
			Objects.requireNonNull(element, "element");
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			LongArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map extends Array<long[], Long>.Map<Long, Long> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		@Override
		public Long compute(Long key, BiFunction<? super Long, ? super Long, ? extends Long> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					long v = LongArray.this.array[i + 1];
					Long value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					LongArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Long computeIfAbsent(Long key, Function<? super Long, ? extends Long> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k))
					//old:notnull
					return LongArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Long computeIfPresent(Long key, BiFunction<? super Long, ? super Long, ? extends Long> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					long v = LongArray.this.array[i + 1];
					Long value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					LongArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long v = LongArray.this.array[i];

				if (LongArray.this.eq(value, v))
					return true;
			}

			return false;
		}

		@Override
		public EntrySet entrySet() {
			return new EntrySet();
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Map) {
				java.util.Map<?, ?> map = (java.util.Map) object;

				if (map.size() == this.size()) {
					for0:
					for (java.util.Map.Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = LongArray.this.beginIndex;
							 i < LongArray.this.endIndex; i += 2) {
							long k = LongArray.this.array[i];

							if (LongArray.this.eq(key, k)) {
								Object value = entry.getValue();
								long v = LongArray.this.array[i + 1];

								if (LongArray.this.eq(value, v))
									continue for0;

								break;
							}
						}

						return false;
					}

					return true;
				}
			}

			return false;
		}

		@Override
		public void forEach(BiConsumer<? super Long, ? super Long> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];
				long v = LongArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Long get(Object key) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k))
					return LongArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Long getOrDefault(Object key, Long defaultValue) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k))
					return LongArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];
				long v = LongArray.this.array[i + 1];
				hashCode += Long.hashCode(k) ^
							Long.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public KeySet keySet() {
			return new KeySet();
		}

		@Override
		public Long merge(Long key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			Objects.requireNonNull(function, "function");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					long v = LongArray.this.array[i + 1];
					Long newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					LongArray.this.array[i + 1] = newValue;
					return newValue;
				}
			}

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Long put(Long key, Long value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					//old:found
					long v = LongArray.this.array[i + 1];
					LongArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends Long, ? extends Long> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends Long, ? extends Long> entry : map.entrySet()) {
				long key = entry.getKey();

				for (int i = LongArray.this.beginIndex;
					 i < LongArray.this.endIndex; i += 2) {
					long k = LongArray.this.array[i];

					if (LongArray.this.eq(key, k)) {
						long value = entry.getValue();
						LongArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Long putIfAbsent(Long key, Long value) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k))
					//old:found
					return LongArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					long v = LongArray.this.array[i + 1];

					if (LongArray.this.eq(value, v))
						//old:match
						throw new UnsupportedOperationException("remove");

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public boolean replace(Long key, Long oldValue, Long newValue) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(newValue, "newValue");
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					long v = LongArray.this.array[i + 1];

					if (LongArray.this.eq(oldValue, v)) {
						//old:match
						LongArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Long replace(Long key, Long value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];

				if (LongArray.this.eq(key, k)) {
					//old:match
					long v = LongArray.this.array[i + 1];
					LongArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Long, ? super Long, ? extends Long> function) {
			Objects.requireNonNull(function, "function");

			for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
				long k = LongArray.this.array[i];
				long v = LongArray.this.array[i + 1];
				long n = function.apply(k, v);

				LongArray.this.array[i + 1] = n;
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = LongArray.this.beginIndex;
			while (true) {
				long k = LongArray.this.array[i];
				long v = LongArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= LongArray.this.endIndex)
					return builder.append("}")
							.toString();

				builder.append(", ");
			}
		}

		@Override
		public Values values() {
			return new Values();
		}

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class Entry extends Array<long[], Long>.Map<Long, Long>.Entry {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 5973497615323125824L;

			/**
			 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the index to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public Entry(int thumb) {
				super(thumb);
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();
					long k = LongArray.this.array[this.index];

					if (LongArray.this.eq(key, k)) {
						Object value = entry.getValue();
						long v = LongArray.this.array[this.index + 1];

						return LongArray.this.eq(value, v);
					}
				}

				return false;
			}

			@Override
			public Long getKey() {
				return LongArray.this.array[this.index];
			}

			@Override
			public Long getValue() {
				return LongArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				long k = LongArray.this.array[this.index];
				long v = LongArray.this.array[this.index + 1];
				return Long.hashCode(k) ^
					   Long.hashCode(v);
			}

			@Override
			public Long setValue(Long value) {
				Objects.requireNonNull(value, "value");
				long v = LongArray.this.array[this.index + 1];
				LongArray.this.array[this.index + 1] = value;
				return v;
			}

			@Override
			public String toString() {
				long k = LongArray.this.array[this.index];
				long v = LongArray.this.array[this.index + 1];
				return k + "=" + v;
			}
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class EntrySet extends Array<long[], Long>.Map<Long, Long>.EntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4823635378224028987L;

			@Override
			public boolean contains(Object object) {
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();

					for (int i = LongArray.this.beginIndex;
						 i < LongArray.this.endIndex; i += 2) {
						long k = LongArray.this.array[i];

						if (LongArray.this.eq(key, k)) {
							Object value = entry.getValue();
							long v = LongArray.this.array[i + 1];

							if (LongArray.this.eq(value, v))
								return true;

							break;
						}
					}
				}

				return false;
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof java.util.Set) {
					java.util.Set set = (java.util.Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object object1 : set) {
							if (object1 instanceof java.util.Map.Entry) {
								java.util.Map.Entry entry = (java.util.Map.Entry) object1;
								Object key = entry.getKey();

								for (int i = LongArray.this.beginIndex;
									 i < LongArray.this.endIndex; i += 2) {
									long k = LongArray.this.array[i];

									if (LongArray.this.eq(key, k)) {
										Object value = entry.getValue();
										long v = LongArray.this.array[i + 1];

										if (LongArray.this.eq(value, v))
											continue for0;

										break;
									}
								}
							}

							return false;
						}

						return true;
					}
				}

				return false;
			}

			@Override
			public void forEach(Consumer<? super java.util.Map.Entry<Long, Long>> consumer) {
				Objects.requireNonNull(consumer, "consumer");

				int i = 0;
				int l = LongArray.this.length();
				for (; i < l; i += 2) {
					Entry entry = new Entry(i);//trimmed index

					consumer.accept(entry);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
					long k = LongArray.this.array[i];
					long v = LongArray.this.array[i + 1];
					hashCode += Long.hashCode(k) ^
								Long.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super java.util.Map.Entry<Long, Long>> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				int i = 0;
				int l = LongArray.this.length();
				for (; i < l; i += 2) {
					Entry entry = new Entry(i); //trimmed index

					if (predicate.test(entry))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

				//no match
				return false;
			}

			@Override
			public Spliterator spliterator() {
				return new Spliterator();
			}

			@Override
			public Object[] toArray() {
				int length = this.size();
				Object[] product = new Object[length];

				int i = 0;
				int l = LongArray.this.length();
				for (int j = 0; i < l; i += 2, j++) {
					Entry entry = new Entry(i);//trimmed index

					product[j] = entry;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else if (array.length > length)
					product[length] = null;

				//should trim the index for the entry creation
				int i = 0;
				int l = LongArray.this.length();
				for (int j = 0; i < l; i += 2, j++) {
					Entry entry = new Entry(i);//trimmed index

					product[j] = (T) entry;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = LongArray.this.beginIndex;
				while (true) {
					long k = LongArray.this.array[i];
					long v = LongArray.this.array[i + 1];

					builder.append(k)
							.append("=")
							.append(v);

					i += 2;
					if (i >= LongArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}

			/**
			 * An iterator iterating the entries in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public class Iterator extends Array<long[], Long>.Map<Long, Long>.EntrySet.Iterator {
				/**
				 * Construct a new iterator iterating the entries in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator() {
				}

				/**
				 * Construct a new iterator iterating the entries in the enclosing array, starting
				 * from the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Long, Long>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					int i = LongArray.this.thumb(index);
					int l = LongArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public Entry next() {
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						int i = LongArray.this.thumb(index);
						return new Entry(i);//trimmed index
					}

					throw new NoSuchElementException();
				}
			}

			/**
			 * A spliterator iterating the entries in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public class Spliterator extends Array<long[], Long>.Map<Long, Long>.EntrySet.Spliterator {
				/**
				 * Construct a new spliterator iterating the entries in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator() {
				}

				/**
				 * Construct a new spliterator iterating the entries in the enclosing array,
				 * starting from the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Long, Long>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					int i = 0;
					int l = LongArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super java.util.Map.Entry<Long, Long>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						int i = LongArray.this.thumb(index);
						Entry entry = new Entry(i);//trimmed index
						consumer.accept(entry);
						return true;
					}

					return false;
				}
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class KeySet extends Array<long[], Long>.Map<Long, Long>.KeySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 7793360078444812816L;

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof java.util.Set) {
					java.util.Set set = (java.util.Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object key : set) {
							for (int i = LongArray.this.beginIndex;
								 i < LongArray.this.endIndex; i += 2) {
								long k = LongArray.this.array[i];

								if (LongArray.this.eq(key, k))
									continue for0;
							}

							return false;
						}

						return true;
					}
				}

				return false;
			}

			@Override
			public void forEach(Consumer<? super Long> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
					long k = LongArray.this.array[i];

					consumer.accept(k);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
					long k = LongArray.this.array[i];

					hashCode += Long.hashCode(k);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Long> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = LongArray.this.beginIndex; i < LongArray.this.endIndex; i += 2) {
					long k = LongArray.this.array[i];

					if (predicate.test(k))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

				//nothing to remove
				return false;
			}

			@Override
			public Spliterator spliterator() {
				return new Spliterator();
			}

			@Override
			public Object[] toArray() {
				int length = this.size();
				Object[] product = new Object[length];

				for (int i = LongArray.this.beginIndex, j = 0;
					 i < LongArray.this.endIndex; i += 2, j++) {
					long k = LongArray.this.array[i];

					product[j] = k;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else if (array.length > length)
					product[length] = null;

				for (int i = LongArray.this.beginIndex, j = 0;
					 i < LongArray.this.endIndex; i += 2, j++) {
					long k = LongArray.this.array[i];

					product[j] = (T) (Long) k;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = LongArray.this.beginIndex;
				while (true) {
					long k = LongArray.this.array[i];

					builder.append(k);

					i += 2;
					if (i >= LongArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}

			/**
			 * An iterator iterating the keys in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public class Iterator extends Array<long[], Long>.Map<Long, Long>.KeySet.Iterator implements PrimitiveIterator.OfLong {
				/**
				 * Construct a new iterator iterating the keys in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator() {
				}

				/**
				 * Construct a new iterator iterating the keys in the enclosing array, starting from
				 * the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index; i < LongArray.this.endIndex; i += 2) {
						long k = LongArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public void forEachRemaining(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index; i < LongArray.this.endIndex; i += 2) {
						long k = LongArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public Long next() {
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						return LongArray.this.array[index];
					}

					throw new NoSuchElementException();
				}

				@Override
				public long nextLong() {
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						return LongArray.this.array[index];
					}

					throw new NoSuchElementException();
				}
			}

			/**
			 * A spliterator iterating the keys in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public class Spliterator extends Array<long[], Long>.Map<Long, Long>.KeySet.Spliterator implements java.util.Spliterator.OfLong {
				/**
				 * Construct a new spliterator iterating the keys in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator() {
				}

				/**
				 * Construct a new spliterator iterating the keys in the enclosing array, starting
				 * from the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index; i < LongArray.this.endIndex; i += 2) {
						long k = LongArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public void forEachRemaining(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index; i < LongArray.this.endIndex; i += 2) {
						long k = LongArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public boolean tryAdvance(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						long k = LongArray.this.array[index];
						consumer.accept(k);
						return true;
					}

					return false;
				}

				@Override
				public boolean tryAdvance(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						long k = LongArray.this.array[index];
						consumer.accept(k);
						return true;
					}

					return false;
				}

				@Override
				public Spliterator trySplit() {
					return (Spliterator) super.trySplit();
				}
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class Values extends Array<long[], Long>.Map<Long, Long>.Values {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7937502933699082438L;

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(Consumer<? super Long> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = LongArray.this.beginIndex + 1;
					 i < LongArray.this.endIndex; i += 2) {
					long v = LongArray.this.array[i];

					consumer.accept(v);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = LongArray.this.beginIndex + 1;
					 i < LongArray.this.endIndex; i += 2) {
					long v = LongArray.this.array[i];

					hashCode += Long.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Long> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = LongArray.this.beginIndex + 1;
					 i < LongArray.this.endIndex; i += 2) {
					long v = LongArray.this.array[i];

					if (predicate.test(v))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

				//nothing to remove
				return false;
			}

			@Override
			public Spliterator spliterator() {
				return new Spliterator();
			}

			@Override
			public Object[] toArray() {
				int length = LongArray.this.endIndex - LongArray.this.beginIndex >>> 1;
				Object[] product = new Object[length];

				for (int i = LongArray.this.beginIndex + 1, j = 0;
					 i < LongArray.this.endIndex; i += 2, j++) {
					long v = LongArray.this.array[i];

					product[j] = v;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = LongArray.this.endIndex - LongArray.this.beginIndex >>> 1;
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else if (array.length > length)
					product[length] = null;

				for (int i = LongArray.this.beginIndex + 1, j = 0;
					 i < LongArray.this.endIndex; i += 2, j++) {
					long v = LongArray.this.array[i];

					product[j] = (T) (Long) v;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = LongArray.this.beginIndex + 1;
				while (true) {
					long v = LongArray.this.array[i];

					builder.append(v);

					i += 2;
					if (i >= LongArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}

			/**
			 * An iterator iterating the values in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public class Iterator extends Array<long[], Long>.Map<Long, Long>.Values.Iterator implements PrimitiveIterator.OfLong {
				/**
				 * Construct a new iterator iterating the values in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator() {
				}

				/**
				 * Construct a new iterator iterating the values in the enclosing array, starting
				 * from the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index + 1; i < LongArray.this.endIndex; i += 2) {
						long v = LongArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public void forEachRemaining(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index + 1; i < LongArray.this.endIndex; i += 2) {
						long v = LongArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public Long next() {
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						return LongArray.this.array[index + 1];
					}

					throw new NoSuchElementException();
				}

				@Override
				public long nextLong() {
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						return LongArray.this.array[index + 1];
					}

					throw new NoSuchElementException();
				}
			}

			/**
			 * A spliterator iterating the values in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public class Spliterator extends Array<long[], Long>.Map<Long, Long>.Values.Spliterator implements java.util.Spliterator.OfLong {
				/**
				 * Construct a new spliterator iterating the values in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator() {
				}

				/**
				 * Construct a new spliterator iterating the values in the enclosing array, starting
				 * from the given {@code index}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int beginThumb) {
					super(beginThumb);
				}

				@Override
				public void forEachRemaining(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index + 1; i < LongArray.this.endIndex; i += 2) {
						long v = LongArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public void forEachRemaining(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = LongArray.this.endIndex;

					for (int i = index + 1; i < LongArray.this.endIndex; i += 2) {
						long v = LongArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public boolean tryAdvance(LongConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						long v = LongArray.this.array[index + 1];
						consumer.accept(v);
						return true;
					}

					return false;
				}

				@Override
				public boolean tryAdvance(Consumer<? super Long> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < LongArray.this.endIndex) {
						this.index += 2;

						long v = LongArray.this.array[index + 1];
						consumer.accept(v);
						return true;
					}

					return false;
				}

				@Override
				public Spliterator trySplit() {
					return (Spliterator) super.trySplit();
				}
			}
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class Spliterator extends Array<long[], Long>.Spliterator implements java.util.Spliterator.OfLong {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public Spliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Spliterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super Long> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = LongArray.this.endIndex;

			for (int i = index; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(LongConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = LongArray.this.endIndex;

			for (int i = index; i < LongArray.this.endIndex; i++) {
				long e = LongArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(LongConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < LongArray.this.endIndex) {
				this.index += 2;
				long e = LongArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Long> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < LongArray.this.endIndex) {
				this.index += 2;
				long e = LongArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator trySplit() {
			return (Spliterator) super.trySplit();
		}
	}
}
