package cufy.util;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An object controlling an array.
 *
 * @param <A> the type of the array.
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.03
 */
public interface Array<A, E> extends Iterable<E> {
	@Override
	int hashCode();

	@Override
	String toString();

	/**
	 * Return the array backing this array. If this array has a restricted area on the backing
	 * array. Then a null will be returned.
	 *
	 * @return the array backing this array. Or null if this array has a restricted area on the
	 * 		backing array.
	 * @since 0.1.5 ~2020.08.11
	 */
	A array();

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
	void arraycopy(A array, int pos, int length);

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 * <p>
	 * If the index found to be greater than {@link Integer#MAX_VALUE}, Then {@link
	 * Integer#MAX_VALUE} will be returned. Or if the element was not found but it could be inserted
	 * in a negative integer less {@link Integer#MIN_VALUE}, then {@link Integer#MIN_VALUE} will be
	 * returned.
	 *
	 * @param object the value to be searched for.
	 * @return index of the search object, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(Object[], Object)
	 * @since 0.1.5 ~2020.08.11
	 */
	int binarySearch(Object object);

	/**
	 * Searches the this array for the specified value using the binary search algorithm. This array
	 * must be sorted prior to making this call. If it is not sorted, the results are undefined. If
	 * the array contains multiple elements with the specified value, there is no guarantee which
	 * one will be found.
	 * <p>
	 * If the index found to be greater than {@link Integer#MAX_VALUE}, Then {@link
	 * Integer#MAX_VALUE} will be returned. Or if the element was not found but it could be inserted
	 * in a negative integer less {@link Integer#MIN_VALUE}, then {@link Integer#MIN_VALUE} will be
	 * returned.
	 *
	 * @param element    the value to be searched for.
	 * @param comparator the comparator by which the array is ordered. A null value indicates that
	 *                   the elements' natural ordering should be used.
	 * @return index of the search element, if it is contained in the array; otherwise,
	 * 		(-(<i>insertion point</i>) - 1).
	 * @see java.util.Arrays#binarySearch(Object[], Object, Comparator)
	 * @since 0.1.5 ~2020.08.11
	 */
	int binarySearch(E element, Comparator<? super E> comparator);

	/**
	 * Get a copy of the array backing this array.
	 *
	 * @return a copy of the array backing this array.
	 * @since 0.1.5 ~2020.08.11
	 */
	A copy();

	/**
	 * Get a copy of the array backing this array.
	 *
	 * @param length the length of the constructed array.
	 * @return a copy of the array backing this array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @since 0.1.5 ~2020.08.11
	 */
	A copy(int length);

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
	<T> T copy(Class<? extends T> klass);

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
	<T> T copy(Class<? extends T> klass, int length);

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
	void copy(Object array, int pos, int length);

	/**
	 * Assign the given {@code element} to each element of this array.
	 *
	 * @param element the element to fill this array with.
	 * @throws ArrayStoreException if the given {@code element} can not be stored in this {@code
	 *                             array}.
	 * @see java.util.Arrays#fill(Object[], Object)
	 * @since 0.1.5 ~2020.08.11
	 */
	void fill(E element);

	/**
	 * Get the element at the given {@code thumb} in this array.
	 *
	 * @param thumb the thumb to get the element from.
	 * @return the element at the given {@code thumb} in this array.
	 * @throws ArrayIndexOutOfBoundsException if {@code thumb < 0} or {@code thumb >= length}.
	 * @see java.lang.reflect.Array#get(Object, int)
	 * @since 0.1.5 ~2020.08.06
	 */
	E get(int thumb);

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
	void hardcopy(Object[] array, int pos, int length);

	/**
	 * Get the length of this array. If the length is greater than {@link Integer#MAX_VALUE} then
	 * {@link Integer#MAX_VALUE} will be returned.
	 *
	 * @return the length of this array.
	 * @see java.lang.reflect.Array#getLength(Object)
	 * @since 0.1.5 ~2020.08.06
	 */
	int length();

	/**
	 * Construct a new list backed by this array.
	 *
	 * @return a new list backed by this array.
	 * @see java.util.Arrays#asList(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	List<E> list();

	/**
	 * Get a list iterator iterating over the elements of this array.
	 *
	 * @return a new list iterator for this array.
	 */
	ListIterator<E> listIterator();

	/**
	 * Construct a new map backed by this array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a new map backed by this array.
	 * @throws IllegalArgumentException if {@code length % 2 != 0}.
	 * @since 0.1.5 ~2020.08.06
	 */
	<K extends E, V extends E> Map<K, V> map();

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
	void parallelPrefix(BinaryOperator<E> operator);

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
	void parallelSetAll(IntFunction<? extends E> function);

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
	void parallelSort();

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
	void parallelSort(Comparator<? super E> comparator);

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
	void set(int thumb, E element);

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
	void setAll(IntFunction<? extends E> function);

	/**
	 * Sort this array.
	 *
	 * @see java.util.Arrays#sort(Object[])
	 * @since 0.1.5 ~2020.08.06
	 */
	void sort();

	/**
	 * Sort this array using the given {@code comparator}.
	 *
	 * @param comparator the comparator to be used.
	 * @see java.util.Arrays#sort(Object[], Comparator)
	 * @since 0.1.5 ~2020.08.06
	 */
	void sort(Comparator<? super E> comparator);

	/**
	 * Get a stream streaming the elements in this array.
	 *
	 * @return a stream streaming the elements in this array.
	 * @see java.util.Arrays#stream(Object[])
	 * @since 0.1.5 ~2020.08.11
	 */
	Stream<E> stream();

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
	Array<A, E> sub(int beginThumb, int endThumb);

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	interface ArrayIterator<E> extends Iterator<E> {
		@Override
		default void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	interface ArrayList<E> extends List<E> {
		@Override
		default boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		default void add(int thumb, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		default boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		default boolean addAll(int thumb, Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		default void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		default E remove(int thumb) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		default boolean remove(Object object) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		default boolean removeAll(Collection<?> collection) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		default boolean removeIf(Predicate<? super E> predicate) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		default boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	interface ArrayListIterator<E> extends ListIterator<E> {
		@Override
		default void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		default void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	interface ArrayMap<E, K extends E, V extends E> extends Map<K, V> {
		@Override
		default void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		default V remove(Object key) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		default boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException("remove");
		}

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayEntry<E, K extends E, V extends E> extends Entry<K, V> {
		}

		/**
		 * An iterator iterating the entries in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayEntryIterator<E, K extends E, V extends E> extends Iterator<Entry<K, V>> {
			@Override
			default void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayEntrySet<E, K extends E, V extends E> extends Set<Entry<K, V>> {
			@Override
			default boolean add(Entry<K, V> entry) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			default boolean addAll(Collection<? extends Entry<K, V>> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			default void clear() {
				throw new UnsupportedOperationException("clear");
			}

			@Override
			default boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			default boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			default boolean removeIf(Predicate<? super Entry<K, V>> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			default boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}
		}

		/**
		 * A spliterator iterating the entries in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		interface ArrayEntrySpliterator<E, K extends E, V extends E> extends Spliterator<Entry<K, V>> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			int CHARACTERISTICS = Spliterator.SIZED |
								  Spliterator.SUBSIZED |
								  Spliterator.ORDERED |
								  Spliterator.IMMUTABLE;

			@Override
			default int characteristics() {
				return ArrayEntrySpliterator.CHARACTERISTICS;
			}

			@Override
			default Comparator<? super Entry<K, V>> getComparator() {
				throw new IllegalStateException();
			}
		}

		/**
		 * An iterator iterating the keys in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayKeyIterator<E, K extends E> extends Iterator<K> {
			@Override
			default void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayKeySet<E, K extends E> extends Set<K> {
			@Override
			default boolean add(K key) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			default boolean addAll(Collection<? extends K> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			default void clear() {
				throw new UnsupportedOperationException("clear");
			}

			@Override
			default boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			default boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			default boolean removeIf(Predicate<? super K> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			default boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}
		}

		/**
		 * A spliterator iterating the keys in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <K> the type of the keys.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		interface ArrayKeySpliterator<E, K extends E> extends Spliterator<K> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			int CHARACTERISTICS = Spliterator.SIZED |
								  Spliterator.SUBSIZED |
								  Spliterator.ORDERED |
								  Spliterator.IMMUTABLE;

			@Override
			default int characteristics() {
				return ArrayKeySpliterator.CHARACTERISTICS;
			}

			@Override
			default Comparator<? super K> getComparator() {
				throw new IllegalStateException();
			}
		}

		/**
		 * An iterator iterating the values in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayValueIterator<E, V extends E> extends Iterator<V> {
			@Override
			default void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * A spliterator iterating the values in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		interface ArrayValueSpliterator<E, V extends E> extends Spliterator<V> {
			/**
			 * The characteristics of this spliterator.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			int CHARACTERISTICS = Spliterator.SIZED |
								  Spliterator.SUBSIZED |
								  Spliterator.ORDERED |
								  Spliterator.IMMUTABLE;

			@Override
			default int characteristics() {
				return ArrayValueSpliterator.CHARACTERISTICS;
			}

			@Override
			default Comparator<? super V> getComparator() {
				throw new IllegalStateException();
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @param <E> the type of the elements.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		interface ArrayValues<E, V extends E> extends Collection<V> {
			@Override
			default boolean add(V value) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			default boolean addAll(Collection<? extends V> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			default void clear() {
				throw new UnsupportedOperationException("clear");
			}

			@Override
			default boolean remove(Object object) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			default boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			default boolean removeIf(Predicate<? super V> predicate) {
				throw new UnsupportedOperationException("removeIf");
			}

			@Override
			default boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	interface ArraySpliterator<E> extends Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		int CHARACTERISTICS = Spliterator.SIZED |
							  Spliterator.SUBSIZED |
							  Spliterator.ORDERED |
							  Spliterator.IMMUTABLE;

		@Override
		default int characteristics() {
			return ArraySpliterator.CHARACTERISTICS;
		}

		@Override
		default Comparator<? super E> getComparator() {
			throw new IllegalStateException();
		}
	}
}
