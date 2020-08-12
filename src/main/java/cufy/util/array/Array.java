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

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An abstraction of an array backed by an actual.
 *
 * @param <A> the type of the array.
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public abstract class Array<A, E> implements Iterable<E>, Serializable {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 3238786977844647983L;

	/**
	 * The array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.04
	 */
	@SuppressWarnings("NonSerializableFieldInSerializableClass")
	protected final A array;
	/**
	 * The first index of the area at the actual array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.05
	 */
	protected final int beginIndex;
	/**
	 * One past the last index of the area at the actual array backing this array.
	 *
	 * @since 0.1.5 ~2020.08.05
	 */
	protected final int endIndex;

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	protected Array(A array) {
		Objects.requireNonNull(array, "array");
		int length = java.lang.reflect.Array.getLength(array);

		this.array = array;
		this.beginIndex = 0;
		this.endIndex = length;
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
	protected Array(A array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		int length = java.lang.reflect.Array.getLength(array);

		if (beginIndex < 0)
			throw new ArrayIndexOutOfBoundsException("beginIndex(" + beginIndex + ") < 0");
		if (endIndex > length)
			throw new ArrayIndexOutOfBoundsException(
					"endIndex(" + endIndex + ") > length(" + length + ")");
		if (beginIndex > endIndex)
			throw new IllegalArgumentException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");

		this.array = array;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths,
	 * deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep
	 * 		lengths, deep elements, and deep orderings.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an
	 *                                  array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean deepEquals(Object array, Object other) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");
		if (other != null && !other.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[] && other instanceof Object[])
			return ObjectArray.deepEquals((Object[]) array, (Object[]) other);
		if (array instanceof boolean[] && other instanceof boolean[])
			return BooleanArray.equals((boolean[]) array, (boolean[]) other);
		if (array instanceof byte[] && other instanceof byte[])
			return ByteArray.equals((byte[]) array, (byte[]) other);
		if (array instanceof char[] && other instanceof char[])
			return CharacterArray.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return DoubleArray.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return FloatArray.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return IntegerArray.equals((int[]) array, (int[]) other);
		if (array instanceof long[] && other instanceof long[])
			return LongArray.equals((long[]) array, (long[]) other);
		if (array instanceof short[] && other instanceof short[])
			return ShortArray.equals((short[]) array, (short[]) other);

		return array == other;
	}

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int deepHashCode(Object array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return ObjectArray.deepHashCode((Object[]) array);
		if (array instanceof boolean[])
			return BooleanArray.hashCode((boolean[]) array);
		if (array instanceof byte[])
			return ByteArray.hashCode((byte[]) array);
		if (array instanceof char[])
			return CharacterArray.hashCode((char[]) array);
		if (array instanceof double[])
			return DoubleArray.hashCode((double[]) array);
		if (array instanceof float[])
			return FloatArray.hashCode((float[]) array);
		if (array instanceof int[])
			return IntegerArray.hashCode((int[]) array);
		if (array instanceof long[])
			return LongArray.hashCode((long[]) array);
		if (array instanceof short[])
			return ShortArray.hashCode((short[]) array);

		return 0;
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String deepToString(Object array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return ObjectArray.deepToString((Object[]) array);
		if (array instanceof boolean[])
			return BooleanArray.toString((boolean[]) array);
		if (array instanceof byte[])
			return ByteArray.toString((byte[]) array);
		if (array instanceof char[])
			return CharacterArray.toString((char[]) array);
		if (array instanceof double[])
			return DoubleArray.toString((double[]) array);
		if (array instanceof float[])
			return FloatArray.toString((float[]) array);
		if (array instanceof int[])
			return IntegerArray.toString((int[]) array);
		if (array instanceof long[])
			return LongArray.toString((long[]) array);
		if (array instanceof short[])
			return ShortArray.toString((short[]) array);

		return "null";
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(Object array, Object other) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");
		if (other != null && !other.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[] && other instanceof Object[])
			return ObjectArray.equals((Object[]) array, (Object[]) other);
		if (array instanceof boolean[] && other instanceof boolean[])
			return BooleanArray.equals((boolean[]) array, (boolean[]) other);
		if (array instanceof byte[] && other instanceof byte[])
			return ByteArray.equals((byte[]) array, (byte[]) other);
		if (array instanceof char[] && other instanceof char[])
			return CharacterArray.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return DoubleArray.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return FloatArray.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return IntegerArray.equals((int[]) array, (int[]) other);
		if (array instanceof long[] && other instanceof long[])
			return LongArray.equals((long[]) array, (long[]) other);
		if (array instanceof short[] && other instanceof short[])
			return ShortArray.equals((short[]) array, (short[]) other);

		return array == other;
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(Object array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return ObjectArray.hashCode((Object[]) array);
		if (array instanceof boolean[])
			return BooleanArray.hashCode((boolean[]) array);
		if (array instanceof byte[])
			return ByteArray.hashCode((byte[]) array);
		if (array instanceof char[])
			return CharacterArray.hashCode((char[]) array);
		if (array instanceof double[])
			return DoubleArray.hashCode((double[]) array);
		if (array instanceof float[])
			return FloatArray.hashCode((float[]) array);
		if (array instanceof int[])
			return IntegerArray.hashCode((int[]) array);
		if (array instanceof long[])
			return LongArray.hashCode((long[]) array);
		if (array instanceof short[])
			return ShortArray.hashCode((short[]) array);

		return 0;
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @param <A>   the type of the array.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <A> Array<A, ?> of(A array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return new ObjectArray((Object[]) array);
		if (array instanceof boolean[])
			return (Array) new BooleanArray((boolean[]) array);
		if (array instanceof byte[])
			return (Array) new ByteArray((byte[]) array);
		if (array instanceof char[])
			return (Array) new CharacterArray((char[]) array);
		if (array instanceof double[])
			return (Array) new DoubleArray((double[]) array);
		if (array instanceof float[])
			return (Array) new FloatArray((float[]) array);
		if (array instanceof int[])
			return (Array) new IntegerArray((int[]) array);
		if (array instanceof long[])
			return (Array) new LongArray((long[]) array);
		if (array instanceof short[])
			return (Array) new ShortArray((short[]) array);

		throw new IllegalArgumentException("Not an array");
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array      the array to be wrapped.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @param <A>        the type of the array.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}. Or  if the given {@code
	 *                                   array} is not an array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <A> Array<A, ?> of(A array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return new ObjectArray((Object[]) array, beginIndex, endIndex);
		if (array instanceof boolean[])
			return (Array) new BooleanArray((boolean[]) array, beginIndex, endIndex);
		if (array instanceof byte[])
			return (Array) new ByteArray((byte[]) array, beginIndex, endIndex);
		if (array instanceof char[])
			return (Array) new CharacterArray((char[]) array, beginIndex, endIndex);
		if (array instanceof double[])
			return (Array) new DoubleArray((double[]) array, beginIndex, endIndex);
		if (array instanceof float[])
			return (Array) new FloatArray((float[]) array, beginIndex, endIndex);
		if (array instanceof int[])
			return (Array) new IntegerArray((int[]) array, beginIndex, endIndex);
		if (array instanceof long[])
			return (Array) new LongArray((long[]) array, beginIndex, endIndex);
		if (array instanceof short[])
			return (Array) new ShortArray((short[]) array, beginIndex, endIndex);

		throw new IllegalArgumentException("Not an array");
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(Object array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return ObjectArray.toString((Object[]) array);
		if (array instanceof boolean[])
			return BooleanArray.toString((boolean[]) array);
		if (array instanceof byte[])
			return ByteArray.toString((byte[]) array);
		if (array instanceof char[])
			return CharacterArray.toString((char[]) array);
		if (array instanceof double[])
			return DoubleArray.toString((double[]) array);
		if (array instanceof float[])
			return FloatArray.toString((float[]) array);
		if (array instanceof int[])
			return IntegerArray.toString((int[]) array);
		if (array instanceof long[])
			return LongArray.toString((long[]) array);
		if (array instanceof short[])
			return ShortArray.toString((short[]) array);

		return "null";
	}

	/**
	 * Get an actual array copy of this array that has the given {@code klass}.
	 *
	 * @param klass the type of the constructed array.
	 * @param <T>   the type of the returned array.
	 * @return an actual array copy of this array.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws ArrayStoreException      if an element can not be stored at the constructed array.
	 * @throws IllegalArgumentException if the given {@code klass} is not an object array class.
	 * @since 0.1.5 ~2020.08.11
	 */
	public <T extends E> T[] array(Class<? super T[]> klass) {
		return this.array(this.length(), klass);
	}

	/**
	 * Get an actual array copy of this array.
	 *
	 * @return an actual array copy of this array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public A array() {
		return this.array(this.length());
	}

	/**
	 * Determine if this array is empty or not.
	 *
	 * @return true, if this array is empty.
	 * @since 0.1.5 ~2020.08.11
	 */
	public boolean isEmpty() {
		return this.endIndex <= this.beginIndex;
	}

	/**
	 * Get the length of this array.
	 *
	 * @return the length of this array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public int length() {
		return this.endIndex - this.beginIndex;
	}

	/**
	 * Get a parallel stream streaming the elements in this array.
	 *
	 * @return a parallel stream streaming the elements in this array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public Stream<E> parallelStream() {
		return StreamSupport.stream(this.spliterator(), true);
	}

	/**
	 * Get a stream streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(Object[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public Stream<E> stream() {
		return StreamSupport.stream(this.spliterator(), false);
	}

	/**
	 * Get the boxed index for the given real {@code index}.
	 *
	 * @param index the real index to get a boxed index for.
	 * @return the boxed index for the given real {@code index}.
	 * @since 0.1.5 ~2020.08.11
	 */
	protected int lowerIndex(int index) {
		return index - this.beginIndex;
	}

	/**
	 * Insure that this array has even length.
	 *
	 * @throws IllegalArgumentException if {@code length % 2 != 0}
	 * @since 0.1.5 ~2020.08.06
	 */
	protected void requireEven() {
		int length = this.length();

		if (length % 2 != 0)
			throw new IllegalArgumentException("length(" + length + ") % 2 != 0");
	}

	/**
	 * Insure that the given {@code index} is a valid index in this array.
	 *
	 * @param index the index to be checked.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	protected void requireIndex(int index) {
		int length = this.length();

		if (index < 0)
			throw new IndexOutOfBoundsException("index(" + index + ") < 0");
		if (index >= length)
			throw new IndexOutOfBoundsException("index(" + index + ") >= length(" + length + ")");
	}

	/**
	 * Insure that the specified range is a valid range in this array.
	 *
	 * @param beginIndex the first index in the range to be checked.
	 * @param endIndex   one past the last index in the range to be checked.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex > length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}
	 * @since 0.1.5 ~2020.08.06
	 */
	protected void requireRange(int beginIndex, int endIndex) {
		int length = this.length();

		if (beginIndex < 0)
			throw new ArrayIndexOutOfBoundsException("beginIndex(" + beginIndex + ") < 0");
		if (endIndex > length)
			throw new ArrayIndexOutOfBoundsException(
					"endIndex(" + endIndex + ") > length(" + length + ")");
		if (beginIndex > endIndex)
			throw new IllegalArgumentException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
	}

	/**
	 * Insure that the specified index is valid as a start/end point of this array.
	 *
	 * @param index the index to be checked.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	protected void requireRange(int index) {
		int length = this.length();

		if (index < 0)
			throw new IndexOutOfBoundsException("index(" + index + ") < 0");
		if (index > length)
			throw new IndexOutOfBoundsException("index(" + index + ") > length(" + length + ")");
	}

	/**
	 * Get the real index for the given boxed {@code index}.
	 *
	 * @param index the boxed index to get a real index for.
	 * @return the real index for the given boxed index.
	 * @since 0.1.5 ~2020.08.11
	 */
	protected int upperIndex(int index) {
		return this.beginIndex + index;
	}

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract void forEach(Consumer<? super E> consumer);

	@Override
	public abstract int hashCode();

	@Override
	public abstract Iterator iterator();

	@Override
	public abstract Spliterator spliterator();

	@Override
	public abstract String toString();

	/**
	 * Determine the index of the first element in the given {@code elements} that does not equal
	 * any element in this array.
	 *
	 * @param elements the elements to be checked.
	 * @return the index of the first element in the given {@code elements} that does not equal any
	 * 		element in this array. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int all(E... elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does not equal
	 * any element in this array.
	 *
	 * @param elements the elements to be checked.
	 * @return the index of the first element in the given {@code elements} that does not equal any
	 * 		element in this array. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int all(A elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any
	 * element in this array.
	 *
	 * @param elements to elements to be checked.
	 * @return the index of the first element in the given {@code elements} that does equal any
	 * 		element in this array. Or -1 if no such element found.
	 * @throws NullPointerException if the {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int any(E... elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any
	 * element in this array.
	 *
	 * @param elements to elements to be checked.
	 * @return the index of the first element in the given {@code elements} that does equal any
	 * 		element in this array. Or -1 if no such element found.
	 * @throws NullPointerException if the {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int any(A elements);

	/**
	 * Get an actual array copy of this array.
	 *
	 * @param length the length of the constructed array.
	 * @return an actual array copy of this array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract A array(int length);

	/**
	 * Get an actual array copy of this array that has the given {@code klass}.
	 *
	 * @param length the length of the constructed array.
	 * @param klass  the type of the constructed array.
	 * @param <T>    the type of the returned array.
	 * @return an actual array copy of this array.
	 * @throws NullPointerException       if the given {@code klass} is null.
	 * @throws IllegalArgumentException   if the given {@code klass} is not an object array class.
	 * @throws ArrayStoreException        if an element can not be stored at the constructed array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract <T extends E> T[] array(int length, Class<? super T[]> klass);

	/**
	 * Fill the given {@code array} with the elements of this array. If the given {@code array} is
	 * smaller than this array, then construct a new array with the same type as the given {@code
	 * array} and the same length as the length of this array. If the given {@code array} is larger
	 * than this array, then set the index at {@code length} to null.
	 *
	 * @param array the array to be filled.
	 * @param <T>   the type of the elements in the given {@code array}.
	 * @return the given {@code array}, if it is not smaller than this array. Otherwise, a new array
	 * 		with the same length as this array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @throws ArrayStoreException  if an element can not be stored at the given {@code array}.
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract <T> T[] array(T[] array);

	/**
	 * Using {@link System#arraycopy(Object, int, Object, int, int)}, copy all elements of this
	 * array to the given {@code array}.
	 *
	 * @param array the destination array.
	 * @param pos   the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > array.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code
	 *                                   array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void arraycopy(A array, int pos);

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 *
	 * @param element the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(Object[], Object)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract int binarySearch(E element);

	/**
	 * Determine if this array contains the given element.
	 *
	 * @param element the element to look for.
	 * @return true, if the given {@code element} equals any element in this array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract boolean contains(Object element);

	/**
	 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in this
	 * array. With the key been the first index in that range and the value been the last index.
	 *
	 * @param index the index to where the key (followed by the value) will be in the constructed
	 *              entry.
	 * @param <K>   the type of the key in the constructed entry.
	 * @param <V>   the type of the value in the constructed entry.
	 * @return a new entry backed by a range from {@code index} to {@code index + 1}.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> Entry<K, V> entry(int index);

	/**
	 * Construct a new set backed by the entries in this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new set backed by the entries in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> EntrySet<K, V> entrySet();

	/**
	 * Assign the given {@code element} to each element of this array.
	 *
	 * @param element the element to fill this array with.
	 * @throws ArrayStoreException if the given {@code element} can not be stored in this {@code
	 *                             array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void fill(E element);

	/**
	 * Get the element at the given {@code index} in this array.
	 *
	 * @param index the index to get the element from.
	 * @return the element at the given {@code index} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @see java.lang.reflect.Array#get(Object, int)
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract E get(int index);

	/**
	 * Manually copy all elements of this array to the given {@code array}.
	 *
	 * @param array the destination array.
	 * @param pos   the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > array.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code
	 *                                   array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void hardcopy(Object[] array, int pos);

	/**
	 * Construct a new set backed by the keys in this array.
	 *
	 * @param <K> the type of the keys.
	 * @return a new set backed by the keys in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> KeySet<K> keySet();

	/**
	 * Construct a new list backed by this array.
	 *
	 * @return a new list backed by this array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract List list();

	/**
	 * Construct a new map backed by this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new map backed by this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> Map<K, V> map();

	/**
	 * Cumulates, in parallel, each element of this array in place, using the supplied function. For
	 * example if this array initially holds [2, 1, 0, 3] and the operation performs addition, then
	 * upon return this array holds [2, 3, 3, 6]. Parallel prefix computation is usually more
	 * efficient than sequential loops for large arrays.
	 *
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(Object[], BinaryOperator)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void parallelPrefix(BinaryOperator<E> operator);

	/**
	 * In parallel, assign each element of this array to the value returned from invoking the given
	 * {@code function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in this {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void parallelSetAll(IntFunction<? extends E> function);

	/**
	 * Sorts this array into ascending order, according to the natural ordering of its elements. All
	 * elements in this array must implement the {@link Comparable} interface. Furthermore, all
	 * elements in the array must be mutually comparable (that is, e1.compareTo(e2) must not throw a
	 * {@link ClassCastException} for any elements e1 and e2 in the array). This sort is guaranteed
	 * to be stable: equal elements will not be reordered as a result of the sort.
	 *
	 * @see java.util.Arrays#parallelSort(Comparable[])
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void parallelSort();

	/**
	 * Set the element at the given {@code index} in this array to the given {@code element}.
	 *
	 * @param index   the index to set the given {@code element} to.
	 * @param element the element to be set.
	 * @return the previous element associated to the given {@code index} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the
	 *                                        array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract E replace(int index, E element);

	/**
	 * Construct a new set backed by this array.
	 *
	 * @return a set backed by this array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract Set set();

	/**
	 * Set the element at the given {@code index} in this array to the given {@code element}.
	 *
	 * @param index   the index to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the
	 *                                        array.
	 * @see java.lang.reflect.Array#set(Object, int, Object)
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract void set(int index, E element);

	/**
	 * Assign each element of this array to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract void setAll(IntFunction<? extends E> function);

	/**
	 * Sort this array.
	 *
	 * @see java.util.Arrays#sort(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract void sort();

	/**
	 * Construct a new array backed by the specified range of this array. The range starts at the
	 * given {@code beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param beginIndex the first index of the area at this array to be backing the constructed
	 *                   array.
	 * @param endIndex   one past the last index of the area at this array to be backing the
	 *                   constructed array.
	 * @return a new array backed by the specified range of this array.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex > length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract Array<A, E> sub(int beginIndex, int endIndex);

	/**
	 * Construct a new collection backed by the values in this array.
	 *
	 * @param <V> the type of the values.
	 * @return a new collection backed by the values of this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> Values<V> values();

	/**
	 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class Entry<K extends E, V extends E> implements java.util.Map.Entry<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1793396182662638233L;

		/**
		 * The index of the key of this entry in the backing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

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
		protected Entry(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index, index + 1);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract K getKey();

		@Override
		public abstract V getValue();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract V setValue(V value);

		@Override
		public abstract String toString();
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
	@SuppressWarnings("AbstractClassWithoutAbstractMethods")
	public abstract class EntryIterator<K extends E, V extends E> implements java.util.Iterator<java.util.Map.Entry<K, V>> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected EntryIterator() {
			this.index = Array.this.beginIndex;
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
		protected EntryIterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = Array.this.endIndex;

			int i = Array.this.lowerIndex(index);
			int l = Array.this.length();
			for (; i < l; i += 2) {
				Entry<K, V> entry = Array.this.entry(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public java.util.Map.Entry<K, V> next() {
			int index = this.index;

			if (index < Array.this.endIndex) {
				this.index += 2;

				int i = Array.this.lowerIndex(index);
				return Array.this.entry(i);//trimmed index
			}

			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
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
	public abstract class EntrySet<K extends E, V extends E> implements java.util.Set<java.util.Map.Entry<K, V>>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7515045887948351373L;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected EntrySet() {
		}

		@Override
		public boolean add(java.util.Map.Entry<K, V> entry) {
			if (this.contains(entry))
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> collection) {
			Objects.requireNonNull(collection, "collection");

			if (this.containsAll(collection))
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public void clear() {
			if (Array.this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public void forEach(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			int i = 0;
			int l = Array.this.length();
			for (; i < l; i += 2) {
				Entry<K, V> entry = Array.this.entry(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public Stream<java.util.Map.Entry<K, V>> parallelStream() {
			return StreamSupport.stream(this.spliterator(), true);
		}

		@Override
		public boolean remove(Object object) {
			if (this.contains(object))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (this.contains(object))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super java.util.Map.Entry<K, V>> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			int i = 0;
			int l = Array.this.length();
			for (; i < l; i += 2) {
				Entry<K, V> entry = Array.this.entry(i); //trimmed index

				if (predicate.test(entry))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//no match
			return false;
		}

		@Override
		public int size() {
			return Array.this.length() >>> 1;
		}

		@Override
		public Stream<java.util.Map.Entry<K, V>> stream() {
			return StreamSupport.stream(this.spliterator(), false);
		}

		@Override
		public Object[] toArray() {
			int length = this.size();
			Object[] product = new Object[length];

			int i = 0;
			int l = Array.this.length();
			for (int j = 0; i < l; i += 2, j++) {
				Entry<K, V> entry = Array.this.entry(i);//trimmed index

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
			int l = Array.this.length();
			for (int j = 0; i < l; i += 2, j++) {
				Entry<K, V> entry = Array.this.entry(i);//trimmed index

				product[j] = (T) entry;
			}

			return product;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract EntryIterator<K, V> iterator();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract EntrySpliterator<K, V> spliterator();

		@Override
		public abstract String toString();
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
	@SuppressWarnings("AbstractClassWithoutAbstractMethods")
	public abstract class EntrySpliterator<K extends E, V extends E> implements java.util.Spliterator<java.util.Map.Entry<K, V>> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED |
											  java.util.Spliterator.SUBSIZED |
											  java.util.Spliterator.ORDERED |
											  java.util.Spliterator.IMMUTABLE;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected EntrySpliterator() {
			this.index = Array.this.beginIndex;
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
		protected EntrySpliterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public int characteristics() {
			return this.characteristics;
		}

		@Override
		public long estimateSize() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = Array.this.endIndex;

			int i = 0;
			int l = Array.this.length();
			for (; i < l; i += 2) {
				Entry<K, V> entry = Array.this.entry(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public Comparator<? super java.util.Map.Entry<K, V>> getComparator() {
			if ((this.characteristics & java.util.Spliterator.SORTED) != 0)
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics & characteristics) == characteristics;
		}

		@Override
		public boolean tryAdvance(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < Array.this.endIndex) {
				this.index += 2;

				int i = Array.this.lowerIndex(index);
				Entry<K, V> entry = Array.this.entry(i);//trimmed index
				consumer.accept(entry);
				return true;
			}

			return false;
		}

		@Override
		public EntrySpliterator<K, V> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.sub(index, midIndex)
						.<K, V>entrySet()
						.spliterator();
			}

			return null;
		}
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class Iterator implements java.util.Iterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Iterator() {
			this.index = Array.this.beginIndex;
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Iterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract E next();
	}

	/**
	 * An iterator iterating the keys in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class KeyIterator<K extends E> implements java.util.Iterator<K> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected KeyIterator() {
			this.index = Array.this.beginIndex;
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
		protected KeyIterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super K> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract K next();
	}

	/**
	 * A set backed by the keys in the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class KeySet<K extends E> implements java.util.Set<K>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4627018232494058734L;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected KeySet() {
		}

		@Override
		public boolean add(K key) {
			if (this.contains(key))
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends K> collection) {
			Objects.requireNonNull(collection, "collection");

			if (this.containsAll(collection))
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public void clear() {
			if (Array.this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public Stream<K> parallelStream() {
			return StreamSupport.stream(this.spliterator(), true);
		}

		@Override
		public boolean remove(Object object) {
			if (this.contains(object))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (this.contains(object))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public int size() {
			return Array.this.length() >>> 1;
		}

		@Override
		public Stream<K> stream() {
			return StreamSupport.stream(this.spliterator(), false);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super K> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract KeyIterator<K> iterator();

		@Override
		public abstract boolean removeIf(Predicate<? super K> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract KeySpliterator<K> spliterator();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	/**
	 * A spliterator iterating the keys in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract class KeySpliterator<K extends E> implements java.util.Spliterator<K> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED |
											  java.util.Spliterator.SUBSIZED |
											  java.util.Spliterator.ORDERED |
											  java.util.Spliterator.IMMUTABLE;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected KeySpliterator() {
			this.index = Array.this.beginIndex;
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
		protected KeySpliterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public int characteristics() {
			return this.characteristics;
		}

		@Override
		public long estimateSize() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public Comparator<? super E> getComparator() {
			if ((this.characteristics & java.util.Spliterator.SORTED) != 0)
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics & characteristics) == characteristics;
		}

		@Override
		public KeySpliterator<K> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.sub(index, midIndex)
						.<K>keySet()
						.spliterator();
			}

			return null;
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super K> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean tryAdvance(Consumer<? super K> consumer);
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class List implements java.util.List<E>, Serializable, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5890878610114060287L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected List() {
		}

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void add(int index, E element) {
			Array.this.requireIndex(index);
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			if (!collection.isEmpty())
				throw new UnsupportedOperationException("addAll");

			return false;
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			Array.this.requireIndex(index);
			if (!collection.isEmpty())
				throw new UnsupportedOperationException("addAll");

			return false;
		}

		@Override
		public void clear() {
			if (Array.this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean contains(Object object) {
			return Array.this.contains(object);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Array.this.forEach(consumer);
		}

		@Override
		public E get(int index) {
			return Array.this.get(index);
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public Iterator iterator() {
			return Array.this.iterator();
		}

		@Override
		public ListIterator listIterator() {
			return this.listIterator(0);
		}

		@Override
		public Stream<E> parallelStream() {
			return Array.this.parallelStream();
		}

		@Override
		public E remove(int index) {
			Array.this.requireIndex(index);
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean remove(Object object) {
			if (this.contains(object))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (this.contains(object))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public E set(int index, E element) {
			return Array.this.replace(index, element);
		}

		@Override
		public int size() {
			return Array.this.length();
		}

		@Override
		public java.util.Spliterator<E> spliterator() {
			return Array.this.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return Array.this.stream();
		}

		@Override
		public List subList(int beginIndex, int endIndex) {
			return Array.this.sub(beginIndex, endIndex)
					.list();
		}

		@Override
		public Object[] toArray() {
			return Array.this.array(Object[].class);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return Array.this.array(array);
		}

		@Override
		public String toString() {
			return Array.this.toString();
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int indexOf(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int lastIndexOf(Object object);

		@Override
		public abstract ListIterator listIterator(int index);

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@Override
		public abstract void replaceAll(UnaryOperator<E> operator);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract void sort(Comparator<? super E> comparator);
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class ListIterator implements java.util.ListIterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;
		/**
		 * The last index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int last = -1;

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ListIterator() {
			this.index = Array.this.beginIndex;
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ListIterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public boolean hasPrevious() {
			return this.index > Array.this.beginIndex;
		}

		@Override
		public int nextIndex() {
			return Array.this.lowerIndex(this.index);
		}

		@Override
		public int previousIndex() {
			return Array.this.lowerIndex(this.index - 1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract E next();

		@Override
		public abstract E previous();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract void set(E element);
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
	public abstract class Map<K extends E, V extends E> implements java.util.Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7692195336903798598L;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Map() {
		}

		@Override
		public void clear() {
			if (this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public EntrySet<K, V> entrySet() {
			return Array.this.entrySet();
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public KeySet<K> keySet() {
			return Array.this.keySet();
		}

		@Override
		public V remove(Object key) {
			if (this.containsKey(key))
				throw new UnsupportedOperationException("remove");

			return null;
		}

		@Override
		public int size() {
			return Array.this.length() >>> 1;
		}

		@Override
		public Values<V> values() {
			return Array.this.values();
		}

		@Override
		public abstract V compute(K key, BiFunction<? super K, ? super V, ? extends V> function);

		@Override
		public abstract V computeIfAbsent(K key, Function<? super K, ? extends V> function);

		@Override
		public abstract V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsKey(Object key);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsValue(Object value);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(BiConsumer<? super K, ? super V> consumer);

		@Override
		public abstract V get(Object key);

		@Override
		public abstract V getOrDefault(Object key, V defaultValue);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function);

		@Override
		public abstract V put(K key, V value);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract void putAll(java.util.Map<? extends K, ? extends V> map);

		@Override
		public abstract V putIfAbsent(K key, V value);

		@Override
		public abstract boolean remove(Object key, Object value);

		@Override
		public abstract boolean replace(K key, V oldValue, V newValue);

		@Override
		public abstract V replace(K key, V value);

		@Override
		public abstract void replaceAll(BiFunction<? super K, ? super V, ? extends V> function);

		@Override
		public abstract String toString();
	}

	/**
	 * A set backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class Set implements java.util.Set<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2092169091443806884L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Set() {
		}

		@Override
		public boolean add(E element) {
			if (this.contains(element))
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				throw new UnsupportedOperationException("add");
			}

			return false;
		}

		@Override
		public void clear() {
			if (this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean contains(Object object) {
			return Array.this.contains(object);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Array.this.forEach(consumer);
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public Iterator iterator() {
			return Array.this.iterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return Array.this.parallelStream();
		}

		@Override
		public boolean remove(Object object) {
			if (this.contains(object))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (this.contains(object))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public int size() {
			return Array.this.length();
		}

		@Override
		public Spliterator spliterator() {
			return Array.this.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return Array.this.stream();
		}

		@Override
		public Object[] toArray() {
			return Array.this.array(Object[].class);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return Array.this.array(array);
		}

		@Override
		public String toString() {
			return Array.this.toString();
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract class Spliterator implements java.util.Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED |
											  java.util.Spliterator.SUBSIZED |
											  java.util.Spliterator.ORDERED |
											  java.util.Spliterator.IMMUTABLE;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Spliterator() {
			this.index = Array.this.beginIndex;
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Spliterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public int characteristics() {
			return this.characteristics;
		}

		@Override
		public long estimateSize() {
			return Array.this.endIndex - this.index;
		}

		@Override
		public Comparator<? super E> getComparator() {
			if ((this.characteristics & java.util.Spliterator.SORTED) != 0)
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return Array.this.endIndex - this.index;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics & characteristics) == characteristics;
		}

		@Override
		public Spliterator trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.sub(index, midIndex)
						.spliterator();
			}

			return null;
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean tryAdvance(Consumer<? super E> consumer);
	}

	/**
	 * An iterator iterating the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class ValueIterator<V extends E> implements java.util.Iterator<V> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ValueIterator() {
			this.index = Array.this.beginIndex;
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
		protected ValueIterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super V> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract V next();
	}

	/**
	 * A spliterator iterating the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract class ValueSpliterator<V extends E> implements java.util.Spliterator<V> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED |
											  java.util.Spliterator.SUBSIZED |
											  java.util.Spliterator.ORDERED |
											  java.util.Spliterator.IMMUTABLE;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ValueSpliterator() {
			this.index = Array.this.beginIndex;
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
		protected ValueSpliterator(int index) {
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireRange(index);
			//noinspection OverridableMethodCallDuringObjectConstruction
			this.index = Array.this.upperIndex(index);
		}

		@Override
		public int characteristics() {
			return this.characteristics;
		}

		@Override
		public long estimateSize() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public Comparator<? super V> getComparator() {
			if ((this.characteristics & java.util.Spliterator.SORTED) != 0)
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return Array.this.endIndex - this.index >>> 1;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics & characteristics) == characteristics;
		}

		@Override
		public ValueSpliterator<V> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.sub(index, midIndex)
						.<V>values()
						.spliterator();
			}

			return null;
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super V> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean tryAdvance(Consumer<? super V> consumer);
	}

	/**
	 * A collection backed by the values in the enclosing array.
	 *
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class Values<V extends E> implements Collection<V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7634421013079755116L;

		{
			//noinspection OverridableMethodCallDuringObjectConstruction
			Array.this.requireEven();
		}

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Values() {
		}

		@Override
		public boolean add(V value) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends V> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			if (this.isEmpty())
				return;

			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection) {
				if (this.contains(object))
					continue;

				return false;
			}

			return true;
		}

		@Override
		public boolean isEmpty() {
			return Array.this.isEmpty();
		}

		@Override
		public Stream<V> parallelStream() {
			return StreamSupport.stream(this.spliterator(), true);
		}

		@Override
		public boolean remove(Object object) {
			if (this.contains(object))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object object : collection)
				if (this.contains(object))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public int size() {
			return Array.this.length() >>> 1;
		}

		@Override
		public Stream<V> stream() {
			return StreamSupport.stream(this.spliterator(), false);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super V> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract ValueIterator<V> iterator();

		@Override
		public abstract boolean removeIf(Predicate<? super V> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract ValueSpliterator<V> spliterator();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}
}
