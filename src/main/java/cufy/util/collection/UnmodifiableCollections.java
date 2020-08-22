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
import java.util.stream.StreamSupport;

/**
 * A class holding the unmodifiable collection wrapper classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface UnmodifiableCollections {
	/**
	 * Construct an unmodifiable view of the given {@code collection}.
	 *
	 * @param collection the collection to get an unmodifiable view of.
	 * @param <T>        the type of the elements.
	 * @return an unmodifiable view of the given {@code collection}.
	 * @throws NullPointerException if the given {@code collection} is null.
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

	//UnmodifiableQueue

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
