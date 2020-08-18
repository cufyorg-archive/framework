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

import cufy.util.array.Array;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utils all about maps.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.b ~2019.06.11
 */
@SuppressWarnings({"JavaDoc", "ClassWithTooManyFields"})
public class CollectionUtil {
	//todo: rename to `Collections` ;P

	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptyCollection EMPTY_COLLECTION = new EmptyCollection();
	public static final EmptyEnumeration EMPTY_ENUMERATION = new EmptyEnumeration();
	public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptyList EMPTY_LIST = new EmptyList();
	public static final EmptyListIterator EMPTY_LIST_ITERATOR = new EmptyListIterator();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptyMap EMPTY_MAP = new EmptyMap();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptyNavigableMap EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptyNavigableSet EMPTY_NAVIGABLE_SET = new EmptyNavigableSet();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySet EMPTY_SET = new EmptySet();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySortedMap EMPTY_SORTED_MAP = new EmptySortedMap();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySortedSet EMPTY_SORTED_SET = new EmptyNavigableSet();

	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	protected CollectionUtil() {
		throw new AssertionError("No instance for you!");
	}

	public static <T> Set<T> UnmodifiableSet(Set<? extends T> set) {
		return new UnmodifiableSet(set);
	}

	public static <T> boolean addAll(Collection<? super T> collection, T... elements) {
		boolean modified = false;
		for (T element : elements)
			modified |= collection.add(element);
		return modified;
	}

	public static <T> LIFOQueue<T> asLifoQueue(Deque<T> deque) {
		return new LIFOQueue(deque);
	}

