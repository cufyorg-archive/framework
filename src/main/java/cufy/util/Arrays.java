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
package cufy.util;

import cufy.util.array.*;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * An Utility class for raw arrays. Supporting various kinds of array operations. All methods
 * accepts any kind of array and has a reflection way method and a switcher method.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Arrays} utility class with
 * the same behaviour. So switching to import this class will not make any changes to files
 * previously imported {@link java.util.Arrays}.
 * <p>
 * Note: the class is not final nor have a private constructor ;). Anyone can override this class to
 * provide more static methods meanwhile provide access to the methods of this class with the
 * sub-class's signature. But, don't forget that this class is not made for OOP! So, any attempt to
 * construct any sub-class of this class will fail no matter what.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.06.11
 */
public class Arrays {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	protected Arrays() {
		throw new AssertionError("No instance for you!");
	}

	//..array

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @param <E>   the type of the elements.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static <E> ObjectArray<E> array(E... array) {
		return new ObjectArray<>(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static BooleanArray array(boolean[] array) {
		return new BooleanArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static ByteArray array(byte[] array) {
		return new ByteArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static CharacterArray array(char[] array) {
		return new CharacterArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static DoubleArray array(double[] array) {
		return new DoubleArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static FloatArray array(float[] array) {
		return new FloatArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static IntegerArray array(int[] array) {
		return new IntegerArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static LongArray array(long[] array) {
		return new LongArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	public static ShortArray array(short[] array) {
		return new ShortArray(array);
	}

	//.asList(A)

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @param <E>   the type of the elements.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> ObjectArray<E>.List asList(E... array) {
		return new ObjectArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static BooleanArray.List asList(boolean[] array) {
		return new BooleanArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ByteArray.List asList(byte[] array) {
		return new ByteArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static CharacterArray.List asList(char[] array) {
		return new CharacterArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static DoubleArray.List asList(double[] array) {
		return new DoubleArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static FloatArray.List asList(float[] array) {
		return new FloatArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static IntegerArray.List asList(int[] array) {
		return new IntegerArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static LongArray.List asList(long[] array) {
		return new LongArray(array).list();
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ShortArray.List asList(short[] array) {
		return new ShortArray(array).list();
	}

	//..asMap(A)

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @param <E>   the type of the elements.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E, K extends E, V extends E> ObjectArray<E>.Map<K, V> asMap(E... array) {
		return new ObjectArray<>(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static BooleanArray.Map asMap(boolean[] array) {
		return new BooleanArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ByteArray.Map asMap(byte[] array) {
		return new ByteArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static CharacterArray.Map asMap(char[] array) {
		return new CharacterArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static DoubleArray.Map asMap(double[] array) {
		return new DoubleArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static FloatArray.Map asMap(float[] array) {
		return new FloatArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static IntegerArray.Map asMap(int[] array) {
		return new IntegerArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static LongArray.Map asMap(long[] array) {
		return new LongArray(array).map();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if {@code array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ShortArray.Map asMap(short[] array) {
		return new ShortArray(array).map();
	}

	//.binarySearch(A, E)

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @param <E>   the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(Object[], Object)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> int binarySearch(E[] array, E key) {
		return new ObjectArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm.  The
	 * array must be sorted into ascending order according to the specified comparator (as by the
	 * {@link #sort(Object[], Comparator) sort(T[], Comparator)} method) prior to making this call.
	 * If it is not sorted, the results are undefined. If the array contains multiple elements equal
	 * to the specified object, there is no guarantee which one will be found.
	 *
	 * @param array      the array to be searched.
	 * @param key        the value to be searched for.
	 * @param comparator the comparator by which the array is ordered.  A
	 *                   <tt>null</tt> value indicates that the elements'
	 *                   {@linkplain Comparable natural ordering} should be used.
	 * @param <E>        the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(Object[], Object, Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> int binarySearch(E[] array, E key, Comparator<? super E> comparator) {
		return new ObjectArray(array).binarySearch(key, comparator);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(boolean[] array, boolean key) {
		return new BooleanArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(byte[], byte)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(byte[] array, byte key) {
		return new ByteArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(char[], char)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(char[] array, char key) {
		return new CharacterArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(double[], double)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(double[] array, double key) {
		return new DoubleArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(float[], float)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(float[] array, float key) {
		return new FloatArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(int[], int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(int[] array, int key) {
		return new IntegerArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(long[], long)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(long[] array, long key) {
		return new LongArray(array).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array the array to be searched.
	 * @param key   the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(short[], short)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(short[] array, short key) {
		return new ShortArray(array).binarySearch(key);
	}

	//.binarySearch(A, int, int, E)

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @param <E>        the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(Object[], int, int, Object)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> int binarySearch(E[] array, int beginIndex, int endIndex, E key) {
		return new ObjectArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm.  The
	 * array must be sorted into ascending order according to the specified comparator (as by the
	 * {@link #sort(Object[], Comparator) sort(T[], Comparator)} method) prior to making this call.
	 * If it is not sorted, the results are undefined. If the array contains multiple elements equal
	 * to the specified object, there is no guarantee which one will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @param comparator the comparator by which the array is ordered.  A
	 *                   <tt>null</tt> value indicates that the elements'
	 *                   {@linkplain Comparable natural ordering} should be used.
	 * @param <E>        the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(Object[], int, int, Object, Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> int binarySearch(E[] array, int beginIndex, int endIndex, E key, Comparator<? super E> comparator) {
		return new ObjectArray(array, beginIndex, endIndex).binarySearch(key, comparator);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(boolean[] array, int beginIndex, int endIndex, boolean key) {
		return new BooleanArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(byte[], int, int, byte)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(byte[] array, int beginIndex, int endIndex, byte key) {
		return new ByteArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(char[], int, int, char)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(char[] array, int beginIndex, int endIndex, char key) {
		return new CharacterArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(double[], int, int, double)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(double[] array, int beginIndex, int endIndex, double key) {
		return new DoubleArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(float[], int, int, float)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(float[] array, int beginIndex, int endIndex, float key) {
		return new FloatArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(int[], int, int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(int[] array, int beginIndex, int endIndex, int key) {
		return new IntegerArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(long[], int, int, long)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(long[] array, int beginIndex, int endIndex, long key) {
		return new LongArray(array, beginIndex, endIndex).binarySearch(key);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm. The
	 * array must be sorted into ascending order according to the {@linkplain Comparable natural
	 * ordering} of its elements (as by the {@link #sort(Object[])} method) prior to making this
	 * call. If it is not sorted, the results are undefined. (If the array contains elements that
	 * are not mutually comparable (for example, strings and integers), it <i>cannot</i> be sorted
	 * according to the natural ordering of its elements, hence results are undefined.) If the array
	 * contains multiple elements equal to the specified object, there is no guarantee which one
	 * will be found.
	 *
	 * @param array      the array to be searched.
	 * @param beginIndex the index of the first element (inclusive) to be searched.
	 * @param endIndex   the index of the last element (exclusive) to be searched.
	 * @param key        the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(short[], int, int, short)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static int binarySearch(short[] array, int beginIndex, int endIndex, short key) {
		return new ShortArray(array, beginIndex, endIndex).binarySearch(key);
	}

	//.copyOf(A, int)

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @param <E>    the type of the elements.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(Object[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> E[] copyOf(E[] array, int length) {
		return new ObjectArray<>(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @param klass  the type of the constructed array.
	 * @param <E>    the type of the elements.
	 * @param <T>    the super type of the elements.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @throws IllegalArgumentException   if the given {@code klass} is not an object array class.
	 * @throws ArrayStoreException        if an element can not be stored in the constructed array.
	 * @see java.util.Arrays#copyOf(Object[], int, Class)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E extends T, T> T[] copyOf(E[] array, int length, Class<? extends T[]> klass) {
		return (T[]) new ObjectArray(array).array(length, klass);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(boolean[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean[] copyOf(boolean[] array, int length) {
		return new BooleanArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(byte[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static byte[] copyOf(byte[] array, int length) {
		return new ByteArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(char[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static char[] copyOf(char[] array, int length) {
		return new CharacterArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(double[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static double[] copyOf(double[] array, int length) {
		return new DoubleArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(float[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static float[] copyOf(float[] array, int length) {
		return new FloatArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(int[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int[] copyOf(int[] array, int length) {
		return new IntegerArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(long[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static long[] copyOf(long[] array, int length) {
		return new LongArray(array).array(length);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(short[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static short[] copyOf(short[] array, int length) {
		return new ShortArray(array).array(length);
	}

	//.copyOfRange(A, int, int)

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @param <E>        the type of the elements.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(Object[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> E[] copyOfRange(E[] array, int beginIndex, int endIndex) {
		return new ObjectArray<>(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @param klass      the type of the constructed array.
	 * @param <E>        the type of the elements.
	 * @param <T>        the super type of the elements.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} or {@code klass} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}, Or if the given {@code
	 *                                   klass} is not an object array class
	 * @throws ArrayStoreException       if an element can not be stored in the constructed array.
	 * @see java.util.Arrays#copyOfRange(Object[], int, int, Class)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E extends T, T> T[] copyOfRange(E[] array, int beginIndex, int endIndex, Class<T[]> klass) {
		return new ObjectArray<>(array, beginIndex, array.length)
				.array(endIndex - beginIndex, klass);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(boolean[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean[] copyOfRange(boolean[] array, int beginIndex, int endIndex) {
		return new BooleanArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(byte[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static byte[] copyOfRange(byte[] array, int beginIndex, int endIndex) {
		return new ByteArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(char[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static char[] copyOfRange(char[] array, int beginIndex, int endIndex) {
		return new CharacterArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(double[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static double[] copyOfRange(double[] array, int beginIndex, int endIndex) {
		return new DoubleArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(float[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static float[] copyOfRange(float[] array, int beginIndex, int endIndex) {
		return new FloatArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(int[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int[] copyOfRange(int[] array, int beginIndex, int endIndex) {
		return new IntegerArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(long[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static long[] copyOfRange(long[] array, int beginIndex, int endIndex) {
		return new LongArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#copyOfRange(short[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static short[] copyOfRange(short[] array, int beginIndex, int endIndex) {
		return new ShortArray(array, beginIndex, array.length)
				.array(endIndex - beginIndex);
	}

	//.deepEquals(Object[])

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths,
	 * deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <E>   the type of the elements.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep
	 * 		lengths, deep elements, and deep orderings.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> boolean deepEquals(E[] array, E... other) {
		return ObjectArray.deepEquals(array, other);
	}

	//.deepHashCode(Object[])

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @param <E>   the type of the elements.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @see java.util.Arrays#deepHashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> int deepHashCode(E[] array) {
		return ObjectArray.deepHashCode(array);
	}

	//.deepToString(Object[])

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <E>   the type of the elements.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> String deepToString(E[] array) {
		return ObjectArray.deepToString(array);
	}

	//.equals(A, A)

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> boolean equals(T[] array, T... other) {
		return ObjectArray.equals(array, other);
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
	public static boolean equals(boolean[] array, boolean... other) {
		return BooleanArray.equals(array, other);
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(byte[], byte[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(byte[] array, byte... other) {
		return ByteArray.equals(array, other);
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(char[], char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(char[] array, char... other) {
		return CharacterArray.equals(array, other);
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(double[], double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(double[] array, double... other) {
		return DoubleArray.equals(array, other);
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
	public static boolean equals(float[] array, float... other) {
		return FloatArray.equals(array, other);
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
	public static boolean equals(int[] array, int... other) {
		return IntegerArray.equals(array, other);
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
	public static boolean equals(long[] array, long... other) {
		return LongArray.equals(array, other);
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(short[], short[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(short[] array, short... other) {
		return ShortArray.equals(array, other);
	}

	//.fill(A, E)

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @param <E>     the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @throws ArrayStoreException  if the given {@code element} can not be stored in the given
	 *                              {@code array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> void fill(E[] array, E element) {
		new ObjectArray<>(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(boolean[], boolean)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(boolean[] array, boolean element) {
		new BooleanArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(byte[], byte)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(byte[] array, byte element) {
		new ByteArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(char[], char)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(char[] array, char element) {
		new CharacterArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(double[], double)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(double[] array, double element) {
		new DoubleArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(float[], float)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(float[] array, float element) {
		new FloatArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(long[], long)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(long[] array, long element) {
		new LongArray(array).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(short[], short)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(short[] array, short element) {
		new ShortArray(array).fill(element);
	}

	//.fill(A, int, int, E)

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws ArrayStoreException       if the given {@code element} can not be stored in the given
	 *                                   {@code array}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(Object[], int, int, Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> void fill(E[] array, int beginIndex, int endIndex, E element) {
		new ObjectArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(boolean[], int, int, boolean)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(boolean[] array, int beginIndex, int endIndex, boolean element) {
		new BooleanArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(byte[], int, int, byte)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(byte[] array, int beginIndex, int endIndex, byte element) {
		new ByteArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(char[], int, int, char)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(char[] array, int beginIndex, int endIndex, char element) {
		new CharacterArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(double[], int, int, double)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(double[] array, int beginIndex, int endIndex, double element) {
		new DoubleArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(float[], int, int, float)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(float[] array, int beginIndex, int endIndex, float element) {
		new FloatArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(int[], int, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(int[] array, int beginIndex, int endIndex, int element) {
		new IntegerArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(long[], int, int, long)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(long[] array, int beginIndex, int endIndex, long element) {
		new LongArray(array, beginIndex, endIndex).fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#fill(short[], int, int, short)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(short[] array, int beginIndex, int endIndex, short element) {
		new ShortArray(array, beginIndex, endIndex).fill(element);
	}

	//..hardcopy(Object, int, Object, int, int)

	/**
	 * Copy the elements from the given {@code src} to the given {@code dest}. Start reading from
	 * the given {@code src} at the given {@code srcPos}. Start writing to the given {@code dest} at
	 * the given {@code destPos}. Copy the specified number of elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code
	 *                                   length < 0} or {@code srcPos + length > src.length} or
	 *                                   {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if the given {@code src} or {@code dest} is not an array.
	 *                                   Or if the given {@code src} and {@code dest} both are
	 *                                   primitive arrays but not the same class (only if the given
	 *                                   {@code length} is not 0). Or if an element can not be
	 *                                   stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object src, int srcPos, Object dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Class srcClass = src.getClass();
		Class destClass = dest.getClass();

		if (!srcClass.isArray())
			throw new ArrayStoreException(
					"hardcopy: source type " + srcClass.getName() + " is not an array");
		if (!destClass.isArray())
			throw new ArrayStoreException(
					"hardcopy: destination type " + destClass.getName() + " is not an array");

		if (srcClass == destClass ||
			!srcClass.getComponentType().isPrimitive() &&
			!destClass.getComponentType().isPrimitive())
			//if the arrays are compatible for System.arraycopy
			System.arraycopy(src, srcPos, dest, destPos, length);
		else if (src instanceof Object[])
			//if the source array is an object array
			new ObjectArray((Object[]) src, srcPos, srcPos + length)
					.hardcopy(dest, destPos);
		else if (dest instanceof Object[])
			//if the destination array is an object array
			Array.of(src, srcPos, srcPos + length)
					.hardcopy((Object[]) dest, destPos);
		else if (length != 0)
			//if both arrays are primitive arrays and they are not an instance of the same class
			throw new ArrayStoreException(
					"hardcopy: type mismatch: can not copy " + srcClass.getTypeName() + " into " +
					destClass.getTypeName());
	}

	//.hashCode(A)

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @param <E>   the type of the elements.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> int hashCode(E[] array) {
		return ObjectArray.hashCode(array);
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
		return BooleanArray.hashCode(array);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(byte[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(byte[] array) {
		return ByteArray.hashCode(array);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(char[] array) {
		return CharacterArray.hashCode(array);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(double[] array) {
		return DoubleArray.hashCode(array);
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
		return FloatArray.hashCode(array);
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
		return IntegerArray.hashCode(array);
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
		return LongArray.hashCode(array);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(short[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode(short[] array) {
		return ShortArray.hashCode(array);
	}

	//..iterator(A)

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param <E>   the type of the elements.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> ObjectArray.Iterator iterator(E... array) {
		return new ObjectArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static BooleanArray.Iterator iterator(boolean[] array) {
		return new BooleanArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ByteArray.Iterator iterator(byte[] array) {
		return new ByteArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static CharacterArray.Iterator iterator(char[] array) {
		return new CharacterArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static DoubleArray.Iterator iterator(double[] array) {
		return new DoubleArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static FloatArray.Iterator iterator(float[] array) {
		return new FloatArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static IntegerArray.Iterator iterator(int[] array) {
		return new IntegerArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static LongArray.Iterator iterator(long[] array) {
		return new LongArray(array).iterator();
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ShortArray.Iterator iterator(short[] array) {
		return new ShortArray(array).iterator();
	}

	//.parallelPrefix(A, BinaryOperator)

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param <E>      the type of the elements.
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(Object[], BinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void parallelPrefix(E[] array, BinaryOperator<E> operator) {
		new ObjectArray(array).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(boolean[] array, BinaryOperator<Boolean> operator) {
		new BooleanArray(array).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(byte[] array, BinaryOperator<Byte> operator) {
		new ByteArray(array).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(char[] array, BinaryOperator<Character> operator) {
		new CharacterArray(array).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(double[], DoubleBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(double[] array, DoubleBinaryOperator operator) {
		new DoubleArray(array).parallelPrefix(operator::applyAsDouble);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(float[] array, BinaryOperator<Float> operator) {
		new FloatArray(array).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(int[], IntBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(int[] array, IntBinaryOperator operator) {
		new IntegerArray(array).parallelPrefix(operator::applyAsInt);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(long[], LongBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(long[] array, LongBinaryOperator operator) {
		new LongArray(array).parallelPrefix(operator::applyAsLong);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(short[] array, BinaryOperator<Short> operator) {
		new ShortArray(array).parallelPrefix(operator);
	}

	//.parallelPrefix(A, int, int, BinaryOperator)

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param <E>        the type of the elements.
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#parallelPrefix(Object[], int, int, BinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void parallelPrefix(E[] array, int beginIndex, int endIndex, BinaryOperator<E> operator) {
		new ObjectArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(boolean[] array, int beginIndex, int endIndex, BinaryOperator<Boolean> operator) {
		new BooleanArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(byte[] array, int beginIndex, int endIndex, BinaryOperator<Byte> operator) {
		new ByteArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(char[] array, int beginIndex, int endIndex, BinaryOperator<Character> operator) {
		new CharacterArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#parallelPrefix(double[], int, int, DoubleBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(double[] array, int beginIndex, int endIndex, DoubleBinaryOperator operator) {
		new DoubleArray(array, beginIndex, endIndex).parallelPrefix(operator::applyAsDouble);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(float[] array, int beginIndex, int endIndex, BinaryOperator<Float> operator) {
		new FloatArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#parallelPrefix(int[], int, int, IntBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(int[] array, int beginIndex, int endIndex, IntBinaryOperator operator) {
		new IntegerArray(array, beginIndex, endIndex).parallelPrefix(operator::applyAsInt);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#parallelPrefix(long[], int, int, LongBinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(long[] array, int beginIndex, int endIndex, LongBinaryOperator operator) {
		new LongArray(array, beginIndex, endIndex).parallelPrefix(operator::applyAsLong);
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array      the array, which is modified in-place by this method.
	 * @param beginIndex the index of the first element, inclusive.
	 * @param endIndex   the index of the last element, exclusive.
	 * @param operator   a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelPrefix(short[] array, int beginIndex, int endIndex, BinaryOperator<Short> operator) {
		new ShortArray(array, beginIndex, endIndex).parallelPrefix(operator);
	}

	//.parallelSetAll(A, IntFunction)

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> void parallelSetAll(E[] array, IntFunction<? extends E> function) {
		new ObjectArray<>(array).parallelSetAll(function);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(boolean[] array, IntFunction<Boolean> function) {
		new BooleanArray(array).parallelSetAll(function);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(byte[] array, IntFunction<Byte> function) {
		new ByteArray(array).parallelSetAll(function);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(char[] array, IntFunction<Character> function) {
		new CharacterArray(array).parallelSetAll(function);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(double[], IntToDoubleFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(double[] array, IntToDoubleFunction function) {
		new DoubleArray(array).parallelSetAll(function::applyAsDouble);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(float[] array, IntFunction<Float> function) {
		new FloatArray(array).parallelSetAll(function);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(int[], IntUnaryOperator)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(int[] array, IntUnaryOperator function) {
		new IntegerArray(array).parallelSetAll(function::applyAsInt);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(long[], IntToLongFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(long[] array, IntToLongFunction function) {
		new LongArray(array).parallelSetAll(function::applyAsLong);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(short[] array, IntFunction<Short> function) {
		new ShortArray(array).parallelSetAll(function);
	}

	//.parallelSort(A)

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(Comparable[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E extends Comparable<? super E>> void parallelSort(E[] array) {
		new ObjectArray<>(array)
				.parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array      the array to be sorted.
	 * @param comparator the comparator to determine the order of the array.  A {@code null} value
	 *                   indicates that the elements' {@linkplain Comparable natural ordering}
	 *                   should be used.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void parallelSort(E[] array, Comparator<? super E> comparator) {
		new ObjectArray<>(array)
				.parallelSort(comparator);
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(boolean[] array) {
		new BooleanArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(byte[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(byte[] array) {
		new ByteArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(char[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(char[] array) {
		new CharacterArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(double[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(double[] array) {
		new DoubleArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(float[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(float[] array) {
		new FloatArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(int[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(int[] array) {
		new IntegerArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(long[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(long[] array) {
		new LongArray(array).parallelSort();
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(short[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(short[] array) {
		new ShortArray(array).parallelSort();
	}

	//.parallelSort(A, int, int)

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(Comparable[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E extends Comparable<? super E>> void parallelSort(E[] array, int beginIndex, int endIndex) {
		new ObjectArray<>(array, beginIndex, endIndex)
				.parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending order. The range to be
	 * sorted extends from the given {@code beginIndex}, inclusive, to the given {@code endIndex},
	 * exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param comparator the comparator to determine the order of the array.  A {@code null} value
	 *                   indicates that the elements' {@linkplain Comparable natural ordering}
	 *                   should be used.
	 * @param <E>        the type of the element.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(Object[], int, int, Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void parallelSort(E[] array, int beginIndex, int endIndex, Comparator<? super E> comparator) {
		new ObjectArray(array, beginIndex, endIndex)
				.parallelSort(comparator);
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(boolean[] array, int beginIndex, int endIndex) {
		new BooleanArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(byte[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(byte[] array, int beginIndex, int endIndex) {
		new ByteArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(char[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(char[] array, int beginIndex, int endIndex) {
		new CharacterArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(double[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(double[] array, int beginIndex, int endIndex) {
		new DoubleArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(float[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(float[] array, int beginIndex, int endIndex) {
		new FloatArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(int[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(int[] array, int beginIndex, int endIndex) {
		new IntegerArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(long[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(long[] array, int beginIndex, int endIndex) {
		new LongArray(array, beginIndex, endIndex).parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(short[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void parallelSort(short[] array, int beginIndex, int endIndex) {
		new ShortArray(array, beginIndex, endIndex).parallelSort();
	}

	//.setAll(A, IntFunction)

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> void setAll(E[] array, IntFunction<? extends E> function) {
		new ObjectArray<>(array).setAll(function);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(boolean[] array, IntFunction<Boolean> function) {
		new BooleanArray(array).setAll(function);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(byte[] array, IntFunction<Byte> function) {
		new ByteArray(array).setAll(function);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(char[] array, IntFunction<Character> function) {
		new CharacterArray(array).setAll(function);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(double[], IntToDoubleFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(double[] array, IntToDoubleFunction function) {
		new DoubleArray(array).setAll(function::applyAsDouble);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(float[] array, IntFunction<Float> function) {
		new FloatArray(array).setAll(function);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(int[], IntUnaryOperator)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(int[] array, IntUnaryOperator function) {
		new IntegerArray(array).setAll(function::applyAsInt);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(long[], IntToLongFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(long[] array, IntToLongFunction function) {
		new LongArray(array).setAll(function::applyAsLong);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(short[] array, IntFunction<Short> function) {
		new ShortArray(array).setAll(function);
	}

	//.sort(A)

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void sort(E[] array) {
		new ObjectArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array      the array to be sorted.
	 * @param comparator the comparator to determine the order of the array.  A {@code null} value
	 *                   indicates that the elements' {@linkplain Comparable natural ordering}
	 *                   should be used.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void sort(E[] array, Comparator<? super E> comparator) {
		new ObjectArray(array).sort(comparator);
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(boolean[] array) {
		new BooleanArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(byte[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(byte[] array) {
		new ByteArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(char[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(char[] array) {
		new CharacterArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(double[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(double[] array) {
		new DoubleArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(float[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(float[] array) {
		new FloatArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(int[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(int[] array) {
		new IntegerArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(long[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(long[] array) {
		new LongArray(array).sort();
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(short[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(short[] array) {
		new ShortArray(array).sort();
	}

	//.sort(A, int, int)

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(Object[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void sort(E[] array, int beginIndex, int endIndex) {
		new ObjectArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param comparator the comparator to determine the order of the array.  A {@code null} value
	 *                   indicates that the elements' {@linkplain Comparable natural ordering}
	 *                   should be used.
	 * @param <E>        the type of the element.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(Object[], int, int, Comparator)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> void sort(E[] array, int beginIndex, int endIndex, Comparator<? super E> comparator) {
		new ObjectArray(array, beginIndex, endIndex).sort(comparator);
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(boolean[] array, int beginIndex, int endIndex) {
		new BooleanArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(byte[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(byte[] array, int beginIndex, int endIndex) {
		new ByteArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(char[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(char[] array, int beginIndex, int endIndex) {
		new CharacterArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(double[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(double[] array, int beginIndex, int endIndex) {
		new DoubleArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(float[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(float[] array, int beginIndex, int endIndex) {
		new FloatArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(int[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(int[] array, int beginIndex, int endIndex) {
		new IntegerArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(long[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(long[] array, int beginIndex, int endIndex) {
		new LongArray(array, beginIndex, endIndex).sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#sort(short[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static void sort(short[] array, int beginIndex, int endIndex) {
		new ShortArray(array, beginIndex, endIndex).sort();
	}

	//.spliterator(A)

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param <E>   the type of the elements.
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#spliterator(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> ObjectArray.Spliterator spliterator(E[] array) {
		return new ObjectArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static BooleanArray.Spliterator spliterator(boolean[] array) {
		return new BooleanArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ByteArray.Spliterator spliterator(byte[] array) {
		return new ByteArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static CharacterArray.Spliterator spliterator(char[] array) {
		return new CharacterArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#spliterator(double[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static DoubleArray.Spliterator spliterator(double[] array) {
		return new DoubleArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static FloatArray.Spliterator spliterator(float[] array) {
		return new FloatArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#spliterator(int[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static IntegerArray.Spliterator spliterator(int[] array) {
		return new IntegerArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#spliterator(long[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static LongArray.Spliterator spliterator(long[] array) {
		return new LongArray(array).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ShortArray.Spliterator spliterator(short[] array) {
		return new ShortArray(array).spliterator();
	}

	//.spliterator(A, int, int)

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param <E>        the type of the elements.
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#spliterator(Object[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> ObjectArray.Spliterator spliterator(E[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ObjectArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static BooleanArray.Spliterator spliterator(boolean[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new BooleanArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ByteArray.Spliterator spliterator(byte[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ByteArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static CharacterArray.Spliterator spliterator(char[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new CharacterArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#spliterator(double[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static DoubleArray.Spliterator spliterator(double[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new DoubleArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static FloatArray.Spliterator spliterator(float[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new FloatArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#spliterator(int[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static IntegerArray.Spliterator spliterator(int[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new IntegerArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#spliterator(long[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static LongArray.Spliterator spliterator(long[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new LongArray(array, beginIndex, endIndex).spliterator();
	}

	/**
	 * Returns a {@link Spliterator} covering the specified range of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static ShortArray.Spliterator spliterator(short[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ShortArray(array, beginIndex, endIndex).spliterator();
	}

	//.stream(A)

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param <E>   the type of the elements.
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#stream(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> Stream<E> stream(E[] array) {
		return new ObjectArray(array).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Boolean> stream(boolean[] array) {
		return new BooleanArray(array).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Byte> stream(byte[] array) {
		return new ByteArray(array).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Character> stream(char[] array) {
		return new CharacterArray(array).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#stream(double[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static DoubleStream stream(double[] array) {
		return new DoubleArray(array).doubleStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Float> stream(float[] array) {
		return new FloatArray(array).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#stream(int[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static IntStream stream(int[] array) {
		return new IntegerArray(array).intStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#stream(long[])
	 * @since 0.1.5 ~2020.08.10
	 */
	public static LongStream stream(long[] array) {
		return new LongArray(array).longStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Short> stream(short[] array) {
		return new ShortArray(array).stream();
	}

	//.stream(A, int, int)

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param <E>        the type of the array elements.
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#stream(Object[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static <E> Stream<E> stream(E[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ObjectArray(array, beginIndex, endIndex).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Boolean> stream(boolean[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new BooleanArray(array, beginIndex, endIndex).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Byte> stream(byte[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ByteArray(array, beginIndex, endIndex).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Character> stream(char[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new CharacterArray(array, beginIndex, endIndex).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#stream(double[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static DoubleStream stream(double[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new DoubleArray(array, beginIndex, endIndex).doubleStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Float> stream(float[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new FloatArray(array, beginIndex, endIndex).stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#stream(int[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static IntStream stream(int[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new IntegerArray(array, beginIndex, endIndex).intStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#stream(long[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	public static LongStream stream(long[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new LongArray(array, beginIndex, endIndex).longStream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.10
	 */
	public static Stream<Short> stream(short[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ShortArray(array, beginIndex, endIndex).stream();
	}

	//.toString(A)

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <E>   the type of the elements.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <E> String toString(E... array) {
		return ObjectArray.toString(array);
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
		return BooleanArray.toString(array);
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(byte[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(byte[] array) {
		return ByteArray.toString(array);
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(char[] array) {
		return CharacterArray.toString(array);
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(double[] array) {
		return DoubleArray.toString(array);
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
		return FloatArray.toString(array);
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
		return IntegerArray.toString(array);
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
		return LongArray.toString(array);
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(short[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(short[] array) {
		return ShortArray.toString(array);
	}
}
