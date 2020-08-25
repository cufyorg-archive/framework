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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An Utility class for collections. Supporting various kinds of collection operations.
 * <p>
 * This class includes all the methods in the standard {@link java.util.Collections} utility class
 * with the same behaviour. So switching to import this class will not make any changes to files
 * previously imported {@link java.util.Collections}.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.0.b ~2019.06.11
 */
public interface Collections {
	/**
	 * A global instance of an empty collection to avoid redundant instances constructions.
	 *
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyCollection EMPTY_COLLECTION = new EmptyCollection();
	/**
	 * A global instance of an empty enumeration to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections.EmptyEnumeration#EMPTY_ENUMERATION
	 * @since 0.1.5 ~2020.08.22
	 */
	EmptyEnumeration EMPTY_ENUMERATION = new EmptyEnumeration();
	/**
	 * A global instance of an empty iterator to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections.EmptyIterator#EMPTY_ITERATOR
	 * @since 0.1.5 ~2020.08.22
	 */
	EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
	/**
	 * A global instance of an empty list to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections#EMPTY_LIST
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyList EMPTY_LIST = new EmptyList();
	/**
	 * A global instance of an empty iterator to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections.EmptyListIterator#EMPTY_ITERATOR
	 * @since 0.1.5 ~2020.08.22
	 */
	EmptyListIterator EMPTY_LIST_ITERATOR = new EmptyListIterator();
	/**
	 * A global instance of an empty map to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections#EMPTY_MAP
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyMap EMPTY_MAP = new EmptyMap();
	/**
	 * A global instance of an empty map to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections.UnmodifiableNavigableMap#EMPTY_NAVIGABLE_MAP
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyNavigableMap EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap();
	/**
	 * A global instance of an empty set to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections.UnmodifiableNavigableSet#EMPTY_NAVIGABLE_SET
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyNavigableSet EMPTY_NAVIGABLE_SET = new EmptyNavigableSet();
	/**
	 * A global instance of an empty queue to avoid redundant instances constructions.
	 *
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptyQueue EMPTY_QUEUE = new EmptyQueue();
	/**
	 * A global instance of an empty set to avoid redundant instances constructions.
	 *
	 * @see java.util.Collections#EMPTY_SET
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptySet EMPTY_SET = new EmptySet();
	/**
	 * A global instance of an empty map to avoid redundant instances constructions.
	 *
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptySortedMap EMPTY_SORTED_MAP = new EmptySortedMap();
	/**
	 * A global instance of an empty set to avoid redundant instances constructions.
	 *
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("PublicStaticCollectionField")
	EmptySortedSet EMPTY_SORTED_SET = new EmptyNavigableSet();
	/**
	 * A global instance of an empty spliterator to avoid redundant instances constructions.
	 *
	 * @since 0.1.5 ~2020.08.22
	 */
	EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

	/**
	 * An instance of the reversed natural comparator.
	 *
	 * @since 0.1.5 ~2020.08.25
	 */
	ReverseNaturalComparator REVERSE_NATURAL_COMPARATOR = new ReverseNaturalComparator();

