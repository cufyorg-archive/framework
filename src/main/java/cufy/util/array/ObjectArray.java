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
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class ObjectArray<E> extends Array<E[], E> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -407181299777988791L;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public ObjectArray(E[] array) {
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length}.
	 * @throws IllegalArgumentException       if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	public ObjectArray(E[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths,
	 * deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep
	 * 		lengths, deep elements, and deep orderings.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean deepEquals(Object[] array, Object[] other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			Object element = array[i];
			Object e = other[i];

			if (element == e || element != null && element.equals(e))
				continue;
			if (element instanceof Object[] && e instanceof Object[] &&
				ObjectArray.deepEquals((Object[]) element, (Object[]) e))
				continue;
			if (element instanceof boolean[] && e instanceof boolean[] &&
				BooleanArray.equals((boolean[]) element, (boolean[]) e))
				continue;
			if (element instanceof byte[] && e instanceof byte[] &&
				ByteArray.equals((byte[]) element, (byte[]) e))
				continue;
			if (element instanceof char[] && e instanceof char[] &&
				CharacterArray.equals((char[]) element, (char[]) e))
				continue;
			if (element instanceof double[] && e instanceof double[] &&
				DoubleArray.equals((double[]) element, (double[]) e))
				continue;
			if (element instanceof float[] && e instanceof float[] &&
				FloatArray.equals((float[]) element, (float[]) e))
				continue;
			if (element instanceof int[] && e instanceof int[] &&
				IntegerArray.equals((int[]) element, (int[]) e))
				continue;
			if (element instanceof long[] && e instanceof long[] &&
				LongArray.equals((long[]) element, (long[]) e))
				continue;
			if (element instanceof short[] && e instanceof short[] &&
				ShortArray.equals((short[]) element, (short[]) e))
				continue;

			return false;
		}

		return true;
	}

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @see java.util.Arrays#deepHashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int deepHashCode(Object[] array) {
		if (array == null)
			return 0;

		int deepHashCode = 1;
		for (int i = 0; i < array.length; i++) {
			Object element = array[i];

			if (element == null)
				deepHashCode = 31 * deepHashCode;
			if (element instanceof Object[])
				deepHashCode = 31 * deepHashCode + ObjectArray.deepHashCode((Object[]) element);
			else if (element instanceof boolean[])
				deepHashCode = 31 * deepHashCode + BooleanArray.hashCode((boolean[]) element);
			else if (element instanceof byte[])
				deepHashCode = 31 * deepHashCode + ByteArray.hashCode((byte[]) element);
			else if (element instanceof char[])
				deepHashCode = 31 * deepHashCode + CharacterArray.hashCode((char[]) element);
			else if (element instanceof double[])
				deepHashCode = 31 * deepHashCode + DoubleArray.hashCode((double[]) element);
			else if (element instanceof float[])
				deepHashCode = 31 * deepHashCode + FloatArray.hashCode((float[]) element);
			else if (element instanceof int[])
				deepHashCode = 31 * deepHashCode + IntegerArray.hashCode((int[]) element);
			else if (element instanceof long[])
				deepHashCode = 31 * deepHashCode + LongArray.hashCode((long[]) element);
			else if (element instanceof short[])
				deepHashCode = 31 * deepHashCode + ShortArray.hashCode((short[]) element);
			else
				deepHashCode = 31 * deepHashCode + element.hashCode();
		}

		return deepHashCode;
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String deepToString(Object[] array) {
		if (array == null)
			return "null";

		int length = 20 * array.length;
		if (array.length != 0 && length <= 0)
			length = Integer.MAX_VALUE;

		StringBuilder builder = new StringBuilder(length);
		ObjectArray.deepToString(array, builder, new java.util.ArrayList());
		return builder.toString();
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(Object[] array, Object[] other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			Object element = array[i];
			Object e = other[i];

			if (element == e || element != null && element.equals(e))
				continue;

			return false;
		}

		return true;
	}

	/**
	 * Get an array from the given {@code collection}.
	 *
	 * @param collection the collection to get an array from it.
	 * @return an array from the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Object[] from(java.util.Collection collection) {
		Objects.requireNonNull(collection, "collection");
		Object[] array = new Object[collection.size()];

		java.util.Iterator iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			Object element = iterator.next();

			array[i] = element;
		}

		return array;
	}

	/**
	 * Get an array from the given {@code map}.
	 *
	 * @param map the map to get an array from it.
	 * @return an array from the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static Object[] from(java.util.Map map) {
		Objects.requireNonNull(map, "map");
		Object[] array = new Object[map.size() << 1];

		java.util.Iterator<java.util.Map.Entry> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			array[i] = key;
			array[i + 1] = value;
		}

		return array;
	}

	/**
	 * Get an array from the given {@code collection}.
	 *
	 * @param collection the collection to get an array from it.
	 * @param klass      the class desired for the product array.
	 * @param <E>        the type of the elements.
	 * @return an array from the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code klass} is null.
	 * @throws ArrayStoreException  if an item in the given {@code map} can not be stored in the
	 *                              product array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <E> E[] from(java.util.Collection<? extends E> collection, Class<E[]> klass) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(klass, "klass");
		E[] array = (E[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), collection.size());

		java.util.Iterator<? extends E> iterator = collection.iterator();
		for (int i = 0; i < array.length; i++) {
			E element = iterator.next();

			array[i] = element;
		}

		return array;
	}

	/**
	 * Get an array from the given {@code map}.
	 *
	 * @param map   the map to get an array from it.
	 * @param klass the class desired for the product array.
	 * @param <E>   the type of the elements.
	 * @return an array from the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @throws ArrayStoreException  if an item in the given {@code map} can not be stored in the
	 *                              product array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <E> E[] from(java.util.Map<? extends E, ? extends E> map, Class<E[]> klass) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(klass, "klass");
		E[] array = (E[]) java.lang.reflect.Array.newInstance(klass.getComponentType(),
				map.size() << 1);

		java.util.Iterator<? extends java.util.Map.Entry<? extends E, ? extends E>> iterator = map.entrySet().iterator();
		for (int i = 0; i < array.length; i += 2) {
			java.util.Map.Entry<? extends E, ? extends E> entry = iterator.next();
			E key = entry.getKey();
			E value = entry.getValue();

			array[i] = key;
			array[i + 1] = value;
		}

		return array;
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(Object[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			Object e = array[i];
			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		}

		return hashCode;
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(Object[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			Object e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array   the array to build a string representation for it.
	 * @param builder the builder to append the string representation to.
	 * @param dejaVu  the arrays that has been seen before.
	 * @throws NullPointerException if the given {@code builder} or {@code dejaVu} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	protected static void deepToString(Object[] array, StringBuilder builder, Collection<Object[]> dejaVu) {
		Objects.requireNonNull(builder, "builder");
		Objects.requireNonNull(dejaVu, "dejaVu");

		if (array == null)
			builder.append("null");
		else if (array.length == 0)
			builder.append("[]");
		else {
			dejaVu.add(array);
			builder.append("[");
			int i = 0;
			while (true) {
				Object element = array[i];

				if (element instanceof boolean[])
					builder.append(BooleanArray.toString((boolean[]) element));
				else if (element instanceof byte[])
					builder.append(ByteArray.toString((byte[]) element));
				else if (element instanceof char[])
					builder.append(CharacterArray.toString((char[]) element));
				else if (element instanceof double[])
					builder.append(DoubleArray.toString((double[]) element));
				else if (element instanceof float[])
					builder.append(FloatArray.toString((float[]) element));
				else if (element instanceof int[])
					builder.append(IntegerArray.toString((int[]) element));
				else if (element instanceof long[])
					builder.append(LongArray.toString((long[]) element));
				else if (element instanceof short[])
					builder.append(ShortArray.toString((short[]) element));
				else if (element instanceof Object[])
					if (dejaVu.contains(element))
						builder.append("[...]");
					else
						ObjectArray.deepToString((Object[]) element, builder, dejaVu);
				else
					builder.append(element);

				i++;
				if (i >= array.length) {
					builder.append("]");
					dejaVu.remove(array);
					return;
				}

				builder.append(", ");
			}
		}
	}

	@Override
	public int all(E... elements) {
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			E element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				E e = this.array[j];

				if (element == e || element != null && element.equals(e))
					continue for0;
			}

			return i;
		}

		return -1;
	}

	@Override
	public int any(E... elements) {
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			E element = elements[i];

			for (int j = this.beginIndex; j < this.endIndex; j++) {
				E e = this.array[j];

				if (element == e || element != null && element.equals(e))
					return i;
			}
		}

		return -1;
	}

	@Override
	public E[] array(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		E[] array = (E[]) java.lang.reflect.Array.newInstance(this.array.getClass().getComponentType(), length);

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
	public <T extends E> T[] array(int length, Class<? super T[]> klass) {
		Objects.requireNonNull(klass, "klass");
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		if (!Object[].class.isAssignableFrom(klass))
			throw new IllegalArgumentException("klass");

		T[] array = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

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
	public <T> T[] array(T[] array) {
		Objects.requireNonNull(array, "array");
		int length = this.length();
		T[] product = array;

		if (array.length < length)
			product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
		else
			product[length] = null;

		System.arraycopy(
				this.array,
				this.beginIndex,
				product,
				0,
				length
		);

		return product;
	}

	@Override
	public void arraycopy(E[] array, int pos) {
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
	public int binarySearch(E element) {
		int low = this.beginIndex;
		int high = this.endIndex - 1;

		while (low <= high) {
			int mid = low + high >>> 1;
			Comparable midVal = (Comparable) this.array[mid];
			int cmp = midVal.compareTo(element);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return this.lowerIndex(mid); // key found
		}

		return this.lowerIndex(-(low + 1));  // key not found.
	}

	@Override
	public boolean contains(Object element) {
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			E e = this.array[i];

			if (element == e || element != null && element.equals(e))
				return true;
		}

		return false;
	}

	@Override
	public <K extends E, V extends E> Entry<K, V> entry(int index) {
		return new Entry(index);
	}

	@Override
	public <K extends E, V extends E> EntrySet<K, V> entrySet() {
		return new EntrySet();
	}

	@Override
	public boolean equals(Object object) {
		//DO NOT ever think validating an actual array!
		if (object == this)
			//same identity
			return true;
		if (object instanceof ObjectArray) {
			//compatible
			ObjectArray<?> array = (ObjectArray<?>) object;

			if (array.length() == this.length()) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					Object element = array.array[i];
					E e = this.array[j];

					if (element == e || element != null && element.equals(e))
						continue;

					return false;
				}

				//all elements equal
				return true;
			}
		}

		//not equal
		return false;
	}

	@Override
	public void fill(E element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++) {
			E e = this.array[i];

			consumer.accept(e);
		}
	}

	@Override
	public E get(int index) {
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
			if (this.array == array && this.beginIndex < pos && this.endIndex > pos) {
				//for the source, here is the map
				//the first part: beginIndex:this.beginIndex endIndex:pos
				//the second part: beginIndex:pos endIndex:this.endIndex

				//for the destination, it is
				//much easier to just remember the position
				int j = pos;

				//transfer the first part directly
				for (int i = this.beginIndex; i < pos; i++, j++)
					array[j] = this.array[i];

				//temporarily copy the second part
				E[] clone = (E[]) new Object[this.endIndex - pos];
				System.arraycopy(this.array, pos, clone, 0, clone.length);

				//transfer the second part using a copy of it
				for (int i = 0; i < clone.length; i++, j++)
					array[j] = clone[i];

				return;
			}

			//if the arrays are not the same
			//Or, if the srcPos is after the destPos
			//Or, if the srcPos is behind the destPos but will never reach it.
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
			E e = this.array[i];

			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		}

		return hashCode;
	}

	@Override
	public Iterator iterator() {
		return new Iterator();
	}

	@Override
	public <K extends E> KeySet<K> keySet() {
		return new KeySet();
	}

	@Override
	public List list() {
		return new List();
	}

	@Override
	public <K extends E, V extends E> Map<K, V> map() {
		return new Map();
	}

	@Override
	public void parallelPrefix(BinaryOperator<E> operator) {
		//manual
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator
		);
	}

	@Override
	public void parallelSetAll(IntFunction<? extends E> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(i -> this.array[i] = function.apply(this.lowerIndex(i)));
	}

	@Override
	public void parallelSort() {
		//manual
		java.util.Arrays.parallelSort(
				(Comparable[]) this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public E replace(int index, E element) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		E old = this.array[i];
		this.array[i] = element;
		return old;
	}

	@Override
	public Set set() {
		return new Set();
	}

	@Override
	public void set(int index, E element) {
		this.requireIndex(index);
		int i = this.upperIndex(index);
		this.array[i] = element;
	}

	@Override
	public void setAll(IntFunction<? extends E> function) {
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
	public ObjectArray<E> sub(int beginIndex, int endIndex) {
		this.requireRange(beginIndex, endIndex);
		return new ObjectArray(
				this.array,
				this.upperIndex(beginIndex),
				this.upperIndex(endIndex)
		);
	}

	@Override
	public String toString() {
		if (this.isEmpty())
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			E e = this.array[i];

			builder.append(e);

			i++;
			if (i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	@Override
	public <V extends E> Values<V> values() {
		return new Values();
	}

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 *
	 * @param element    the value to be searched for.
	 * @param comparator the comparator by which the array is ordered. A null value indicates that
	 *                   the elements' natural ordering should be used.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(Object[], Object, Comparator)
	 * @since 0.1.5 ~2020.08.11
	 */
	public int binarySearch(E element, Comparator<? super E> comparator) {
		if (comparator == null)
			return this.binarySearch(element);

		int low = this.beginIndex;
		int high = this.endIndex - 1;

		while (low <= high) {
			int mid = low + high >>> 1;
			E midVal = this.array[mid];
			int cmp = comparator.compare(midVal, element);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return this.lowerIndex(mid); // key found
		}

		return this.lowerIndex(-(low + 1));  // key not found.
	}

	/**
	 * Manually copy all elements of this array to the given {@code array}.
	 *
	 * @param array the destination array.
	 * @param pos   the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > array.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code
	 *                                   array}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public void hardcopy(Object array, int pos) {
		Objects.requireNonNull(array, "array");

		int length = this.length();
		int arrayLength = java.lang.reflect.Array.getLength(array);

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length > arrayLength)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + arrayLength +
					")");

		try {
			if (this.array == array && this.beginIndex < pos && this.endIndex > pos) {
				//for the source, here is the map
				//the first part: beginIndex:this.beginIndex endIndex:pos
				//the second part: beginIndex:pos endIndex:this.endIndex

				//for the destination, it is
				//much easier to just remember the position
				int j = pos;

				//transfer the first part directly
				for (int i = this.beginIndex; i < pos; i++, j++)
					//it is much easier to access this.array (no problem this.array == array)
					this.array[j] = this.array[i];

				//temporarily copy the second part
				E[] clone = (E[]) new Object[this.endIndex - pos];
				System.arraycopy(this.array, pos, clone, 0, clone.length);

				//transfer the second part using a copy of it
				for (int i = 0; i < clone.length; i++, j++)
					//it is much easier to access this.array (no problem this.array == array)
					this.array[j] = clone[i];

				return;
			}

			//if the arrays are not the same
			//Or, if the srcPos is after the destPos
			//Or, if the srcPos is behind the destPos but will never reach it.
			for (int i = this.beginIndex, j = pos; i < this.endIndex; i++, j++)
				java.lang.reflect.Array.set(array, j, this.array[i]);
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	/**
	 * Sorts this array according to the order induced by the specified {@code comparator}. All
	 * elements in this array must be mutually comparable by the specified {@code comparator} (that
	 * is, c.compare(e1, e2) must not throw a {@link ClassCastException} for any elements e1 and e2
	 * in the array). This sort is guaranteed to be stable: equal elements will not be reordered as
	 * a result of the sort.
	 *
	 * @param comparator the comparator to determine the order of this array. A null value indicates
	 *                   that the elements' natural ordering should be used.
	 * @see java.util.Arrays#parallelSort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.11
	 */
	public void parallelSort(Comparator<? super E> comparator) {
		//manual
		java.util.Arrays.parallelSort(
				this.array,
				this.beginIndex,
				this.endIndex,
				comparator
		);
	}

	/**
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @see java.util.Arrays#sort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.06
	 */
	public void sort(Comparator<? super E> comparator) {
		//manual
		java.util.Arrays.sort(
				this.array,
				this.beginIndex,
				this.endIndex,
				comparator
		);
	}

	/**
	 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Entry<K extends E, V extends E> extends Array<E[], E>.Entry<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7098093446276533400L;

		/**
		 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in the
		 * enclosing array.
		 *
		 * @param index the index to where the key (followed by the value) will be in the
		 *              constructed entry.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
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
				K k = (K) ObjectArray.this.array[this.index];

				if (key == k || key != null && key.equals(k)) {
					Object value = entry.getValue();
					V v = (V) ObjectArray.this.array[this.index + 1];

					return value == v || value != null && value.equals(v);
				}
			}

			return false;
		}

		@Override
		public K getKey() {
			return (K) ObjectArray.this.array[this.index];
		}

		@Override
		public V getValue() {
			return (V) ObjectArray.this.array[this.index + 1];
		}

		@Override
		public int hashCode() {
			K k = (K) ObjectArray.this.array[this.index];
			V v = (V) ObjectArray.this.array[this.index + 1];
			return (k == null ? 0 : k.hashCode()) ^
				   (v == null ? 0 : v.hashCode());
		}

		@Override
		public V setValue(V value) {
			V v = (V) ObjectArray.this.array[this.index + 1];
			ObjectArray.this.array[this.index + 1] = value;
			return v;
		}

		@Override
		public String toString() {
			K k = (K) ObjectArray.this.array[this.index];
			V v = (V) ObjectArray.this.array[this.index + 1];
			return k + "=" + v;
		}
	}

	/**
	 * An iterator iterating the entries in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class EntryIterator<K extends E, V extends E> extends Array<E[], E>.EntryIterator<K, V> {
		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntryIterator() {
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntryIterator(int index) {
			super(index);
		}
	}

	/**
	 * A set backed by the entries in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class EntrySet<K extends E, V extends E> extends Array<E[], E>.EntrySet<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1358655666106432532L;

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
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

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k)) {
						Object value = entry.getValue();
						V v = (V) ObjectArray.this.array[i + 1];

						if (value == v || value != null && value.equals(v))
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

							for (int i = ObjectArray.this.beginIndex;
								 i < ObjectArray.this.endIndex; i += 2) {
								K k = (K) ObjectArray.this.array[i];

								if (key == k || key != null && key.equals(k)) {
									Object value = entry.getValue();
									V v = (V) ObjectArray.this.array[i + 1];

									if (value == v || value != null && value.equals(v))
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
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];
				hashCode += (k == null ? 0 : k.hashCode()) ^
							(v == null ? 0 : v.hashCode());
			}

			return hashCode;
		}

		@Override
		public EntryIterator<K, V> iterator() {
			return new EntryIterator();
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				for (Object object : collection)
					if (object instanceof java.util.Map.Entry) {
						java.util.Map.Entry entry = (java.util.Map.Entry) object;
						Object key = entry.getKey();

						if (key == k || key != null && key.equals(k)) {
							Object value = entry.getValue();

							if (value == v || value != null && value.equals(v))
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
		public EntrySpliterator<K, V> spliterator() {
			return new EntrySpliterator();
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A spliterator iterating the entries in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class EntrySpliterator<K extends E, V extends E> extends Array<E[], E>.EntrySpliterator<K, V> {
		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntrySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public EntrySpliterator(int index) {
			super(index);
		}
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Iterator extends Array<E[], E>.Iterator {
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
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public E next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index++;

				return ObjectArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * An iterator iterating the keys in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class KeyIterator<K extends E> extends Array<E[], E>.KeyIterator<K> {
		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeyIterator() {
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeyIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super K> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public K next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index += 2;

				return (K) ObjectArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A set backed by the keys in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class KeySet<K extends E> extends Array<E[], E>.KeySet<K> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 6047336320717832956L;

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public KeySet() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (object == k || object != null && object.equals(k))
					return true;
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
					for (Object key : set) {
						for (int i = ObjectArray.this.beginIndex;
							 i < ObjectArray.this.endIndex; i += 2) {
							K k = (K) ObjectArray.this.array[i];

							if (key == k || key != null && key.equals(k))
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
		public void forEach(Consumer<? super K> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				hashCode += k == null ? 0 : k.hashCode();
			}

			return hashCode;
		}

		@Override
		public KeyIterator<K> iterator() {
			return new KeyIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super K> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

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
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				for (Object key : collection)
					if (key == k || key != null && key.equals(k))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public KeySpliterator<K> spliterator() {
			return new KeySpliterator();
		}

		@Override
		public Object[] toArray() {
			Object[] product = new Object[this.size()];

			for (int i = ObjectArray.this.beginIndex, j = 0;
				 i < ObjectArray.this.endIndex; i += 2, j++) {
				K k = (K) ObjectArray.this.array[i];

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

			for (int i = ObjectArray.this.beginIndex, j = 0;
				 i < ObjectArray.this.endIndex; i += 2, j++) {
				K k = (K) ObjectArray.this.array[i];

				product[j] = (T) k;
			}

			return product;
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				K k = (K) ObjectArray.this.array[i];

				builder.append(k);

				i += 2;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A spliterator iterating the keys in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class KeySpliterator<K extends E> extends Array<E[], E>.KeySpliterator<K> {
		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public KeySpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super K> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				consumer.accept(k);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super K> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index += 2;

				K k = (K) ObjectArray.this.array[index];
				consumer.accept(k);
				return true;
			}

			return false;
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class List extends Array<E[], E>.List {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6821775231116953767L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public List() {
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

					int i = ObjectArray.this.beginIndex;
					for (Object element : list) {
						//for each element

						if (i < ObjectArray.this.endIndex) {
							//still same length
							E e = ObjectArray.this.array[i++];

							if (element == e || element != null && element.equals(e))
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
		public int hashCode() {
			int hashCode = 1;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					return ObjectArray.this.lowerIndex(i);
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = ObjectArray.this.endIndex - 1; i >= ObjectArray.this.beginIndex; i--) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					return ObjectArray.this.lowerIndex(i);
			}

			return -1;
		}

		@Override
		public ListIterator listIterator(int index) {
			return new ListIterator(index);
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			Objects.requireNonNull(predicate, "predicate");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (predicate.test(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				ObjectArray.this.array[i] = operator.apply(e);
			}
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				for (Object element : collection)
					if (element == e || element != null && element.equals(e))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			//manual
			java.util.Arrays.sort(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					ObjectArray.this.endIndex,
					comparator
			);
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ListIterator extends Array<E[], E>.ListIterator {
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
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;
			this.last = ObjectArray.this.endIndex - 1;

			for (int i = index; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public E next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index++;
				this.last = index;

				return ObjectArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public E previous() {
			int index = this.index - 1;

			if (index >= ObjectArray.this.beginIndex) {
				this.index--;
				this.last = index;

				return ObjectArray.this.array[index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(E element) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			ObjectArray.this.array[index] = element;
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Map<K extends E, V extends E> extends Array<E[], E>.Map<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1924164470541987638L;

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
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];
					V value = function.apply(k, v);

					if (value == null && v != null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					ObjectArray.this.array[i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (v == null) {
						//old:null
						V value = function.apply(k);
						ObjectArray.this.array[i + 1] = value;
						return value;
					}

					//old:notnull
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (v != null) {
						V value = function.apply(k, v);

						if (value == null)
							//old:notnull new:null
							throw new UnsupportedOperationException("remove");

						//old:notnull new:notnull
						ObjectArray.this.array[i + 1] = value;
						return value;
					}

					//old:null
					return null;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				if (value == v || value != null && value.equals(v))
					return true;
			}

			return false;
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

						for (int i = ObjectArray.this.beginIndex;
							 i < ObjectArray.this.endIndex; i += 2) {
							K k = (K) ObjectArray.this.array[i];

							if (key == k || key != null && key.equals(k)) {
								Object value = entry.getValue();
								V v = (V) ObjectArray.this.array[i + 1];

								if (value == v || value != null && value.equals(v))
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
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				consumer.accept(k, v);
			}
		}

		@Override
		public V get(Object key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					return (V) ObjectArray.this.array[i + 1];
			}

			return null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					return (V) ObjectArray.this.array[i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];
				hashCode += (k == null ? 0 : k.hashCode()) ^
							(v == null ? 0 : v.hashCode());
			}

			return hashCode;
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];
					V newValue = v == null ? value : function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					ObjectArray.this.array[i + 1] = newValue;
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
		public V put(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					//old:found
					V v = (V) ObjectArray.this.array[i + 1];
					ObjectArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (java.util.Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
				K key = entry.getKey();

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k)) {
						V value = entry.getValue();
						ObjectArray.this.array[i + 1] = value;
						continue for0;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public V putIfAbsent(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					//old:found
					V v = (V) ObjectArray.this.array[i + 1];

					if (v == null)
						ObjectArray.this.array[i + 1] = value;

					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (value == v || value != null && value.equals(v))
						//old:match
						throw new UnsupportedOperationException("remove");

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (oldValue == v || oldValue != null && oldValue.equals(v)) {
						//old:match
						ObjectArray.this.array[i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public V replace(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					//old:match
					V v = (V) ObjectArray.this.array[i + 1];
					ObjectArray.this.array[i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				ObjectArray.this.array[i + 1] = function.apply(k, v);
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				builder.append(k)
						.append("=")
						.append(v);

				i += 2;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("}")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A set backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class Set extends Array<E[], E>.Set {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8265570214297191438L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Set() {
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof java.util.Set) {
				//same class
				java.util.Set set = (java.util.Set) object;

				if (set.size() == this.size()) {
					//same length

					for0:
					for (Object element : set) {
						//for each element

						for (int i = ObjectArray.this.beginIndex;
							 i < ObjectArray.this.endIndex; i++) {
							E e = ObjectArray.this.array[i];

							if (element == e || element != null && element.equals(e))
								continue for0;
						}

						return false;
					}

					//all elements equal
					return true;
				}
			}

			//not equal
			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				hashCode += e == null ? 0 : e.hashCode();
			}

			return hashCode;
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (predicate.test(e))
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
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				for (Object element : collection)
					if (element == e || element != null && element.equals(e))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class Spliterator extends Array<E[], E>.Spliterator {
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
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index += 2;
				E e = ObjectArray.this.array[index];

				consumer.accept(e);
				return true;
			}

			return false;
		}
	}

	/**
	 * An iterator iterating the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class ValueIterator<V extends E> extends Array<E[], E>.ValueIterator<V> {
		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueIterator() {
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array, starting from the
		 * given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueIterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public V next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index += 2;

				return (V) ObjectArray.this.array[index + 1];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A spliterator iterating the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class ValueSpliterator<V extends E> extends Array<E[], E>.ValueSpliterator<V> {
		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueSpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ValueSpliterator(int index) {
			super(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;

			for (int i = index + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index += 2;

				V v = (V) ObjectArray.this.array[index + 1];
				consumer.accept(v);
				return true;
			}

			return false;
		}
	}

	/**
	 * A collection backed by the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class Values<V extends E> extends Array<E[], E>.Values<V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6890162958501631510L;

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public Values() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				if (object == v || object != null && object.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean equals(Object object) {
			return object == this;
		}

		@Override
		public void forEach(Consumer<? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				consumer.accept(v);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				hashCode += v == null ? 0 : v.hashCode();
			}

			return hashCode;
		}

		@Override
		public ValueIterator<V> iterator() {
			return new ValueIterator();
		}

		@Override
		public boolean removeIf(Predicate<? super V> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

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
			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				for (Object value : collection)
					if (value == v || value != null && value.equals(v))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public ValueSpliterator<V> spliterator() {
			return new ValueSpliterator();
		}

		@Override
		public Object[] toArray() {
			Object[] product = new Object[this.size()];

			for (int i = ObjectArray.this.beginIndex + 1, j = 0;
				 i < ObjectArray.this.endIndex; i += 2, j++) {
				V v = (V) ObjectArray.this.array[i];

				product[j] = v;
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

			for (int i = ObjectArray.this.beginIndex + 1, j = 0;
				 i < ObjectArray.this.endIndex; i += 2, j++) {
				V v = (V) ObjectArray.this.array[i];

				product[j] = (T) v;
			}

			return product;
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = ObjectArray.this.beginIndex + 1;
			while (true) {
				V v = (V) ObjectArray.this.array[i];

				builder.append(v);

				i += 2;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}
}
