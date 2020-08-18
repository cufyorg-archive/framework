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
public abstract class Array<A, E> implements Serializable, Cloneable, Iterable<E> {
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
			return CharArray.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return DoubleArray.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return FloatArray.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return IntArray.equals((int[]) array, (int[]) other);
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
			return CharArray.hashCode((char[]) array);
		if (array instanceof double[])
			return DoubleArray.hashCode((double[]) array);
		if (array instanceof float[])
			return FloatArray.hashCode((float[]) array);
		if (array instanceof int[])
			return IntArray.hashCode((int[]) array);
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
			return CharArray.toString((char[]) array);
		if (array instanceof double[])
			return DoubleArray.toString((double[]) array);
		if (array instanceof float[])
			return FloatArray.toString((float[]) array);
		if (array instanceof int[])
			return IntArray.toString((int[]) array);
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
			return CharArray.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return DoubleArray.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return FloatArray.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return IntArray.equals((int[]) array, (int[]) other);
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
			return CharArray.hashCode((char[]) array);
		if (array instanceof double[])
			return DoubleArray.hashCode((double[]) array);
		if (array instanceof float[])
			return FloatArray.hashCode((float[]) array);
		if (array instanceof int[])
			return IntArray.hashCode((int[]) array);
		if (array instanceof long[])
			return LongArray.hashCode((long[]) array);
		if (array instanceof short[])
			return ShortArray.hashCode((short[]) array);