	/**
	 * Add all the given {@code elements} to the given {@code collection}.
	 *
	 * @param collection the collection to add the given {@code elements} to.
	 * @param elements   the elements to be added to the given {@code collection}.
	 * @param <T>        the type of the elements.
	 * @return true, if hte given {@code collection} has been changed as a result of this call.
	 * @throws NullPointerException if the given {@code collection} or {@code elements} is null.
	 * @see java.util.Collections#addAll(Collection, Object[])
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> boolean addAll(Collection<? super T> collection, T... elements) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(elements, "elements");
		boolean modified = false;
		for (T element : elements)
			modified |= collection.add(element);
		return modified;
	}

	/**
	 * Construct a view of the given {@code deque} as a {@code Last-In-First-Out} queue.
	 *
	 * @param deque the deque to be wrapped.
	 * @param <T>   the type of the elements.
	 * @return a LIFO-queue view of the given {@code deque}.
	 * @throws NullPointerException if the given {@code deque} is null.
	 * @see java.util.Collections#asLifoQueue(Deque)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> DequeLIFOQueue<T> asLifoQueue(Deque<T> deque) {
		return new DequeLIFOQueue(deque);
	}

	/**
	 * Search the given {@code list} for the given {@code key} using the binarySearch algorithm. The
	 * list should be sorted for this method to work. If it is not sorted, the results are
	 * undefined. If the list contains multiple elements with the specified value, there is no
	 * guarantee witch one will be found.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#binarySearch(List, Object)} since
	 * the field {@link java.util.Collections#BINARYSEARCH_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be searched.
	 * @param key  the key to search for.
	 * @param <T>  the type of the elements.
	 * @return the index of the search element, if is contained in the given {@code list};
	 * 		otherwise, (-(<i>insertion point</i>) - 1).
	 * @see java.util.Collections#binarySearch(List, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key);
	}

	/**
	 * Search the given {@code list} for the given {@code key} using the binarySearch algorithm. The
	 * list should be sorted for this method to work. If it is not sorted, the results are
	 * undefined. If the list contains multiple elements with the specified value, there is no
	 * guarantee witch one will be found.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#binarySearch(List, Object,
	 * Comparator)} since the field {@link java.util.Collections#BINARYSEARCH_THRESHOLD} is not
	 * accessible.
	 *
	 * @param list       the list to be searched.
	 * @param key        the key to search for.
	 * @param comparator the comparator by which the list is ordered. A null value indicates that
	 *                   the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the index of the search element, if is contained in the given {@code list};
	 * 		otherwise, (-(<i>insertion point</i>) - 1).
	 * @see java.util.Collections#binarySearch(List, Object, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> int binarySearch(List<T> list, T key, Comparator<? super T> comparator) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key, comparator);
	}

	/**
	 * Construct a checked view of the given {@code collection} that does not allow storing elements
	 * that is not an instance of the given {@code type}.
	 *
	 * @param collection the collection to get a checked view of.
	 * @param type       the type of the elements allowed to be stored at the returned collection.
	 * @param <T>        the type of the elements.
	 * @return a checked view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code type} is null.
	 * @see java.util.Collections#checkedCollection(Collection, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedCollection<T> checkedCollection(Collection<T> collection, Class<T> type) {
		return new CheckedCollection(collection, type);
	}

	/**
	 * Construct a checked view of the given {@code enumeration}.
	 *
	 * @param enumeration the enumeration to get a checked view of.
	 * @param <T>         the type of the elements.
	 * @return a checked view of the given {@code enumeration}.
	 * @throws NullPointerException if the given {@code enumeration} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedEnumeration<T> checkedEnumeration(Enumeration<T> enumeration) {
		return new CheckedEnumeration(enumeration);
	}

	/**
	 * Construct a checked view of the given {@code iterator}.
	 *
	 * @param iterator the iterator to get a checked view of.
	 * @param <T>      the type of the elements.
	 * @return a checked view of the given {@code iterator}.
	 * @throws NullPointerException if the given {@code iterator} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedIterator<T> checkedIterator(Iterator<T> iterator) {
		return new CheckedIterator(iterator);
	}

	/**
	 * Construct a checked view of the given {@code list} that does not allow storing elements that
	 * is not an instance of the given {@code type}.
	 *
	 * @param list the list to get a checked view of.
	 * @param type the type of the elements allowed to be stored at the returned list.
	 * @param <T>  the type of the elements.
	 * @return a checked view of the given {@code list}.
	 * @throws NullPointerException if the given {@code list} or {@code type} is null.
	 * @see java.util.Collections#checkedList(List, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedList<T> checkedList(List<T> list, Class<T> type) {
		return list instanceof RandomAccess ?
			   new CheckedRandomAccessList(list, type) :
			   new CheckedList(list, type);
	}

	/**
	 * Construct a checked view of the given {@code iterator} that does not allow storing elements
	 * that is not an instance of the given {@code type}.
	 *
	 * @param iterator the iterator to get a checked view of.
	 * @param type     the type of the elements allowed to be stored at the returned iterator.
	 * @param <T>      the type of the elements.
	 * @return a checked view of the given {@code iterator}.
	 * @throws NullPointerException if the given {@code iterator} or {@code type} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedListIterator<T> checkedListIterator(ListIterator<T> iterator, Class<T> type) {
		return new CheckedListIterator(iterator, type);
	}

	/**
	 * Construct a checked view of the given {@code map} that does not allow storing keys that is
	 * not an instance of the given {@code keyType} neither values that is not an instance of the
	 * given {@code valueType}.
	 *
	 * @param map       the map to get a checked view of.
	 * @param keyType   the type of the keys allowed to be stored at the returned map.
	 * @param valueType the type of the values allowed to be stored at the returned map.
	 * @param <K>       the type of the keys.
	 * @param <V>       the type of the values.
	 * @return a checked view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code valueType}
	 *                              is null.
	 * @see java.util.Collections#checkedMap(Map, Class, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> CheckedMap<K, V> checkedMap(Map<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedMap(map, keyType, valueType);
	}

	/**
	 * Construct a checked view of the given {@code map} that does not allow storing keys that is
	 * not an instance of the given {@code keyType} neither values that is not an instance of the
	 * given {@code valueType}.
	 *
	 * @param map       the map to get a checked view of.
	 * @param keyType   the type of the keys allowed to be stored at the returned map.
	 * @param valueType the type of the values allowed to be stored at the returned map.
	 * @param <K>       the type of the keys.
	 * @param <V>       the type of the values.
	 * @return a checked view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code valueType}
	 *                              is null.
	 * @see java.util.Collections#checkedNavigableMap(NavigableMap, Class, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> CheckedNavigableMap<K, V> checkedNavigableMap(NavigableMap<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedNavigableMap(map, keyType, valueType);
	}

	/**
	 * Construct a checked view of the given {@code set} that does not allow storing elements that
	 * is not an instance of the given {@code type}.
	 *
	 * @param set  the set to get a checked view of.
	 * @param type the type of the elements allowed to be stored at the returned set.
	 * @param <T>  the type of the elements.
	 * @return a checked view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code type} is null.
	 * @see java.util.Collections#checkedNavigableSet(NavigableSet, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedNavigableSet<T> checkedNavigableSet(NavigableSet<T> set, Class<T> type) {
		return new CheckedNavigableSet(set, type);
	}

	/**
	 * Construct a checked view of the given {@code queue} that does not allow storing elements that
	 * is not an instance of the given {@code type}.
	 *
	 * @param queue the queue to get a checked view of.
	 * @param type  the type of the elements allowed to be stored at the returned queue.
	 * @param <T>   the type of the elements.
	 * @return a checked view of the given {@code queue}.
	 * @throws NullPointerException if the given {@code queue} or {@code type} is null.
	 * @see java.util.Collections#checkedQueue(Queue, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedQueue<T> checkedQueue(Queue<T> queue, Class<T> type) {
		return new CheckedQueue(queue, type);
	}

	/**
	 * Construct a checked view of the given {@code set} that does not allow storing elements that
	 * is not an instance of the given {@code type}.
	 *
	 * @param set  the set to get a checked view of.
	 * @param type the type of the elements allowed to be stored at the returned set.
	 * @param <T>  the type of the elements.
	 * @return a checked view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code type} is null.
	 * @see java.util.Collections#checkedSet(Set, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedSet<T> checkedSet(Set<T> set, Class<T> type) {
		return new CheckedSet(set, type);
	}

	/**
	 * Construct a checked view of the given {@code map} that does not allow storing keys that is
	 * not an instance of the given {@code keyType} neither values that is not an instance of the
	 * given {@code valueType}.
	 *
	 * @param map       the map to get a checked view of.
	 * @param keyType   the type of the keys allowed to be stored at the returned map.
	 * @param valueType the type of the values allowed to be stored at the returned map.
	 * @param <K>       the type of the keys.
	 * @param <V>       the type of the values.
	 * @return a checked view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code keyType} or {@code valueType}
	 *                              is null.
	 * @see java.util.Collections#checkedSortedMap(SortedMap, Class, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> CheckedSortedMap<K, V> checkedSortedMap(SortedMap<K, V> map, Class<K> keyType, Class<V> valueType) {
		return new CheckedSortedMap(map, keyType, valueType);
	}

	/**
	 * Construct a checked view of the given {@code set} that does not allow storing elements that
	 * is not an instance of the given {@code type}.
	 *
	 * @param set  the set to get a checked view of.
	 * @param type the type of the elements allowed to be stored at the returned set.
	 * @param <T>  the type of the elements.
	 * @return a checked view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code type} is null.
	 * @see java.util.Collections#checkedSortedSet(SortedSet, Class)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedSortedSet<T> checkedSortedSet(SortedSet<T> set, Class<T> type) {
		return new CheckedSortedSet(set, type);
	}

	/**
	 * Construct a checked view of the given {@code spliterator}.
	 *
	 * @param spliterator the spliterator to get a checked view of.
	 * @param <T>         the type of the elements.
	 * @return a checked view of the given {@code spliterator}.
	 * @throws NullPointerException if the given {@code spliterator} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> CheckedSpliterator<T> checkedSpliterator(Spliterator<T> spliterator) {
		return new CheckedSpliterator(spliterator);
	}

	/**
	 * Copy all the element in the given {@code src} to exactly the same indexes at the given {@code
	 * dest}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#copy(List, List)} since the field
	 * {@link java.util.Collections#COPY_THRESHOLD} is not accessible.
	 *
	 * @param dest the destination list.
	 * @param src  the source list.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code dest} or {@code src} is null.
	 * @see java.util.Collections#copy(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void copy(List<? super T> dest, List<? extends T> src) {
		//can not access java.util.Collections.COPY_THRESHOLD
		java.util.Collections.copy(dest, src);
	}

	/**
	 * Determine if the given {@code collection} contains no element in the given {@code other}.
	 *
	 * @param collection the collection.
	 * @param other      the other collection.
	 * @param <T>        the type of the elements in the first collection.
	 * @param <U>        the type of the elements in the second collection.
	 * @return true, if the given {@code collection} contains no element in the given {@code other}.
	 * @throws NullPointerException if the given {@code collection} or {@code other} is null.
	 * @see java.util.Collections#disjoint(Collection, Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> boolean disjoint(Collection<T> collection, Collection<U> other) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(other, "other");

		if (collection.isEmpty() || other.isEmpty())
			//nothing to match
			return true;
		if (collection == other)
			//no need to match, since it is itself and itself is not empty
			return false;

		if (collection instanceof Set ||
			!(other instanceof Set) &&
			collection.size() > other.size()) {
			for (U u : other)
				if (collection.contains(u))
					return false;
		} else
			for (T t : collection)
				if (other.contains(t))
					return false;

		//no match
		return true;
	}

	/**
	 * Get an empty collection.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty collection.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyCollection<T> emptyCollection() {
		return Collections.EMPTY_COLLECTION;
	}

	/**
	 * Get an empty enumeration.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty enumeration.
	 * @see java.util.Collections#emptyEnumeration()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyEnumeration<T> emptyEnumeration() {
		return Collections.EMPTY_ENUMERATION;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @see java.util.Collections#emptyIterator()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyIterator<T> emptyIterator() {
		return Collections.EMPTY_ITERATOR;
	}

	/**
	 * Get an empty list.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty list.
	 * @see java.util.Collections#emptyList()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyList<T> emptyList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @see java.util.Collections#emptyListIterator()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyListIterator<T> emptyListIterator() {
		return Collections.EMPTY_LIST_ITERATOR;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see java.util.Collections#emptyMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptyMap<K, V> emptyMap() {
		return Collections.EMPTY_MAP;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see java.util.Collections#emptyNavigableMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptyNavigableMap<K, V> emptyNavigableMap() {
		return Collections.EMPTY_NAVIGABLE_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see java.util.Collections#emptyNavigableSet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyNavigableSet<T> emptyNavigableSet() {
		return Collections.EMPTY_NAVIGABLE_SET;
	}

	/**
	 * Get an empty queue.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty queue.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyQueue<T> emptyQueue() {
		return Collections.EMPTY_QUEUE;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see java.util.Collections#emptySet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySet<T> emptySet() {
		return Collections.EMPTY_SET;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see java.util.Collections#emptySortedMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptySortedMap<K, V> emptySortedMap() {
		return Collections.EMPTY_SORTED_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see java.util.Collections#emptySortedSet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySortedSet<T> emptySortedSet() {
		return Collections.EMPTY_SORTED_SET;
	}

	/**
	 * Get an empty spliterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty spliterator.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySpliterator<T> emptySpliterator() {
		return Collections.EMPTY_SPLITERATOR;
	}

	/**
	 * Construct a new enumeration for the given {@code collection}.
	 *
	 * @param collection the collection to get an enumeration for.
	 * @param <T>        the type of the elements.
	 * @return an enumeration for the given {@code collections}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @see java.util.Collections#enumeration(Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> IteratorEnumeration<T> enumeration(Collection<T> collection) {
		Objects.requireNonNull(collection, "collection");
		return new IteratorEnumeration(collection.iterator());
	}

	/**
	 * Replace all the elements in the given {@code list} with the given {@code element}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#fill(List, Object)} since the
	 * field {@link java.util.Collections#FILL_THRESHOLD} is not accessible.
	 *
	 * @param list    the list to be filled.
	 * @param element the element to fill the given {@code list}.
	 * @param <T>     the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#fill(List, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void fill(List<? super T> list, T element) {
		//can not access java.util.Collections.FILL_THRESHOLD
		java.util.Collections.fill(list, element);
	}

	/**
	 * Determine how many times the given {@code element} has been stored in the given {@code
	 * collection}.
	 *
	 * @param collection the collection that could have the given {@code element}.
	 * @param element    the element to find how many time it occurred in the given {@code
	 *                   collection}.
	 * @param <T>        the type of the elements in the collection.
	 * @param <U>        the type of the element.
	 * @return the number of how many times the given {@code element} appeared in the given {@code
	 * 		collection}.
	 * @throws NullPointerException if the given {@code collection is null}.
	 * @see java.util.Collections#frequency(Collection, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int frequency(Collection<T> collection, U element) {
		Objects.requireNonNull(collection, "collection");
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
	 * Find the index where the first sequence of elements that equals the given {@code target}
	 * begins.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#indexOfSubList(List, List)} since
	 * the field {@link java.util.Collections#INDEXOFSUBLIST_THRESHOLD} is not accessible.
	 *
	 * @param source the source array.
	 * @param target the sequence to be found.
	 * @param <T>    the type of the elements in the source list.
	 * @param <U>    the type of the elements in the target list.
	 * @return the index where the first sequence of elements that equals the given {@code target}
	 * 		begins.
	 * @throws NullPointerException if the given {@code source} or {@code target} is null.
	 * @see java.util.Collections#indexOfSubList(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int indexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.indexOfSubList(source, target);
	}

	/**
	 * Find the index where the last sequence of elements that equals the given {@code target}
	 * begins.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#lastIndexOfSubList(List, List)}
	 * since the field {@link java.util.Collections#INDEXOFSUBLIST_THRESHOLD} is not accessible.
	 *
	 * @param source the source array.
	 * @param target the sequence to be found.
	 * @param <T>    the type of the elements in the source list.
	 * @param <U>    the type of the elements in the target list.
	 * @return the index where the last sequence of elements that equals the given {@code target}
	 * 		begins.
	 * @throws NullPointerException if the given {@code source} or {@code target} is null.
	 * @see java.util.Collections#lastIndexOfSubList(List, List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T, U> int lastIndexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.lastIndexOfSubList(source, target);
	}

	/**
	 * Construct a new {@link ArrayList} then fill it with the remaining elements in the given
	 * {@code enumeration}.
	 *
	 * @param enumeration the enumeration to read from.
	 * @param <T>         the type of the elements.
	 * @return a new {@link ArrayList} filled with the remaining elements in the given {@code
	 * 		enumeration}.
	 * @throws NullPointerException if the given {@code enumeration} is null.
	 * @see java.util.Collections#list(Enumeration)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> ArrayList<T> list(Enumeration<T> enumeration) {
		Objects.requireNonNull(enumeration, "enumeration");
		ArrayList list = new ArrayList();

		while (enumeration.hasMoreElements())
			list.add(enumeration.nextElement());

		return list;
	}

	/**
	 * Find the maximum element in the given {@code collection} based on the <i>natural
	 * ordering</i>.
	 *
	 * @param collection the collection to find the maximum element stored in it.
	 * @param <T>        the type of the elements.
	 * @return the maximum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#max(Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T extends Comparable<? super T>> T max(Collection<? extends T> collection) {
		Objects.requireNonNull(collection, "collection");
		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(max) > 0)
				max = element;
		}

		return max;
	}

	/**
	 * Find the maximum element in the given {@code collection} based on the given{@code
	 * comparator}.
	 *
	 * @param collection the collection to find the maximum element stored in it.
	 * @param comparator the comparator to be used to compare between the elements. A null value
	 *                   indicates that the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the maximum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#max(Collection, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> T max(Collection<? extends T> collection, Comparator<? super T> comparator) {
		Objects.requireNonNull(collection, "collection");
		if (comparator == null)
			return (T) Collections.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, max) > 0)
				max = element;
		}

		return max;
	}

	/**
	 * Find the minimum element in the given {@code collection} based on the <i>natural
	 * ordering</i>.
	 *
	 * @param collection the collection to find the minimum element stored in it.
	 * @param <T>        the type of the elements.
	 * @return the minimum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#min(Collection)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T extends Comparable<? super T>> T min(Collection<? extends T> collection) {
		Objects.requireNonNull(collection, "collection");
		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(min) < 0)
				min = element;
		}

		return min;
	}

	/**
	 * Find the minimum element in the given {@code collection} based on the given{@code
	 * comparator}.
	 *
	 * @param collection the collection to find the minimum element stored in it.
	 * @param comparator the comparator to be used to compare between the elements. A null value
	 *                   indicates that the elements' natural ordering should be used.
	 * @param <T>        the type of the elements.
	 * @return the minimum element in the given {@code collection}.
	 * @throws NullPointerException   if the given {@code collection} is null.
	 * @throws NoSuchElementException if the given {@code collection} is empty.
	 * @see java.util.Collections#min(Collection, Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> T min(Collection<? extends T> collection, Comparator<? super T> comparator) {
		Objects.requireNonNull(collection, "collection");
		if (comparator == null)
			return (T) Collections.max((Collection) collection);

		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (comparator.compare(element, min) < 0)
				min = element;
		}

		return min;
	}

	/**
	 * Construct a new list from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed list will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a list from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @see java.util.Collections#nCopies(int, Object)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesList<T> nCopies(int n, T element) {
		return new NCopiesList(n, element);
	}

	/**
	 * Construct a new collection from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed collection will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a collection from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesCollection<T> nCopiesCollection(int n, T element) {
		return new NCopiesCollection(n, element);
	}

	/**
	 * Construct a new enumeration from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed enumeration will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a enumeration from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesEnumeration<T> nCopiesEnumeration(int n, T element) {
		return new NCopiesEnumeration(n, element);
	}

	/**
	 * Construct a new iterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed iterator will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a iterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesIterator<T> nCopiesIterator(int n, T element) {
		return new NCopiesIterator(n, element);
	}

	/**
	 * Construct a new iterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed iterator will repeat the given {@code
	 *                n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a iterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesListIterator<T> nCopiesListIterator(int n, T element) {
		return new NCopiesListIterator(n, element);
	}

	/**
	 * Construct a new spliterator from repeating the given {@code element} {@code n} many times.
	 *
	 * @param n       the number of repetition the constructed spliterator will repeat the given
	 *                {@code n}.
	 * @param element the element to be repeated.
	 * @param <T>     the type of the element.
	 * @return a spliterator from repeating the given {@code element} {@code n} many times.
	 * @throws IllegalArgumentException if {@code n < 0}.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> NCopiesSpliterator<T> nCopiesSpliterator(int n, T element) {
		return new NCopiesSpliterator(n, element);
	}

	/**
	 * Construct new set view from the given {@code map}.
	 *
	 * @param map the map backing the constructed set.
	 * @param <T> the type of the elements.
	 * @return a set view from the given {@code map}.
	 * @throws NullPointerException if the given {@code map}.
	 * @see java.util.Collections#newSetFromMap(Map)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> MapSet<T> newSetFromMap(Map<T, Boolean> map) {
		Objects.requireNonNull(map, "map");
		return new MapSet(map);
	}

	/**
	 * Replace all the elements that equals the given {@code oldValue} in the given {@code list}
	 * with the given {@code newValue}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#replaceAll(List, Object, Object)}
	 * since the field {@link java.util.Collections#REPLACEALL_THRESHOLD} is not accessible.
	 *
	 * @param list     the list to be modified.
	 * @param oldValue the value to be replaced with the given {@code newValue}.
	 * @param newValue the new value to replace all the elements that equals the given {@code
	 *                 oldValue} with.
	 * @param <T>      the type of the elements.
	 * @return true, if the given {@code list} changed as a result of this call.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#replaceAll(List, Object, Object)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> boolean replaceAll(List<T> list, T oldValue, T newValue) {
		//can not access java.util.Collections.REPLACEALL_THRESHOLD
		return java.util.Collections.replaceAll(list, oldValue, newValue);
	}

	/**
	 * Reverse the given {@code list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#reverse(List)} since the field
	 * {@link java.util.Collections#REVERSE_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be reversed.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#reverse(List)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> void reverse(List<T> list) {
		//can not access java.util.Collections.REVERSE_THRESHOLD
		java.util.Collections.reverse(list);
	}

	/**
	 * Return the reversed <i>natural ordering</i> comparator.
	 *
	 * @param <T> the type of the elements.
	 * @return the <i>natural ordering</i> comparator.
	 * @see java.util.Collections#reverseOrder()
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> Comparator<T> reverseOrder() {
		return Collections.REVERSE_NATURAL_COMPARATOR;
	}

	/**
	 * Return a reversed comparator for the given {@code comparator}.
	 *
	 * @param <T>        the type of the elements.
	 * @param comparator the comparator to be reversed. Null value will return the reverse of the
	 *                   <i>natural ordering</i>.
	 * @return a reversed comparator of the given {@code comparator}.
	 * @see java.util.Collections#reverseOrder(Comparator)
	 * @since 0.1.5 ~2020.08.24
	 */
	static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
		//noinspection AccessingNonPublicFieldOfAnotherObject
		return comparator == null ?
			   Collections.REVERSE_NATURAL_COMPARATOR :
			   comparator instanceof ReverseComparator ?
			   ((ReverseComparator<T>) comparator).comparator :
			   new ReverseComparator(comparator);
	}

	/**
	 * Rotate teh given given by the given {@code degree}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#rotate(List, int)} since the field
	 * {@link java.util.Collections#ROTATE_THRESHOLD} is not accessible.
	 *
	 * @param list   the list to be rotated.
	 * @param degree how much steps the elements will be shifted by.
	 * @param <T>    the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#rotate(List, int)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void rotate(List<T> list, int degree) {
		//can not access java.util.Collections.ROTATE_THRESHOLD
		java.util.Collections.rotate(list, degree);
	}

	/**
	 * Randomly swap the elements in the given {@code list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#shuffle(List)} since the field
	 * {@link java.util.Collections#SHUFFLE_THRESHOLD} is not accessible.
	 *
	 * @param list the list to be shuffled.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#shuffle(List)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void shuffle(List<T> list) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list);
	}

	/**
	 * Randomly (depending on the given {@code random}) swap the elements in the given {@code
	 * list}.
	 * <p>
	 * Note: This method redirect to {@link java.util.Collections#shuffle(List, Random)} since the
	 * field {@link java.util.Collections#ROTATE_THRESHOLD} is not accessible.
	 *
	 * @param list   the list to be shuffled.
	 * @param random the source of randomness.
	 * @param <T>    the type of the elements.
	 * @throws NullPointerException if the given {@code list} or {@code random} is null.
	 * @see java.util.Collections#shuffle(List, Random)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void shuffle(List<T> list, Random random) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list, random);
	}

	/**
	 * Get a set that has only the given {@code element}.
	 *
	 * @param element the only element the returned set will have.
	 * @param <T>     the type of the element.
	 * @return a set with only the given {@code element}.
	 * @see java.util.Collections#singleton(Object)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> SingletonSet<T> singleton(T element) {
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
	static <T> SingletonCollection<T> singletonCollection(T element) {
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
	static <T> SingletonEnumeration<T> singletonEnumeration(T element) {
		return new SingletonEnumeration(element);
	}

	/**
	 * Get an iterator with only the given {@code element}.
	 *
	 * @param element the only element the returned iterator will have.
	 * @param <T>     the type of the element.
	 * @return an iterator with only the given {@code element}.
	 * @see java.util.Collections#singletonIterator(Object)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> SingletonIterator<T> singletonIterator(T element) {
		return new SingletonIterator(element);
	}

	/**
	 * Get a list that has only the given {@code element}.
	 *
	 * @param element the only element the returned list will have.
	 * @param <T>     the type of the element.
	 * @return a list with only the given {@code element}.
	 * @see java.util.Collections#singletonList(Object)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> SingletonList<T> singletonList(T element) {
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
	static <T> SingletonListIterator<T> singletonListIterator(T element) {
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
	 * @see java.util.Collections#singletonMap(Object, Object)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> SingletonMap<K, V> singletonMap(K key, V value) {
		return new SingletonMap(key, value);
	}

	/**
	 * Get an spliterator with only the given {@code element}.
	 *
	 * @param element the only element the returned spliterator will have.
	 * @param <T>     the type of the element.
	 * @return an spliterator with only the given {@code element}.
	 * @see java.util.Collections#singletonSpliterator(Object)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> SingletonSpliterator<T> singletonSpliterator(T element) {
		return new SingletonSpliterator(element);
	}

	/**
	 * Sort the given {@code list} depending on the <i>natural ordering</i>.
	 *
	 * @param list the list to be sorted.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#sort(List)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T extends Comparable<? super T>> void sort(List<T> list) {
		Objects.requireNonNull(list, "list");
		//as in java.util.Collections.sort(List)
		list.sort(null);
	}

	/**
	 * Sort the given {@code list} depending on the given {@code comparator}.
	 *
	 * @param list       the list to be sorted.
	 * @param comparator the comparator to be used to sort the given {@code list}. Null will make
	 *                   the method sort depending on the <i>natural ordering</i> of the elements.
	 * @param <T>        the type of the elements.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#sort(List, Comparator)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void sort(List<T> list, Comparator<? super T> comparator) {
		Objects.requireNonNull(list, "list");
		//as in java.util.Collections.sort(List, Comparator)
		list.sort(comparator);
	}

	/**
	 * Swap the element in the given {@code list} at the index {@code i} with the element in the
	 * index {@code j}.
	 *
	 * @param list the list to swap the two of its elements.
	 * @param i    the index of the first element in the given {@code list} to be swapped.
	 * @param j    the index of the second element in the given {@code list} to be swapped.
	 * @param <T>  the type of the elements.
	 * @throws NullPointerException      if the given {@code list} is null.
	 * @throws IndexOutOfBoundsException if {@code i < 0} or {@code j < 0} or {@code i >=
	 *                                   list.size()} or {@code j >= list.size()}.
	 * @see java.util.Collections#swap(List, int, int)
	 * @since 0.1.5 ~2020.08.25
	 */
	static <T> void swap(List<T> list, int i, int j) {
		Objects.requireNonNull(list, "list");
		list.set(i, list.set(j, list.get(i)));
	}

	/**
	 * Construct a synchronized view of the given {@code collection}.
	 *
	 * @param collection the collection to get a synchronized view of.
	 * @param <T>        the type of the elements.
	 * @return a synchronized view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> collection) {
		return new SynchronizedCollection(collection);
	}

	/**
	 * Construct a synchronized view of the given {@code collection}.
	 *
	 * @param collection the collection to get a synchronized view of.
	 * @param mutex      the object the returned collection will synchronized on.
	 * @param <T>        the type of the elements.
	 * @return a synchronized view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> collection, Object mutex) {
		return new SynchronizedCollection(collection, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code list}.
	 *
	 * @param list the list to get a synchronized view of.
	 * @param <T>  the type of the elements.
	 * @return a synchronized view of the given {@code list}.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#synchronizedList(List)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedList<T> synchronizedList(List<T> list) {
		return list instanceof RandomAccess ?
			   new SynchronizedRandomAccessList(list) :
			   new SynchronizedList(list);
	}

	/**
	 * Construct a synchronized view of the given {@code list}.
	 *
	 * @param list  the list to get a synchronized view of.
	 * @param mutex the object the returned list will synchronized on.
	 * @param <T>   the type of the elements.
	 * @return a synchronized view of the given {@code list}.
	 * @throws NullPointerException if the given {@code collection} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedList<T> synchronizedList(List<T> list, Object mutex) {
		return list instanceof RandomAccess ?
			   new SynchronizedRandomAccessList(list, mutex) :
			   new SynchronizedList(list, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map the map to get a synchronized view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @see java.util.Collections#synchronizedMap(Map)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedMap<K, V> synchronizedMap(Map<K, V> map) {
		return new SynchronizedMap(map);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map   the map to get a synchronized view of.
	 * @param mutex the object the returned map will synchronized on.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedMap<K, V> synchronizedMap(Map<K, V> map, Object mutex) {
		return new SynchronizedMap(map, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map the map to get a synchronized view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @see java.util.Collections#synchronizedNavigableMap(NavigableMap)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedNavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> map) {
		return new SynchronizedNavigableMap(map);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map   the map to get a synchronized view of.
	 * @param mutex the object the returned map will synchronized on.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedNavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> map, Object mutex) {
		return new SynchronizedNavigableMap(map, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set the set to get a synchronized view of.
	 * @param <T> the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#synchronizedNavigableSet(NavigableSet)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedNavigableSet<T> synchronizedNavigableSet(NavigableSet<T> set) {
		return new SynchronizedNavigableSet(set);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set   the set to get a synchronized view of.
	 * @param mutex the object the returned set will synchronized on.
	 * @param <T>   the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedNavigableSet<T> synchronizedNavigableSet(NavigableSet<T> set, Object mutex) {
		return new SynchronizedNavigableSet(set, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set the set to get a synchronized view of.
	 * @param <T> the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#synchronizedSet(Set)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedSet<T> synchronizedSet(Set<T> set) {
		return new SynchronizedSet(set);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set   the set to get a synchronized view of.
	 * @param mutex the object the returned set will synchronized on.
	 * @param <T>   the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedSet<T> synchronizedSet(Set<T> set, Object mutex) {
		return new SynchronizedSet(set, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map the map to get a synchronized view of.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedSortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> map) {
		return new SynchronizedSortedMap(map);
	}

	/**
	 * Construct a synchronized view of the given {@code map}.
	 *
	 * @param map   the map to get a synchronized view of.
	 * @param mutex the object the returned map will synchronized on.
	 * @param <K>   the type of the keys.
	 * @param <V>   the type of the values.
	 * @return a synchronized view of the given {@code map}.
	 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <K, V> SynchronizedSortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> map, Object mutex) {
		return new SynchronizedSortedMap(map, mutex);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set the set to get a synchronized view of.
	 * @param <T> the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedSortedSet<T> synchronizedSortedSet(SortedSet<T> set) {
		return new SynchronizedSortedSet(set);
	}

	/**
	 * Construct a synchronized view of the given {@code set}.
	 *
	 * @param set   the set to get a synchronized view of.
	 * @param mutex the object the returned set will synchronized on.
	 * @param <T>   the type of the elements.
	 * @return a synchronized view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
	 * @since 0.1.5 ~2020.08.22
	 */
	static <T> SynchronizedSortedSet<T> synchronizedSortedSet(SortedSet<T> set, Object mutex) {
		return new SynchronizedSortedSet(set, mutex);
	}

	/**
	 * Construct an unmodifiable view of the given {@code collection}.
	 *
	 * @param collection the collection to get an unmodifiable view of.
	 * @param <T>        the type of the elements.
	 * @return an unmodifiable view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
	 * @see java.util.Collections#unmodifiableCollection(Collection)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> UnmodifiableCollection<T> unmodifiableCollection(Collection<? extends T> collection) {
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
	static <T> UnmodifiableEnumeration<T> unmodifiableEnumeration(Enumeration<T> enumeration) {
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
	static <T> UnmodifiableIterator<T> unmodifiableIterator(Iterator<T> iterator) {
		return new UnmodifiableIterator(iterator);
	}

	/**
	 * Construct an unmodifiable view of the given {@code list}.
	 *
	 * @param list the list to get an unmodifiable view of.
	 * @param <T>  the type of the elements.
	 * @return an unmodifiable view of the given {@code list}.
	 * @throws NullPointerException if the given {@code list} is null.
	 * @see java.util.Collections#unmodifiableList(List)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> UnmodifiableList<T> unmodifiableList(List<? extends T> list) {
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
	static <T> UnmodifiableListIterator<T> unmodifiableListIterator(ListIterator<T> iterator) {
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
	 * @see java.util.Collections#unmodifiableMap(Map)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> UnmodifiableMap<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
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
	 * @see java.util.Collections#unmodifiableNavigableMap(NavigableMap)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> map) {
		return new UnmodifiableNavigableMap(map);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#unmodifiableNavigableSet(NavigableSet)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> UnmodifiableNavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> set) {
		return new UnmodifiableNavigableSet(set);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#unmodifiableSet(Set)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> UnmodifiableSet<T> unmodifiableSet(Set<T> set) {
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
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> UnmodifiableSortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> map) {
		return new UnmodifiableSortedMap(map);
	}

	/**
	 * Construct an unmodifiable view of the given {@code set}.
	 *
	 * @param set the set to get an unmodifiable view of.
	 * @param <T> the type of the elements.
	 * @return an unmodifiable view of the given {@code set}.
	 * @throws NullPointerException if the given {@code set} is null.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> UnmodifiableSortedSet<T> unmodifiableSortedSet(SortedSet<T> set) {
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
	static <T> UnmodifiableSpliterator<T> unmodifiableSpliterator(Spliterator<T> spliterator) {
		return new UnmodifiableSpliterator(spliterator);
	}

	/**
	 * A collection wrapping another collection. Preventing that other collection from storing an
	 * element with an unappreciated type.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	class CheckedCollection<E> implements Collection<E>, Serializable {
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
	class CheckedEnumeration<E> implements Enumeration<E> {
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
	class CheckedIterator<E> implements Iterator<E> {
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
	class CheckedList<E> extends CheckedCollection<E> implements List<E> {
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
	class CheckedListIterator<E> extends CheckedIterator<E> implements ListIterator<E> {
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
	class CheckedMap<K, V> implements Map<K, V>, Serializable {
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
				Entry<K, V> entry = (Entry) object;
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
		public static class CheckedEntry<K, V> implements Entry<K, V>, Serializable {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 6060443667289298487L;

			/**
			 * The wrapped entry.
			 *
			 * @since 0.1.5 ~2020.08.19
			 */
			protected final Entry<K, V> entry;
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
				this.iterator.forEachRemaining(entry -> consumer.accept(new CheckedEntry<>(entry, this.valueType)));
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
				return object == this ||
					   object instanceof Set &&
					   ((Set) object).size() == this.entrySet.size() &&
					   this.containsAll((Set) object);
			}

			@Override
			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.entrySet.forEach(entry -> consumer.accept(new CheckedEntry<>(entry, this.valueType)));
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
				return this.entrySet.removeIf(entry -> predicate.test(new CheckedEntry<>(entry, this.valueType)));
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
				this.spliterator.forEachRemaining(entry -> consumer.accept(new CheckedEntry<>(entry, this.valueType)));
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
				return this.spliterator.tryAdvance(entry -> consumer.accept(new CheckedEntry<>(entry, this.valueType)));
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
	class CheckedNavigableMap<K, V> extends CheckedSortedMap<K, V> implements NavigableMap<K, V> {
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
		public CheckedNavigableMap<K, V> headMap(K endKey) {
			return new CheckedNavigableMap(this.map.headMap(
					endKey,
					false
			), this.keyType, this.valueType);
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
		public CheckedNavigableMap<K, V> subMap(K beginKey, K endKey) {
			return new CheckedNavigableMap(this.map.subMap(
					beginKey,
					true,
					endKey,
					false
			), this.keyType, this.valueType);
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
		public CheckedNavigableMap<K, V> tailMap(K beginKey) {
			return new CheckedNavigableMap(this.map.tailMap(
					beginKey,
					true
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
	class CheckedNavigableSet<E> extends CheckedSortedSet<E> implements NavigableSet<E> {
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
		public CheckedNavigableSet<E> headSet(E endElement) {
			return new CheckedNavigableSet(this.set.headSet(
					endElement,
					false
			), this.type);
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
		public CheckedNavigableSet<E> subSet(E beginElement, E endElement) {
			return new CheckedNavigableSet(this.set.subSet(
					beginElement,
					true,
					endElement,
					false
			), this.type);
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
		public CheckedNavigableSet<E> tailSet(E beginElement) {
			return new CheckedNavigableSet(this.set.tailSet(
					beginElement,
					true
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
	class CheckedQueue<E> extends CheckedCollection<E> implements Queue<E> {
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
	class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess {
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
	class CheckedSet<E> extends CheckedCollection<E> implements Set<E> {
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
	class CheckedSortedMap<K, V> extends CheckedMap<K, V> implements SortedMap<K, V> {
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
	class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E> {
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
			return new CheckedSortedSet(this.set.headSet(
					endElement
			), this.type);
		}

		@Override
		public E last() {
			return this.set.last();
		}

		@Override
		public CheckedSortedSet<E> subSet(E beginElement, E endElement) {
			return new CheckedSortedSet(this.set.subSet(
					beginElement,
					endElement
			), this.type);
		}

		@Override
		public CheckedSortedSet<E> tailSet(E beginElement) {
			return new CheckedSortedSet(this.set.tailSet(
					beginElement
			), this.type);
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
	class CheckedSpliterator<E> implements Spliterator<E> {
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

	/**
	 * A Last-In-First-Out queue from wrapping a deque.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.24
	 */
	class DequeLIFOQueue<E> implements Queue<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7406211233756224981L;

		/**
		 * The deque backing this queue.
		 *
		 * @since 0.1.5 ~2020.08.24
		 */
		protected final Deque<E> deque;

		/**
		 * Construct a new Last-In-First-Out queue backed by the given {@code deque}.
		 *
		 * @param deque the deque backing the constructed queue.
		 * @throws NullPointerException if the given {@code deque} is null.
		 * @since 0.1.5 ~2020.08.24
		 */
		public DequeLIFOQueue(Deque<E> deque) {
			Objects.requireNonNull(deque, "deque");
			this.deque = deque;
		}

		@Override
		public boolean add(E element) {
			this.deque.addFirst(element);
			return true;
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			boolean modified = false;

			for (E element : collection) {
				this.deque.addFirst(element);
				modified = true;
			}

			return modified;
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

	/**
	 * An empty collection. Use {@link #EMPTY_COLLECTION} if you do not want to initialize a new
	 * instance.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.18
	 */
	class EmptyCollection<E> implements Collection<E>, Serializable {
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
	class EmptyEnumeration<E> implements Enumeration<E> {
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
	class EmptyIterator<E> implements Iterator<E> {
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
	class EmptyList<E> extends EmptyCollection<E> implements List<E>, RandomAccess {
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
	class EmptyListIterator<E> extends EmptyIterator<E> implements ListIterator<E> {
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
	class EmptyMap<K, V> implements Map<K, V>, Serializable {
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
	class EmptyNavigableMap<K, V> extends EmptySortedMap<K, V> implements NavigableMap<K, V> {
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
		public EmptyNavigableMap<K, V> headMap(K endKey) {
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
		public EmptyNavigableMap<K, V> subMap(K beginKey, K endKey) {
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
		public EmptyNavigableMap<K, V> tailMap(K beginKey) {
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
	class EmptyNavigableSet<E> extends EmptySortedSet<E> implements NavigableSet<E> {
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
		public EmptyNavigableSet<E> headSet(E endElement) {
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
		public EmptyNavigableSet<E> subSet(E beginElement, E endElement) {
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
		public EmptyNavigableSet<E> tailSet(E beginElement) {
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
	class EmptyQueue<E> extends EmptyCollection<E> implements Queue<E> {
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
	class EmptySet<E> extends EmptyCollection<E> implements Set<E> {
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
	class EmptySortedMap<K, V> extends EmptyMap<K, V> implements SortedMap<K, V> {
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
	class EmptySortedSet<E> extends EmptySet<E> implements SortedSet<E> {
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
	class EmptySpliterator<E> implements Spliterator<E> {
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

	/**
	 * An enumeration wrapping an iterator.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.24
	 */
	class IteratorEnumeration<E> implements Enumeration<E> {
		/**
		 * The wrapped iterator.
		 *
		 * @since 0.1.5 ~2020.08.24
		 */
		protected final Iterator<E> iterator;

		/**
		 * Construct a new enumeration from the given {@code iterator}.
		 *
		 * @param iterator the iterator to be wrapped into an enumeration.
		 * @throws NullPointerException if the given {@code iterator} is null.
		 * @since 0.1.5 ~2020.08.24
		 */
		public IteratorEnumeration(Iterator<E> iterator) {
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
	 * A set from wrapping a map.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.24
	 */
	class MapSet<E> implements Set<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -4325561989691359892L;

		/**
		 * The wrapped map.
		 *
		 * @since 0.1.5 ~2020.08.24
		 */
		protected final Map<E, Boolean> map;
		/**
		 * The key set (from the wrapped map).
		 *
		 * @since 0.1.5 ~2020.08.24
		 */
		protected transient Set<E> keySet;

		/**
		 * Construct a new set from wrapping the given {@code map}.
		 *
		 * @param map the map to be wrapped.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.24
		 */
		public MapSet(Map<E, Boolean> map) {
			//checking if the 'map' is empty is a completely redundant check
			Objects.requireNonNull(map, "map");
			this.map = map;
			this.keySet = map.keySet();
		}

		@Override
		public boolean add(E element) {
			return this.map.put(element, Boolean.TRUE) == null;
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			boolean modified = false;

			for (E element : collection)
				modified |= this.map.put(element, Boolean.TRUE) == null;

			return modified;
		}

		@Override
		public void clear() {
			this.keySet.clear();
		}

		@Override
		public boolean contains(Object object) {
			return this.keySet.contains(object);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return this.keySet.containsAll(collection);
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			return object == this || this.keySet.equals(object);
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			this.keySet.forEach(consumer);
		}

		@Override
		public int hashCode() {
			return this.keySet.hashCode();
		}

		@Override
		public boolean isEmpty() {
			return this.keySet.isEmpty();
		}

		@Override
		public Iterator<E> iterator() {
			return this.keySet.iterator();
		}

		@Override
		public Stream<E> parallelStream() {
			return this.keySet.parallelStream();
		}

		@Override
		public boolean remove(Object object) {
			return this.keySet.remove(object);
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			return this.keySet.removeAll(collection);
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			return this.keySet.removeIf(predicate);
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			return this.keySet.retainAll(collection);
		}

		@Override
		public int size() {
			return this.keySet.size();
		}

		@Override
		public Spliterator<E> spliterator() {
			return this.keySet.spliterator();
		}

		@Override
		public Stream<E> stream() {
			return this.keySet.stream();
		}

		@Override
		public Object[] toArray() {
			return this.keySet.toArray();
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return this.keySet.toArray(array);
		}

		@Override
		public String toString() {
			return this.keySet.toString();
		}

		@SuppressWarnings("JavaDoc")
		private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
			stream.defaultReadObject();
			this.keySet = this.map.keySet();
		}
	}

	/**
	 * A collection from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2273830207474183967L;

		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;

		/**
		 * Construct a new collection from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesCollection(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
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
			return this.n != 0 && this.eq(object, this.element);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			if (this.n != 0) {
				for (Object object : collection) {
					if (this.eq(object, this.element))
						continue;

					return false;
				}

				return true;
			}

			return false;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			for (int i = 0; i < this.n; i++)
				consumer.accept(this.element);
		}

		@Override
		public boolean isEmpty() {
			return this.n == 0;
		}

		@Override
		public NCopiesIterator<E> iterator() {
			return new NCopiesIterator(this.n, this.element);
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(new NCopiesSpliterator(
					this.n, this.element
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
		public boolean removeIf(Predicate<? super E> predicate) {
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
			return this.n;
		}

		@Override
		public NCopiesSpliterator<E> spliterator() {
			return new NCopiesSpliterator(this.n, this.element);
		}

		@Override
		public Stream<E> stream() {
			return StreamSupport.stream(new NCopiesSpliterator(
					this.n, this.element
			), false);
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			T[] product = array;

			if (array.length < this.n)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), this.n);
			else if (product.length > this.n)
				product[this.n] = null;

			for (int i = 0; i < this.n; i++)
				product[i] = (T) this.element;

			return product;
		}

		@Override
		public Object[] toArray() {
			Object[] array = new Object[this.n];

			for (int i = 0; i < this.n; i++)
				array[i] = this.element;

			return array;
		}

		@Override
		public String toString() {
			if (this.n == 0)
				return "[]";

			StringJoiner joiner = new StringJoiner(", ", "[", "]");

			for (int i = 0; i < this.n; i++)
				joiner.add(String.valueOf(this.element));

			return joiner.toString();
		}

		/**
		 * Determine if the given two elements are equal or not. This is the base equality check in
		 * this class and it should be for its subclasses.
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
	}

	/**
	 * An enumeration from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesEnumeration<E> implements Enumeration<E> {
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new enumeration from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesEnumeration(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public boolean hasMoreElements() {
			return this.i < this.n;
		}

		@Override
		public E nextElement() {
			int i = this.i;

			if (i < this.n) {
				this.i++;
				return this.element;
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * An iterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesIterator<E> implements Iterator<E> {
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesIterator(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;
			this.i = this.n;

			while (i++ < this.n)
				consumer.accept(this.element);
		}

		@Override
		public boolean hasNext() {
			return this.i < this.n;
		}

		@Override
		public E next() {
			int i = this.i;

			if (i < this.n) {
				this.i++;
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
	 * A list from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesList<E> extends NCopiesCollection<E> implements List<E>, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8959888285748864462L;

		/**
		 * Construct a new list from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesList(int n, E element) {
			super(n, element);
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
				   ((List) object).size() == this.n &&
				   this.containsAll((List) object);
		}

		@Override
		public E get(int index) {
			if (index < 0)
				throw new IndexOutOfBoundsException("index(" + index + ") < 0");
			if (index >= this.n)
				throw new IndexOutOfBoundsException("index(" + index + ") >= n(" + this.n + ")");

			return this.element;
		}

		@Override
		public int hashCode() {
			int hashCode = 1;
			int h = this.element == null ? 0 : this.element.hashCode();

			for (int i = 0; i < this.n; i++)
				hashCode = 31 * hashCode + h;

			return h;
		}

		@Override
		public int indexOf(Object object) {
			return this.n != 0 && this.eq(object, this.element) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return this.n != 0 && this.eq(object, this.element) ? this.n - 1 : -1;
		}

		@Override
		public NCopiesListIterator<E> listIterator() {
			return new NCopiesListIterator(this.n, this.element);
		}

		@Override
		public NCopiesListIterator<E> listIterator(int index) {
			return new NCopiesListIterator(this.n, this.element, index);
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
		public NCopiesList<E> subList(int beginIndex, int endIndex) {
			if (beginIndex < 0)
				throw new IndexOutOfBoundsException(
						"beginIndex(" + beginIndex + ") < 0"
				);
			if (endIndex > this.n)
				throw new IndexOutOfBoundsException(
						"endIndex(" + endIndex + ") > n(" + this.n + ")"
				);
			if (endIndex < beginIndex)
				throw new IllegalArgumentException(
						"endIndex(" + endIndex + ") < beginIndex(" + beginIndex + ")"
				);
			return new NCopiesList(endIndex - beginIndex, this.element);
		}
	}

	/**
	 * An iterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesListIterator<E> extends NCopiesIterator<E> implements ListIterator<E> {
		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesListIterator(int n, E element) {
			super(n, element);
		}

		/**
		 * Construct a new iterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @param i       the initial position of the constructed iterator.
		 * @throws IllegalArgumentException  if {@code n < 0}.
		 * @throws IndexOutOfBoundsException if {@code i < 0} or {@code i > n}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesListIterator(int n, E element, int i) {
			super(n, element);
			if (i < 0)
				throw new IndexOutOfBoundsException("i(" + i + ") < 0");
			if (i > n)
				throw new IndexOutOfBoundsException("i(" + i + ") > n(" + n + ")");
			//noinspection AssignmentToSuperclassField
			this.i = i;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasPrevious() {
			return this.i > 0;
		}

		@Override
		public int nextIndex() {
			return this.i;
		}

		@Override
		public E previous() {
			int i = this.i;

			if (i > 0) {
				this.i--;
				return this.element;
			}

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.i - 1;
		}

		@Override
		public void set(E element) {
			throw new UnsupportedOperationException("set");
		}
	}

	/**
	 * A spliterator from repeating an element (n) number of times.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class NCopiesSpliterator<E> implements Spliterator<E> {
		/**
		 * The characteristics of this spliterator.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		public static final int CHARACTERISTICS = Spliterator.SIZED |
												  Spliterator.SUBSIZED |
												  Spliterator.ORDERED |
												  Spliterator.IMMUTABLE;
		/**
		 * The repeated element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final E element;
		/**
		 * The number of repetition of the element.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final int n;
		/**
		 * The current index.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected int i;

		/**
		 * Construct a new spliterator from {@code n} copies of the given {@code element}.
		 *
		 * @param n       the number of repetition of the element.
		 * @param element the element to be repeated.
		 * @throws IllegalArgumentException if {@code n < 0}.
		 * @since 0.1.5 ~2020.08.22
		 */
		public NCopiesSpliterator(int n, E element) {
			if (n < 0)
				throw new IllegalArgumentException("n(" + n + ") < 0");
			this.element = element;
			this.n = n;
		}

		@Override
		public int characteristics() {
			return NCopiesSpliterator.CHARACTERISTICS;
		}

		@Override
		public long estimateSize() {
			return this.n;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;
			this.i = this.n;

			while (i++ < this.n)
				consumer.accept(this.element);
		}

		@Override
		public Comparator<? super E> getComparator() {
			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.n;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (NCopiesSpliterator.CHARACTERISTICS & characteristics) == characteristics;
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int i = this.i;

			if (i < this.n) {
				this.i++;
				consumer.accept(this.element);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator<E> trySplit() {
			int i = this.i;
			int half = this.n - i >>> 1;

			if (half > 0) {
				this.i = i + half;
				return new NCopiesSpliterator(half, this.element);
			}

			return null;
		}
	}

	/**
	 * Reversed comparator based on another comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.25
	 */
	class ReverseComparator<E> implements Comparator<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8830222155440983107L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.08.25
		 */
		protected final Comparator<E> comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.08.25
		 */
		public ReverseComparator(Comparator<E> comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(E element, E other) {
			return this.comparator.compare(other, element);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof ReverseComparator &&
				   ((ReverseComparator) object).comparator.equals(this.comparator);
		}

		@Override
		public int hashCode() {
			return this.comparator.hashCode() ^ Integer.MIN_VALUE;
		}

		@Override
		public Comparator<E> reversed() {
			return this.comparator;
		}
	}

	/**
	 * Reversed <i>natural ordering</i> comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.25
	 */
	class ReverseNaturalComparator<E extends Comparable<E>> implements Comparator<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1204085588134449462L;

		@Override
		public int compare(E element, E other) {
			return other.compareTo(element);
		}

		@Override
		public Comparator<E> reversed() {
			return Comparator.naturalOrder();
		}
	}

	/**
	 * A collection with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	class SingletonCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
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
			return this.eq(object, this.element);
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			for (Object object : collection) {
				if (this.eq(object, this.element))
					continue;

				return false;
			}

			return true;
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

		/**
		 * Determine if the given two elements are equal or not. This is the base equality check in
		 * this class and it should be for its subclasses.
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
	}

	/**
	 * An enumeration with a single element.
	 *
	 * @param <E> the type of the element.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	class SingletonEnumeration<E> implements Enumeration<E> {
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
	class SingletonIterator<E> implements Iterator<E> {
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
	class SingletonList<E> extends SingletonCollection<E> implements List<E>, RandomAccess {
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
			return this.eq(object, this.element) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object object) {
			return this.eq(object, this.element) ? 0 : -1;
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
	class SingletonListIterator<E> extends SingletonIterator<E> implements ListIterator<E> {
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
	class SingletonMap<K, V> implements Map<K, V>, Serializable {
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
			return this.keq(key, this.key);
		}

		@Override
		public boolean containsValue(Object value) {
			return this.veq(value, this.value);
		}

		@Override
		public SingletonSet<Entry<K, V>> entrySet() {
			return new SingletonSet(new AbstractMap.SimpleImmutableEntry(this.key, this.value));
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Map &&
				   ((Map) object).size() == 1 &&
				   ((Map) object).containsKey(this.key) &&
				   ((Map) object).containsValue(this.value);
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			consumer.accept(this.key, this.value);
		}

		@Override
		public V get(Object key) {
			return this.keq(key, this.key) ? this.value : null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return this.keq(key, this.key) ? this.value : defaultValue;
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

		/**
		 * Determine if the given two elements are equal or not. This is the base equality check in
		 * this class and it should be for its subclasses.
		 *
		 * @param key the first key.
		 * @param k   the second key.
		 * @return true, if the given {@code key} equals the given {@code k} in this class's
		 * 		standard.
		 * @since 0.1.5 ~2020.08.18
		 */
		protected boolean keq(Object key, K k) {
			return key == k || key != null && key.equals(k);
		}

		/**
		 * Determine if the given two elements are equal or not. This is the base equality check in
		 * this class and it should be for its subclasses.
		 *
		 * @param value the first value.
		 * @param v     the second value.
		 * @return true, if the given {@code value} equals the given {@code v} in this class's
		 * 		standard.
		 * @since 0.1.5 ~2020.08.18
		 */
		protected boolean veq(Object value, V v) {
			return value == v || value != null && value.equals(v);
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
	class SingletonSet<E> extends SingletonCollection<E> implements Set<E> {
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
	class SingletonSpliterator<E> implements Spliterator<E> {
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

	/**
	 * A collection view backed by another collection and all its operations are synchronized with a
	 * specified object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class SynchronizedCollection<E> implements Collection<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 5789337830471750010L;

		/**
		 * The wrapped collection.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final Collection<E> collection;
		/**
		 * The object all the operations of this collection is synchronized with.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final Object mutex;

		/**
		 * Construct a new synchronized collection view for the given {@code collection}.
		 *
		 * @param collection the collection to be wrapped.
		 * @throws NullPointerException if the given {@code collection} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedCollection(Collection<E> collection) {
			Objects.requireNonNull(collection, "collection");
			this.collection = collection;
			this.mutex = this;
		}

		/**
		 * Construct a new synchronized collection view with the given {@code mutex} for the given
		 * {@code collection}.
		 *
		 * @param collection the collection to be wrapped.
		 * @param mutex      the object the constructed collection will be synchronized with.
		 * @throws NullPointerException if the given {@code collection} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedCollection(Collection<E> collection, Object mutex) {
			Objects.requireNonNull(collection, "collection");
			Objects.requireNonNull(mutex, "mutex");
			this.collection = collection;
			this.mutex = mutex;
		}

		@Override
		public boolean add(E element) {
			synchronized (this.mutex) {
				return this.collection.add(element);
			}
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			synchronized (this.mutex) {
				return this.collection.addAll(collection);
			}
		}

		@Override
		public void clear() {
			synchronized (this.mutex) {
				this.collection.clear();
			}
		}

		@Override
		public boolean contains(Object object) {
			synchronized (this.mutex) {
				return this.collection.contains(object);
			}
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			synchronized (this.mutex) {
				return this.collection.containsAll(collection);
			}
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			synchronized (this.mutex) {
				this.collection.forEach(consumer);
			}
		}

		@Override
		public boolean isEmpty() {
			synchronized (this.mutex) {
				return this.collection.isEmpty();
			}
		}

		@Override
		public Iterator<E> iterator() {
			//see java.util.Collections.SynchronizedCollection.iterator()
			//"Must be manually synched by user!"
			return this.collection.iterator();
		}

		@Override
		public Stream<E> parallelStream() {
			//see java.util.Collections.SynchronisedCollection.parallelStream()
			//"Must be manually synched by user!"
			return this.collection.parallelStream();
		}

		@Override
		public boolean remove(Object object) {
			synchronized (this.mutex) {
				return this.collection.remove(object);
			}
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			synchronized (this.mutex) {
				return this.collection.removeAll(collection);
			}
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			synchronized (this.mutex) {
				return this.collection.removeIf(predicate);
			}
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			synchronized (this.mutex) {
				return this.collection.retainAll(collection);
			}
		}

		@Override
		public int size() {
			synchronized (this.mutex) {
				return this.collection.size();
			}
		}

		@Override
		public Spliterator<E> spliterator() {
			//see java.util.Collections.SynchronizedCollection.spliterator()
			//"Must be manually synched by user!"
			return this.collection.spliterator();
		}

		@Override
		public Stream<E> stream() {
			//see java.util.Collections.SynchronisedCollection.stream()
			//"Must be manually synched by user!"
			return this.collection.stream();
		}

		@Override
		public Object[] toArray() {
			synchronized (this.mutex) {
				return this.collection.toArray();
			}
		}

		@Override
		public <T> T[] toArray(T[] array) {
			synchronized (this.mutex) {
				return this.collection.toArray(array);
			}
		}

		@Override
		public String toString() {
			synchronized (this.mutex) {
				return this.collection.toString();
			}
		}

		@SuppressWarnings("JavaDoc")
		private void writeObject(ObjectOutputStream stream) throws IOException {
			synchronized (this.mutex) {
				stream.defaultWriteObject();
			}
		}
	}

	/**
	 * A list view backed by another list and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2436428270890417537L;

		/**
		 * The wrapped list (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final List<E> list;

		/**
		 * Construct a new synchronized list view for the given {@code list}.
		 *
		 * @param list the list to be wrapped.
		 * @throws NullPointerException if the given {@code list} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedList(List<E> list) {
			super(list);
			this.list = list;
		}

		/**
		 * Construct a new synchronized list view with the given {@code mutex} for the given {@code
		 * list}.
		 *
		 * @param list  the list to be wrapped.
		 * @param mutex the object the constructed list will be synchronized with.
		 * @throws NullPointerException if the given {@code list} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedList(List<E> list, Object mutex) {
			super(list, mutex);
			this.list = list;
		}

		@Override
		public void add(int index, E element) {
			synchronized (this.mutex) {
				this.list.add(index, element);
			}
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			synchronized (this.mutex) {
				return this.list.addAll(index, collection);
			}
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;

			synchronized (this.mutex) {
				return this.list.equals(object);
			}
		}

		@Override
		public E get(int index) {
			synchronized (this.mutex) {
				return this.list.get(index);
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.mutex) {
				return this.list.hashCode();
			}
		}

		@Override
		public int indexOf(Object object) {
			synchronized (this.mutex) {
				return this.list.indexOf(object);
			}
		}

		@Override
		public int lastIndexOf(Object object) {
			synchronized (this.mutex) {
				return this.list.lastIndexOf(object);
			}
		}

		@Override
		public ListIterator<E> listIterator() {
			//see java.util.Collections.SynchronizedList.listIterator()
			//"Must be manually synched by user!"
			return this.list.listIterator();
		}

		@Override
		public ListIterator<E> listIterator(int index) {
			//see java.util.Collections.SynchronizedList.listIterator(int)
			//"Must be manually synched by user!"
			return this.list.listIterator(index);
		}

		@Override
		public E remove(int index) {
			synchronized (this.mutex) {
				return this.list.remove(index);
			}
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			synchronized (this.mutex) {
				this.list.replaceAll(operator);
			}
		}

		@Override
		public E set(int index, E element) {
			synchronized (this.mutex) {
				return this.list.set(index, element);
			}
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			synchronized (this.mutex) {
				this.list.sort(comparator);
			}
		}

		@Override
		public SynchronizedList<E> subList(int beginIndex, int endIndex) {
			synchronized (this.mutex) {
				return new SynchronizedList(this.list.subList(beginIndex, endIndex), this.mutex);
			}
		}
	}

	/**
	 * A map view backed by another map and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1243744996526469332L;

		/**
		 * The wrapped map.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final Map<K, V> map;
		/**
		 * The object all the operations of this map is synchronized with.
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final Object mutex;

		/**
		 * Construct a new synchronized map view for the given {@code map}.
		 *
		 * @param map the map to be wrapped.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedMap(Map<K, V> map) {
			Objects.requireNonNull(map, "map");
			this.map = map;
			this.mutex = this;
		}

		/**
		 * Construct a new synchronized map view with the given {@code mutex} for the given {@code
		 * map}.
		 *
		 * @param map   the map to be wrapped.
		 * @param mutex the object the constructed map will be synchronized with.
		 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedMap(Map<K, V> map, Object mutex) {
			Objects.requireNonNull(map, "map");
			Objects.requireNonNull(mutex, "mutex");
			this.map = map;
			this.mutex = mutex;
		}

		@Override
		public void clear() {
			synchronized (this.mutex) {
				this.map.clear();
			}
		}

		@Override
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			synchronized (this.mutex) {
				return this.map.compute(key, function);
			}
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			synchronized (this.mutex) {
				return this.map.computeIfAbsent(key, function);
			}
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			synchronized (this.mutex) {
				return this.map.computeIfPresent(key, function);
			}
		}

		@Override
		public boolean containsKey(Object key) {
			synchronized (this.mutex) {
				return this.map.containsKey(key);
			}
		}

		@Override
		public boolean containsValue(Object value) {
			synchronized (this.mutex) {
				return this.map.containsValue(value);
			}
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			synchronized (this.mutex) {
				return new SynchronizedSet(this.map.entrySet(), this.mutex);
			}
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;

			synchronized (this.mutex) {
				return this.map.equals(object);
			}
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			synchronized (this.mutex) {
				this.map.forEach(consumer);
			}
		}

		@Override
		public V get(Object key) {
			synchronized (this.mutex) {
				return this.map.get(key);
			}
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			synchronized (this.mutex) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.mutex) {
				return this.map.hashCode();
			}
		}

		@Override
		public boolean isEmpty() {
			synchronized (this.mutex) {
				return this.map.isEmpty();
			}
		}

		@Override
		public SynchronizedSet<K> keySet() {
			synchronized (this.mutex) {
				return new SynchronizedSet(this.map.keySet(), this.mutex);
			}
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			synchronized (this.mutex) {
				return this.map.merge(key, value, function);
			}
		}

		@Override
		public V put(K key, V value) {
			synchronized (this.mutex) {
				return this.map.put(key, value);
			}
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map) {
			synchronized (this.mutex) {
				this.map.putAll(map);
			}
		}

		@Override
		public V putIfAbsent(K key, V value) {
			synchronized (this.mutex) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public V remove(Object key) {
			synchronized (this.mutex) {
				return this.map.remove(key);
			}
		}

		@Override
		public boolean remove(Object key, Object value) {
			synchronized (this.mutex) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public V replace(K key, V value) {
			synchronized (this.mutex) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			synchronized (this.mutex) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			synchronized (this.mutex) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public int size() {
			synchronized (this.mutex) {
				return this.map.size();
			}
		}

		@Override
		public String toString() {
			synchronized (this.mutex) {
				return this.map.toString();
			}
		}

		@Override
		public Collection<V> values() {
			synchronized (this.mutex) {
				return new SynchronizedCollection(this.map.values(), this.mutex);
			}
		}

		@SuppressWarnings("JavaDoc")
		private void writeObject(ObjectOutputStream stream) throws IOException {
			synchronized (this.mutex) {
				stream.defaultWriteObject();
			}
		}
	}

	/**
	 * A map view backed by another map and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -454950785076281653L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final NavigableMap<K, V> map;

		/**
		 * Construct a new synchronized map view for the given {@code map}.
		 *
		 * @param map the map to be wrapped.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedNavigableMap(NavigableMap<K, V> map) {
			super(map);
			this.map = map;
		}

		/**
		 * Construct a new synchronized map view with the given {@code mutex} for the given {@code
		 * map}.
		 *
		 * @param map   the map to be wrapped.
		 * @param mutex the object the constructed map will be synchronized with.
		 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedNavigableMap(NavigableMap<K, V> map, Object mutex) {
			super(map, mutex);
			this.map = map;
		}

		@Override
		public Entry<K, V> ceilingEntry(K key) {
			synchronized (this.mutex) {
				return this.map.ceilingEntry(key);
			}
		}

		@Override
		public K ceilingKey(K key) {
			synchronized (this.mutex) {
				return this.map.ceilingKey(key);
			}
		}

		@Override
		public SynchronizedNavigableSet<K> descendingKeySet() {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.map.descendingKeySet(), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> descendingMap() {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.descendingMap(), this.mutex);
			}
		}

		@Override
		public Entry<K, V> firstEntry() {
			synchronized (this.mutex) {
				return this.map.firstEntry();
			}
		}

		@Override
		public Entry<K, V> floorEntry(K key) {
			synchronized (this.mutex) {
				return this.map.floorEntry(key);
			}
		}

		@Override
		public K floorKey(K key) {
			synchronized (this.mutex) {
				return this.map.floorKey(key);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> headMap(K endKey) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.headMap(
						endKey,
						false
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> headMap(K endKey, boolean inclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.headMap(
						endKey,
						inclusive
				), this.mutex);
			}
		}

		@Override
		public Entry<K, V> higherEntry(K key) {
			synchronized (this.mutex) {
				return this.map.higherEntry(key);
			}
		}

		@Override
		public K higherKey(K key) {
			synchronized (this.mutex) {
				return this.map.higherKey(key);
			}
		}

		@Override
		public Entry<K, V> lastEntry() {
			synchronized (this.mutex) {
				return this.map.lastEntry();
			}
		}

		@Override
		public Entry<K, V> lowerEntry(K key) {
			synchronized (this.mutex) {
				return this.map.lowerEntry(key);
			}
		}

		@Override
		public K lowerKey(K key) {
			synchronized (this.mutex) {
				return this.map.lowerKey(key);
			}
		}

		@Override
		public SynchronizedNavigableSet<K> navigableKeySet() {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.map.navigableKeySet(), this.mutex);
			}
		}

		@Override
		public Entry<K, V> pollFirstEntry() {
			synchronized (this.mutex) {
				return this.map.pollFirstEntry();
			}
		}

		@Override
		public Entry<K, V> pollLastEntry() {
			synchronized (this.mutex) {
				return this.map.pollLastEntry();
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> subMap(K beginKey, K endKey) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.subMap(
						beginKey,
						true,
						endKey,
						false
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> subMap(K beginKey, boolean beginInclusive, K endKey, boolean endInclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.subMap(
						beginKey,
						beginInclusive,
						endKey,
						endInclusive
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> tailMap(K beginKey) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.tailMap(
						beginKey,
						true
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableMap<K, V> tailMap(K beginKey, boolean inclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableMap(this.map.tailMap(
						beginKey,
						inclusive
				), this.mutex);
			}
		}
	}

	/**
	 * A set view backed by another set and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 5020761590586629549L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final NavigableSet<E> set;

		/**
		 * Construct a new synchronized set view for the given {@code set}.
		 *
		 * @param set the set to be wrapped.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedNavigableSet(NavigableSet<E> set) {
			super(set);
			this.set = set;
		}

		/**
		 * Construct a new synchronized set view with the given {@code mutex} for the given {@code
		 * set}.
		 *
		 * @param set   the set to be wrapped.
		 * @param mutex the object the constructed set will be synchronized with.
		 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedNavigableSet(NavigableSet<E> set, Object mutex) {
			super(set, mutex);
			this.set = set;
		}

		@Override
		public E ceiling(E element) {
			synchronized (this.mutex) {
				return this.set.ceiling(element);
			}
		}

		@Override
		public Iterator<E> descendingIterator() {
			//see java.util.Collections.SynchronizedNavigableSet.descendingIterator()
			synchronized (this.mutex) {
				//it is the same :|
				return this.set.descendingIterator();
			}
		}

		@Override
		public SynchronizedNavigableSet<E> descendingSet() {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.descendingSet(), this.mutex);
			}
		}

		@Override
		public E floor(E element) {
			synchronized (this.mutex) {
				return this.set.floor(element);
			}
		}

		@Override
		public SynchronizedNavigableSet<E> headSet(E endElement) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.headSet(
						endElement,
						false
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableSet<E> headSet(E endElement, boolean inclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.headSet(
						endElement,
						inclusive
				), this.mutex);
			}
		}

		@Override
		public E higher(E element) {
			synchronized (this.mutex) {
				return this.set.higher(element);
			}
		}

		@Override
		public E lower(E element) {
			synchronized (this.mutex) {
				return this.set.lower(element);
			}
		}

		@Override
		public E pollFirst() {
			synchronized (this.mutex) {
				return this.set.pollFirst();
			}
		}

		@Override
		public E pollLast() {
			synchronized (this.mutex) {
				return this.set.pollLast();
			}
		}

		@Override
		public SynchronizedNavigableSet<E> subSet(E beginElement, E endElement) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.subSet(
						beginElement,
						true,
						endElement,
						false
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableSet<E> subSet(E beginElement, boolean beginInclusive, E endElement, boolean endInclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.subSet(
						beginElement,
						beginInclusive,
						endElement,
						endInclusive
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableSet<E> tailSet(E beginElement) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.tailSet(
						beginElement,
						true
				), this.mutex);
			}
		}

		@Override
		public SynchronizedNavigableSet<E> tailSet(E beginElement, boolean inclusive) {
			synchronized (this.mutex) {
				return new SynchronizedNavigableSet(this.set.tailSet(
						beginElement,
						inclusive
				), this.mutex);
			}
		}
	}

	/**
	 * A queue view backed by another queue and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -497874333100433258L;

		/**
		 * The wrapped queue (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final Queue<E> queue;

		/**
		 * Construct a new synchronized queue view for the given {@code queue}.
		 *
		 * @param queue the queue to be wrapped.
		 * @throws NullPointerException if the given {@code queue} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedQueue(Queue<E> queue) {
			super(queue);
			this.queue = queue;
		}

		/**
		 * Construct a new synchronized queue view with the given {@code mutex} for the given {@code
		 * queue}.
		 *
		 * @param queue the queue to be wrapped.
		 * @param mutex the object the constructed queue will be synchronized with.
		 * @throws NullPointerException if the given {@code queue} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedQueue(Queue<E> queue, Object mutex) {
			super(queue, mutex);
			this.queue = queue;
		}

		@Override
		public E element() {
			synchronized (this.mutex) {
				return this.queue.element();
			}
		}

		@Override
		public boolean offer(E element) {
			synchronized (this.mutex) {
				return this.queue.offer(element);
			}
		}

		@Override
		public E peek() {
			synchronized (this.mutex) {
				return this.queue.peek();
			}
		}

		@Override
		public E poll() {
			synchronized (this.mutex) {
				return this.queue.poll();
			}
		}

		@Override
		public E remove() {
			synchronized (this.mutex) {
				return this.queue.remove();
			}
		}
	}

	/**
	 * A list view backed by another list and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 3537476550500792998L;

		/**
		 * Construct a new synchronized list view for the given {@code list}.
		 *
		 * @param list the list to be wrapped.
		 * @throws NullPointerException if the given {@code list} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedRandomAccessList(List<E> list) {
			super(list);
		}

		/**
		 * Construct a new synchronized list view with the given {@code mutex} for the given {@code
		 * list}.
		 *
		 * @param list  the list to be wrapped.
		 * @param mutex the object the constructed list will be synchronized with.
		 * @throws NullPointerException if the given {@code list} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedRandomAccessList(List<E> list, Object mutex) {
			super(list, mutex);
		}

		@Override
		public SynchronizedRandomAccessList<E> subList(int beginIndex, int endIndex) {
			synchronized (this.mutex) {
				return new SynchronizedRandomAccessList(this.list.subList(beginIndex, endIndex), this.mutex);
			}
		}
	}

	/**
	 * A set view backed by another set and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6032712944227921269L;

		/**
		 * Construct a new synchronized set view for the given {@code set}.
		 *
		 * @param set the set to be wrapped.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSet(Set<E> set) {
			super(set);
		}

		/**
		 * Construct a new synchronized set view with the given {@code mutex} for the given {@code
		 * set}.
		 *
		 * @param set   the set to be wrapped.
		 * @param mutex the object the constructed set will be synchronized with.
		 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSet(Set<E> set, Object mutex) {
			super(set, mutex);
		}

		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;

			synchronized (this.mutex) {
				return this.collection.equals(object);
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.mutex) {
				return this.collection.hashCode();
			}
		}
	}

	/**
	 * A map view backed by another map and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8385025551424159520L;

		/**
		 * The wrapped map (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final SortedMap<K, V> map;

		/**
		 * Construct a new synchronized map view for the given {@code map}.
		 *
		 * @param map the map to be wrapped.
		 * @throws NullPointerException if the given {@code map} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSortedMap(SortedMap<K, V> map) {
			super(map);
			this.map = map;
		}

		/**
		 * Construct a new synchronized map view with the given {@code mutex} for the given {@code
		 * map}.
		 *
		 * @param map   the map to be wrapped.
		 * @param mutex the object the constructed map will be synchronized with.
		 * @throws NullPointerException if the given {@code map} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSortedMap(SortedMap<K, V> map, Object mutex) {
			super(map, mutex);
			this.map = map;
		}

		@Override
		public Comparator<? super K> comparator() {
			synchronized (this.mutex) {
				return this.map.comparator();
			}
		}

		@Override
		public K firstKey() {
			synchronized (this.mutex) {
				return this.map.firstKey();
			}
		}

		@Override
		public SynchronizedSortedMap<K, V> headMap(K endKey) {
			synchronized (this.mutex) {
				return new SynchronizedSortedMap(this.map.headMap(
						endKey
				), this.mutex);
			}
		}

		@Override
		public K lastKey() {
			synchronized (this.mutex) {
				return this.map.lastKey();
			}
		}

		@Override
		public SynchronizedSortedMap<K, V> subMap(K beginKey, K endKey) {
			synchronized (this.mutex) {
				return new SynchronizedSortedMap(this.map.subMap(
						beginKey, endKey
				), this.mutex);
			}
		}

		@Override
		public SynchronizedSortedMap<K, V> tailMap(K beginKey) {
			synchronized (this.mutex) {
				return new SynchronizedSortedMap(this.map.tailMap(
						beginKey
				), this.mutex);
			}
		}
	}

	/**
	 * A set view backed by another set and all its operations are synchronized with a specified
	 * object.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.22
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2367692007117797912L;

		/**
		 * The wrapped set (casted reference).
		 *
		 * @since 0.1.5 ~2020.08.22
		 */
		protected final SortedSet<E> set;

		/**
		 * Construct a new synchronized set view for the given {@code set}.
		 *
		 * @param set the set to be wrapped.
		 * @throws NullPointerException if the given {@code set} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSortedSet(SortedSet<E> set) {
			super(set);
			this.set = set;
		}

		/**
		 * Construct a new synchronized set view with the given {@code mutex} for the given {@code
		 * set}.
		 *
		 * @param set   the set to be wrapped.
		 * @param mutex the object the constructed set will be synchronized with.
		 * @throws NullPointerException if the given {@code set} or {@code mutex} is null.
		 * @since 0.1.5 ~2020.08.22
		 */
		public SynchronizedSortedSet(SortedSet<E> set, Object mutex) {
			super(set, mutex);
			this.set = set;
		}

		@Override
		public Comparator<? super E> comparator() {
			synchronized (this.mutex) {
				return this.set.comparator();
			}
		}

		@Override
		public E first() {
			synchronized (this.mutex) {
				return this.set.first();
			}
		}

		@Override
		public SynchronizedSortedSet<E> headSet(E endElement) {
			synchronized (this.mutex) {
				return new SynchronizedSortedSet(this.set.headSet(
						endElement
				), this.mutex);
			}
		}

		@Override
		public E last() {
			synchronized (this.mutex) {
				return this.set.last();
			}
		}

		@Override
		public SynchronizedSortedSet<E> subSet(E beginElement, E endElement) {
			synchronized (this.set) {
				return new SynchronizedSortedSet(this.set.subSet(
						beginElement,
						endElement
				), this.mutex);
			}
		}

		@Override
		public SynchronizedSortedSet<E> tailSet(E beginElement) {
			synchronized (this.mutex) {
				return new SynchronizedSortedSet(this.set.tailSet(
						beginElement
				), this.mutex);
			}
		}
	}

	/**
	 * An unmodifiable view collection of another collection.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.19
	 */
	class UnmodifiableCollection<E> implements Collection<E>, Serializable {
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
	class UnmodifiableEnumeration<E> implements Enumeration<E> {
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
	class UnmodifiableIterator<E> implements Iterator<E> {
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
	class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
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
	class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
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
	class UnmodifiableMap<K, V> implements Map<K, V>, Serializable {
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
				this.iterator.forEachRemaining(entry -> consumer.accept(new UnmodifiableEntry<>(entry)));
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
				return object == this ||
					   object instanceof Set &&
					   ((Set) object).size() == this.entrySet.size() &&
					   this.containsAll((Set) object);
			}

			@Override
			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				this.entrySet.forEach(entry -> consumer.accept(new UnmodifiableEntry<>(entry)));
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
					if (predicate.test(new UnmodifiableEntry<>(entry)))
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
						array : java.util.Arrays.copyOf(array, 0)
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
				this.spliterator.forEachRemaining(entry -> consumer.accept(new UnmodifiableEntry<>(entry)));
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
				return this.spliterator.tryAdvance(entry -> consumer.accept(new UnmodifiableEntry<>(entry)));
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
	class UnmodifiableNavigableMap<K, V> extends UnmodifiableSortedMap<K, V> implements NavigableMap<K, V> {
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
		public UnmodifiableNavigableMap<K, V> headMap(K endKey) {
			return new UnmodifiableNavigableMap(this.map.headMap(
					endKey,
					false
			));
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
		public UnmodifiableNavigableMap<K, V> subMap(K beginKey, K endKey) {
			return new UnmodifiableNavigableMap(this.map.subMap(
					beginKey,
					true,
					endKey,
					false
			));
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
		public UnmodifiableNavigableMap<K, V> tailMap(K beginKey) {
			return new UnmodifiableNavigableMap(this.map.tailMap(
					beginKey,
					true
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
	class UnmodifiableNavigableSet<E> extends UnmodifiableSortedSet<E> implements NavigableSet<E> {
		@SuppressWarnings("JavaDoc")
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
		public UnmodifiableNavigableSet<E> headSet(E endElement) {
			return new UnmodifiableNavigableSet(this.set.headSet(
					endElement,
					false
			));
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
		public UnmodifiableNavigableSet<E> subSet(E beginElement, E endElement) {
			return new UnmodifiableNavigableSet(this.set.subSet(
					beginElement,
					true,
					endElement,
					false
			));
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
		public UnmodifiableNavigableSet<E> tailSet(E beginElement) {
			return new UnmodifiableNavigableSet(this.set.tailSet(
					beginElement,
					true
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
	class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E> implements RandomAccess {
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
	class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {
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
	class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements SortedMap<K, V> {
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
	class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E> {
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
			return new UnmodifiableSortedSet(this.set.headSet(
					endElement
			));
		}

		@Override
		public E last() {
			return this.set.last();
		}

		@Override
		public UnmodifiableSortedSet<E> subSet(E beginElement, E endElement) {
			return new UnmodifiableSortedSet(this.set.subSet(
					beginElement,
					endElement
			));
		}

		@Override
		public UnmodifiableSortedSet<E> tailSet(E beginElement) {
			return new UnmodifiableSortedSet(this.set.tailSet(
					beginElement
			));
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
	class UnmodifiableSpliterator<E> implements Spliterator<E> {
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
//EmptyRandomAccessList

//NCopiesMap
//NCopiesNavigableMap
//NCopiesNavigableSet
//NCopiesQueue
//NCopiesRandomAccessList
//NCopiesSet
//NCopiesSortedMap
//NCopiesSortedSet

//SingletonNavigableMap
//SingletonNavigableSet
//SingletonQueue
//SingletonRandomAccessList
//SingletonSortedMap
//SingletonSortedSet

//SynchronizedEnumeration
//SynchronizedIterator
//SynchronizedListIterator
//SynchronizedSpliterator

//UnmodifiableQueue
