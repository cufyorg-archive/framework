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
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A holder for an array of {@link Object}s.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class BooleanArray extends Array<boolean[], Boolean> {
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
	public BooleanArray(int length) {
		super(new boolean[length]);
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public BooleanArray(boolean[] array) {
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
	public BooleanArray(boolean[] array, int beginIndex, int endIndex) {
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
	public BooleanArray(java.util.Collection<Boolean> collection) {
		super(BooleanArray.from(collection));
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map} using {@link
	 * #from(java.util.Map)}.
	 *
	 * @param map the map to construct a new array from to be backing the constructed array.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public BooleanArray(java.util.Map<Boolean, Boolean> map) {
		super(BooleanArray.from(map));
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(boolean[], boolean[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(boolean[] array, boolean[] other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				boolean element = other[i];
				boolean e = array[i];

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
	public static boolean[] from(java.util.Collection<Boolean> collection) {
		Objects.requireNonNull(collection, "collection");
		boolean[] array = new boolean[collection.size()];

		java.util.Iterator iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			Object element = iterator.next();

			if (element instanceof Boolean)
				array[i] = (boolean) element;
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
	public static boolean[] from(java.util.Map<Boolean, Boolean> map) {
		Objects.requireNonNull(map, "map");
		boolean[] array = new boolean[map.size() << 1];

		java.util.Iterator<? extends java.util.Map.Entry> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key instanceof Boolean && value instanceof Boolean) {
				array[i] = (boolean) key;
				array[i + 1] = (boolean) value;
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
	 * @see java.util.Arrays#hashCode(boolean[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(boolean[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			boolean e = array[i];
			hashCode = 31 * hashCode + Boolean.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(boolean[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(boolean[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			boolean e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public boolean[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		boolean[] array = new boolean[length];

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
	public void arraycopy(boolean[] array, int pos) {
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
	public int binarySearch(Boolean element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;
		while (low <= high) {
			int mid = low + high >>> 1;
			boolean midVal = this.array[mid];
			if (midVal == element)
				return this.thumb(mid);  // key found
			if (element)
				low = mid + 1;
			else
				high = mid - 1;
		}
		return this.thumb(-(low + 1));  // key not found.
	}

	@Override
	public BooleanArray clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new BooleanArray(this.array());
	}

	@Override
	public void copy(Object array, int pos) {
		Objects.requireNonNull(array, "array");

		if (array instanceof boolean[])
			this.arraycopy((boolean[]) array, pos);
		else if (array instanceof Object[])
			this.hardcopy((Object[]) array, pos);
		else
			throw new ArrayStoreException(
					"copy: type mismatch: can not copy boolean[] into " +
					array.getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof BooleanArray) {
			//same class
			BooleanArray array = (BooleanArray) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					boolean e = this.array[j];

					if (element.equals(e))
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
	public void fill(Boolean element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super Boolean> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			boolean e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Boolean get(int thumb) {
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
			boolean e = this.array[i];

			hashCode = 31 * hashCode + Boolean.hashCode(e);
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
	public void parallelPrefix(BinaryOperator<Boolean> operator) {
		//manual
		Boolean[] temp = new Boolean[this.length()];

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];

		java.util.Arrays.parallelPrefix(temp, operator);

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(i -> this.array[i] = function.apply(this.thumb(i)));
	}

	@Override
	public void parallelSort() {//hard manual
		Boolean[] temp = new Boolean[this.length()];
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];
		java.util.Arrays.parallelSort(temp);
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
	}

	@Override
	public void set(int thumb, Boolean element) {
		this.array[this.index(thumb)] = element;
	}

	@Override
	public void setAll(IntFunction<? extends Boolean> function) {
		Objects.requireNonNull(function, "function");
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = function.apply(j);
	}

	@Override
	public void sort() {//hard manual
		Boolean[] temp = new Boolean[this.length()];
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];
		java.util.Arrays.sort(temp);
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
	}

	@Override
	public Spliterator spliterator() {
		return new Spliterator();
	}

	@Override
	public BooleanArray sub(int beginThumb, int endThumb) {
		this.range(beginThumb, endThumb);
		return new BooleanArray(
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
			boolean e = this.array[i];

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
	 * @see java.lang.reflect.Array#getBoolean(Object, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public boolean getBoolean(int thumb) {
		return this.array[this.index(thumb)];
	}

	/**
	 * Set the element at the given {@code thumb} in this array to the given {@code element}.
	 *
	 * @param thumb   the thumb to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the
	 *                                        array.
	 * @see java.lang.reflect.Array#setBoolean(Object, int, boolean)
	 * @since 0.1.5 ~2020.08.13
	 */
	public void setBoolean(int thumb, boolean element) {
		this.array[this.index(thumb)] = element;
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<boolean[], Boolean>.Iterator {
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
		public void forEachRemaining(Consumer<? super Boolean> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = BooleanArray.this.endIndex;

			for (int i = index; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Boolean next() {
			int index = this.index;

			if (index < BooleanArray.this.endIndex) {
				this.index++;

				return BooleanArray.this.array[index];
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
	public class List extends Array<boolean[], Boolean>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		@Override
		public boolean contains(Object object) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

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

					int i = BooleanArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < BooleanArray.this.endIndex) {
							//still same length
							boolean e = BooleanArray.this.array[i++];

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
		public Boolean get(int thumb) {
			return BooleanArray.this.array[BooleanArray.this.index(thumb)];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				hashCode = 31 * hashCode + Boolean.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				if (object != null && object.equals(e))
					return i - BooleanArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = BooleanArray.this.endIndex - 1;
				 i >= BooleanArray.this.beginIndex; i--) {
				boolean e = BooleanArray.this.array[i];

				if (object != null && object.equals(e))
					return i - BooleanArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int beginThumb) {
			return new ListIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Boolean> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Boolean> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];
				boolean n = operator.apply(e);

				BooleanArray.this.array[i] = n;
			}
		}

		@Override
		public Boolean set(int thumb, Boolean element) {
			Objects.requireNonNull(element, "element");
			int i = BooleanArray.this.index(thumb);
			boolean old = BooleanArray.this.array[i];
			BooleanArray.this.array[i] = element;
			return old;
		}

		@Override
		public void sort(Comparator<? super Boolean> comparator) {//hard manual
			if (comparator == null)
				BooleanArray.this.sort();
			Boolean[] temp = new Boolean[this.size()];
			for (int i = BooleanArray.this.beginIndex, j = 0;
				 i < BooleanArray.this.endIndex; i++, j++)
				temp[j] = BooleanArray.this.array[i];
			java.util.Arrays.sort(temp, comparator);
			for (int i = BooleanArray.this.beginIndex, j = 0;
				 i < BooleanArray.this.endIndex; i++, j++)
				BooleanArray.this.array[i] = temp[j];
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<boolean[], Boolean>.ListIterator {
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
		public void forEachRemaining(Consumer<? super Boolean> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = BooleanArray.this.endIndex;
			this.last = BooleanArray.this.endIndex - 1;

			for (int i = index; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Boolean next() {
			int index = this.index;

			if (index < BooleanArray.this.endIndex) {
				this.index++;
				this.last = index;

				return BooleanArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Boolean previous() {
			int index = this.index - 1;

			if (index >= BooleanArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return BooleanArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Boolean element) {
			Objects.requireNonNull(element, "element");
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			BooleanArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map extends Array<boolean[], Boolean>.Map<Boolean, Boolean> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		@Override
		public Boolean compute(Boolean key, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					boolean v = BooleanArray.this.array[i + 1];
					Boolean value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					BooleanArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Boolean computeIfAbsent(Boolean key, Function<? super Boolean, ? extends Boolean> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k))
					//old:notnull
					return BooleanArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Boolean computeIfPresent(Boolean key, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					boolean v = BooleanArray.this.array[i + 1];
					Boolean value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					BooleanArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean v = BooleanArray.this.array[i];

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

						for (int i = BooleanArray.this.beginIndex;
							 i < BooleanArray.this.endIndex; i += 2) {
							boolean k = BooleanArray.this.array[i];

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();
								boolean v = BooleanArray.this.array[i + 1];

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
		public void forEach(BiConsumer<? super Boolean, ? super Boolean> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];
				boolean v = BooleanArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Boolean get(Object key) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key != null && key.equals(k))
					return BooleanArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Boolean getOrDefault(Object key, Boolean defaultValue) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key != null && key.equals(k))
					return BooleanArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];
				boolean v = BooleanArray.this.array[i + 1];
				hashCode += Boolean.hashCode(k) ^
							Boolean.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public KeySet keySet() {
			return new KeySet();
		}

		@Override
		public Boolean merge(Boolean key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			Objects.requireNonNull(function, "function");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					boolean v = BooleanArray.this.array[i + 1];
					Boolean newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					BooleanArray.this.array[i + 1] = newValue;
					return newValue;
				}
			}

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Boolean put(Boolean key, Boolean value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					//old:found
					boolean v = BooleanArray.this.array[i + 1];
					BooleanArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends Boolean, ? extends Boolean> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends Boolean, ? extends Boolean> entry : map.entrySet()) {
				boolean key = entry.getKey();

				for (int i = BooleanArray.this.beginIndex;
					 i < BooleanArray.this.endIndex; i += 2) {
					boolean k = BooleanArray.this.array[i];

					if (key == k) {
						boolean value = entry.getValue();
						BooleanArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Boolean putIfAbsent(Boolean key, Boolean value) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					return BooleanArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key != null && key.equals(k)) {
					boolean v = BooleanArray.this.array[i + 1];

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
		public boolean replace(Boolean key, Boolean oldValue, Boolean newValue) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(newValue, "newValue");
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					boolean v = BooleanArray.this.array[i + 1];

					if (oldValue != null && oldValue.equals(v)) {
						//old:match
						BooleanArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Boolean replace(Boolean key, Boolean value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];

				if (key.equals(k)) {
					//old:match
					boolean v = BooleanArray.this.array[i + 1];
					BooleanArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> function) {
			Objects.requireNonNull(function, "function");

			for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
				boolean k = BooleanArray.this.array[i];
				boolean v = BooleanArray.this.array[i + 1];
				boolean n = function.apply(k, v);

				BooleanArray.this.array[i + 1] = n;
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = BooleanArray.this.beginIndex;
			while (true) {
				boolean k = BooleanArray.this.array[i];
				boolean v = BooleanArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= BooleanArray.this.endIndex)
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
		public class Entry extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.Entry {
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
					boolean k = BooleanArray.this.array[this.index];

					if (key != null && key.equals(k)) {
						Object value = entry.getValue();
						boolean v = BooleanArray.this.array[this.index + 1];

						return value != null && value.equals(v);
					}
				}

				return false;
			}

			@Override
			public Boolean getKey() {
				return BooleanArray.this.array[this.index];
			}

			@Override
			public Boolean getValue() {
				return BooleanArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				boolean k = BooleanArray.this.array[this.index];
				boolean v = BooleanArray.this.array[this.index + 1];
				return Boolean.hashCode(k) ^
					   Boolean.hashCode(v);
			}

			@Override
			public Boolean setValue(Boolean value) {
				Objects.requireNonNull(value, "value");
				boolean v = BooleanArray.this.array[this.index + 1];
				BooleanArray.this.array[this.index + 1] = value;
				return v;
			}

			@Override
			public String toString() {
				boolean k = BooleanArray.this.array[this.index];
				boolean v = BooleanArray.this.array[this.index + 1];
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
		public class EntrySet extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.EntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4823635378224028987L;

			@Override
			public boolean contains(Object object) {
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();

					for (int i = BooleanArray.this.beginIndex;
						 i < BooleanArray.this.endIndex; i += 2) {
						boolean k = BooleanArray.this.array[i];

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();
							boolean v = BooleanArray.this.array[i + 1];

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

								for (int i = BooleanArray.this.beginIndex;
									 i < BooleanArray.this.endIndex; i += 2) {
									boolean k = BooleanArray.this.array[i];

									if (key != null && key.equals(k)) {
										Object value = entry.getValue();
										boolean v = BooleanArray.this.array[i + 1];

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
			public void forEach(Consumer<? super java.util.Map.Entry<Boolean, Boolean>> consumer) {
				Objects.requireNonNull(consumer, "consumer");

				int i = 0;
				int l = BooleanArray.this.length();
				for (; i < l; i += 2) {
					Entry entry = new Entry(i);//trimmed index

					consumer.accept(entry);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
					boolean k = BooleanArray.this.array[i];
					boolean v = BooleanArray.this.array[i + 1];
					hashCode += Boolean.hashCode(k) ^
								Boolean.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super java.util.Map.Entry<Boolean, Boolean>> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				int i = 0;
				int l = BooleanArray.this.length();
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
				int l = BooleanArray.this.length();
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
				int l = BooleanArray.this.length();
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

				int i = BooleanArray.this.beginIndex;
				while (true) {
					boolean k = BooleanArray.this.array[i];
					boolean v = BooleanArray.this.array[i + 1];

					builder.append(k)
							.append("=")
							.append(v);

					i += 2;
					if (i >= BooleanArray.this.endIndex)
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
			public class Iterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.EntrySet.Iterator {
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
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Boolean, Boolean>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					int i = BooleanArray.this.thumb(index);
					int l = BooleanArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public Entry next() {
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						int i = BooleanArray.this.thumb(index);
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
			public class Spliterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.EntrySet.Spliterator {
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
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Boolean, Boolean>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					int i = 0;
					int l = BooleanArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super java.util.Map.Entry<Boolean, Boolean>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						int i = BooleanArray.this.thumb(index);
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
		public class KeySet extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.KeySet {
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
							for (int i = BooleanArray.this.beginIndex;
								 i < BooleanArray.this.endIndex; i += 2) {
								boolean k = BooleanArray.this.array[i];

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
			public void forEach(Consumer<? super Boolean> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
					boolean k = BooleanArray.this.array[i];

					consumer.accept(k);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
					boolean k = BooleanArray.this.array[i];

					hashCode += Boolean.hashCode(k);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Boolean> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = BooleanArray.this.beginIndex; i < BooleanArray.this.endIndex; i += 2) {
					boolean k = BooleanArray.this.array[i];

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

				for (int i = BooleanArray.this.beginIndex, j = 0;
					 i < BooleanArray.this.endIndex; i += 2, j++) {
					boolean k = BooleanArray.this.array[i];

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

				for (int i = BooleanArray.this.beginIndex, j = 0;
					 i < BooleanArray.this.endIndex; i += 2, j++) {
					boolean k = BooleanArray.this.array[i];

					product[j] = (T) (Boolean) k;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = BooleanArray.this.beginIndex;
				while (true) {
					boolean k = BooleanArray.this.array[i];

					builder.append(k);

					i += 2;
					if (i >= BooleanArray.this.endIndex)
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
			public class Iterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.KeySet.Iterator {
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
				public void forEachRemaining(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					for (int i = index; i < BooleanArray.this.endIndex; i += 2) {
						boolean k = BooleanArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public Boolean next() {
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						return BooleanArray.this.array[index];
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
			public class Spliterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.KeySet.Spliterator {
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
				public void forEachRemaining(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					for (int i = index; i < BooleanArray.this.endIndex; i += 2) {
						boolean k = BooleanArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						boolean k = BooleanArray.this.array[index];
						consumer.accept(k);
						return true;
					}

					return false;
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
		public class Values extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.Values {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7937502933699082438L;

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(Consumer<? super Boolean> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = BooleanArray.this.beginIndex + 1;
					 i < BooleanArray.this.endIndex; i += 2) {
					boolean v = BooleanArray.this.array[i];

					consumer.accept(v);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = BooleanArray.this.beginIndex + 1;
					 i < BooleanArray.this.endIndex; i += 2) {
					boolean v = BooleanArray.this.array[i];

					hashCode += Boolean.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Boolean> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = BooleanArray.this.beginIndex + 1;
					 i < BooleanArray.this.endIndex; i += 2) {
					boolean v = BooleanArray.this.array[i];

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
				int length = BooleanArray.this.endIndex - BooleanArray.this.beginIndex >>> 1;
				Object[] product = new Object[length];

				for (int i = BooleanArray.this.beginIndex + 1, j = 0;
					 i < BooleanArray.this.endIndex; i += 2, j++) {
					boolean v = BooleanArray.this.array[i];

					product[j] = v;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = BooleanArray.this.endIndex - BooleanArray.this.beginIndex >>> 1;
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else
					product[length] = null;

				for (int i = BooleanArray.this.beginIndex + 1, j = 0;
					 i < BooleanArray.this.endIndex; i += 2, j++) {
					boolean v = BooleanArray.this.array[i];

					product[j] = (T) (Boolean) v;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = BooleanArray.this.beginIndex + 1;
				while (true) {
					boolean v = BooleanArray.this.array[i];

					builder.append(v);

					i += 2;
					if (i >= BooleanArray.this.endIndex)
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
			public class Iterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.Values.Iterator {
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
				public void forEachRemaining(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					for (int i = index + 1; i < BooleanArray.this.endIndex; i += 2) {
						boolean v = BooleanArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public Boolean next() {
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						return BooleanArray.this.array[index + 1];
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
			public class Spliterator extends Array<boolean[], Boolean>.Map<Boolean, Boolean>.Values.Spliterator {
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
				public void forEachRemaining(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = BooleanArray.this.endIndex;

					for (int i = index + 1; i < BooleanArray.this.endIndex; i += 2) {
						boolean v = BooleanArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super Boolean> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < BooleanArray.this.endIndex) {
						this.index += 2;

						boolean v = BooleanArray.this.array[index + 1];
						consumer.accept(v);
						return true;
					}

					return false;
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
	public class Spliterator extends Array<boolean[], Boolean>.Spliterator {
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
		public void forEachRemaining(Consumer<? super Boolean> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = BooleanArray.this.endIndex;

			for (int i = index; i < BooleanArray.this.endIndex; i++) {
				boolean e = BooleanArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super Boolean> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < BooleanArray.this.endIndex) {
				this.index += 2;
				boolean e = BooleanArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}
	}
}