	public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key);
	}

	public static <T> int binarySearch(List<T> list, T key, Comparator<? super T> comparator) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key, comparator);
	}

	public static <T> CheckedCollection<T> checkedCollection(Collection<T> collection, Class<T> type) {
		return new CheckedCollection(collection, type);
	}

	public static <T> CheckedList<T> checkedList(List<T> list, Class<T> type) {
		return new CheckedList(list, type);
	}

	public static <K, V> CheckedMap<K, V> checkedMap(Map<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedMap(map, keyType, valueType);
	}

	public static <K, V> CheckedNavigableMap<K, V> checkedNavigableMap(NavigableMap<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedNavigableMap(map, keyType, valueType);
	}

	public static <T> CheckedNavigableSet<T> checkedNavigableSet(NavigableSet<T> set, Class<T> type) {
		return new CheckedNavigableSet(set, type);
	}

	public static <T> CheckedQueue<T> checkedQueue(Queue<T> queue, Class<T> type) {
		return new CheckedQueue(queue, type);
	}

	public static <T> CheckedSet<T> checkedSet(Set<T> set, Class<T> type) {
		return new CheckedSet(set, type);
	}

	public static <K, V> CheckedSortedMap<K, V> checkedSortedMap(SortedMap<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedSortedMap(map, keyType, valueType);
	}

	public static <T> CheckedSortedSet<T> checkedSortedSet(SortedSet<T> set, Class<T> type) {
		return new CheckedSortedSet(set, type);
	}

	/**
	 * Get an iterator combined from the given iterators.
	 *
	 * @param <T>       the type of elements provided by the returned iterator.
	 * @param iterators to combine.
	 * @return an iterator combined from the given iterators.
	 * @throws NullPointerException if the given {@code iterators} or any of its elements is null.
	 */
	@Deprecated
	public static <T> CombinedIterator<T> combine(Iterator<? extends T>... iterators) {
		return new CombinedIterator(iterators);
	}

	@Deprecated
	public static <T> CombinedList<T> combine(List<T>... lists) {
		return new CombinedList(lists);
	}

	@Deprecated
	public static <T> CombinedCollection<T> combine(Collection<T>... collections) {
		return new CombinedCollection(collections);
	}

	public static <T> void copy(List<? super T> dest, List<? extends T> src) {
		//can not access java.util.Collections.COPY_THRESHOLD
		java.util.Collections.copy(dest, src);
	}

	public static <T, U> boolean disjoint(Collection<T> collection, Collection<U> other) {
		//an operation based on benchmarks is always better!
		return java.util.Collections.disjoint(collection, other);
	}

	public static <T> EmptyEnumeration<T> emptyEnumeration() {
		return new EmptyEnumeration();
	}

	public static <T> EmptyIterator<T> emptyIterator() {
		return new EmptyIterator();
	}

	public static <T> EmptyList<T> emptyList() {
		return new EmptyList();
	}

	public static <T> EmptyListIterator<T> emptyListIterator() {
		return new EmptyListIterator();
	}

	public static <K, V> EmptyMap<K, V> emptyMap() {
		return new EmptyMap();
	}

	public static <K, V> EmptyNavigableMap<K, V> emptyNavigableMap() {
		return new EmptyNavigableMap();
	}

	public static <T> NavigableSet<T> emptyNavigableSet() {
		return new EmptyNavigableSet();
	}

	public static <T> EmptySet<T> emptySet() {
		return new EmptySet();
	}

	public static <K, V> SortedMap<K, V> emptySortedMap() {
		return new EmptySortedMap();
	}

	public static <T> SortedSet<T> emptySortedSet() {
		return new EmptySortedSet();
	}

	public static <T> EnumerationFromIterator<T> enumeration(Collection<T> collection) {
		return new EnumerationFromIterator(collection.iterator());
	}

	public static <T> void fill(List<? super T> list, T element) {
		//can not access java.util.Collections.FILL_THRESHOLD
		java.util.Collections.fill(list, element);
	}

	@Deprecated
	public static <T> FilteredCollection<T> filteredCollection(Collection<T> collection, Predicate<T> predicate) {
		return new FilteredCollection(collection, predicate);
	}

	@Deprecated
	public static <T> FilteredSet<T> filteredSet(Set<T> set, Predicate<T> predicate) {
		return new FilteredSet(set, predicate);
	}

	public static <T, U> int frequency(Collection<T> collection, U element) {
		int frequency = 0;

		if (element == null) {
			for (T e : collection)
				if (e == null)
					frequency++;
		} else
			for (T e : collection)
				if (element.equals(e))
					frequency++;

		return frequency;
	}

	/**
	 * Get a map that do read and write to the given {@code list}. The map will use the indexes as
	 * the keys of the elements on the given list.
	 *
	 * @param list the list that the returned map is reading-from/writing-to.
	 * @param <T>  the type of values on the returned map.
	 * @return a map that do read and write to the given {@code list}.
	 * @throws NullPointerException if the given {@code list} is null.
	 */
	@Deprecated
	public static <T> IndexMapFromList indexMapFromList(List<T> list) {
		return new IndexMapFromList(list);
	}

	public static <T, U> int indexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.indexOfSubList(source, target);
	}

	public static <T, U> int lastIndexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.lastIndexOfSubList(source, target);
	}

	public static <T> ArrayList<T> list(Enumeration<T> enumeration) {
		ArrayList list = new ArrayList();

		while (enumeration.hasMoreElements())
			list.add(enumeration.nextElement());

		return list;
	}

	/**
	 * Get a list that do read and write to the given {@code map}. The list will use the positive
	 * integer keys only.
	 *
	 * @param map the map that the returned list is reading-from/writing-to.
	 * @param <T> the type of the elements in the returned list.
	 * @return a list that do read and write to the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 */
	@Deprecated
	public static <T> ListFromIndexMap<T> listFromIndexMap(Map<Integer, T> map) {
		return new ListFromIndexMap(map);
	}

	public static <T extends Comparable<? super T>> T max(Collection<? extends T> collection) {
		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(max) > 0)
				max = element;
		}

		return max;
	}

	public static <T> T max(Collection<? extends T> collection, Comparator<? super T> comparator) {
		if (comparator == null)
			return (T) CollectionUtil.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, max) > 0)
				max = element;
		}

		return max;
	}

	public static <T extends Comparable<? super T>> T min(Collection<? extends T> collection) {
		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(min) < 0)
				min = element;
		}

		return min;
	}

	public static <T> T min(Collection<? extends T> collection, Comparator<? super T> comparator) {
		if (comparator == null)
			return (T) CollectionUtil.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, min) < 0)
				min = element;
		}

		return min;
	}

	public static <T> NCopiesList<T> nCopies(int size, T element) {
		return new NCopiesList(size, element);
	}

	public static <T> SetFromMap<T> newSetFromMap(Map<T, Boolean> map) {
		return new SetFromMap(map);
	}

	public static <T> boolean replaceAll(List<T> list, T oldValue, T newValue) {
		//can not access java.util.Collections.REPLACEALL_THRESHOLD
		return java.util.Collections.replaceAll(list, oldValue, newValue);
	}

	public static <T> void reverse(List<T> list) {
		//can not access java.util.Collections.REVERSE_THRESHOLD
		java.util.Collections.reverse(list);
	}

	public static <T> Comparator<T> reverseOrder() {
		return (Comparator) Comparator.reverseOrder();
	}

	public static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
		return comparator == null ?
			   (Comparator) Comparator.reverseOrder() :
			   comparator instanceof ReverseComparator ?
			   ((ReverseComparator) comparator).reversed() :
			   new ReverseComparator(comparator);
	}

	public static <T> void rotate(List<T> list, int distance) {
		//can not access java.util.Collections.ROTATE_THRESHOLD
		java.util.Collections.rotate(list, distance);
	}

	public static <T> void shuffle(List<T> list) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list);
	}

	public static <T> void shuffle(List<T> list, Random random) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list, random);
	}

	public static <T> SingletonSet<T> singleton(T element) {
		return new SingletonSet(element);
	}

	public static <T> SingletonIterator<T> singletonIterator(T element) {
		return new SingletonIterator(element);
	}

	public static <T> SingletonList<T> singletonList(T element) {
		return new SingletonList(element);
	}

	public static <K, V> SingletonMap<K, V> singletonMap(K key, V value) {
		return new SingletonMap(key, value);
	}

	public static <T> SingletonSpliterator<T> singletonSpliterator(T element) {
		return new SingletonSpliterator(element);
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		//as in java.util.Collections.sort(List)
		list.sort(null);
	}

	public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
		//as in java.util.Collections.sort(List, Comparator)
		list.sort(comparator);
	}

	public static <T> void swap(List<T> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	public static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> collection) {
		return new SynchronizedCollection(collection);
	}

	public static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> collection, Object mutex) {
		return new SynchronizedCollection(collection, mutex);
	}

	public static <T> SynchronizedList<T> synchronizedList(List<T> list) {
		return list instanceof RandomAccess ?
			   new SynchronizedRandomAccessList(list) :
			   new SynchronizedList(list);
	}

	public static <T> SynchronizedList<T> synchronizedList(List<T> list, Object mutex) {
		return list instanceof RandomAccess ?
			   new SynchronizedRandomAccessList(list, mutex) :
			   new SynchronizedList(list, mutex);
	}

	public static <K, V> SynchronizedMap<K, V> synchronizedMap(Map<K, V> map) {
		return new SynchronizedMap(map);
	}

	public static <K, V> SynchronizedMap<K, V> synchronizedMap(Map<K, V> map, Object mutex) {
		return new SynchronizedMap(map, mutex);
	}

	public static <K, V> SynchronizedNavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> map) {
		return new SynchronizedNavigableMap(map);
	}

	public static <K, V> SynchronizedNavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> map, Object mutex) {
		return new SynchronizedNavigableMap(map, mutex);
	}

	public static <T> SynchronizedNavigableSet<T> synchronizedNavigableSet(NavigableSet<T> set) {
		return new SynchronizedNavigableSet(set);
	}

	public static <T> SynchronizedNavigableSet<T> synchronizedNavigableSet(NavigableSet<T> set, Object mutex) {
		return new SynchronizedNavigableSet(set, mutex);
	}

	public static <T> SynchronizedSet<T> synchronizedSet(Set<T> set) {
		return new SynchronizedSet(set);
	}

	public static <T> SynchronizedSet<T> synchronizedSet(Set<T> set, Object mutex) {
		return new SynchronizedSet(set, mutex);
	}

	public static <K, V> SynchronizedSortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> map) {
		return new SynchronizedSortedMap(map);
	}

	public static <K, V> SynchronizedSortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> map, Object mutex) {
		return new SynchronizedSortedMap(map, mutex);
	}

	public static <T> SynchronizedSortedSet<T> synchronizedSortedSet(SortedSet<T> set) {
		return new SynchronizedSortedSet(set);
	}

	public static <T> SynchronizedSortedSet<T> synchronizedSortedSet(SortedSet<T> set, Object mutex) {
		return new SynchronizedSortedSet(set, mutex);
	}

	public static <T> UnmodifiableCollection<T> unmodifiableCollection(Collection<? extends T> collection) {
		return new UnmodifiableCollection(collection);
	}

	public static <T> UnmodifiableList<T> unmodifiableList(List<? extends T> list) {
		return new UnmodifiableList(list);
	}

	public static <K, V> UnmodifiableMap<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
		return new UnmodifiableMap(map);
	}

	public static <K, V> UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> map) {
		return new UnmodifiableNavigableMap(map);
	}

	public static <T> UnmodifiableNavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> set) {
		return new UnmodifiableNavigableSet(set);
	}

	public static <K, V> UnmodifiableSortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> map) {
		return new UnmodifiableSortedMap(map);
	}

	public static <T> UnmodifiableSortedSet<T> unmodifiableSortedSet(SortedSet<T> set) {
		return new UnmodifiableSortedSet(set);
	}

	/**
	 * A collection from combining multiple collections.
	 *
	 * @param <E> the type of the elements in the collection.
	 */
	@Deprecated
	public static final class CombinedCollection<E> extends AbstractCollection<E> {
		/**
		 * The collections combined in this collection.
		 */
		private final Collection<E>[] collections;

		/**
		 * Construct a new collection from combining the given {@code collections}.
		 *
		 * @param collections to be combined in the constructed collection.
		 * @throws NullPointerException if the given {@code collections} is null.
		 */
		private CombinedCollection(Collection<E>... collections) {
			Objects.requireNonNull(collections, "collections");
			this.collections = collections;
		}

		@Override
		public boolean add(E element) {
			for (int i = this.collections.length - 1; i >= 0; i--)
				if (this.collections[i] != null)
					try {
						return this.collections[i].add(element);
					} catch (UnsupportedOperationException ignored) {
					}

			//all nulls
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof CombinedCollection &&
				   ((Collection) object).size() == this.size() &&
				   ((Collection) object).containsAll((Collection) object);
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (Collection<E> collection : this.collections)
				if (collection != null)
					for (E element : collection)
						hashCode += Objects.hashCode(element);

			return hashCode;
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public int size() {
			int size = 0;

			for (Collection<E> collection : this.collections)
				if (collection != null)
					size += collection.size();

			return size;
		}

		@Override
		public String toString() {
			java.util.Iterator<Collection<E>> collections = Arrays.iterator(this.collections);

			if (collections.hasNext()) {
				StringBuilder builder = new StringBuilder();

				while (true) {
					Collection<E> collection = collections.next();

					if (collection == null)
						builder.append("null");
					else {
						java.util.Iterator<E> elements = collection.iterator();

						if (elements.hasNext()) {
							builder.append("[");

							while (true) {
								builder.append(elements.next());

								if (elements.hasNext()) {
									builder.append(", ");
									continue;
								}

								break;
							}

							builder.append("]");
						} else
							builder.append("[]");
					}

					if (collections.hasNext()) {
						builder.append(" ");
						continue;
					}

					break;
				}

				return builder.toString();
			}

			return "";
		}

		/**
		 * The iterator backing {@link CombinedCollection}.
		 */
		private final class Iterator implements java.util.Iterator<E> {
			/**
			 * The iterator backing this iterator.
			 */
			private final java.util.Iterator<Collection<E>> collections = Arrays.iterator(CombinedCollection.this.collections);
			/**
			 * The iterator to be used on the next {@link #next()} call.
			 */
			private java.util.Iterator<E> iterator;
			/**
			 * The last iterator used by the last {@link #next()} call.
			 */
			private java.util.Iterator<E> lastIterator;

			@Override
			public boolean hasNext() {
				while (true) {
					if (this.iterator != null && this.iterator.hasNext())
						return true;
					if (this.collections.hasNext()) {
						Collection<E> collection = this.collections.next();
						this.iterator = collection == null ? null : collection.iterator();
					} else
						return false;
				}
			}

			@Override
			public E next() {
				while (true) {
					if (this.iterator != null && this.iterator.hasNext()) {
						this.lastIterator = this.iterator;
						return this.iterator.next();
					}

					Collection<E> collection = this.collections.next();
					this.iterator = collection == null ? null : collection.iterator();
				}
			}

			@Override
			public void remove() {
				this.lastIterator.remove();
			}
		}
	}

	/**
	 * An iterator iterating the elements of multiple iterators.
	 *
	 * @param <E> the type of elements returned by this iterator
	 */
	@Deprecated
	public static final class CombinedIterator<E> implements Iterator<E> {
		/**
		 * The iterators this iterator is iterating its elements.
		 */
		private final Iterator<E>[] iterators;

		/**
		 * The index of the current iterator.
		 */
		private int cursor;

		/**
		 * Construct a new iterator iterating the elements of the given {@code iterators}.
		 *
		 * @param iterators the iterators the constructed iterator is iterating its elements.
		 * @throws NullPointerException if the given {@code iterators} is null.
		 */
		private CombinedIterator(Iterator<E>... iterators) {
			Objects.requireNonNull(iterators, "iterators");
			this.iterators = iterators;
		}

		@Override
		public boolean hasNext() {
			for (int i = this.cursor; i < this.iterators.length; i++) {
				Iterator<E> iterator = this.iterators[i];

				if (iterator != null && iterator.hasNext())
					return true;
			}

			return false;
		}

		@Override
		public E next() {
			for (; this.cursor < this.iterators.length; this.cursor++) {
				Iterator<E> iterator = this.iterators[this.cursor];

				if (iterator != null && iterator.hasNext())
					return iterator.next();
			}

			//allow next calls to remove(). (if the exception got caught)
			this.cursor--;
			throw new NoSuchElementException("No more elements");
		}

		@Override
		public void remove() {
			this.iterators[this.cursor].remove();
		}
	}

	/**
	 * A list from combining multiple lists.
	 *
	 * @param <E> the type of the elements in the list.
	 */
	@Deprecated
	public static final class CombinedList<E> extends AbstractList<E> {
		/**
		 * The lists combined in this list.
		 */
		private final List<E>[] lists;

		/**
		 * Construct a new list from combining the given {@code lists}.
		 *
		 * @param lists to be combined in the constructed list.
		 * @throws NullPointerException if the given {@code lists} is null.
		 */
		private CombinedList(List<E>... lists) {
			Objects.requireNonNull(lists, "lists");
			this.lists = lists;
		}

		@Override
		public void add(int index, E element) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index: " + index);

			int i = index;
			for (List<E> list : this.lists)
				if (list != null) {
					int size = list.size();

					if (i < size)
						list.add(i, element);

					i -= size;
				}

			throw new IndexOutOfBoundsException("index: " + index);
		}

		@Override
		public boolean add(E e) {
			for (int i = this.lists.length - 1; i >= 0; i--)
				if (this.lists[i] != null)
					return this.lists[i].add(e);

			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof List) {
				Iterator oi = ((Iterable) object).iterator();
				Iterator ti = this.iterator();

				while (true) {
					if (oi.hasNext()) {
						if (ti.hasNext() &&
							Objects.equals(oi.next(), ti.next()))
							continue;

						return false;
					}

					return !ti.hasNext();
				}
			}

			return false;
		}

		@Override
		public E get(int index) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index: " + index);

			int i = index;
			for (List<E> list : this.lists)
				if (list != null) {
					int size = list.size();

					if (i < size)
						//flow ends in this list
						return list.get(i);

					//remove flow from the current list
					i -= size;
				}

			throw new IndexOutOfBoundsException("index: " + index);
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (List<E> list : this.lists)
				if (list != null)
					for (E element : list)
						hashCode = 31 * hashCode + Objects.hashCode(element);

			return hashCode;
		}

		@Override
		public E set(int index, E element) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index: " + index);

			int i = index;
			for (List<E> list : this.lists)
				if (list != null) {
					int size = list.size();

					if (i < size)
						//flow ends in this list
						return list.set(i, element);

					//remove flow from the current list
					i -= size;
				}

			throw new IndexOutOfBoundsException("index: " + index);
		}

		@Override
		public int size() {
			int size = 0;

			for (List<E> list : this.lists)
				if (list != null)
					size += list.size();

			return size;
		}

		@Override
		public String toString() {
			java.util.Iterator<List<E>> lists = Arrays.iterator(this.lists);

			if (lists.hasNext()) {
				StringBuilder builder = new StringBuilder();

				while (true) {
					List<E> list = lists.next();

					if (list == null)
						builder.append("null");
					else {
						java.util.Iterator<E> elements = list.iterator();

						if (elements.hasNext()) {
							builder.append("[");

							while (true) {
								builder.append(elements.next());

								if (elements.hasNext()) {
									builder.append(", ");
									continue;
								}

								break;
							}

							builder.append("]");
						} else
							builder.append("[]");
					}

					if (lists.hasNext()) {
						builder.append(" ");
						continue;
					}

					break;
				}

				return builder.toString();
			}

			return "";
		}
	}

	/**
	 * A set containing filtered elements from another set.
	 *
	 * @param <E> the type of the elements in the set.
	 */
	@Deprecated
	public static final class FilteredSet<E> extends FilteredCollection<E> implements Set<E> {
		/**
		 * Construct a new set containing filtered elements form the given {@code set}.
		 *
		 * @param set    to be filtered.
		 * @param filter the filter to be applied.
		 * @throws NullPointerException if the given {@code set} or {@code filter} is null.
		 */
		private FilteredSet(Set<E> set, Predicate<E> filter) {
			super(Objects.requireNonNull(set, "set"), filter);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Set &&
				   ((Collection) object).size() == this.size() &&
				   ((Collection) object).containsAll(this);
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (E element : this)
				hashCode += Objects.hashCode(element);

			return hashCode;
		}

		@Override
		public String toString() {
			Iterator<E> iterator = this.iterator();

			if (iterator.hasNext()) {
				StringBuilder builder = new StringBuilder("[");

				while (true) {
					builder.append(iterator.next());

					if (iterator.hasNext()) {
						builder.append(", ");
						continue;
					}

					break;
				}

				return builder.append("]").toString();
			}

			return "[]";
		}
	}

	/**
	 * A map backed by a list.
	 *
	 * @param <V> the type of mapped values
	 */
	@Deprecated
	public static final class IndexMapFromList<V> extends AbstractMap<Integer, V> {
		/**
		 * The entrySet of this map.
		 */
		private final ListMapEntrySet entrySet = new ListMapEntrySet();
		/**
		 * The backing list of this map.
		 */
		private final List<V> list;

		/**
		 * Construct a new map that is backed by the given {@code list}.
		 *
		 * @param list the list backing the constructed map.
		 * @throws NullPointerException if the given {@code list} is null.
		 */
		private IndexMapFromList(List<V> list) {
			Objects.requireNonNull(list, "list");
			this.list = list;
		}

		@Override
		public Set<Map.Entry<Integer, V>> entrySet() {
			return (Set) this.entrySet;
		}

		@Override
		public V put(Integer key, V value) {
			if (key < 0)
				throw new UnsupportedOperationException("can't store the key: " + key);

			int index = key;
			int size = this.list.size();

			if (size > index)
				return this.list.set(index, value);
			else {
				this.list.addAll(java.util.Collections.nCopies(index - size, null));
				this.list.add(value);
				return null;
			}
		}

		/**
		 * An entry for maps that is backed by a list.
		 */
		public final class ListMapEntry implements Map.Entry<Integer, V> {
			/**
			 * The index of where this entry is reading from in the backing list.
			 */
			private final int index;
			/**
			 * Reassignment for the {@link IndexMapFromList#list} to be visible across instances.
			 */
			private final List<V> list = IndexMapFromList.this.list;
			/**
			 * The size of the list at the time creating this entry.
			 */
			private final int mod;

			/**
			 * Construct a new list-map-entry that reads from the given {@code index}.
			 *
			 * @param mod   the size of the list at the time creating this entry.
			 * @param index the index the constructing is reading at at the backed list.
			 * @throws IllegalArgumentException if the given {@code index} is negative, or if the
			 *                                  given {@code mod} is less or equals to the given
			 *                                  {@code index}.
			 */
			private ListMapEntry(int mod, int index) {
				if (index < 0)
					throw new IllegalArgumentException("Negative index " + index);
				if (mod < index)
					throw new IllegalArgumentException("mod is less that index " + mod);
				this.mod = mod;
				this.index = index;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == this)
					//quick match
					return true;
				if (obj instanceof IndexMapFromList.ListMapEntry)
					//index match
					return ((ListMapEntry) obj).list == this.list &&
						   ((ListMapEntry) obj).index == this.index;
				//entry match
				return obj instanceof Map.Entry &&
					   Objects.equals(((Map.Entry) obj).getKey(), this.getKey()) &&
					   Objects.equals(((Map.Entry) obj).getValue(), this.getValue());
			}

			@Override
			public Integer getKey() {
				return this.index;
			}

			@Override
			public V getValue() {
				this.assertNoModification();
				return this.list.get(this.index);
			}

			@Override
			public int hashCode() {
				return Objects.hashCode(this.getKey()) ^
					   Objects.hashCode(this.getValue());
			}

			@Override
			public V setValue(V v) {
				this.assertNoModification();
				return this.list.set(this.index, v);
			}

			@Override
			public String toString() {
				return this.getKey() + "=" + this.getValue();
			}

			/**
			 * Check if the list has modified in the interval of creating this entry and the time
			 * invoking this method.
			 *
			 * @throws ConcurrentModificationException if the list has been modified since creating
			 *                                         this entry.
			 */
			private void assertNoModification() {
				int size = IndexMapFromList.this.list.size();
				if (this.mod != size)
					throw new ConcurrentModificationException(
							"expected: " + this.mod + ", but was: " + size);
			}
		}

		/**
		 * An entry-set for maps that is backed by a list.
		 */
		public final class ListMapEntrySet extends AbstractSet<ListMapEntry> {
			/**
			 * Construct a new entry-set.
			 */
			private ListMapEntrySet() {
			}

			@Override
			public ListMapEntrySetIterator iterator() {
				return new ListMapEntrySetIterator();
			}

			@Override
			public int size() {
				return IndexMapFromList.this.list.size();
			}

			/**
			 * An iterator for an entry-set of maps that is backed by a list.
			 */
			public final class ListMapEntrySetIterator implements Iterator<ListMapEntry> {
				/**
				 * The current position of this iterator.
				 */
				private int cursor;
				/**
				 * The initial size of this array once this iterator created.
				 */
				private int mod = IndexMapFromList.this.list.size();

				/**
				 * Construct a new iterator.
				 */
				private ListMapEntrySetIterator() {
				}

				@Override
				public boolean hasNext() {
					return this.mod > this.cursor;
				}

				@Override
				public ListMapEntry next() {
					this.assertNoModification();
					this.assertInBounds();
					return new ListMapEntry(this.mod, this.cursor++);
				}

				@Override
				public void remove() {
					this.assertNoModification();
					IndexMapFromList.this.list.remove(this.cursor);
					this.mod--;
				}

				/**
				 * Check if this iterator still has more elements.
				 *
				 * @throws NoSuchElementException if this iterator has no more elements.
				 */
				private void assertInBounds() {
					if (this.mod <= this.cursor)
						throw new NoSuchElementException(String.valueOf(this.cursor + 1));
				}

				/**
				 * Check if the list has modified while using this iterator.
				 *
				 * @throws ConcurrentModificationException if the list has been modified since
				 *                                         creating this iterator.
				 */
				private void assertNoModification() {
					int size = IndexMapFromList.this.list.size();
					if (this.mod != size)
						throw new ConcurrentModificationException(
								"expected: " + this.mod + ", but was: " + size);
				}
			}
		}
	}

	/**
	 * A list backed by a map.
	 *
	 * @param <E> the type of elements in this list.
	 */
	@Deprecated
	public static final class ListFromIndexMap<E> extends AbstractList<E> {
		/**
		 * The map backing this list.
		 */
		private final Map<Integer, E> map;

		/**
		 * Construct a new {@link ListFromIndexMap} backed by the given {@code map}.
		 *
		 * @param map the map backing the constructed list.
		 * @throws NullPointerException if the given {@code map} is null.
		 */
		private ListFromIndexMap(Map<Integer, E> map) {
			Objects.requireNonNull(map, "map");
			this.map = map;
		}

		/**
		 * Check if the given index is valid or not.
		 *
		 * @param index   the index to check
		 * @param allowed the allowed index (as maximum)
		 */
		private static void assertWithinBounds(int index, int allowed) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index=" + index + " is negative");
			else if (index > allowed)
				throw new IndexOutOfBoundsException(
						"index=" + index + " is out of bounds allowed=" + allowed);
		}

		@Override
		public void add(int index, E element) {
			ListFromIndexMap.assertWithinBounds(index, this.size());
			this.shiftIndexes(index, null, 1);
			this.map.put(index, element);
		}

		@Override
		public E get(int index) {
			ListFromIndexMap.assertWithinBounds(index, this.maxIndex());
			return this.map.get(index);
		}

		@Override
		public E remove(int index) {
			ListFromIndexMap.assertWithinBounds(index, this.maxIndex());
			E value = this.map.remove(index);
			this.shiftIndexes(index, null, -1);
			return value;
		}

		@Override
		public E set(int index, E element) {
			ListFromIndexMap.assertWithinBounds(index, this.maxIndex());
			return this.map.put(index, element);
		}

		@Override
		public int size() {
			return this.maxIndex() + 1;
		}

		/**
		 * The maximum integer. That have been stored as a key in this.
		 *
		 * @return the maximum index in the provided {@code map}.
		 */
		private int maxIndex() {
			int index;
			int max = -1;

			for (Object key : this.map.keySet())
				//noinspection NestedAssignment
				if (key instanceof Integer && (index = (int) key) > max)
					max = index;

			return max;
		}

		/**
		 * Shift indexes within the given range by the given value.
		 * <p>
		 * ex.
		 * <pre>
		 *     map = {0:zero, 1:one, 2:two, 3:three}
		 *     map.shiftIndexes(<font color="gray">start</font> 1, <font color="gray">end</font> 2, <font color="gray">by</font> -1)
		 *     assert map == {0:zero, 1:two, 3:three}
		 *     map = {0:A, 1:B, 2:C, 3:D, 4:E}
		 *     map.shiftIndexes(<font color="gray">start</font> 2, <font color="gray">end</font> null, <font color="gray">by</font> 2)
		 *     assert map == {0:A, 1:B, 4:C, 5:D, 6:E}
		 * </pre>
		 *
		 * @param start the start of the shifting range (null for no start).
		 * @param end   the end of the shifting range (null for no end).
		 * @param by    the length to shift the values of the provided {@code map} by.
		 */
		private void shiftIndexes(Integer start, Integer end, int by) {
			Map<Integer, E> modified = new HashMap<>();

			boolean noStart = start == null;
			boolean noEnd = end == null;

			this.map.forEach((Object key, E value) -> {
				if (key instanceof Integer && (noStart || (int) key >= start) &&
					(noEnd || (int) key <= end))
					modified.put((int) key, value);
			});

			modified.forEach((key, value) -> {
				int index = key;
				this.map.remove(index, value);

				index += by;
				if ((noStart || index >= start) && (noEnd || index <= end))
					this.map.put(index, value);
			});
		}
	}

	public static class CheckedCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = -4080006260960608086L;

		protected final Collection<E> collection;
		protected final Class<E> type;

		public CheckedCollection(Collection<E> collection, Class<E> type) {
			Objects.requireNonNull(collection, "collection");
			Objects.requireNonNull(type, "type");
			this.collection = collection;
			this.type = type;
		}

		@Override
		public boolean add(E element) {
			return this.collection.add(this.checked(element));
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			return this.collection.addAll(this.checked(collection));
		}

		@Override
		public void clear() {
			this.collection.clear();
		}

		@Override
		public boolean contains(Object object) {
			return this.collection.contains(object);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return this.collection.containsAll(collection);
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			this.collection.forEach(consumer);
		}

		@Override
		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		@Override
		public Iterator iterator() {
			return new Iterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return this.collection.parallelStream();
		}

		@Override
		public boolean remove(Object object) {
			return this.collection.remove(object);
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			return this.collection.removeAll(collection);
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			return this.collection.removeIf(predicate);
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			return this.collection.retainAll(collection);
		}

		@Override
		public int size() {
			return this.collection.size();
		}

		@Override
		public Spliterator<E> spliterator() {
			return this.collection.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return this.collection.stream();
		}

		@Override
		public Object[] toArray() {
			return this.collection.toArray();
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return this.collection.toArray(array);
		}

		@Override
		public String toString() {
			return this.collection.toString();
		}

		protected E checked(E element) {
			return this.type.cast(element);
		}

		protected Collection<E> checked(Collection<? extends E> collection) {
			return (Collection<E>) Array.of(collection, this.type).list();
		}

		public class Iterator implements java.util.Iterator<E> {
			protected final java.util.Iterator<E> iterator = CheckedCollection.this.collection.iterator();

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public E next() {
				return this.iterator.next();
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedList<E> extends CheckedCollection<E> implements List<E> {
		private static final long serialVersionUID = -8220369572278063061L;

		protected final List<E> list;

		public CheckedList(List<E> list, Class<E> type) {
			super(list, type);
			this.list = list;
		}

		@Override
		public void add(int index, E element) {
			this.list.add(index, this.checked(element));
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			return this.list.addAll(index, this.checked(collection));
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.list.equals(object);
		}

		@Override
		public E get(int index) {
			return this.list.get(index);
		}

		@Override
		public int hashCode() {
			return this.list.hashCode();
		}

		@Override
		public int indexOf(Object object) {
			return this.list.indexOf(object);
		}

		@Override
		public int lastIndexOf(Object object) {
			return this.list.lastIndexOf(object);
		}

		@Override
		public ListIterator listIterator() {
			return new ListIterator();
		}

		@Override
		public ListIterator listIterator(int index) {
			return new ListIterator(index);
		}

		@Override
		public E remove(int index) {
			return this.list.remove(index);
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
			this.list.replaceAll(element -> this.checked(operator.apply(element)));
		}

		@Override
		public E set(int index, E element) {
			return this.list.set(index, this.checked(element));
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			this.list.sort(comparator);
		}

		@Override
		public CheckedList<E> subList(int beginIndex, int endIndex) {
			return new CheckedList(this.list.subList(beginIndex, endIndex), this.type);
		}

		public class ListIterator implements java.util.ListIterator<E> {
			protected final java.util.ListIterator<E> iterator;

			public ListIterator() {
				this.iterator = CheckedList.this.list.listIterator();
			}

			public ListIterator(int index) {
				this.iterator = CheckedList.this.list.listIterator(index);
			}

			@Override
			public void add(E element) {
				this.iterator.add(CheckedList.this.checked(element));
			}

			@Override
			public void forEachRemaining(Consumer<? super E> consumer) {
				this.iterator.forEachRemaining(consumer);
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return this.iterator.hasPrevious();
			}

			@Override
			public E next() {
				return this.iterator.next();
			}

			@Override
			public int nextIndex() {
				return this.iterator.nextIndex();
			}

			@Override
			public E previous() {
				return this.iterator.previous();
			}

			@Override
			public int previousIndex() {
				return this.iterator.previousIndex();
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}

			@Override
			public void set(E element) {
				this.iterator.set(CheckedList.this.checked(element));
			}
		}
	}

	public static class CheckedMap<K, V> implements Map<K, V>, Serializable {
		private static final long serialVersionUID = 8504055594508288742L;

		protected final Class<K> keyType;
		protected final Map<K, V> map;
		protected final Class<V> valueType;

		public CheckedMap(Map<K, V> map, Class<K> keyType, Class<V> valueType) {
			Objects.requireNonNull(map, "map");
			Objects.requireNonNull(keyType, "keyType");
			Objects.requireNonNull(valueType, "valueType");
			this.map = map;
			this.keyType = keyType;
			this.valueType = valueType;
		}

		@Override
		public void clear() {
			this.map.clear();
		}

		@Override
		public boolean containsKey(Object key) {
			return this.map.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return this.map.containsValue(value);
		}

		@Override
		public CheckedEntrySet entrySet() {
			return new CheckedEntrySet();
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.map.equals(object);
		}

		@Override
		public V get(Object key) {
			return this.map.get(key);
		}

		@Override
		public int hashCode() {
			return this.map.hashCode();
		}

		@Override
		public boolean isEmpty() {
			return this.map.isEmpty();
		}

		@Override
		public Set<K> keySet() {
			return new CheckedSet(this.map.keySet(), this.keyType);
		}

		@Override
		public V put(K key, V value) {
			return this.map.put(
					this.checkedKey(key),
					this.checkedValue(value)
			);
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map) {
			this.map.putAll(this.checked(map));
		}

		@Override
		public V remove(Object key) {
			return this.map.remove(key);
		}

		@Override
		public int size() {
			return this.map.size();
		}

		@Override
		public String toString() {
			return this.map.toString();
		}

		@Override
		public Collection<V> values() {
			return new CheckedCollection(this.map.values(), this.valueType);
		}

		protected Map<K, V> checked(Map<? extends K, ? extends V> map) {
			Map checked = new HashMap();

			for (Object object : map.entrySet()) {
				Map.Entry<K, V> entry = (Map.Entry) object;
				checked.put(
						this.checkedKey(entry.getKey()),
						this.checkedValue(entry.getValue())
				);
			}

			return checked;
		}

		protected K checkedKey(K key) {
			return this.keyType.cast(key);
		}

		protected V checkedValue(V value) {
			return this.valueType.cast(value);
		}

		public class CheckedEntry implements Map.Entry<K, V>, Serializable {
			private static final long serialVersionUID = 6060443667289298487L;

			protected final Map.Entry<K, V> entry;

			public CheckedEntry(Map.Entry<K, V> entry) {
				Objects.requireNonNull(entry, "entry");
				this.entry = entry;
			}

			@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
			@Override
			public boolean equals(Object object) {
				//todo; I believe that if the entry got its way,
				// it is the fault of the wrapped entry relying on a random entry
				// to do its job
				return object == this || this.entry.equals(object);
			}

			@Override
			public K getKey() {
				return this.entry.getKey();
			}

			@Override
			public V getValue() {
				return this.entry.getValue();
			}

			@Override
			public int hashCode() {
				return this.entry.hashCode();
			}

			@Override
			public V setValue(V value) {
				return this.entry.setValue(CheckedMap.this.checkedValue(value));
			}

			@Override
			public String toString() {
				return this.entry.toString();
			}
		}

		public class CheckedEntrySet implements Set<Map.Entry<K, V>>, Serializable {
			private static final long serialVersionUID = -2054578456248104847L;

			protected final Set<Map.Entry<K, V>> set = CheckedMap.this.map.entrySet();

			@Override
			public boolean add(Entry<K, V> entry) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends Entry<K, V>> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public void clear() {
				this.set.clear();
			}

			@Override
			public boolean contains(Object object) {
				if (object instanceof CheckedMap.CheckedEntry)
					return this.set.contains(object);
				if (object instanceof Map.Entry)
					return this.set.contains(new CheckedEntry((Entry) object));

				return false;
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				for (Object object : collection) {
					if (this.contains(object))
						continue;

					return false;
				}

				return true;
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Set) {
					Set set = (Set) object;

					if (set.size() == this.set.size())
						return this.containsAll(set);
				}

				return false;
			}

			@Override
			public int hashCode() {
				return this.set.hashCode();
			}

			@Override
			public boolean isEmpty() {
				return this.set.isEmpty();
			}

			@Override
			public java.util.Iterator iterator() {
				return new Iterator();
			}

			@Override
			public boolean remove(Object object) {
				if (object instanceof CheckedMap.CheckedEntry)
					return this.set.remove(object);
				if (object instanceof Entry)
					this.set.remove(new CheckedEntry(object));

			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				//todo; I believe that if the collection got its way to an entry,
				// it is the fault of the wrapped collection relying on a random collection
				// to do its job
				return this.set.remove(collection);
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				//todo; I believe that if the collection got its way to an entry,
				// it is the fault of the wrapped collection relying on a random collection
				// to do its job
				return this.set.retainAll(collection);
			}

			@Override
			public int size() {
				return this.set.size();
			}

			@Override
			public Object[] toArray() {
				Object[] src = this.set.toArray();
				Object[] dest = src.getClass().isAssignableFrom(CheckedEntry[].class) ?
								src : new Object[src.length];

				for (int i = 0; i < src.length; i++) {
					Entry<K, V> entry = (Entry<K, V>) src[i];
					dest[i] = entry == null ? null : new CheckedEntry((Entry) src[i]);
				}

				return dest;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Object[] temp = this.set.toArray(
						array.length == 0 ?
						array :
						Arrays.copyOf(array, 0)
				);

				for (int i = 0; i < array.length; i++) {
					Entry<K, V> entry = (Entry<K, V>) temp[i];
					temp[i] = entry == null ? null : (T) new CheckedEntry((Entry) temp[i]);
				}

				if (temp.length > array.length)
					return (T[]) temp;

				System.arraycopy(
						temp,
						0,
						array,
						0,
						temp.length
				);

				if (array.length > temp.length)
					array[temp.length] = null;

				return array;
			}

			@Override
			public String toString() {
				return this.set.toString();
			}

			public class Iterator implements java.util.Iterator<CheckedEntry> {
				protected final java.util.Iterator<Map.Entry<K, V>> iterator = CheckedEntrySet.this.set.iterator();

				@Override
				public boolean hasNext() {
					return this.iterator.hasNext();
				}

				@Override
				public CheckedEntry next() {
					return new CheckedEntry(this.iterator.next());
				}

				@Override
				public void remove() {
					this.iterator.remove();
				}
			}
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedNavigableMap<K, V> extends CheckedSortedMap<K, V> implements NavigableMap<K, V> {
		private static final long serialVersionUID = 8979018726865156296L;

		protected final NavigableMap<K, V> map;

		public CheckedNavigableMap(NavigableMap<K, V> map, Class<K> keyType, Class<V> valueType) {
			super(map, keyType, valueType);
			this.map = map;
		}

		@Override
		public CheckedEntry ceilingEntry(K key) {
			Entry<K, V> entry = this.map.ceilingEntry(key);
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public K ceilingKey(K key) {
			return this.map.ceilingKey(key);
		}

		@Override
		public CheckedNavigableSet<K> descendingKeySet() {
			return new CheckedNavigableSet(this.map.descendingKeySet(), this.keyType);
		}

		@Override
		public CheckedNavigableMap descendingMap() {
			return new CheckedNavigableMap(
					this.map.descendingMap(),
					this.keyType,
					this.valueType
			);
		}

		@Override
		public CheckedEntry firstEntry() {
			Entry<K, V> entry = this.map.firstEntry();
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public CheckedEntry floorEntry(K key) {
			Entry<K, V> entry = this.map.floorEntry(key);
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public K floorKey(K key) {
			return this.map.floorKey(key);
		}

		@Override
		public CheckedNavigableMap<K, V> headMap(K endKey, boolean inclusive) {
			return new CheckedNavigableMap(this.map.headMap(
					endKey,
					inclusive
			), this.keyType, this.valueType);
		}

		@Override
		public CheckedEntry higherEntry(K key) {
			Entry<K, V> entry = this.map.higherEntry(key);
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public K higherKey(K key) {
			return this.map.higherKey(key);
		}

		@Override
		public CheckedSet<K> keySet() {
			return new CheckedSet(this.map.keySet(), this.keyType);
		}

		@Override
		public CheckedEntry lastEntry() {
			Entry<K, V> entry = this.map.lastEntry();
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public CheckedEntry lowerEntry(K key) {
			Entry<K, V> entry = this.map.lowerEntry(key);
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public K lowerKey(K key) {
			return this.map.lowerKey(key);
		}

		@Override
		public CheckedNavigableSet<K> navigableKeySet() {
			return new CheckedNavigableSet(this.map.navigableKeySet(), this.keyType);
		}

		@Override
		public CheckedEntry pollFirstEntry() {
			Entry<K, V> entry = this.map.pollFirstEntry();
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public CheckedEntry pollLastEntry() {
			Entry<K, V> entry = this.map.pollLastEntry();
			return entry == null ? null : new CheckedEntry(entry);
		}

		@Override
		public CheckedNavigableMap<K, V> subMap(K beginKey, boolean beginInclusive, K endKey, boolean endInclusive) {
			return new CheckedNavigableMap(this.map.subMap(
					beginKey,
					beginInclusive,
					endKey,
					endInclusive
			), this.keyType, this.valueType);
		}

		@Override
		public CheckedNavigableMap<K, V> tailMap(K beginKey, boolean inclusive) {
			return new CheckedNavigableMap(this.map.tailMap(
					beginKey,
					inclusive
			), this.keyType, this.valueType);
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedNavigableSet<E> extends CheckedSortedSet<E> implements NavigableSet<E> {
		private static final long serialVersionUID = 1361875270830495159L;

		private final NavigableSet<E> set;

		public CheckedNavigableSet(NavigableSet<E> set, Class<E> type) {
			super(set, type);
			this.set = set;
		}

		@Override
		public E ceiling(E element) {
			return this.set.ceiling(element);
		}

		@Override
		public Iterator descendingIterator() {
			return new CheckedNavigableSet(this.set.descendingSet(), this.type).iterator();
		}

		@Override
		public CheckedNavigableSet<E> descendingSet() {
			return new CheckedNavigableSet(this.set.descendingSet(), this.type);
		}

		@Override
		public E floor(E element) {
			return this.set.floor(element);
		}

		@Override
		public NavigableSet<E> headSet(E endElement, boolean inclusive) {
			return new CheckedNavigableSet(this.set.headSet(
					endElement,
					inclusive
			), this.type);
		}

		@Override
		public E higher(E element) {
			return this.set.higher(element);
		}

		@Override
		public E lower(E element) {
			return this.set.lower(element);
		}

		@Override
		public E pollFirst() {
			return this.set.pollFirst();
		}

		@Override
		public E pollLast() {
			return this.set.pollLast();
		}

		@Override
		public CheckedNavigableSet<E> subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive) {
			return new CheckedNavigableSet(this.set.subSet(
					beginElement,
					beginInclusive,
					endElement,
					endInclusive
			), this.type);
		}

		@Override
		public NavigableSet<E> tailSet(E beginElement, boolean inclusive) {
			return new CheckedNavigableSet(this.set.tailSet(
					beginElement,
					inclusive
			), this.type);
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedQueue<E> extends CheckedCollection<E> implements Queue<E> {
		private static final long serialVersionUID = -7115131037976269824L;

		protected final Queue<E> queue;

		public CheckedQueue(Queue<E> queue, Class<E> type) {
			super(queue, type);
			this.queue = queue;
		}

		@Override
		public E element() {
			return this.queue.element();
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.queue.equals(object);
		}

		@Override
		public int hashCode() {
			return this.queue.hashCode();
		}

		@Override
		public boolean offer(E element) {
			return this.queue.offer(this.checked(element));
		}

		@Override
		public E peek() {
			return this.queue.peek();
		}

		@Override
		public E poll() {
			return this.queue.poll();
		}

		@Override
		public E remove() {
			return this.queue.remove();
		}
	}

	public static class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess {
		private static final long serialVersionUID = 7752208959092863009L;

		public CheckedRandomAccessList(List<E> list, Class<E> type) {
			super(list, type);
		}

		@Override
		public CheckedRandomAccessList subList(int beginIndex, int endIndex) {
			return new CheckedRandomAccessList(this.list.subList(
					beginIndex,
					endIndex
			), this.type);
		}
	}

	public static class CheckedSet<E> extends CheckedCollection<E> implements Set<E> {
		private static final long serialVersionUID = -5155099774357573990L;

		public CheckedSet(Set<E> set, Class<E> type) {
			super(set, type);
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.collection.equals(object);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedSortedMap<K, V> extends CheckedMap<K, V> implements SortedMap<K, V> {
		private static final long serialVersionUID = 8177693612808969276L;

		protected final SortedMap<K, V> map;

		public CheckedSortedMap(SortedMap<K, V> map, Class<K> keyType, Class<V> valueType) {
			super(map, keyType, valueType);
			this.map = map;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.map.comparator();
		}

		@Override
		public K firstKey() {
			return this.map.firstKey();
		}

		@Override
		public CheckedSortedMap<K, V> headMap(K endKey) {
			return new CheckedSortedMap(this.map.headMap(
					endKey
			), this.keyType, this.valueType);
		}

		@Override
		public K lastKey() {
			return this.map.lastKey();
		}

		@Override
		public CheckedSortedMap<K, V> subMap(K beginKey, K endKey) {
			return new CheckedSortedMap(this.map.subMap(
					beginKey,
					endKey
			), this.keyType, this.valueType);
		}

		@Override
		public CheckedSortedMap<K, V> tailMap(K beginKey) {
			return new CheckedSortedMap(this.map.tailMap(
					beginKey
			), this.keyType, this.valueType);
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E> {
		private static final long serialVersionUID = 263048283547773050L;

		protected final SortedSet<E> set;

		public CheckedSortedSet(SortedSet<E> set, Class<E> type) {
			super(set, type);
			this.set = set;
		}

		@Override
		public Comparator<? super E> comparator() {
			return this.set.comparator();
		}

		@Override
		public E first() {
			return this.set.first();
		}

		@Override
		public CheckedSortedSet<E> headSet(E endElement) {
			return new CheckedSortedSet(this.set.headSet(endElement), this.type);
		}

		@Override
		public E last() {
			return this.set.last();
		}

		@Override
		public CheckedSortedSet<E> subSet(E beginElement, E endElement) {
			return new CheckedSortedSet(this.set.subSet(beginElement, endElement), this.type);
		}

		@Override
		public CheckedSortedSet<E> tailSet(E beginElement) {
			return new CheckedSortedSet(this.set.tailSet(beginElement), this.type);
		}
	}

	//empty

	/**
	 * An empty collection. Use {@link #EMPTY_COLLECTION} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = -8351977182530429149L;

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			if (collection.isEmpty())
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public void clear() {
		}

		@Override
		public boolean contains(Object object) {
			return true;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			return collection.isEmpty();
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public EmptyIterator<E> iterator() {
			return new EmptyIterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return Stream.empty();
		}

		@Override
		public boolean remove(Object object) {
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			Objects.requireNonNull(predicate, "predicate");
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			return false;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Spliterator<E> spliterator() {
			return Spliterators.emptySpliterator();
		}

		@Override
		public Stream<E> stream() {
			return Stream.empty();
		}

		@Override
		public Object[] toArray() {
			return new Object[0];
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			if (array.length != 0)
				array[0] = null;

			return array;
		}

		@Override
		public String toString() {
			return "[]";
		}
	}

	/**
	 * An empty {@code enumeration}. Use {@link #EMPTY_ENUMERATION} if you do not want to initialize
	 * a new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyEnumeration<E> implements Enumeration<E> {
		@Override
		public boolean hasMoreElements() {
			return false;
		}

		@Override
		public E nextElement() {
			throw new NoSuchElementException();
		}
	}

	/**
	 * An empty iterator. Use {@link #EMPTY_ITERATOR} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyIterator<E> implements Iterator<E> {
		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E next() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	}

	/**
	 * An empty list. Use {@link #EMPTY_LIST} if you do not want to initialize a new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyList<E> extends EmptyCollection<E> implements List<E>, RandomAccess {
		private static final long serialVersionUID = -9014261348114229075L;

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			if (index != 0)
				throw new IndexOutOfBoundsException(Integer.toString(index));
			if (collection.isEmpty())
				return false;

			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof List &&
				   ((List) object).isEmpty();
		}

		@Override
		public E get(int index) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public int indexOf(Object object) {
			return -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return -1;
		}

		@Override
		public EmptyListIterator<E> listIterator() {
			return new EmptyListIterator();
		}

		@Override
		public EmptyListIterator<E> listIterator(int index) {
			if (index != 0)
				throw new IndexOutOfBoundsException(Integer.toString(index));

			return new EmptyListIterator();
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
		}

		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException("set");
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
		}

		@Override
		public EmptyList<E> subList(int beginIndex, int endIndex) {
			if (beginIndex != 0)
				throw new IndexOutOfBoundsException(Integer.toString(beginIndex));
			if (endIndex != 0)
				throw new IndexOutOfBoundsException(Integer.toString(endIndex));

			return this;
		}
	}

	/**
	 * An empty list iterator. Use {@link #EMPTY_LIST_ITERATOR} if you do not want to initialize a
	 * new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyListIterator<E> extends EmptyIterator<E> implements ListIterator<E> {
		@Override
		public void add(E e) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasPrevious() {
			return false;
		}

		@Override
		public int nextIndex() {
			return 0;
		}

		@Override
		public E previous() {
			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return -1;
		}

		@Override
		public void set(E e) {
			throw new IllegalStateException();
		}
	}

	/**
	 * An empty map. Use {@link #EMPTY_MAP} if you do not want to initialize a new instance.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptyMap<K, V> implements Map<K, V>, Serializable {
		private static final long serialVersionUID = -4177271220044195084L;

		@Override
		public void clear() {
		}

		@Override
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("compute");
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			throw new UnsupportedOperationException("computeIfAbsent");
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("computeIfPresent");
		}

		@Override
		public boolean containsKey(Object key) {
			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			return false;
		}

		@Override
		public EmptySet<Entry<K, V>> entrySet() {
			return new EmptySet();
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Map &&
				   ((Map) object).isEmpty();
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
		}

		@Override
		public V get(Object key) {
			return null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return defaultValue;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public EmptySet<K> keySet() {
			return new EmptySet();
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("merge");
		}

		@Override
		public V put(K key, V value) {
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map) {
			if (map.isEmpty())
				return;

			throw new UnsupportedOperationException("put");
		}

		@Override
		public V putIfAbsent(K key, V value) {
			throw new UnsupportedOperationException("putIfAbsent");
		}

		@Override
		public V remove(Object key) {
			return null;
		}

		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public V replace(K key, V value) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public String toString() {
			return "{}";
		}

		@Override
		public EmptyCollection<V> values() {
			return new EmptyCollection();
		}
	}

	/**
	 * An empty navigable map. Use {@link #EMPTY_NAVIGABLE_MAP} if you do not want to initialize a
	 * new instance.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptyNavigableMap<K, V> extends EmptySortedMap<K, V> implements NavigableMap<K, V> {
		private static final long serialVersionUID = 3397829386961847917L;

		@Override
		public Entry<K, V> ceilingEntry(K key) {
			return null;
		}

		@Override
		public K ceilingKey(K key) {
			return null;
		}

		@Override
		public EmptyNavigableSet<K> descendingKeySet() {
			return new EmptyNavigableSet();
		}

		@Override
		public EmptyNavigableMap<K, V> descendingMap() {
			return this;
		}

		@Override
		public Entry<K, V> firstEntry() {
			return null;
		}

		@Override
		public Entry<K, V> floorEntry(K key) {
			return null;
		}

		@Override
		public K floorKey(K key) {
			return null;
		}

		@Override
		public EmptyNavigableMap<K, V> headMap(K endKey, boolean inclusive) {
			//java.util.Collections.emptyNavigableMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.headMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) endKey).compareTo(endKey);

			return this;
		}

		@Override
		public Entry<K, V> higherEntry(K key) {
			return null;
		}

		@Override
		public K higherKey(K key) {
			return null;
		}

		@Override
		public Entry<K, V> lastEntry() {
			return null;
		}

		@Override
		public Entry<K, V> lowerEntry(K key) {
			return null;
		}

		@Override
		public K lowerKey(K key) {
			return null;
		}

		@Override
		public EmptyNavigableSet<K> navigableKeySet() {
			return new EmptyNavigableSet();
		}

		@Override
		public Entry<K, V> pollFirstEntry() {
			throw new UnsupportedOperationException("pollFirstEntry");
		}

		@Override
		public Entry<K, V> pollLastEntry() {
			throw new UnsupportedOperationException("pollLastEntry");
		}

		@Override
		public EmptyNavigableMap<K, V> subMap(K beginKey, boolean beginInclusive, K endKey, boolean endInclusive) {
			//java.util.Collections.emptyNavigableMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.subMap(K, boolean, K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			if (((Comparable) beginKey).compareTo(endKey) > 0)
				throw new IllegalArgumentException("beginElement > endElement");

			return this;
		}

		@Override
		public EmptyNavigableMap<K, V> tailMap(K beginKey, boolean inclusive) {
			//java.util.Collections.emptyNavigableMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.tailMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) beginKey).compareTo(beginKey);

			return this;
		}
	}

	/**
	 * An empty navigable set. Use {@link #EMPTY_NAVIGABLE_SET} if do not want to initialize a new
	 * instnace.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptyNavigableSet<E> extends EmptySortedSet<E> implements NavigableSet<E> {
		private static final long serialVersionUID = -6501678607767173838L;

		@Override
		public E ceiling(E element) {
			return null;
		}

		@Override
		public EmptyIterator<E> descendingIterator() {
			return new EmptyIterator();
		}

		@Override
		public EmptyNavigableSet<E> descendingSet() {
			return this;
		}

		@Override
		public E floor(E element) {
			return null;
		}

		@Override
		public EmptyNavigableSet<E> headSet(E endElement, boolean inclusive) {
			//java.util.Collections.emptyNavigableSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.headMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) endElement).compareTo(endElement);

			return this;
		}

		@Override
		public E higher(E element) {
			return null;
		}

		@Override
		public E lower(E element) {
			return null;
		}

		@Override
		public E pollFirst() {
			throw new UnsupportedOperationException("pollFirst");
		}

		@Override
		public E pollLast() {
			throw new UnsupportedOperationException("pollLast");
		}

		@Override
		public EmptyNavigableSet<E> subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive) {
			//java.util.Collections.emptyNavigableSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.subMap(K, boolean, K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			if (((Comparable) beginElement).compareTo(endElement) > 0)
				throw new IllegalArgumentException("beginElement > endElement");

			return this;
		}

		@Override
		public EmptyNavigableSet<E> tailSet(E beginElement, boolean inclusive) {
			//java.util.Collections.emptyNavigableSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.tailMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) beginElement).compareTo(beginElement);

			return this;
		}
	}

	/**
	 * An empty set. Use {@link #EMPTY_SET} if you do not want to initialize a new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptySet<E> extends EmptyCollection<E> implements Set<E> {
		private static final long serialVersionUID = 2341708820388507659L;

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Set &&
				   ((Set) object).isEmpty();
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}

	/**
	 * An empty sorted map. Use {@link #EMPTY_SORTED_MAP} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptySortedMap<K, V> extends EmptyMap<K, V> implements SortedMap<K, V> {
		private static final long serialVersionUID = 7767754588764717002L;

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public K firstKey() {
			return null;
		}

		@Override
		public EmptySortedMap<K, V> headMap(K endKey) {
			//java.util.Collections.emptySortedMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.headMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) endKey).compareTo(endKey);

			return this;
		}

		@Override
		public K lastKey() {
			return null;
		}

		@Override
		public EmptySortedMap<K, V> subMap(K beginKey, K endKey) {
			//java.util.Collections.emptySortedMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.subMap(K, boolean, K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			if (((Comparable) beginKey).compareTo(endKey) > 0)
				throw new IllegalArgumentException("beginElement > endElement");

			return this;
		}

		@Override
		public EmptySortedMap<K, V> tailMap(K beginKey) {
			//java.util.Collections.emptySortedMap() ->
			//java.util.Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP ->
			//java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.tailMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) beginKey).compareTo(beginKey);

			return this;
		}
	}

	/**
	 * An empty sorted set. Use {@link #EMPTY_SORTED_SET} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	public static class EmptySortedSet<E> extends EmptySet<E> implements SortedSet<E> {
		private static final long serialVersionUID = -417508919441363162L;

		@Override
		public Comparator<? super E> comparator() {
			return null;
		}

		@Override
		public E first() {
			return null;
		}

		@Override
		public EmptySortedSet<E> headSet(E endElement) {
			//java.util.Collections.emptySortedSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.headMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) endElement).compareTo(endElement);

			return this;
		}

		@Override
		public E last() {
			return null;
		}

		@Override
		public EmptySortedSet<E> subSet(E beginElement, E endElement) {
			//java.util.Collections.emptySortedSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.subMap(K, boolean, K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			if (((Comparable) beginElement).compareTo(endElement) > 0)
				throw new IllegalArgumentException("beginElement > endElement");

			return this;
		}

		@Override
		public EmptySortedSet<E> tailSet(E beginElement) {
			//java.util.Collections.emptySortedSet() ->
			//java.util.Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET ->
			//java.util.Collections.UnmodifiableNavigableSet.EmptyNavigableSet() ->
			//java.util.TreeSet() ->
			//java.util.TreeMap() ->>
			//java.util.TreeMap.tailMap(K, boolean) ->
			//java.util.TreeMap.AscendingSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.NavigableSubMap(TreeMap, boolean, K, boolean, boolean, K, boolean) ->
			//java.util.TreeMap.compare(Object, Object)
			((Comparable) beginElement).compareTo(beginElement);

			return this;
		}
	}

	//

	public static class EnumerationFromIterator<E> implements Enumeration<E> {
		protected final Iterator<E> iterator;

		public EnumerationFromIterator(Iterator<E> iterator) {
			Objects.requireNonNull(iterator, "iterator");
			this.iterator = iterator;
		}

		@Override
		public boolean hasMoreElements() {
			return this.iterator.hasNext();
		}

		@Override
		public E nextElement() {
			return this.iterator.next();
		}
	}

	/**
	 * @param <E>
	 */
	@Deprecated
	public static class FilteredCollection<E> extends AbstractCollection<E> {
		//todo hashCode(), equals()
		/**
		 * The collection to be filtered.
		 */
		private final Collection<E> collection;
		/**
		 * The filter this collection is using.
		 */
		private final Predicate<E> filter;

		/**
		 * Construct a new filtered-collection. Filtered from the given{@code collection}.
		 *
		 * @param collection the collection the constructed collection is filtering.
		 * @param filter     the filter the constructed is using.
		 * @throws NullPointerException if the given {@code collection} or {@code filter} is null.
		 */
		private FilteredCollection(Collection<E> collection, Predicate filter) {
			Objects.requireNonNull(collection, "collection");
			Objects.requireNonNull(filter, "filter");
			this.collection = collection;
			this.filter = filter;
		}

		@Override
		public boolean add(E e) {
			if (!this.filter.test(e))
				throw new UnsupportedOperationException("Illegal element");

			return this.collection.add(e);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof FilteredCollection &&
				   ((Collection) object).size() == this.size() &&
				   ((Collection) object).containsAll(this);
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (E element : this)
				hashCode += Objects.hashCode(element);

			return hashCode;
		}

		@Override
		public boolean isEmpty() {
			return this.collection.isEmpty() || !this.iterator().hasNext();
		}

		@Override
		public Iterator<E> iterator() {
			return new FilteredCollectionIterator();
		}

		@Override
		public int size() {
			Iterator<E> iterator = this.collection.iterator();
			int size = 0;

			while (iterator.hasNext())
				if (this.filter.test(iterator.next()))
					size++;

			return size;
		}

		@Override
		public String toString() {
			Iterator<E> iterator = this.iterator();

			if (iterator.hasNext()) {
				StringBuilder builder = new StringBuilder("[");

				while (true) {
					builder.append(iterator.next());

					if (iterator.hasNext()) {
						builder.append(", ");
						continue;
					}

					break;
				}

				return builder.append("]").toString();
			}

			return "[]";
		}

		/**
		 * An iterator for filtered collections.
		 */
		public final class FilteredCollectionIterator implements Iterator<E> {
			/**
			 * The backing iterator for this iterator.
			 */
			private final Iterator<E> iterator = FilteredCollection.this.collection.iterator();
			/**
			 * If next element is available.
			 */
			private boolean available;
			/**
			 * The next element to be returned. (maybe null).
			 */
			private E next;
			/**
			 * If {@link #remove()} invocation is allowed.
			 */
			private boolean remove;

			/**
			 * Construct a new filtered-collection-iterator.
			 */
			private FilteredCollectionIterator() {
			}

			@Override
			public boolean hasNext() {
				while (true) {
					if (this.available)
						//there is previously solved element
						return true;
					if (!this.iterator.hasNext())
						//no more elements
						return false;

					//the next unfiltered element
					E next = this.iterator.next();

					if (FilteredCollection.this.filter.test(next)) {
						//if the element is valid, cache it!
						this.available = true;
						this.next = next;
					}
				}
			}

			@Override
			public E next() {
				while (true) {
					if (this.available) {
						//there is previously solved element
						this.available = false;
						this.remove = true;
						return this.next;
					}
					if (!this.iterator.hasNext())
						//stop! no more elements.
						throw new NoSuchElementException("No more filtered elements");

					//the next unfiltered element
					E next = this.iterator.next();

					if (FilteredCollection.this.filter.test(next)) {
						//if the element is valid
						this.available = true;
						this.next = next;
					}
				}
			}

			@Override
			public void remove() {
				if (!this.remove)
					throw new IllegalStateException("One remove() call is allowed for each next() call!");

				this.remove = false;
				this.iterator.remove();
			}
		}
	}

	public static class LIFOQueue<E> extends AbstractQueue<E> implements Serializable {
		private static final long serialVersionUID = 7406211233756224981L;

		protected final Deque<E> deque;

		public LIFOQueue(Deque<E> deque) {
			Objects.requireNonNull(deque, "deque");
			this.deque = deque;
		}

		@Override
		public boolean add(E element) {
			this.deque.addFirst(element);
			return true;
		}

		@Override
		public void clear() {
			this.deque.clear();
		}

		@Override
		public boolean contains(Object object) {
			return this.deque.contains(object);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return this.deque.containsAll(collection);
		}

		@Override
		public E element() {
			return this.deque.getFirst();
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			this.deque.forEach(consumer);
		}

		@Override
		public boolean isEmpty() {
			return this.deque.isEmpty();
		}

		@Override
		public Iterator<E> iterator() {
			return this.deque.iterator();
		}

		@Override
		public boolean offer(E element) {
			return this.deque.offerFirst(element);
		}

		@Override
		public Stream<E> parallelStream() {
			return this.deque.parallelStream();
		}

		@Override
		public E peek() {
			return this.deque.peekFirst();
		}

		@Override
		public E poll() {
			return this.deque.pollFirst();
		}

		@Override
		public E remove() {
			return this.deque.removeFirst();
		}

		@Override
		public boolean remove(Object object) {
			return this.deque.remove(object);
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			return this.deque.removeAll(collection);
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			return this.deque.removeIf(predicate);
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			return this.deque.retainAll(collection);
		}

		@Override
		public int size() {
			return this.deque.size();
		}

		@Override
		public Spliterator<E> spliterator() {
			return this.deque.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return this.deque.stream();
		}

		@Override
		public Object[] toArray() {
			return this.deque.toArray();
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return this.deque.toArray(array);
		}

		@Override
		public String toString() {
			return this.deque.toString();
		}
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class NCopiesList<E> extends AbstractList<E> implements RandomAccess, Serializable {
		private static final long serialVersionUID = -8959888285748864462L;
		protected final E element;
		protected final int size;

		public NCopiesList(int size, E element) {
			if (size < 0)
				throw new IllegalArgumentException("Negative list size");
			this.size = size;
			this.element = element;
		}

		@Override
		public boolean contains(Object object) {
			return this.size != 0 &&
				   object == this.element ||
				   object != null &&
				   object.equals(this.element);
		}

		@Override
		public E get(int index) {
			if (index > -1 && index < this.size)
				return this.element;

			throw new IndexOutOfBoundsException(Integer.toString(index));
		}

		@Override
		public int indexOf(Object object) {
			return this.contains(object) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return this.contains(object) ? this.size - 1 : -1;
		}

		@Override
		public Stream<E> parallelStream() {
			return IntStream.range(0, this.size)
					.parallel()
					.mapToObj(i -> this.element);
		}

		@Override
		public int size() {
			return this.size;
		}

		@Override
		public Spliterator<E> spliterator() {
			return this.stream().spliterator();
		}

		@Override
		public Stream<E> stream() {
			return IntStream.range(0, this.size)
					.mapToObj(i -> this.element);
		}

		@Override
		public NCopiesList<E> subList(int beginIndex, int endIndex) {
			if (beginIndex < 0)
				throw new IndexOutOfBoundsException(Integer.toString(beginIndex));
			if (endIndex > this.size)
				throw new IndexOutOfBoundsException(Integer.toString(endIndex));
			if (endIndex < beginIndex)
				throw new IllegalArgumentException("endIndex < beginIndex");
			return new NCopiesList(endIndex - beginIndex, this.element);
		}

		@Override
		public Object[] toArray() {
			Object[] array = new Object[this.size];

			if (this.element != null)
				for (int i = 0; i < this.size; i++)
					array[i] = this.element;

			return array;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			T[] product = array;

			if (product.length < this.size)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), this.size);
			else if (product.length > this.size)
				product[this.size] = null;

			for (int i = 0; i < this.size; i++)
				product[i] = (T) this.element;

			return product;
		}
	}

	public static class ReverseComparator<T> implements Comparator<T>, Serializable {
		protected Comparator<T> comparator;

		public ReverseComparator(Comparator<T> comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(T object, T other) {
			return this.comparator.compare(object, other);
		}

		@Override
		public Comparator<T> reversed() {
			return this.comparator;
		}
	}

	public static class SetFromMap<E> extends AbstractSet<E> implements Serializable {
		public SetFromMap(Map<E, Boolean> map) {
		}
	}

	public static class SingletonIterator<E> implements Iterator<E> {
		protected final E element;
		protected boolean hasNext;

		public SingletonIterator(E element) {
			this.element = element;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			if (this.hasNext) {
				this.hasNext = false;
				consumer.accept(this.element);
			}
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public E next() {
			if (this.hasNext) {
				this.hasNext = false;
				return this.element;
			}

			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	public static class SingletonList<E> extends AbstractList<E> implements RandomAccess, Serializable {
		public SingletonList(E element) {
		}
	}

	public static class SingletonMap<K, V> extends AbstractMap<K, V> implements Serializable {
		public SingletonMap(K key, V value) {
		}
	}

	public static class SingletonSet<E> extends AbstractSet<E> implements Serializable {
		private static final long serialVersionUID = -6326316058851840405L;

		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final E element;

		public SingletonSet(E element) {
			this.element = element;
		}

		@Override
		public boolean contains(Object object) {
			return object == this.element || object != null && object.equals(this.element);
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Set) {
				Set set = (Set) object;

				if (set.size() == 1) {
					Object element = set.iterator().next();

					return element == this.element ||
						   element != null &&
						   element.equals(this.element);
				}
			}

			return false;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			consumer.accept(this.element);
		}

		@Override
		public int hashCode() {
			return this.element == null ? 0 : this.element.hashCode();
		}

		@Override
		public SingletonIterator<E> iterator() {
			return new SingletonIterator(this.element);
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public SingletonSpliterator<E> spliterator() {
			return new SingletonSpliterator(this.element);
		}

		@Override
		public String toString() {
			return "[" + this.element + "]";
		}
	}

	public static class SingletonSpliterator<E> implements Spliterator<E> {
		protected final E element;
		protected long size = 1;

		public SingletonSpliterator(E element) {
			this.element = element;
		}

		@Override
		public int characteristics() {
			return (this.element == null ? 0 : Spliterator.NONNULL) |
				   Spliterator.SIZED |
				   Spliterator.SUBSIZED |
				   Spliterator.IMMUTABLE |
				   Spliterator.DISTINCT |
				   Spliterator.ORDERED;
		}

		@Override
		public long estimateSize() {
			return this.size;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			if (this.size > 0) {
				this.size--;
				consumer.accept(this.element);
			}
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.size;
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			if (this.size > 0) {
				this.size--;
				consumer.accept(this.element);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator<E> trySplit() {
			return null;
		}
	}

	public static class SynchronizedCollection<E> implements Collection<E>, Serializable {
		protected final Collection<E> collection;
		protected final Object mutex;

		public SynchronizedCollection(Collection<E> collection) {
			Objects.requireNonNull(collection, "collection");
			this.collection = collection;
			this.mutex = this;
		}

		public SynchronizedCollection(Collection<E> collection, Object mutex) {
			Objects.requireNonNull(collection, "collection");
			Objects.requireNonNull(mutex, "mutex");
			this.collection = collection;
			this.mutex = mutex;
		}
	}

	public static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
		protected final List<E> list;

		public SynchronizedList(List<E> list) {
			super(list);
			this.list = list;
		}

		public SynchronizedList(List<E> list, Object mutex) {
			super(list, mutex);
			this.list = list;
		}
	}

	public static class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
		protected final Map<K, V> map;
		protected final Object mutex;

		public SynchronizedMap(Map<K, V> map) {
			Objects.requireNonNull(map, "map");
			this.map = map;
			this.mutex = this;
		}

		public SynchronizedMap(Map<K, V> map, Object mutex) {
			Objects.requireNonNull(map, "map");
			Objects.requireNonNull(mutex, "mutex");
			this.map = map;
			this.mutex = mutex;
		}
	}

	public static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
		public SynchronizedNavigableMap(NavigableMap<K, V> map) {
		}

		public SynchronizedNavigableMap(NavigableMap<K, V> map, Object mutex) {
		}
	}

	public static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
		public SynchronizedNavigableSet(NavigableSet<E> set) {
		}

		public SynchronizedNavigableSet(NavigableSet<E> set, Object mutex) {
		}
	}

	public static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
		public SynchronizedRandomAccessList(List<E> list) {
			super(list);
		}

		public SynchronizedRandomAccessList(List<E> list, Object mutex) {
			super(list, mutex);
		}
	}

	public static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
		public SynchronizedSet(Set<E> set) {
		}

		public SynchronizedSet(Set<E> set, Object mutex) {
		}
	}

	public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
		public SynchronizedSortedMap(SortedMap<K, V> map) {
		}

		public SynchronizedSortedMap(SortedMap<K, V> map, Object mutex) {
		}
	}

	public static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
		public SynchronizedSortedSet(SortedSet<E> set) {
		}

		public SynchronizedSortedSet(SortedSet<E> set, Object mutex) {
		}
	}

	public static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
		public UnmodifiableCollection(Collection<E> collection) {
		}
	}

	public static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
		public UnmodifiableList(List<E> list) {
		}
	}

	public static class UnmodifiableMap<K, V> implements Map<K, V>, Serializable {
		public UnmodifiableMap(Map<K, V> map) {
		}
	}

	public static class UnmodifiableNavigableMap<K, V> extends UnmodifiableSortedMap<K, V> implements NavigableMap<K, V> {
		public UnmodifiableNavigableMap(NavigableMap<K, V> map) {
		}
	}

	public static class UnmodifiableNavigableSet<E> extends UnmodifiableSortedSet<E> implements NavigableSet<E> {
		public UnmodifiableNavigableSet(NavigableSet<E> set) {
		}
	}

	public static class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {
		public UnmodifiableSet(Set<E> set) {
		}
	}

	public static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements SortedMap<K, V> {
		public UnmodifiableSortedMap(SortedMap<K, V> map) {
		}
	}

	public static class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E> {
		public UnmodifiableSortedSet(SortedSet<E> set) {
		}
	}
}
//	/**
//	 * An unmodifiable-group backed by another group.
//	 *
//	 * @param <E> the type of the elements this group holds.
//	 */
//	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
//	@Deprecated
//	public static final class UnmodifiableGroup<E> implements Group<E> {
//		/**
//		 * The group backing this unmodifiable-group.
//		 */
//		private final Group<E> group;
//
//		/**
//		 * Construct a new unmodifiable-group backed by the given {@code group}.
//		 *
//		 * @param group the group backing the constructed unmodifiable-group.
//		 * @throws NullPointerException if the given {@code group} is null.
//		 */
//		private UnmodifiableGroup(Group<E> group) {
//			Objects.requireNonNull(group, "group");
//			this.group = group;
//		}
//
//		@Override
//		public boolean add(E element) {
//			throw new UnsupportedOperationException("add");
//		}
//
//		@Override
//		public boolean addAll(Collection<? extends E> collection) {
//			throw new UnsupportedOperationException("addAll");
//		}
//
//		@Override
//		public void clear() {
//			throw new UnsupportedOperationException("clear");
//		}
//
//		@Override
//		public boolean contains(Object object) {
//			return this.group.contains(object);
//		}
//
//		@Override
//		public boolean containsAll(Collection<?> collection) {
//			return this.group.containsAll(collection);
//		}
//
//		@Override
//		public boolean equals(Object object) {
//			return this.group.equals(object);
//		}
//
//		@Override
//		public void forEach(Consumer<? super E> consumer) {
//			this.group.forEach(consumer);
//		}
//
//		@Override
//		public int hashCode() {
//			return this.group.hashCode();
//		}
//
//		@Override
//		public boolean isEmpty() {
//			return this.group.isEmpty();
//		}
//
//		@Override
//		public UnmodifiableGroupIterator iterator() {
//			return new UnmodifiableGroupIterator();
//		}
//
//		@Override
//		public boolean remove(Object object) {
//			throw new UnsupportedOperationException("remove");
//		}
//
//		@Override
//		public boolean removeAll(Collection<?> collection) {
//			throw new UnsupportedOperationException("removeAll");
//		}
//
//		@Override
//		public boolean removeIf(Predicate<? super E> predicate) {
//			throw new UnsupportedOperationException("removeIf");
//		}
//
//		@Override
//		public boolean retainAll(Collection<?> collection) {
//			throw new UnsupportedOperationException("retainAll");
//		}
//
//		@Override
//		public int size() {
//			return this.group.size();
//		}
//
//		@Override
//		public Group<E> subGroup(Object key, Predicate<E> predicate) {
//			return null;
////			return CollectionUtil.unmodifiableGroup(this.group.subGroup(key, predicate));
//		}
//
//		@Override
//		public Group<E> subGroup(Object key) {
//			return null;
////			return CollectionUtil.unmodifiableGroup(this.group.subGroup(key));
//		}
//
//		@Override
//		public Object[] toArray() {
//			return this.group.toArray();
//		}
//
//		@Override
//		public Object[] toArray(Object[] array) {
//			return this.group.toArray(array);
//		}
//
//		@Override
//		public String toString() {
//			return this.group.toString();
//		}
//
//		/**
//		 * An iterator for unmodifiable-groups.
//		 */
//		public final class UnmodifiableGroupIterator implements Iterator<E> {
//			/**
//			 * The backing iterator.
//			 */
//			private final Iterator<E> iterator =/* UnmodifiableGroup.this.group.iterator()*/ null;
//
//			/**
//			 * Construct a new unmodifiable iterator.
//			 */
//			private UnmodifiableGroupIterator() {
//			}
//
//			@Override
//			public boolean hasNext() {
//				return this.iterator.hasNext();
//			}
//
//			@Override
//			public E next() {
//				return this.iterator.next();
//			}
//		}
//	}

//	/**
//	 * Get a group that reads directly from the given group, but can't modify it.
//	 *
//	 * @param group to get an unmodifiable group for.
//	 * @param <T>   the type of the elements on given group.
//	 * @return an unmodifiable group for the given group.
//	 * @throws NullPointerException if the given {@code group} is null.
//	 */
//	@Deprecated
//	public static <T> Group<T> unmodifiableGroup(Group<T> group) {
//		Objects.requireNonNull(group, "group");
//		return new UnmodifiableGroup(group);
//	}
