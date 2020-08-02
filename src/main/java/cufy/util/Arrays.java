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

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * An Utility class for raw arrays. Supporting various kinds of array operations. All methods accepts any kind of array and has a
 * reflection way method and a switcher method.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Arrays} utility class with the same behaviour. So
 * switching to import this class will not make any changes to files previously imported {@link java.util.Arrays}.
 * <p>
 * Note: the class is not final nor have a private constructor ;). Anyone can override this class to provide more static methods
 * meanwhile provide access to the methods of this class with the sub-class's signature. But, don't forget that this class is not
 * made for OOP! So, any attempt to construct any sub-class of this class will fail no matter what.
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

	//all

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @param <T>      the type of the elements.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> int all(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			T e = elements[i];

			for (int j = 0; j < array.length; j++) {
				T a = array[j];

				if (e == a || e != null && e.equals(a))
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			boolean e = elements[i];

			for (int j = 0; j < array.length; j++) {
				boolean a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			byte e = elements[i];

			for (int j = 0; j < array.length; j++) {
				byte a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			char e = elements[i];

			for (int j = 0; j < array.length; j++) {
				char a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			long e = Double.doubleToLongBits(elements[i]);

			for (int j = 0; j < array.length; j++) {
				long a = Double.doubleToLongBits(array[j]);

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			int e = Float.floatToIntBits(elements[i]);

			for (int j = 0; j < array.length; j++) {
				int a = Float.floatToIntBits(array[j]);

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			int e = elements[i];

			for (int j = 0; j < array.length; j++) {
				int a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			long e = elements[i];

			for (int j = 0; j < array.length; j++) {
				long a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Determine the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int i = 0; i < elements.length; i++) {
			short e = elements[i];

			for (int j = 0; j < array.length; j++) {
				short a = array[j];

				if (e == a)
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Using Reflection, determine the index to the first element in the given {@code elements} that does not equal any element in
	 * the given {@code array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException     if the given {@code array} or {@code elements} is null.
	 * @throws IllegalArgumentException if the given {@code array} or {@code elements} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all0(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(elements, Objects::isArray, "elements");

		int m = Array.getLength(elements);
		int n = Array.getLength(array);

		for0:
		for (int i = 0; i < m; i++) {
			Object e = Array.get(elements, i);

			for (int j = 0; j < n; j++) {
				Object a = Array.get(array, j);

				if (e == a || e != null && e.equals(a))
					continue for0;
			}

			return i;
		}

		return -1;
	}

	/**
	 * Using the best {@link #all(Object[], Object[])} method, determine the index to the first element in the given {@code
	 * elements} that does not equal any element in the given {@code array}.
	 *
	 * @param array    the array to be checked.
	 * @param elements the elements to be matched to the elements of the given {@code array}.
	 * @return the index to the first element in the given {@code elements} that does not equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException     if the given {@code array} or {@code elements} is null.
	 * @throws IllegalArgumentException if the given {@code array} or {@code elements} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int all1(Object array, Object elements) {
		if (array instanceof Object[] && elements instanceof Object[])
			return Arrays.all((Object[]) array, (Object[]) elements);
		if (array instanceof boolean[] && elements instanceof boolean[])
			return Arrays.all((boolean[]) array, (boolean[]) elements);
		if (array instanceof byte[] && elements instanceof byte[])
			return Arrays.all((byte[]) array, (byte[]) elements);
		if (array instanceof char[] && elements instanceof char[])
			return Arrays.all((char[]) array, (char[]) elements);
		if (array instanceof double[] && elements instanceof double[])
			return Arrays.all((double[]) array, (double[]) elements);
		if (array instanceof float[] && elements instanceof float[])
			return Arrays.all((float[]) array, (float[]) elements);
		if (array instanceof int[] && elements instanceof int[])
			return Arrays.all((int[]) array, (int[]) elements);
		if (array instanceof long[] && elements instanceof long[])
			return Arrays.all((long[]) array, (long[]) elements);
		if (array instanceof short[] && elements instanceof short[])
			return Arrays.all((short[]) array, (short[]) elements);

		return Arrays.all0(array, elements);
	}

	//any

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @param <T>      the type of the elements.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> int any(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			T e = elements[i];

			for (int j = 0; j < array.length; j++) {
				T a = array[j];

				if (e == a || e != null && e.equals(a))
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			boolean e = elements[i];

			for (int j = 0; j < array.length; j++) {
				boolean a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			byte e = elements[i];

			for (int j = 0; j < array.length; j++) {
				byte a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			char e = elements[i];

			for (int j = 0; j < array.length; j++) {
				char a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			long e = Double.doubleToLongBits(elements[i]);

			for (int j = 0; j < array.length; j++) {
				long a = Double.doubleToLongBits(array[j]);

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			int e = Float.floatToIntBits(elements[i]);

			for (int j = 0; j < array.length; j++) {
				int a = Float.floatToIntBits(array[j]);

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			int e = elements[i];

			for (int j = 0; j < array.length; j++) {
				int a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			long e = elements[i];

			for (int j = 0; j < array.length; j++) {
				long a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException if the given {@code array} or {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int i = 0; i < elements.length; i++) {
			short e = elements[i];

			for (int j = 0; j < array.length; j++) {
				short a = array[j];

				if (e == a)
					return i;
			}
		}

		return -1;
	}

	/**
	 * Using Reflection, determine the index of the first element in the given {@code elements} that does equal any element in the
	 * given {@code array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException     if the given {@code array} or {@code elements} is null.
	 * @throws IllegalArgumentException if the given {@code array} or {@code elements} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any0(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(elements, Objects::isArray, "elements");

		int m = Array.getLength(elements);
		int n = Array.getLength(array);
		for (int i = 0; i < m; i++) {
			Object e = Array.get(elements, i);

			for (int j = 0; j < n; j++) {
				Object a = Array.get(array, j);

				if (e == a || e != null && e.equals(a))
					return i;
			}
		}

		return -1;
	}

	/**
	 * Using the best {@link #any(Object[], Object[])} method, determine the index of the first element in the given {@code
	 * elements} that does equal any element in the given {@code array}.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in the given {@code
	 * 		array}. Or -1 if no such element found.
	 * @throws NullPointerException     if the given {@code array} or {@code elements} is null.
	 * @throws IllegalArgumentException if the given {@code array} or {@code elements} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int any1(Object array, Object elements) {
		if (array instanceof Object[] && elements instanceof Object[])
			return Arrays.any((Object[]) array, (Object[]) elements);
		if (array instanceof boolean[] && elements instanceof boolean[])
			return Arrays.any((boolean[]) array, (boolean[]) elements);
		if (array instanceof byte[] && elements instanceof byte[])
			return Arrays.any((byte[]) array, (byte[]) elements);
		if (array instanceof char[] && elements instanceof char[])
			return Arrays.any((char[]) array, (char[]) elements);
		if (array instanceof double[] && elements instanceof double[])
			return Arrays.any((double[]) array, (double[]) elements);
		if (array instanceof float[] && elements instanceof float[])
			return Arrays.any((float[]) array, (float[]) elements);
		if (array instanceof int[] && elements instanceof int[])
			return Arrays.any((int[]) array, (int[]) elements);
		if (array instanceof long[] && elements instanceof long[])
			return Arrays.any((long[]) array, (long[]) elements);
		if (array instanceof short[] && elements instanceof short[])
			return Arrays.any((short[]) array, (short[]) elements);

		return Arrays.any0(array, elements);
	}

	//asList

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @param <T>   the type of the elements.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> cufy.util.array.ArrayList<T> asList(T... array) {
		return new cufy.util.array.ArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static BooleanArrayList asList(boolean[] array) {
		return new BooleanArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ByteArrayList asList(byte[] array) {
		return new ByteArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static CharacterArrayList asList(char[] array) {
		return new CharacterArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static DoubleArrayList asList(double[] array) {
		return new DoubleArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static FloatArrayList asList(float[] array) {
		return new FloatArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static IntegerArrayList asList(int[] array) {
		return new IntegerArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static LongArrayList asList(long[] array) {
		return new LongArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ShortArrayList asList(short[] array) {
		return new ShortArrayList(array);
	}

	/**
	 * Using Reflection, construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ArrayList0 asList0(Object array) {
		return new ArrayList0(array);
	}

	/**
	 * Using the best {@link #asList(Object[])} method, construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the returned list.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static List asList1(Object array) {
		if (array instanceof Object[])
			return Arrays.asList((Object[]) array);
		if (array instanceof boolean[])
			return Arrays.asList((boolean[]) array);
		if (array instanceof byte[])
			return Arrays.asList((byte[]) array);
		if (array instanceof char[])
			return Arrays.asList((char[]) array);
		if (array instanceof double[])
			return Arrays.asList((double[]) array);
		if (array instanceof float[])
			return Arrays.asList((float[]) array);
		if (array instanceof int[])
			return Arrays.asList((int[]) array);
		if (array instanceof long[])
			return Arrays.asList((long[]) array);
		if (array instanceof short[])
			return Arrays.asList((short[]) array);

		return Arrays.asList0(array);
	}

	//todo binarySearch +(boolean | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(Object[], Object)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> int binarySearch(T[] array, T key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(Object[], Object, Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> int binarySearch(T[] array, T key, Comparator<? super T> comparator) {
		return java.util.Arrays.binarySearch(array, key, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(byte[], byte)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(byte[] array, byte key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(char[], char)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(char[] array, char key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(double[], double)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(double[] array, double key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(float[], float)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(float[] array, float key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(int[], int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(int[] array, int key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(long[], long)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(long[] array, long key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(short[], short)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(short[] array, short key) {
		return java.util.Arrays.binarySearch(array, key);
	}

	//todo binarySearch +(boolean | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(Object[], int, int, Object)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> int binarySearch(T[] array, int beginIndex, int endIndex, T key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(Object[], int, int, Object, Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> int binarySearch(T[] array, int beginIndex, int endIndex, T key, Comparator<? super T> comparator) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(byte[], int, int, byte)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(byte[] array, int beginIndex, int endIndex, byte key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(char[], int, int, char)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(char[] array, int beginIndex, int endIndex, char key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(double[], int, int, double)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(double[] array, int beginIndex, int endIndex, double key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(float[], int, int, float)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(float[] array, int beginIndex, int endIndex, float key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(int[], int, int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(int[] array, int beginIndex, int endIndex, int key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(long[], int, int, long)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(long[] array, int beginIndex, int endIndex, long key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	/**
	 * Redirect to {@link java.util.Arrays#binarySearch(short[], int, int, short)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static int binarySearch(short[] array, int beginIndex, int endIndex, short key) {
		return java.util.Arrays.binarySearch(array, beginIndex, endIndex, key);
	}

	//concat

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order). The type of the product array will be the
	 * type of the first non-null array in the given {@code arrays}.
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @param <T>    the type of the elements.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T[] concat(T[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		Class component = Object.class;
		for (int i = arrays.length - 1; i >= 0; i--) {
			T[] array = arrays[i];

			if (array != null) {
				component = array.getClass().getComponentType();
				length += array.length;
			}
		}

		T[] product = (T[]) Array.newInstance(component, length);

		for (int i = 0, j = 0; j < arrays.length; j++) {
			T[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @param klass  the type of the constructed array
	 * @param <T>    the type of the elements.
	 * @param <U>    the super type of the elements.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException     if the given {@code klass} or {@code arrays} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array class.
	 * @throws ArrayStoreException      if an element can not be stored in the product array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T extends U, U> U[] concat(Class<U[]> klass, T[][] arrays) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(arrays, "arrays");
		Objects.require(klass, Class::isArray, "klass");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			T[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		T[] product = (T[]) Array.newInstance(klass.getComponentType(), length);

		for (int i = 0, j = 0; j < arrays.length; j++) {
			T[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean[] concat(boolean[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			boolean[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		boolean[] product = new boolean[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			boolean[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static byte[] concat(byte[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			byte[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		byte[] product = new byte[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			byte[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static char[] concat(char[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			char[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		char[] product = new char[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			char[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static double[] concat(double[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			double[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		double[] product = new double[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			double[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static float[] concat(float[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			float[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		float[] product = new float[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			float[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int[] concat(int[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			int[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		int[] product = new int[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			int[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static long[] concat(long[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			long[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		long[] product = new long[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			long[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static short[] concat(short[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			short[] array = arrays[i];

			if (array != null)
				length += array.length;
		}

		short[] product = new short[length];

		for (int i = 0, j = 0; j < arrays.length; j++) {
			short[] array = arrays[j];

			if (array != null) {
				System.arraycopy(array, 0, product, i, array.length);
				i += array.length;
			}
		}

		return product;
	}

	/**
	 * Using Reflection, construct a new array from concatenating the given {@code arrays} (in order). The type of the product
	 * array will be the type of the first non-null array in the given {@code arrays}.
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException     if the given {@code arrays} is null.
	 * @throws IllegalArgumentException if the given {@code arrays} is not an array. Or if an array is not an array.
	 * @throws ArrayStoreException      if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object concat0(Object[] arrays) {
		Objects.requireNonNull(arrays, "arrays");
		Objects.require(arrays, Objects::isArray, "arrays");

		int length = 0;
		Class component = Object.class;
		for (int i = arrays.length - 1; i >= 0; i--) {
			Object array = arrays[i];

			if (array != null) {
				Objects.require(array, Objects::isArray, "arrays[?]");
				component = array.getClass().getComponentType();
				length += Array.getLength(array);
			}
		}

		Object product = Array.newInstance(component, length);

		for (int i = 0, j = 0; j < arrays.length; j++) {
			Object array = arrays[j];

			if (array != null) {
				Objects.require(array, Objects::isArray, "arrays[?]");
				int m = Array.getLength(array);

				if (component.isPrimitive() == array.getClass().getComponentType().isPrimitive())
					System.arraycopy(array, 0, product, i, m);
				else
					Arrays.hardcopy0(array, 0, product, i, m);

				i += m;
			}
		}

		return product;
	}

	/**
	 * Using Reflection, construct a new array from concatenating the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @param klass  the type of the constructed array
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException     if the given {@code klass} or {@code arrays} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array class. Or if the given {@code arrays} is not an
	 *                                  array. Or if an array is not an array.
	 * @throws ArrayStoreException      if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object concat0(Class klass, Object[] arrays) {
		Objects.requireNonNull(klass, "klass");
		Objects.requireNonNull(arrays, "arrays");
		Objects.require(klass, Class::isArray, "klass");
		Objects.require(arrays, Objects::isArray, "arrays");

		int length = 0;
		for (int i = arrays.length - 1; i >= 0; i--) {
			Object array = arrays[i];

			if (array != null) {
				Objects.require(array, Objects::isArray, "arrays[?]");
				length += Array.getLength(array);
			}
		}

		Object product = Array.newInstance(klass.getComponentType(), length);

		for (int i = 0, j = 0; j < arrays.length; j++) {
			Object array = arrays[j];

			if (array != null) {
				Objects.require(array, Objects::isArray, "array[?]");
				int m = Array.getLength(array);

				if (klass.getComponentType().isPrimitive() == array.getClass().getComponentType().isPrimitive())
					System.arraycopy(array, 0, product, i, length);
				else
					Arrays.hardcopy0(array, 0, product, i, m);

				i += m;
			}
		}

		return product;
	}

	/**
	 * Using the best {@link #concat(Object[][])} method. construct a new array from concatenating the given {@code arrays} (in
	 * order). The type of the product array will be the component-type of the given {@code arrays}, If the given {@code arrays}
	 * has a primitive component-type. Otherwise, the type of the product array will be the type of the first non-null array in
	 * the given {@code arrays}.
	 *
	 * @param arrays the arrays to be concatenated to construct the returned array.
	 * @return a new array from concatenating the given {@code arrays} (in order).
	 * @throws NullPointerException     if the given {@code arrays} is null.
	 * @throws IllegalArgumentException if the given {@code arrays} is not an array. Or if an array is not an array.
	 * @throws ArrayStoreException      if an element can not be stored in the constructed array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object concat1(Object[] arrays) {
		if (arrays instanceof Object[][])
			return Arrays.concat((Object[][]) arrays);
		if (arrays instanceof boolean[][])
			return Arrays.concat((boolean[][]) arrays);
		if (arrays instanceof byte[][])
			return Arrays.concat((byte[][]) arrays);
		if (arrays instanceof char[][])
			return Arrays.concat((char[][]) arrays);
		if (arrays instanceof double[][])
			return Arrays.concat((double[][]) arrays);
		if (arrays instanceof float[][])
			return Arrays.concat((float[][]) arrays);
		if (arrays instanceof int[][])
			return Arrays.concat((int[][]) arrays);
		if (arrays instanceof long[][])
			return Arrays.concat((long[][]) arrays);
		if (arrays instanceof short[][])
			return Arrays.concat((short[][]) arrays);

		return Arrays.concat0(arrays);
	}

	//copyOf

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @param <T>    the type of the elements.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(Object[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T[] copyOf(T[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		T[] copy = (T[]) Array.newInstance(array.getClass().getComponentType(), length);

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
	 * @see java.util.Arrays#copyOf(Object[], int, Class)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T extends U, U> U[] copyOf(T[] array, int length, Class<? extends U[]> klass) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(klass, "klass");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");
		Objects.require(klass, Objects::isObjectArrayClass, "klass");

		U[] copy = (U[]) Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(boolean[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean[] copyOf(boolean[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		boolean[] copy = new boolean[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(byte[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static byte[] copyOf(byte[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		byte[] copy = new byte[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(char[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static char[] copyOf(char[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		char[] copy = new char[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(double[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static double[] copyOf(double[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		double[] copy = new double[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(float[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static float[] copyOf(float[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		float[] copy = new float[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(int[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int[] copyOf(int[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		int[] copy = new int[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(long[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static long[] copyOf(long[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		long[] copy = new long[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(short[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static short[] copyOf(short[] array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		short[] copy = new short[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Using Reflection, construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws IllegalArgumentException   if the given {@code array} is not an array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(Object[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOf0(Object array, int length) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");

		Object copy = Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));

		return copy;
	}

	/**
	 * Using Reflection, construct a new copy of the given {@code array} capped to the given {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @param klass  the type of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws IllegalArgumentException   if the given {@code array} is not an array. Or if the given {@code klass} is not an
	 *                                    array class.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @throws ArrayStoreException        if an element can not be stored in the constructed array.
	 * @see java.util.Arrays#copyOf(Object[], int, Class)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOf0(Object array, int length, Class klass) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(klass, "klass");
		Objects.require(length, Objects::nonNegative, NegativeArraySizeException.class, "length");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(klass, Class::isArray, "klass");

		Object copy = Array.newInstance(klass.getComponentType(), length);

		if (klass.getComponentType().isPrimitive() == array.getClass().getComponentType().isPrimitive())
			System.arraycopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));
		else
			Arrays.hardcopy0(array, 0, copy, 0, Math.min(Array.getLength(array), length));

		return copy;
	}

	/**
	 * Using the best {@link #copyOf(Object[], int)} method, construct a new copy of the given {@code array} capped to the given
	 * {@code length}.
	 *
	 * @param array  the original array.
	 * @param length the length cap of the constructed array.
	 * @return a new copy of the given {@code array} capped to the given {@code length}.
	 * @throws NullPointerException       if the given {@code array} is null.
	 * @throws IllegalArgumentException   if the given {@code array} is not an array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.util.Arrays#copyOf(Object[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOf1(Object array, int length) {
		if (array instanceof Object[])
			return Arrays.copyOf((Object[]) array, length);
		if (array instanceof boolean[])
			return Arrays.copyOf((boolean[]) array, length);
		if (array instanceof byte[])
			return Arrays.copyOf((byte[]) array, length);
		if (array instanceof char[])
			return Arrays.copyOf((char[]) array, length);
		if (array instanceof double[])
			return Arrays.copyOf((double[]) array, length);
		if (array instanceof float[])
			return Arrays.copyOf((float[]) array, length);
		if (array instanceof int[])
			return Arrays.copyOf((int[]) array, length);
		if (array instanceof long[])
			return Arrays.copyOf((long[]) array, length);
		if (array instanceof short[])
			return Arrays.copyOf((short[]) array, length);

		return Arrays.copyOf0(array, length);
	}

	//copyOfRange

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
	 * @see java.util.Arrays#copyOfRange(Object[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> T[] copyOfRange(T[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		T[] product = (T[]) Array.newInstance(array.getClass().getComponentType(), length);

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
	 * @see java.util.Arrays#copyOfRange(Object[], int, int, Class)
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

		T[] product = (T[]) Array.newInstance(klass.getComponentType(), length);

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(boolean[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean[] copyOfRange(boolean[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		boolean[] product = new boolean[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(byte[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static byte[] copyOfRange(byte[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		byte[] product = new byte[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(char[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static char[] copyOfRange(char[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		char[] product = new char[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(double[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static double[] copyOfRange(double[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		double[] product = new double[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(float[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static float[] copyOfRange(float[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		float[] product = new float[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(int[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int[] copyOfRange(int[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		int[] product = new int[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(long[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static long[] copyOfRange(long[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		long[] product = new long[length];

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
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code beginIndex} is greater than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(short[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static short[] copyOfRange(short[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");

		int length = endIndex - beginIndex;

		short[] product = new short[length];

		System.arraycopy(array, beginIndex, product, 0, Math.min(array.length - beginIndex, length));

		return product;
	}

	/**
	 * Using Reflection, construct a new copy of the given {@code array} containing the elements from the given {@code beginIndex}
	 * to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the index where to start copying from the original array to the constructed array.
	 * @param endIndex   the index where to stop copying from the original array to the constructed array.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. if the given {@code beginIndex} is greater
	 *                                   than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(Object[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOfRange0(Object array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(array, Objects::isArray, "array");

		int length = endIndex - beginIndex;

		Object product = Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, beginIndex, product, 0, Math.min(Array.getLength(array) - beginIndex, length));

		return product;
	}

	/**
	 * Using Reflection, construct a new copy of the given {@code array} containing the elements from the given {@code beginIndex}
	 * to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the index where to start copying from the original array to the constructed array.
	 * @param endIndex   the index where to stop copying from the original array to the constructed array.
	 * @param klass      the type of the constructed array.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if the given {@code beginIndex} is greater
	 *                                   than the given {@code endIndex}. Or if the given {@code klass} is not an array class.
	 * @throws ArrayStoreException       if an element can not be stored in the constructed array.
	 * @see java.util.Arrays#copyOfRange(Object[], int, int, Class)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOfRange0(Object array, int beginIndex, int endIndex, Class klass) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(beginIndex, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(klass, Class::isArray, "klass");

		int length = endIndex - beginIndex;

		Object product = Array.newInstance(klass.getComponentType(), length);

		if (klass.getComponentType().isPrimitive() == array.getClass().getComponentType().isPrimitive())
			System.arraycopy(array, beginIndex, product, 0, Math.min(Array.getLength(array) - beginIndex, length));
		else
			Arrays.hardcopy0(array, beginIndex, product, 0, Math.min(Array.getLength(array) - beginIndex, length));

		return product;
	}

	/**
	 * Using the best {@link #copyOfRange(Object[], int, int)} method, construct a new copy of the given {@code array} containing
	 * the elements from the given {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the original array.
	 * @param beginIndex the index where to start copying from the original array to the constructed array.
	 * @param endIndex   the index where to stop copying from the original array to the constructed array.
	 * @return a new copy of the given {@code array} containing the elements from the given {@code beginIndex} to the given {@code
	 * 		endIndex}.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if the given {@code beginIndex} is negative. Or if the given {@code endIndex} is greater
	 *                                   than or equals the length of the given {@code array}.
	 * @throws IllegalArgumentException  if the given {@code array} is not an array. Or if the given {@code beginIndex} is greater
	 *                                   than the given {@code endIndex}.
	 * @see java.util.Arrays#copyOfRange(Object[], int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Object copyOfRange1(Object array, int beginIndex, int endIndex) {
		if (array instanceof Object[])
			return Arrays.copyOfRange((Object[]) array, beginIndex, endIndex);
		if (array instanceof boolean[])
			return Arrays.copyOfRange((boolean[]) array, beginIndex, endIndex);
		if (array instanceof byte[])
			return Arrays.copyOfRange((byte[]) array, beginIndex, endIndex);
		if (array instanceof char[])
			return Arrays.copyOfRange((char[]) array, beginIndex, endIndex);
		if (array instanceof double[])
			return Arrays.copyOfRange((double[]) array, beginIndex, endIndex);
		if (array instanceof float[])
			return Arrays.copyOfRange((float[]) array, beginIndex, endIndex);
		if (array instanceof int[])
			return Arrays.copyOfRange((int[]) array, beginIndex, endIndex);
		if (array instanceof long[])
			return Arrays.copyOfRange((long[]) array, beginIndex, endIndex);
		if (array instanceof short[])
			return Arrays.copyOfRange((short[]) array, beginIndex, endIndex);

		return Arrays.copyOfRange0(array, beginIndex, endIndex);
	}

	//deepEquals

	/**
	 * Determine if the given {@code array} deeply equals the given {@code other} in deep lengths, deep elements, and deep
	 * orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep lengths, deep elements, and deep
	 * 		orderings.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> boolean deepEquals(T[] array, T... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			Object a = array[i];
			Object o = other[i];

			if (a == o)
				continue;
			if (a == null || o == null)
				return false;

			if (a instanceof Object[] && o instanceof Object[]) {
				if (!Arrays.deepEquals((Object[]) a, (Object[]) o))
					return false;
			} else if (a instanceof boolean[] && o instanceof boolean[]) {
				if (!Arrays.equals((boolean[]) a, (boolean[]) o))
					return false;
			} else if (a instanceof byte[] && o instanceof byte[]) {
				if (!Arrays.equals((byte[]) a, (byte[]) o))
					return false;
			} else if (a instanceof char[] && o instanceof char[]) {
				if (!Arrays.equals((char[]) a, (char[]) o))
					return false;
			} else if (a instanceof double[] && o instanceof double[]) {
				if (!Arrays.equals((double[]) a, (double[]) o))
					return false;
			} else if (a instanceof float[] && o instanceof float[]) {
				if (!Arrays.equals((float[]) a, (float[]) o))
					return false;
			} else if (a instanceof int[] && o instanceof int[]) {
				if (!Arrays.equals((int[]) a, (int[]) o))
					return false;
			} else if (a instanceof long[] && o instanceof long[]) {
				if (!Arrays.equals((long[]) a, (long[]) o))
					return false;
			} else if (a instanceof short[] && o instanceof short[]) {
				if (!Arrays.equals((short[]) a, (short[]) o))
					return false;
			} else if (!a.equals(o))
				return false;
		}

		return true;
	}

	/**
	 * Using Reflection, determine if the given {@code array} deeply equals the given {@code other} in deep lengths, deep
	 * elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep lengths, deep elements, and deep
	 * 		orderings.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an array.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean deepEquals0(Object array, Object other) {
		Objects.require(array, Objects::isArray, "array");
		Objects.require(other, Objects::isArray, "other");

		int length = Array.getLength(array);

		if (array == other)
			return true;
		if (array == null || other == null || length != Array.getLength(other))
			return false;
		if (array.getClass().getComponentType().isPrimitive() !=
			other.getClass().getComponentType().isPrimitive())
			return false;

		for (int i = 0; i < length; i++) {
			Object a = Array.get(array, i);
			Object o = Array.get(other, i);

			if (a == o)
				continue;
			if (a == null || o == null)
				return false;

			if (a instanceof Object[] && o instanceof Object[]) {
				if (!Arrays.deepEquals0(a, o))
					return false;
			} else if (a.getClass().isArray() && o.getClass().isArray()) {
				if (!Arrays.equals0(a, o))
					return false;
			} else if (!a.equals(o))
				return false;
		}

		return true;
	}

	/**
	 * Using the best {@link #deepEquals(Object[], Object[])} {@link #equals(Object[], Object[])} method, determine if the given
	 * {@code array} deeply equals the given {@code other} in deep lengths, deep elements, and deep orderings.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} deeply equals the given {@code other} in deep lengths, deep elements, and deep
	 * 		orderings.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an array.
	 * @see java.util.Arrays#deepEquals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean deepEquals1(Object array, Object other) {
		if (array instanceof Object[] && other instanceof Object[])
			return Arrays.deepEquals((Object[]) array, (Object[]) other);
		if (array instanceof boolean[] && other instanceof boolean[])
			return Arrays.equals((boolean[]) array, (boolean[]) other);
		if (array instanceof byte[] && other instanceof byte[])
			return Arrays.equals((byte[]) array, (byte[]) other);
		if (array instanceof char[] && other instanceof char[])
			return Arrays.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return Arrays.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return Arrays.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return Arrays.equals((int[]) array, (int[]) other);
		if (array instanceof long[] && other instanceof long[])
			return Arrays.equals((long[]) array, (long[]) other);
		if (array instanceof short[] && other instanceof short[])
			return Arrays.equals((short[]) array, (short[]) other);

		return Arrays.deepEquals0(array, other);
	}

	//deepHashCode

	/**
	 * Calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @param <T>   the type of the elements.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @see java.util.Arrays#deepHashCode(Object[])
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
				deepHashCode = 31 * deepHashCode + Arrays.deepHashCode((Object[]) element);
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
	 * Using Reflection, calculate the hash code of the elements deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#deepHashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int deepHashCode0(Object array) {
		Objects.require(array, Objects::isArray, "array");

		if (array == null)
			return 0;

		int length = Array.getLength(array);
		int deepHashCode = 1;
		for (int i = 0; i < length; i++) {
			Object element = Array.get(array, i);

			if (element == null)
				deepHashCode = 31 * deepHashCode;
			else if (element instanceof Object[])
				deepHashCode = 31 * Arrays.deepHashCode0(element);
			else if (element.getClass().isArray())
				deepHashCode = 31 * Arrays.hashCode0(element);
			else
				deepHashCode = 31 * element.hashCode();
		}

		return deepHashCode;
	}

	/**
	 * Using the best {@link #deepHashCode(Object[])} {@link #hashCode(Object[])} method, calculate the hash code of the elements
	 * deeply stored in the given {@code array}.
	 *
	 * @param array the array to compute its deep hash code.
	 * @return the hash code of the elements deeply stored in the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#deepHashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int deepHashCode1(Object array) {
		if (array instanceof Object[])
			return Arrays.deepHashCode((Object[]) array);
		if (array instanceof boolean[])
			return Arrays.hashCode((boolean[]) array);
		if (array instanceof byte[])
			return Arrays.hashCode((byte[]) array);
		if (array instanceof char[])
			return Arrays.hashCode((char[]) array);
		if (array instanceof double[])
			return Arrays.hashCode((double[]) array);
		if (array instanceof float[])
			return Arrays.hashCode((float[]) array);
		if (array instanceof int[])
			return Arrays.hashCode((int[]) array);
		if (array instanceof long[])
			return Arrays.hashCode((long[]) array);
		if (array instanceof short[])
			return Arrays.hashCode((short[]) array);

		return Arrays.deepHashCode0(array);
	}

	//deepToString

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <T>   the type of the elements.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> String deepToString(T... array) {
		if (array == null)
			return "null";

		int cap = 20 * array.length;
		if (array.length != 0 && cap <= 0)
			cap = Integer.MAX_VALUE;

		StringBuilder builder = new StringBuilder(cap);
		Arrays.deepToString(array, builder, new java.util.ArrayList());
		return builder.toString();
	}

	/**
	 * Build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array   the array to build a string representation for it.
	 * @param builder the builder to append the string representation to.
	 * @param dejaVu  the arrays that has been seen before.
	 * @throws NullPointerException if the given {@code builder} or {@code dejaVu} is null.
	 * @see java.util.Arrays#deepToString(Object[], StringBuilder, Set)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void deepToString(Object[] array, StringBuilder builder, Collection<Object[]> dejaVu) {
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
						Arrays.deepToString((Object[]) element, builder, dejaVu);
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

	/**
	 * Using Reflection, build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String deepToString0(Object array) {
		Objects.require(array, Objects::isArray, "array");

		if (array == null)
			return "null";

		int length = Array.getLength(array);
		int cap = 20 * length;
		if (length != 0 && cap <= 0)
			cap = Integer.MAX_VALUE;

		StringBuilder builder = new StringBuilder(cap);
		Arrays.deepToString0(array, builder, new java.util.ArrayList());
		return builder.toString();
	}

	/**
	 * Using Reflection, build a string representation of the deep contents of the given {@code array}.
	 *
	 * @param array   the array to build a string representation for it.
	 * @param builder the builder to append the string representation to.
	 * @param dejaVu  the arrays that has been seen before.
	 * @throws NullPointerException     if the given {@code builder} or {@code dejaVu} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#deepToString(Object[], StringBuilder, Set)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void deepToString0(Object array, StringBuilder builder, Collection dejaVu) {
		Objects.requireNonNull(builder, "builder");
		Objects.requireNonNull(dejaVu, "dejaVu");
		Objects.require(array, Objects::isArray, "array");

		if (array == null)
			builder.append("null");

		int length = Array.getLength(array);

		if (length != 0) {
			dejaVu.add(array);
			builder.append("[");

			int i = 0;
			while (true) {
				Object element = Array.get(array, i);

				if (element == null)
					builder.append("null");
				if (element instanceof Object[])
					if (dejaVu.contains(element))
						builder.append("[...]");
					else
						Arrays.deepToString0(element, builder, dejaVu);
				else if (element.getClass().isArray())
					builder.append(Arrays.toString0(element));
				else
					builder.append(element);

				if (++i < length) {
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

	/**
	 * Using the best {@link #deepToString(Object[])} {@link #toString(Object[])} method, build a string representation of the
	 * deep contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the deep contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#deepToString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String deepToString1(Object array) {
		if (array instanceof Object[])
			return Arrays.deepToString((Object[]) array);
		if (array instanceof boolean[])
			return Arrays.toString((boolean[]) array);
		if (array instanceof byte[])
			return Arrays.toString((byte[]) array);
		if (array instanceof char[])
			return Arrays.toString((char[]) array);
		if (array instanceof double[])
			return Arrays.toString((double[]) array);
		if (array instanceof float[])
			return Arrays.toString((float[]) array);
		if (array instanceof int[])
			return Arrays.toString((int[]) array);
		if (array instanceof long[])
			return Arrays.toString((long[]) array);
		if (array instanceof short[])
			return Arrays.toString((short[]) array);

		return Arrays.deepToString0(array);
	}

	//equals

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @param <T>   the type of the elements.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(Object[], Object[])
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
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(boolean[], boolean[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(boolean[] array, boolean... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			boolean a = array[i];
			boolean o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(byte[], byte[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(byte[] array, byte... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			byte a = array[i];
			byte o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(char[], char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(char[] array, char... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			char a = array[i];
			char o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(double[], double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(double[] array, double... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			long a = Double.doubleToLongBits(array[i]);
			long o = Double.doubleToLongBits(other[i]);

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(float[], float[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(float[] array, float... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			int a = Float.floatToIntBits(array[i]);
			int o = Float.floatToIntBits(other[i]);

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(int[], int[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(int[] array, int... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			int a = array[i];
			int o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(long[], long[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(long[] array, long... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			long a = array[i];
			long o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @see java.util.Arrays#equals(short[], short[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals(short[] array, short... other) {
		if (array == other)
			return true;
		if (array == null || other == null || array.length != other.length)
			return false;

		for (int i = 0; i < array.length; i++) {
			short a = array[i];
			short o = other[i];

			if (a != o)
				return false;
		}

		return true;
	}

	/**
	 * Using Reflection, determine if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an array.
	 * @see java.util.Arrays#equals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals0(Object array, Object other) {
		Objects.require(array, Objects::isArray, "array");
		Objects.require(other, Objects::isArray, "other");

		int length = Array.getLength(array);

		if (array == other)
			return true;
		if (array == null || other == null || length != Array.getLength(other))
			return false;
		if (array.getClass().getComponentType().isPrimitive() !=
			other.getClass().getComponentType().isPrimitive())
			return false;

		for (int i = 0; i < length; i++) {
			Object a = Array.get(array, i);
			Object o = Array.get(other, i);

			if (a != o && (a == null || !a.equals(o)))
				return false;
		}

		return true;
	}

	/**
	 * Using the best {@link #equals(Object[], Object[])} method, determine if the given {@code array} does equals the given
	 * {@code other} in length, elements, and order.
	 *
	 * @param array the first array to be matched.
	 * @param other the second array to be matched.
	 * @return true, if the given {@code array} does equals the given {@code other} in length, elements, and order.
	 * @throws IllegalArgumentException if the given {@code array} or {@code other} is not an array.
	 * @see java.util.Arrays#equals(Object[], Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static boolean equals1(Object array, Object other) {
		if (array instanceof Object[] && other instanceof Object[])
			return Arrays.equals((Object[]) array, (Object[]) other);
		if (array instanceof boolean[] && other instanceof boolean[])
			return Arrays.equals((boolean[]) array, (boolean[]) other);
		if (array instanceof byte[] && other instanceof byte[])
			return Arrays.equals((byte[]) array, (byte[]) other);
		if (array instanceof char[] && other instanceof char[])
			return Arrays.equals((char[]) array, (char[]) other);
		if (array instanceof double[] && other instanceof double[])
			return Arrays.equals((double[]) array, (double[]) other);
		if (array instanceof float[] && other instanceof float[])
			return Arrays.equals((float[]) array, (float[]) other);
		if (array instanceof int[] && other instanceof int[])
			return Arrays.equals((int[]) array, (int[]) other);
		if (array instanceof long[] && other instanceof long[])
			return Arrays.equals((long[]) array, (long[]) other);
		if (array instanceof short[] && other instanceof short[])
			return Arrays.equals((short[]) array, (short[]) other);

		return Arrays.equals0(array, other);
	}

	//fill

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @param <T>     the type of the elements.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @throws ArrayStoreException  if the given {@code element} can not be stored in the given {@code array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void fill(T[] array, T element) {
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
	}

	/**
	 * Assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#fill(int[], int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(int[] array, int element) {
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			array[i] = element;
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
	 * @see java.util.Arrays#fill(Object[], int, int, Object)
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
	 * Assign the given {@code element} to each element of the given {@code array} from the given {@code beginIndex} to the given
	 * {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(boolean[], int, int, boolean)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(boolean[] array, int beginIndex, int endIndex, boolean element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(byte[], int, int, byte)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(byte[] array, int beginIndex, int endIndex, byte element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(char[], int, int, char)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(char[] array, int beginIndex, int endIndex, char element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(double[], int, int, double)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(double[] array, int beginIndex, int endIndex, double element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(float[], int, int, float)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(float[] array, int beginIndex, int endIndex, float element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(int[], int, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(int[] array, int beginIndex, int endIndex, int element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(long[], int, int, long)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(long[] array, int beginIndex, int endIndex, long element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
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
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(short[], int, int, short)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill(short[] array, int beginIndex, int endIndex, short element) {
		Objects.requireNonNull(array, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
			array[i] = element;
	}

	/**
	 * Using Reflection, assign the given {@code element} to each element of the given {@code array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @throws ArrayStoreException      if the given {@code element} can not be stored in the given {@code array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill0(Object array, Object element) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
			try {
				Array.set(array, i, element);
			} catch (IllegalArgumentException e) {
				throw new ArrayStoreException(e.getMessage());
			}
	}

	/**
	 * Using Reflection, assign the given {@code element} to each element of the given {@code array} from the given {@code
	 * beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored in the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(Object[], int, int, Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill0(Object array, int beginIndex, int endIndex, Object element) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(beginIndex, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(endIndex, Array.getLength(array), Objects::isLess, ArrayIndexOutOfBoundsException.class, "endIndex");

		for (int i = beginIndex; i < endIndex; i++)
			try {
				Array.set(array, i, element);
			} catch (IllegalArgumentException e) {
				throw new ArrayStoreException(e.getMessage());
			}
	}

	/**
	 * Using the best {@link #fill(Object[], Object)} method, assign the given {@code element} to each element of the given {@code
	 * array}.
	 *
	 * @param array   the array to be filled.
	 * @param element the element to fill the given {@code array} with.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @throws ArrayStoreException      if the given {@code element} can not be stored in the given {@code array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill1(Object array, Object element) {
		if (array instanceof Object[])
			Arrays.fill((Object[]) array, element);
		else if (array instanceof boolean[] && element instanceof Boolean)
			Arrays.fill((boolean[]) array, (boolean) element);
		else if (array instanceof byte[] && element instanceof Byte)
			Arrays.fill((byte[]) array, (byte) element);
		else if (array instanceof char[] && element instanceof Character)
			Arrays.fill((char[]) array, (char) element);
		else if (array instanceof double[] && element instanceof Double)
			Arrays.fill((double[]) array, (double) element);
		else if (array instanceof float[] && element instanceof Float)
			Arrays.fill((float[]) array, (float) element);
		else if (array instanceof int[] && element instanceof Integer)
			Arrays.fill((int[]) array, (int) element);
		else if (array instanceof long[] && element instanceof Long)
			Arrays.fill((long[]) array, (long) element);
		else if (array instanceof short[] && element instanceof Short)
			Arrays.fill((short[]) array, (short) element);
		else
			Arrays.fill0(array, element);
	}

	/**
	 * Using the best {@link #fill(Object[], int, int, Object)} method. assign the given {@code element} to each element of the
	 * given {@code array} from the given {@code beginIndex} to the given {@code endIndex}.
	 *
	 * @param array      the array to be filled.
	 * @param beginIndex the index where to start filling the given {@code array}.
	 * @param endIndex   the index where to stop filling the given {@code array}.
	 * @param element    the element to fill the given {@code array} with.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored in the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code beginIndex > endIndex} or {@code endIndex >=
	 *                                        array.length}.
	 * @see java.util.Arrays#fill(Object[], int, int, Object)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void fill1(Object array, int beginIndex, int endIndex, Object element) {
		if (array instanceof Object[])
			Arrays.fill((Object[]) array, beginIndex, endIndex, element);
		else if (array instanceof boolean[] && element instanceof Boolean)
			Arrays.fill((boolean[]) array, beginIndex, endIndex, (boolean) element);
		else if (array instanceof byte[] && element instanceof Byte)
			Arrays.fill((byte[]) array, beginIndex, endIndex, (byte) element);
		else if (array instanceof char[] && element instanceof Character)
			Arrays.fill((char[]) array, beginIndex, endIndex, (char) element);
		else if (array instanceof double[] && element instanceof Double)
			Arrays.fill((double[]) array, beginIndex, endIndex, (double) element);
		else if (array instanceof float[] && element instanceof Float)
			Arrays.fill((float[]) array, beginIndex, endIndex, (float) element);
		else if (array instanceof int[] && element instanceof Integer)
			Arrays.fill((int[]) array, beginIndex, endIndex, (int) element);
		else if (array instanceof long[] && element instanceof Long)
			Arrays.fill((long[]) array, beginIndex, endIndex, (long) element);
		else if (array instanceof short[] && element instanceof Short)
			Arrays.fill((short[]) array, beginIndex, endIndex, (short) element);
		else
			Arrays.fill0(array, element);
	}

	//hardcopy

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
	 * @see System#arraycopy(Object, int, Object, int, int)
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(boolean[] src, int srcPos, boolean[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			boolean[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(byte[] src, int srcPos, byte[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			byte[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(char[] src, int srcPos, char[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			char[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(double[] src, int srcPos, double[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			double[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(float[] src, int srcPos, float[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			float[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(int[] src, int srcPos, int[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			int[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(long[] src, int srcPos, long[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			long[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(short[] src, int srcPos, short[] dest, int destPos, int length) {
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
				dest[di++] = src[si++];
		else {
			short[] clone = java.util.Arrays.copyOfRange(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				dest[di++] = src[si++];
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				dest[di++] = clone[i];
		}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, boolean[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (boolean) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, byte[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (byte) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, char[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (char) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, double[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (double) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, float[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (float) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, int[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (int) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, long[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (long) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(Object[] src, int srcPos, short[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			try {
				dest[di++] = (short) src[si++];
			} catch (ClassCastException e) {
				throw new ArrayStoreException(e.getMessage());
			}
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(boolean[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(byte[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(char[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(double[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(float[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(int[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(long[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
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
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy(short[] src, int srcPos, Object[] dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(srcPos + length, src.length, Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos + length, dest.length, Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		for (int i = 0; i < length; i++)
			dest[di++] = src[si++];
	}

	/**
	 * Using Reflection, copy the elements from the given {@code src} to the given {@code dest}. Start reading from the given
	 * {@code src} at the given {@code srcPos}. Start writing to the given {@code dest} at the given {@code destPos}. Copy the
	 * specified number of elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if the given {@code src} or {@code dest} is not an array. Or if an element can not be
	 *                                   stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy0(Object src, int srcPos, Object dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		Objects.require(src, Objects::isArray, ArrayStoreException.class, "src");
		Objects.require(dest, Objects::isArray, ArrayStoreException.class, "dest");
		Objects.require(srcPos, Objects::nonNegative, IndexOutOfBoundsException.class, "srcPos");
		Objects.require(destPos, Objects::nonNegative, IndexOutOfBoundsException.class, "destPos");
		Objects.require(length, Objects::nonNegative, IndexOutOfBoundsException.class, "length");
		Objects.require(
				srcPos + length, Array.getLength(src), Objects::nonGreater, IndexOutOfBoundsException.class, "srcPos + length");
		Objects.require(destPos +
						length, Array.getLength(dest), Objects::nonGreater, IndexOutOfBoundsException.class, "destPos + length");

		int si = srcPos;
		int di = destPos;
		if (src != dest || destPos <= srcPos || srcPos + length <= destPos)
			for (int i = 0; i < length; i++)
				try {
					Array.set(dest, di++, Array.get(src, si++));
				} catch (IllegalArgumentException e) {
					throw new ArrayStoreException(e.getMessage());
				}
		else {
			Object clone = Arrays.copyOfRange0(src, destPos, srcPos + length);

			for (int i = 0, l = destPos - srcPos; i < l; i++)
				try {
					Array.set(dest, di++, Array.get(src, si++));
				} catch (IllegalArgumentException e) {
					throw new ArrayStoreException(e.getMessage());
				}
			for (int i = 0, l = srcPos + length - destPos; i < l; i++)
				try {
					Array.set(dest, di++, Array.get(clone, i));
				} catch (IllegalArgumentException e) {
					throw new ArrayStoreException(e.getMessage());
				}
		}
	}

	/**
	 * Using the best {@link #hardcopy(Object[], int, Object[], int, int)} method, copy the elements from the given {@code src} to
	 * the given {@code dest}. Start reading from the given {@code src} at the given {@code srcPos}. Start writing to the given
	 * {@code dest} at the given {@code destPos}. Copy the specified number of elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if the given {@code src} or {@code dest} is not an array. Or if an element can not be
	 *                                   stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy1(Object[] src, int srcPos, Object dest, int destPos, int length) {
		if (dest instanceof Object[])
			Arrays.hardcopy(src, srcPos, (Object[]) dest, destPos, length);
		else if (dest instanceof boolean[])
			Arrays.hardcopy(src, srcPos, (boolean[]) dest, destPos, length);
		else if (dest instanceof byte[])
			Arrays.hardcopy(src, srcPos, (byte[]) dest, destPos, length);
		else if (dest instanceof char[])
			Arrays.hardcopy(src, srcPos, (char[]) dest, destPos, length);
		else if (dest instanceof double[])
			Arrays.hardcopy(src, srcPos, (double[]) dest, destPos, length);
		else if (dest instanceof float[])
			Arrays.hardcopy(src, srcPos, (float[]) dest, destPos, length);
		else if (dest instanceof int[])
			Arrays.hardcopy(src, srcPos, (int[]) dest, destPos, length);
		else if (dest instanceof long[])
			Arrays.hardcopy(src, srcPos, (long[]) dest, destPos, length);
		else if (dest instanceof short[])
			Arrays.hardcopy(src, srcPos, (short[]) dest, destPos, length);
		else
			Arrays.hardcopy0(src, srcPos, dest, destPos, length);
	}

	/**
	 * Using the best {@link #hardcopy(Object[], int, Object[], int, int)} method, copy the elements from the given {@code src} to
	 * the given {@code dest}. Start reading from the given {@code src} at the given {@code srcPos}. Start writing to the given
	 * {@code dest} at the given {@code destPos}. Copy the specified number of elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if the given {@code src} or {@code dest} is not an array. Or if an element can not be
	 *                                   stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy1(Object src, int srcPos, Object[] dest, int destPos, int length) {
		if (src instanceof Object[])
			Arrays.hardcopy((Object[]) src, srcPos, dest, destPos, length);
		if (src instanceof boolean[])
			Arrays.hardcopy((boolean[]) src, srcPos, dest, destPos, length);
		else if (src instanceof byte[])
			Arrays.hardcopy((byte[]) src, srcPos, dest, destPos, length);
		else if (src instanceof char[])
			Arrays.hardcopy((char[]) src, srcPos, dest, destPos, length);
		else if (src instanceof double[])
			Arrays.hardcopy((double[]) src, srcPos, dest, destPos, length);
		else if (src instanceof float[])
			Arrays.hardcopy((float[]) src, srcPos, dest, destPos, length);
		else if (src instanceof int[])
			Arrays.hardcopy((int[]) src, srcPos, dest, destPos, length);
		else if (src instanceof long[])
			Arrays.hardcopy((long[]) src, srcPos, dest, destPos, length);
		else if (src instanceof short[])
			Arrays.hardcopy((short[]) src, srcPos, dest, destPos, length);
		else
			Arrays.hardcopy0(src, srcPos, dest, destPos, length);
	}

	/**
	 * Using the best {@link #hardcopy(Object[], int, Object[], int, int)} method, copy the elements from the given {@code src} to
	 * the given {@code dest}. Start reading from the given {@code src} at the given {@code srcPos}. Start writing to the given
	 * {@code dest} at the given {@code destPos}. Copy the specified number of elements {@code length}.
	 *
	 * @param src     the source array.
	 * @param srcPos  the index to start reading from the source array.
	 * @param dest    the destination array.
	 * @param destPos the index to start writing to the destination array.
	 * @param length  the number of elements to be copied.
	 * @throws NullPointerException      if the given {@code src} or {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code srcPos < 0} or {@code destPos < 0} or {@code length < 0} or {@code srcPos +
	 *                                   length > src.length} or {@code destPos + length > dest.length}.
	 * @throws ArrayStoreException       if the given {@code src} or {@code dest} is not an array. Or if an element can not be
	 *                                   stored in the given {@code dest}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void hardcopy1(Object src, int srcPos, Object dest, int destPos, int length) {
		if (src instanceof Object[] && dest instanceof Object[])
			Arrays.hardcopy((Object[]) src, srcPos, (Object[]) dest, destPos, length);
		else if (src instanceof Object[])
			Arrays.hardcopy1((Object[]) src, srcPos, dest, destPos, length);
		else if (dest instanceof Object[])
			Arrays.hardcopy1(src, srcPos, (Object[]) dest, destPos, length);
		else if (src instanceof boolean[] && dest instanceof boolean[])
			Arrays.hardcopy((boolean[]) src, srcPos, (boolean[]) dest, destPos, length);
		else if (src instanceof byte[] && dest instanceof byte[])
			Arrays.hardcopy((byte[]) src, srcPos, (byte[]) dest, destPos, length);
		else if (src instanceof char[] && dest instanceof char[])
			Arrays.hardcopy((char[]) src, srcPos, (char[]) dest, destPos, length);
		else if (src instanceof double[] && dest instanceof double[])
			Arrays.hardcopy((double[]) src, srcPos, (double[]) dest, destPos, length);
		else if (src instanceof float[] && dest instanceof float[])
			Arrays.hardcopy((float[]) src, srcPos, (float[]) dest, destPos, length);
		else if (src instanceof int[] && dest instanceof int[])
			Arrays.hardcopy((int[]) src, srcPos, (int[]) dest, destPos, length);
		else if (src instanceof long[] && dest instanceof long[])
			Arrays.hardcopy((long[]) src, srcPos, (long[]) dest, destPos, length);
		else if (src instanceof short[] && dest instanceof short[])
			Arrays.hardcopy((short[]) src, srcPos, (short[]) dest, destPos, length);
		else
			Arrays.hardcopy0(src, srcPos, dest, destPos, length);
	}

	//hashCode

	/**
	 * Calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @param <T>   the type of the elements.
	 * @return the hash code of the elements of the given {@code array}.
	 * @see java.util.Arrays#hashCode(Object[])
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
			boolean element = array[i];

			hashCode = 31 * hashCode + Boolean.hashCode(element);
		}

		return hashCode;
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
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			byte element = array[i];

			hashCode = 31 * hashCode + Byte.hashCode(element);
		}

		return hashCode;
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
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			char element = array[i];

			hashCode = 31 * hashCode + Character.hashCode(element);
		}

		return hashCode;
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
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			double element = array[i];

			hashCode = 31 * hashCode + Double.hashCode(element);
		}

		return hashCode;
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
			float element = array[i];

			hashCode = 31 * hashCode + Float.hashCode(element);
		}

		return hashCode;
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
			int element = array[i];

			hashCode = 31 * hashCode + Integer.hashCode(element);
		}

		return hashCode;
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
			long element = array[i];

			hashCode = 31 * hashCode + Long.hashCode(element);
		}

		return hashCode;
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
		if (array == null)
			return 0;

		int hashCode = 1;
		for (int i = 0; i < array.length; i++) {
			short element = array[i];

			hashCode = 31 * hashCode + Short.hashCode(element);
		}

		return hashCode;
	}

	/**
	 * Using Reflection, calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode0(Object array) {
		Objects.require(array, Objects::isArray, "array");

		if (array == null)
			return 0;

		int hashCode = 1;
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++) {
			Object element = Array.get(array, i);

			hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
		}

		return hashCode;
	}

	/**
	 * Using the best {@link #hashCode(Object[])} method, calculate the hash code of the elements of the given {@code array}.
	 *
	 * @param array the array to compute its hash code.
	 * @return the hash code of the elements of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#hashCode(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static int hashCode1(Object array) {
		if (array instanceof Object[])
			return Arrays.hashCode((Object[]) array);
		if (array instanceof boolean[])
			return Arrays.hashCode((boolean[]) array);
		if (array instanceof byte[])
			return Arrays.hashCode((byte[]) array);
		if (array instanceof char[])
			return Arrays.hashCode((char[]) array);
		if (array instanceof double[])
			return Arrays.hashCode((double[]) array);
		if (array instanceof float[])
			return Arrays.hashCode((float[]) array);
		if (array instanceof int[])
			return Arrays.hashCode((int[]) array);
		if (array instanceof long[])
			return Arrays.hashCode((long[]) array);
		if (array instanceof short[])
			return Arrays.hashCode((short[]) array);

		return Arrays.hashCode0(array);
	}

	//iterator

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param <T>   the type of the elements.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> ArrayIterator<T> iterator(T... array) {
		return new ArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static BooleanArrayIterator iterator(boolean[] array) {
		return new BooleanArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ByteArrayIterator iterator(byte[] array) {
		return new ByteArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static CharacterArrayIterator iterator(char[] array) {
		return new CharacterArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static DoubleArrayIterator iterator(double[] array) {
		return new DoubleArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static FloatArrayIterator iterator(float[] array) {
		return new FloatArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static IntegerArrayIterator iterator(int[] array) {
		return new IntegerArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static LongArrayIterator iterator(long[] array) {
		return new LongArrayIterator(array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ShortArrayIterator iterator(short[] array) {
		return new ShortArrayIterator(array);
	}

	/**
	 * Using Reflection, construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ArrayIterator0 iterator0(Object array) {
		return new ArrayIterator0(array);
	}

	/**
	 * Using the best {@link #iterator(Object[])} method, construct a new iterator iterating the elements of the given {@code
	 * array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ListIterator iterator1(Object array) {
		if (array instanceof Object[])
			return new ArrayIterator((Object[]) array);
		if (array instanceof boolean[])
			return new BooleanArrayIterator((boolean[]) array);
		if (array instanceof byte[])
			return new ByteArrayIterator((byte[]) array);
		if (array instanceof char[])
			return new CharacterArrayIterator((char[]) array);
		if (array instanceof double[])
			return new DoubleArrayIterator((double[]) array);
		if (array instanceof float[])
			return new FloatArrayIterator((float[]) array);
		if (array instanceof int[])
			return new IntegerArrayIterator((int[]) array);
		if (array instanceof long[])
			return new LongArrayIterator((long[]) array);
		if (array instanceof short[])
			return new ShortArrayIterator((short[]) array);

		return Arrays.iterator0(array);
	}

	//todo parallelPrefix +(boolean | byte | char | float | short | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(Object[], BinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void parallelPrefix(T[] array, BinaryOperator<T> operator) {
		java.util.Arrays.parallelPrefix(array, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(double[], DoubleBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(double[] array, DoubleBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(int[], IntBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(int[] array, IntBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(long[], LongBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(long[] array, LongBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(Object[], int, int, BinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void parallelPrefix(T[] array, int beginIndex, int endIndex, BinaryOperator<T> operator) {
		java.util.Arrays.parallelPrefix(array, beginIndex, endIndex, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(double[], int, int, DoubleBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(double[] array, int beginIndex, int endIndex, DoubleBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, beginIndex, endIndex, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(int[], int, int, IntBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(int[] array, int beginIndex, int endIndex, IntBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, beginIndex, endIndex, operator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelPrefix(long[], int, int, LongBinaryOperator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelPrefix(long[] array, int beginIndex, int endIndex, LongBinaryOperator operator) {
		java.util.Arrays.parallelPrefix(array, beginIndex, endIndex, operator);
	}

	//parallelSetAll

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <T>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> array[i] = function.apply(i));
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(boolean[] array, IntFunction<Boolean> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Boolean) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(byte[] array, IntFunction<Byte> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Byte) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(char[] array, IntFunction<Character> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Character) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(double[], IntToDoubleFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(double[] array, IntToDoubleFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> array[i] = function.applyAsDouble(i));
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(float[] array, IntFunction<Float> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Float) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(int[], IntUnaryOperator)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(int[] array, IntUnaryOperator function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> array[i] = function.applyAsInt(i));
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#parallelSetAll(long[], IntToLongFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(long[] array, IntToLongFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> array[i] = function.applyAsLong(i));
	}

	/**
	 * In parallel, assign the given each element of the given {@code array} to the value returned from invoking the given {@code
	 * function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll(short[] array, IntFunction<Short> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		IntStream.range(0, array.length).parallel().forEach(i -> {
			Object element = function.apply(i);

			try {
				array[i] = (Short) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * Using Reflection, in parallel, assign the given each element of the given {@code array} to the value returned from invoking
	 * the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if the given {@code array} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll0(Object array, IntFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		Objects.require(array, Objects::isArray, "array");
		IntStream.range(0, Array.getLength(array)).parallel().forEach(i -> {
			try {
				Array.set(array, i, function.apply(i));
			} catch (IllegalArgumentException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		});
	}

	/**
	 * Using the best {@link #parallelSetAll(Object[], IntFunction)} method, in parallel, assign the given each element of the
	 * given {@code array} to the value returned from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if the given {@code array} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void parallelSetAll1(Object array, IntFunction function) {
		if (array instanceof Object[])
			Arrays.parallelSetAll((Object[]) array, function);
		else if (array instanceof boolean[])
			Arrays.parallelSetAll((boolean[]) array, function);
		else if (array instanceof byte[])
			Arrays.parallelSetAll((byte[]) array, function);
		else if (array instanceof char[])
			Arrays.parallelSetAll((char[]) array, function);
		else if (array instanceof double[])
			Arrays.parallelSetAll((double[]) array, i -> (double) function.apply(i));
		else if (array instanceof float[])
			Arrays.parallelSetAll((float[]) array, function);
		else if (array instanceof int[])
			Arrays.parallelSetAll((int[]) array, i -> (int) function.apply(i));
		else if (array instanceof long[])
			Arrays.parallelSetAll((long[]) array, i -> (long) function.apply(i));
		else if (array instanceof short[])
			Arrays.parallelSetAll((short[]) array, function);
		else
			Arrays.parallelSetAll0(array, function);
	}

	//todo parallelSort +(boolean | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(Comparable[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T extends Comparable<? super T>> void parallelSort(T... array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(Object[], Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void parallelSort(T[] array, Comparator<? super T> comparator) {
		java.util.Arrays.parallelSort(array, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(byte[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(byte[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(char[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(char[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(double[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(double[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(float[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(float[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(int[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(int[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(long[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(long[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(short[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(short[] array) {
		java.util.Arrays.parallelSort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(Comparable[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T extends Comparable<? super T>> void parallelSort(T[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(Object[], int, int, Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void parallelSort(T[] array, int beginIndex, int endIndex, Comparator<? super T> comparator) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(byte[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(byte[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(char[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(char[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(double[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(double[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(float[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(float[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(int[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(int[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(long[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(long[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#parallelSort(short[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void parallelSort(short[] array, int beginIndex, int endIndex) {
		java.util.Arrays.parallelSort(array, beginIndex, endIndex);
	}

	//setAll

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @param <T>      the type of the elements.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> void setAll(T[] array, IntFunction<? extends T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++)
			array[i] = function.apply(i);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(boolean[] array, IntFunction<Boolean> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Boolean) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(byte[] array, IntFunction<Byte> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Byte) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(char[] array, IntFunction<Character> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Character) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(double[], IntToDoubleFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(double[] array, IntToDoubleFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++)
			array[i] = function.applyAsDouble(i);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(float[] array, IntFunction<Float> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Float) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(int[], IntUnaryOperator)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(int[] array, IntUnaryOperator function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++)
			array[i] = function.applyAsInt(i);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @see java.util.Arrays#setAll(long[], IntToLongFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(long[] array, IntToLongFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++)
			array[i] = function.applyAsLong(i);
	}

	/**
	 * Assign the given each element of the given {@code array} to the value returned from invoking the given {@code function}
	 * with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code array} or {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll(short[] array, IntFunction<Short> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		for (int i = 0; i < array.length; i++) {
			Object element = function.apply(i);

			try {
				array[i] = (Short) element;
			} catch (ClassCastException | NullPointerException e) {
				throw new ArrayStoreException(e.getMessage());
			}
		}
	}

	/**
	 * Using Reflection, assign the given each element of the given {@code array} to the value returned from invoking the given
	 * {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if the given {@code array} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll0(Object array, IntFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		Objects.require(array, Objects::isArray, "array");
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
			try {
				Array.set(array, i, function.apply(i));
			} catch (IllegalArgumentException e) {
				throw new ArrayStoreException(e.getMessage());
			}
	}

	/**
	 * Using the best {@link #setAll(Object[], IntFunction)} method, assign the given each element of the given {@code array} to
	 * the value returned from invoking the given {@code function} with the index of that element.
	 *
	 * @param array    the array with elements to be reassigned.
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException     if the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if the given {@code array} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the given {@code array}.
	 * @see java.util.Arrays#setAll(Object[], IntFunction)
	 * @since 0.1.5 ~2020.07.24
	 */
	public static void setAll1(Object array, IntFunction function) {
		if (array instanceof Object[])
			Arrays.setAll((Object[]) array, function);
		else if (array instanceof boolean[])
			Arrays.setAll((boolean[]) array, function);
		else if (array instanceof byte[])
			Arrays.setAll((byte[]) array, function);
		else if (array instanceof char[])
			Arrays.setAll((char[]) array, function);
		else if (array instanceof double[])
			Arrays.setAll((double[]) array, i -> (double) function.apply(i));
		else if (array instanceof float[])
			Arrays.setAll((float[]) array, function);
		else if (array instanceof int[])
			Arrays.setAll((int[]) array, i -> (int) function.apply(i));
		else if (array instanceof long[])
			Arrays.setAll((long[]) array, i -> (long) function.apply(i));
		else if (array instanceof short[])
			Arrays.setAll((short[]) array, function);
		else
			Arrays.setAll0(array, function);
	}

	//todo sort +(boolean | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#sort(Object[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void sort(T... array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(Object[], Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void sort(T[] array, Comparator<? super T> comparator) {
		java.util.Arrays.sort(array, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(byte[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(byte[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(char[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(char[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(double[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(double[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(float[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(float[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(int[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(int[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(long[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(long[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(short[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(short[] array) {
		java.util.Arrays.sort(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(Object[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void sort(T[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(Object[], int, int, Comparator)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> void sort(T[] array, int beginIndex, int endIndex, Comparator<? super T> comparator) {
		java.util.Arrays.sort(array, beginIndex, endIndex, comparator);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(byte[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(byte[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(char[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(char[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(double[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(double[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(float[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(float[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(int[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(int[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(long[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(long[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#sort(short[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static void sort(short[] array, int beginIndex, int endIndex) {
		java.util.Arrays.sort(array, beginIndex, endIndex);
	}

	//todo spliterator +(boolean | byte | char | float | short | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(Object[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> Spliterator<T> spliterator(T... array) {
		return java.util.Arrays.spliterator(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(double[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfDouble spliterator(double[] array) {
		return java.util.Arrays.spliterator(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(int[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfInt spliterator(int[] array) {
		return java.util.Arrays.spliterator(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(long[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfLong spliterator(long[] array) {
		return java.util.Arrays.spliterator(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(Object[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> Spliterator<T> spliterator(T[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.spliterator(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(double[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfDouble spliterator(double[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.spliterator(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(int[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfInt spliterator(int[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.spliterator(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#spliterator(long[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static Spliterator.OfLong spliterator(long[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.spliterator(array, beginIndex, endIndex);
	}

	//todo stream +(boolean | byte | char | float | short | 0 | 1)

	/**
	 * Redirect to {@link java.util.Arrays#stream(Object[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> Stream<T> stream(T... array) {
		return java.util.Arrays.stream(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(double[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static DoubleStream stream(double[] array) {
		return java.util.Arrays.stream(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(int[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static IntStream stream(int[] array) {
		return java.util.Arrays.stream(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(long[])}.
	 */
	@SuppressWarnings("JavaDoc")
	public static LongStream stream(long[] array) {
		return java.util.Arrays.stream(array);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(Object[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static <T> Stream<T> stream(T[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.stream(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(double[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static DoubleStream stream(double[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.stream(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(int[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static IntStream stream(int[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.stream(array, beginIndex, endIndex);
	}

	/**
	 * Redirect to {@link java.util.Arrays#stream(long[], int, int)}.
	 */
	@SuppressWarnings("JavaDoc")
	public static LongStream stream(long[] array, int beginIndex, int endIndex) {
		return java.util.Arrays.stream(array, beginIndex, endIndex);
	}

	//toString

	/**
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @param <T>   the type of the elements.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(Object[])
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
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(byte[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(byte[] array) {
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
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(char[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(char[] array) {
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
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(double[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(double[] array) {
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
	 * Build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @see java.util.Arrays#toString(short[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString(short[] array) {
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
	 * Using Reflection, build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#toString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString0(Object array) {
		Objects.require(array, Objects::isArray, "array");

		if (array == null)
			return "null";

		int length = Array.getLength(array);

		if (length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			builder.append(Array.get(array, i));

			if (++i < length) {
				builder.append(", ");
				continue;
			}

			return builder.append("]")
					.toString();
		}
	}

	/**
	 * Using the best {@link #toString(Object[])}, build a string representation of the contents of the given {@code array}.
	 *
	 * @param array the array to build a string representation for it.
	 * @return a string representation of the contents of the given {@code array}.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @see java.util.Arrays#toString(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static String toString1(Object array) {
		if (array instanceof Object[])
			return Arrays.toString((Object[]) array);
		if (array instanceof boolean[])
			return Arrays.toString((boolean[]) array);
		if (array instanceof byte[])
			return Arrays.toString((byte[]) array);
		if (array instanceof char[])
			return Arrays.toString((char[]) array);
		if (array instanceof double[])
			return Arrays.toString((double[]) array);
		if (array instanceof float[])
			return Arrays.toString((float[]) array);
		if (array instanceof int[])
			return Arrays.toString((int[]) array);
		if (array instanceof long[])
			return Arrays.toString((long[]) array);
		if (array instanceof short[])
			return Arrays.toString((short[]) array);

		return Arrays.toString0(array);
	}
}
