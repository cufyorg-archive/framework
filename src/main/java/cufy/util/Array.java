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

import java.io.Serializable;
import java.util.Objects;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
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
	 * Return the array backing this array. If this array has a restricted area on the backing
	 * array. Then a null will be returned.
	 *
	 * @return the array backing this array. Or null if this array has a restricted area on the
	 * 		backing array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public A array() {
		return this.endIndex - this.beginIndex ==
			   java.lang.reflect.Array.getLength(this.array) ?
			   this.array :
			   null;
	}

	/**
	 * Using {@link System#arraycopy(Object, int, Object, int, int)}, copy elements from this array
	 * to the given {@code array}.
	 *
	 * @param array  the destination array.
	 * @param pos    the position where to start writing in the destination array.
	 * @param length how many elements to be copied.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code pos < 0} or {@code length < 0} or {@code pos
	 *                                        + length > array.length} or {@code length >
	 *                                        this.length}.
	 * @throws ArrayStoreException            if an element can not be stored in the given {@code
	 *                                        array}. Or if either this array or the given {@code
	 *                                        array} is an of primitives and the other is an array
	 *                                        of objects. Or if both arrays are primitives but each
	 *                                        has different primitive type.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public void arraycopy(A array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new ArrayIndexOutOfBoundsException("length(" + length + ") < 0");
		if (pos < 0)
			throw new ArrayIndexOutOfBoundsException("pos(" + pos + ") < 0");

		int thisLength = this.endIndex - this.beginIndex;
		int arrayLength = java.lang.reflect.Array.getLength(array);

		if (length > thisLength)
			throw new ArrayIndexOutOfBoundsException(
					"length(" + length + ") > this.length(" + thisLength + ")"
			);
		if (pos + length > arrayLength)
			throw new ArrayIndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + arrayLength +
					")"
			);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				pos,
				length
		);
	}

	/**
	 * Get a copy of the array backing this array.
	 *
	 * @return a copy of the array backing this array.
	 * @since 0.1.5 ~2020.08.11
	 */
	public A copy() {
		int length = this.endIndex - this.beginIndex;
		A array = (A) java.lang.reflect.Array.newInstance(
				this.array.getClass().getComponentType(),
				length
		);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				length
		);

		return array;
	}

	/**
	 * Get a copy of the array backing this array.
	 *
	 * @param length the length of the constructed array.
	 * @return a copy of the array backing this array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.08.11
	 */
	public A copy(int length) {
		if (length < 0)
			throw new NegativeArraySizeException("length(" + length + ") < 0");
		A array = (A) java.lang.reflect.Array.newInstance(
				this.array.getClass().getComponentType(),
				length
		);

		System.arraycopy(
				this.array,
				this.beginIndex,
				array,
				0,
				Math.min(this.length(), length)
		);

		return array;
	}

	/**
	 * Get a copy of the array backing this array. The type of the copy will be the given {@code
	 * klass}.
	 *
	 * @param klass the type of the constructed array.
	 * @param <T>   the type of the elements.
	 * @return a copy of the array backing this array.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws ArrayStoreException      if an element can not be stored in the product array.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array class.
	 * @since 0.1.5 ~2020.08.30
	 */
	public <T> T copy(Class<? extends T> klass) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not array class");
		int length = this.endIndex - this.beginIndex;

		T product = (T) java.lang.reflect.Array.newInstance(
				klass.getComponentType(),
				length
		);

		this.copy(
				product,
				0,
				length
		);

		return product;
	}

	/**
	 * Get a copy of the array backing this array. The type of the copy will be the given {@code
	 * klass}.
	 *
	 * @param klass  the type of the constructed array.
	 * @param length the length of the constructed array.
	 * @param <T>    the type of the elements.
	 * @return a copy of the array backing this array.
	 * @throws NullPointerException       if the given {@code klass} is null.
	 * @throws ArrayStoreException        if an element can not be stored in the product array.
	 * @throws NegativeArraySizeException if {@code length < 0}.
	 * @throws IllegalArgumentException   if the given {@code klass} is not an array class.
	 * @since 0.1.5 ~2020.08.30
	 */
	public <T> T copy(Class<? extends T> klass, int length) {
		Objects.requireNonNull(klass, "klass");
		if (!klass.isArray())
			throw new IllegalArgumentException("Not array class");

		T product = (T) java.lang.reflect.Array.newInstance(
				klass.getComponentType(),
				length
		);

		this.copy(
				product,
				0,
				Math.min(length, this.endIndex - this.beginIndex)
		);

		return product;
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

	/**
	 * Calculate the hash code of the given element. This is the base hash code algorithm in this
	 * class and it should be for its subclasses.
	 *
	 * @param e the element to calculate its hashCode.
	 * @return the calculated hash code of the given element.
	 * @since 0.1.5 ~2020.08.31
	 */
	protected int hash(E e) {
		return e == null ? 0 : e.hashCode();
	}

	@Override
	public abstract Array<A, E> clone();

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract void forEach(Consumer<? super E> consumer);

	@Override
	public abstract int hashCode();

	@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
	@Override
	public abstract Iterator<E> iterator();

	@Override
	public abstract Spliterator<E> spliterator();

	@Override
	public abstract String toString();

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 *
	 * @param object the value to be searched for.
	 * @return index of the search object, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(Object[], Object)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract int binarySearch(Object object);

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
	public abstract int binarySearch(E element, Comparator<? super E> comparator);

	/**
	 * Use the best available copying method to copy elements from this array to the given {@code
	 * array}.
	 *
	 * @param array  the destination array.
	 * @param pos    the position where to start writing in the destination array.
	 * @param length how many elements to be copied.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code length < 0} or {@code pos +
	 *                                   length > array.length} or {@code length > this.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code array}.
	 *                                   Or if no available method to copy elements from this array
	 *                                   to the given {@code array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public abstract void copy(Object array, int pos, int length);

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
	 * Manually copy elements from this array to the given {@code array}.
	 *
	 * @param array  the destination array.
	 * @param pos    the position where to start writing in the destination array.
	 * @param length how many elements to be copied.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code length < 0} or {@code pos +
	 *                                   length > array.length} or {@code length > this.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code
	 *                                   array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public abstract void hardcopy(Object[] array, int pos, int length);

	/**
	 * Construct a new list backed by this array.
	 *
	 * @return a new list backed by this array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract List<E> list();

	/**
	 * Get a list iterator iterating over the elements of this array.
	 *
	 * @return a new list iterator for this array.
	 */
	public abstract ListIterator<E> listIterator();

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
	 * @throws ArrayStoreException  if an element can not be stored in this {@code array}.
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
	public abstract void parallelSort(Comparator<? super E> comparator);

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
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @see java.util.Arrays#sort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract void sort(Comparator<? super E> comparator);

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
	public abstract class ArrayIterator implements Iterator<E> {
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
		protected ArrayIterator() {
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
		protected ArrayIterator(int beginThumb) {
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
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	@SuppressWarnings({"UnqualifiedFieldAccess", "UnqualifiedMethodAccess"})
	public abstract class ArrayList implements List<E>, Serializable, RandomAccess, Cloneable {
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
			return endIndex <= beginIndex;
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
		public boolean removeIf(Predicate<? super E> predicate) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public int size() {
			return endIndex - beginIndex;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			Array.this.sort(comparator);
		}

		@Override
		public Object[] toArray() {
			return copy(Object[].class);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = endIndex - beginIndex;

			if (array.length < length)
				return (T[]) copy(array.getClass());
			if (array.length > length)
				array[length] = null;

			copy(array, 0, length);
			return array;
		}

		@Override
		public String toString() {
			return Array.this.toString();
		}

		@Override
		public abstract ArrayList clone();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	@SuppressWarnings({"UnqualifiedMethodAccess", "UnqualifiedFieldAccess"})
	public abstract class ArrayListIterator implements ListIterator<E> {
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
		protected ArrayListIterator() {
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
		protected ArrayListIterator(int beginThumb) {
			range(beginThumb);
			this.index = index(beginThumb);
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.index < endIndex;
		}

		@Override
		public boolean hasPrevious() {
			return this.index > beginIndex;
		}

		@Override
		public int nextIndex() {
			return thumb(this.index);
		}

		@Override
		public int previousIndex() {
			return thumb(this.index - 1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
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
	@SuppressWarnings("UnqualifiedFieldAccess")
	public abstract class ArrayMap<K extends E, V extends E> implements Map<K, V>, Serializable, Cloneable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7692195336903798598L;

		{
			int length = endIndex - beginIndex;
			if (length % 2 != 0)
				throw new IllegalArgumentException("length(" + length + ") % 2 != 0");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return endIndex <= beginIndex;
		}

		@Override
		public V remove(Object key) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public int size() {
			return endIndex - beginIndex >>> 1;
		}

		@Override
		public abstract ArrayMap clone();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract String toString();

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayEntry implements Entry<K, V>, Serializable, Cloneable {
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
			protected ArrayEntry(int thumb) {
				range(thumb, thumb + 1);
				this.index = index(thumb);
			}

			@Override
			public abstract ArrayEntry clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract String toString();
		}

		/**
		 * An iterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayEntryIterator implements Iterator<Map.Entry<K, V>> {
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
			protected ArrayEntryIterator() {
				this.index = beginIndex;
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayEntryIterator(int beginThumb) {
				range(beginThumb);
				this.index = index(beginThumb);
			}

			@Override
			public boolean hasNext() {
				return this.index < endIndex;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class ArrayEntrySet implements Set<Entry<K, V>>, Serializable, Cloneable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7515045887948351373L;

			@Override
			public boolean add(Map.Entry<K, V> entry) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
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
				return endIndex <= beginIndex;
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
			public boolean removeIf(Predicate<? super Map.Entry<K, V>> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return endIndex - beginIndex >>> 1;
			}

			@Override
			public abstract ArrayEntrySet clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean contains(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@Override
			public abstract void forEach(Consumer<? super Map.Entry<K, V>> consumer);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();
		}

		/**
		 * A spliterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayEntrySpliterator implements Spliterator<Map.Entry<K, V>> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected static final int CHARACTERISTICS = Spliterator.SIZED |
														 Spliterator.SUBSIZED |
														 Spliterator.ORDERED |
														 Spliterator.IMMUTABLE;

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
			protected ArrayEntrySpliterator() {
				this.index = beginIndex;
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayEntrySpliterator(int beginThumb) {
				range(beginThumb);
				this.index = index(beginThumb);
			}

			@Override
			public int characteristics() {
				return ArrayEntrySpliterator.CHARACTERISTICS;
			}

			@Override
			public long estimateSize() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public Comparator<? super Map.Entry<K, V>> getComparator() {
				throw new IllegalStateException();
			}

			@Override
			public long getExactSizeIfKnown() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public boolean hasCharacteristics(int characteristics) {
				return (ArrayEntrySpliterator.CHARACTERISTICS & characteristics) ==
					   characteristics;
			}
		}

		/**
		 * An iterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayKeyIterator implements Iterator<K> {
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
			protected ArrayKeyIterator() {
				this.index = beginIndex;
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayKeyIterator(int beginThumb) {
				range(beginThumb);
				this.index = index(beginThumb);
			}

			@Override
			public boolean hasNext() {
				return this.index < endIndex;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class ArrayKeySet implements Set<K>, Serializable, Cloneable {
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
				return ArrayMap.this.containsKey(object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (ArrayMap.this.containsKey(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return endIndex <= beginIndex;
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
			public boolean removeIf(Predicate<? super K> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return endIndex - beginIndex >>> 1;
			}

			@Override
			public abstract ArrayKeySet clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

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
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayKeySpliterator implements Spliterator<K> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected static final int CHARACTERISTICS = Spliterator.SIZED |
														 Spliterator.SUBSIZED |
														 Spliterator.ORDERED |
														 Spliterator.IMMUTABLE;
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
			protected ArrayKeySpliterator() {
				this.index = index(0);
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayKeySpliterator(int beginThumb) {
				range(beginThumb);
				this.index = index(beginThumb);
			}

			@Override
			public int characteristics() {
				return ArrayKeySpliterator.CHARACTERISTICS;
			}

			@Override
			public long estimateSize() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public Comparator<? super E> getComparator() {
				throw new IllegalStateException();
			}

			@Override
			public long getExactSizeIfKnown() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public boolean hasCharacteristics(int characteristics) {
				return (ArrayKeySpliterator.CHARACTERISTICS & characteristics) ==
					   characteristics;
			}
		}

		/**
		 * An iterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayValueIterator implements Iterator<V> {
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
			protected ArrayValueIterator() {
				this.index = beginIndex;
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayValueIterator(int beginThumb) {
				range(beginThumb);
				this.index = beginIndex + beginThumb;
			}

			@Override
			public boolean hasNext() {
				return this.index < endIndex;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A spliterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		@SuppressWarnings("UnqualifiedMethodAccess")
		public abstract class ArrayValueSpliterator implements Spliterator<V> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			protected static final int CHARACTERISTICS = Spliterator.SIZED |
														 Spliterator.SUBSIZED |
														 Spliterator.ORDERED |
														 Spliterator.IMMUTABLE;
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
			protected ArrayValueSpliterator() {
				this.index = beginIndex;
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code beginThumb}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code beginThumb < 0} or {@code beginThumb >
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			protected ArrayValueSpliterator(int beginThumb) {
				range(beginThumb);
				this.index = beginIndex + beginThumb;
			}

			@Override
			public int characteristics() {
				return ArrayValueSpliterator.CHARACTERISTICS;
			}

			@Override
			public long estimateSize() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public Comparator<? super V> getComparator() {
				throw new IllegalStateException();
			}

			@Override
			public long getExactSizeIfKnown() {
				return endIndex - this.index >>> 1;
			}

			@Override
			public boolean hasCharacteristics(int characteristics) {
				return (ArrayValueSpliterator.CHARACTERISTICS & characteristics) ==
					   characteristics;
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public abstract class ArrayValues implements Collection<V>, Serializable, Cloneable {
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
				return ArrayMap.this.containsValue(object);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				Objects.requireNonNull(collection, "collection");

				for (Object object : collection) {
					if (ArrayMap.this.containsValue(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean isEmpty() {
				return endIndex <= beginIndex;
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
			public boolean removeIf(Predicate<? super V> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return endIndex - beginIndex >>> 1;
			}

			@Override
			public abstract ArrayValues clone();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract boolean equals(Object object);

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract int hashCode();

			@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
			@Override
			public abstract Object[] toArray();

			@Override
			public abstract <T> T[] toArray(T[] array);

			@Override
			public abstract String toString();
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	@SuppressWarnings({"UnqualifiedFieldAccess", "UnqualifiedMethodAccess"})
	public abstract class ArraySpliterator implements Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected static final int CHARACTERISTICS = Spliterator.SIZED |
													 Spliterator.SUBSIZED |
													 Spliterator.ORDERED |
													 Spliterator.IMMUTABLE;
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
		protected ArraySpliterator() {
			this.index = beginIndex;
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
		protected ArraySpliterator(int beginThumb) {
			range(beginThumb);
			this.index = beginIndex + beginThumb;
		}

		@Override
		public int characteristics() {
			return ArraySpliterator.CHARACTERISTICS;
		}

		@Override
		public long estimateSize() {
			return endIndex - this.index;
		}

		@Override
		public Comparator<? super E> getComparator() {
			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return endIndex - this.index;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (ArraySpliterator.CHARACTERISTICS & characteristics) == characteristics;
		}
	}
}
