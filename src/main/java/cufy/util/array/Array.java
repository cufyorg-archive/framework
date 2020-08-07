package cufy.util.array;

import cufy.util.Objects;

import java.io.Serializable;
import java.util.*;
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
	protected Array(A array, int beginIndex, int endIndex) {
		Objects.requireNonNull(array, "array");
		int length = java.lang.reflect.Array.getLength(array);

		if (beginIndex < 0)
			throw new ArrayIndexOutOfBoundsException("beginIndex(" + beginIndex + ") < 0");
		if (endIndex > length)
			throw new ArrayIndexOutOfBoundsException("endIndex(" + endIndex + ") > length(" + length + ")");
		if (beginIndex > endIndex)
			throw new IllegalArgumentException("beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");

		this.array = array;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}

	/**
	 * Insure that this array has even length.
	 *
	 * @throws IllegalArgumentException if {@code length % 2 != 0}
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final void requireEven() {
		int length = this.endIndex - this.beginIndex;
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
	protected final void requireIndex(int index) {
		int length = this.endIndex - this.beginIndex;
		if (index < 0)
			throw new IndexOutOfBoundsException("index(" + index + ") < 0");
		if (index >= length)
			throw new IndexOutOfBoundsException("index(" + index + ") >= length(" + length + ")");
	}

	/**
	 * Insure that the specified index is valid as a start/end point of this array.
	 *
	 * @param index the index to be checked.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	protected final void requireRange(int index) {
		int length = this.endIndex - this.beginIndex;
		if (index < 0)
			throw new IndexOutOfBoundsException("index(" + index + ") < 0");
		if (index > length)
			throw new IndexOutOfBoundsException("index(" + index + ") > length(" + length + ")");
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
	protected final void requireRange(int beginIndex, int endIndex) {
		int length = this.endIndex - this.beginIndex;
		if (beginIndex < 0)
			throw new ArrayIndexOutOfBoundsException("beginIndex(" + beginIndex + ") < 0");
		if (endIndex > length)
			throw new ArrayIndexOutOfBoundsException("endIndex(" + endIndex + ") > length(" + length + ")");
		if (beginIndex > endIndex)
			throw new IllegalArgumentException("beginIndex(" + beginIndex + ") > endIndex(" + endIndex + ")");
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

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract void forEach(Consumer<? super E> consumer);

	@Override
	public abstract int hashCode();

	@Override
	public abstract ArrayIterator iterator();

	@Override
	public abstract ArraySpliterator spliterator();

	@Override
	public abstract String toString();

	/**
	 * Determine the index of the first element in the given {@code elements} that does not equal any element in this array.
	 *
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does not equal any element in this array. Or -1
	 * 		if no such element found.
	 * @throws NullPointerException if the given {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int all(E... elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does not equal any element in this array.
	 *
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does not equal any element in this array. Or -1
	 * 		if no such element found.
	 * @throws NullPointerException if the given {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int all(A elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in this array.
	 *
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in this array. Or -1 if no
	 * 		such element found.
	 * @throws NullPointerException if the {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int any(E... elements);

	/**
	 * Determine the index of the first element in the given {@code elements} that does equal any element in this array.
	 *
	 * @param elements to check for.
	 * @return the index of the first element in the given {@code elements} that does equal any element in this array. Or -1 if no
	 * 		such element found.
	 * @throws NullPointerException if the {@code elements} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract int any(A elements);

	/**
	 * Using {@link System#arraycopy(Object, int, Object, int, int)}, copy all elements of this array to the given {@code dest}.
	 *
	 * @param dest the destination array.
	 * @param pos  the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > dest.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code dest}.
	 */
	public abstract void arraycopy(A dest, int pos);

	/**
	 * Construct a new set backed by the keys in this array.
	 *
	 * @param <K> the type of the keys.
	 * @return a new set backed by the keys in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> ArrayKeySet<K> asKeySet();

	/**
	 * Construct a new list backed by this array.
	 *
	 * @return a new list backed by this array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArrayList asList();

	/**
	 * Construct a new map backed by this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new map backed by this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayMap<K, V> asMap();

	/**
	 * Construct a new set backed by this array.
	 *
	 * @return a set backed by this array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArraySet asSet();

	/**
	 * Construct a new collection backed by the values in this array.
	 *
	 * @param <V> the type of the values.
	 * @return a new collection backed by the values of this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> ArrayValues<V> asValues();

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array must be sorted prior to
	 * making this call. If it is not sorted, the results are undefined. If the array contains multiple elements with the
	 * specified value, there is no guarantee which one will be found.
	 *
	 * @param element the value to be searched for.
	 * @return index of the search element, if it is contained in the array; otherwise, -1.
	 */
	public abstract int binarySearch(E element);

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array must be sorted prior to
	 * making this call. If it is not sorted, the results are undefined. If the array contains multiple elements with the
	 * specified value, there is no guarantee which one will be found.
	 *
	 * @param element    the value to be searched for.
	 * @param comparator the comparator by which the array is ordered. A null value indicates that the elements' natural ordering
	 *                   should be used.
	 * @return index of the search element, if it is contained in the array; otherwise, -1.
	 */
	public abstract int binarySearch(E element, Comparator<? super E> comparator);

	/**
	 * Construct a new array from concatenating this array with the given {@code arrays} (in order).
	 *
	 * @param arrays the arrays to be concatenated with this array to construct the returned array.
	 * @return a new array from concatenating this array with the given {@code arrays} (in order).
	 * @throws NullPointerException if the given {@code arrays} is null.
	 * @throws ArrayStoreException  if an element can not be stored in the copy of this array.
	 */
	public abstract Array<A, E> concat(A... arrays);

	/**
	 * Determine if this object deeply equals the given {@code object} in deep lengths, deep elements, and deep orderings.
	 *
	 * @param object the other object to be compared with this.
	 * @return true, if this object deeply equals the given {@code object} in deep lengths, deep elements, and deep orderings.
	 */
	public abstract boolean deepEquals(Object object);

	/**
	 * Calculate the hash code of the elements deeply stored in this array.
	 *
	 * @return the hash code of the elements deeply stored in this array.
	 */
	public abstract int deepHashCode();

	/**
	 * Build a string representation of the deep contents this array.
	 *
	 * @return a string representation of the deep contents of this array.
	 */
	public abstract String deepToString();

	/**
	 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in this array. With the key been the first
	 * index in that range and the value been the last index.
	 *
	 * @param index the index to where the key (followed by the value) will be in the constructed entry.
	 * @param <K>   the type of the key in the constructed entry.
	 * @param <V>   the type of the value in the constructed entry.
	 * @return a new entry backed by a range from {@code index} to {@code index + 1}.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntry<K, V> entryAt(int index);

	/**
	 * Construct a new iterator iterating the entries in this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new iterator iterating the entries in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntryIterator<K, V> entryIterator();

	/**
	 * Construct a new iterator iterating the entries in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned iterator.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values.
	 * @return a new iterator iterating the entries in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntryIterator<K, V> entryIterator(int index);

	/**
	 * Construct a new set backed by the entries in this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new set backed by the entries in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntrySet<K, V> entrySet();

	/**
	 * Construct a new spliterator iterating the entries in this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new spliterator iterating the entries in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntrySpliterator<K, V> entrySpliterator();

	/**
	 * Construct a new spliterator iterating the entries in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned spliterator.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values.
	 * @return a new spliterator iterating the entries in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E, V extends E> ArrayEntrySpliterator<K, V> entrySpliterator(int index);

	/**
	 * Assign the given {@code element} to each element of this array.
	 *
	 * @param element the element to fill this array with.
	 * @throws ArrayStoreException if the given {@code element} can not be stored in this {@code array}.
	 */
	public abstract void fill(E element);

	/**
	 * Get the element at the given {@code index} in this array.
	 *
	 * @param index the index to get the element from.
	 * @return the element at the given {@code index} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract E get(int index);

	/**
	 * Manually copy all elements of this array to the given {@code dest}.
	 *
	 * @param dest the destination array.
	 * @param pos  the position where to start writing in the destination array.
	 * @throws NullPointerException      if the given {@code dest} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code pos + length > dest.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code dest}.
	 */
	public abstract void hardcopy(Object[] dest, int pos);

	/**
	 * Construct a new iterator iterating the elements in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned iterator.
	 * @return a new iterator iterating the entries in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArrayIterator iterator(int index);

	/**
	 * Construct a new iterator iterating the keys in this array.
	 *
	 * @param <K> the type of the keys.
	 * @return a new iterator iterating the keys in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> ArrayKeyIterator<K> keyIterator();

	/**
	 * Construct a new iterator iterating the keys in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned iterator.
	 * @param <K>   the type of the keys.
	 * @return a new iterator iterating the keys in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> ArrayKeyIterator<K> keyIterator(int index);

	/**
	 * Construct a new spliterator iterating the keys in this array.
	 *
	 * @param <K> the type of the keys.
	 * @return a new spliterator iterating the keys in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> ArrayKeySpliterator<K> keySpliterator();

	/**
	 * Construct a new spliterator iterating the keys in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned spliterator.
	 * @param <K>   the type of the keys.
	 * @return a new spliterator iterating the keys in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <K extends E> ArrayKeySpliterator<K> keySpliterator(int index);

	/**
	 * Construct a new list iterator iterating the elements in this array.
	 *
	 * @return a new list iterator iterating the elements in this array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArrayListIterator listIterator();

	/**
	 * Construct a new list iterator iterating the elements in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned iterator.
	 * @return a new list iterator iterating the elements in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArrayListIterator listIterator(int index);

	/**
	 * Cumulates, in parallel, each element of this array in place, using the supplied function. For example if this array
	 * initially holds [2, 1, 0, 3] and the operation performs addition, then upon return this array holds [2, 3, 3, 6]. Parallel
	 * prefix computation is usually more efficient than sequential loops for large arrays.
	 *
	 * @param operator a side-effect-free, associative function to perform the cumulation.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @see java.util.Arrays#parallelPrefix(Object[], BinaryOperator)
	 */
	public abstract void parallelPrefix(BinaryOperator<? extends E> operator);

	/**
	 * In parallel, assign each element of this array to the value returned from invoking the given {@code function} with the
	 * index of that element.
	 *
	 * @param function the function returning the new value of an element by its index.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @throws ArrayStoreException  if an element can not be stored in this {@code array}.
	 * @see java.util.Arrays#parallelSetAll(Object[], IntFunction)
	 */
	public abstract void parallelSetAll(IntFunction<? extends E> function);

	/**
	 * Sorts this array into ascending order, according to the natural ordering of its elements. All elements in this array must
	 * implement the {@link Comparable} interface. Furthermore, all elements in the array must be mutually comparable (that is,
	 * e1.compareTo(e2) must not throw a {@link ClassCastException} for any elements e1 and e2 in the array). This sort is
	 * guaranteed to be stable: equal elements will not be reordered as a result of the sort.
	 *
	 * @see java.util.Arrays#parallelSort(Comparable[])
	 */
	public abstract void parallelSort();

	/**
	 * Sorts this array according to the order induced by the specified {@code comparator}. All elements in this array must be
	 * mutually comparable by the specified {@code comparator} (that is, c.compare(e1, e2) must not throw a {@link
	 * ClassCastException} for any elements e1 and e2 in the array). This sort is guaranteed to be stable: equal elements will not
	 * be reordered as a result of the sort.
	 *
	 * @param comparator the comparator to determine the order of this array. A null value indicates that the elements' natural
	 *                   ordering should be used.
	 * @see java.util.Arrays#parallelSort(Object[], Comparator)
	 */
	public abstract void parallelSort(Comparator<? super E> comparator);

	/**
	 * Get a parallel stream streaming the elements in this array.
	 *
	 * @return a parallel stream streaming the elements in this array.
	 */
	public abstract Stream<E> parallelStream();

	/**
	 * Set the element at the given {@code index} in this array to the given {@code element}.
	 *
	 * @param index   the index to set the given {@code element} to.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the array.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract void set(int index, E element);

	/**
	 * Assign each element of this array to the value returned from invoking the given {@code function} with the index of that
	 * element.
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
	 * Construct a new spliterator iterating the elements in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned spliterator.
	 * @return a new spliterator iterating the elements in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract ArraySpliterator spliterator(int index);

	/**
	 * Get a stream streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(Object[])
	 */
	public abstract Stream<E> stream();

	/**
	 * Construct a new array backed by the specified range of this array. The range starts at the given {@code beginIndex} and
	 * ends before the given {@code endIndex}.
	 *
	 * @param beginIndex the first index of the area at this array to be backing the constructed array.
	 * @param endIndex   one past the last index of the area at this array to be backing the constructed array.
	 * @return a new array backed by the specified range of this array.
	 * @throws IndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex > length}.
	 * @throws IllegalArgumentException  if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract Array<A, E> subArray(int beginIndex, int endIndex);

	/**
	 * Get an actual array copy of this array.
	 *
	 * @return an actual array copy of this array.
	 */
	public abstract A toArray();

	/**
	 * Get an actual array copy of this array.
	 *
	 * @param length the length of the constructed array.
	 * @return an actual array copy of this array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 */
	public abstract A toArray(int length);

	/**
	 * Get an actual array copy of this array that has the given {@code klass}.
	 *
	 * @param klass the type of the constructed array.
	 * @param <T>   the type of the returned array.
	 * @return an actual array copy of this array.
	 * @throws NullPointerException     if the given {@code klass} is null.
	 * @throws IllegalArgumentException if the given {@code klass} is not an array class.
	 */
	public abstract <T extends E> T[] toArray(Class<? super T[]> klass);

	/**
	 * Get an actual array copy of this array that has the given {@code klass}.
	 *
	 * @param length the length of the constructed array.
	 * @param klass  the type of the constructed array.
	 * @param <T>    the type of the returned array.
	 * @return an actual array copy of this array.
	 * @throws NullPointerException       if the given {@code klass} is null.
	 * @throws IllegalArgumentException   if the given {@code klass} is not an array class.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 */
	public abstract <T extends E> T[] toArray(int length, Class<? super T[]> klass);

	/**
	 * Construct a new iterator iterating the values in this array.
	 *
	 * @param <V> the type of the values.
	 * @return a new iterator iterating the values in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> ArrayValueIterator<V> valueIterator();

	/**
	 * Construct a new iterator iterating the values in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned iterator.
	 * @param <V>   the type of the values.
	 * @return a new iterator iterating the values in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> ArrayValueIterator<V> valueIterator(int index);

	/**
	 * Construct a new spliterator iterating the values in this array.
	 *
	 * @param <V> the type of the values.
	 * @return a new spliterator iterating the values in this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> ArrayValueSpliterator<V> valueSpliterator();

	/**
	 * Construct a new spliterator iterating the values in this array, starting from the given {@code index}.
	 *
	 * @param index the initial position of the returned spliterator.
	 * @param <V>   the type of the values.
	 * @return a new spliterator iterating the values in this array.
	 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
	 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	public abstract <V extends E> ArrayValueSpliterator<V> valueSpliterator(int index);

	/**
	 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
	 *
	 * @param <K> the type of the key in the entry.
	 * @param <V> the type of the value in the entry.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract class ArrayEntry<K extends E, V extends E> implements Map.Entry<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1793396182662638233L;

		/**
		 * The index of the key of this entry in the backing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int index;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in the enclosing array.
		 *
		 * @param index the index to where the key (followed by the value) will be in the constructed entry.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntry(int index) {
			Array.this.requireRange(index, index + 1);
			this.index = Array.this.beginIndex + index;
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
	public abstract class ArrayEntryIterator<K extends E, V extends E> implements Iterator<Map.Entry<K, V>> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntryIterator() {
		}

		/**
		 * Construct a new iterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntryIterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
		}

		@Override
		public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = Array.this.endIndex;

			int i = index - Array.this.beginIndex;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (; i < l; i += 2) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public boolean hasNext() {
			return this.index < Array.this.endIndex;
		}

		@Override
		public Map.Entry<K, V> next() {
			int index = this.index;

			if (index < Array.this.endIndex) {
				this.index += 2;

				int i = index - Array.this.beginIndex;
				return Array.this.entryAt(i);//trimmed index
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
	public abstract class ArrayEntrySet<K extends E, V extends E> implements Set<Map.Entry<K, V>>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7515045887948351373L;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new set backed by the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntrySet() {
		}

		@Override
		public void clear() {
			if (Array.this.endIndex - Array.this.beginIndex != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			int i = 0;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (; i < l; i += 2) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayEntryIterator<K, V> iterator() {
			return Array.this.entryIterator();
		}

		@Override
		public Stream<Map.Entry<K, V>> parallelStream() {
			return StreamSupport.stream(Array.this.entrySpliterator(), true);
		}

		@Override
		public boolean removeIf(Predicate<? super Map.Entry<K, V>> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			int i = 0;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (; i < l; i += 2) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i); //trimmed index

				if (predicate.test(entry))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//no match
			return false;
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex >>> 1;
		}

		@Override
		public ArrayEntrySpliterator<K, V> spliterator() {
			return Array.this.entrySpliterator();
		}

		@Override
		public Stream<Map.Entry<K, V>> stream() {
			return StreamSupport.stream(Array.this.entrySpliterator(), false);
		}

		@Override
		public Object[] toArray() {
			int length = Array.this.endIndex - Array.this.beginIndex >>> 1;
			Object[] product = new Object[length];

			int i = 0;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (int j = 0; i < l; i += 2, j++) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index

				product[j] = entry;
			}

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = Array.this.endIndex - Array.this.beginIndex >>> 1;
			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			//should trim the index for the entry creation
			int i = 0;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (int j = 0; i < l; i += 2, j++) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index

				product[j] = (T) entry;
			}

			return product;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean add(Map.Entry<K, V> entry);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean addAll(Collection<? extends Map.Entry<K, V>> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

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
	public abstract class ArrayEntrySpliterator<K extends E, V extends E> implements Spliterator<Map.Entry<K, V>> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED | java.util.Spliterator.SUBSIZED;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntrySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the entries in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayEntrySpliterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
		public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = Array.this.endIndex;

			int i = 0;
			int l = Array.this.endIndex - Array.this.beginIndex;
			for (; i < l; i += 2) {
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index

				consumer.accept(entry);
			}
		}

		@Override
		public Comparator<? super Map.Entry<K, V>> getComparator() {
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
		public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < Array.this.endIndex) {
				this.index += 2;

				int i = index - Array.this.beginIndex;
				ArrayEntry<K, V> entry = Array.this.entryAt(i);//trimmed index
				consumer.accept(entry);
				return true;
			}

			return false;
		}

		@Override
		public ArrayEntrySpliterator<K, V> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.subArray(index, midIndex)
						.entrySpliterator();
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
	public abstract class ArrayIterator implements Iterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayIterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
	public abstract class ArrayKeyIterator<K extends E> implements Iterator<K> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayKeyIterator() {
		}

		/**
		 * Construct a new iterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayKeyIterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
	public abstract class ArrayKeySet<K extends E> implements Set<K>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4627018232494058734L;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new set backed by the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayKeySet() {
		}

		@Override
		public void clear() {
			if (Array.this.endIndex - Array.this.beginIndex != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayKeyIterator<K> iterator() {
			return Array.this.keyIterator();
		}

		@Override
		public Stream<K> parallelStream() {
			return StreamSupport.stream(Array.this.keySpliterator(), true);
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex >>> 1;
		}

		@Override
		public ArrayKeySpliterator<K> spliterator() {
			return Array.this.keySpliterator();
		}

		@Override
		public Stream<K> stream() {
			return StreamSupport.stream(Array.this.keySpliterator(), false);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean add(K key);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean addAll(Collection<? extends K> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super K> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super K> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

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
	public abstract class ArrayKeySpliterator<K extends E> implements Spliterator<K> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayKeySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the keys in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayKeySpliterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
		public ArrayKeySpliterator<K> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.subArray(index, midIndex)
						.keySpliterator();
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
	public abstract class ArrayList implements List<E>, Serializable, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5890878610114060287L;

		/**
		 * Construct a new list backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayList() {
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
			if (Array.this.endIndex > Array.this.beginIndex)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayIterator iterator() {
			return Array.this.iterator();
		}

		@Override
		public ArrayListIterator listIterator() {
			return Array.this.listIterator();
		}

		@Override
		public ArrayListIterator listIterator(int index) {
			return Array.this.listIterator(index);
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(Array.this.spliterator(), true);
		}

		@Override
		public E remove(int index) {
			Array.this.requireIndex(index);
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			Array.this.sort(comparator);
		}

		@Override
		public Spliterator<E> spliterator() {
			return Array.this.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return StreamSupport.stream(Array.this.spliterator(), false);
		}

		@Override
		public ArrayList subList(int beginIndex, int endIndex) {
			return Array.this.subArray(beginIndex, endIndex)
					.asList();
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super E> consumer);

		@Override
		public abstract E get(int index);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int indexOf(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int lastIndexOf(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@Override
		public abstract void replaceAll(UnaryOperator<E> operator);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract E set(int index, E element);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract class ArrayListIterator implements ListIterator<E> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;
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
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayListIterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
			return this.index - Array.this.beginIndex;
		}

		@Override
		public int previousIndex() {
			return this.index - Array.this.beginIndex - 1;
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
	public abstract class ArrayMap<K extends E, V extends E> implements Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7692195336903798598L;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new map backed by the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayMap() {
		}

		@Override
		public void clear() {
			if (Array.this.endIndex - Array.this.beginIndex != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public ArrayEntrySet<K, V> entrySet() {
			return Array.this.entrySet();
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayKeySet<K> keySet() {
			return Array.this.asKeySet();
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex >>> 1;
		}

		@Override
		public ArrayValues<V> values() {
			return Array.this.asValues();
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
		public abstract void putAll(Map<? extends K, ? extends V> map);

		@Override
		public abstract V putIfAbsent(K key, V value);

		@Override
		public abstract V remove(Object key);

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
	public abstract class ArraySet implements Set<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2092169091443806884L;

		/**
		 * Construct a new set backed by the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArraySet() {
		}

		@Override
		public void clear() {
			if (Array.this.endIndex - Array.this.beginIndex != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayIterator iterator() {
			return Array.this.iterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(Array.this.spliterator(), true);
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex;
		}

		@Override
		public ArraySpliterator spliterator() {
			return Array.this.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return StreamSupport.stream(Array.this.spliterator(), false);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean add(E element);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean addAll(Collection<? extends E> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super E> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract class ArraySpliterator implements Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = Spliterator.SIZED | Spliterator.SUBSIZED;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArraySpliterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
		public ArraySpliterator trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.subArray(index, midIndex)
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
	public abstract class ArrayValueIterator<V extends E> implements Iterator<V> {
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayValueIterator() {
		}

		/**
		 * Construct a new iterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayValueIterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
	public abstract class ArrayValueSpliterator<V extends E> implements Spliterator<V> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected final int characteristics = java.util.Spliterator.SIZED | java.util.Spliterator.SUBSIZED;
		/**
		 * The next index.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		protected int index = Array.this.beginIndex;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayValueSpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the values in the enclosing array, starting from the given {@code index}.
		 *
		 * @param index the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @throws IllegalArgumentException  if {@code length % 2 != 0}
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayValueSpliterator(int index) {
			Array.this.requireRange(index);
			this.index += index;
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
		public ArrayValueSpliterator<V> trySplit() {
			int index = this.index;
			int midIndex = index + Array.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return Array.this.subArray(index, midIndex)
						.valueSpliterator();
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
	public abstract class ArrayValues<V extends E> implements Collection<V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7634421013079755116L;

		{
			Array.this.requireEven();
		}

		/**
		 * Construct a new collection backed by the values in the enclosing array.
		 *
		 * @throws IllegalArgumentException if {@code length % 2 != 0}.
		 * @since 0.1.5 ~2020.08.06
		 */
		protected ArrayValues() {
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
			if (Array.this.endIndex - Array.this.beginIndex != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return Array.this.endIndex <= Array.this.beginIndex;
		}

		@Override
		public ArrayValueIterator<V> iterator() {
			return Array.this.valueIterator();
		}

		@Override
		public Stream<V> parallelStream() {
			return StreamSupport.stream(Array.this.valueSpliterator(), true);
		}

		@Override
		public int size() {
			return Array.this.endIndex - Array.this.beginIndex >>> 1;
		}

		@Override
		public ArrayValueSpliterator<V> spliterator() {
			return Array.this.valueSpliterator();
		}

		@Override
		public Stream<V> stream() {
			return StreamSupport.stream(Array.this.valueSpliterator(), false);
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super V> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super V> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}
}
