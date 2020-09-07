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

import cufy.util.function.*;

import java.util.Objects;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * An Utility class for raw arrays. Supporting various kinds of array operations. All methods
 * accepts any kind of array.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Arrays} utility class with
 * the same behaviour. So switching to import this class will not make any changes to files
 * previously imported {@link java.util.Arrays}.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.06.11
 */
public interface Arrays {
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
	 *                                   Or if an element can not be stored in the given {@code
	 *                                   dest}. Or if no available method to copy elements from the
	 *                                   given {@code src} to the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	static void copy(Object src, int srcPos, Object dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		if (!src.getClass().isArray())
			//throw ArrayStoreException instead
			throw new ArrayStoreException(
					"hardcopy: source type " + src.getClass().getName() + " is not an array"
			);
		if (!dest.getClass().isArray())
			//throw ArrayStoreException instead
			throw new ArrayStoreException(
					"hardcopy: destination type " + dest.getClass().getName() + " is not an array"
			);

		Arrays.array0(src, srcPos, srcPos + length)
				.copy(dest, destPos, length);
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
	static <E> ObjectArray<E> newArray(int length) {
		return new ObjectArray(length);
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code length}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param length        the length of the new actual array backing the construct array.
	 * @param <A>           the type of the array.
	 * @param <E>           the type of the elements.
	 * @return a new array backed by a new actual array of the given {@code componentType} that have
	 * 		the given {@code length}.
	 * @throws NullPointerException       if the given {@code componentType} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	static <A, E> Array<A, E> newArray(Class<E> componentType, int length) {
		//with char|boolean|byte|double|float|int|long|short primitive//
		if (componentType == char.class)
			return (Array) new CharArray(length);
		//endwith//

		return (Array) new ObjectArray(componentType, length);
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code dimensions}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param dimensions    the dimensions of the new actual array backing the construct array.
	 * @param <A>           the type of the array.
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
	static <A, E> Array<A, E> newArray(Class componentType, int... dimensions) {
		return dimensions.length == 1 ?
			   Arrays.newArray(componentType, dimensions[0]) :
			   new ObjectArray(componentType, dimensions);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @param <E>   the type of the elements.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	static <E> ObjectArray<E> array(E... array) {
		return new ObjectArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array      the array to be wrapped.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @param <E>        the type of the elements.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length}.
	 * @throws IllegalArgumentException       if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <E> ObjectArray<E> array(E[] array, int beginIndex, int endIndex) {
		return new ObjectArray(array, beginIndex, endIndex);
	}

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
	static <E> ObjectArray.ObjectArrayList asList(E... array) {
		return new ObjectArray(array)
				.list();
	}

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
	static <E, K extends E, V extends E> ObjectArray<E>.ObjectArrayMap<K, V> asMap(E... array) {
		return new ObjectArray(array)
				.map();
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
	 * @param array   the array to be searched.
	 * @param element the value to be searched for.
	 * @param <E>     the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(Object[], Object)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> int binarySearch(E[] array, E element) {
		return new ObjectArray(array)
				.binarySearch(element);
	}

	/**
	 * Searches the specified array for the specified object using the binary search algorithm.  The
	 * array must be sorted into ascending order according to the specified comparator (as by the
	 * {@link #sort(Object[], Comparator) sort(T[], Comparator)} method) prior to making this call.
	 * If it is not sorted, the results are undefined. If the array contains multiple elements equal
	 * to the specified object, there is no guarantee which one will be found.
	 *
	 * @param array      the array to be searched.
	 * @param element    the value to be searched for.
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
	static <E> int binarySearch(E[] array, E element, Comparator<? super E> comparator) {
		return new ObjectArray(array)
				.binarySearch(element, comparator);
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
	 * @param element    the value to be searched for.
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
	static <E> int binarySearch(E[] array, int beginIndex, int endIndex, E element) {
		return new ObjectArray(array, beginIndex, endIndex)
				.binarySearch(element);
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
	 * @param element    the value to be searched for.
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
	static <E> int binarySearch(E[] array, int beginIndex, int endIndex, E element, Comparator<? super E> comparator) {
		return new ObjectArray(array, beginIndex, endIndex)
				.binarySearch(element, comparator);
	}

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
	static <E> E[] copyOf(E[] array, int length) {
		return new ObjectArray<>(array)
				.copy(length);
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
	static <E extends T, T> T[] copyOf(E[] array, int length, Class<? extends T[]> klass) {
		if (Object[].class.isAssignableFrom(klass))
			throw new ArrayStoreException("Not object array");
		return new ObjectArray<>(array)
				.copy(klass, length);
	}

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
	static <E> E[] copyOfRange(E[] array, int beginIndex, int endIndex) {
		return new ObjectArray<>(array, beginIndex, array.length)
				.copy(endIndex - beginIndex);
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
	static <E> E[] copyOfRange(Object[] array, int beginIndex, int endIndex, Class<? extends E[]> klass) {
		if (!Object[].class.isAssignableFrom(klass))
			throw new ArrayStoreException("Not object array");
		return new ObjectArray<>(array, beginIndex, array.length)
				.copy(klass, endIndex - beginIndex);
	}

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths,
	 * deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements in the first array.
	 * @param <U>   the type of the elements in the second array.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep
	 * 		lengths, deep elements, and deep orderings.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T, U> boolean deepEquals(T[] array, U... other) {
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
				Arrays.deepEquals((Object[]) element, (Object[]) e))
				continue;
			//with char|boolean|byte|double|float|int|long|short primitive//
			if (element instanceof char[] && e instanceof char[] &&
				Arrays.equals((char[]) element, (char[]) e))
				continue;
			//endwith//

			return false;
		}

		return true;
	}

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @param <E>   the type of the elements.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @see java.util.Arrays#deepHashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> int deepHashCode(E... array) {
		if (array == null)
			return 0;

		int deepHashCode = 1;
		for (int i = 0; i < array.length; i++) {
			Object element = array[i];

			if (element == null)
				deepHashCode = 31 * deepHashCode;
			if (element instanceof Object[])
				deepHashCode = 31 * deepHashCode + Arrays.deepHashCode((Object[]) element);
				//with char|boolean|byte|double|float|int|long|short primitive//
			else if (element instanceof char[])
				deepHashCode = 31 * deepHashCode + Arrays.hashCode((char[]) element);
				//endwith//
			else
				deepHashCode = 31 * deepHashCode + element.hashCode();
		}

		return deepHashCode;
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <E>   the type of the elements.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> String deepToString(E... array) {
		if (array == null)
			return "null";

		int length = 20 * array.length;
		if (array.length != 0 && length <= 0)
			length = Integer.MAX_VALUE;

		StringBuilder builder = new StringBuilder(length);
		Arrays.deepToString(array, builder, new java.util.ArrayList());
		return builder.toString();
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array   the array to build a string representation for it.
	 * @param builder the builder to append the string representation to.
	 * @param dejaVu  the arrays that has been seen before.
	 * @param <E>     the type of the elements.
	 * @throws NullPointerException if the given {@code builder} or {@code dejaVu} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> void deepToString(E[] array, StringBuilder builder, Collection<Object[]> dejaVu) {
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

				if (element instanceof Object[])
					if (dejaVu.contains(element))
						builder.append("[...]");
					else
						Arrays.deepToString((Object[]) element, builder, dejaVu);
					//with char|boolean|byte|double|float|int|long|short primitive//
				else if (element instanceof char[])
					builder.append(Arrays.toString((char[]) element));
					//endwith//
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

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements in the first array.
	 * @param <U>   the type of the elements in the second array.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @see java.util.Arrays#equals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <T, U> boolean equals(T[] array, U... other) {
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
	static <E> void fill(E[] array, E element) {
		new ObjectArray(array)
				.fill(element);
	}

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
	static <E> void fill(E[] array, int beginIndex, int endIndex, E element) {
		new ObjectArray(array, beginIndex, endIndex)
				.fill(element);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @param <E>   the type of the elements.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> int hashCode(E... array) {
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
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param <E>   the type of the elements.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> ObjectArray.ObjectArrayIterator iterator(E... array) {
		return new ObjectArray(array)
				.iterator();
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(Object[], BinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> void parallelPrefix(E[] array, BinaryOperator<E> operator) {
		new ObjectArray(array)
				.parallelPrefix(operator);
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
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#parallelPrefix(Object[], int, int, BinaryOperator)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> void parallelPrefix(E[] array, int beginIndex, int endIndex, BinaryOperator<E> operator) {
		new ObjectArray(array, beginIndex, endIndex)
				.parallelPrefix(operator);
	}

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
	static <E> void parallelSetAll(E[] array, IntFunction<? extends E> function) {
		new ObjectArray(array)
				.parallelSetAll(function);
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(Comparable[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E extends Comparable<? super E>> void parallelSort(E[] array) {
		new ObjectArray(array)
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
	static <E> void parallelSort(E[] array, Comparator<? super E> comparator) {
		new ObjectArray(array)
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
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @see java.util.Arrays#parallelSort(Comparable[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E extends Comparable<? super E>> void parallelSort(E[] array, int beginIndex, int endIndex) {
		new ObjectArray(array, beginIndex, endIndex)
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
	static <E> void parallelSort(E[] array, int beginIndex, int endIndex, Comparator<? super E> comparator) {
		new ObjectArray(array, beginIndex, endIndex)
				.parallelSort(comparator);
	}

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
	static <E> void setAll(E[] array, IntFunction<? extends E> function) {
		new ObjectArray(array)
				.setAll(function);
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> void sort(E[] array) {
		new ObjectArray(array)
				.sort();
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
	static <E> void sort(E[] array, Comparator<? super E> comparator) {
		new ObjectArray(array)
				.sort(comparator);
	}

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
	static <E> void sort(E[] array, int beginIndex, int endIndex) {
		new ObjectArray(array, beginIndex, endIndex)
				.sort();
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
	static <E> void sort(E[] array, int beginIndex, int endIndex, Comparator<? super E> comparator) {
		new ObjectArray(array, beginIndex, endIndex)
				.sort(comparator);
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @param <E>   the type of the elements.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#spliterator(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> ObjectArray.ObjectArraySpliterator spliterator(E... array) {
		return new ObjectArray(array)
				.spliterator();
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
	 * @param <E>        the type of the elements.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#spliterator(Object[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> ObjectArray.ObjectArraySpliterator spliterator(E[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ObjectArray(array, beginIndex, endIndex)
				.spliterator();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @param <E>   the type of the elements.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#stream(Object[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> Stream<E> stream(E... array) {
		return new ObjectArray(array)
				.stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @param <E>        the type of the array elements.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#stream(Object[], int, int)
	 * @since 0.1.5 ~2020.08.10
	 */
	static <E> Stream<E> stream(E[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new ObjectArray(array, beginIndex, endIndex)
				.stream();
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <E>   the type of the elements.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static <E> String toString(E... array) {
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
	static <A, E> Array<A, E> array0(A array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return (Array) new ObjectArray((Object[]) array);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return (Array) new CharArray((char[]) array);
		/* endwith */

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
	static <A, E> Array<A, E> array0(A array, int beginIndex, int endIndex) {
		java.util.Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return (Array) new ObjectArray((Object[]) array, beginIndex, endIndex);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return (Array) new CharArray((char[]) array, beginIndex, endIndex);
		/* endwith */

		throw new IllegalArgumentException("Not an array");
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> List<E> asList0(A array) {
		return Arrays.<A, E>array0(array)
				.list();
	}

	/**
	 * Construct a new map backed by the given {@code array}.
	 *
	 * @param array the array backing the returned map.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values
	 * @return a map containing the given pairs.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array. Or if {@code
	 *                                  array.length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E, K extends E, V extends E> Map<K, V> asMap0(A array) {
		return Arrays.<A, E>array0(array)
				.<K, V>map();
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
	 * @param array   the array to be searched.
	 * @param element the value to be searched for.
	 * @param <A>     the type of the array.
	 * @param <E>     the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> int binarySearch0(A array, E element) {
		return Arrays.<A, E>array0(array)
				.binarySearch(element);
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
	 * @param element    the value to be searched for.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> int binarySearch0(A array, int beginIndex, int endIndex, E element) {
		return Arrays.<A, E>array0(array, beginIndex, endIndex)
				.binarySearch(element);
	}

	/**
	 * Construct a new copy of the given {@code array} with the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length of the constructed array.
	 * @param <A>    the type of the array.
	 * @param <E>    the type of the elements.
	 * @return a new copy of the given {@code array} with the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @throws IllegalArgumentException   if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> A copyOf0(A array, int length) {
		return Arrays.<A, E>array0(array)
				.copy(length);
	}

	/**
	 * Construct a new copy of the given {@code array} containing the elements from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the initial index of the range to be copied, inclusive.
	 * @param endIndex   the final index of the range to be copied, exclusive. (This index may lie
	 *                   outside the array.)
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code
	 * 		beginIndex} to the given {@code endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> A copyOfRange0(A array, int beginIndex, int endIndex) {
		int arrayLength = java.lang.reflect.Array.getLength(array);
		return Arrays.<A, E>array0(array, beginIndex, arrayLength)
				.copy(endIndex - beginIndex);
	}

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths,
	 * deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements in the first array.
	 * @param <U>   the type of the elements in the second array.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep
	 * 		lengths, deep elements, and deep orderings.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an
	 *                                  array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U> boolean deepEquals0(T array, U other) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");
		if (other != null && !other.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[] && other instanceof Object[])
			return Arrays.deepEquals((Object[]) array, (Object[]) other);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[] && other instanceof char[])
			return Arrays.equals((char[]) array, (char[]) other);
		/* endwith */

		return array == other;
	}

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @param <A>   the type of the array.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A> int deepHashCode0(A array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return Arrays.deepHashCode((Object[]) array);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return Arrays.hashCode((char[]) array);
		/* endwith */

		return 0;
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <A>   the type of the array.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A> String deepToString0(A array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return Arrays.deepToString((Object[]) array);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return Arrays.toString((char[]) array);
		/* endwith */

		return "null";
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements,
	 * and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the first array.
	 * @param <U>   the type of the second array.
	 * @return true, if the given {@code array} does equals the given {@code other} in length,
	 * 		elements, and order.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an
	 *                                  array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <T, U> boolean equals0(T array, U other) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");
		if (other != null && !other.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[] && other instanceof Object[])
			return Arrays.equals((Object[]) array, (Object[]) other);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[] && other instanceof char[])
			return Arrays.equals((char[]) array, (char[]) other);
		/* endwith */

		return array == other;
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @param <A>     the type of the array.
	 * @param <E>     the type of the elements.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws ArrayStoreException      if the given {@code element} can not be stored in the given
	 *                                  {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void fill0(A array, E element) {
		Arrays.<A, E>array0(array)
				.fill(element);
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array} from the given
	 * {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws ArrayStoreException       if the given {@code element} can not be stored in the given
	 *                                   {@code array}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void fill0(A array, int beginIndex, int endIndex, E element) {
		Arrays.<A, E>array0(array, beginIndex, endIndex)
				.fill(element);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @param <A>   the type of the array.
	 * @return the hash code of the elements of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A> int hashCode0(A array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return Arrays.hashCode((Object[]) array);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return Arrays.hashCode((char[]) array);
		/* endwith */

		return 0;
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> Iterator<E> iterator0(A array) {
		return Arrays.<A, E>array0(array)
				.iterator();
	}

	/**
	 * Cumulates, in parallel, each element of the given {@code array} in place, using the supplied
	 * function. For example if the array initially holds {@code [2, 1, 0, 3]} and the operation
	 * performs addition, then upon return the array holds {@code [2, 3, 3, 6]}. Parallel prefix
	 * computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param array    the array, which is modified in-place by this method.
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @param <A>      the type of the array.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException     if the given {@code array} or {@code operator} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void parallelPrefix0(A array, BinaryOperator<E> operator) {
		Arrays.<A, E>array0(array)
				.parallelPrefix(operator);
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
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} or {@code operator} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void parallelPrefix0(A array, int beginIndex, int endIndex, BinaryOperator<E> operator) {
		Arrays.<A, E>array0(array, beginIndex, endIndex)
				.parallelPrefix(operator);
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned
	 * from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <A>      the type of the array.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void parallelSetAll0(A array, IntFunction<? extends E> function) {
		Arrays.<A, E>array0(array)
				.parallelSetAll(function);
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void parallelSort0(A array) {
		Arrays.<A, E>array0(array)
				.parallelSort();
	}

	/**
	 * In parallel, sorts the specified range of the array into ascending numerical order. The range
	 * to be sorted extends from the given {@code beginIndex}, inclusive, to the given {@code
	 * endIndex}, exclusive. If {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void parallelSort0(A array, int beginIndex, int endIndex) {
		Arrays.<A, E>array0(array, beginIndex, endIndex)
				.parallelSort();
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <A>      the type of the array.
	 * @param <E>      the type of the elements.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void setAll0(A array, IntFunction<E> function) {
		Arrays.<A, E>array0(array)
				.setAll(function);
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void sort0(A array) {
		Arrays.<A, E>array0(array)
				.sort();
	}

	/**
	 * Sorts the specified range of the array into ascending order. The range to be sorted extends
	 * from the given {@code beginIndex}, inclusive, to the given {@code endIndex}, exclusive. If
	 * {@code beginIndex == endIndex}, the range to be sorted is empty.
	 *
	 * @param array      the array to be sorted.
	 * @param beginIndex the index of the first element, inclusive, to be sorted.
	 * @param endIndex   the index of the last element, exclusive, to be sorted.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if {@code
	 *                                   beginIndex > endIndex}.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> void sort0(A array, int beginIndex, int endIndex) {
		Arrays.<A, E>array0(array, beginIndex, endIndex)
				.sort();
	}

	/**
	 * Returns a {@link Spliterator} covering all of the specified array.
	 * <p>
	 * The spliterator reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, {@link
	 * Spliterator#ORDERED}, and {@link Spliterator#IMMUTABLE}.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> Spliterator<E> spliterator0(A array) {
		return Arrays.<A, E>array0(array)
				.spliterator();
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
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the elements.
	 * @return a spliterator for the array elements.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> Spliterator<E> spliterator0(A array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return Arrays.<A, E>array0(array, beginIndex, endIndex)
				.spliterator();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @param <A>   the type of the array.
	 * @param <E>   the type of the elements.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> Stream<E> stream0(A array) {
		return Arrays.<A, E>array0(array)
				.stream();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified range of the specified array as its
	 * source.
	 *
	 * @param array      the array, assumed to be unmodified during use.
	 * @param beginIndex the first index to cover, inclusive.
	 * @param endIndex   index immediately past the last index to cover.
	 * @param <A>        the type of the array.
	 * @param <E>        the type of the array elements.
	 * @return a {@code Stream} for the array range.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length} or {@code beginIndex > endIndex}.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A, E> Stream<E> stream0(A array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return Arrays.<A, E>array0(array, beginIndex, endIndex)
				.stream();
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <A>   the type of the array.
	 * @return a string representation of the contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.08.30
	 */
	static <A> String toString0(A array) {
		if (array != null && !array.getClass().isArray())
			throw new IllegalArgumentException("Not an array");

		if (array instanceof Object[])
			return Arrays.toString((Object[]) array);
		/* with char|boolean|byte|double|float|int|long|short primitive */
		if (array instanceof char[])
			return Arrays.toString((char[]) array);
		/* endwith */

		return "null";
	}
	/*
	with char|boolean|byte|double|float|int|long|short primitive
	*//*
	define IntTo ////
	if boolean|byte|char|double|float|long|short primitive //IntToCharFunction//
	elif int primitive //IntUnaryOperator//
	endif ////
	enddefine
	*//*
	define Stream ////
	if boolean|byte|char|float|short primitive //Stream<Character>//
	elif double|int|long primitive //CharStream//
	endif ////
	enddefine
	*/

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array the array to be wrapped.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.11
	 */
	static CharArray array(char[] array) {
		return new CharArray(array);
	}

	/**
	 * Construct a new array wrapper for the given {@code array}.
	 *
	 * @param array      the array to be wrapped.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @return an array wrapper for the given {@code array}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length}.
	 * @throws IllegalArgumentException       if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static CharArray array(char[] array, int beginIndex, int endIndex) {
		return new CharArray(array, beginIndex, endIndex);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static CharArray.CharArrayList asList(char[] array) {
		return new CharArray(array)
				.list();
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
	static CharArray.CharArrayMap asMap(char[] array) {
		return new CharArray(array)
				.map();
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
	 * @param array   the array to be searched.
	 * @param element the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#binarySearch(char[], char)
	 * @since 0.1.5 ~2020.08.10
	 */
	static int binarySearch(char[] array, char element) {
		return new CharArray(array)
				.binarySearch(element);
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
	 * @param element    the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                   array.length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @see java.util.Arrays#binarySearch(char[], int, int, char)
	 * @since 0.1.5 ~2020.08.10
	 */
	static int binarySearch(char[] array, int beginIndex, int endIndex, char element) {
		return new CharArray(array, beginIndex, endIndex)
				.binarySearch(element);
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
	static char[] copyOf(char[] array, int length) {
		return new CharArray(array)
				.copy(length);
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
	static char[] copyOfRange(char[] array, int beginIndex, int endIndex) {
		return new CharArray(array, beginIndex, array.length)
				.copy(endIndex - beginIndex);
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
	static boolean equals(char[] array, char... other) {
		if (array == other)
			return true;
		if (array.length == other.length)
			for (int i = 0; i < array.length; i++) {
				char element = other[i];
				char e = array[i];

				if (element == e)
					continue;

				return false;
			}

		return false;
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
	static void fill(char[] array, char element) {
		new CharArray(array)
				.fill(element);
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
	static void fill(char[] array, int beginIndex, int endIndex, char element) {
		new CharArray(array, beginIndex, endIndex)
				.fill(element);
	}

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static int hashCode(char[] array) {
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			char e = array[i];
			hashCode = 31 * hashCode + Character.hashCode(e);
		}

		return hashCode;
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	static CharArray.CharArrayIterator iterator(char[] array) {
		return new CharArray(array)
				.iterator();
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
	static void parallelPrefix(char[] array, CharBinaryOperator operator) {
		new CharArray(array)
				.parallelPrefix(operator);
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
	static void parallelPrefix(char[] array, int beginIndex, int endIndex, CharBinaryOperator operator) {
		new CharArray(array, beginIndex, endIndex)
				.parallelPrefix(operator);
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
	static void parallelSetAll(char[] array, /*IntTo*/ function) {
		new CharArray(array)
				.parallelSetAll(function);
	}

	/**
	 * In parallel, sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#parallelSort(char[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static void parallelSort(char[] array) {
		new CharArray(array)
				.parallelSort();
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
	static void parallelSort(char[] array, int beginIndex, int endIndex) {
		new CharArray(array, beginIndex, endIndex)
				.parallelSort();
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
	static void setAll(char[] array, IntFunction<Character> function) {
		new CharArray(array)
				.setAll(function);
	}

	/**
	 * Sorts the given {@code array} into ascending numerical order.
	 *
	 * @param array the array to be sorted.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#sort(char[])
	 * @since 0.1.5 ~2020.08.10
	 */
	static void sort(char[] array) {
		new CharArray(array)
				.sort();
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
	static void sort(char[] array, int beginIndex, int endIndex) {
		new CharArray(array, beginIndex, endIndex)
				.sort();
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
	static CharArray.CharArraySpliterator spliterator(char[] array) {
		return new CharArray(array)
				.spliterator();
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
	static CharArray.CharArraySpliterator spliterator(char[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		return new CharArray(array, beginIndex, endIndex)
				.spliterator();
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its source.
	 *
	 * @param array the array, assumed to be unmodified during use.
	 * @return a {@code Stream} for the array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.10
	 */
	static /*Stream*/ stream(char[] array) {
		/* if double|int|long primitive */
		return new CharArray(array)
				.charStream();
		/* elif boolean|byte|char|float|short primitive */
		return new CharArray(array)
				.stream();
		/* endif */
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
	static /*Stream*/ stream(char[] array, int beginIndex, int endIndex) {
		if (beginIndex > endIndex)
			//since the constructor throws IllegalArgumentException for this condition
			throw new ArrayIndexOutOfBoundsException(
					"beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
		/* if double|int|long primitive */
		return new CharArray(array, beginIndex, endIndex)
				.charStream();
		/* elif boolean|byte|char|float|short primitive */
		return new CharArray(array, beginIndex, endIndex)
				.stream();
		/* endif */
	}

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	static String toString(char[] array) {
		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			char e = array[i];

			builder.append(e);

			i++;
			if (i >= array.length)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}
	/*
	endwith
	*/
}
