package cufy.util.collection;

import cufy.util.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A class holding the transform collection wrapper classes.
 * <p>
 * Note: this class chosen to be an interface to allow inheritance in the utility classes.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.24
 */
public interface TransformCollections {
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
}
//	/**
//	 * Get a map that do read and write to the given {@code list}. The map will use the indexes as
//	 * the keys of the elements on the given list.
//	 *
//	 * @param list the list that the returned map is reading-from/writing-to.
//	 * @param <T>  the type of values on the returned map.
//	 * @return a map that do read and write to the given {@code list}.
//	 * @throws NullPointerException if the given {@code list} is null.
//	 */
//	@Deprecated
//	static <T> IndexMapList newIndexMapFromList(List<T> list) {
//		return new IndexMapList(list);
//	}
//
//	/**
//	 * Get a list that do read and write to the given {@code map}. The list will use the positive
//	 * integer keys only.
//	 *
//	 * @param map the map that the returned list is reading-from/writing-to.
//	 * @param <T> the type of the elements in the returned list.
//	 * @return a list that do read and write to the given {@code map}.
//	 * @throws NullPointerException if the given {@code map} is null.
//	 */
//	@Deprecated
//	static <T> ListIndexMap<T> newListFromIndexMap(Map<Integer, T> map) {
//		return new ListIndexMap(map);
//	}
//
//	/**
//	 * A map backed by a list.
//	 *
//	 * @param <V> the type of mapped values
//	 */
//	@Deprecated
//	class IndexMapList<V> extends AbstractMap<Integer, V> {
//		/**
//		 * The entrySet of this map.
//		 */
//		private final ListMapEntrySet entrySet = new ListMapEntrySet();
//		/**
//		 * The backing list of this map.
//		 */
//		private final List<V> list;
//
//		/**
//		 * Construct a new map that is backed by the given {@code list}.
//		 *
//		 * @param list the list backing the constructed map.
//		 * @throws NullPointerException if the given {@code list} is null.
//		 */
//		private IndexMapList(List<V> list) {
//			Objects.requireNonNull(list, "list");
//			this.list = list;
//		}
//
//		@Override
//		public Set<Entry<Integer, V>> entrySet() {
//			return (Set) this.entrySet;
//		}
//
//		@Override
//		public V put(Integer key, V value) {
//			if (key < 0)
//				throw new UnsupportedOperationException("can't store the key: " + key);
//
//			int index = key;
//			int size = this.list.size();
//
//			if (size > index)
//				return this.list.set(index, value);
//
//			this.list.addAll(Collections.nCopies(index - size, null));
//			this.list.add(value);
//			return null;
//		}
//
//		/**
//		 * An entry for maps that is backed by a list.
//		 */
//		public final class ListMapEntry implements Entry<Integer, V> {
//			/**
//			 * The index of where this entry is reading from in the backing list.
//			 */
//			private final int index;
//			/**
//			 * Reassignment for the {@link IndexMapList#list} to be visible across instances.
//			 */
//			private final List<V> list = IndexMapList.this.list;
//			/**
//			 * The size of the list at the time creating this entry.
//			 */
//			private final int mod;
//
//			/**
//			 * Construct a new list-map-entry that reads from the given {@code index}.
//			 *
//			 * @param mod   the size of the list at the time creating this entry.
//			 * @param index the index the constructing is reading at at the backed list.
//			 * @throws IllegalArgumentException if the given {@code index} is negative, or if the
//			 *                                  given {@code mod} is less or equals to the given
//			 *                                  {@code index}.
//			 */
//			private ListMapEntry(int mod, int index) {
//				if (index < 0)
//					throw new IllegalArgumentException("Negative index " + index);
//				if (mod < index)
//					throw new IllegalArgumentException("mod is less that index " + mod);
//				this.mod = mod;
//				this.index = index;
//			}
//
//			@Override
//			public boolean equals(Object obj) {
//				if (obj == this)
//					//quick match
//					return true;
//				if (obj instanceof IndexMapList.ListMapEntry)
//					//index match
//					return ((ListMapEntry) obj).list == this.list &&
//						   ((ListMapEntry) obj).index == this.index;
//				//entry match
//				return obj instanceof Map.Entry &&
//					   Objects.equals(((Entry) obj).getKey(), this.getKey()) &&
//					   Objects.equals(((Entry) obj).getValue(), this.getValue());
//			}
//
//			@Override
//			public Integer getKey() {
//				return this.index;
//			}
//
//			@Override
//			public V getValue() {
//				this.assertNoModification();
//				return this.list.get(this.index);
//			}
//
//			@Override
//			public int hashCode() {
//				return Objects.hashCode(this.getKey()) ^
//					   Objects.hashCode(this.getValue());
//			}
//
//			@Override
//			public V setValue(V v) {
//				this.assertNoModification();
//				return this.list.set(this.index, v);
//			}
//
//			@Override
//			public String toString() {
//				return this.getKey() + "=" + this.getValue();
//			}
//
//			/**
//			 * Check if the list has modified in the interval of creating this entry and the time
//			 * invoking this method.
//			 *
//			 * @throws ConcurrentModificationException if the list has been modified since creating
//			 *                                         this entry.
//			 */
//			private void assertNoModification() {
//				int size = IndexMapList.this.list.size();
//				if (this.mod != size)
//					throw new ConcurrentModificationException(
//							"expected: " + this.mod + ", but was: " + size);
//			}
//		}
//
//		/**
//		 * An entry-set for maps that is backed by a list.
//		 */
//		public final class ListMapEntrySet extends AbstractSet<ListMapEntry> {
//			/**
//			 * Construct a new entry-set.
//			 */
//			private ListMapEntrySet() {
//			}
//
//			@Override
//			public ListMapEntrySetIterator iterator() {
//				return new ListMapEntrySetIterator();
//			}
//
//			@Override
//			public int size() {
//				return IndexMapList.this.list.size();
//			}
//
//			/**
//			 * An iterator for an entry-set of maps that is backed by a list.
//			 */
//			public final class ListMapEntrySetIterator implements Iterator<ListMapEntry> {
//				/**
//				 * The current position of this iterator.
//				 */
//				private int cursor;
//				/**
//				 * The initial size of this array once this iterator created.
//				 */
//				private int mod = IndexMapList.this.list.size();
//
//				/**
//				 * Construct a new iterator.
//				 */
//				private ListMapEntrySetIterator() {
//				}
//
//				@Override
//				public boolean hasNext() {
//					return this.mod > this.cursor;
//				}
//
//				@Override
//				public ListMapEntry next() {
//					this.assertNoModification();
//					this.assertInBounds();
//					return new ListMapEntry(this.mod, this.cursor++);
//				}
//
//				@Override
//				public void remove() {
//					this.assertNoModification();
//					IndexMapList.this.list.remove(this.cursor);
//					this.mod--;
//				}
//
//				/**
//				 * Check if this iterator still has more elements.
//				 *
//				 * @throws NoSuchElementException if this iterator has no more elements.
//				 */
//				private void assertInBounds() {
//					if (this.mod <= this.cursor)
//						throw new NoSuchElementException(String.valueOf(this.cursor + 1));
//				}
//
//				/**
//				 * Check if the list has modified while using this iterator.
//				 *
//				 * @throws ConcurrentModificationException if the list has been modified since
//				 *                                         creating this iterator.
//				 */
//				private void assertNoModification() {
//					int size = IndexMapList.this.list.size();
//					if (this.mod != size)
//						throw new ConcurrentModificationException(
//								"expected: " + this.mod + ", but was: " + size);
//				}
//			}
//		}
//	}
//	/**
//	 * A list backed by a map.
//	 *
//	 * @param <E> the type of elements in this list.
//	 */
//	@Deprecated
//	class ListIndexMap<E> implements List<E>, Serializable {
//		@SuppressWarnings("JavaDoc")
//		private static final long serialVersionUID = 4123623166970364331L;
//
//		/**
//		 * The map backing this list.
//		 */
//		private final Map<Integer, E> map;
//
//		/**
//		 * Construct a new {@link ListIndexMap} backed by the given {@code map}.
//		 *
//		 * @param map the map backing the constructed list.
//		 * @throws NullPointerException if the given {@code map} is null.
//		 */
//		private ListIndexMap(Map<Integer, E> map) {
//			Objects.requireNonNull(map, "map");
//			this.map = map;
//		}
//
//		@Override
//		public void add(int index, E element) {
//			this.checkIndex(index);
//			this.shift(1);
//			this.map.put(index, element);
//		}
//
//		@Override
//		public E get(int index) {
//			this.checkIndex(index);
//			return this.map.get(index);
//		}
//
//		@Override
//		public E remove(int index) {
//			this.checkIndex(index);
//			E value = this.map.remove(index);
//			this.shift(-1);
//			return value;
//		}
//
//		@Override
//		public E set(int index, E element) {
//			this.checkIndex(index);
//			return this.map.put(index, element);
//		}
//
//		@Override
//		public int size() {
//			int max = -1;
//
//			for (Object key : this.map.keySet())
//				if (key instanceof Integer) {
//					int i = (int) key;
//
//					if (i > max)
//						max = i;
//				}
//
//			return max + 1;
//		}
//
//		/**
//		 * Shift the elements in this list by the given {@code degree}.
//		 *
//		 * @param degree how many steps to move the elements by.
//		 * @since 0.1.5 ~2020.08.25
//		 */
//		protected void shift(int degree) {
//			//avoid casting object to integer
//			((Map<Object, Object>) (Map) this.map)
//					.keySet()
//					.stream()
//					.filter(i -> i instanceof Integer && (int) i >= 0)
//					.collect(Collectors.toList())
//					.forEach(i -> this.map.put((int) i + degree, this.map.remove(i)));
//		}
//
//		/**
//		 * Validate the given {@code index}.
//		 *
//		 * @param index the index to be validated.
//		 * @throws IndexOutOfBoundsException if the given {@code index} is not a valid index.
//		 * @since 0.1.5 ~2020.08.25
//		 */
//		private void checkIndex(int index) {
//			int size = this.size();
//
//			if (index < 0)
//				throw new IndexOutOfBoundsException("index(" + index + ") < 0");
//			if (index >= size)
//				throw new IndexOutOfBoundsException("index(" + index + ") >= size(" + size + ")");
//		}
//	}
