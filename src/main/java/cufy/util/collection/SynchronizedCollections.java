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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A class holding the synchronized collection wrapper classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface SynchronizedCollections {
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

	//SynchronizedEnumeration
	//SynchronizedIterator

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

	//SynchronizedListIterator

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

	//SynchronizedSpliterator
}
