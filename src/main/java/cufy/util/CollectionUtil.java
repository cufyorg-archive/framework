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

import cufy.util.collection.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utils all about maps.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.b ~2019.06.11
 */
@SuppressWarnings("JavaDoc")
public interface CollectionUtil extends
		CheckedCollections,
		CombinedCollections,
		EmptyCollections,
		SingletonCollections,
		SynchronizedCollections,
		UnmodifiableCollections {
	//todo: rename to `Collections` ;P

	//misc

	static <T> boolean addAll(Collection<? super T> collection, T... elements) {
		boolean modified = false;
		for (T element : elements)
			modified |= collection.add(element);
		return modified;
	}

	static <T> LIFOQueue<T> asLifoQueue(Deque<T> deque) {
		return new LIFOQueue(deque);
	}

	static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key);
	}

	static <T> int binarySearch(List<T> list, T key, Comparator<? super T> comparator) {
		//can not access java.util.Collections.BINARYSEARCH_THRESHOLD
		return java.util.Collections.binarySearch(list, key, comparator);
	}

	//combine

	//copy

	static <T> void copy(List<? super T> dest, List<? extends T> src) {
		//can not access java.util.Collections.COPY_THRESHOLD
		java.util.Collections.copy(dest, src);
	}

	//disjoint

	static <T, U> boolean disjoint(Collection<T> collection, Collection<U> other) {
		//an operation based on benchmarks is always better!
		return java.util.Collections.disjoint(collection, other);
	}

	//empty

	//enumeration

	static <T> EnumerationFromIterator<T> enumeration(Collection<T> collection) {
		return new EnumerationFromIterator(collection.iterator());
	}

	//fill

	static <T> void fill(List<? super T> list, T element) {
		//can not access java.util.Collections.FILL_THRESHOLD
		java.util.Collections.fill(list, element);
	}

	//filtered

	@Deprecated
	static <T> FilteredCollection<T> filteredCollection(Collection<T> collection, Predicate<T> predicate) {
		return new FilteredCollection(collection, predicate);
	}

	@Deprecated
	static <T> FilteredSet<T> filteredSet(Set<T> set, Predicate<T> predicate) {
		return new FilteredSet(set, predicate);
	}

	//frequency

	static <T, U> int frequency(Collection<T> collection, U element) {
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

	//indexMapFromList

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
	static <T> IndexMapFromList indexMapFromList(List<T> list) {
		return new IndexMapFromList(list);
	}

	//indexOfSubList

	static <T, U> int indexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.indexOfSubList(source, target);
	}

	//lastIndexOfSubList

	static <T, U> int lastIndexOfSubList(List<T> source, List<U> target) {
		//can not access java.util.Collections.INDEXOFSUBLIST_THRESHOLD
		return java.util.Collections.lastIndexOfSubList(source, target);
	}

	//list

	static <T> ArrayList<T> list(Enumeration<T> enumeration) {
		ArrayList list = new ArrayList();

		while (enumeration.hasMoreElements())
			list.add(enumeration.nextElement());

		return list;
	}

	//listFromIndexMap

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
	static <T> ListFromIndexMap<T> listFromIndexMap(Map<Integer, T> map) {
		return new ListFromIndexMap(map);
	}

	//max

	static <T extends Comparable<? super T>> T max(Collection<? extends T> collection) {
		Iterator<? extends T> iterator = collection.iterator();

		T max = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(max) > 0)
				max = element;
		}

		return max;
	}

	static <T> T max(Collection<? extends T> collection, Comparator<? super T> comparator) {
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

	//min

	static <T extends Comparable<? super T>> T min(Collection<? extends T> collection) {
		Iterator<? extends T> iterator = collection.iterator();

		T min = iterator.next();
		while (iterator.hasNext()) {
			T element = iterator.next();

			if (element.compareTo(min) < 0)
				min = element;
		}

		return min;
	}

	static <T> T min(Collection<? extends T> collection, Comparator<? super T> comparator) {
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

	//nCopies

	static <T> NCopiesList<T> nCopies(int size, T element) {
		return new NCopiesList(size, element);
	}

	//newSetFromMap

	static <T> SetFromMap<T> newSetFromMap(Map<T, Boolean> map) {
		return new SetFromMap(map);
	}

	//repalceAll

	static <T> boolean replaceAll(List<T> list, T oldValue, T newValue) {
		//can not access java.util.Collections.REPLACEALL_THRESHOLD
		return java.util.Collections.replaceAll(list, oldValue, newValue);
	}

	//reverse

	static <T> void reverse(List<T> list) {
		//can not access java.util.Collections.REVERSE_THRESHOLD
		java.util.Collections.reverse(list);
	}

	//reverseOrder

	static <T> Comparator<T> reverseOrder() {
		return (Comparator) Comparator.reverseOrder();
	}

	static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
		return comparator == null ?
			   (Comparator) Comparator.reverseOrder() :
			   comparator instanceof ReverseComparator ?
			   ((ReverseComparator) comparator).reversed() :
			   new ReverseComparator(comparator);
	}

	//rotate

	static <T> void rotate(List<T> list, int distance) {
		//can not access java.util.Collections.ROTATE_THRESHOLD
		java.util.Collections.rotate(list, distance);
	}

	//shuffle

	static <T> void shuffle(List<T> list) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list);
	}

	static <T> void shuffle(List<T> list, Random random) {
		//can not access java.util.Collections.SHUFFLE_THRESHOLD
		java.util.Collections.shuffle(list, random);
	}

	//singleton

	//sort

	static <T extends Comparable<? super T>> void sort(List<T> list) {
		//as in java.util.Collections.sort(List)
		list.sort(null);
	}

	static <T> void sort(List<T> list, Comparator<? super T> comparator) {
		//as in java.util.Collections.sort(List, Comparator)
		list.sort(comparator);
	}

	//swap

	static <T> void swap(List<T> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	//synchronized

	//unmodifiable

	//classes

	/**
	 * A set containing filtered elements from another set.
	 *
	 * @param <E> the type of the elements in the set.
	 */
	@Deprecated
	final class FilteredSet<E> extends FilteredCollection<E> implements Set<E> {
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
	final class IndexMapFromList<V> extends AbstractMap<Integer, V> {
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
	final class ListFromIndexMap<E> extends AbstractList<E> {
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

	class EnumerationFromIterator<E> implements Enumeration<E> {
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
	class FilteredCollection<E> extends AbstractCollection<E> {
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

	class LIFOQueue<E> extends AbstractQueue<E> implements Serializable {
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
	class NCopiesList<E> extends AbstractList<E> implements RandomAccess, Serializable {
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
	class ReverseComparator<T> implements Comparator<T>, Serializable {
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

	class SetFromMap<E> extends AbstractSet<E> implements Serializable {
		private static final long serialVersionUID = -4325561989691359892L;

		public SetFromMap(Map<E, Boolean> map) {
		}
	}
}
