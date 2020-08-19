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
import java.util.stream.StreamSupport;

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
	public static final EmptyQueue EMPTY_QUEUE = new EmptyQueue();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySet EMPTY_SET = new EmptySet();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySortedMap EMPTY_SORTED_MAP = new EmptySortedMap();
	@SuppressWarnings("PublicStaticCollectionField")
	public static final EmptySortedSet EMPTY_SORTED_SET = new EmptyNavigableSet();
	public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	protected CollectionUtil() {
		throw new AssertionError("No instance for you!");
	}

	//misc

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

	//checked

	public static <T> CheckedCollection<T> checkedCollection(Collection<T> collection, Class<T> type) {
		return new CheckedCollection(collection, type);
	}

	public static <T> CheckedEnumeration<T> checkedEnumeration(Enumeration<T> enumeration) {
		return new CheckedEnumeration(enumeration);
	}

	public static <T> CheckedIterator<T> checkedIterator(Iterator<T> iterator) {
		return new CheckedIterator(iterator);
	}

	public static <T> CheckedList<T> checkedList(List<T> list, Class<T> type) {
		return list instanceof RandomAccess ?
			   new CheckedRandomAccessList(list, type) :
			   new CheckedList(list, type);
	}

	public static <T> CheckedListIterator<T> checkedListIterator(ListIterator<T> iterator, Class<T> type) {
		return new CheckedListIterator(iterator, type);
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

	public static <T> CheckedSpliterator<T> checkedSpliterator(Spliterator<T> spliterator) {
		return new CheckedSpliterator(spliterator);
	}

	//misc

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

	//empty

	/**
	 * Get an empty collection.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty collection.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyCollection<T> emptyCollection() {
		return CollectionUtil.EMPTY_COLLECTION;
	}

	/**
	 * Get an empty enumeration.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty enumeration.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyEnumeration<T> emptyEnumeration() {
		return CollectionUtil.EMPTY_ENUMERATION;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyIterator<T> emptyIterator() {
		return CollectionUtil.EMPTY_ITERATOR;
	}

	/**
	 * Get an empty list.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty list.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyList<T> emptyList() {
		return CollectionUtil.EMPTY_LIST;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyListIterator<T> emptyListIterator() {
		return CollectionUtil.EMPTY_LIST_ITERATOR;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> EmptyMap<K, V> emptyMap() {
		return CollectionUtil.EMPTY_MAP;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> EmptyNavigableMap<K, V> emptyNavigableMap() {
		return CollectionUtil.EMPTY_NAVIGABLE_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyNavigableSet<T> emptyNavigableSet() {
		return CollectionUtil.EMPTY_NAVIGABLE_SET;
	}

	/**
	 * Get an empty queue.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty queue.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptyQueue<T> emptyQueue() {
		return CollectionUtil.EMPTY_QUEUE;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptySet<T> emptySet() {
		return CollectionUtil.EMPTY_SET;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> EmptySortedMap<K, V> emptySortedMap() {
		return CollectionUtil.EMPTY_SORTED_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptySortedSet<T> emptySortedSet() {
		return CollectionUtil.EMPTY_SORTED_SET;
	}

	/**
	 * Get an empty spliterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty spliterator.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> EmptySpliterator<T> emptySpliterator() {
		return CollectionUtil.EMPTY_SPLITERATOR;
	}

	//misc

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

	//singleton

	/**
	 * Get a set that has only the given {@code element}.
	 *
	 * @param element the only element the returned set will have.
	 * @param <T>     the type of the element.
	 * @return a set with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonSet<T> singleton(T element) {
		return new SingletonSet(element);
	}

	/**
	 * Get a collection that has only the given {@code element}.
	 *
	 * @param element the only element the returned collection will have.
	 * @param <T>     the type of the element.
	 * @return a collection with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonCollection<T> singletonCollection(T element) {
		return new SingletonCollection(element);
	}

	/**
	 * Get an enumeration with only the given {@code element}.
	 *
	 * @param element the only element the returned enumeration will have.
	 * @param <T>     the type of the element.
	 * @return an enumeration with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonEnumeration<T> singletonEnumeration(T element) {
		return new SingletonEnumeration(element);
	}

	/**
	 * Get an iterator with only the given {@code element}.
	 *
	 * @param element the only element the returned iterator will have.
	 * @param <T>     the type of the element.
	 * @return an iterator with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonIterator<T> singletonIterator(T element) {
		return new SingletonIterator(element);
	}

	/**
	 * Get a list that has only the given {@code element}.
	 *
	 * @param element the only element the returned list will have.
	 * @param <T>     the type of the element.
	 * @return a list with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonList<T> singletonList(T element) {
		return new SingletonList(element);
	}

	/**
	 * Get a iterator that has only the given {@code element}.
	 *
	 * @param element the only element the returned iterator will have.
	 * @param <T>     the type of the element.
	 * @return a iterator with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonListIterator<T> singletonListIterator(T element) {
		return new SingletonListIterator(element);
	}

	/**
	 * Get a map that has only the given {@code key} and {@code value} pair.
	 *
	 * @param key   the key of the only pair in the returned map.
	 * @param value the value of the only pair in the returned map.
	 * @param <K>   the type of hte keys.
	 * @param <V>   the type of the values
	 * @return a map with only the given {@code key} and {@code value} pair.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> SingletonMap<K, V> singletonMap(K key, V value) {
		return new SingletonMap(key, value);
	}

	/**
	 * Get an spliterator with only the given {@code element}.
	 *
	 * @param element the only element the returned spliterator will have.
	 * @param <T>     the type of the element.
	 * @return an spliterator with only the given {@code element}.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> SingletonSpliterator<T> singletonSpliterator(T element) {
		return new SingletonSpliterator(element);
	}

	//misc

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

	//synchronized

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

	//unmodifiable

	/**
	 * Construct an unmodifiable view of the given {@code collection}.
	 *
	 * @param collection the collection to get an unmodifiable view of.
	 * @param <T>        the type of the elements.
	 * @return an unmodifiable view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableCollection<T> unmodifiableCollection(Collection<? extends T> collection) {
		return new UnmodifiableCollection(collection);
	}

	/**
	 * Construct an unmodifiable view of the given {@code enumeration}.
	 *
	 * @param enumeration the enumeration to get an unmodifiable view of.
	 * @param <T>         the type of the elements.
	 * @return an unmodifiable view of the given {@code enumeration}.
	 * @throws NullPointerException if the given {@code enumeration} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableEnumeration<T> unmodifiableEnumeration(Enumeration<T> enumeration) {
		return new UnmodifiableEnumeration(enumeration);
	}

	/**
	 * Construct an unmodifiable view of the given {@code iterator}.
	 *
	 * @param iterator the iterator to get an unmodifiable view of.
	 * @param <T>      the type of the elements.
	 * @return an unmodifiable view of the given {@code iterator}.
	 * @throws NullPointerException if the given {@code iterator} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableIterator<T> unmodifiableIterator(Iterator<T> iterator) {
		return new UnmodifiableIterator(iterator);
	}

	/**
	 * Construct an unmodifiable view of the given {@code list}.
	 *
	 * @param list the list to get an unmodifiable view of.
	 * @param <T>  the type of the elements.
	 * @return an unmodifiable view of the given {@code list}.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableList<T> unmodifiableList(List<? extends T> list) {
		return list instanceof RandomAccess ?
			   new UnmodifiableRandomAccessList(list) :
			   new UnmodifiableList(list);
	}

	/**
	 * Construct an unmodifiable view of the given {@code iterator}.
	 *
	 * @param iterator the iterator to get an unmodifiable view of.
	 * @param <T>      the type of the elements.
	 * @return an unmodifiable view of the given {@code iterator}.
	 * @throws NullPointerException if the given {@code iterator} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableListIterator<T> unmodifiableListIterator(ListIterator<T> iterator) {
		return new UnmodifiableListIterator(iterator);
	}

	/**
	 * Construct an unmodifiable view of the given {@code map}.
	 *
	 * @param map the map to get an unmodifiable view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an unmodifiable view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> UnmodifiableMap<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
		return new UnmodifiableMap(map);
	}

	/**
	 * Construct an unmodifiable view of the given {@code map}.
	 *
	 * @param map the map to get an unmodifiable view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an unmodifiable view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> map) {
		return new UnmodifiableNavigableMap(map);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableNavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> set) {
		return new UnmodifiableNavigableSet(set);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableSet<T> unmodifiableSet(Set<T> set) {
		return new UnmodifiableSet(set);
	}

	/**
	 * Construct an unmodifiable view of the given {@code map}.
	 *
	 * @param map the map to get an unmodifiable view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an unmodifiable view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <K, V> UnmodifiableSortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> map) {
		return new UnmodifiableSortedMap(map);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableSortedSet<T> unmodifiableSortedSet(SortedSet<T> set) {
		return new UnmodifiableSortedSet(set);
	}

	/**
	 * Construct an unmodifiable view of the given {@code spliterator}.
	 *
	 * @param spliterator the spliterator to get an unmodifiable view of.
	 * @param <T>         the type of the elements.
	 * @return an unmodifiable view of the given {@code spliterator}.
	 * @throws NullPointerException if the given {@code spliterator} is null.
	 * @since 0.1.5 ~2020.08.19
	 */
	public static <T> UnmodifiableSpliterator<T> unmodifiableSpliterator(Spliterator<T> spliterator) {
		return new UnmodifiableSpliterator(spliterator);
	}

	//misc

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

			this.list.addAll(java.util.Collections.nCopies(index - size, null));
			this.list.add(value);
			return null;
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
			if (index > allowed)
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

	//checked

	/**
	 * A collection wrapping another collection. Preventing that other collection from storing an
	 * element with an unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -4080006260960608086L;

		/**
		 * The wrapped collection.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Collection<E> collection;
		/**
		 * The type of the allowed elements to be added to the wrapped collection.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Class<E> type;

		/**
		 * Construct a new collection wrapping the given {@code collection} with restricted
		 * element-addition to the given {@code type}.
		 *
		 * @param collection the collection to be wrapped.
		 * @param type       the type of the allowed elements to be added to the wrapped
		 *                   collection.
		 * @throws NullPointerException if the given {@code collection} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedCollection(Collection<E> collection, Class<E> type) {
			Objects.requireNonNull(collection, "collection");
			Objects.requireNonNull(type, "type");
			this.collection = collection;
			this.type = type;
		}

		@Override
		public boolean add(E element) {
			return this.collection.add(this.checkedElement(element));
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			return this.collection.addAll(this.checkedCollection(collection));
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
		public CheckedIterator<E> iterator() {
			return new CheckedIterator(this.collection.iterator());
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
		public CheckedSpliterator<E> spliterator() {
			return new CheckedSpliterator(this.collection.spliterator());
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

		/**
		 * Make a checked copy of the given {@code collection}.
		 *
		 * @param collection the collection to make a checked copy of.
		 * @return a checked copy of the given {@code collection}.
		 * @throws NullPointerException if the given {@code collection} is null.
		 * @throws ClassCastException   if the given {@code collection} contain any element that is
		 *                              neither null nor an instance of the allowed type in this
		 *                              wrapping collection.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected Collection<E> checkedCollection(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");

			try {
				return (Collection<E>) Array.of(collection, this.type).list();
			} catch (ArrayStoreException e) {
				throw new ClassCastException(e.getMessage());
			}
		}

		/**
		 * Get the given {@code element} after checking its type.
		 *
		 * @param element the element to be checked then returned.
		 * @return the given {@code element}.
		 * @throws ClassCastException if the given {@code element} is neither null nor an instance
		 *                            of the allowed type in this wrapping collection.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected E checkedElement(E element) {
			return this.type.cast(element);
		}
	}

	/**
	 * An enumeration wrapping another enumeration. Preventing that other enumeration from been
	 * accessed in an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedEnumeration<E> implements Enumeration<E> {
		/**
		 * The wrapped enumeration.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Enumeration<E> enumeration;

		/**
		 * Construct a new enumeration wrapping the given {@code enumeration}.
		 *
		 * @param enumeration the enumeration to be wrapped.
		 * @throws NullPointerException if the given {@code enumeration} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedEnumeration(Enumeration<E> enumeration) {
			Objects.requireNonNull(enumeration, "enumeration");
			this.enumeration = enumeration;
		}

		@Override
		public boolean hasMoreElements() {
			return this.enumeration.hasMoreElements();
		}

		@Override
		public E nextElement() {
			return this.enumeration.nextElement();
		}
	}

	/**
	 * An iterator wrapping another iterator. Preventing that other iterator from been accessed in
	 * an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedIterator<E> implements Iterator<E> {
		/**
		 * The wrapped iterator.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Iterator<E> iterator;

		/**
		 * Construct a new iterator wrapping the given {@code iterator}.
		 *
		 * @param iterator the iterator to be wrapped.
		 * @throws NullPointerException if the given {@code iterator} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedIterator(Iterator<E> iterator) {
			Objects.requireNonNull(iterator, "iterator");
			this.iterator = iterator;
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
		public E next() {
			return this.iterator.next();
		}

		@Override
		public void remove() {
			this.iterator.remove();
		}
	}

	/**
	 * A list wrapping another list. Preventing that other list from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedList<E> extends CheckedCollection<E> implements List<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8220369572278063061L;

		/**
		 * The wrapped list (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final List<E> list;

		/**
		 * Construct a new list wrapping the given {@code list} with restricted element-addition to
		 * the given {@code type}.
		 *
		 * @param list the list to be wrapped.
		 * @param type the type of the allowed elements to be added to the wrapped list.
		 * @throws NullPointerException if the given {@code list} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedList(List<E> list, Class<E> type) {
			super(list, type);
			this.list = list;
		}

		@Override
		public void add(int index, E element) {
			this.list.add(index, this.checkedElement(element));
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			return this.list.addAll(index, this.checkedCollection(collection));
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
		public CheckedListIterator<E> listIterator() {
			return new CheckedListIterator(this.list.listIterator(), this.type);
		}

		@Override
		public CheckedListIterator listIterator(int index) {
			return new CheckedListIterator(this.list.listIterator(index), this.type);
		}

		@Override
		public E remove(int index) {
			return this.list.remove(index);
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
			this.list.replaceAll(element -> this.checkedElement(operator.apply(element)));
		}

		@Override
		public E set(int index, E element) {
			return this.list.set(index, this.checkedElement(element));
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			this.list.sort(comparator);
		}

		@Override
		public CheckedList<E> subList(int beginIndex, int endIndex) {
			return new CheckedList(this.list.subList(beginIndex, endIndex), this.type);
		}
	}

	/**
	 * An iterator wrapping another iterator. Preventing that other iterator from storing an element
	 * with an unappropriated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedListIterator<E> extends CheckedIterator<E> implements ListIterator<E> {
		/**
		 * The wrapped iterator (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final ListIterator<E> iterator;
		/**
		 * The type of the allowed elements to be added to the wrapped iterator.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Class<E> type;

		/**
		 * Construct a new iterator wrapping the given {@code iterator}.  with restricted
		 * element-addition to the given {@code type}.
		 *
		 * @param iterator the iterator to be wrapped.
		 * @param type     the type of the allowed elements to be added to the wrapped iterator.
		 * @throws NullPointerException if the given {@code iterator} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedListIterator(ListIterator<E> iterator, Class<E> type) {
			super(iterator);
			Objects.requireNonNull(type, "type");
			this.iterator = iterator;
			this.type = type;
		}

		@Override
		public void add(E element) {
			this.iterator.add(this.checkedElement(element));
		}

		@Override
		public boolean hasPrevious() {
			return this.iterator.hasPrevious();
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
		public void set(E element) {
			this.iterator.set(this.checkedElement(element));
		}

		/**
		 * Get the given {@code element} after checking its type.
		 *
		 * @param element the element to be checked then returned.
		 * @return the given {@code element}.
		 * @throws ClassCastException if the given {@code element} is neither null nor an instance
		 *                            of the allowed type in this wrapping iterator.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected E checkedElement(E element) {
			return this.type.cast(element);
		}
	}

	/**
	 * A map wrapping another map. Preventing that other map from storing an element with an
	 * unappreciated type.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedMap<K, V> implements Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8504055594508288742L;

		/**
		 * The type required for a key to be put.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Class<K> keyType;
		/**
		 * The wrapped map.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Map<K, V> map;
		/**
		 * The type required for a value to be put.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Class<V> valueType;

		/**
		 * Construct a new map wrapping the given {@code map}. The constructed map will only allow
		 * keys with the given {@code keyType} and only the values with the given {@code
		 * valueType}.
		 *
		 * @param map       the map to be wrapped.
		 * @param keyType   the type required for a key to be put.
		 * @param valueType the type required for a value to be put.
		 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code
		 *                              valueType} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			return this.map.compute(
					this.checkedKey(key),
					(k, v) -> this.checkedValue(function.apply(k, v))
			);
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			return this.map.computeIfAbsent(
					this.checkedKey(key),
					k -> this.checkedValue(function.apply(k))
			);
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			return this.map.computeIfPresent(
					this.checkedKey(key),
					(k, v) -> this.checkedValue(function.apply(k, v))
			);
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
			return new CheckedEntrySet(this.map.entrySet(), this.valueType);
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.map.equals(object);
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			this.map.forEach(consumer);
		}

		@Override
		public V get(Object key) {
			return this.map.get(key);
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
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
		public CheckedSet<K> keySet() {
			return new CheckedSet(this.map.keySet(), this.keyType);
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			return this.map.merge(
					this.checkedKey(key),
					this.checkedValue(value),
					(k, v) -> this.checkedValue(function.apply(k, v))
			);
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
			Objects.requireNonNull(map, "map");
			this.map.putAll(this.checkedMap(map));
		}

		@Override
		public V putIfAbsent(K key, V value) {
			return this.map.putIfAbsent(
					this.checkedKey(key),
					this.checkedValue(value)
			);
		}

		@Override
		public boolean remove(Object key, Object value) {
			return this.map.remove(key, value);
		}

		@Override
		public V remove(Object key) {
			return this.map.remove(key);
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			return this.map.replace(
					this.checkedKey(key),
					oldValue,
					this.checkedValue(newValue)
			);
		}

		@Override
		public V replace(K key, V value) {
			return this.map.replace(
					this.checkedKey(key),
					this.checkedValue(value)
			);
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			this.map.replaceAll((key, value) -> this.checkedValue(function.apply(key, value)));
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
		public CheckedCollection<V> values() {
			return new CheckedCollection(this.map.values(), this.valueType);
		}

		/**
		 * Return the given {@code key} after casting it to the keyType of this map.
		 *
		 * @param key the key to be casted then returned.
		 * @return the given {@code key}.
		 * @throws ClassCastException if the given {@code key} is neither null nor an instance of
		 *                            the keyType of this map.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected K checkedKey(K key) {
			return this.keyType.cast(key);
		}

		/**
		 * Return a checked copy of the given {@code map}.
		 *
		 * @param map the map to make a checked copy of.
		 * @return a checked copy of the given {@code map}.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @throws ClassCastException   if the given {@code map} contained any key or value that is
		 *                              neither a null nor an instance of the specified type for it
		 *                              in this map.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected Map<K, V> checkedMap(Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(map, "map");

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

		/**
		 * Return the given {@code value} after casting it to the valueType of this map.
		 *
		 * @param value the value to be casted then returned.
		 * @return the given {@code value}.
		 * @throws ClassCastException if the given {@code value} is neither null nor an instance of
		 *                            the valueType of this map.
		 * @since 0.1.5 ~2020.08.19
		 */
		protected V checkedValue(V value) {
			return this.valueType.cast(value);
		}

		/**
		 * An entry wrapping another entry. Preventing that other entry from storing a value with an
		 * unappreciated type.
		 *
		 * @param <K> the type of the key.
		 * @param <V> the type of the value.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class CheckedEntry<K, V> implements Map.Entry<K, V>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 6060443667289298487L;

			/**
			 * The wrapped entry.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Map.Entry<K, V> entry;
			/**
			 * The type required for a value to be set.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Class<V> valueType;

			/**
			 * Construct a new entry wrapping the given {@code entry}. The constructed entry will
			 * only allow values with the given {@code valueType}.
			 *
			 * @param entry     the entry to be wrapped.
			 * @param valueType hte type required for a value to be put.
			 * @throws NullPointerException if the given {@code entry} or {@code valueType} is
			 *                              null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public CheckedEntry(Entry<K, V> entry, Class<V> valueType) {
				Objects.requireNonNull(entry, "entry");
				Objects.requireNonNull(valueType, "valueType");
				this.entry = entry;
				this.valueType = valueType;
			}

			@Override
			public boolean equals(Object object) {
				return object == this ||
					   object instanceof Entry &&
					   //todo why SimpleImmutableEntry?
					   this.entry.equals(new AbstractMap.SimpleImmutableEntry((Entry) object));
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
				return this.entry.setValue(this.checkedValue(value));
			}

			@Override
			public String toString() {
				return this.entry.toString();
			}

			/**
			 * Return the given {@code value} after casting it to the valueType of this entry.
			 *
			 * @param value the value to be casted then returned.
			 * @return the given {@code value}.
			 * @throws ClassCastException if the given {@code value} is neither null nor an instance
			 *                            of the valueType of this entry.
			 * @since 0.1.5 ~2020.08.19
			 */
			protected V checkedValue(V value) {
				return this.valueType.cast(value);
			}
		}

		/**
		 * An iterator wrapping another iterator. Wrapping each entry in the other iterator.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class CheckedEntryIterator<K, V> implements Iterator<Entry<K, V>> {
			/**
			 * The wrapped iterator.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Iterator<Entry<K, V>> iterator;
			/**
			 * The class required for the values.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Class<V> valueType;

			/**
			 * Construct a new iterator wrapping the given {@code iterator}. The constructed
			 * iterator will wrap the entries returned from the given {@code iterator}.
			 *
			 * @param iterator  the iterator to be wrapped.
			 * @param valueType the class required for the values.
			 * @throws NullPointerException if the given {@code iterator} or {@code valueType} is
			 *                              null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public CheckedEntryIterator(Iterator<Entry<K, V>> iterator, Class<V> valueType) {
				Objects.requireNonNull(iterator, "iterator");
				Objects.requireNonNull(valueType, "valueType");
				this.iterator = iterator;
				this.valueType = valueType;
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				this.iterator.forEachRemaining(entry -> consumer.accept(new CheckedEntry(entry, this.valueType)));
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public CheckedEntry<K, V> next() {
				return new CheckedEntry(this.iterator.next(), this.valueType);
			}

			@Override
			public void remove() {
				this.iterator.remove();
			}
		}

		/**
		 * An entry set that wraps another entry set. Care should be taken since the entries is a
		 * sensitive area in a map and a user reaching a raw entry will break the 'checked'
		 * property.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class CheckedEntrySet<K, V> implements Set<Entry<K, V>>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -2054578456248104847L;

			/**
			 * The wrapped entry set.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Set<Entry<K, V>> entrySet;
			/**
			 * The class required for the values.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Class<V> valueType;

			/**
			 * Construct a new set wrapping the given {@code entrySet} with restricted
			 * values-assignment to the given {@code valueType}.
			 *
			 * @param entrySet  the set to be wrapped.
			 * @param valueType the type of the allowed values to be set to the wrapped entries.
			 * @throws NullPointerException if the given {@code entrySet} or {@code valueType} is
			 *                              null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public CheckedEntrySet(Set<Entry<K, V>> entrySet, Class<V> valueType) {
				Objects.requireNonNull(entrySet, "entrySet");
				Objects.requireNonNull(valueType, "valueType");
				this.entrySet = entrySet;
				this.valueType = valueType;
			}

			@Override
			public boolean add(Entry<K, V> element) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends Entry<K, V>> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public void clear() {
				this.entrySet.clear();
			}

			@Override
			public boolean contains(Object object) {
				return object instanceof CheckedEntry ?
					   this.entrySet.contains(object) :
					   object instanceof Entry &&
					   this.entrySet.contains(new CheckedEntry((Entry) object, this.valueType));
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

					return set.size() == this.entrySet.size() &&
						   this.containsAll(set);
				}

				return false;
			}

			@Override
			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.entrySet.forEach(entry -> consumer.accept(new CheckedEntry(entry, this.valueType)));
			}

			@Override
			public int hashCode() {
				return this.entrySet.hashCode();
			}

			@Override
			public boolean isEmpty() {
				return this.entrySet.isEmpty();
			}

			@Override
			public CheckedEntryIterator<K, V> iterator() {
				return new CheckedEntryIterator(this.entrySet.iterator(), this.valueType);
			}

			@Override
			public Stream<Entry<K, V>> parallelStream() {
				return StreamSupport.stream(new CheckedEntrySpliterator(
						this.entrySet.spliterator(),
						this.valueType
				), true);
			}

			@Override
			public boolean remove(Object object) {
				//todo why SimpleImmutableEntry?
				return object instanceof Entry &&
					   this.entrySet.remove(new AbstractMap.SimpleImmutableEntry((Entry) object));
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				boolean modified = false;

				Iterator iterator = this.iterator();
				while (iterator.hasNext())
					if (collection.contains(iterator.next())) {
						iterator.remove();
						modified = true;
					}

				return modified;
			}

			@Override
			public boolean removeIf(Predicate<? super Entry<K, V>> predicate) {
				Objects.requireNonNull(predicate, "predicate");
				return this.entrySet.removeIf(entry -> predicate.test(new CheckedEntry(entry, this.valueType)));
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				boolean modified = false;

				Iterator iterator = this.iterator();
				while (iterator.hasNext()) {
					if (collection.contains(iterator.next()))
						continue;

					iterator.remove();
					modified = true;
				}

				return modified;
			}

			@Override
			public int size() {
				return this.entrySet.size();
			}

			@Override
			public CheckedEntrySpliterator<K, V> spliterator() {
				return new CheckedEntrySpliterator(
						this.entrySet.spliterator(),
						this.valueType
				);
			}

			@Override
			public Stream<Entry<K, V>> stream() {
				return StreamSupport.stream(new CheckedEntrySpliterator(
						this.entrySet.spliterator(),
						this.valueType
				), false);
			}

			@Override
			public Object[] toArray() {
				Object[] array = this.entrySet.toArray();
				Object[] product = array;

				if (!array.getClass().isAssignableFrom(CheckedEntry[].class))
					product = new Object[array.length];

				for (int i = 0; i < array.length; i++)
					product[i] = new CheckedEntry((Entry) array[i], this.valueType);

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Object[] product = this.entrySet.toArray(
						array.length == 0 ?
						array : Arrays.copyOf(array, 0)
				);

				for (int i = 0; i < product.length; i++)
					product[i] = new CheckedEntry((Entry) product[i], this.valueType);

				if (array.length < product.length)
					return (T[]) product;
				if (array.length > product.length)
					array[product.length] = null;

				System.arraycopy(
						product,
						0,
						array,
						0,
						product.length
				);

				return array;
			}

			@Override
			public String toString() {
				return this.entrySet.toString();
			}
		}

		/**
		 * A spliterator wrapping another spliterator. Wrapping each entry in the other
		 * spliterator.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class CheckedEntrySpliterator<K, V> implements Spliterator<Entry<K, V>> {
			/**
			 * The wrapped spliterator.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Spliterator<Entry<K, V>> spliterator;
			/**
			 * The class required for the values.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Class<V> valueType;

			/**
			 * Construct a new spliterator wrapping the given {@code spliterator}. The constructed
			 * spliterator will wrap the entries returned from the given {@code spliterator}.
			 *
			 * @param spliterator the spliterator to be wrapped.
			 * @param valueType   the class required for the values.
			 * @throws NullPointerException if the given {@code spliterator} or {@code valueType} is
			 *                              null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public CheckedEntrySpliterator(Spliterator<Entry<K, V>> spliterator, Class<V> valueType) {
				Objects.requireNonNull(spliterator, "spliterator");
				Objects.requireNonNull(valueType, "valueType");
				this.spliterator = spliterator;
				this.valueType = valueType;
			}

			@Override
			public int characteristics() {
				return this.spliterator.characteristics();
			}

			@Override
			public long estimateSize() {
				return this.spliterator.estimateSize();
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				this.spliterator.forEachRemaining(entry -> consumer.accept(new CheckedEntry(entry, this.valueType)));
			}

			@Override
			public Comparator<? super Entry<K, V>> getComparator() {
				return this.spliterator.getComparator();
			}

			@Override
			public long getExactSizeIfKnown() {
				return this.spliterator.getExactSizeIfKnown();
			}

			@Override
			public boolean hasCharacteristics(int characteristics) {
				return this.spliterator.hasCharacteristics(characteristics);
			}

			@Override
			public boolean tryAdvance(Consumer<? super Entry<K, V>> consumer) {
				return this.spliterator.tryAdvance(entry -> consumer.accept(new CheckedEntry(entry, this.valueType)));
			}

			@Override
			public CheckedEntrySpliterator<K, V> trySplit() {
				Spliterator<Entry<K, V>> spliterator = this.spliterator.trySplit();
				return spliterator == null ? null :
					   new CheckedEntrySpliterator(spliterator, this.valueType);
			}
		}
	}

	/**
	 * A map wrapping another map. Preventing that other map from storing an element with an
	 * unappreciated type.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedNavigableMap<K, V> extends CheckedSortedMap<K, V> implements NavigableMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8979018726865156296L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final NavigableMap<K, V> map;

		/**
		 * Construct a new map wrapping the given {@code map}. The constructed map will only allow
		 * keys with the given {@code keyType} and only the values with the given {@code
		 * valueType}.
		 *
		 * @param map       the map to be wrapped.
		 * @param keyType   the type required for a key to be put.
		 * @param valueType the type required for a value to be put.
		 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code
		 *                              valueType} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedNavigableMap(NavigableMap<K, V> map, Class<K> keyType, Class<V> valueType) {
			super(map, keyType, valueType);
			this.map = map;
		}

		@Override
		public CheckedEntry<K, V> ceilingEntry(K key) {
			Entry<K, V> entry = this.map.ceilingEntry(key);
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
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
		public CheckedNavigableMap<K, V> descendingMap() {
			return new CheckedNavigableMap(
					this.map.descendingMap(),
					this.keyType,
					this.valueType
			);
		}

		@Override
		public CheckedEntry<K, V> firstEntry() {
			Entry<K, V> entry = this.map.firstEntry();
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
		}

		@Override
		public CheckedEntry<K, V> floorEntry(K key) {
			Entry<K, V> entry = this.map.floorEntry(key);
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
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
		public CheckedEntry<K, V> higherEntry(K key) {
			Entry<K, V> entry = this.map.higherEntry(key);
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
		}

		@Override
		public K higherKey(K key) {
			return this.map.higherKey(key);
		}

		@Override
		public CheckedEntry<K, V> lastEntry() {
			Entry<K, V> entry = this.map.lastEntry();
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
		}

		@Override
		public CheckedEntry<K, V> lowerEntry(K key) {
			Entry<K, V> entry = this.map.lowerEntry(key);
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
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
		public CheckedEntry<K, V> pollFirstEntry() {
			Entry<K, V> entry = this.map.pollFirstEntry();
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
		}

		@Override
		public CheckedEntry<K, V> pollLastEntry() {
			Entry<K, V> entry = this.map.pollLastEntry();
			return entry == null ? null : new CheckedEntry(entry, this.valueType);
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

	/**
	 * A set wrapping another set. Preventing that other set from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedNavigableSet<E> extends CheckedSortedSet<E> implements NavigableSet<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1361875270830495159L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		private final NavigableSet<E> set;

		/**
		 * Construct a new set wrapping the given {@code set} with restricted element-addition to
		 * the given {@code type}.
		 *
		 * @param set  the list to be wrapped.
		 * @param type the type of the allowed elements to be added to the wrapped set.
		 * @throws NullPointerException if the given {@code set} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedNavigableSet(NavigableSet<E> set, Class<E> type) {
			super(set, type);
			this.set = set;
		}

		@Override
		public E ceiling(E element) {
			return this.set.ceiling(element);
		}

		@Override
		public CheckedIterator<E> descendingIterator() {
			return new CheckedIterator(this.set.descendingIterator());
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
		public CheckedNavigableSet<E> headSet(E endElement, boolean inclusive) {
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
		public CheckedNavigableSet<E> tailSet(E beginElement, boolean inclusive) {
			return new CheckedNavigableSet(this.set.tailSet(
					beginElement,
					inclusive
			), this.type);
		}
	}

	/**
	 * A queue wrapping another queue. Preventing that other queue from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedQueue<E> extends CheckedCollection<E> implements Queue<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7115131037976269824L;

		/**
		 * The wrapped queue (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Queue<E> queue;

		/**
		 * Construct a new queue wrapping the given {@code queue} with restricted element-addition
		 * to the given {@code type}.
		 *
		 * @param queue the list to be wrapped.
		 * @param type  the type of the allowed elements to be added to the wrapped queue.
		 * @throws NullPointerException if the given {@code queue} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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
			return this.queue.offer(this.checkedElement(element));
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

	/**
	 * A list wrapping another list. Preventing that other list from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7752208959092863009L;

		/**
		 * Construct a new list wrapping the given {@code list} with restricted element-addition to
		 * the given {@code type}.
		 *
		 * @param list the list to be wrapped.
		 * @param type the type of the allowed elements to be added to the wrapped list.
		 * @throws NullPointerException if the given {@code list} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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

	/**
	 * A set wrapping another set. Preventing that other set from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedSet<E> extends CheckedCollection<E> implements Set<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5155099774357573990L;

		/**
		 * Construct a new set wrapping the given {@code set} with restricted element-addition to
		 * the given {@code type}.
		 *
		 * @param set  the set to be wrapped.
		 * @param type the type of the allowed elements to be added to the wrapped set.
		 * @throws NullPointerException if the given {@code set} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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

	/**
	 * A map wrapping another map. Preventing that other map from storing an element with an
	 * unappreciated type.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedSortedMap<K, V> extends CheckedMap<K, V> implements SortedMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8177693612808969276L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final SortedMap<K, V> map;

		/**
		 * Construct a new map wrapping the given {@code map}. The constructed map will only allow
		 * keys with the given {@code keyType} and only the values with the given {@code
		 * valueType}.
		 *
		 * @param map       the map to be wrapped.
		 * @param keyType   the type required for a key to be put.
		 * @param valueType the type required for a value to be put.
		 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code
		 *                              valueType} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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

	/**
	 * A set wrapping another set. Preventing that other set from storing an element with an
	 * unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 263048283547773050L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final SortedSet<E> set;

		/**
		 * Construct a new set wrapping the given {@code set} with restricted element-addition to
		 * the given {@code type}.
		 *
		 * @param set  the list to be wrapped.
		 * @param type the type of the allowed elements to be added to the wrapped set.
		 * @throws NullPointerException if the given {@code set} or {@code type} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
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

	/**
	 * A spliterator wrapping another spliterator. Preventing that other spliterator from been
	 * accessed in an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class CheckedSpliterator<E> implements Spliterator<E> {
		/**
		 * The wrapped spliterator.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Spliterator<E> spliterator;

		/**
		 * Construct a new spliterator wrapping the given {@code spliterator}.
		 *
		 * @param spliterator the spliterator to be wrapped.
		 * @throws NullPointerException if the given {@code spliterator} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public CheckedSpliterator(Spliterator<E> spliterator) {
			Objects.requireNonNull(spliterator, "spliterator");
			this.spliterator = spliterator;
		}

		@Override
		public int characteristics() {
			return this.spliterator.characteristics();
		}

		@Override
		public long estimateSize() {
			return this.spliterator.estimateSize();
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			this.spliterator.forEachRemaining(consumer);
		}

		@Override
		public Comparator<? super E> getComparator() {
			return this.spliterator.getComparator();
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.spliterator.getExactSizeIfKnown();
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return this.spliterator.hasCharacteristics(characteristics);
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			return this.spliterator.tryAdvance(consumer);
		}

		@Override
		public CheckedSpliterator<E> trySplit() {
			Spliterator<E> spliterator = this.spliterator.trySplit();
			return spliterator == null ? null : new CheckedSpliterator(spliterator);
		}
	}

	//empty -EmptyRandomAccessList

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
		@SuppressWarnings("JavaDoc")
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
		public EmptySpliterator<E> spliterator() {
			return new EmptySpliterator();
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
		@SuppressWarnings("JavaDoc")
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
		@SuppressWarnings("JavaDoc")
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
		@SuppressWarnings("JavaDoc")
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
	 * An empty navigable set. Use {@link #EMPTY_NAVIGABLE_SET} if you do not want to initialize a
	 * new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptyNavigableSet<E> extends EmptySortedSet<E> implements NavigableSet<E> {
		@SuppressWarnings("JavaDoc")
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
	 * An empty queue. Use {@link #EMPTY_QUEUE} if you do not want to initialize a new instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptyQueue<E> extends EmptyCollection<E> implements Queue<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2433176803157950622L;

		@Override
		public E element() {
			throw new NoSuchElementException();
		}

		@Override
		public boolean offer(E element) {
			return false;
		}

		@Override
		public E peek() {
			return null;
		}

		@Override
		public E poll() {
			return null;
		}

		@Override
		public E remove() {
			throw new NoSuchElementException();
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
		@SuppressWarnings("JavaDoc")
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
		@SuppressWarnings("JavaDoc")
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
		@SuppressWarnings("JavaDoc")
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

	/**
	 * An empty spliterator. Use {@link #EMPTY_SPLITERATOR} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class EmptySpliterator<E> implements Spliterator<E> {
		@Override
		public int characteristics() {
			return Spliterator.SIZED | Spliterator.SUBSIZED;
		}

		@Override
		public long estimateSize() {
			return 0;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
		}

		@Override
		public Comparator<? super E> getComparator() {
			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return 0;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics() & characteristics) == characteristics;
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			return false;
		}

		@Override
		public EmptySpliterator<E> trySplit() {
			return null;
		}
	}

	//misc

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
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
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

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class ReverseComparator<T> implements Comparator<T>, Serializable {
		private static final long serialVersionUID = 7199401935207759830L;

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
		private static final long serialVersionUID = -4325561989691359892L;

		public SetFromMap(Map<E, Boolean> map) {
		}

		@Override
		public Iterator<E> iterator() {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}
	}

	//singleton -SingletonNavigableMap -SingletonNavigableSet -SingletonQueue -SingletonRandomAccessList -SingletonSortedMap -SingletonSortedSet

	/**
	 * A collection with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = 876332647859807964L;

		/**
		 * The only single element in this collection.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final E element;

		/**
		 * Construct a new collection with only the given {@code element}.
		 *
		 * @param element the element to be the only element of the constructed collection.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonCollection(E element) {
			this.element = element;
		}

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean contains(Object object) {
			return object == this.element || object != null && object.equals(this.element);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return collection.size() == 1 &&
				   collection.contains(this.element);
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			consumer.accept(this.element);
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public SingletonIterator<E> iterator() {
			return new SingletonIterator(this.element);
		}

		@Override
		public Stream<E> parallelStream() {
			return Stream.of(this.element).parallel();
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
			Objects.requireNonNull(predicate, "predicate");
			if (predicate.test(this.element))
				throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public Spliterator<E> spliterator() {
			return new SingletonSpliterator(this.element);
		}

		@Override
		public Stream<E> stream() {
			return Stream.of(this.element);
		}

		@Override
		public Object[] toArray() {
			return new Object[]{this.element};
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			T[] product = array;

			if (array.length < 1)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), 1);
			else if (array.length > 1)
				product[1] = null;

			product[0] = (T) this.element;
			return product;
		}

		@Override
		public String toString() {
			return "[" + this.element + "]";
		}
	}

	/**
	 * An enumeration with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonEnumeration<E> implements Enumeration<E> {
		/**
		 * The only single element in this enumeration.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final E element;
		/**
		 * True, if this enumeration has more elements.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected boolean hasMoreElements = true;

		/**
		 * Construct a new enumeration with only one element.
		 *
		 * @param element the only element the constructed enumeration will have.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonEnumeration(E element) {
			this.element = element;
		}

		@Override
		public boolean hasMoreElements() {
			return this.hasMoreElements;
		}

		@Override
		public E nextElement() {
			if (this.hasMoreElements) {
				this.hasMoreElements = false;
				return this.element;
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * An iterator with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonIterator<E> implements Iterator<E> {
		/**
		 * The only element this iterator has.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final E element;
		/**
		 * True, if this iterator still has next.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected boolean hasNext = true;

		/**
		 * Construct a new iterator with only one element.
		 *
		 * @param element the only element the constructed iterator will have.
		 * @since 0.1.5 ~2020.08.19
		 */
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

	/**
	 * A list with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonList<E> extends SingletonCollection<E> implements List<E>, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7051230761107552456L;

		/**
		 * Construct a new list with only the given {@code element}.
		 *
		 * @param element the element to be the only element of the constructed list.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonList(E element) {
			super(element);
		}

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof List &&
				   ((List) object).size() == 1 &&
				   ((List) object).contains(this.element);
		}

		@Override
		public E get(int index) {
			if (index != 0)
				throw new IndexOutOfBoundsException(Integer.toString(index));

			return this.element;
		}

		@Override
		public int hashCode() {
			return 31 + (this.element == null ? 0 : this.element.hashCode());
		}

		@Override
		public int indexOf(Object object) {
			return object == this.element || object != null && object.equals(this.element) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return object == this.element || object != null && object.equals(this.element) ? 0 : -1;
		}

		@Override
		public SingletonListIterator<E> listIterator() {
			return new SingletonListIterator(this.element);
		}

		@Override
		public SingletonListIterator<E> listIterator(int index) {
			return new SingletonListIterator(this.element, index);
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			throw new UnsupportedOperationException("replaceAll");
		}

		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException("set");
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
		}

		@Override
		public List<E> subList(int beginIndex, int endIndex) {
			if (beginIndex < 0)
				throw new IndexOutOfBoundsException(Integer.toString(beginIndex));
			if (endIndex > 1)
				throw new IndexOutOfBoundsException(Integer.toString(endIndex));
			if (endIndex < beginIndex)
				throw new IllegalArgumentException("endIndex < beginIndex");

			return endIndex - beginIndex == 0 ? new EmptyList() : this;
		}
	}

	/**
	 * An iterator with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonListIterator<E> extends SingletonIterator<E> implements ListIterator<E> {
		/**
		 * Construct a new iterator with only one element.
		 *
		 * @param element the only element the constructed iterator will have.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonListIterator(E element) {
			super(element);
		}

		/**
		 * Construct a new iterator with only one element.
		 *
		 * @param element the only element the constructed iterator will have.
		 * @param index   the index where to start this iterator where 0 is at the start and 1 at
		 *                the end.
		 * @throws IndexOutOfBoundsException if the given {@code index} is neither 0 nor 1.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonListIterator(E element, int index) {
			super(element);
			if (index == 1)
				//noinspection AssignmentToSuperclassField
				this.hasNext = false;
			else if (index != 0)
				throw new IndexOutOfBoundsException(Integer.toString(index));
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasPrevious() {
			return !this.hasNext;
		}

		@Override
		public int nextIndex() {
			return this.hasNext ? 0 : 1;
		}

		@Override
		public E previous() {
			if (!this.hasNext) {
				this.hasNext = true;
				return this.element;
			}

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.hasNext ? -1 : 0;
		}

		@Override
		public void set(E element) {
			throw new UnsupportedOperationException("set");
		}
	}

	/**
	 * A map with a single pair.
	 *
	 * @param <K> the type of the key.
	 * @param <V> the type of hte value.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonMap<K, V> implements Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2215065372588540794L;

		/**
		 * The key of the only pair in this map.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final K key;
		/**
		 * The value of the only pair in this map.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final V value;

		/**
		 * Construct a new map that has only the given {@code key} and {@code value} pair.
		 *
		 * @param key   the key of the only pair in the constructed map.
		 * @param value the value of the only pair in the constructed map.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonMap(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
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
			return key == this.key || key != null && key.equals(this.key);
		}

		@Override
		public boolean containsValue(Object value) {
			return value == this.value || value != null && value.equals(this.value);
		}

		@Override
		public SingletonSet<Entry<K, V>> entrySet() {
			return new SingletonSet(new AbstractMap.SimpleImmutableEntry(this.key, this.value));
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map) {
				Map map = (Map) object;

				if (map.size() == 1) {
					Object value = map.get(this.key);
					return value == this.value || value != null && value.equals(this.value);
				}
			}

			return false;
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			consumer.accept(this.key, this.value);
		}

		@Override
		public V get(Object key) {
			return key == this.key || key != null && key.equals(this.key) ?
				   this.value : null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return key == this.key || key != null && key.equals(this.key) ?
				   this.value : defaultValue;
		}

		@Override
		public int hashCode() {
			return (this.key == null ? 0 : this.key.hashCode()) ^
				   (this.value == null ? 0 : this.value.hashCode());
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public SingletonSet<K> keySet() {
			return new SingletonSet(this.key);
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
			throw new UnsupportedOperationException("putAll");
		}

		@Override
		public V putIfAbsent(K key, V value) {
			throw new UnsupportedOperationException("putIfAbsent");
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
		public V replace(K key, V value) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("replaceAll");
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public String toString() {
			return "{" + this.key + "=" + this.value + "}";
		}

		@Override
		public SingletonCollection<V> values() {
			return new SingletonCollection(this.value);
		}
	}

	/**
	 * A set with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonSet<E> extends SingletonCollection<E> implements Set<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6326316058851840405L;

		/**
		 * Construct a new set with only the given {@code element}.
		 *
		 * @param element the element to be the only element of the constructed set.
		 * @since 0.1.5 ~2020.08.19
		 */
		public SingletonSet(E element) {
			super(element);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Set &&
				   ((Set) object).size() == 1 &&
				   ((Set) object).contains(this.element);
		}

		@Override
		public int hashCode() {
			return this.element == null ? 0 : this.element.hashCode();
		}
	}

	/**
	 * An spliterator with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class SingletonSpliterator<E> implements Spliterator<E> {
		/**
		 * The only element this iterator has.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final E element;
		/**
		 * The size of the remaining elements.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected long size = 1;

		/**
		 * Construct a new spliterator with only one element.
		 *
		 * @param element the only element the constructed spliterator will have.
		 * @since 0.1.5 ~2020.08.19
		 */
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
		public SingletonSpliterator<E> trySplit() {
			return null;
		}
	}

	//synchronized

	public static class SynchronizedCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = 5789337830471750010L;
		protected final Collection<E> collection;
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
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

		@Override
		public String toString() {
			synchronized (this.mutex) {
				return this.collection.toString();
			}
		}
	}

	public static class SynchronizedEnumeration<E> implements Enumeration<E> {
		//must not be implemented in this. specified in java.util.Collections
	}

	public static class SynchronizedIterator<E> implements Iterator<E> {
		//must not be implemented in this. specified in java.util.Collections
	}

	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
		private static final long serialVersionUID = 2436428270890417537L;
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

	public static class SynchronizedListIterator<E> implements ListIterator<E> {
		//must not be implemented in this. specified in java.util.Collections
	}

	public static class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
		private static final long serialVersionUID = -1243744996526469332L;
		protected final Map<K, V> map;
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
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

		@Override
		public String toString() {
			synchronized (this.mutex) {
				return this.map.toString();
			}
		}
	}

	public static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
		private static final long serialVersionUID = -454950785076281653L;

		public SynchronizedNavigableMap(NavigableMap<K, V> map) {
		}

		public SynchronizedNavigableMap(NavigableMap<K, V> map, Object mutex) {
		}
	}

	public static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
		private static final long serialVersionUID = 5020761590586629549L;

		public SynchronizedNavigableSet(NavigableSet<E> set) {
		}

		public SynchronizedNavigableSet(NavigableSet<E> set, Object mutex) {
		}
	}

	public static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
		private static final long serialVersionUID = -497874333100433258L;
	}

	public static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
		private static final long serialVersionUID = 3537476550500792998L;

		public SynchronizedRandomAccessList(List<E> list) {
			super(list);
		}

		public SynchronizedRandomAccessList(List<E> list, Object mutex) {
			super(list, mutex);
		}
	}

	public static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
		private static final long serialVersionUID = -6032712944227921269L;

		public SynchronizedSet(Set<E> set) {
		}

		public SynchronizedSet(Set<E> set, Object mutex) {
		}
	}

	public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
		private static final long serialVersionUID = 8385025551424159520L;

		public SynchronizedSortedMap(SortedMap<K, V> map) {
		}

		public SynchronizedSortedMap(SortedMap<K, V> map, Object mutex) {
		}
	}

	public static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
		private static final long serialVersionUID = -2367692007117797912L;

		public SynchronizedSortedSet(SortedSet<E> set) {
		}

		public SynchronizedSortedSet(SortedSet<E> set, Object mutex) {
		}
	}

	public static class SynchronizedSpliterator<E> implements Spliterator<E> {
		//must not be implemented in this. specified in java.util.Collections
	}

	//unmodifiable -UnmodifiableQueue

	/**
	 * An unmodifiable view collection of another collection.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8777395987818073607L;

		/**
		 * The wrapped collection.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Collection<E> collection;

		/**
		 * Construct a new unmodifiable view of the given {@code collection}.
		 *
		 * @param collection the collection backing the constructed collection.
		 * @throws NullPointerException if the given {@code collection} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableCollection(Collection<E> collection) {
			Objects.requireNonNull(collection, "collection");
			this.collection = collection;
		}

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
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
		public UnmodifiableIterator<E> iterator() {
			return new UnmodifiableIterator(this.collection.iterator());
		}

		@Override
		public Stream<E> parallelStream() {
			return this.collection.parallelStream();
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
			return this.collection.size();
		}

		@Override
		public UnmodifiableSpliterator<E> spliterator() {
			return new UnmodifiableSpliterator(this.collection.spliterator());
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
	}

	/**
	 * An enumeration wrapping another enumeration. Preventing that other enumeration from been
	 * accessed in an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableEnumeration<E> implements Enumeration<E> {
		/**
		 * The wrapped enumeration.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Enumeration<E> enumeration;

		/**
		 * Construct a new enumeration wrapping the given {@code enumeration}.
		 *
		 * @param enumeration the enumeration to be wrapped.
		 * @throws NullPointerException if the given {@code enumeration} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableEnumeration(Enumeration<E> enumeration) {
			Objects.requireNonNull(enumeration, "enumeration");
			this.enumeration = enumeration;
		}

		@Override
		public boolean hasMoreElements() {
			return this.enumeration.hasMoreElements();
		}

		@Override
		public E nextElement() {
			return this.enumeration.nextElement();
		}
	}

	/**
	 * An iterator wrapping another iterator. Preventing that other iterator from been accessed in
	 * an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableIterator<E> implements Iterator<E> {
		/**
		 * The wrapped iterator.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Iterator<E> iterator;

		/**
		 * Construct a new iterator wrapping the given {@code iterator}.
		 *
		 * @param iterator the iterator to be wrapped.
		 * @throws NullPointerException if the given {@code iterator} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableIterator(Iterator<E> iterator) {
			Objects.requireNonNull(iterator, "iterator");
			this.iterator = iterator;
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
		public E next() {
			return this.iterator.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * An unmodifiable view list of another list.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -4958100446409066123L;

		/**
		 * The wrapped list (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final List<E> list;

		/**
		 * Construct a new unmodifiable view of the given {@code list}.
		 *
		 * @param list the list backing the constructed list.
		 * @throws NullPointerException if the given {@code list} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableList(List<E> list) {
			super(list);
			this.list = list;
		}

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
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
		public UnmodifiableListIterator<E> listIterator(int index) {
			return new UnmodifiableListIterator(this.list.listIterator(index));
		}

		@Override
		public UnmodifiableListIterator<E> listIterator() {
			return new UnmodifiableListIterator(this.list.listIterator());
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			throw new UnsupportedOperationException("replaceAll");
		}

		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException("set");
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			throw new UnsupportedOperationException("sort");
		}

		@Override
		public UnmodifiableList<E> subList(int beginIndex, int endIndex) {
			return new UnmodifiableList(this.list.subList(beginIndex, endIndex));
		}
	}

	/**
	 * An iterator wrapping another iterator. Preventing that other iterator from storing any
	 * element.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
		/**
		 * The wrapped iterator (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final ListIterator<E> iterator;

		/**
		 * Construct a new iterator wrapping the given {@code iterator}.
		 *
		 * @param iterator the iterator to be wrapped.
		 * @throws NullPointerException if the given {@code iterator} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableListIterator(ListIterator<E> iterator) {
			super(iterator);
			this.iterator = iterator;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasPrevious() {
			return this.iterator.hasPrevious();
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
		public void set(E element) {
			throw new UnsupportedOperationException("set");
		}
	}

	/**
	 * An unmodifiable view map of another map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableMap<K, V> implements Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 175429931317032845L;

		/**
		 * The wrapped map.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Map<K, V> map;

		/**
		 * Construct a new unmodifiable view of the given {@code map}.
		 *
		 * @param map the map backing the constructed map.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableMap(Map<K, V> map) {
			Objects.requireNonNull(map, "map");
			this.map = map;
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
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
			return this.map.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return this.map.containsValue(value);
		}

		@Override
		public UnmodifiableEntrySet<K, V> entrySet() {
			return new UnmodifiableEntrySet(this.map.entrySet());
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.map.equals(object);
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			this.map.forEach(consumer);
		}

		@Override
		public V get(Object key) {
			return this.map.get(key);
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
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
		public UnmodifiableSet<K> keySet() {
			return new UnmodifiableSet(this.map.keySet());
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
			throw new UnsupportedOperationException("map");
		}

		@Override
		public V putIfAbsent(K key, V value) {
			throw new UnsupportedOperationException("putIfAbsent");
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
		public boolean replace(K key, V oldValue, V newValue) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public V replace(K key, V value) {
			throw new UnsupportedOperationException("replace");
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException("replaceAll");
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
		public UnmodifiableCollection<V> values() {
			return new UnmodifiableCollection(this.map.values());
		}

		/**
		 * An unmodifiable entry wrapping another entry.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class UnmodifiableEntry<K, V> implements Entry<K, V>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7707932941264072789L;

			/**
			 * The wrapped entry.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Entry<K, V> entry;

			/**
			 * Construct a new unmodifiable view for the given {@code entry}.
			 *
			 * @param entry the entry to be wrapped.
			 * @throws NullPointerException if the given {@code entry} is null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public UnmodifiableEntry(Entry<K, V> entry) {
				Objects.requireNonNull(entry, "entry");
				this.entry = entry;
			}

			@Override
			public boolean equals(Object object) {
				return object == this ||
					   object instanceof Entry &&
					   //todo why SimpleImmutableEntry?
					   this.entry.equals(new AbstractMap.SimpleImmutableEntry((Entry) object));
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
				throw new UnsupportedOperationException("setValue");
			}

			@Override
			public String toString() {
				return this.entry.toString();
			}
		}

		/**
		 * An entry iterator wrapping the entries from another iterator.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of hte values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class UnmodifiableEntryIterator<K, V> implements Iterator<Entry<K, V>> {
			/**
			 * The wrapped iterator.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Iterator<Entry<K, V>> iterator;

			/**
			 * Construct a new iterator wrapping the entries from the given {@code iterator}.
			 *
			 * @param iterator the iterator to be wrapped.
			 * @throws NullPointerException if the given {@code iterator} is null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public UnmodifiableEntryIterator(Iterator<Entry<K, V>> iterator) {
				Objects.requireNonNull(iterator, "iterator");
				this.iterator = iterator;
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.iterator.forEachRemaining(entry -> consumer.accept(new UnmodifiableEntry(entry)));
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public UnmodifiableEntry<K, V> next() {
				return new UnmodifiableEntry(this.iterator.next());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}
		}

		/**
		 * An entry set that wraps another entry set.Care should be taken since the entries is a
		 * sensitive area in a map and a user reaching a raw entry will break the 'unmodifiable'
		 * property.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class UnmodifiableEntrySet<K, V> implements Set<Entry<K, V>>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7636625387235075080L;

			/**
			 * The wrapped entry set.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Set<Entry<K, V>> entrySet;

			/**
			 * Construct a new set wrapping the given {@code entrySet}.
			 *
			 * @param entrySet the set to be wrapped.
			 * @throws NullPointerException if the given {@code entrySet} is null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public UnmodifiableEntrySet(Set<Entry<K, V>> entrySet) {
				Objects.requireNonNull(entrySet, "entrySet");
				this.entrySet = entrySet;
			}

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
				throw new UnsupportedOperationException("clear");
			}

			@Override
			public boolean contains(Object object) {
				return object instanceof UnmodifiableEntry ?
					   this.entrySet.contains(object) :
					   object instanceof Entry &&
					   this.entrySet.contains(new UnmodifiableEntry((Entry) object));
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

					return set.size() == this.entrySet.size() &&
						   this.containsAll(set);
				}

				return false;
			}

			@Override
			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.entrySet.forEach(entry -> consumer.accept(new UnmodifiableEntry(entry)));
			}

			@Override
			public int hashCode() {
				return this.entrySet.hashCode();
			}

			@Override
			public boolean isEmpty() {
				return this.entrySet.isEmpty();
			}

			@Override
			public UnmodifiableEntryIterator<K, V> iterator() {
				return new UnmodifiableEntryIterator(this.entrySet.iterator());
			}

			@Override
			public Stream<Entry<K, V>> parallelStream() {
				return StreamSupport.stream(new UnmodifiableEntrySpliterator(
						this.entrySet.spliterator()
				), true);
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
			public boolean removeIf(Predicate<? super Entry<K, V>> predicate) {
				for (Entry<K, V> entry : this.entrySet)
					if (predicate.test(new UnmodifiableEntry(entry)))
						throw new UnsupportedOperationException("remove");

				return false;
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return this.entrySet.size();
			}

			@Override
			public UnmodifiableEntrySpliterator<K, V> spliterator() {
				return new UnmodifiableEntrySpliterator(
						this.entrySet.spliterator()
				);
			}

			@Override
			public Stream<Entry<K, V>> stream() {
				return StreamSupport.stream(new UnmodifiableEntrySpliterator(
						this.entrySet.spliterator()
				), false);
			}

			@Override
			public Object[] toArray() {
				Object[] array = this.entrySet.toArray();
				Object[] product = array;

				if (!array.getClass().isAssignableFrom(UnmodifiableEntry[].class))
					product = new Object[array.length];

				for (int i = 0; i < array.length; i++)
					product[i] = new UnmodifiableEntry((Entry) array[i]);

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Object[] product = this.entrySet.toArray(
						array.length == 0 ?
						array : Arrays.copyOf(array, 0)
				);

				for (int i = 0; i < product.length; i++)
					product[i] = new UnmodifiableEntry((Entry) product[i]);

				if (array.length < product.length)
					return (T[]) product;
				if (array.length > product.length)
					array[product.length] = null;

				System.arraycopy(
						product,
						0,
						array,
						0,
						product.length
				);

				return array;
			}

			@Override
			public String toString() {
				return this.entrySet.toString();
			}
		}

		/**
		 * A spliterator wrapping another spliterator. Wrapping each entry in the other
		 * spliterator.
		 *
		 * @param <K> the type of the keys.
		 * @param <V> the type of the values.
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.19
		 */
		public static class UnmodifiableEntrySpliterator<K, V> implements Spliterator<Entry<K, V>> {
			/**
			 * The wrapped spliterator.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Spliterator<Entry<K, V>> spliterator;

			/**
			 * Construct a new spliterator wrapping the given {@code spliterator}.
			 *
			 * @param spliterator the spliterator to be wrapped.
			 * @throws NullPointerException if the given {@code spliterator} is null.
			 * @since 0.1.5 ~2020.08.19
			 */
			public UnmodifiableEntrySpliterator(Spliterator<Entry<K, V>> spliterator) {
				Objects.requireNonNull(spliterator, "spliterator");
				this.spliterator = spliterator;
			}

			@Override
			public int characteristics() {
				return this.spliterator.characteristics();
			}

			@Override
			public long estimateSize() {
				return this.spliterator.estimateSize();
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.spliterator.forEachRemaining(entry -> consumer.accept(new UnmodifiableEntry(entry)));
			}

			@Override
			public Comparator<? super Entry<K, V>> getComparator() {
				return this.spliterator.getComparator();
			}

			@Override
			public long getExactSizeIfKnown() {
				return this.spliterator.getExactSizeIfKnown();
			}

			@Override
			public boolean hasCharacteristics(int characteristics) {
				return this.spliterator.hasCharacteristics(characteristics);
			}

			@Override
			public boolean tryAdvance(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				return this.spliterator.tryAdvance(entry -> consumer.accept(new UnmodifiableEntry(entry)));
			}

			@Override
			public UnmodifiableEntrySpliterator<K, V> trySplit() {
				Spliterator<Entry<K, V>> spliterator = this.spliterator.trySplit();
				return spliterator == null ? null :
					   new UnmodifiableEntrySpliterator(spliterator);
			}
		}
	}

	/**
	 * An unmodifiable view map of another map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class UnmodifiableNavigableMap<K, V> extends UnmodifiableSortedMap<K, V> implements NavigableMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 5573994241418117460L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final NavigableMap<K, V> map;

		/**
		 * Construct a new unmodifiable view of the given {@code map}.
		 *
		 * @param map the map backing the constructed map.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableNavigableMap(NavigableMap<K, V> map) {
			super(map);
			this.map = map;
		}

		@Override
		public UnmodifiableEntry<K, V> ceilingEntry(K key) {
			Entry<K, V> entry = this.map.ceilingEntry(key);
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public K ceilingKey(K key) {
			return this.map.ceilingKey(key);
		}

		@Override
		public UnmodifiableNavigableSet<K> descendingKeySet() {
			return new UnmodifiableNavigableSet(this.map.descendingKeySet());
		}

		@Override
		public UnmodifiableNavigableMap<K, V> descendingMap() {
			return new UnmodifiableNavigableMap(this.map.descendingMap());
		}

		@Override
		public UnmodifiableEntry<K, V> firstEntry() {
			Entry<K, V> entry = this.map.firstEntry();
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public UnmodifiableEntry<K, V> floorEntry(K key) {
			Entry<K, V> entry = this.map.floorEntry(key);
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public K floorKey(K key) {
			return this.map.floorKey(key);
		}

		@Override
		public UnmodifiableNavigableMap<K, V> headMap(K endKey, boolean inclusive) {
			return new UnmodifiableNavigableMap(this.map.headMap(
					endKey,
					inclusive
			));
		}

		@Override
		public UnmodifiableEntry<K, V> higherEntry(K key) {
			Entry<K, V> entry = this.map.higherEntry(key);
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public K higherKey(K key) {
			return this.map.higherKey(key);
		}

		@Override
		public UnmodifiableEntry<K, V> lastEntry() {
			Entry<K, V> entry = this.map.lastEntry();
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public UnmodifiableEntry<K, V> lowerEntry(K key) {
			Entry<K, V> entry = this.map.lowerEntry(key);
			return entry == null ? null : new UnmodifiableEntry(entry);
		}

		@Override
		public K lowerKey(K key) {
			return this.map.lowerKey(key);
		}

		@Override
		public UnmodifiableNavigableSet<K> navigableKeySet() {
			return new UnmodifiableNavigableSet(this.map.navigableKeySet());
		}

		@Override
		public UnmodifiableEntry<K, V> pollFirstEntry() {
			throw new UnsupportedOperationException("pollFirstEntry");
		}

		@Override
		public UnmodifiableEntry<K, V> pollLastEntry() {
			throw new UnsupportedOperationException("pollLastEntry");
		}

		@Override
		public UnmodifiableNavigableMap<K, V> subMap(K beginKey, boolean beginInclusive, K endKey, boolean endInclusive) {
			return new UnmodifiableNavigableMap(this.map.subMap(
					beginKey,
					beginInclusive,
					endKey,
					endInclusive
			));
		}

		@Override
		public UnmodifiableNavigableMap<K, V> tailMap(K beginKey, boolean inclusive) {
			return new UnmodifiableNavigableMap(this.map.tailMap(
					beginKey,
					inclusive
			));
		}
	}

	/**
	 * An unmodifiable view set of another set.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class UnmodifiableNavigableSet<E> extends UnmodifiableSortedSet<E> implements NavigableSet<E> {
		private static final long serialVersionUID = -6556998700026339239L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final NavigableSet<E> set;

		/**
		 * Construct a new unmodifiable view of the given {@code set}.
		 *
		 * @param set the set backing the constructed set.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableNavigableSet(NavigableSet<E> set) {
			super(set);
			this.set = set;
		}

		@Override
		public E ceiling(E element) {
			return this.set.ceiling(element);
		}

		@Override
		public UnmodifiableIterator<E> descendingIterator() {
			return new UnmodifiableIterator(this.set.descendingIterator());
		}

		@Override
		public UnmodifiableNavigableSet<E> descendingSet() {
			return new UnmodifiableNavigableSet(this.set.descendingSet());
		}

		@Override
		public E floor(E element) {
			return this.set.floor(element);
		}

		@Override
		public UnmodifiableNavigableSet<E> headSet(E endElement, boolean inclusive) {
			return new UnmodifiableNavigableSet(this.set.headSet(
					endElement,
					inclusive
			));
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
			throw new UnsupportedOperationException("pollFirst");
		}

		@Override
		public E pollLast() {
			throw new UnsupportedOperationException("pollLast");
		}

		@Override
		public UnmodifiableNavigableSet<E> subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive) {
			return new UnmodifiableNavigableSet(this.set.subSet(
					beginElement,
					beginInclusive,
					endElement,
					endInclusive
			));
		}

		@Override
		public UnmodifiableNavigableSet<E> tailSet(E endElement, boolean inclusive) {
			return new UnmodifiableNavigableSet(this.set.tailSet(
					endElement,
					inclusive
			));
		}
	}

	/**
	 * An unmodifiable view list of another list.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E> implements RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6329610600582841094L;

		/**
		 * Construct a new unmodifiable view of the given {@code list}.
		 *
		 * @param list the list backing the constructed list.
		 * @throws NullPointerException if the given {@code list} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableRandomAccessList(List<E> list) {
			super(list);
		}

		@Override
		public UnmodifiableRandomAccessList<E> subList(int beginIndex, int endIndex) {
			return new UnmodifiableRandomAccessList(this.list.subList(beginIndex, endIndex));
		}
	}

	/**
	 * An unmodifiable view set of another set.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4913223673966606267L;

		/**
		 * Construct a new unmodifiable view of the given {@code set}.
		 *
		 * @param set the set backing the constructed set.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableSet(Set<E> set) {
			super(set);
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

	/**
	 * An unmodifiable view map of another map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements SortedMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7355423126533592244L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final SortedMap<K, V> map;

		/**
		 * Construct a new unmodifiable view of the given {@code map}.
		 *
		 * @param map the map backing the constructed map.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableSortedMap(SortedMap<K, V> map) {
			super(map);
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
		public UnmodifiableSortedMap<K, V> headMap(K endKey) {
			return new UnmodifiableSortedMap(this.map.headMap(
					endKey
			));
		}

		@Override
		public K lastKey() {
			return this.map.lastKey();
		}

		@Override
		public UnmodifiableSortedMap<K, V> subMap(K beginKey, K endKey) {
			return new UnmodifiableSortedMap(this.map.subMap(
					beginKey,
					endKey
			));
		}

		@Override
		public UnmodifiableSortedMap<K, V> tailMap(K beginKey) {
			return new UnmodifiableSortedMap(this.map.tailMap(
					beginKey
			));
		}
	}

	/**
	 * An unmodifiable view set of another set.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	public static class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8353256504329557793L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final SortedSet<E> set;

		/**
		 * Construct a new unmodifiable view of the given {@code set}.
		 *
		 * @param set the set backing the constructed set.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableSortedSet(SortedSet<E> set) {
			super(set);
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
		public UnmodifiableSortedSet<E> headSet(E endElement) {
			return new UnmodifiableSortedSet(this.set.headSet(endElement));
		}

		@Override
		public E last() {
			return this.set.last();
		}

		@Override
		public UnmodifiableSortedSet<E> subSet(E beginElement, E endElement) {
			return new UnmodifiableSortedSet(this.set.subSet(beginElement, endElement));
		}

		@Override
		public UnmodifiableSortedSet<E> tailSet(E beginElement) {
			return new UnmodifiableSortedSet(this.set.tailSet(beginElement));
		}
	}

	/**
	 * A spliterator wrapping another spliterator. Preventing that other spliterator from been
	 * accessed in an unappropriated way.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	public static class UnmodifiableSpliterator<E> implements Spliterator<E> {
		/**
		 * The wrapped spliterator.
		 *
		 * @since 0.1.5 ~2020.08.19
		 */
		protected final Spliterator<E> spliterator;

		/**
		 * Construct a new spliterator wrapping the given {@code spliterator}.
		 *
		 * @param spliterator the spliterator to be wrapped.
		 * @throws NullPointerException if the given {@code spliterator} is null.
		 * @since 0.1.5 ~2020.08.19
		 */
		public UnmodifiableSpliterator(Spliterator<E> spliterator) {
			Objects.requireNonNull(spliterator, "spliterator");
			this.spliterator = spliterator;
		}

		@Override
		public int characteristics() {
			return this.spliterator.characteristics();
		}

		@Override
		public long estimateSize() {
			return this.spliterator.estimateSize();
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			this.spliterator.forEachRemaining(consumer);
		}

		@Override
		public Comparator<? super E> getComparator() {
			return this.spliterator.getComparator();
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.spliterator.getExactSizeIfKnown();
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return this.spliterator.hasCharacteristics(characteristics);
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			return this.spliterator.tryAdvance(consumer);
		}

		@Override
		public UnmodifiableSpliterator<E> trySplit() {
			Spliterator<E> spliterator = this.spliterator.trySplit();
			return spliterator == null ? null : new UnmodifiableSpliterator(spliterator);
		}
	}
}
