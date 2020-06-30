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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Utils all about maps.
 *
 * @author LSafer
 * @version 0.1.3
 * @since 0.0.b ~2019.06.11
 */
public final class Collectionz {
	/**
	 * This is an util class and must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Collectionz() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Get a list that do read and write to the given {@code map}. The list will use the positive integer keys only.
	 *
	 * @param map the map that the returned list is reading-from/writing-to.
	 * @param <T> the type of the elements in the returned list.
	 * @return a list that do read and write to the given {@code map}.
	 * @throws NullPointerException if the given {@code map} is null.
	 */
	public static <T> MapList<T> asList(Map<Integer, T> map) {
		Objects.requireNonNull(map, "map");
		return new MapList(map);
	}

	/**
	 * Get a map that whenever the method {@link Map#put(Object, Object)} is invoked to it. It will put the value to an
	 * existing PUBLIC NON-FINALE field with the name of the key's value (only if it is string), and whenever the method
	 * {@link Map#get(Object)} is invoked to it. It will get the value of an existing PUBLIC field.
	 *
	 * @param object the object the returned map is reading-from/writing-to.
	 * @param <T>    the type of values on the returned map.
	 * @return a map that is remotely reading and writing to the fields of the given {@code object}.
	 * @throws NullPointerException if the given {@code object} is null.
	 */
	public static <T> ObjectMap asMap(Object object) {
		Objects.requireNonNull(object, "object");
		return new ObjectMap(object);
	}

	/**
	 * Get a map that do read and write to the given {@code list}. The map will use the indexes as the keys of the
	 * elements on the given list.
	 *
	 * @param list the list that the returned map is reading-from/writing-to.
	 * @param <T>  the type of values on the returned map.
	 * @return a map that do read and write to the given {@code list}.
	 * @throws NullPointerException if the given {@code list} is null.
	 */
	public static <T> ListMap asMap(List<T> list) {
		Objects.requireNonNull(list, "list");
		return new ListMap(list);
	}

	/**
	 * Get an iterator combined from the given iterators.
	 *
	 * @param <T>       the type of elements provided by the returned iterator.
	 * @param iterators to combine.
	 * @return an iterator combined from the given iterators.
	 * @throws NullPointerException if the given {@code iterators} or any of its elements is null.
	 */
	public static <T> Iterator<T> combine(Iterator<? extends T>... iterators) {
		Objects.requireNonNull(iterators, "iterators");
		return new CombinedIterator(iterators);
	}

	/**
	 * Get a group that reads directly from the given group, but can't modify it.
	 *
	 * @param group to get an unmodifiable group for.
	 * @param <T>   the type of the elements on given group.
	 * @return an unmodifiable group for the given group.
	 * @throws NullPointerException if the given {@code group} is null.
	 */
	public static <T> Group<T> unmodifiableGroup(Group<T> group) {
		Objects.requireNonNull(group, "group");
		return new UnmodifiableGroup(group);
	}

