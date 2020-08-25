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
package cufy.util.collection;

import cufy.util.Objects;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A class holding the empty collection classes. Empty collections are unmodifiable collections.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface EmptyCollections {
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
	 * Get an empty collection.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty collection.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyCollection<T> emptyCollection() {
		return EmptyCollections.EMPTY_COLLECTION;
	}

	/**
	 * Get an empty enumeration.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty enumeration.
	 * @see Collections#emptyEnumeration()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyEnumeration<T> emptyEnumeration() {
		return EmptyCollections.EMPTY_ENUMERATION;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @see Collections#emptyIterator()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyIterator<T> emptyIterator() {
		return EmptyCollections.EMPTY_ITERATOR;
	}

	/**
	 * Get an empty list.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty list.
	 * @see Collections#emptyList()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyList<T> emptyList() {
		return EmptyCollections.EMPTY_LIST;
	}

	/**
	 * Get an empty iterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty iterator.
	 * @see Collections#emptyListIterator()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyListIterator<T> emptyListIterator() {
		return EmptyCollections.EMPTY_LIST_ITERATOR;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see Collections#emptyMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptyMap<K, V> emptyMap() {
		return EmptyCollections.EMPTY_MAP;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see Collections#emptyNavigableMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptyNavigableMap<K, V> emptyNavigableMap() {
		return EmptyCollections.EMPTY_NAVIGABLE_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see Collections#emptyNavigableSet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyNavigableSet<T> emptyNavigableSet() {
		return EmptyCollections.EMPTY_NAVIGABLE_SET;
	}

	/**
	 * Get an empty queue.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty queue.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptyQueue<T> emptyQueue() {
		return EmptyCollections.EMPTY_QUEUE;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see Collections#emptySet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySet<T> emptySet() {
		return EmptyCollections.EMPTY_SET;
	}

	/**
	 * Get an empty map.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @return an empty map.
	 * @see Collections#emptySortedMap()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <K, V> EmptySortedMap<K, V> emptySortedMap() {
		return EmptyCollections.EMPTY_SORTED_MAP;
	}

	/**
	 * Get an empty set.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty set.
	 * @see Collections#emptySortedSet()
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySortedSet<T> emptySortedSet() {
		return EmptyCollections.EMPTY_SORTED_SET;
	}

	/**
	 * Get an empty spliterator.
	 *
	 * @param <T> the type of the elements.
	 * @return an empty spliterator.
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> EmptySpliterator<T> emptySpliterator() {
		return EmptyCollections.EMPTY_SPLITERATOR;
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

	//EmptyRandomAccessList

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
}
