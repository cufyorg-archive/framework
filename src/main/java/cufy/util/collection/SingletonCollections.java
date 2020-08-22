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
 * A class holding the singleton collection classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.22
 */
public interface SingletonCollections {
	/**
	 * Get a set that has only the given {@code element}.
	 *
	 * @param element the only element the returned set will have.
	 * @param <T>     the type of the element.
	 * @return a set with only the given {@code element}.
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
	 * @since 0.1.5 ~2020.08.19
	 */
	static <T> SingletonSpliterator<T> singletonSpliterator(T element) {
		return new SingletonSpliterator(element);
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

			return endIndex - beginIndex == 0 ? new EmptyCollections.EmptyList() : this;
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

	//SingletonNavigableMap
	//SingletonNavigableSet
	//SingletonQueue
	//SingletonRandomAccessList

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

	//SingletonSortedMap
	//SingletonSortedSet

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
}
