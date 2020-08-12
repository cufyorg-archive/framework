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
import java.util.stream.StreamSupport;

/**
 * A holder for an array of {@link Object}s.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class IntegerArray extends Array<int[], Integer> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3201994039505608491L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public IntegerArray(int... array) {
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
	public IntegerArray(int[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map} using {@link
	 * #from(java.util.Map)}.
	 *
	 * @param map the map to construct a new array from to be backing the constructed array.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public IntegerArray(java.util.Map map) {
		super(IntegerArray.from(map));
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
	public IntegerArray(java.util.Collection collection) {
		super(IntegerArray.from(collection));
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(int[], int[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(int[] array, int[] other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				int element = other[i];
				int e = array[i];

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
	public static int[] from(java.util.Collection collection) {
		Objects.requireNonNull(collection, "collection");
		int[] array = new int[collection.size()];

		java.util.Iterator iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			Object element = iterator.next();

			if (element instanceof Integer)
				array[i] = (int) element;
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
	public static int[] from(java.util.Map map) {
		Objects.requireNonNull(map, "map");
		int[] array = new int[map.size() << 1];

		java.util.Iterator<java.util.Map.Entry> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key instanceof Integer && value instanceof Integer) {
				array[i] = (int) key;
				array[i + 1] = (int) value;
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
	 * @see java.util.Arrays#hashCode(int[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(int[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			int e = array[i];
			hashCode = 31 * hashCode + Integer.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(int[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(int[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			int e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public int all(int[] elements) {
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			int element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				int e = this.array[j];

				if (element == e)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	@Override
	public int all(Integer... elements) {
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			Integer element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				int e = this.array[j];

				if (element != null && element.equals(e))
					continue for0;
			}

			return i;
		}

		return -1;
	}

	@Override
	public int any(int[] elements) {
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			int element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				int e = this.array[j];

				if (element == e)
					return i;
			}
		}

		return -1;
	}

	@Override
	public int any(Integer... elements) {
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			Integer element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				int e = this.array[j];

				if (element != null && element.equals(e))
					return i;
			}
		}

		return -1;
	}

	@Override
	public int[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		int[] array = new int[length];

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
	public <T extends Integer> T[] array(int length, Class<? super T[]> klass) {
		Objects.requireNonNull(klass, "klass");
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		if (Object[].class.isAssignableFrom(klass))
			throw new IllegalArgumentException("klass");

		T[] array = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		this.sub(0, Math.min(this.length(), length))
				.hardcopy(array, 0);

		return array;
	}

	@Override
	public <T> T[] array(T[] array) {
		Objects.requireNonNull(array, "array");
		int length = this.length();
		T[] product = array;

		if (array.length < length)
			product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
		else
			product[length] = null;

		this.sub(0, Math.min(this.length(), length))
				.hardcopy(array, 0);

		return product;
	}

	@Override
	public void arraycopy(int[] array, int pos) {
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
	public int binarySearch(Integer element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;

		while (low <= high) {
			int mid = low + high >>> 1;
			int midVal = this.array[mid];

			if (midVal < element)
				low = mid + 1;
			else if (midVal > element)
				high = mid - 1;
			else
				return this.lowerIndex(mid); // key found
		}
		return this.lowerIndex(-(low + 1));  // key not found.
	}

	@Override
	public IntegerArray clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new IntegerArray(this.array());
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof IntegerArray) {
			//same class
			IntegerArray array = (IntegerArray) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					int e = this.array[j];

					if (element != null && element.equals(e))
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
	public void fill(Integer element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super Integer> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			int e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Integer get(int index) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		return this.array[i];
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
			int e = this.array[i];

			hashCode = 31 * hashCode + Integer.hashCode(e);
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
	public Map map() {
		return new Map();
	}

	@Override
	public void parallelPrefix(BinaryOperator<Integer> operator) {
		//manual
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator::apply
		);
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Integer> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(i -> this.array[i] = function.apply(this.lowerIndex(i)));
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
	public void set(int index, Integer element) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		this.array[i] = element;
	}

	@Override
	public void setAll(IntFunction<? extends Integer> function) {
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
	public IntegerArray sub(int beginIndex, int endIndex) {
		this.requireRange(beginIndex, endIndex);
		return new IntegerArray(
				this.array,
				this.upperIndex(beginIndex),
				this.upperIndex(endIndex)
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.beginIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			int e = this.array[i];

			builder.append(e);

			i++;
			if (i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	/**
	 * Get a {@link IntStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(int[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public IntStream intStream() {
		return StreamSupport.intStream(this.spliterator(), false);
	}

	/**
	 * Get a parallel {@link IntStream} streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(int[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public IntStream parallelIntStream() {
		return StreamSupport.intStream(this.spliterator(), true);
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<int[], Integer>.Iterator implements PrimitiveIterator.OfInt {
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
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Iterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Integer> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = IntegerArray.this.endIndex;

			for (int i = index; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(IntConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = IntegerArray.this.endIndex;

			for (int i = index; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Integer next() {
			int index = this.index;

			if (index < IntegerArray.this.endIndex) {
				this.index++;

				return IntegerArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public int nextInt() {
			int index = this.index;

			if (index < IntegerArray.this.endIndex) {
				this.index++;

				return IntegerArray.this.array[index];
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
	public class List extends Array<int[], Integer>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public List() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				if (object != null && object.equals(e))
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

					int i = IntegerArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < IntegerArray.this.endIndex) {
							//still same length
							int e = IntegerArray.this.array[i++];

							if (element != null && element.equals(e))
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
		public Integer get(int index) {
			IntegerArray.this.requireIndex(index);
			int i = IntegerArray.this.upperIndex(index);
			return IntegerArray.this.array[i];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				hashCode = 31 * hashCode + Integer.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				if (object != null && object.equals(e))
					return i - IntegerArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = IntegerArray.this.endIndex - 1;
				 i >= IntegerArray.this.beginIndex; i--) {
				int e = IntegerArray.this.array[i];

				if (object != null && object.equals(e))
					return i - IntegerArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int index) {
			return new ListIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Integer> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Integer> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				IntegerArray.this.array[i] = operator.apply(e);
			}
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				for (Object element : collection)
					if (element != null && element.equals(e))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public Integer set(int index, Integer element) {
			IntegerArray.this.requireIndex(index);
			int i = IntegerArray.this.upperIndex(index);
			int old = IntegerArray.this.array[i];
			IntegerArray.this.array[i] = element;
			return old;
		}

		@Override
		public void sort(Comparator<? super Integer> comparator) {
			//manual
			if (comparator == null)
				IntegerArray.this.sort();

			Integer[] temp = new Integer[this.size()];

			for (int i = IntegerArray.this.beginIndex, j = 0;
				 i < IntegerArray.this.endIndex; i++, j++)
				temp[j] = IntegerArray.this.array[i];
			java.util.Arrays.sort(temp, comparator);
			for (int i = IntegerArray.this.beginIndex, j = 0;
				 i < IntegerArray.this.endIndex; i++, j++)
				IntegerArray.this.array[i] = temp[j];
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<int[], Integer>.ListIterator {
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
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ListIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Integer> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = IntegerArray.this.endIndex;
			this.last = IntegerArray.this.endIndex - 1;

			for (int i = index; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Integer next() {
			int index = this.index;

			if (index < IntegerArray.this.endIndex) {
				this.index++;
				this.last = index;

				return IntegerArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Integer previous() {
			int index = this.index - 1;

			if (index >= IntegerArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return IntegerArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Integer element) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			IntegerArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map extends Array<int[], Integer>.Map<Integer, Integer> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Map() {
		}

		@Override
		public Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
			Objects.requireNonNull(function, "function");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					int v = IntegerArray.this.array[i + 1];
					Integer value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					IntegerArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> function) {
			Objects.requireNonNull(function, "function");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k))
					//old:notnull
					return IntegerArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
			Objects.requireNonNull(function, "function");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					int v = IntegerArray.this.array[i + 1];
					Integer value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					IntegerArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int v = IntegerArray.this.array[i];

				if (value != null && value.equals(v))
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

						for (int i = IntegerArray.this.beginIndex;
							 i < IntegerArray.this.endIndex; i += 2) {
							int k = IntegerArray.this.array[i];

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();
								int v = IntegerArray.this.array[i + 1];

								if (value != null && value.equals(v))
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
		public void forEach(BiConsumer<? super Integer, ? super Integer> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];
				int v = IntegerArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Integer get(Object key) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k))
					return IntegerArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Integer getOrDefault(Object key, Integer defaultValue) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k))
					return IntegerArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];
				int v = IntegerArray.this.array[i + 1];
				hashCode += Integer.hashCode(k) ^
							Integer.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public KeySet keySet() {
			return new KeySet();
		}

		@Override
		public Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
			Objects.requireNonNull(function, "function");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					int v = IntegerArray.this.array[i + 1];
					Integer newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					IntegerArray.this.array[i + 1] = newValue;
					return newValue;
				}
			}

			if (value == null)
				//old:notfound new:null
				return null;

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Integer put(Integer key, Integer value) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:found
					int v = IntegerArray.this.array[i + 1];
					IntegerArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends Integer, ? extends Integer> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends Integer, ? extends Integer> entry : map.entrySet()) {
				Integer key = entry.getKey();

				for (int i = IntegerArray.this.beginIndex;
					 i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];

					if (key != null && key.equals(k)) {
						Integer value = entry.getValue();
						IntegerArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Integer putIfAbsent(Integer key, Integer value) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					return IntegerArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					int v = IntegerArray.this.array[i + 1];

					if (value != null && value.equals(v))
						//old:match
						throw new UnsupportedOperationException("remove");

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public boolean replace(Integer key, Integer oldValue, Integer newValue) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					int v = IntegerArray.this.array[i + 1];

					if (oldValue != null && oldValue.equals(v)) {
						//old:match
						IntegerArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Integer replace(Integer key, Integer value) {
			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];

				if (key != null && key.equals(k)) {
					//old:match
					int v = IntegerArray.this.array[i + 1];
					IntegerArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
			Objects.requireNonNull(function, "function");

			for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
				int k = IntegerArray.this.array[i];
				int v = IntegerArray.this.array[i + 1];

				IntegerArray.this.array[i + 1] = function.apply(k, v);
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = IntegerArray.this.beginIndex;
			while (true) {
				int k = IntegerArray.this.array[i];
				int v = IntegerArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= IntegerArray.this.endIndex)
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
		public class Entry extends Array<int[], Integer>.Map<Integer, Integer>.Entry {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 5973497615323125824L;

			/**
			 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in
			 * the enclosing array.
			 *
			 * @param index the index to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public Entry(int index) {
				super(index);
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();
					int k = IntegerArray.this.array[this.index];

					if (key != null && key.equals(k)) {
						Object value = entry.getValue();
						int v = IntegerArray.this.array[this.index + 1];

						return value != null && value.equals(v);
					}
				}

				return false;
			}

			@Override
			public Integer getKey() {
				return IntegerArray.this.array[this.index];
			}

			@Override
			public Integer getValue() {
				return IntegerArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				int k = IntegerArray.this.array[this.index];
				int v = IntegerArray.this.array[this.index + 1];
				return Integer.hashCode(k) ^
					   Integer.hashCode(v);
			}

			@Override
			public Integer setValue(Integer value) {
				int v = IntegerArray.this.array[this.index + 1];
				IntegerArray.this.array[this.index + 1] = value;
				return v;
			}

			@Override
			public String toString() {
				int k = IntegerArray.this.array[this.index];
				int v = IntegerArray.this.array[this.index + 1];
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
		public class EntrySet extends Array<int[], Integer>.Map<Integer, Integer>.EntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4823635378224028987L;

			/**
			 * Construct a new set backed by the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			@SuppressWarnings("RedundantNoArgConstructor")
			public EntrySet() {
			}

			@Override
			public boolean contains(Object object) {
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();

					for (int i = IntegerArray.this.beginIndex;
						 i < IntegerArray.this.endIndex; i += 2) {
						int k = IntegerArray.this.array[i];

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();
							int v = IntegerArray.this.array[i + 1];

							if (value != null && value.equals(v))
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

								for (int i = IntegerArray.this.beginIndex;
									 i < IntegerArray.this.endIndex; i += 2) {
									int k = IntegerArray.this.array[i];

									if (key != null && key.equals(k)) {
										Object value = entry.getValue();
										int v = IntegerArray.this.array[i + 1];

										if (value != null && value.equals(v))
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
			public void forEach(Consumer<? super java.util.Map.Entry<Integer, Integer>> consumer) {
				Objects.requireNonNull(consumer, "consumer");

				int i = 0;
				int l = IntegerArray.this.length();
				for (; i < l; i += 2) {
					Entry entry = new Entry(i);//trimmed index

					consumer.accept(entry);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];
					int v = IntegerArray.this.array[i + 1];
					hashCode += Integer.hashCode(k) ^
								Integer.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super java.util.Map.Entry<Integer, Integer>> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				int i = 0;
				int l = IntegerArray.this.length();
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
			public boolean retainAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for0:
				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];
					int v = IntegerArray.this.array[i + 1];

					for (Object object : collection)
						if (object instanceof java.util.Map.Entry) {
							java.util.Map.Entry entry = (java.util.Map.Entry) object;
							Object key = entry.getKey();

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();

								if (value != null && value.equals(v))
									//retained
									continue for0;
							}
						}

					//can not remove
					throw new UnsupportedOperationException("remove");
				}

				//all retained
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
				int l = IntegerArray.this.length();
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
				else
					product[length] = null;

				//should trim the index for the entry creation
				int i = 0;
				int l = IntegerArray.this.length();
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

				int i = IntegerArray.this.beginIndex;
				while (true) {
					int k = IntegerArray.this.array[i];
					int v = IntegerArray.this.array[i + 1];

					builder.append(k)
							.append("=")
							.append(v);

					i += 2;
					if (i >= IntegerArray.this.endIndex)
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
			public class Iterator extends Array<int[], Integer>.Map<Integer, Integer>.EntrySet.Iterator {
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
				 * @param index the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Integer, Integer>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					int i = IntegerArray.this.lowerIndex(index);
					int l = IntegerArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public Entry next() {
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int i = IntegerArray.this.lowerIndex(index);
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
			public class Spliterator extends Array<int[], Integer>.Map<Integer, Integer>.EntrySet.Spliterator {
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
				 * @param index the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Integer, Integer>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					int i = 0;
					int l = IntegerArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super java.util.Map.Entry<Integer, Integer>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int i = IntegerArray.this.lowerIndex(index);
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
		public class KeySet extends Array<int[], Integer>.Map<Integer, Integer>.KeySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 7793360078444812816L;

			/**
			 * Construct a new set backed by the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			@SuppressWarnings("RedundantNoArgConstructor")
			public KeySet() {
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof java.util.Set) {
					java.util.Set set = (java.util.Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object key : set) {
							for (int i = IntegerArray.this.beginIndex;
								 i < IntegerArray.this.endIndex; i += 2) {
								int k = IntegerArray.this.array[i];

								if (key != null && key.equals(k))
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
			public void forEach(Consumer<? super Integer> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];

					consumer.accept(k);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];

					hashCode += Integer.hashCode(k);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Integer> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];

					if (predicate.test(k))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

				//nothing to remove
				return false;
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for0:
				for (int i = IntegerArray.this.beginIndex; i < IntegerArray.this.endIndex; i += 2) {
					int k = IntegerArray.this.array[i];

					for (Object key : collection)
						if (key != null && key.equals(k))
							//retained
							continue for0;

					//can not remove
					throw new UnsupportedOperationException("remove");
				}

				//all retained
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

				for (int i = IntegerArray.this.beginIndex, j = 0;
					 i < IntegerArray.this.endIndex; i += 2, j++) {
					int k = IntegerArray.this.array[i];

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
				else
					product[length] = null;

				for (int i = IntegerArray.this.beginIndex, j = 0;
					 i < IntegerArray.this.endIndex; i += 2, j++) {
					int k = IntegerArray.this.array[i];

					product[j] = (T) (Integer) k;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = IntegerArray.this.beginIndex;
				while (true) {
					int k = IntegerArray.this.array[i];

					builder.append(k);

					i += 2;
					if (i >= IntegerArray.this.endIndex)
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
			public class Iterator extends Array<int[], Integer>.Map<Integer, Integer>.KeySet.Iterator implements PrimitiveIterator.OfInt {
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
				 * @param index the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index; i < IntegerArray.this.endIndex; i += 2) {
						int k = IntegerArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public void forEachRemaining(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index; i < IntegerArray.this.endIndex; i += 2) {
						int k = IntegerArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public Integer next() {
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						return IntegerArray.this.array[index];
					}

					throw new NoSuchElementException();
				}

				@Override
				public int nextInt() {
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						return IntegerArray.this.array[index];
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
			public class Spliterator extends Array<int[], Integer>.Map<Integer, Integer>.KeySet.Spliterator implements java.util.Spliterator.OfInt {
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
				 * @param index the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index; i < IntegerArray.this.endIndex; i += 2) {
						int k = IntegerArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public void forEachRemaining(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index; i < IntegerArray.this.endIndex; i += 2) {
						int k = IntegerArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public boolean tryAdvance(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int k = IntegerArray.this.array[index];
						consumer.accept(k);
						return true;
					}

					return false;
				}

				@Override
				public boolean tryAdvance(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int k = IntegerArray.this.array[index];
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
		public class Values extends Array<int[], Integer>.Map<Integer, Integer>.Values {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7937502933699082438L;

			/**
			 * Construct a new collection backed by the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			@SuppressWarnings("RedundantNoArgConstructor")
			public Values() {
			}

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(Consumer<? super Integer> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = IntegerArray.this.beginIndex + 1;
					 i < IntegerArray.this.endIndex; i += 2) {
					int v = IntegerArray.this.array[i];

					consumer.accept(v);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = IntegerArray.this.beginIndex + 1;
					 i < IntegerArray.this.endIndex; i += 2) {
					int v = IntegerArray.this.array[i];

					hashCode += Integer.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Integer> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = IntegerArray.this.beginIndex + 1;
					 i < IntegerArray.this.endIndex; i += 2) {
					int v = IntegerArray.this.array[i];

					if (predicate.test(v))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

				//nothing to remove
				return false;
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for0:
				for (int i = IntegerArray.this.beginIndex + 1;
					 i < IntegerArray.this.endIndex; i += 2) {
					int v = IntegerArray.this.array[i];

					for (Object value : collection)
						if (value != null && value.equals(v))
							//retained
							continue for0;

					//can not remove
					throw new UnsupportedOperationException("remove");
				}

				//all retained
				return false;
			}

			@Override
			public Spliterator spliterator() {
				return new Spliterator();
			}

			@Override
			public Object[] toArray() {
				int length = IntegerArray.this.endIndex - IntegerArray.this.beginIndex >>> 1;
				Object[] product = new Object[length];

				for (int i = IntegerArray.this.beginIndex + 1, j = 0;
					 i < IntegerArray.this.endIndex; i += 2, j++) {
					int v = IntegerArray.this.array[i];

					product[j] = v;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = IntegerArray.this.endIndex - IntegerArray.this.beginIndex >>> 1;
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else
					product[length] = null;

				for (int i = IntegerArray.this.beginIndex + 1, j = 0;
					 i < IntegerArray.this.endIndex; i += 2, j++) {
					int v = IntegerArray.this.array[i];

					product[j] = (T) (Integer) v;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = IntegerArray.this.beginIndex + 1;
				while (true) {
					int v = IntegerArray.this.array[i];

					builder.append(v);

					i += 2;
					if (i >= IntegerArray.this.endIndex)
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
			public class Iterator extends Array<int[], Integer>.Map<Integer, Integer>.Values.Iterator implements PrimitiveIterator.OfInt {
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
				 * @param index the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Iterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index + 1; i < IntegerArray.this.endIndex; i += 2) {
						int v = IntegerArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public void forEachRemaining(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index + 1; i < IntegerArray.this.endIndex; i += 2) {
						int v = IntegerArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public Integer next() {
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						return IntegerArray.this.array[index + 1];
					}

					throw new NoSuchElementException();
				}

				@Override
				public int nextInt() {
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						return IntegerArray.this.array[index + 1];
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
			public class Spliterator extends Array<int[], Integer>.Map<Integer, Integer>.Values.Spliterator implements java.util.Spliterator.OfInt {
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
				 * @param index the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >
				 *                                   length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				public Spliterator(int index) {
					super(index);
				}

				@Override
				public void forEachRemaining(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index + 1; i < IntegerArray.this.endIndex; i += 2) {
						int v = IntegerArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public void forEachRemaining(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = IntegerArray.this.endIndex;

					for (int i = index + 1; i < IntegerArray.this.endIndex; i += 2) {
						int v = IntegerArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public boolean tryAdvance(IntConsumer consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int v = IntegerArray.this.array[index + 1];
						consumer.accept(v);
						return true;
					}

					return false;
				}

				@Override
				public boolean tryAdvance(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < IntegerArray.this.endIndex) {
						this.index += 2;

						int v = IntegerArray.this.array[index + 1];
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
	public class Spliterator extends Array<int[], Integer>.Spliterator implements java.util.Spliterator.OfInt {
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
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public Spliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super Integer> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = IntegerArray.this.endIndex;

			for (int i = index; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public void forEachRemaining(IntConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = IntegerArray.this.endIndex;

			for (int i = index; i < IntegerArray.this.endIndex; i++) {
				int e = IntegerArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(IntConsumer consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < IntegerArray.this.endIndex) {
				this.index += 2;
				int e = IntegerArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Integer> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < IntegerArray.this.endIndex) {
				this.index += 2;
				int e = IntegerArray.this.array[index];

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