	/**
	 * An iterator iterating the elements of multiple iterators.
	 *
	 * @param <E> the type of elements returned by this iterator
	 */
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
	 * A map backed by a list.
	 *
	 * @param <V> the type of mapped values
	 */
	public static final class ListMap<V> extends AbstractMap<Integer, V> {
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
		private ListMap(List<V> list) {
			Objects.requireNonNull(list, "list");
			this.list = list;
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

		@Override
		public Set<Map.Entry<Integer, V>> entrySet() {
			return this.entrySet;
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
			 * The size of the list at the time creating this entry.
			 */
			private final int mod;

			/**
			 * Construct a new list-map-entry that reads from the given {@code index}.
			 *
			 * @param mod   the size of the list at the time creating this entry.
			 * @param index the index the constructing is reading at at the backed list.
			 * @throws IllegalArgumentException if the given {@code index} is negative, or if the given {@code mod} is
			 *                                  less or equals to the given {@code index}.
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
			public Integer getKey() {
				return this.index;
			}

			@Override
			public V getValue() {
				this.assertNoModification();
				return ListMap.this.list.get(this.index);
			}

			@Override
			public V setValue(V v) {
				this.assertNoModification();
				return ListMap.this.list.set(this.index, v);
			}

			/**
			 * Check if the list has modified in the interval of creating this entry and the time invoking this method.
			 *
			 * @throws ConcurrentModificationException if the list has been modified since creating this entry.
			 */
			private void assertNoModification() {
				int size = ListMap.this.list.size();
				if (this.mod != size)
					throw new ConcurrentModificationException("expected: " + this.mod + ", but was: " + size);
			}
		}

		/**
		 * An entry-set for maps that is backed by a list.
		 */
		public final class ListMapEntrySet extends AbstractSet<Map.Entry<Integer, V>> {
			/**
			 * Construct a new entry-set.
			 */
			private ListMapEntrySet() {
			}

			@Override
			public Iterator<Map.Entry<Integer, V>> iterator() {
				return (Iterator) new ListMapEntrySetIterator();
			}

			@Override
			public int size() {
				return ListMap.this.list.size();
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
				private int mod = ListMap.this.list.size();

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
					ListMap.this.list.remove(this.cursor);
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
				 * @throws ConcurrentModificationException if the list has been modified since creating this iterator.
				 */
				private void assertNoModification() {
					int size = ListMap.this.list.size();
					if (this.mod != size)
						throw new ConcurrentModificationException("expected: " + this.mod + ", but was: " + size);
				}
			}
		}
	}

	/**
	 * A list backed by a map.
	 *
	 * @param <E> the type of elements in this list.
	 */
	public static final class MapList<E> extends AbstractList<E> {
		/**
		 * The map backing this list.
		 */
		private final Map<Integer, E> map;

		/**
		 * Construct a new {@link MapList} backed by the given {@code map}.
		 *
		 * @param map the map backing the constructed list.
		 * @throws NullPointerException if the given {@code map} is null.
		 */
		private MapList(Map<Integer, E> map) {
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
				throw new IndexOutOfBoundsException("index=" + index + " is out of bounds allowed=" + allowed);
		}

		@Override
		public E get(int index) {
			MapList.assertWithinBounds(index, this.maxIndex());
			return this.map.get(index);
		}

		@Override
		public E set(int index, E element) {
			MapList.assertWithinBounds(index, this.maxIndex());
			return this.map.put(index, element);
		}

		@Override
		public void add(int index, E element) {
			MapList.assertWithinBounds(index, this.size());
			this.shiftIndexes(index, null, 1);
			this.map.put(index, element);
		}

