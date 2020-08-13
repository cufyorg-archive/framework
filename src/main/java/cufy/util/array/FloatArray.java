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
public class FloatArray extends Array<float[], Float> {
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
	public FloatArray(int length) {
		super(new float[length]);
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public FloatArray(float[] array) {
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
	public FloatArray(float[] array, int beginIndex, int endIndex) {
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
	public FloatArray(java.util.Collection<Float> collection) {
		super(FloatArray.from(collection));
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map} using {@link
	 * #from(java.util.Map)}.
	 *
	 * @param map the map to construct a new array from to be backing the constructed array.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public FloatArray(java.util.Map<Float, Float> map) {
		super(FloatArray.from(map));
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(float[], float[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(float[] array, float[] other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				float element = other[i];
				int elementb = Float.floatToIntBits(element);
				float e = array[i];
				int eb = Float.floatToIntBits(e);

				if (elementb == eb)
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
	public static float[] from(java.util.Collection<Float> collection) {
		Objects.requireNonNull(collection, "collection");
		float[] array = new float[collection.size()];

		java.util.Iterator iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			Object element = iterator.next();

			if (element instanceof Float)
				array[i] = (float) element;
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
	public static float[] from(java.util.Map<Float, Float> map) {
		Objects.requireNonNull(map, "map");
		float[] array = new float[map.size() << 1];

		java.util.Iterator<? extends java.util.Map.Entry> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key instanceof Float && value instanceof Float) {
				array[i] = (float) key;
				array[i + 1] = (float) value;
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
	 * @see java.util.Arrays#hashCode(float[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(float[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			float e = array[i];
			hashCode = 31 * hashCode + Float.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(float[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(float[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			float e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public float[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		float[] array = new float[length];

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
	public void arraycopy(float[] array, int pos) {
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
	public int binarySearch(Float element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;
		while (low <= high) {
			int mid = low + high >>> 1;
			float midVal = this.array[mid];
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
	public FloatArray clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new FloatArray(this.array());
	}

	@Override
	public void copy(Object array, int pos) {
		Objects.requireNonNull(array, "array");

		if (array instanceof float[])
			this.arraycopy((float[]) array, pos);
		else if (array instanceof Object[])
			this.hardcopy((Object[]) array, pos);
		else
			throw new ArrayStoreException(
					"copy: type mismatch: can not copy float[] into " +
					array.getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof FloatArray) {
			//same class
			FloatArray array = (FloatArray) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					float e = this.array[j];

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
	public void fill(Float element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super Float> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			float e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public Float get(int thumb) {
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
			float e = this.array[i];

			hashCode = 31 * hashCode + Float.hashCode(e);
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
	public void parallelPrefix(BinaryOperator<Float> operator) {
		//manual
		Float[] temp = new Float[this.length()];

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			temp[j] = this.array[i];

		java.util.Arrays.parallelPrefix(temp, operator);

		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = temp[j];
	}

	@Override
	public void parallelSetAll(IntFunction<? extends Float> function) {
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
	public void set(int thumb, Float element) {
		this.array[this.index(thumb)] = element;
	}

	@Override
	public void setAll(IntFunction<? extends Float> function) {
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
	public FloatArray sub(int beginThumb, int endThumb) {
		this.range(beginThumb, endThumb);
		return new FloatArray(
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
			float e = this.array[i];

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
	 * @see java.lang.reflect.Array#getFloat(Object, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public float getFloat(int thumb) {
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
	 * @see java.lang.reflect.Array#setFloat(Object, int, float)
	 * @since 0.1.5 ~2020.08.13
	 */
	public void setFloat(int thumb, float element) {
		this.array[this.index(thumb)] = element;
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<float[], Float>.Iterator {
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
		public void forEachRemaining(Consumer<? super Float> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = FloatArray.this.endIndex;

			for (int i = index; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Float next() {
			int index = this.index;

			if (index < FloatArray.this.endIndex) {
				this.index++;

				return FloatArray.this.array[index];
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
	public class List extends Array<float[], Float>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 848985287158674978L;

		@Override
		public boolean contains(Object object) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

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

					int i = FloatArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < FloatArray.this.endIndex) {
							//still same length
							float e = FloatArray.this.array[i++];

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
		public Float get(int thumb) {
			return FloatArray.this.array[FloatArray.this.index(thumb)];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				hashCode = 31 * hashCode + Float.hashCode(e);
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				if (object != null && object.equals(e))
					return i - FloatArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = FloatArray.this.endIndex - 1;
				 i >= FloatArray.this.beginIndex; i--) {
				float e = FloatArray.this.array[i];

				if (object != null && object.equals(e))
					return i - FloatArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int beginThumb) {
			return new ListIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super Float> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<Float> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];
				float n = operator.apply(e);

				FloatArray.this.array[i] = n;
			}
		}

		@Override
		public Float set(int thumb, Float element) {
			Objects.requireNonNull(element, "element");
			int i = FloatArray.this.index(thumb);
			float old = FloatArray.this.array[i];
			FloatArray.this.array[i] = element;
			return old;
		}

		@Override
		public void sort(Comparator<? super Float> comparator) {
			//manual
			if (comparator == null)
				FloatArray.this.sort();

			Float[] temp = new Float[this.size()];
			for (int i = FloatArray.this.beginIndex, j = 0; i < FloatArray.this.endIndex; i++, j++)
				temp[j] = FloatArray.this.array[i];
			java.util.Arrays.sort(temp, comparator);
			for (int i = FloatArray.this.beginIndex, j = 0; i < FloatArray.this.endIndex; i++, j++)
				FloatArray.this.array[i] = temp[j];
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<float[], Float>.ListIterator {
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
		public void forEachRemaining(Consumer<? super Float> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = FloatArray.this.endIndex;
			this.last = FloatArray.this.endIndex - 1;

			for (int i = index; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public Float next() {
			int index = this.index;

			if (index < FloatArray.this.endIndex) {
				this.index++;
				this.last = index;

				return FloatArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public Float previous() {
			int index = this.index - 1;

			if (index >= FloatArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return FloatArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(Float element) {
			Objects.requireNonNull(element, "element");
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			FloatArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map extends Array<float[], Float>.Map<Float, Float> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2840280796050057228L;

		@Override
		public Float compute(Float key, BiFunction<? super Float, ? super Float, ? extends Float> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					float v = FloatArray.this.array[i + 1];
					Float value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					FloatArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Float computeIfAbsent(Float key, Function<? super Float, ? extends Float> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k))
					//old:notnull
					return FloatArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Float computeIfPresent(Float key, BiFunction<? super Float, ? super Float, ? extends Float> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(function, "function");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					float v = FloatArray.this.array[i + 1];
					Float value = function.apply(k, v);

					if (value == null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:notnull new:notnull
					FloatArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float v = FloatArray.this.array[i];

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

						for (int i = FloatArray.this.beginIndex;
							 i < FloatArray.this.endIndex; i += 2) {
							float k = FloatArray.this.array[i];

							if (key != null && key.equals(k)) {
								Object value = entry.getValue();
								float v = FloatArray.this.array[i + 1];

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
		public void forEach(BiConsumer<? super Float, ? super Float> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];
				float v = FloatArray.this.array[i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public Float get(Object key) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key != null && key.equals(k))
					return FloatArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public Float getOrDefault(Object key, Float defaultValue) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key != null && key.equals(k))
					return FloatArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];
				float v = FloatArray.this.array[i + 1];
				hashCode += Float.hashCode(k) ^
							Float.hashCode(v);
			}

			return hashCode;
		}

		@Override
		public KeySet keySet() {
			return new KeySet();
		}

		@Override
		public Float merge(Float key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> function) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			Objects.requireNonNull(function, "function");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					float v = FloatArray.this.array[i + 1];
					Float newValue = function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					FloatArray.this.array[i + 1] = newValue;
					return newValue;
				}
			}

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public Float put(Float key, Float value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					//old:found
					float v = FloatArray.this.array[i + 1];
					FloatArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends Float, ? extends Float> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends Float, ? extends Float> entry : map.entrySet()) {
				float key = entry.getKey();
				int keyb = Float.floatToIntBits(key);

				for (int i = FloatArray.this.beginIndex;
					 i < FloatArray.this.endIndex; i += 2) {
					float k = FloatArray.this.array[i];
					int kb = Float.floatToIntBits(k);

					if (keyb == kb) {
						float value = entry.getValue();
						FloatArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public Float putIfAbsent(Float key, Float value) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key != null && key.equals(k))
					//old:found
					return FloatArray.this.array[i + 1];
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key != null && key.equals(k)) {
					float v = FloatArray.this.array[i + 1];

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
		public boolean replace(Float key, Float oldValue, Float newValue) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(newValue, "newValue");
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					float v = FloatArray.this.array[i + 1];

					if (oldValue != null && oldValue.equals(v)) {
						//old:match
						FloatArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public Float replace(Float key, Float value) {
			Objects.requireNonNull(key, "key");
			Objects.requireNonNull(value, "value");
			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];

				if (key.equals(k)) {
					//old:match
					float v = FloatArray.this.array[i + 1];
					FloatArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> function) {
			Objects.requireNonNull(function, "function");

			for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
				float k = FloatArray.this.array[i];
				float v = FloatArray.this.array[i + 1];
				float n = function.apply(k, v);

				FloatArray.this.array[i + 1] = n;
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = FloatArray.this.beginIndex;
			while (true) {
				float k = FloatArray.this.array[i];
				float v = FloatArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= FloatArray.this.endIndex)
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
		public class Entry extends Array<float[], Float>.Map<Float, Float>.Entry {
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
					float k = FloatArray.this.array[this.index];

					if (key != null && key.equals(k)) {
						Object value = entry.getValue();
						float v = FloatArray.this.array[this.index + 1];

						return value != null && value.equals(v);
					}
				}

				return false;
			}

			@Override
			public Float getKey() {
				return FloatArray.this.array[this.index];
			}

			@Override
			public Float getValue() {
				return FloatArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				float k = FloatArray.this.array[this.index];
				float v = FloatArray.this.array[this.index + 1];
				return Float.hashCode(k) ^
					   Float.hashCode(v);
			}

			@Override
			public Float setValue(Float value) {
				Objects.requireNonNull(value, "value");
				float v = FloatArray.this.array[this.index + 1];
				FloatArray.this.array[this.index + 1] = value;
				return v;
			}

			@Override
			public String toString() {
				float k = FloatArray.this.array[this.index];
				float v = FloatArray.this.array[this.index + 1];
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
		public class EntrySet extends Array<float[], Float>.Map<Float, Float>.EntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -4823635378224028987L;

			@Override
			public boolean contains(Object object) {
				if (object instanceof java.util.Map.Entry) {
					java.util.Map.Entry entry = (java.util.Map.Entry) object;
					Object key = entry.getKey();

					for (int i = FloatArray.this.beginIndex;
						 i < FloatArray.this.endIndex; i += 2) {
						float k = FloatArray.this.array[i];

						if (key != null && key.equals(k)) {
							Object value = entry.getValue();
							float v = FloatArray.this.array[i + 1];

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

								for (int i = FloatArray.this.beginIndex;
									 i < FloatArray.this.endIndex; i += 2) {
									float k = FloatArray.this.array[i];

									if (key != null && key.equals(k)) {
										Object value = entry.getValue();
										float v = FloatArray.this.array[i + 1];

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
			public void forEach(Consumer<? super java.util.Map.Entry<Float, Float>> consumer) {
				Objects.requireNonNull(consumer, "consumer");

				int i = 0;
				int l = FloatArray.this.length();
				for (; i < l; i += 2) {
					Entry entry = new Entry(i);//trimmed index

					consumer.accept(entry);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
					float k = FloatArray.this.array[i];
					float v = FloatArray.this.array[i + 1];
					hashCode += Float.hashCode(k) ^
								Float.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super java.util.Map.Entry<Float, Float>> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				int i = 0;
				int l = FloatArray.this.length();
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
				int l = FloatArray.this.length();
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
				int l = FloatArray.this.length();
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

				int i = FloatArray.this.beginIndex;
				while (true) {
					float k = FloatArray.this.array[i];
					float v = FloatArray.this.array[i + 1];

					builder.append(k)
							.append("=")
							.append(v);

					i += 2;
					if (i >= FloatArray.this.endIndex)
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
			public class Iterator extends Array<float[], Float>.Map<Float, Float>.EntrySet.Iterator {
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
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Float, Float>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					int i = FloatArray.this.thumb(index);
					int l = FloatArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public Entry next() {
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						int i = FloatArray.this.thumb(index);
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
			public class Spliterator extends Array<float[], Float>.Map<Float, Float>.EntrySet.Spliterator {
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
				public void forEachRemaining(Consumer<? super java.util.Map.Entry<Float, Float>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					int i = 0;
					int l = FloatArray.this.length();
					for (; i < l; i += 2) {
						Entry entry = new Entry(i);//trimmed index

						consumer.accept(entry);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super java.util.Map.Entry<Float, Float>> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						int i = FloatArray.this.thumb(index);
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
		public class KeySet extends Array<float[], Float>.Map<Float, Float>.KeySet {
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
							for (int i = FloatArray.this.beginIndex;
								 i < FloatArray.this.endIndex; i += 2) {
								float k = FloatArray.this.array[i];

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
			public void forEach(Consumer<? super Float> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
					float k = FloatArray.this.array[i];

					consumer.accept(k);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
					float k = FloatArray.this.array[i];

					hashCode += Float.hashCode(k);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Float> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = FloatArray.this.beginIndex; i < FloatArray.this.endIndex; i += 2) {
					float k = FloatArray.this.array[i];

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

				for (int i = FloatArray.this.beginIndex, j = 0;
					 i < FloatArray.this.endIndex; i += 2, j++) {
					float k = FloatArray.this.array[i];

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

				for (int i = FloatArray.this.beginIndex, j = 0;
					 i < FloatArray.this.endIndex; i += 2, j++) {
					float k = FloatArray.this.array[i];

					product[j] = (T) (Float) k;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = FloatArray.this.beginIndex;
				while (true) {
					float k = FloatArray.this.array[i];

					builder.append(k);

					i += 2;
					if (i >= FloatArray.this.endIndex)
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
			public class Iterator extends Array<float[], Float>.Map<Float, Float>.KeySet.Iterator {
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
				public void forEachRemaining(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					for (int i = index; i < FloatArray.this.endIndex; i += 2) {
						float k = FloatArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public Float next() {
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						return FloatArray.this.array[index];
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
			public class Spliterator extends Array<float[], Float>.Map<Float, Float>.KeySet.Spliterator {
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
				public void forEachRemaining(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					for (int i = index; i < FloatArray.this.endIndex; i += 2) {
						float k = FloatArray.this.array[i];

						consumer.accept(k);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						float k = FloatArray.this.array[index];
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
		public class Values extends Array<float[], Float>.Map<Float, Float>.Values {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7937502933699082438L;

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(Consumer<? super Float> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = FloatArray.this.beginIndex + 1;
					 i < FloatArray.this.endIndex; i += 2) {
					float v = FloatArray.this.array[i];

					consumer.accept(v);
				}
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = FloatArray.this.beginIndex + 1;
					 i < FloatArray.this.endIndex; i += 2) {
					float v = FloatArray.this.array[i];

					hashCode += Float.hashCode(v);
				}

				return hashCode;
			}

			@Override
			public Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean removeIf(Predicate<? super Float> predicate) {
				Objects.requireNonNull(predicate, "predicate");

				for (int i = FloatArray.this.beginIndex + 1;
					 i < FloatArray.this.endIndex; i += 2) {
					float v = FloatArray.this.array[i];

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
				int length = FloatArray.this.endIndex - FloatArray.this.beginIndex >>> 1;
				Object[] product = new Object[length];

				for (int i = FloatArray.this.beginIndex + 1, j = 0;
					 i < FloatArray.this.endIndex; i += 2, j++) {
					float v = FloatArray.this.array[i];

					product[j] = v;
				}

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = FloatArray.this.endIndex - FloatArray.this.beginIndex >>> 1;
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
				else
					product[length] = null;

				for (int i = FloatArray.this.beginIndex + 1, j = 0;
					 i < FloatArray.this.endIndex; i += 2, j++) {
					float v = FloatArray.this.array[i];

					product[j] = (T) (Float) v;
				}

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = FloatArray.this.beginIndex + 1;
				while (true) {
					float v = FloatArray.this.array[i];

					builder.append(v);

					i += 2;
					if (i >= FloatArray.this.endIndex)
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
			public class Iterator extends Array<float[], Float>.Map<Float, Float>.Values.Iterator {
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
				public void forEachRemaining(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					for (int i = index + 1; i < FloatArray.this.endIndex; i += 2) {
						float v = FloatArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public Float next() {
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						return FloatArray.this.array[index + 1];
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
			public class Spliterator extends Array<float[], Float>.Map<Float, Float>.Values.Spliterator {
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
				public void forEachRemaining(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;
					this.index = FloatArray.this.endIndex;

					for (int i = index + 1; i < FloatArray.this.endIndex; i += 2) {
						float v = FloatArray.this.array[i];

						consumer.accept(v);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super Float> consumer) {
					Objects.requireNonNull(consumer, "consumer");
					int index = this.index;

					if (index < FloatArray.this.endIndex) {
						this.index += 2;

						float v = FloatArray.this.array[index + 1];
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
	public class Spliterator extends Array<float[], Float>.Spliterator {
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
		public void forEachRemaining(Consumer<? super Float> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = FloatArray.this.endIndex;

			for (int i = index; i < FloatArray.this.endIndex; i++) {
				float e = FloatArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super Float> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < FloatArray.this.endIndex) {
				this.index += 2;
				float e = FloatArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}
	}
}