		return 0;
	}

	/**
	 * Construct a new array backed by a new actual array that have the given {@code length}.
	 *
	 * @param length the length of the new actual array backing the construct array.
	 * @param <E>    the type of the elements.
	 * @return a new array backed by a new actual array that have the given {@code length}.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public static <E> ObjectArray<E> of(int length) {
		return new ObjectArray(length);
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code length}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param length        the length of the new actual array backing the construct array.
	 * @param <A>           the type of the array.
	 * @param <E>           the type of the elements
	 * @return a new array backed by a new actual array of the given {@code componentType} that have
	 * 		the given {@code length}.
	 * @throws NullPointerException       if the given {@code componentType} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public static <A, E> Array<A, E> of(Class<E> componentType, int length) {
		if (componentType == boolean.class)
			return (Array<A, E>) new BooleanArray(length);
		if (componentType == byte.class)
			return (Array<A, E>) new ByteArray(length);
		if (componentType == char.class)
			return (Array<A, E>) new CharArray(length);
		if (componentType == double.class)
			return (Array<A, E>) new DoubleArray(length);
		if (componentType == float.class)
			return (Array<A, E>) new FloatArray(length);
		if (componentType == int.class)
			return (Array<A, E>) new IntArray(length);
		if (componentType == long.class)
			return (Array<A, E>) new LongArray(length);
		if (componentType == short.class)
			return (Array<A, E>) new ShortArray(length);

		return new ObjectArray(componentType, length);
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code dimensions}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param dimensions    the dimensions of the new actual array backing the construct array.
	 * @param <E>           the type of the elements.
	 * @return a new array backed by a new actual array of the given {@code componentType} that have
	 * 		the given {@code dimensions}.
	 * @throws NullPointerException       if the given {@code componentType} or {@code dimensions}
	 *                                    is null.
	 * @throws NegativeArraySizeException if an element in the given {@code dimensions} is
	 *                                    negative.
	 * @throws ClassCastException         if the given {@code componentType} is not assignable from
	 *                                    {@link Object} (if only one dimensions has been
	 *                                    provided).
	 * @throws IllegalArgumentException   if {@code dimensions.length < 1} or {@code
	 *                                    dimensions.length > 255}.
	 * @see java.lang.reflect.Array#newInstance(Class, int[])
	 * @since 0.1.5 ~2020.08.13
	 */
	public static <E> ObjectArray<E> of(Class<E> componentType, int... dimensions) {
		return new ObjectArray(componentType, dimensions);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <A, E> Array<A, E> of(A array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return new ObjectArray((Object[]) array);
		if (array instanceof boolean[])
			return (Array) new BooleanArray((boolean[]) array);
		if (array instanceof byte[])
			return (Array) new ByteArray((byte[]) array);
		if (array instanceof char[])
			return (Array) new CharArray((char[]) array);
		if (array instanceof double[])
			return (Array) new DoubleArray((double[]) array);
		if (array instanceof float[])
			return (Array) new FloatArray((float[]) array);
		if (array instanceof int[])
			return (Array) new IntArray((int[]) array);
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
	 * @param <E>        the type of the elements.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}. Or if the given {@code
	 *                                   array} is not an array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <A, E> Array<A, E> of(A array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return new ObjectArray((Object[]) array, beginIndex, endIndex);
		if (array instanceof boolean[])
			return (Array) new BooleanArray((boolean[]) array, beginIndex, endIndex);
		if (array instanceof byte[])
			return (Array) new ByteArray((byte[]) array, beginIndex, endIndex);
		if (array instanceof char[])
			return (Array) new CharArray((char[]) array, beginIndex, endIndex);
		if (array instanceof double[])
			return (Array) new DoubleArray((double[]) array, beginIndex, endIndex);
		if (array instanceof float[])
			return (Array) new FloatArray((float[]) array, beginIndex, endIndex);
		if (array instanceof int[])
			return (Array) new IntArray((int[]) array, beginIndex, endIndex);
		if (array instanceof long[])
			return (Array) new LongArray((long[]) array, beginIndex, endIndex);
		if (array instanceof short[])
			return (Array) new ShortArray((short[]) array, beginIndex, endIndex);

		throw new IllegalArgumentException("Not an array");
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map}.
	 *
	 * @param map the map to construct a new array from to be backing the constructed array.
	 * @param <E> the type of the elements.
	 * @return a new array backed by a new array from the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public static <E> ObjectArray<E> of(java.util.Map<? extends E, ? extends E> map) {
		return new ObjectArray(map);
	}

	/**
	 * Construct a new array backed by a new array from the given {@code collection}.
	 *
	 * @param collection the collection to construct a new array from to be backing the constructed
	 *                   array.
	 * @param <E>        the type of the elements.
	 * @return a new array backed by a new array from the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public static <E> ObjectArray<E> of(java.util.Collection<? extends E> collection) {
		return new ObjectArray(collection);
	}

	/**
	 * Construct a new array backed by a new array from the given {@code map}.
	 *
	 * @param map   the map to construct a new array from to be backing the constructed array.
	 * @param klass the class of the new constructed backing array of the constructed array.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return a new array backed by a new array from the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public static <A, E> Array<A, E> of(java.util.Map<? extends E, ? extends E> map, Class<A> klass) {
		if (klass == boolean[].class)
			return (Array<A, E>) new BooleanArray((java.util.Map) map);
		if (klass == byte[].class)
			return (Array<A, E>) new ByteArray((java.util.Map) map);
		if (klass == char[].class)
			return (Array<A, E>) new CharArray((java.util.Map) map);
		if (klass == double[].class)
			return (Array<A, E>) new DoubleArray((java.util.Map) map);
		if (klass == float[].class)
			return (Array<A, E>) new FloatArray((java.util.Map) map);
		if (klass == int[].class)
			return (Array<A, E>) new IntArray((java.util.Map) map);
		if (klass == long[].class)
			return (Array<A, E>) new LongArray((java.util.Map) map);
		if (klass == short[].class)
			return (Array<A, E>) new ShortArray((java.util.Map) map);

		return new ObjectArray(map, klass);
	}

	/**
	 * Construct a new array backed by a new array from the given {@code collection}.
	 *
	 * @param collection the collection to construct a new array from to be backing the constructed
	 *                   array.
	 * @param klass      the class of the new constructed backing array of the constructed array.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @return a new array backed by a new array from the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @since 0.1.5 ~2020.08.12
	 */
	public static <A, E> Array<A, E> of(java.util.Collection<? extends E> collection, Class<A> klass) {
		if (klass == boolean[].class)
			return (Array<A, E>) new BooleanArray((java.util.Collection) collection);
		if (klass == byte[].class)
			return (Array<A, E>) new ByteArray((java.util.Collection) collection);
		if (klass == char[].class)
			return (Array<A, E>) new CharArray((java.util.Collection) collection);
		if (klass == double[].class)
			return (Array<A, E>) new DoubleArray((java.util.Collection) collection);
		if (klass == float[].class)
			return (Array<A, E>) new FloatArray((java.util.Collection) collection);
		if (klass == int[].class)
			return (Array<A, E>) new IntArray((java.util.Collection) collection);
		if (klass == long[].class)
			return (Array<A, E>) new LongArray((java.util.Collection) collection);
		if (klass == short[].class)
			return (Array<A, E>) new ShortArray((java.util.Collection) collection);

		return new ObjectArray(collection, klass);
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
			return CharArray.toString((char[]) array);
		if (array instanceof double[])
			return DoubleArray.toString((double[]) array);
		if (array instanceof float[])
			return FloatArray.toString((float[]) array);
		if (array instanceof int[])
			return IntArray.toString((int[]) array);
		if (array instanceof long[])
			return LongArray.toString((long[]) array);
		if (array instanceof short[])
			return ShortArray.toString((short[]) array);

		return "null";
	}

	/**
	 * Get the length of this array.
	 *
	 * @return the length of this array.
	 * @see java.lang.reflect.Array#getLength(Object)
	 * @since 0.1.5 ~2020.08.06
	 */
	public final int length() {
		return this.endIndex - this.beginIndex;
	}

	/**
	 * Get the thumb at the backing array where the given {@code thumb} is pointing at in this
	 * array.
	 *
	 * @param thumb the thumb at this array.
	 * @return the thumb the given {@code thumb} at this array is backed by at the backing array.
	 * @throws IndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @since 0.1.5 ~2020.08.13
	 */
	protected final int index(int thumb) {
		int length = this.length();

		if (thumb < 0)
			throw new IndexOutOfBoundsException("thumb(" + thumb + ") < 0");
		if (thumb >= length)
			throw new IndexOutOfBoundsException("thumb(" + thumb + ") >= length(" + length + ")");

		return this.beginIndex + thumb;
	}

	/**
	 * Insure that the specified range is a valid range in this array.
	 *
	 * @param beginThumb the first index in the range to be checked.
	 * @return the length of the range.
	 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final int range(int beginThumb) {
		int length = this.length();

		if (beginThumb < 0)
			throw new IndexOutOfBoundsException("beginThumb(" + beginThumb + ") < 0");
		if (beginThumb > length)
			throw new IndexOutOfBoundsException(
					"beginThumb(" + beginThumb + ") > length(" + length + ")");

		return length - beginThumb;
	}

	/**
	 * Insure that the specified range is a valid range in this array.
	 *
	 * @param beginThumb the first index in the range to be checked.
	 * @param endThumb   one past the last index in the range to be checked.
	 * @return the length of the range between the given {@code beginThumb} and {@code endThumb}.
	 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code endThumb > length}.
	 * @throws IllegalArgumentException  if {@code beginThumb > endThumb}
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final int range(int beginThumb, int endThumb) {
		int length = this.length();

		if (beginThumb < 0)
			throw new ArrayIndexOutOfBoundsException("beginThumb(" + beginThumb + ") < 0");
		if (endThumb > length)
			throw new ArrayIndexOutOfBoundsException(
					"endThumb(" + endThumb + ") > length(" + length + ")");
		if (beginThumb > endThumb)
			throw new IllegalArgumentException(
					"beginThumb(" + beginThumb + ") > endThumb(" + endThumb + ")");

		return length;
	}

	/**
	 * Get the boxed index for the given real {@code index}.
	 *
	 * @param index the real index to get a boxed index for.
	 * @return the boxed index for the given real {@code index}.
	 * @since 0.1.5 ~2020.08.11
	 */
	protected final int thumb(int index) {
		return index - this.beginIndex;
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
	 * Determine if the given two elements are equal or not. This is the base equality check in this
	 * class and it should be for its subclasses.
	 *
	 * @param element the first element.
	 * @param e       the second element.
	 * @return true, if the given {@code element} equals the given {@code e} in this class's
	 * 		standard.
	 * @since 0.1.5 ~2020.08.18
	 */
	protected boolean eq(Object element, E e) {
		return element == e || element != null && element.equals(e);
	}

	@Override
	public abstract Array<A, E> clone();

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
	 * Get an actual array copy of this array.
	 *
	 * @param length the length of the constructed array.
	 * @return an actual array copy of this array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract A array(int length);

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
	 * Use the best available copying method to copy elements from this array to the given {@code
	 * array}.
	 *
	 * @param array the destination array.
	 * @param pos   the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > array.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code array}.
	 *                                   Or if no available method to copy elements from this array
	 *                                   to the given {@code array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public abstract void copy(Object array, int pos);

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
	 * Get the element at the given {@code thumb} in this array.
	 *
	 * @param thumb the thumb to get the element from.
	 * @return the element at the given {@code thumb} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @see java.lang.reflect.Array#get(Object, int)
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract E get(int thumb);

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
	 * Construct a new list backed by this array.
	 *
	 * @return a new list backed by this array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract List list();

	/**
	 * Get a list iterator iterating over the elements of this array.
	 *
	 * @return a new list iterator for this array.
	 */
	public abstract ListIterator listIterator();

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
	 * Set the element at the given {@code thumb} in this array to the given {@code element}.
	 *
	 * @param thumb   the thumb to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the
	 *                                        array.
	 * @see java.lang.reflect.Array#set(Object, int, Object)
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract void set(int thumb, E element);

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
	 * given {@code beginThumb} and ends before the given {@code endThumb}.
	 *
	 * @param beginThumb the first index of the area at this array to be backing the constructed
	 *                   array.
	 * @param endThumb   one past the last index of the area at this array to be backing the
	 *                   constructed array.
	 * @return a new array backed by the specified range of this array.
	 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code endThumb > length}.
	 * @throws IllegalArgumentException  if {@code beginThumb > endThumb}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract Array<A, E> sub(int beginThumb, int endThumb);

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
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Iterator(int beginThumb) {
			Array.this.range(beginThumb);
			this.index = Array.this.beginIndex + beginThumb;
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
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class List implements java.util.List<E>, Serializable, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5890878610114060287L;

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void add(int thumb, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean addAll(int thumb, Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
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
		public void forEach(Consumer<? super E> consumer) {
			Array.this.forEach(consumer);
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public Iterator iterator() {
			return Array.this.iterator();
		}

		@Override
		public ListIterator listIterator() {
			return Array.this.listIterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(this.spliterator(), true);
		}

		@Override
		public E remove(int thumb) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean remove(Object object) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
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
			return StreamSupport.stream(this.spliterator(), false);
		}

		@Override
		public List subList(int beginThumb, int endThumb) {
			return Array.this.sub(beginThumb, endThumb)
					.list();
		}

		@Override
		public Object[] toArray() {
			int length = this.size();
			Object[] product = new Object[length];
			Array.this.copy(product, 0);
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

			Array.this.sub(0, Math.min(array.length, length))
					.copy(array, 0);

			return product;
		}

		@Override
		public String toString() {
			return Array.this.toString();
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract E get(int thumb);

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
		public abstract ListIterator listIterator(int beginThumb);

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@Override
		public abstract void replaceAll(UnaryOperator<E> operator);

		@Override
		public abstract E set(int thumb, E element);

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
		 * from the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ListIterator(int beginThumb) {
			Array.this.range(beginThumb);
			this.index = Array.this.beginIndex + beginThumb;
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
			return Array.this.thumb(this.index);
		}

		@Override
		public int previousIndex() {
			return Array.this.thumb(this.index - 1);
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
			int length = Array.this.length();
			if (length % 2 != 0)
				throw new IllegalArgumentException("length(" + length + ") % 2 != 0");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public V remove(Object key) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public int size() {
			return Array.this.length() >>> 1;
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

		@Override
		public abstract EntrySet entrySet();

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
		public abstract KeySet keySet();

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

		@Override
		public abstract Values values();

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class Entry implements java.util.Map.Entry<K, V>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -1793396182662638233L;

			/**
			 * The index of the key of this entry in the backing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected final int index;

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
			protected Entry(int thumb) {
				Array.this.range(thumb, thumb + 1);
				this.index = Array.this.beginIndex + thumb;
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
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class EntrySet implements java.util.Set<java.util.Map.Entry<K, V>>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7515045887948351373L;

			@Override
			public boolean add(java.util.Map.Entry<K, V> entry) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public void clear() {
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
				return Map.this.isEmpty();
			}

			@Override
			public Stream<java.util.Map.Entry<K, V>> parallelStream() {
				return StreamSupport.stream(this.spliterator(), true);
			}

			@Override
			public boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return Map.this.size();
			}

			@Override
			public Stream<java.util.Map.Entry<K, V>> stream() {
				return StreamSupport.stream(this.spliterator(), false);
			}

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean contains(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@Override
			public abstract void forEach(Consumer<? super java.util.Map.Entry<K, V>> consumer);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract Iterator iterator();

			@Override
			public abstract boolean removeIf(Predicate<? super java.util.Map.Entry<K, V>> predicate);

			@Override
			public abstract Spliterator spliterator();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();

			/**
			 * An iterator iterating the entries in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public abstract class Iterator implements java.util.Iterator<java.util.Map.Entry<K, V>> {
				/**
				 * The next index.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected int index;

				/**
				 * Construct a new iterator iterating the entries in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new iterator iterating the entries in the enclosing array, starting
				 * from the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
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
				public abstract void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer);

				@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
				@Override
				public abstract Entry next();
			}

			/**
			 * A spliterator iterating the entries in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public abstract class Spliterator implements java.util.Spliterator<java.util.Map.Entry<K, V>> {
				/**
				 * The characteristics of this spliterator.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected static final int CHARACTERISTICS = java.util.Spliterator.SIZED |
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
				 * Construct a new spliterator iterating the entries in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new spliterator iterating the entries in the enclosing array,
				 * starting from the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
				}

				@Override
				public int characteristics() {
					return Spliterator.CHARACTERISTICS;
				}

				@Override
				public long estimateSize() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public Comparator<? super java.util.Map.Entry<K, V>> getComparator() {
					if ((Spliterator.CHARACTERISTICS & java.util.Spliterator.SORTED) != 0)
						return null;

					throw new IllegalStateException();
				}

				@Override
				public long getExactSizeIfKnown() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public boolean hasCharacteristics(int characteristics) {
					return (Spliterator.CHARACTERISTICS & characteristics) == characteristics;
				}

				@Override
				public Spliterator trySplit() {
					int index = this.index;
					int midIndex = index + Array.this.endIndex >>> 1;

					if (index < midIndex) {
						this.index = midIndex;
						return Array.this.sub(index, midIndex)
								.<K, V>map()
								.entrySet()
								.spliterator();
					}

					return null;
				}

				@Override
				public abstract void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer);

				@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
				@Override
				public abstract boolean tryAdvance(Consumer<? super java.util.Map.Entry<K, V>> consumer);
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class KeySet implements java.util.Set<K>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 4627018232494058734L;

			@Override
			public boolean add(K key) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends K> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public void clear() {
				throw new UnsupportedOperationException("clear");
			}

			@Override
			public boolean contains(Object object) {
				return Map.this.containsKey(object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (Map.this.containsKey(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return Map.this.isEmpty();
			}

			@Override
			public Stream<K> parallelStream() {
				return StreamSupport.stream(this.spliterator(), true);
			}

			@Override
			public boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return Map.this.size();
			}

			@Override
			public Stream<K> stream() {
				return StreamSupport.stream(this.spliterator(), false);
			}

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@Override
			public abstract void forEach(Consumer<? super K> consumer);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract Iterator iterator();

			@Override
			public abstract boolean removeIf(Predicate<? super K> predicate);

			@Override
			public abstract Spliterator spliterator();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();

			/**
			 * An iterator iterating the keys in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public abstract class Iterator implements java.util.Iterator<K> {
				/**
				 * The next index.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected int index;

				/**
				 * Construct a new iterator iterating the keys in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new iterator iterating the keys in the enclosing array, starting from
				 * the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
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
			 * A spliterator iterating the keys in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public abstract class Spliterator implements java.util.Spliterator<K> {
				/**
				 * The characteristics of this spliterator.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected static final int CHARACTERISTICS = java.util.Spliterator.SIZED |
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
				 * Construct a new spliterator iterating the keys in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new spliterator iterating the keys in the enclosing array, starting
				 * from the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
				}

				@Override
				public int characteristics() {
					return Spliterator.CHARACTERISTICS;
				}

				@Override
				public long estimateSize() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public Comparator<? super E> getComparator() {
					if ((Spliterator.CHARACTERISTICS & java.util.Spliterator.SORTED) != 0)
						return null;

					throw new IllegalStateException();
				}

				@Override
				public long getExactSizeIfKnown() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public boolean hasCharacteristics(int characteristics) {
					return (Spliterator.CHARACTERISTICS & characteristics) == characteristics;
				}

				@Override
				public Spliterator trySplit() {
					int index = this.index;
					int midIndex = index + Array.this.endIndex >>> 1;

					if (index < midIndex) {
						this.index = midIndex;
						return Array.this.sub(index, midIndex)
								.<K, V>map()
								.keySet()
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
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class Values implements Collection<V>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 7634421013079755116L;

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
				throw new UnsupportedOperationException("clear");
			}

			@Override
			public boolean contains(Object object) {
				return Map.this.containsValue(object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (Map.this.containsValue(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return Map.this.isEmpty();
			}

			@Override
			public Stream<V> parallelStream() {
				return StreamSupport.stream(this.spliterator(), true);
			}

			@Override
			public boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return Map.this.size();
			}

			@Override
			public Stream<V> stream() {
				return StreamSupport.stream(this.spliterator(), false);
			}

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@Override
			public abstract void forEach(Consumer<? super V> consumer);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract Iterator iterator();

			@Override
			public abstract boolean removeIf(Predicate<? super V> predicate);

			@Override
			public abstract Spliterator spliterator();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();

			/**
			 * An iterator iterating the values in the enclosing array.
			 *
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.03
			 */
			public abstract class Iterator implements java.util.Iterator<V> {
				/**
				 * The next index.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected int index;

				/**
				 * Construct a new iterator iterating the values in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new iterator iterating the values in the enclosing array, starting
				 * from the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed iterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Iterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
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
			 * @author LSafer
			 * @version 0.1.5
			 * @since 0.1.5 ~2020.08.02
			 */
			public abstract class Spliterator implements java.util.Spliterator<V> {
				/**
				 * The characteristics of this spliterator.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected static final int CHARACTERISTICS = java.util.Spliterator.SIZED |
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
				 * Construct a new spliterator iterating the values in the enclosing array.
				 *
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator() {
					this.index = Array.this.beginIndex;
				}

				/**
				 * Construct a new spliterator iterating the values in the enclosing array, starting
				 * from the given {@code beginThumb}.
				 *
				 * @param beginThumb the initial position of the constructed spliterator.
				 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb
				 *                                   > length}.
				 * @since 0.1.5 ~2020.08.06
				 */
				protected Spliterator(int beginThumb) {
					Array.this.range(beginThumb);
					this.index = Array.this.beginIndex + beginThumb;
				}

				@Override
				public int characteristics() {
					return Spliterator.CHARACTERISTICS;
				}

				@Override
				public long estimateSize() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public Comparator<? super V> getComparator() {
					if ((Spliterator.CHARACTERISTICS & java.util.Spliterator.SORTED) != 0)
						return null;

					throw new IllegalStateException();
				}

				@Override
				public long getExactSizeIfKnown() {
					return Array.this.endIndex - this.index >>> 1;
				}

				@Override
				public boolean hasCharacteristics(int characteristics) {
					return (Spliterator.CHARACTERISTICS & characteristics) == characteristics;
				}

				@Override
				public Spliterator trySplit() {
					int index = this.index;
					int midIndex = index + Array.this.endIndex >>> 1;

					if (index < midIndex) {
						this.index = midIndex;
						return Array.this.sub(index, midIndex)
								.<K, V>map()
								.values()
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
		}
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
		protected static final int CHARACTERISTICS = java.util.Spliterator.SIZED |
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
		 * the given {@code beginThumb}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
		 *                                   length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected Spliterator(int beginThumb) {
			Array.this.range(beginThumb);
			this.index = Array.this.beginIndex + beginThumb;
		}

		@Override
		public int characteristics() {
			return Spliterator.CHARACTERISTICS;
		}

		@Override
		public long estimateSize() {
			return Array.this.endIndex - this.index;
		}

		@Override
		public Comparator<? super E> getComparator() {
			if ((Spliterator.CHARACTERISTICS & java.util.Spliterator.SORTED) != 0)
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return Array.this.endIndex - this.index;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (Spliterator.CHARACTERISTICS & characteristics) == characteristics;
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
}