		@Override
		public E remove(int index) {
			MapList.assertWithinBounds(index, this.maxIndex());
			E value = this.map.remove(index);
			this.shiftIndexes(index, null, -1);
			return value;
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
				if (key instanceof Integer && (noStart || (int) key >= start) && (noEnd || (int) key <= end))
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

	/**
	 * A map backed by the public fields of an object.
	 *
	 * @param <V> the type of mapped values
	 */
	public static final class ObjectMap<V> extends AbstractMap<String, V> {
		/**
		 * The object of the fields backing this map.
		 */
		private final Object object;
		/**
		 * The entry-set of this map.
		 */
		private Set<Map.Entry<String, V>> entrySet;

		/**
		 * Construct a new map that is backed by the fields of the given object.
		 *
		 * @param object the object of the fields backing the constructed map.
		 * @throws NullPointerException if the given {@code object} is null.
		 */
		private ObjectMap(Object object) {
			Objects.requireNonNull(object, "object");
			this.object = object;
		}

		@Override
		public V put(String key, V value) {
			for (Map.Entry<String, V> entry : this.entrySet())
				if (Objects.equals(entry.getKey(), key))
					return entry.setValue(value);

			throw new IllegalArgumentException("can't store the key: " + key);
		}

		@Override
		public Set<Map.Entry<String, V>> entrySet() {
			if (this.entrySet == null) {
				Set<ObjectMapEntry> entrySet = new HashSet();

				for (Field field : Reflection.getAllFields(this.object.getClass()))
					if (Modifier.isPublic(field.getModifiers()))
						entrySet.add(new ObjectMapEntry(field));

				this.entrySet = java.util.Collections.unmodifiableSet(entrySet);
			}

			return this.entrySet;
		}

		/**
		 * An entry for maps backed by the fields of an object.
		 */
		private final class ObjectMapEntry implements Map.Entry<String, V> {
			/**
			 * The field that this entry is reading from.
			 */
			private final Field field;

			/**
			 * Construct a new entry backed by the given field.
			 *
			 * @param field the field that the constructed entry will be reading from.
			 * @throws NullPointerException if the given {@code field} is null.
			 */
			private ObjectMapEntry(Field field) {
				Objects.requireNonNull(field, "field");
				this.field = field;
			}

			@Override
			public String getKey() {
				return this.field.getName();
			}

			@Override
			public V getValue() {
				try {
					this.field.setAccessible(true);
					return (V) this.field.get(ObjectMap.this.object);
				} catch (IllegalAccessException e) {
					throw (IllegalAccessError) new IllegalAccessError().initCause(e);
				}
			}

			@Override
			public V setValue(V value) {
				try {
					V old = this.getValue();
					this.field.set(ObjectMap.this.object, value);
					return old;
				} catch (IllegalAccessException e) {
					throw (IllegalAccessError) new IllegalAccessError().initCause(e);
				}
			}
		}
	}

	/**
	 * An unmodifiable-group backed by another group.
	 *
	 * @param <E> the type of the elements this group holds.
	 */
	public static final class UnmodifiableGroup<E> implements Group<E> {
		/**
		 * The group backing this unmodifiable-group.
		 */
		private final Group<E> group;

		/**
		 * Construct a new unmodifiable-group backed by the given {@code group}.
		 *
		 * @param group the group backing the constructed unmodifiable-group.
		 * @throws NullPointerException if the given {@code group} is null.
		 */
		private UnmodifiableGroup(Group<E> group) {
			Objects.requireNonNull(group, "group");
			this.group = group;
		}

		@Override
		public void forEach(Consumer<? super E> action) {
			this.group.forEach(action);
		}

		@Override
		public int hashCode() {
			return this.group.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return this.group.equals(obj);
		}

		@Override
		public String toString() {
			return this.group.toString();
		}

		@Override
		public int size() {
			return this.group.size();
		}

		@Override
		public boolean isEmpty() {
			return this.group.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return this.group.contains(o);
		}

		@Override
		public Iterator<E> iterator() {
			return new UnmodifiableGroupIterator();
		}

		@Override
		public Object[] toArray() {
			return this.group.toArray();
		}

		@Override
		public Object[] toArray(Object[] ts) {
			return this.group.toArray(ts);
		}

		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return this.group.containsAll(collection);
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		public boolean removeIf(Predicate<? super E> filter) {
			throw new UnsupportedOperationException("removeIf");
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public Group<E> subGroup(Object key, Predicate<E> predicate) {
			return Collectionz.unmodifiableGroup(this.group.subGroup(key, predicate));
		}

		@Override
		public Group<E> subGroup(Object key) {
			return Collectionz.unmodifiableGroup(this.group.subGroup(key));
		}

		/**
		 * An iterator for unmodifiable-groups.
		 */
		public final class UnmodifiableGroupIterator implements Iterator<E> {
			/**
			 * The backing iterator.
			 */
			private final Iterator<E> iterator = UnmodifiableGroup.this.iterator();

			/**
			 * Construct a new unmodifiable iterator.
			 */
			private UnmodifiableGroupIterator() {
			}

			@Override
			public boolean hasNext() {
				return this.iterator.hasNext();
			}

			@Override
			public E next() {
				return this.iterator.next();
			}
		}
	}
}
