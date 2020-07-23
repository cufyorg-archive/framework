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
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.06.11
 */
public final class Arrays {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Arrays() {
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
	 * @param array the array backing the retuned list.
	 * @param <T>   the type of the elements.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> ArrayList<T> asList(T... array) {
		return new ArrayList(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	 * @param array the array backing the retuned list.
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
	public static <T extends U, U> U[] concat(Class<U[]> klass, T[]... arrays) {
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

		if (klass.getComponentType().isPrimitive() == array.getClass().isPrimitive())
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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");
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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");

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
		Objects.require(beginIndex, Array.getLength(array), Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");
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
		Objects.require(beginIndex, Array.getLength(array), Objects::isLess, ArrayIndexOutOfBoundsException.class, "beginIndex");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(klass, Class::isArray, "klass");

		int length = endIndex - beginIndex;

		Object product = Array.newInstance(klass.getComponentType(), length);

		if (klass.getComponentType().isPrimitive() == array.getClass().isPrimitive())
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
		//todo both primitives
		if (src instanceof Object[])
			Arrays.hardcopy1((Object[]) src, srcPos, dest, destPos, length);
		else if (dest instanceof Object[])
			Arrays.hardcopy1(src, srcPos, (Object[]) dest, destPos, length);
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
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @param <T>   the type of the elements.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T> ArrayIterator<T> iterator(int index, T... array) {
		return new ArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static BooleanArrayIterator iterator(int index, boolean[] array) {
		return new BooleanArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ByteArrayIterator iterator(int index, byte[] array) {
		return new ByteArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static CharacterArrayIterator iterator(int index, char[] array) {
		return new CharacterArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static DoubleArrayIterator iterator(int index, double[] array) {
		return new DoubleArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static FloatArrayIterator iterator(int index, float[] array) {
		return new FloatArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static IntegerArrayIterator iterator(int index, int[] array) {
		return new IntegerArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static LongArrayIterator iterator(int index, long[] array) {
		return new LongArrayIterator(index, array);
	}

	/**
	 * Construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ShortArrayIterator iterator(int index, short[] array) {
		return new ShortArrayIterator(index, array);
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
	 * Using Reflection, construct a new iterator iterating the elements of the given {@code array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ArrayIterator0 iterator0(int index, Object array) {
		return new ArrayIterator0(index, array);
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

	/**
	 * Using the best {@link #iterator(int, Object[])} method, construct a new iterator iterating the elements of the given {@code
	 * array}.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param index the index to start iterating at.
	 * @return a new iterator iterating the elements of the given {@code array}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static ListIterator iterator1(int index, Object array) {
		if (array instanceof Object[])
			return new ArrayIterator(index, (Object[]) array);
		if (array instanceof boolean[])
			return new BooleanArrayIterator(index, (boolean[]) array);
		if (array instanceof byte[])
			return new ByteArrayIterator(index, (byte[]) array);
		if (array instanceof char[])
			return new CharacterArrayIterator(index, (char[]) array);
		if (array instanceof double[])
			return new DoubleArrayIterator(index, (double[]) array);
		if (array instanceof float[])
			return new FloatArrayIterator(index, (float[]) array);
		if (array instanceof int[])
			return new IntegerArrayIterator(index, (int[]) array);
		if (array instanceof long[])
			return new LongArrayIterator(index, (long[]) array);
		if (array instanceof short[])
			return new ShortArrayIterator(index, (short[]) array);

		return Arrays.iterator0(index, array);
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

	//toCollection

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <T>        the type of the elements.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, C extends Collection<? super T>> C toCollection(C collection, T... array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Boolean>> C toCollection(C collection, boolean[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Byte>> C toCollection(C collection, byte[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Character>> C toCollection(C collection, char[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Double>> C toCollection(C collection, double[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Float>> C toCollection(C collection, float[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Integer>> C toCollection(C collection, int[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Long>> C toCollection(C collection, long[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @param <C>        the type of the collection.
	 * @return the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <C extends Collection<? super Short>> C toCollection(C collection, short[] array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		for (int i = 0; i < array.length; i++)
			collection.add(array[i]);

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @return the given {@code collection}.
	 * @throws NullPointerException     if the given {@code collection} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Collection toCollection0(Collection collection, Object array) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");

		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
			collection.add(Array.get(array, i));

		return collection;
	}

	/**
	 * Add all the elements in the given {@code array} to the given {@code collection}.
	 *
	 * @param collection the collection to be filled.
	 * @param array      the source array to fill the given {@code collection} with.
	 * @return the given {@code collection}.
	 * @throws NullPointerException     if the given {@code collection} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Collection toCollection1(Collection collection, Object array) {
		if (array instanceof Object[])
			return Arrays.toCollection(collection, (Object[]) array);
		if (array instanceof boolean[])
			return Arrays.toCollection(collection, (boolean[]) array);
		if (array instanceof byte[])
			return Arrays.toCollection(collection, (byte[]) array);
		if (array instanceof char[])
			return Arrays.toCollection(collection, (char[]) array);
		if (array instanceof double[])
			return Arrays.toCollection(collection, (double[]) array);
		if (array instanceof float[])
			return Arrays.toCollection(collection, (float[]) array);
		if (array instanceof int[])
			return Arrays.toCollection(collection, (int[]) array);
		if (array instanceof long[])
			return Arrays.toCollection(collection, (long[]) array);
		if (array instanceof short[])
			return Arrays.toCollection(collection, (short[]) array);

		return Arrays.toCollection0(collection, array);
	}

	//toMap

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <T>   the type of the elements.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <T, M extends Map<? super T, ? super T>> M toMap(M map, T... array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Boolean, ? super Boolean>> M toMap(M map, boolean[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Byte, ? super Byte>> M toMap(M map, byte[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Character, ? super Character>> M toMap(M map, char[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Double, ? super Double>> M toMap(M map, double[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Float, ? super Float>> M toMap(M map, float[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Integer, ? super Integer>> M toMap(M map, int[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Long, ? super Long>> M toMap(M map, long[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @param <M>   the type of the map.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static <M extends Map<? super Short, ? super Short>> M toMap(M map, short[] array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(array.length, Objects::isEven, "array");
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Using Reflection, fill the given {@code map} with the given {@code array} as key-value sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd. Or if the given {@code array} is not an
	 *                                  array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Map toMap0(Map map, Object array) {
		Objects.requireNonNull(map, "map");
		Objects.requireNonNull(array, "array");
		Objects.require(Array.getLength(array), Objects::isEven, "array");
		Objects.require(array, Objects::isArray, "array");

		int length = Array.getLength(array);
		for (int i = 0; i < length; i += 2)
			map.put(Array.get(array, i), Array.get(array, i + 1));

		return map;
	}

	/**
	 * Using the best {@link #toMap(Map, Object[])} method, fill the given {@code map} with the given {@code array} as key-value
	 * sequential pairs.
	 *
	 * @param map   the map to be filled.
	 * @param array the source array containing sequence of key and value pairs.
	 * @return the given {@code map}.
	 * @throws NullPointerException     if the given {@code map} or {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array}'s length is odd. Or if the given {@code array} is not an
	 *                                  array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public static Map toMap1(Map map, Object array) {
		if (array instanceof Object[])
			return Arrays.toMap(map, (Object[]) array);
		if (array instanceof boolean[])
			return Arrays.toMap(map, (boolean[]) array);
		if (array instanceof byte[])
			return Arrays.toMap(map, (byte[]) array);
		if (array instanceof char[])
			return Arrays.toMap(map, (char[]) array);
		if (array instanceof double[])
			return Arrays.toMap(map, (double[]) array);
		if (array instanceof float[])
			return Arrays.toMap(map, (float[]) array);
		if (array instanceof int[])
			return Arrays.toMap(map, (int[]) array);
		if (array instanceof long[])
			return Arrays.toMap(map, (long[]) array);
		if (array instanceof short[])
			return Arrays.toMap(map, (short[]) array);

		return Arrays.toMap0(map, array);
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (array.length != 0) {
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

		return "[]";
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

		if (length != 0) {
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

		return "[]";
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

	//classes

	/**
	 * An iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ArrayIterator<E> implements ListIterator<E> {
		/**
		 * The backing array.
		 */
		private final E[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayIterator(E... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayIterator(int index, E... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public E next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public E previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(E element) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = element;
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ArrayIterator0 implements ListIterator {
		/**
		 * The backing array.
		 */
		private final Object array;
		/**
		 * The length of the backing array.
		 */
		private final int length;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Using Reflection, construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array} is not an array.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayIterator0(Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			this.array = array;
			this.length = Array.getLength(array);
		}

		/**
		 * Using Reflection, construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code array} is not an array.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayIterator0(int index, Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, Array.getLength(array), Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
			this.length = Array.getLength(array);
		}

		@Override
		public void add(Object object) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Object next() {
			if (this.cursor < this.length)
				return Array.get(this.array, this.last = this.cursor++);

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Object previous() {
			if (this.cursor > -1)
				return Array.get(this.array, this.last = this.cursor--);

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Object object) {
			if (this.last < 0 || this.last >= this.length)
				throw new IllegalStateException();

			Array.set(this.array, this.last, object);
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ArrayList<E> extends AbstractList<E> {
		/**
		 * The backing array.
		 */
		private final E[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayList(E... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof ArrayList)
				return Arrays.equals(((ArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public E get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public ArrayIterator<E> iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ArrayIterator<E> listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ArrayIterator<E> listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public E set(int index, E element) {
			E old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			Arrays.sort(this.array, comparator);
		}

		@Override
		public Spliterator<E> spliterator() {
			return Arrays.spliterator(this.array);
		}

		@Override
		public Stream<E> stream() {
			return Arrays.stream(this.array);
		}

		@Override
		public E[] toArray() {
			return Arrays.copyOf(this.array, this.array.length);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return Arrays.copyOf(this.array, this.array.length, array.getClass());

			System.arraycopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ArrayList0 extends AbstractList {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final Object array;
		/**
		 * The length of the backing array of this list.
		 */
		private final int length;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array} isn't actually an array.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ArrayList0(Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			this.array = array;
			this.length = Array.getLength(array);
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof ArrayList0)
				return Arrays.equals0(((ArrayList0) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.length) {
						Object o = iterator.next();
						Object t = Array.get(this.array, i++);

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.length;
			}

			return false;
		}

		@Override
		public Object get(int index) {
			return Array.get(this.array, index);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode0(this.array);
		}

		@Override
		public Iterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ArrayIterator0 listIterator() {
			return Arrays.iterator0(this.array);
		}

		@Override
		public ArrayIterator0 listIterator(int index) {
			return Arrays.iterator0(index, this.array);
		}

		@Override
		public Object set(int index, Object element) {
			Object old = Array.get(this.array, index);
			Array.set(this.array, index, element);
			return old;
		}

		@Override
		public int size() {
			return this.length;
		}

		@Override
		public Object[] toArray() {
			return (Object[]) Arrays.copyOf0(this.array, this.length, Object[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.length)
				return (Object[]) Arrays.copyOf0(this.array, this.length, array.getClass());

			if (this.array.getClass().getComponentType().isPrimitive())
				Arrays.hardcopy0(this.array, 0, array, 0, this.length);
			else
				System.arraycopy(this.array, 0, array, 0, this.length);

			if (array.length > this.length)
				array[this.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString0(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class BooleanArrayIterator implements ListIterator<Boolean> {
		/**
		 * The backing array.
		 */
		private final boolean[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private BooleanArrayIterator(boolean... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private BooleanArrayIterator(int index, boolean... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Boolean boolean_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Boolean next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Boolean previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Boolean boolean_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = boolean_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class BooleanArrayList extends AbstractList<Boolean> {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final boolean[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private BooleanArrayList(boolean... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof BooleanArrayList)
				return Arrays.equals(((BooleanArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Boolean get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public BooleanArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public BooleanArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public BooleanArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Boolean set(int index, Boolean element) {
			Boolean old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Boolean[] toArray() {
			return (Boolean[]) Arrays.copyOf0(this.array, this.array.length, Boolean[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ByteArrayIterator implements ListIterator<Byte> {
		/**
		 * The backing array.
		 */
		private final byte[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ByteArrayIterator(byte... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ByteArrayIterator(int index, byte... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Byte byte_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Byte next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Byte previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Byte byte_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = byte_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ByteArrayList extends AbstractList<Byte> {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final byte[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ByteArrayList(byte... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof ByteArrayList)
				return Arrays.equals(((ByteArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Byte get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public ByteArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ByteArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ByteArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Byte set(int index, Byte element) {
			Byte old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Byte[] toArray() {
			return (Byte[]) Arrays.copyOf0(this.array, this.array.length, Byte.class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class CharacterArrayIterator implements ListIterator<Character> {
		/**
		 * The backing array.
		 */
		private final char[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private CharacterArrayIterator(char... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private CharacterArrayIterator(int index, char... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Character character) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Character next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Character previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Character character) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = character;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class CharacterArrayList extends AbstractList<Character> {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final char[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private CharacterArrayList(char... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof CharacterArrayList)
				return Arrays.equals(((CharacterArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Character get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public CharacterArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public CharacterArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public CharacterArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Character set(int index, Character element) {
			Character old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Character[] toArray() {
			return (Character[]) Arrays.copyOf0(this.array, this.array.length, Character.class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class DoubleArrayIterator implements ListIterator<Double> {
		/**
		 * The backing array.
		 */
		private final double[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private DoubleArrayIterator(double... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private DoubleArrayIterator(int index, double... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Double double_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Double next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Double previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Double double_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = double_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class DoubleArrayList extends AbstractList<Double> {
		//todo override sort, stream

		/**
		 * The array backing this list.
		 */
		private final double[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private DoubleArrayList(double... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof DoubleArrayList)
				return Arrays.equals(((DoubleArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Double get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public DoubleArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public DoubleArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public DoubleArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Double set(int index, Double element) {
			Double old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Spliterator.OfDouble spliterator() {
			return Arrays.spliterator(this.array);
		}

		@Override
		public Double[] toArray() {
			return (Double[]) Arrays.copyOf0(this.array, this.array.length, Double[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class FloatArrayIterator implements ListIterator<Float> {
		/**
		 * The backing array.
		 */
		private final float[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private FloatArrayIterator(float... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private FloatArrayIterator(int index, float... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Float float_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Float next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Float previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Float float_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = float_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class FloatArrayList extends AbstractList<Float> {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final float[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private FloatArrayList(float... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof FloatArrayList)
				return Arrays.equals(((FloatArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Float get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public FloatArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public FloatArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public FloatArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Float set(int index, Float element) {
			Float old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Float[] toArray() {
			return (Float[]) Arrays.copyOf0(this.array, this.array.length, Float[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class IntegerArrayIterator implements ListIterator<Integer> {
		/**
		 * The backing array.
		 */
		private final int[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private IntegerArrayIterator(int... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private IntegerArrayIterator(int index, int... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Integer integer) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Integer next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Integer previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Integer integer) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = integer;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class IntegerArrayList extends AbstractList<Integer> {
		//todo override sort, stream

		/**
		 * The array backing this list.
		 */
		private final int[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private IntegerArrayList(int... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof IntegerArrayList)
				return Arrays.equals(((IntegerArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Integer get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public IntegerArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public IntegerArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public IntegerArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Integer set(int index, Integer element) {
			Integer old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Spliterator.OfInt spliterator() {
			return Arrays.spliterator(this.array);
		}

		@Override
		public Integer[] toArray() {
			return (Integer[]) Arrays.copyOf0(this.array, this.array.length, Integer[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class LongArrayIterator implements ListIterator<Long> {
		/**
		 * The backing array.
		 */
		private final long[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private LongArrayIterator(long... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private LongArrayIterator(int index, long... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Long long_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Long next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Long previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Long long_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = long_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class LongArrayList extends AbstractList<Long> {
		//todo override sort, stream

		/**
		 * The array backing this list.
		 */
		private final long[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private LongArrayList(long... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof LongArrayList)
				return Arrays.equals(((LongArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Long get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public LongArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public LongArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public LongArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Long set(int index, Long element) {
			Long old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Spliterator.OfLong spliterator() {
			return Arrays.spliterator(this.array);
		}

		@Override
		public Long[] toArray() {
			return (Long[]) Arrays.copyOf0(this.array, this.array.length, Long[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ShortArrayIterator implements ListIterator<Short> {
		/**
		 * The backing array.
		 */
		private final short[] array;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ShortArrayIterator(short... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index to start iterating at.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ShortArrayIterator(int index, short... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index");
			this.array = array;
			this.cursor = index;
		}

		@Override
		public void add(Short short_) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.array.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > -1;
		}

		@Override
		public Short next() {
			if (this.cursor < this.array.length)
				return this.array[this.last = this.cursor++];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Short previous() {
			if (this.cursor > -1)
				return this.array[this.last = this.cursor--];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Short short_) {
			if (this.last < 0 || this.last >= this.array.length)
				throw new IllegalStateException();

			this.array[this.last] = short_;
		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ShortArrayList extends AbstractList<Short> {
		//todo override sort, spliterator, stream

		/**
		 * The array backing this list.
		 */
		private final short[] array;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing this list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		private ShortArrayList(short... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof ShortArrayList)
				return Arrays.equals(((ShortArrayList) object).array, this.array);
			if (object instanceof List) {
				Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.array.length) {
						Object o = iterator.next();
						Object t = this.array[i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.array.length;
			}

			return false;
		}

		@Override
		public Short get(int index) {
			return this.array[index];
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(this.array);
		}

		@Override
		public ShortArrayIterator iterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ShortArrayIterator listIterator() {
			return Arrays.iterator(this.array);
		}

		@Override
		public ShortArrayIterator listIterator(int index) {
			return Arrays.iterator(index, this.array);
		}

		@Override
		public Short set(int index, Short element) {
			Short old = this.array[index];
			this.array[index] = element;
			return old;
		}

		@Override
		public int size() {
			return this.array.length;
		}

		@Override
		public Short[] toArray() {
			return (Short[]) Arrays.copyOf0(this.array, this.array.length, Short[].class);
		}

		@Override
		public Object[] toArray(Object[] array) {
			Objects.requireNonNull(array, "array");

			if (array.length < this.array.length)
				return (Object[]) Arrays.copyOf0(this.array, this.array.length, array.getClass());

			Arrays.hardcopy(this.array, 0, array, 0, this.array.length);

			if (array.length > this.array.length)
				array[this.array.length] = null;

			return array;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.array);
		}
	}
}
