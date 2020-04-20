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
import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Useful methods for Arrays.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 11 Jun 2019
 */
final public class Arrays {
	/**
	 * This is a util class. And shall not be instanced as an object.
	 *
	 * @throws AssertionError when called
	 */
	private Arrays() {
		throw new AssertionError("No instance for you!");
	}

	//all

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @param <T>      the type of the elements of the given array
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static <T> int all(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (T ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (boolean ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (byte ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (char ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (double ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (float ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (int ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (long ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (short ae : array)
				if (Objects.equals(elements[ei], ae))
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all of the given elements.
	 *
	 * @param array    the array to be checked
	 * @param elements to check for
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array
	 */
	public static int all0(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (!elements.getClass().isArray())
			throw new IllegalArgumentException(elements + " isn't an array");

		int el = Array.getLength(elements);
		int al = Array.getLength(array);
		for0:
		for (int ei = 0; ei < el; ei++) {
			Object ee = Array.get(elements, ei);

			for (int ai = 0; ai < al; ai++) {
				Object ae = Array.get(array, ai);

				if (Objects.equals(ee, ae))
					continue for0;
			}

			return ei;
		}

		return -1;
	}

	//any

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @param <T>      the type of elements to look for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static <T> int any(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (T ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (boolean ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (byte ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (char ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (double ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (float ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (int ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (long ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array
	 */
	public static int any(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (short ee : elements)
				if (Objects.equals(array[ai], ee))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check
	 * @param elements to check for
	 * @return the index of the first element found on the given array, Or -1 if no element found
	 * @throws NullPointerException     if ether 'array' or 'elements' are null
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' param is actually not an array
	 */
	public static int any0(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (!elements.getClass().isArray())
			throw new IllegalArgumentException(elements + " isn't an array");

		int al = Array.getLength(array);
		int el = Array.getLength(elements);
		for (int ai = 0; ai < al; ai++) {
			Object ae = Array.get(array, ai);

			for (int ei = 0; ei < el; ei++) {
				Object ee = Array.get(elements, ei);

				if (Objects.equals(ae, ee))
					return ai;
			}
		}

		return -1;
	}

	//asList

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @param <T>   the type of the elements from the given array
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static <T> List<T> asList(T... array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<T>() {
			@Override
			public T get(int index) {
				return array[index];
			}

			@Override
			public T set(int index, T element) {
				T old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public T[] toArray() {
				return Arrays.copyOf(array, array.length);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Boolean> asList(boolean[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Boolean>() {
			@Override
			public Boolean get(int index) {
				return array[index];
			}

			@Override
			public Boolean set(int index, Boolean element) {
				Boolean old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Boolean[] toArray() {
				return (Boolean[]) Arrays.copyOf0(array, array.length, Boolean.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Byte> asList(byte[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Byte>() {
			@Override
			public Byte get(int index) {
				return array[index];
			}

			@Override
			public Byte set(int index, Byte element) {
				Byte old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Byte[] toArray() {
				return (Byte[]) Arrays.copyOf0(array, array.length, Byte.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Character> asList(char[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Character>() {
			@Override
			public Character get(int index) {
				return array[index];
			}

			@Override
			public Character set(int index, Character element) {
				Character old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Character[] toArray() {
				return (Character[]) Arrays.copyOf0(array, array.length, Character.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Double> asList(double[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Double>() {
			@Override
			public Double get(int index) {
				return array[index];
			}

			@Override
			public Double set(int index, Double element) {
				Double old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Double[] toArray() {
				return (Double[]) Arrays.copyOf0(array, array.length, Double.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Float> asList(float[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Float>() {
			@Override
			public Float get(int index) {
				return array[index];
			}

			@Override
			public Float set(int index, Float element) {
				Float old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Float[] toArray() {
				return (Float[]) Arrays.copyOf0(array, array.length, Float.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Integer> asList(int[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Integer>() {
			@Override
			public Integer get(int index) {
				return array[index];
			}

			@Override
			public Integer set(int index, Integer element) {
				Integer old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Integer[] toArray() {
				return (Integer[]) Arrays.copyOf0(array, array.length, Integer.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Long> asList(long[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Long>() {
			@Override
			public Long get(int index) {
				return array[index];
			}

			@Override
			public Long set(int index, Long element) {
				Long old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Long[] toArray() {
				return (Long[]) Arrays.copyOf0(array, array.length, Long.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List<Short> asList(short[] array) {
		Objects.requireNonNull(array, "array");

		return new AbstractList<Short>() {
			@Override
			public Short get(int index) {
				return array[index];
			}

			@Override
			public Short set(int index, Short element) {
				Short old = array[index];
				array[index] = element;
				return old;
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Short[] toArray() {
				return (Short[]) Arrays.copyOf0(array, array.length, Short.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < array.length) {
					return (Object[]) Arrays.copyOf0(array, array.length, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, array.length);
					else hardcopy(array, 0, a, 0, array.length);

					if (length > array.length) {
						a[array.length] = null;
					}

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with
	 * @return a list containing all the given elements from the given array object
	 * @throws NullPointerException     if the given 'array' is null
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array
	 */
	public static List asList0(Object array) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");

		int size = Array.getLength(array);
		return new AbstractList() {
			@Override
			public Object get(int index) {
				return Array.get(array, index);
			}

			@Override
			public Object set(int index, Object element) {
				Object old = Array.get(array, index);
				Array.set(array, index, element);
				return old;
			}

			@Override
			public int size() {
				return size;
			}

			@Override
			public Object[] toArray() {
				return (Object[]) Arrays.copyOf0(array, size, Object[].class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				final int length = a.length;

				if (length < size) {
					return (Object[]) Arrays.copyOf0(array, size, a.getClass());
				} else {
					if (a.getClass().isAssignableFrom(array.getClass()))
						System.arraycopy(array, 0, a, 0, size);
					else hardcopy(array, 0, a, 0, size);

					if (length > size) {
						a[size] = null;
					}

					return a;
				}
			}
		};
	}

	//copyOf

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @param <T>    the type of the elements of the given array
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static <T> T[] copyOf(T[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		T[] copy = (T[]) Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @param klass  the type of the result array
	 * @param <T>    the type of the elements of the given array
	 * @param <U>    the type of the elements in the returned array
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static <T extends U, U> U[] copyOf(T[] array, int length, Class<U[]> klass) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " isn't a class for an array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		U[] copy = (U[]) Array.newInstance(klass.getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static boolean[] copyOf(boolean[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		boolean[] copy = new boolean[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static byte[] copyOf(byte[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		byte[] copy = new byte[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static char[] copyOf(char[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		char[] copy = new char[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static double[] copyOf(double[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		double[] copy = new double[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static float[] copyOf(float[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		float[] copy = new float[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static int[] copyOf(int[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		int[] copy = new int[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static long[] copyOf(long[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		long[] copy = new long[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static short[] copyOf(short[] array, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		short[] copy = new short[length];

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static Object copyOf0(Object array, int length) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		Object copy = Array.newInstance(array.getClass().getComponentType(), length);

		System.arraycopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied
	 * @param length the length of the new
	 * @param klass  the type of the new array
	 * @return a copy of the given array with the type of the given class
	 * @throws NullPointerException     if the given array is null
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or the given length is negative
	 */
	public static Object copyOf0(Object array, int length, Class klass) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(klass, "klass");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " isn't a class for an array");
		if (length < 0)
			throw new NegativeArraySizeException("negative length provided (" + length + ")");

		Object copy = Array.newInstance(klass.getComponentType(), length);

		if (klass.isAssignableFrom(array.getClass()))
			System.arraycopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));
		else hardcopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));

		return copy;
	}

	//copyOfRange

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @param <T>        the type of the elements on the given array
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static <T> T[] copyOfRange(T[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		T[] product = (T[]) Array.newInstance(array.getClass().getComponentType(), subLen);

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @param klass      the class of the returned array
	 * @param <T>        the type of the elements on the given array
	 * @param <U>        the type of the returned array
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static <T extends U, U> U[] copyOfRange(T[] array, int beginIndex, int endIndex, Class<U[]> klass) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		T[] product = (T[]) Array.newInstance(array.getClass().getComponentType(), subLen);

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static boolean[] copyOfRange(boolean[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		boolean[] product = new boolean[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static byte[] copyOfRange(byte[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		byte[] product = new byte[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static char[] copyOfRange(char[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		char[] product = new char[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static double[] copyOfRange(double[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		double[] product = new double[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static float[] copyOfRange(float[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		float[] product = new float[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static int[] copyOfRange(int[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		int[] product = new int[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static long[] copyOfRange(long[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		long[] product = new long[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static short[] copyOfRange(short[] array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > array.length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > array.length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		short[] product = new short[subLen];

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IllegalArgumentException  if the given 'array' isn't actually an array
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static Object copyOfRange0(Object array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");

		int length = Array.getLength(array);
		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		Object product = Array.newInstance(array.getClass().getComponentType(), subLen);

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	/**
	 * Remove the last and the first elements of the given {@link Object array}. Depending on the given values.
	 *
	 * @param array      to get a subarray of
	 * @param beginIndex the index to begin
	 * @param endIndex   the index to stop
	 * @param klass      the class of the product array
	 * @return a subarray of the given array
	 * @throws NullPointerException      if the given 'array' is null
	 * @throws IllegalArgumentException  if the given 'array' isn't actually an array
	 * @throws IndexOutOfBoundsException if ether the beginIndex or the endIndex is negative. Or if the endIndex is less than beginIndex. Or if ether
	 *                                   the beginIndex or the endIndex is greater than array.length.
	 * @see String#substring
	 */
	public static Object copyOfRange0(Object array, int beginIndex, int endIndex, Class klass) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " is not a class for arrays");

		int length = Array.getLength(array);
		int subLen = endIndex - beginIndex;

		if (beginIndex < 0)
			throw new IndexOutOfBoundsException("beginIndex is negative");
		if (endIndex < 0)
			throw new IndexOutOfBoundsException("endIndex is negative");
		if (endIndex < beginIndex)
			throw new IndexOutOfBoundsException("endIndex is less than beginIndex");
		if (beginIndex > length)
			throw new IndexOutOfBoundsException("beginIndex is greater than array.length");
		if (endIndex > length)
			throw new IndexOutOfBoundsException("endIndex is greater than array.length");

		Object product = Array.newInstance(klass.getComponentType(), subLen);

		System.arraycopy(array, beginIndex, product, 0, subLen);

		return product;
	}

	//hardcopy

	/**
	 * Copies elements on an array from the specified source array, beginning at the specified position, to the specified position of the destination
	 * array. A subsequence of array components are copied from the source array referenced by src to the destination array referenced by dest. The
	 * number of components copied is equal to the length argument. The components at positions srcPos through srcPos+length-1 in the source array are
	 * copied into positions destPos through destPos+length-1, respectively, of the destination array.If the src and dest arguments refer to the same
	 * array object, then the copying is performed as if the components at positions srcPos through srcPos+length-1 were first copied to a temporary
	 * array with length components and then the contents of the temporary array were copied into positions destPos through destPos+length-1 of the
	 * destination array. If dest is null, then a NullPointerException is thrown. If src is null, then a NullPointerException is thrown and the
	 * destination array is not modified. Otherwise, if any of the following is true, an ArrayStoreException is thrown and the destination is not
	 * modified:
	 * <ul>
	 *     <li>The src argument refers to an object that is not an array.</li>
	 *     <li>The dest argument refers to an object that is not an array.</li>
	 * </ul>
	 * Otherwise, if any of the following is true, an IndexOutOfBoundsException is thrown and the destination is not modified:
	 * <ul>
	 *     <li>The srcPos argument is negative.</li>
	 *     <li>The destPos argument is negative.</li>
	 *     <li>The length argument is negative.</li>
	 *     <li>srcPos+length is greater than src.length, the length of the source array.</li>
	 *     <li>destPos+length is greater than dest.length, the length of the destination array.</li>
	 * </ul>
	 * Otherwise, if any actual component of the source array from position srcPos through srcPos+length-1 cannot be converted to the component type
	 * of the destination array by assignment conversion, an ArrayStoreException is thrown. In this case, let k be the smallest nonnegative integer
	 * less than length such that src[srcPos+k] cannot be converted to the component type of the destination array; when the exception is thrown,
	 * source array components from positions srcPos through srcPos+k-1 will already have been copied to destination array positions destPos through
	 * destPos+k-1 and no other positions of the destination array will have been modified. (Because of the restrictions already itemized, this
	 * paragraph effectively applies only to the situation where both arrays have component types that are reference types.)
	 *
	 * @param src     the source array.
	 * @param srcPos  starting position in the source array.
	 * @param dest    the destination array.
	 * @param destPos starting position in the destination data.
	 * @param length  the number of array elements to be copied.
	 * @throws IndexOutOfBoundsException if copying would cause access of data outside array bounds.
	 * @throws ArrayStoreException       if an element in the src array could not be stored into the dest array because of a type mismatch.
	 * @throws NullPointerException      if either src or dest is null.
	 * @apiNote the different between this method and {@link System#arraycopy(Object, int, Object, int, int)} is how it deals with primitive array. As
	 * this method don't mind if ether the src or dest have primitive or objective types. It copies anyway.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public static void hardcopy(Object src, int srcPos, Object dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");

		//actually no need for this method
		if (src == dest || dest.getClass().isAssignableFrom(src.getClass())) {
			System.arraycopy(src, srcPos, dest, destPos, length);
			return;
		}

		if (!src.getClass().isArray())
			throw new ArrayStoreException("src is not an array");
		if (!dest.getClass().isArray())
			throw new ArrayStoreException("dest is not an array");

		int srcLength = Array.getLength(src);
		int destLength = Array.getLength(dest);

		if (length < 0)
			throw new IndexOutOfBoundsException("length is negative");
		if (srcLength < 0)
			throw new IndexOutOfBoundsException("srcLength is negative");
		if (destLength < 0)
			throw new IndexOutOfBoundsException("destLength is negative");
		if (srcPos + length > srcLength)
			throw new IndexOutOfBoundsException("srcPos+length is greater than src.length");
		if (destPos + length > destLength)
			throw new IndexOutOfBoundsException("destPos+length is greater than dest.length");

		Class<?> type = dest.getClass().getComponentType();
		for (int i = 0; i < length; i++)
			try {
				Array.set(dest, destPos++, Reflection.primitiveCast(type, Array.get(src, srcPos++)));
			} catch (IllegalArgumentException e) {
				throw (ArrayStoreException) new ArrayStoreException(e.getMessage()).initCause(e);
			}
	}

	//merge

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @param <T>    the type of the given arrays
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static <T> T[] merge(T[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		if (arrays.length == 0)
			return (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), 0);

		T[] product = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), sum(arrays, 0, (s, a) -> s + Array.getLength(a)));

		int i = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param klass  the class of the returned array
	 * @param arrays the arrays to be merged
	 * @param <T>    the type of the given arrays
	 * @param <U>    the type of the returned array
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static <T extends U, U> U[] merge(Class<U[]> klass, T[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		T[] product = (T[]) Array.newInstance(klass.getComponentType(), sum(arrays, 0, (s, a) -> s + Array.getLength(a)));

		int i = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static boolean[] merge(boolean[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		boolean[] product = new boolean[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (boolean[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static byte[] merge(byte[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		byte[] product = new byte[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (byte[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static char[] merge(char[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		char[] product = new char[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (char[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static double[] merge(double[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		double[] product = new double[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (double[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static float[] merge(float[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		float[] product = new float[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (float[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static int[] merge(int[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int[] product = new int[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (int[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static long[] merge(long[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		long[] product = new long[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (long[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static short[] merge(short[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		short[] product = new short[sum(arrays, 0, (s, a) -> s + Array.getLength(a))];

		int i = 0;
		for (short[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param arrays the arrays to be merged
	 * @param <T>    the type of the returned array
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static <T> T merge0(T[] arrays) {
		Objects.requireNonNull(arrays, "arrays");

		if (arrays.length == 0)
			return (T) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), 0);

		T product = (T) Array.newInstance(arrays[0].getClass().getComponentType(), sum(arrays, 0, (s, a) -> s + Array.getLength(a)));

		int i = 0;
		for (Object array : arrays) {
			int length = Array.getLength(array);
			System.arraycopy(array, 0, product, i, length);
			i += length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in order.
	 *
	 * @param klass  the type of the new array
	 * @param arrays the arrays to be merged
	 * @param <T>    the type of the returned array
	 * @param <U>    the type of the returned array
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't an array class
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the given klass is null
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array
	 */
	public static <T extends U, U> U merge0(Class<U> klass, T[] arrays) {
		Objects.requireNonNull(arrays, "arrays");
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " isn't an array class");

		T product = (T) Array.newInstance(klass.getComponentType(), sum(arrays, 0, (s, a) -> s + Array.getLength(a)));

		int i = 0;
		for (Object array : arrays) {
			int length = Array.getLength(array);

			//if the types don't match. Then System.arraycopy() will not work
			if (klass.isAssignableFrom(array.getClass()))
				System.arraycopy(array, 0, product, i, length);
			else hardcopy(array, 0, product, i, length);

			i += length;
		}

		return product;
	}

	//sum

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @param <U>      the type of the elements on the given array
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T, U> T sum(U[] array, T sum, BiFunction<T, U, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (U value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(boolean[] array, T sum, BiFunction<T, Boolean, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (boolean value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(byte[] array, T sum, BiFunction<T, Byte, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (byte value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(char[] array, T sum, BiFunction<T, Character, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (char value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(double[] array, T sum, BiFunction<T, Double, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (double value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(float[] array, T sum, BiFunction<T, Float, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (float value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(int[] array, T sum, BiFunction<T, Integer, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (int value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(long[] array, T sum, BiFunction<T, Long, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (long value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum(short[] array, T sum, BiFunction<T, Short, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		for (short value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum all the returned values
	 * together.
	 *
	 * @param array    to sum their elements
	 * @param sum      the initial sum
	 * @param function the function to get the value of each element
	 * @param <T>      the type of the sum
	 * @return the total sum of the given arrays
	 * @throws NullPointerException     if ether the given 'array' or the given 'function' is null
	 * @throws IllegalArgumentException if any of the given array isn't actually an array
	 */
	public static <T> T sum0(Object array, T sum, BiFunction<T, Object, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");

		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
			sum = function.apply(sum, Array.get(array, i));

		return sum;
	}
}
