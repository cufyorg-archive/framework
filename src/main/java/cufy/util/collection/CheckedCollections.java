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

import cufy.util.Arrays;
import cufy.util.Objects;
import cufy.util.array.Array;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A class holding the checked collection wrapper classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface CheckedCollections {
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
				return object == this ||
					   object instanceof Set &&
					   ((Set) object).size() == this.entrySet.size() &&
					   this.containsAll((Set) object);
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
}
