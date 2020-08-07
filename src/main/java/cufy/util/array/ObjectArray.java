package cufy.util.array;

import cufy.util.Objects;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	public ObjectArray(E... array) {
		super(array);
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range starts at the given {@code
	 * beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be backing the constructed array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex > array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	public ObjectArray(E[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @param <T>    the type of the elements.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T[] copyOf(T[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		T[] copy = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @param klass  the type of the constructed array.
	 * @param <T>    the type of the elements.
	 * @param <U>    the super type of the elements.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws IllegalArgumentException   if the given {@code klass} is not an array class. Or if the given {@code klass} is not
	 *                                    an array class. Or if the component type of the given {@code klass} is primitive.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @throws ArrayStoreException        if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T extends U, U> U[] copyOf(T[] array, int length, Class<? extends U[]> klass) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(klass, "klass");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");
		Objects.require(klass, Objects::isObjectArrayClass, "klass");

		U[] copy = (U[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given
	 * {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the index where to start copying from the original array to the constructed array.
	 * @param endIndex   the index where to stop copying from the original array to the constructed array.
	 * @param <T>        the type of the elements.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T[] copyOfRange(T[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		T[] product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, beginIndex, product, 0, Math.min(array.length - beginIndex, length));

		return product;
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given
	 * {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the index where to start copying from the original array to the constructed array.
	 * @param endIndex   the index where to stop copying from the original array to the constructed array.
	 * @param klass      the type of the constructed array.
	 * @param <T>        the type of the elements.
	 * @param <U>        the super type of the elements.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}. Or if the
	 *                                   given {@code klass} is not an array class. Or if the component type of the given {@code
	 *                                   klass} is primitive.
	 * @throws ArrayStoreException       if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T extends U, U> U[] copyOfRange(T[] array, int beginIndex, int endIndex, Class<U[]> klass) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(klass, Class::isArray, "klass");
		Objects.require(klass, Objects::isObjectArrayClass, "klass");

		int length = endIndex - beginIndex;

		T[] product = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(array, beginIndex, product, 0, Math.min(array.length - beginIndex, length));

		return product;
	}

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @param <T>   the type of the elements.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> int deepHashCode(T... array) {
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
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((boolean[]) element);
			else if (element instanceof byte[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((byte[]) element);
			else if (element instanceof char[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((char[]) element);
			else if (element instanceof double[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((double[]) element);
			else if (element instanceof float[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((float[]) element);
			else if (element instanceof int[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((int[]) element);
			else if (element instanceof long[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((long[]) element);
			else if (element instanceof short[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((short[]) element);
			else
				deepHashCode = 31 * deepHashCode + element.hashCode();
		}

		return deepHashCode;
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <T>   the type of the elements.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> String deepToString(T... array) {
		if (array == null)
			return "null";

		int cap = 20 * array.length;
		if (array.length != 0 && cap <= 0)
			cap = Integer.MAX_VALUE;

		StringBuilder builder = new StringBuilder(cap);
		ObjectArray.deepToString(array, builder, new java.util.ArrayList());
		return builder.toString();
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> boolean equals(T[] array, T... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			Object a = array[i];
			Object o = other[i];

			if (a != o && (a == null || !a.equals(o)))
				return false;
		}

		return true;
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @param <T>     the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @throws ArrayStoreException  if the given {@code element} can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void fill(T[] array, T element) {
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given {@code beginIndex} to the given
	 * {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @param <T>        the type of the elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored in the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void fill(T[] array, int beginIndex, int endIndex, T element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
			array[i] = element;
	}

	/**
	 * Copy the elements from the given {@code src} to the given {@code dest}. Start reading from the given {@code src} at the
	 * given {@code srcPos}. Start writing to the given {@code dest} at the given {@code destPos}. Copy the specified number of
	 * elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code dest}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		if (src != dest || destPos <= srcPos || srcPos + length <= destPos)
			for (int i = 0; i < length; i++)
				try {
					dest[di++] = src[si++];
				} catch (ClassCastException e) {
					throw new ArrayStoreException(e.getMessage());
				}
		else {
			Object[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				try {
					dest[di++] = src[si++];
				} catch (ClassCastException e) {
					throw new ArrayStoreException(e.getMessage());
				}
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				try {
					dest[di++] = clone[i];
				} catch (ClassCastException e) {
					throw new ArrayStoreException(e.getMessage());
				}
		}
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @param <T>   the type of the elements.
	 * @return the hash code of the elements of the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> int hashCode(T... array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			Object element = array[i];

			hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
		}

		return hashCode;
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <T>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> array[i] = function.apply(i));
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <T>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void setAll(T[] array, IntFunction<? extends T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++)
			array[i] = function.apply(i);
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <T>   the type of the elements.
	 * @return a string representation of the contents of the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> String toString(T... array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			builder.append(array[i]);

			if (++i < array.length) {
				builder.append(", ");
				continue;
			}

			return builder.append("]")
					.toString();
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

		if (array.length != 0) {
			dejaVu.add(array);
			builder.append("[");

			int i = 0;
			while (true) {
				Object element = array[i];

				if (element instanceof boolean[])
					builder.append(Arrays.toString((boolean[]) element));
				else if (element instanceof byte[])
					builder.append(Arrays.toString((byte[]) element));
				else if (element instanceof char[])
					builder.append(Arrays.toString((char[]) element));
				else if (element instanceof double[])
					builder.append(Arrays.toString((double[]) element));
				else if (element instanceof float[])
					builder.append(Arrays.toString((float[]) element));
				else if (element instanceof int[])
					builder.append(Arrays.toString((int[]) element));
				else if (element instanceof long[])
					builder.append(Arrays.toString((long[]) element));
				else if (element instanceof short[])
					builder.append(Arrays.toString((short[]) element));
				else if (element instanceof Object[])
					if (dejaVu.contains(element))
						builder.append("[...]");
					else
						ObjectArray.deepToString((Object[]) element, builder, dejaVu);
				else
					builder.append(element);

				if (++i < array.length) {
					builder.append(", ");
					continue;
				}

				builder.append("]");
				dejaVu.remove(array);
				return;
			}
		}

		builder.append("[]");
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
	public void arraycopy(E[] dest, int pos) {
		Objects.requireNonNull(dest, "dest");

		int length = this.endIndex - this.beginIndex;

		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");
		if (pos + length < dest.length)
			throw new IndexOutOfBoundsException("pos(" + pos + ") + length(" + length + ") > dest.length(" + dest.length + ")");

		System.arraycopy(
				this.array,
				this.beginIndex,
				dest,
				pos,
				length
		);
	}

	@Override
	public <K extends E> ObjectArrayKeySet<K> asKeySet() {
		return new ObjectArrayKeySet();
	}

	@Override
	public ObjectArrayList asList() {
		return new ObjectArrayList();
	}

	@Override
	public <K extends E, V extends E> ObjectArrayMap<K, V> asMap() {
		return new ObjectArrayMap();
	}

	@Override
	public ObjectArraySet asSet() {
		return new ObjectArraySet();
	}

	@Override
	public <V extends E> ObjectArrayValues<V> asValues() {
		return new ObjectArrayValues();
	}

	@Override
	public int binarySearch(E element) {
		return java.util.Arrays.binarySearch(
				this.array,
				this.beginIndex,
				this.endIndex,
				element
		);
	}

	@Override
	public int binarySearch(E element, Comparator<? super E> comparator) {
		return java.util.Arrays.binarySearch(
				this.array,
				this.beginIndex,
				this.endIndex,
				element,
				comparator
		);
	}

	@Override
	public E[] concat(E[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int l = this.endIndex - this.beginIndex;
		int length = l;

		for (E[] array : arrays)
			if (array != null)
				length += array.length;

		E[] product = (E[]) java.lang.reflect.Array.newInstance(this.array.getClass(), length);

		System.arraycopy(this.array, this.beginIndex, product, 0, l);

		for (int i = l, j = 0; j < arrays.length; j++) {
			E[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	@Override
	public boolean deepEquals(Object object) {
		if (object == this)
			//same identity
			return false;
		if (object instanceof ObjectArray) {

		}
		if (this.array == null || object == null || this.array.length != object.length)
			return false;

		for (int i = 0; i < this.array.length; i++) {
			Object element = object[i];
			Object e = this.array[i];

			if (e == element)
				continue;
			if (e == null || element == null)
				return false;

			if (e instanceof Object[] && element instanceof Object[]) {
				new ObjectArray<>()
				if (!ObjectArray.deepEquals((Object[]) e, (Object[]) element))
					return false;
			} else if (e instanceof boolean[] && element instanceof boolean[]) {
				if (!Arrays.equals((boolean[]) e, (boolean[]) element))
					return false;
			} else if (e instanceof byte[] && element instanceof byte[]) {
				if (!Arrays.equals((byte[]) e, (byte[]) element))
					return false;
			} else if (e instanceof char[] && element instanceof char[]) {
				if (!Arrays.equals((char[]) e, (char[]) element))
					return false;
			} else if (e instanceof double[] && element instanceof double[]) {
				if (!Arrays.equals((double[]) e, (double[]) element))
					return false;
			} else if (e instanceof float[] && element instanceof float[]) {
				if (!Arrays.equals((float[]) e, (float[]) element))
					return false;
			} else if (e instanceof int[] && element instanceof int[]) {
				if (!Arrays.equals((int[]) e, (int[]) element))
					return false;
			} else if (e instanceof long[] && element instanceof long[]) {
				if (!Arrays.equals((long[]) e, (long[]) element))
					return false;
			} else if (e instanceof short[] && element instanceof short[]) {
				if (!Arrays.equals((short[]) e, (short[]) element))
					return false;
			} else if (!e.equals(element))
				return false;
		}

		return true;
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntry<K, V> entryAt(int index) {
		return new ObjectArrayEntry(index);
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntryIterator<K, V> entryIterator() {
		return new ObjectArrayEntryIterator();
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntryIterator<K, V> entryIterator(int index) {
		return new ObjectArrayEntryIterator(index);
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntrySet<K, V> entrySet() {
		return new ObjectArrayEntrySet();
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntrySpliterator<K, V> entrySpliterator() {
		return new ObjectArrayEntrySpliterator();
	}

	@Override
	public <K extends E, V extends E> ObjectArrayEntrySpliterator<K, V> entrySpliterator(int index) {
		return new ObjectArrayEntrySpliterator(index);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			//same identity
			return true;
		if (object instanceof ObjectArray) {
			//same class
			ObjectArray<?> array = (ObjectArray) object;

			if (array.endIndex - array.beginIndex == this.endIndex - this.beginIndex) {
				//same length

				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					//for each element
					Object element = array.array[i];
					E e = this.array[j];

					if (element == e || element != null && element.equals(e))
						continue;

					return false;
				}

				//all elements are equal
				return true;
			}
		}
		if (object instanceof Object[]) {
			//an array :P
			Object[] array = (Object[]) object;

			if (array.length == this.endIndex - this.beginIndex) {
				//same length

				for (int i = 0, j = this.beginIndex; i < array.length; i++, j++) {
					//for each element
					Object element = array[i];
					E e = this.array[j];

					if (element == e || element != null && element.equals(e))
						continue;

					return false;
				}

				//all elements are equal
				return true;
			}
		}
		if (object instanceof ObjectArray || object instanceof Object[]) {
			Object[] array;
			int beginIndex;
			int endIndex;

			if (object instanceof ObjectArray) {
				ObjectArray<?> a = (ObjectArray) object;
				array = a.array;
				beginIndex = a.beginIndex;
				endIndex = a.endIndex;
			} else {
				Object[] a = (Object[]) object;
				beginIndex = 0;
				endIndex = a.length;
			}

			if (endIndex - beginIndex == this.endIndex - this.beginIndex) {
				//same length

				for (int i = beginIndex, j = this.beginIndex; i < endIndex; i++, j++) {

				}

				//all elemetns equal
				return true;
			}
		}

		//not equal
		return false;
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
		int i = this.beginIndex + index;
		return this.array[i];
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
	public ObjectArrayIterator iterator() {
		return new ObjectArrayIterator();
	}

	@Override
	public ObjectArrayIterator iterator(int index) {
		return new ObjectArrayIterator(index);
	}

	@Override
	public <K extends E> ObjectArrayKeyIterator<K> keyIterator() {
		return new ObjectArrayKeyIterator();
	}

	@Override
	public <K extends E> ObjectArrayKeyIterator<K> keyIterator(int index) {
		return new ObjectArrayKeyIterator(index);
	}

	@Override
	public <K extends E> ObjectArrayKeySpliterator<K> keySpliterator() {
		return new ObjectArrayKeySpliterator();
	}

	@Override
	public <K extends E> ObjectArrayKeySpliterator<K> keySpliterator(int index) {
		return new ObjectArrayKeySpliterator(index);
	}

	@Override
	public ObjectArrayListIterator listIterator() {
		return new ObjectArrayListIterator();
	}

	@Override
	public ObjectArrayListIterator listIterator(int index) {
		return new ObjectArrayListIterator(index);
	}

	@Override
	public Stream<E> parallelStream() {
		return StreamSupport.stream(new ObjectArraySpliterator(), true);
	}

	@Override
	public void set(int index, E element) {
		this.requireIndex(index);
		int i = this.beginIndex + index;
		this.array[i] = element;
	}

	@Override
	public void sort() {
		java.util.Arrays.sort(this.array, this.beginIndex, this.endIndex);
	}

	@Override
	public void sort(Comparator<? super E> comparator) {
		java.util.Arrays.sort(this.array, this.beginIndex, this.endIndex, comparator);
	}

	@Override
	public ObjectArraySpliterator spliterator() {
		return new ObjectArraySpliterator();
	}

	@Override
	public ObjectArraySpliterator spliterator(int index) {
		return new ObjectArraySpliterator(index);
	}

	@Override
	public Stream<E> stream() {
		return StreamSupport.stream(new ObjectArraySpliterator(), false);
	}

	@Override
	public ObjectArray<E> subArray(int beginIndex, int endIndex) {
		this.requireRange(beginIndex, endIndex);
		return new ObjectArray(
				this.array,
				this.beginIndex + beginIndex,
				this.beginIndex + endIndex
		);
	}

	@Override
	public E[] toArray() {
		int length = this.endIndex - this.beginIndex;
		E[] array = (E[]) java.lang.reflect.Array.newInstance(this.array.getClass().getComponentType(), length);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				length
		);

		return array;
	}

	@Override
	public E[] toArray(int length) {
		E[] array = (E[]) java.lang.reflect.Array.newInstance(this.array.getClass().getComponentType(), length);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				Math.min(this.endIndex - this.beginIndex, length)
		);

		return array;
	}

	@Override
	public <T extends E> T[] toArray(Class<? super T[]> klass) {
		Objects.requireNonNull(klass, "klass");
		int length = this.endIndex - this.beginIndex;

		T[] array = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				length
		);

		return array;
	}

	@Override
	public <T extends E> T[] toArray(int length, Class<? super T[]> klass) {
		Objects.requireNonNull(klass, "klass");

		T[] array = (T[]) java.lang.reflect.Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				Math.min(this.endIndex - this.beginIndex, length)
		);

		return array;
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.endIndex)
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
	public <V extends E> ObjectArrayValueIterator<V> valueIterator() {
		return new ObjectArrayValueIterator();
	}

	@Override
	public <V extends E> ObjectArrayValueIterator<V> valueIterator(int index) {
		return new ObjectArrayValueIterator(index);
	}

	@Override
	public <V extends E> ObjectArrayValueSpliterator<V> valueSpliterator() {
		return new ObjectArrayValueSpliterator();
	}

	@Override
	public <V extends E> ObjectArrayValueSpliterator<V> valueSpliterator(int index) {
		return new ObjectArrayValueSpliterator(index);
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
	public final class ObjectArrayEntry<K extends E, V extends E> extends ArrayEntry<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7098093446276533400L;

		/**
		 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
		 *
		 * @param index the index to where the key (followed by the value) will be in the constructed entry.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayEntry(int index) {
			super(index);
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
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
	public final class ObjectArrayEntryIterator<K extends E, V extends E> extends ArrayEntryIterator<K, V> {
		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayEntryIterator() {
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayEntryIterator(int index) {
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
	public final class ObjectArrayEntrySet<K extends E, V extends E> extends ArrayEntrySet<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1358655666106432532L;

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArrayEntrySet() {
		}

		@Override
		public boolean add(Map.Entry<K, V> entry) {
			K key = entry.getKey();

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k)) {
					V value = entry.getValue();
					V v = (V) ObjectArray.this.array[i + 1];

					if (value == v || value != null && value.equals(v))
						//already exists
						return false;

					break;
				}
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Map.Entry<K, V> entry : collection) {
				K key = entry.getKey();

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k)) {
						V value = entry.getValue();
						V v = (V) ObjectArray.this.array[i + 1];

						if (value == v || value != null && value.equals(v))
							//already exists
							continue for0;

						break;
					}
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
				Object key = entry.getKey();

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k)) {
						Object value = entry.getValue();
						E v = ObjectArray.this.array[i + 1];

						if (value == v || value != null && value.equals(v))
							return true;

						break;
					}
				}
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object object : collection) {
				if (object instanceof Map.Entry) {
					Map.Entry entry = (Map.Entry) object;
					Object key = entry.getKey();

					for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
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

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Set) {
				Set set = (Set) object;

				if (set.size() == ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1) {
					for0:
					for (Object object1 : set) {
						if (object1 instanceof Map.Entry) {
							Map.Entry entry = (Map.Entry) object1;
							Object key = entry.getKey();

							for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
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
		public boolean remove(Object object) {
			if (object instanceof Map.Entry) {
				Map.Entry entry = (Map.Entry) object;
				Object key = entry.getKey();

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k)) {
						Object value = ((java.util.Map.Entry) object).getValue();
						V v = (V) ObjectArray.this.array[i + 1];

						if (value == v || value != null && value.equals(v))
							//can not remove
							throw new UnsupportedOperationException("remove");

						break;
					}
				}
			}

			//no match
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (object instanceof Map.Entry) {
					Map.Entry entry = (Map.Entry) object;
					Object key = entry.getKey();

					for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
						K k = (K) ObjectArray.this.array[i];

						if (key == k || key != null && key.equals(k)) {
							Object value = entry.getValue();
							V v = (V) ObjectArray.this.array[i + 1];

							if (value == v || value != null && value.equals(v))
								//can not remove
								throw new UnsupportedOperationException("remove");

							break;
						}
					}
				}

			//no match
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];
				V v = (V) ObjectArray.this.array[i + 1];

				for (Object object : collection)
					if (object instanceof Map.Entry) {
						Map.Entry entry = (Map.Entry) object;
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
		public String toString() {
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
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
	public final class ObjectArrayEntrySpliterator<K extends E, V extends E> extends ArrayEntrySpliterator<K, V> {
		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayEntrySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayEntrySpliterator(int index) {
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
	public final class ObjectArrayIterator extends ArrayIterator {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayIterator(int index) {
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
	public final class ObjectArrayKeyIterator<K extends E> extends ArrayKeyIterator<K> {
		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayKeyIterator() {
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayKeyIterator(int index) {
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
	public final class ObjectArrayKeySet<K extends E> extends ArrayKeySet<K> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 6047336320717832956L;

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArrayKeySet() {
		}

		@Override
		public boolean add(K key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					//already exists
					return false;
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends K> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (K key : collection) {
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k))
						//already exists
						continue for0;
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
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
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object key : collection) {
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Set) {
				Set set = (Set) object;

				if (set.size() == ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1) {
					for0:
					for (Object key : set) {
						for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
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
		public boolean remove(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (object == k || object != null && object.equals(k))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object key : collection)
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
					K k = (K) ObjectArray.this.array[i];

					if (key == k || key != null && key.equals(k))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
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
		public Object[] toArray() {
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			for (int i = ObjectArray.this.beginIndex, j = 0; i < ObjectArray.this.endIndex; i += 2, j++) {
				K k = (K) ObjectArray.this.array[i];

				product[j] = k;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = ObjectArray.this.beginIndex, j = 0; i < ObjectArray.this.endIndex; i += 2, j++) {
				K k = (K) ObjectArray.this.array[i];

				product[j] = (T) k;
			}

			return product;
		}

		@Override
		public String toString() {
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
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
	public final class ObjectArrayKeySpliterator<K extends E> extends ArrayKeySpliterator<K> {
		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayKeySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayKeySpliterator(int index) {
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
	public final class ObjectArrayList extends ArrayList {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6821775231116953767L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArrayList() {
		}

		@Override
		public boolean contains(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object element : collection) {
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
					E e = ObjectArray.this.array[i];

					if (element == e || element != null && element.equals(e))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof List) {
				//same class
				List list = (List) object;

				if (list.size() == ObjectArray.this.endIndex - ObjectArray.this.beginIndex) {
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
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				consumer.accept(e);
			}
		}

		@Override
		public E get(int index) {
			ObjectArray.this.requireIndex(index);
			int i = ObjectArray.this.beginIndex + index;
			return ObjectArray.this.array[i];
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
					return i - ObjectArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = ObjectArray.this.endIndex - 1; i >= ObjectArray.this.beginIndex; i--) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					return i - ObjectArray.this.beginIndex;
			}

			return -1;
		}

		@Override
		public boolean remove(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			for (Object element : collection)
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
					E e = ObjectArray.this.array[i];

					if (element == e || element != null && element.equals(e))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
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
		public E set(int index, E element) {
			ObjectArray.this.requireIndex(index);
			int i = ObjectArray.this.beginIndex + index;
			E old = ObjectArray.this.array[i];
			ObjectArray.this.array[i] = element;
			return old;
		}

		@Override
		public Object[] toArray() {
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex;
			Object[] product = new Object[length];

			System.arraycopy(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			System.arraycopy(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public String toString() {
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				E e = ObjectArray.this.array[i];

				builder.append(e);

				i++;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
			}
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public final class ObjectArrayListIterator extends ArrayListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayListIterator(int index) {
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
	public final class ObjectArrayMap<K extends E, V extends E> extends ArrayMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1924164470541987638L;

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArrayMap() {
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
			if (object instanceof Map) {
				Map<?, ?> map = (Map) object;

				if (map.size() == ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1) {
					for0:
					for (Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
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
		public void putAll(Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
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
		public V remove(Object key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (key == k || key != null && key.equals(k))
					//old:found
					throw new UnsupportedOperationException("remove");
			}

			//old:notfound
			return null;
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
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
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
	public final class ObjectArraySet extends ArraySet {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8265570214297191438L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArraySet() {
		}

		@Override
		public boolean add(E element) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (element == e || element != null && element.equals(e))
					//already exists
					return false;
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (E element : collection) {
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
					E e = ObjectArray.this.array[i];

					if (element == e || element != null && element.equals(e))
						//already exists
						continue for0;
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object element : collection) {
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
					E e = ObjectArray.this.array[i];

					if (element == e || element != null && element.equals(e))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				//same identity
				return true;
			if (object instanceof Set) {
				//same class
				Set set = (Set) object;

				if (set.size() == ObjectArray.this.endIndex - ObjectArray.this.beginIndex) {
					//same length

					for0:
					for (Object element : set) {
						//for each element

						for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
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
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				consumer.accept(e);
			}
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
		public boolean remove(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				E e = ObjectArray.this.array[i];

				if (object == e || object != null && object.equals(e))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object element : collection)
				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
					E e = ObjectArray.this.array[i];

					if (element == e || element != null && element.equals(e))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
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

		@Override
		public Object[] toArray() {
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex;
			Object[] product = new Object[length];

			System.arraycopy(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			System.arraycopy(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					product,
					0,
					length
			);

			return product;
		}

		@Override
		public String toString() {
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				E e = ObjectArray.this.array[i];

				builder.append(e);

				i++;
				if (i >= ObjectArray.this.endIndex)
					return builder.append("]")
							.toString();

				builder.append(", ");
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
	public final class ObjectArraySpliterator extends ArraySpliterator {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArraySpliterator(int index) {
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
	public final class ObjectArrayValueIterator<V extends E> extends ArrayValueIterator<V> {
		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayValueIterator() {
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayValueIterator(int index) {
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
	public final class ObjectArrayValueSpliterator<V extends E> extends ArrayValueSpliterator<V> {
		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayValueSpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayValueSpliterator(int index) {
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
	public final class ObjectArrayValues<V extends E> extends ArrayValues<V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6890162958501631510L;

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		@SuppressWarnings("RedundantNoArgConstructor")
		public ObjectArrayValues() {
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
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object value : collection) {
				for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
					V v = (V) ObjectArray.this.array[i];

					if (value == v || value != null && value.equals(v))
						continue for0;
				}

				return false;
			}

			return true;
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
		public boolean remove(Object object) {
			for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
				V v = (V) ObjectArray.this.array[i];

				if (object == v || object != null && object.equals(v))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//nothing to remove
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object value : collection)
				for (int i = ObjectArray.this.beginIndex + 1; i < ObjectArray.this.endIndex; i += 2) {
					V v = (V) ObjectArray.this.array[i];

					if (value == v || value != null && value.equals(v))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//nothing to remove
			return false;
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
		public Object[] toArray() {
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			for (int i = ObjectArray.this.beginIndex + 1, j = 0; i < ObjectArray.this.endIndex; i += 2, j++) {
				V v = (V) ObjectArray.this.array[i];

				product[j] = v;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = ObjectArray.this.beginIndex + 1, j = 0; i < ObjectArray.this.endIndex; i += 2, j++) {
				V v = (V) ObjectArray.this.array[i];

				product[j] = (T) v;
			}

			return product;
		}

		@Override
		public String toString() {
			if (ObjectArray.this.endIndex <= ObjectArray.this.beginIndex)
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
