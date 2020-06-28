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
import java.util.function.BiFunction;

/**
 * Useful methods for Arrays.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.a ~2019.06.11
 */
public final class Arrayz {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Arrayz() {
		throw new AssertionError("No instance for you!");
	}

	//all

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @param <T>      the type of the elements of the given array.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static <T> int all(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (T ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (boolean ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (byte ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (char ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (double ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (float ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (int ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (long ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elements.length; ei++) {
			for (short ae : array)
				if (Objects.equals(elements[ei], ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements. Using reflection.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' aren't an array.
	 */
	public static int all0(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (!elements.getClass().isArray())
			throw new IllegalArgumentException(elements + " isn't an array");

		int elementsLength = Array.getLength(elements);
		int al = Array.getLength(array);
		//noinspection LabeledStatement
		for0:
		for (int ei = 0; ei < elementsLength; ei++) {
			Object element = Array.get(elements, ei);

			for (int ai = 0; ai < al; ai++) {
				Object ae = Array.get(array, ai);

				if (Objects.equals(element, ae))
					//noinspection ContinueStatementWithLabel
					continue for0;
			}

			return ei;
		}

		return -1;
	}

	/**
	 * Check whether the given array contains all the given elements. Using {@code if} statements.
	 *
	 * @param array    the array to be checked.
	 * @param elements to check for.
	 * @return the index of the elements missing on the given elements, Or -1 if no element missing.
	 * @throws NullPointerException     if ether the given 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' isn't an array.
	 * @throws ClassCastException       if the given 'elements' is not an array.
	 */
	public static int all1(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		if (array instanceof Object[])
			return Arrayz.all((Object[]) array, (Object[]) elements);
		if (array instanceof boolean[])
			return Arrayz.all((boolean[]) array, (boolean[]) elements);
		if (array instanceof byte[])
			return Arrayz.all((byte[]) array, (byte[]) elements);
		if (array instanceof char[])
			return Arrayz.all((char[]) array, (char[]) elements);
		if (array instanceof double[])
			return Arrayz.all((double[]) array, (double[]) elements);
		if (array instanceof float[])
			return Arrayz.all((float[]) array, (float[]) elements);
		if (array instanceof int[])
			return Arrayz.all((int[]) array, (int[]) elements);
		if (array instanceof long[])
			return Arrayz.all((long[]) array, (long[]) elements);
		if (array instanceof short[])
			return Arrayz.all((short[]) array, (short[]) elements);

		throw new IllegalArgumentException("Not an array");
	}

	//any

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @param <T>      the type of elements to look for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static <T> int any(T[] array, T... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (T element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(boolean[] array, boolean... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (boolean element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(byte[] array, byte... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (byte element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(char[] array, char... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (char element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(double[] array, double... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (double element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(float[] array, float... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (float element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(int[] array, int... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (int element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(long[] array, long... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (long element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 */
	public static int any(short[] array, short... elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		for (int ai = 0; ai < array.length; ai++)
			for (short element : elements)
				if (Objects.equals(array[ai], element))
					return ai;

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not. Using reflection.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if ether the given 'array' or 'elements' param is actually not an array.
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
				Object element = Array.get(elements, ei);

				if (Objects.equals(ae, element))
					return ai;
			}
		}

		return -1;
	}

	/**
	 * Check whether the given array contains any of the given elements or not. Using {@code if} statements.
	 *
	 * @param array    to check.
	 * @param elements to check for.
	 * @return the index of the first element found on the given array, Or -1 if no element found.
	 * @throws NullPointerException     if ether 'array' or 'elements' are null.
	 * @throws IllegalArgumentException if the given 'array' param is actually not an array.
	 * @throws ClassCastException       if the given 'elements' is not an array.
	 */
	public static int any1(Object array, Object elements) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(elements, "elements");

		if (array instanceof Object[])
			return Arrayz.any((Object[]) array, (Object[]) elements);
		if (array instanceof boolean[])
			return Arrayz.any((boolean[]) array, (boolean[]) elements);
		if (array instanceof byte[])
			return Arrayz.any((byte[]) array, (byte[]) elements);
		if (array instanceof char[])
			return Arrayz.any((char[]) array, (char[]) elements);
		if (array instanceof double[])
			return Arrayz.any((double[]) array, (double[]) elements);
		if (array instanceof float[])
			return Arrayz.any((float[]) array, (float[]) elements);
		if (array instanceof int[])
			return Arrayz.any((int[]) array, (int[]) elements);
		if (array instanceof long[])
			return Arrayz.any((long[]) array, (long[]) elements);
		if (array instanceof short[])
			return Arrayz.any((short[]) array, (short[]) elements);

		throw new IllegalArgumentException("not an array");
	}

	//asList

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @param <T>   the type of the elements from the given array.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return Arrayz.copyOf(array, array.length);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Boolean[]) Arrayz.copyOf0(array, array.length, Boolean.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Byte[]) Arrayz.copyOf0(array, array.length, Byte.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Character[]) Arrayz.copyOf0(array, array.length, Character.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Double[]) Arrayz.copyOf0(array, array.length, Double.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Float[]) Arrayz.copyOf0(array, array.length, Float.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Integer[]) Arrayz.copyOf0(array, array.length, Integer.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Long[]) Arrayz.copyOf0(array, array.length, Long.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Short[]) Arrayz.copyOf0(array, array.length, Short.class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < array.length)
					return (Object[]) Arrayz.copyOf0(array, array.length, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, array.length);

					if (length > array.length)
						a[array.length] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object. Using reflection.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
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
				return (Object[]) Arrayz.copyOf0(array, size, Object[].class);
			}

			@Override
			public Object[] toArray(Object[] a) {
				Objects.requireNonNull(a, "a");
				int length = a.length;

				if (length < size)
					return (Object[]) Arrayz.copyOf0(array, size, a.getClass());
				else {
					Arrayz.hardcopy(array, 0, a, 0, size);

					if (length > size)
						a[size] = null;

					return a;
				}
			}
		};
	}

	/**
	 * Construct a new list and add all the given elements from the given 'array' object. Using {@code if statements}.
	 *
	 * @param array to construct the list with.
	 * @return a list containing all the given elements from the given array object.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't actually an array.
	 */
	public static List asList1(Object array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.asList((Object[]) array);
		if (array instanceof boolean[])
			return Arrayz.asList((boolean[]) array);
		if (array instanceof byte[])
			return Arrayz.asList((byte[]) array);
		if (array instanceof char[])
			return Arrayz.asList((char[]) array);
		if (array instanceof double[])
			return Arrayz.asList((double[]) array);
		if (array instanceof float[])
			return Arrayz.asList((float[]) array);
		if (array instanceof int[])
			return Arrayz.asList((int[]) array);
		if (array instanceof long[])
			return Arrayz.asList((long[]) array);
		if (array instanceof short[])
			return Arrayz.asList((short[]) array);

		throw new IllegalArgumentException("not an array");
	}

	//asMap

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @param <K>   the type of the key of the returned map.
	 * @param <V>   the type of the value of the returned map.
	 * @param <E>   the type of the elements of the given array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static <K extends E, V extends E, E> Map<K, V> asMap(E... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Boolean, Boolean> asMap(boolean... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Byte, Byte> asMap(byte... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Character, Character> asMap(char... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Double, Double> asMap(double... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Float, Float> asMap(float... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Integer, Integer> asMap(int... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Long, Long> asMap(long... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' has odd length.
	 */
	public static Map<Short, Short> asMap(short... array) {
		Objects.requireNonNull(array, "array");
		if (array.length % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0; i < array.length; i += 2)
			map.put(array[i], array[i + 1]);

		return map;
	}

	/**
	 * Construct a new map from the given array. Using reflection.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' is not an array, or has an odd length.
	 */
	public static Map asMap0(Object array) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");
		if (Array.getLength(array) % 2 != 0)
			throw new IllegalArgumentException("odd length array");

		Map map = new HashMap();
		for (int i = 0, l = Array.getLength(array); i < l; i += 2)
			map.put(Array.get(array, i), Array.get(array, i + 1));

		return map;
	}

	/**
	 * Construct a new map from the given array. Using {@code if} statements.
	 *
	 * @param array the source array.
	 * @return a new map from the given array.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' is not an array, or has an odd length.
	 */
	public static Map asMap1(Object array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.asMap((Object[]) array);
		if (array instanceof boolean[])
			return Arrayz.asMap((boolean[]) array);
		if (array instanceof byte[])
			return Arrayz.asMap((byte[]) array);
		if (array instanceof char[])
			return Arrayz.asMap((char[]) array);
		if (array instanceof double[])
			return Arrayz.asMap((double[]) array);
		if (array instanceof float[])
			return Arrayz.asMap((float[]) array);
		if (array instanceof int[])
			return Arrayz.asMap((int[]) array);
		if (array instanceof long[])
			return Arrayz.asMap((long[]) array);
		if (array instanceof short[])
			return Arrayz.asMap((short[]) array);

		throw new IllegalArgumentException("not an array");
	}

	//copyOf

	/**
	 * Get a copy of the given array. Copy to a new array from the given class.
	 *
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @param <T>    the type of the elements of the given array.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given 'array' is null.
	 * @throws IllegalArgumentException if the given 'array' isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @param klass  the type of the result array.
	 * @param <T>    the type of the elements of the given array.
	 * @param <U>    the type of the elements in the returned array.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or
	 *                                  the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * Get a copy of the given array. Copy to a new array from the given class. Using reflection.
	 *
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
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
	 * Get a copy of the given array. Copy to a new array from the given class. Using reflection.
	 *
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @param klass  the type of the new array.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given class isn't an array class. Or
	 *                                  the given length is negative.
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

		Arrayz.hardcopy(array, 0, copy, 0, Math.min(Array.getLength(array), length));

		return copy;
	}

	/**
	 * Get a copy of the given array. Copy to a new array from the given class. Using {@code if} statements.
	 *
	 * @param array  the array to be copied.
	 * @param length the length of the new.
	 * @return a copy of the given array with the type of the given class.
	 * @throws NullPointerException     if the given array is null.
	 * @throws IllegalArgumentException if the given array isn't an array. Or the given length is negative.
	 */
	public static Object copyOf1(Object array, int length) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.copyOf((Object[]) array, length);
		if (array instanceof boolean[])
			return Arrayz.copyOf((boolean[]) array, length);
		if (array instanceof byte[])
			return Arrayz.copyOf((byte[]) array, length);
		if (array instanceof char[])
			return Arrayz.copyOf((char[]) array, length);
		if (array instanceof double[])
			return Arrayz.copyOf((double[]) array, length);
		if (array instanceof float[])
			return Arrayz.copyOf((float[]) array, length);
		if (array instanceof int[])
			return Arrayz.copyOf((int[]) array, length);
		if (array instanceof long[])
			return Arrayz.copyOf((long[]) array, length);
		if (array instanceof short[])
			return Arrayz.copyOf((short[]) array, length);

		throw new IllegalArgumentException("not an array");
	}

	//copyOfRange

	/**
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @param <T>        the type of the elements on the given array.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @param klass      the class of the returned array.
	 * @param <T>        the type of the elements on the given array.
	 * @param <U>        the type of the returned array.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array. Using reflection.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IllegalArgumentException  if the given 'array' isn't actually an array.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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
	 * Copies the specified range of the specified array into a new array. Using reflection.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @param klass      the class of the product array.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IllegalArgumentException  if the given 'array' isn't actually an array.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
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

	/**
	 * Copies the specified range of the specified array into a new array. Using {@code if} statements.
	 *
	 * @param array      to get a subarray of.
	 * @param beginIndex the index to begin.
	 * @param endIndex   the index to stop.
	 * @return a subarray of the given array.
	 * @throws NullPointerException      if the given 'array' is null.
	 * @throws IllegalArgumentException  if the given 'array' isn't actually an array.
	 * @throws IndexOutOfBoundsException if ether the beginIndex, or the endIndex is negative. Or if the endIndex is
	 *                                   less than beginIndex. Or if ether the beginIndex or the endIndex is greater
	 *                                   than array.length.
	 * @see String#substring
	 */
	public static Object copyOfRange1(Object array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.copyOfRange((Object[]) array, beginIndex, endIndex);
		if (array instanceof boolean[])
			return Arrayz.copyOfRange((boolean[]) array, beginIndex, endIndex);
		if (array instanceof byte[])
			return Arrayz.copyOfRange((byte[]) array, beginIndex, endIndex);
		if (array instanceof char[])
			return Arrayz.copyOfRange((char[]) array, beginIndex, endIndex);
		if (array instanceof double[])
			return Arrayz.copyOfRange((double[]) array, beginIndex, endIndex);
		if (array instanceof float[])
			return Arrayz.copyOfRange((float[]) array, beginIndex, endIndex);
		if (array instanceof int[])
			return Arrayz.copyOfRange((int[]) array, beginIndex, endIndex);
		if (array instanceof long[])
			return Arrayz.copyOfRange((long[]) array, beginIndex, endIndex);
		if (array instanceof short[])
			return Arrayz.copyOfRange((short[]) array, beginIndex, endIndex);

		throw new IllegalArgumentException("not an array");
	}

	//hardcopy

	/**
	 * Copies elements on an array from the specified source array, beginning at the specified position, to the
	 * specified position of the destination array. A subsequence of array components are copied from the source array
	 * referenced by src to the destination array referenced by dest. The number of components copied is equal to the
	 * length argument. The components at positions srcPos through srcPos+length-1 in the source array are copied into
	 * positions destPos through destPos+length-1, respectively, of the destination array.If the src and dest arguments
	 * refer to the same array object, then the copying is performed as if the components at positions srcPos through
	 * srcPos+length-1 were first copied to a temporary array with length components and then the contents of the
	 * temporary array were copied into positions destPos through destPos+length-1 of the destination array. If dest is
	 * null, then a NullPointerException is thrown. If src is null, then a NullPointerException is thrown, and the
	 * destination array is not modified. Otherwise, if any of the following is true, an ArrayStoreException is thrown,
	 * and the destination is not modified:
	 * <ul>
	 *     <li>The src argument refers to an object that is not an array.</li>
	 *     <li>The dest argument refers to an object that is not an array.</li>
	 * </ul>
	 * Otherwise, if any of the following is true, an IndexOutOfBoundsException is thrown, and the destination is not modified:
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
	 * <p>
	 * Note: the different between this method and {@link System#arraycopy(Object, int, Object, int, int)} is how it deals with primitive array. As
	 * this method don't mind if ether the src or dest have primitive or objective types. It copies anyway.
	 *
	 * @param src     the source array.
	 * @param srcPos  starting position in the source array.
	 * @param dest    the destination array.
	 * @param destPos starting position in the destination data.
	 * @param length  the number of array elements to be copied.
	 * @throws IndexOutOfBoundsException if copying would cause access of data outside array bounds.
	 * @throws ArrayStoreException       if an element in the src array could not be stored into the dest array because
	 *                                   of a type mismatch.
	 * @throws NullPointerException      if either src or dest is null.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public static void hardcopy(Object src, int srcPos, Object dest, int destPos, int length) {
		Objects.requireNonNull(src, "src");
		Objects.requireNonNull(dest, "dest");
		if (!src.getClass().isArray())
			throw new ArrayStoreException("src is not an array");
		if (!dest.getClass().isArray())
			throw new ArrayStoreException("dest is not an array");

		if (src == dest || dest.getClass().isAssignableFrom(src.getClass())) {
			//if we can use System.arraycopy, then it is better to do so,
			//considering it has the same behavior of this method
			System.arraycopy(src, srcPos, dest, destPos, length);
			return;
		}

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

		for (int i = 0, s = srcPos, d = destPos; i < length; i++, s++, d++)
			try {
				Array.set(dest, d, Array.get(src, s));
			} catch (IllegalArgumentException e) {
				throw (ArrayStoreException) new ArrayStoreException(e.getMessage()).initCause(e);
			}
	}

	//iterator

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @param <T>   the type of the iterated elements in the returned iterator.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static <T> ListIterator<T> iterator(T... array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<T>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public T next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public T previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(T t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(T t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Boolean> iterator(boolean[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Boolean>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Boolean next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Boolean previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Boolean t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Boolean t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Byte> iterator(byte[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Byte>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Byte next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Byte previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Byte t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Byte t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Character> iterator(char[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Character>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Character next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Character previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Character t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Character t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Double> iterator(double[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Double>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Double next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Double previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Double t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Double t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Float> iterator(float[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Float>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Float next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Float previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Float t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Float t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Integer> iterator(int[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Integer>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Integer next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Integer previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Integer t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Integer t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Long> iterator(long[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Long>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Long next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Long previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Long t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Long t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public static ListIterator<Short> iterator(short[] array) {
		Objects.requireNonNull(array, "array");
		return new ListIterator<Short>() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < array.length;
			}

			@Override
			public Short next() {
				if (this.cursor >= array.length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + array.length);

				return array[this.cursor++];
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Short previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return array[--this.cursor];
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Short t) {
				array[this.cursor] = t;
			}

			@Override
			public void add(Short t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size. Using reflection.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 */
	public static ListIterator iterator0(Object array) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException("Not an array " + array);
		int length = Array.getLength(array);
		return new ListIterator() {
			/**
			 * The index of the next element.
			 */
			private int cursor;

			@Override
			public boolean hasNext() {
				return this.cursor < length;
			}

			@Override
			public Object next() {
				if (this.cursor >= length)
					throw new NoSuchElementException("cursor: " + this.cursor + " length: " + length);

				return Array.get(array, this.cursor++);
			}

			@Override
			public boolean hasPrevious() {
				return this.cursor > 0;
			}

			@Override
			public Object previous() {
				if (this.cursor == 0)
					throw new NoSuchElementException("cursor: 0");

				return Array.get(array, --this.cursor);
			}

			@Override
			public int nextIndex() {
				return this.cursor;
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
			public void set(Object t) {
				Array.set(array, this.cursor, t);
			}

			@Override
			public void add(Object t) {
				throw new UnsupportedOperationException("add");
			}
		};
	}

	/**
	 * Get an iterator that iterates the elements of the given {@code array}. Note that the returned iterator will not
	 * support {@link ListIterator#remove()} nor {@link ListIterator#add(Object)} since arrays can't be modified in
	 * size. Using {@code if} statements.
	 *
	 * @param array the array that the returned iterator is iterating.
	 * @return an iterator that iterates the elements of the given {@code array}.
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 */
	public static ListIterator iterator1(Object array) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.iterator((Object[]) array);
		if (array instanceof boolean[])
			return Arrayz.iterator((boolean[]) array);
		if (array instanceof byte[])
			return Arrayz.iterator((byte[]) array);
		if (array instanceof char[])
			return Arrayz.iterator((char[]) array);
		if (array instanceof double[])
			return Arrayz.iterator((double[]) array);
		if (array instanceof float[])
			return Arrayz.iterator((float[]) array);
		if (array instanceof int[])
			return Arrayz.iterator((int[]) array);
		if (array instanceof long[])
			return Arrayz.iterator((long[]) array);
		if (array instanceof short[])
			return Arrayz.iterator((short[]) array);

		throw new IllegalArgumentException("not an array");
	}

	//merge

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @param <T>    the type of the given arrays.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static <T> T[] merge(T[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		if (arrays.length == 0)
			return (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), 0);

		T[] product = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), Arrayz.sum(arrays, 0, (s, a) ->
				s + a.length));

		int i = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param klass  the class of the returned array.
	 * @param arrays the arrays to be merged.
	 * @param <T>    the type of the given arrays.
	 * @param <U>    the type of the returned array.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static <T extends U, U> U[] merge(Class<U[]> klass, T[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		T[] product = (T[]) Array.newInstance(klass.getComponentType(), Arrayz.sum(arrays, 0, (s, a) -> s + a.length));

		int i = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static boolean[] merge(boolean[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		boolean[] product = new boolean[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (boolean[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static byte[] merge(byte[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		byte[] product = new byte[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (byte[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static char[] merge(char[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		char[] product = new char[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (char[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static double[] merge(double[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		double[] product = new double[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (double[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static float[] merge(float[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		float[] product = new float[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (float[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static int[] merge(int[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		int[] product = new int[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (int[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static long[] merge(long[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		long[] product = new long[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (long[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static short[] merge(short[]... arrays) {
		Objects.requireNonNull(arrays, "arrays");

		short[] product = new short[Arrayz.sum(arrays, 0, (s, a) -> s + a.length)];

		int i = 0;
		for (short[] array : arrays) {
			System.arraycopy(array, 0, product, i, array.length);
			i += array.length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order. Using reflection.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static Object merge0(Object[] arrays) {
		Objects.requireNonNull(arrays, "arrays");

		if (arrays.length == 0)
			return Array.newInstance(arrays.getClass().getComponentType().getComponentType(), 0);

		Object product = Array.newInstance(arrays[0].getClass().getComponentType(), Arrayz.sum(arrays, 0, (s, a) -> s +
																													Array.getLength(a)));

		int i = 0;
		for (Object array : arrays) {
			int length = Array.getLength(array);
			System.arraycopy(array, 0, product, i, length);
			i += length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order. Using reflection.
	 *
	 * @param klass  the type of the new array.
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static Object merge0(Class klass, Object[] arrays) {
		Objects.requireNonNull(arrays, "arrays");
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException(klass + " isn't an array class");

		Object product = Array.newInstance(klass.getComponentType(), Arrayz.sum(arrays, 0, (s, a) -> s +
																									 Array.getLength(a)));

		int i = 0;
		for (Object array : arrays) {
			int length = Array.getLength(array);

			//if the types don't match. Then System.arraycopy() will not work.
			Arrayz.hardcopy(array, 0, product, i, length);

			i += length;
		}

		return product;
	}

	/**
	 * Merge the given arrays in a new array. The new array will contain the content of the given arrays merged in
	 * order. Using {@code if} statements.
	 *
	 * @param arrays the arrays to be merged.
	 * @return an array that includes all the elements of the given arrays ordered with the order of the arrays given.
	 * @throws IllegalArgumentException if any of the given arrays is not actually an array. Or if the given class isn't
	 *                                  an array class.
	 * @throws NullPointerException     if 'arrays' param is null. Or if any of the given arrays are null. Or if the
	 *                                  given klass is null.
	 * @throws ArrayStoreException      if any element from the given arrays can't be stored at the product array.
	 */
	public static Object merge1(Object[] arrays) {
		Objects.requireNonNull(arrays, "arrays");

		if (arrays instanceof Object[][])
			return Arrayz.merge((Object[][]) arrays);
		if (arrays instanceof boolean[][])
			return Arrayz.merge((boolean[][]) arrays);
		if (arrays instanceof byte[][])
			return Arrayz.merge((byte[][]) arrays);
		if (arrays instanceof char[][])
			return Arrayz.merge((char[][]) arrays);
		if (arrays instanceof double[][])
			return Arrayz.merge((double[][]) arrays);
		if (arrays instanceof float[][])
			return Arrayz.merge((float[][]) arrays);
		if (arrays instanceof int[][])
			return Arrayz.merge((int[][]) arrays);
		if (arrays instanceof long[][])
			return Arrayz.merge((long[][]) arrays);
		if (arrays instanceof short[][])
			return Arrayz.merge((short[][]) arrays);

		throw new IllegalArgumentException("not an array of array");
	}

	//sum

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @param <U>        the type of the elements on the given array.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T, U> T sum(U[] array, T initialSum, BiFunction<T, U, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (U value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(boolean[] array, T initialSum, BiFunction<T, Boolean, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (boolean value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(byte[] array, T initialSum, BiFunction<T, Byte, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (byte value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(char[] array, T initialSum, BiFunction<T, Character, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (char value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(double[] array, T initialSum, BiFunction<T, Double, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (double value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(float[] array, T initialSum, BiFunction<T, Float, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (float value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(int[] array, T initialSum, BiFunction<T, Integer, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (int value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(long[] array, T initialSum, BiFunction<T, Long, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (long value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @param <T>        the type of the sum.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static <T> T sum(short[] array, T initialSum, BiFunction<T, Short, T> function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");

		T sum = initialSum;
		for (short value : array)
			sum = function.apply(sum, value);

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together. Using reflection.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static Object sum0(Object array, Object initialSum, BiFunction function) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(function, "function");
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(array + " isn't an array");

		Object sum = initialSum;
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
			sum = function.apply(sum, Array.get(array, i));

		return sum;
	}

	/**
	 * Get the total sum of the given array. By applying the given function foreach element of the given array. Then sum
	 * all the returned values together. Using {@code if} statements.
	 *
	 * @param array      to sum their elements.
	 * @param initialSum the initial sum.
	 * @param function   the function to get the value of each element.
	 * @return the total sum of the given arrays.
	 * @throws NullPointerException     if ether the given {@code array} or {@code function} is null.
	 * @throws IllegalArgumentException if any of the given array isn't actually an array.
	 */
	public static Object sum1(Object array, Object initialSum, BiFunction function) {
		Objects.requireNonNull(array, "array");

		if (array instanceof Object[])
			return Arrayz.sum((Object[]) array, initialSum, function);
		if (array instanceof boolean[])
			return Arrayz.sum((boolean[]) array, initialSum, function);
		if (array instanceof byte[])
			return Arrayz.sum((byte[]) array, initialSum, function);
		if (array instanceof char[])
			return Arrayz.sum((char[]) array, initialSum, function);
		if (array instanceof double[])
			return Arrayz.sum((double[]) array, initialSum, function);
		if (array instanceof float[])
			return Arrayz.sum((float[]) array, initialSum, function);
		if (array instanceof int[])
			return Arrayz.sum((int[]) array, initialSum, function);
		if (array instanceof long[])
			return Arrayz.sum((long[]) array, initialSum, function);
		if (array instanceof short[])
			return Arrayz.sum((short[]) array, initialSum, function);

		throw new IllegalArgumentException("not an array");
	}
}
