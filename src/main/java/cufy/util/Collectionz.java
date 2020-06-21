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
	public static <T> List<T> asList(Map<Integer, T> map) {
		Objects.requireNonNull(map, "map");
		return new AbstractList<T>() {
			@Override
			public T get(int index) {
				this.checkBounds(index, this.maxIndex());
				return map.get(index);
			}

			@Override
			public T set(int index, T element) {
				this.checkBounds(index, this.maxIndex());
				return map.put(index, element);
			}

			@Override
			public void add(int index, T element) {
				this.checkBounds(index, this.size());
				this.shiftIndexes(index, null, 1);
				map.put(index, element);
			}

			@Override
			public T remove(int index) {
				this.checkBounds(index, this.maxIndex());
				T value = map.remove(index);
				this.shiftIndexes(index, null, -1);
				return value;
			}

			@Override
			public int size() {
				return this.maxIndex() + 1;
			}

			/**
			 * Check if the given index is valid or not.
			 *
			 * @param index the index to check
			 * @param allowed the allowed index (as maximum)
			 */
			private void checkBounds(int index, int allowed) {
				if (index < 0)
					throw new IndexOutOfBoundsException("index=" + index + " is negative");
				else if (index > allowed)
					throw new IndexOutOfBoundsException("index=" + index + " is out of bounds allowed=" + allowed);
			}

			/**
			 * The maximum integer. That have been stored as a key in this.
			 *
			 * @return the maximum index in the provided {@code map}.
			 */
			private int maxIndex() {
				int index;
				int max = -1;

				for (Object key : map.keySet())
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
			 *
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
				HashMap<Integer, T> modified = new HashMap<>();

				boolean noStart = start == null;
				boolean noEnd = end == null;

				map.forEach((Object key, T value) -> {
					if (key instanceof Integer && (noStart || (int) key >= start) && (noEnd || (int) key <= end))
						modified.put((int) key, value);
				});

				modified.forEach((key, value) -> {
					map.remove(key, value);
					key += by;

					if ((noStart || key >= start) && (noEnd || key <= end))
						map.put(key, value);
				});
			}
		};
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
	public static <T> Map<String, T> asMap(Object object) {
		Objects.requireNonNull(object, "object");
		return new AbstractMap<String, T>() {
			/**
			 * The entry-set of this map.
			 */
			private Set<Map.Entry<String, T>> entrySet;

			@Override
			public T put(String key, T value) {
				for (Map.Entry<String, T> entry : this.entrySet())
					if (Objects.equals(entry.getKey(), key))
						return entry.setValue(value);

				throw new IllegalArgumentException("can't store the key: " + key);
			}

			@Override
			public Set<Map.Entry<String, T>> entrySet() {
				if (this.entrySet == null) {
					HashSet<Map.Entry<String, T>> entrySet = new HashSet<>();

					for (Field field : Reflection.getAllFields(object.getClass()))
						if (Modifier.isPublic(field.getModifiers()))
							entrySet.add(new Map.Entry<String, T>() {
								@Override
								public String getKey() {
									return field.getName();
								}

								@Override
								public T getValue() {
									try {
										field.setAccessible(true);
										return (T) field.get(object);
									} catch (IllegalAccessException e) {
										throw (IllegalAccessError) new IllegalAccessError().initCause(e);
									}
								}

								@Override
								public T setValue(T value) {
									try {
										T old = this.getValue();
										field.set(object, value);
										return old;
									} catch (IllegalAccessException e) {
										throw (IllegalAccessError) new IllegalAccessError().initCause(e);
									}
								}
							});

					this.entrySet = java.util.Collections.unmodifiableSet(entrySet);
				}

				return this.entrySet;
			}
		};
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
	public static <T> Map<Integer, T> asMap(List<T> list) {
		Objects.requireNonNull(list, "list");
		return (Map) new AbstractMap<Object, T>() {
			/**
			 * The entrySet of this map.
			 */
			private Set<Map.Entry<Object, T>> entrySet;

			@Override
			public T put(Object key, T value) {
				if (!(key instanceof Integer) || (Integer) key < 0)
					throw new UnsupportedOperationException("can't store the key: " + key);

				int index = (Integer) key;

				if (list.size() > index)
					return list.set(index, value);
				else {
					list.addAll(java.util.Collections.nCopies(index - list.size(), null));
					list.add(value);
					return null;
				}
			}

			@Override
			public Set<Map.Entry<Object, T>> entrySet() {
				if (this.entrySet == null)
					this.entrySet = new AbstractSet<Map.Entry<Object, T>>() {
						@Override
						public Iterator<Map.Entry<Object, T>> iterator() {
							return new Iterator<Map.Entry<Object, T>>() {
								/**
								 * The current position of this iterator.
								 */
								private int cursor = 0;
								/**
								 * The initial size of this array once this iterator created.
								 */
								private int mod = list.size();

								@Override
								public boolean hasNext() {
									return this.mod > this.cursor;
								}

								@Override
								public Map.Entry<Object, T> next() {
									this.assertNoModification();
									this.assertInBounds();
									int key = this.cursor++;
									return new Map.Entry<Object, T>() {
										@Override
										public Integer getKey() {
											return key;
										}

										@Override
										public T getValue() {
											//noinspection UnqualifiedMethodAccess
											assertNoModification();
											return list.get(key);
										}

										@Override
										public T setValue(T t) {
											//noinspection UnqualifiedMethodAccess
											assertNoModification();
											return list.set(key, t);
										}
									};
								}

								@Override
								public void remove() {
									this.assertNoModification();
									list.remove(this.cursor);
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
									int size = list.size();
									if (this.mod != size)
										throw new ConcurrentModificationException(
												"expected: " + this.mod + ", but was: " + size);
								}
							};
						}

						@Override
						public int size() {
							return list.size();
						}
					};

				return this.entrySet;
			}
		};
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
		return new Iterator<T>() {
			/**
			 * The index of the current iterator.
			 */
			protected int cursor = 0;

			@Override
			public boolean hasNext() {
				for (int i = this.cursor; i < iterators.length; i++)
					if (iterators[i].hasNext())
						return true;
				return false;
			}

			@Override
			public T next() {
				for (; this.cursor < iterators.length; this.cursor++)
					if (iterators[this.cursor].hasNext())
						return iterators[this.cursor].next();

				//allow next calls to remove()
				this.cursor--;
				throw new NoSuchElementException("No more elements");
			}

			@Override
			public void remove() {
				iterators[this.cursor].remove();
			}
		};
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
		return new Group<T>() {
			@Override
			public void forEach(Consumer<? super T> action) {
				group.forEach(action);
			}

			@Override
			public int hashCode() {
				return group.hashCode();
			}

			@Override
			public boolean equals(Object obj) {
				return group.equals(obj);
			}

			@Override
			public String toString() {
				return group.toString();
			}

			@Override
			public int size() {
				return group.size();
			}

			@Override
			public boolean isEmpty() {
				return group.isEmpty();
			}

			@Override
			public boolean contains(Object o) {
				return group.contains(o);
			}

			@Override
			public Iterator<T> iterator() {
				Iterator<T> it = group.iterator();
				return new Iterator<T>() {
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}

					@Override
					public T next() {
						return it.next();
					}
				};
			}

			@Override
			public Object[] toArray() {
				return group.toArray();
			}

			@Override
			public Object[] toArray(Object[] ts) {
				return group.toArray(ts);
			}

			@Override
			public boolean add(T t) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean remove(Object o) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				return group.containsAll(collection);
			}

			@Override
			public boolean addAll(Collection<? extends T> collection) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean removeIf(Predicate<? super T> filter) {
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
			public Group<T> subGroup(Object key, Predicate<T> predicate) {
				return Collectionz.unmodifiableGroup(group.subGroup(key, predicate));
			}

			@Override
			public Group<T> subGroup(Object key) {
				return Collectionz.unmodifiableGroup(group.subGroup(key));
			}
		};
	}
}
