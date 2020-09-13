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

import java.util.Objects;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * An array specialized for {@link Object}s.
 *
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class ObjectArray<E> extends AbstractArray<E[], E> {
	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = -407181299777988791L;

	/**
	 * Construct a new array backed by a new actual array that have the given {@code length}.
	 *
	 * @param length the length of the new actual array backing the construct array.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public ObjectArray(int length) {
		super((E[]) new Object[length]);
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code length}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param length        the length of the new actual array backing the construct array.
	 * @throws NullPointerException       if the given {@code componentType} is null.
	 * @throws NegativeArraySizeException if the given {@code length} is negative.
	 * @throws ClassCastException         if the given {@code componentType} is not assignable from
	 *                                    {@link Object}.
	 * @see java.lang.reflect.Array#newInstance(Class, int)
	 * @since 0.1.5 ~2020.08.13
	 */
	public ObjectArray(Class<E> componentType, int length) {
		super((E[]) java.lang.reflect.Array.newInstance(componentType, length));
	}

	/**
	 * Construct a new array backed by a new actual array of the given {@code componentType} that
	 * have the given {@code dimensions}.
	 *
	 * @param componentType the component type of the constructed actual array.
	 * @param dimensions    the dimensions of the new actual array backing the construct array.
	 * @throws NullPointerException       if the given {@code componentType} or {@code dimensions}
	 *                                    is null.
	 * @throws NegativeArraySizeException if an element in the given {@code dimensions} is
	 *                                    negative.
	 * @throws ClassCastException         if the given {@code componentType} is not assignable from
	 *                                    {@link Object} (if only one dimensions has been
	 *                                    provided).
	 * @throws IllegalArgumentException   if {@code dimensions.length < 1} or {@code
	 *                                    dimensions.length > 255}.
	 * @see java.lang.reflect.Array#newInstance(Class, int[])
	 * @since 0.1.5 ~2020.08.13
	 */
	public ObjectArray(Class componentType, int... dimensions) {
		super((E[]) java.lang.reflect.Array.newInstance(componentType, dimensions));
	}

	/**
	 * Construct a new array backed by the given {@code array}.
	 *
	 * @param array the array to be backing the constructed array.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.08.05
	 */
	public ObjectArray(E[] array) {
		super(array);
	}

	/**
	 * Construct a new array backed by the specified range of the given {@code array}. The range
	 * starts at the given {@code beginIndex} and ends before the given {@code endIndex}.
	 *
	 * @param array      the array to be backing the constructed array.
	 * @param beginIndex the first index of the area at the given {@code array} to be backing the
	 *                   constructed array.
	 * @param endIndex   one past the last index of the area at the given {@code array} to be
	 *                   backing the constructed array.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code beginIndex < 0} or {@code endIndex >
	 *                                        array.length}.
	 * @throws IllegalArgumentException       if {@code beginIndex > endIndex}.
	 * @since 0.1.5 ~2020.08.05
	 */
	public ObjectArray(E[] array, int beginIndex, int endIndex) {
		super(array, beginIndex, endIndex);
	}

	@Override
	public int binarySearch(Object object) {
		int l = this.beginIndex;
		int h = this.endIndex - 1;
		while (l <= h) {
			int m = l + h >>> 1;
			Comparable mv = (Comparable) this.array[m];
			int r = mv.compareTo(object);

			if (r < 0)
				l = m + 1;
			else if (r > 0)
				h = m - 1;
			else
				return this.thumb(m);
		}

		return this.thumb(-(l + 1));
	}

	@Override
	public int binarySearch(E element, Comparator<? super E> comparator) {
		if (comparator == null)
			return this.binarySearch(element);

		int l = this.beginIndex;
		int h = this.endIndex - 1;
		while (l <= h) {
			int m = l + h >>> 1;
			E mv = this.array[m];
			int r = comparator.compare(mv, element);

			if (r < 0)
				l = m + 1;
			else if (r > 0)
				h = m - 1;
			else
				return this.thumb(m);
		}

		return this.thumb(-(l + 1));
	}

	@Override
	public ObjectArray<E> clone() {
		// noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
		return new ObjectArray(this.copy());
	}

	@Override
	public void copy(Object array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (array instanceof Object[])
			this.arraycopy((E[]) array, pos, length);
		else
			this.hardcopy(array, pos, length);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object instanceof ObjectArray) {
			ObjectArray<?> array = (ObjectArray<?>) object;

			if (array.length() == this.length()) {
				for (int i = array.beginIndex, j = this.beginIndex; i < array.endIndex; i++, j++) {
					if (this.eq(array.array[i], this.array[j]))
						continue;
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void fill(E element) {
		for (int i = this.beginIndex; i < this.endIndex; i++)
			this.array[i] = element;
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = this.beginIndex; i < this.endIndex; i++)
			consumer.accept(this.array[i]);
	}

	@Override
	public E get(int thumb) {
		return this.array[this.index(thumb)];
	}

	@Override
	public void hardcopy(Object[] array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (length < 0)
			throw new IndexOutOfBoundsException("length(" + length + ") < 0");
		if (pos < 0)
			throw new IndexOutOfBoundsException("pos(" + pos + ") < 0");

		int thisLength = this.endIndex - this.beginIndex;
		if (length > thisLength)
			throw new ArrayIndexOutOfBoundsException(
					"length(" + length + ") > this.length(" + thisLength + ")"
			);
		if (pos + length > array.length)
			throw new IndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + array.length +
					")"
			);

		try {
			//set boundaries
			int beginIndex = this.beginIndex;
			int endIndex = this.beginIndex + length;

			if (this.array == array && beginIndex < pos && endIndex > pos) {
				//for the source, here is the map
				//the first part: start:beginIndex end:pos
				//the second part: start:pos end:endIndex

				//for the destination, it is
				//much easier to just remember the position
				int j = pos;

				//transfer the first part directly
				for (int i = beginIndex; i < pos; i++, j++)
					array[j] = this.array[i];

				//temporarily copy the second part
				E[] clone = (E[]) new Object[endIndex - pos];
				System.arraycopy(this.array, pos, clone, 0, clone.length);

				//transfer the second part using a copy of it
				for (int i = 0; i < clone.length; i++, j++)
					array[j] = clone[i];

				return;
			}

			//if the arrays are not the same
			//Or, if the srcPos is after the destPos
			//Or, if the srcPos is behind the destPos but will never reach it.
			for (int i = beginIndex, j = pos; i < endIndex; i++, j++)
				array[j] = this.array[i];
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = this.beginIndex; i < this.endIndex; i++)
			hashCode = 31 * hashCode + this.hash(this.array[i]);

		return hashCode;
	}

	@Override
	public ObjectArrayIterator iterator() {
		return new ObjectArrayIterator();
	}

	@Override
	public ObjectArrayList list() {
		return new ObjectArrayList();
	}

	@Override
	public ObjectArrayListIterator listIterator() {
		return new ObjectArrayListIterator();
	}

	@Override
	public <K extends E, V extends E> ObjectArrayMap<K, V> map() {
		return new ObjectArrayMap();
	}

	@Override
	public void parallelPrefix(BinaryOperator<E> operator) {
		Objects.requireNonNull(operator, "operator");
		//fixme: -redirect
		java.util.Arrays.parallelPrefix(
				this.array,
				this.beginIndex,
				this.endIndex,
				operator
		);
	}

	@Override
	public void parallelSetAll(IntFunction<? extends E> function) {
		Objects.requireNonNull(function, "function");
		IntStream.range(this.beginIndex, this.endIndex)
				.parallel()
				.forEach(
						i -> this.array[i] = function.apply(this.thumb(i))
				);
	}

	@Override
	public void parallelSort() {
		//fixme: -redirect
		java.util.Arrays.parallelSort(
				(Comparable[]) this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public void parallelSort(Comparator<? super E> comparator) {
		if (comparator == null)
			this.parallelSort();
		else
			//fixme: -redirect
			java.util.Arrays.parallelSort(
					this.array,
					this.beginIndex,
					this.endIndex,
					comparator
			);
	}

	@Override
	public void set(int thumb, E element) {
		this.array[this.index(thumb)] = element;
	}

	@Override
	public void setAll(IntFunction<? extends E> function) {
		Objects.requireNonNull(function, "function");
		for (int i = this.beginIndex, j = 0; i < this.endIndex; i++, j++)
			this.array[i] = function.apply(j);
	}

	@Override
	public void sort() {
		//fixme: -redirect
		java.util.Arrays.sort(
				this.array,
				this.beginIndex,
				this.endIndex
		);
	}

	@Override
	public void sort(Comparator<? super E> comparator) {
		if (comparator == null)
			this.sort();
		else
			//fixme: -redirect
			java.util.Arrays.sort(
					this.array,
					this.beginIndex,
					this.endIndex,
					comparator
			);
	}

	@Override
	public ObjectArraySpliterator spliterator() {
		return new ObjectArraySpliterator();
	}

	@Override
	public ObjectArray<E> sub(int beginThumb, int endThumb) {
		this.range(beginThumb, endThumb);
		return new ObjectArray(
				this.array,
				this.beginIndex + beginThumb,
				this.beginIndex + endThumb
		);
	}

	@Override
	public String toString() {
		if (this.endIndex <= this.beginIndex)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = this.beginIndex;
		while (true) {
			builder.append(this.array[i]);

			if (++i >= this.endIndex)
				return builder.append("]")
						.toString();

			builder.append(", ");
		}
	}

	/**
	 * Manually copy elements from this array to the given {@code array}.
	 *
	 * @param array  the destination array.
	 * @param pos    the position where to start writing in the destination array.
	 * @param length how many elements to be copied.
	 * @throws NullPointerException      if the given {@code array} is null.
	 * @throws IndexOutOfBoundsException if {@code pos < 0} or {@code length < 0} or {@code pos +
	 *                                   length > array.length} or {@code length > this.length}.
	 * @throws ArrayStoreException       if an element can not be stored in the given {@code
	 *                                   array}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 * @since 0.1.5 ~2020.08.11
	 */
	public void hardcopy(Object array, int pos, int length) {
		Objects.requireNonNull(array, "array");
		if (!array.getClass().isArray())
			throw new ArrayStoreException(
					"hardcopy: destination type " + array.getClass().getName() + " is not an array"
			);
		if (length < 0)
			throw new ArrayIndexOutOfBoundsException("length(" + length + ") < 0");
		if (pos < 0)
			throw new ArrayIndexOutOfBoundsException("pos(" + pos + ") < 0");

		int thisLength = this.endIndex - this.beginIndex;
		int arrayLength = java.lang.reflect.Array.getLength(array);

		if (length > thisLength)
			throw new ArrayIndexOutOfBoundsException(
					"length(" + length + ") > this.length(" + thisLength + ")"
			);
		if (pos + length > arrayLength)
			throw new ArrayIndexOutOfBoundsException(
					"pos(" + pos + ") + length(" + length + ") > array.length(" + arrayLength +
					")"
			);

		try {
			//set boundaries
			int beginIndex = this.beginIndex;
			int endIndex = this.beginIndex + length;

			if (this.array == array && beginIndex < pos && endIndex > pos) {
				//for the source, here is the map
				//the first part: start:beginIndex end:pos
				//the second part: start:pos end:endIndex

				//for the destination, it is
				//much easier to just remember the position
				int j = pos;

				//transfer the first part directly
				for (int i = beginIndex; i < pos; i++, j++)
					//it is much easier to access this.array (no problem this.array == array)
					this.array[j] = this.array[i];

				//temporarily copy the second part
				E[] clone = (E[]) new Object[endIndex - pos];
				System.arraycopy(this.array, pos, clone, 0, clone.length);

				//transfer the second part using a copy of it
				for (int i = 0; i < clone.length; i++, j++)
					//it is much easier to access this.array (no problem this.array == array)
					this.array[j] = clone[i];

				return;
			}

			//if the arrays are not the same
			//Or, if the srcPos is after the destPos
			//Or, if the srcPos is behind the destPos but will never reach it.
			for (int i = beginIndex, j = pos; i < endIndex; i++, j++)
				java.lang.reflect.Array.set(array, j, this.array[i]);
		} catch (IllegalArgumentException e) {
			throw new ArrayStoreException(e.getMessage());
		}
	}

	/**
	 * An iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ObjectArrayIterator extends AbstractArrayIterator {
		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayIterator() {
		}

		/**
		 * Construct a new iterator iterating the elements in the enclosing array.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayIterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;
			for (int i = index; i < ObjectArray.this.endIndex; i++)
				consumer.accept(ObjectArray.this.array[i]);
		}

		@Override
		public E next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index++;
				return ObjectArray.this.array[index];
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * A list backed by the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ObjectArrayList extends AbstractArrayList {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6821775231116953767L;

		@Override
		public ObjectArrayList clone() {
			//noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
			return new ObjectArray(ObjectArray.this.copy())
					.new ObjectArrayList();
		}

		@Override
		public boolean contains(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++)
				if (ObjectArray.this.eq(object, ObjectArray.this.array[i]))
					return true;

			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof List) {
				List list = (List) object;

				if (list.size() == this.size()) {
					int i = ObjectArray.this.beginIndex;
					for (Object element : list) {
						if (i < ObjectArray.this.endIndex &&
							ObjectArray.this.eq(element, ObjectArray.this.array[i++]))
							continue;
						return false;
					}
					return true;
				}
			}
			return false;
		}

		@Override
		public E get(int thumb) {
			return ObjectArray.this.array[ObjectArray.this.index(thumb)];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++)
				hashCode = 31 * hashCode + ObjectArray.this.hash(ObjectArray.this.array[i]);

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++)
				if (ObjectArray.this.eq(object, ObjectArray.this.array[i]))
					return ObjectArray.this.thumb(i);

			return -1;
		}

		@Override
		public ObjectArrayIterator iterator() {
			return new ObjectArrayIterator();
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = ObjectArray.this.endIndex - 1; i >= ObjectArray.this.beginIndex; i--)
				if (ObjectArray.this.eq(object, ObjectArray.this.array[i]))
					return ObjectArray.this.thumb(i);

			return -1;
		}

		@Override
		public ObjectArrayListIterator listIterator() {
			return new ObjectArrayListIterator();
		}

		@Override
		public ObjectArrayListIterator listIterator(int beginThumb) {
			return new ObjectArrayListIterator(beginThumb);
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i++) {
				//done catch someone-else's exceptions!
				E n = operator.apply(ObjectArray.this.array[i]);

				try {
					ObjectArray.this.array[i] = n;
				} catch (ArrayStoreException exception) {
					//as in the specifications!
					throw new ClassCastException(exception.getMessage());
				}
			}
		}

		@Override
		public E set(int thumb, E element) {
			int i = ObjectArray.this.index(thumb);
			E old = ObjectArray.this.array[i];
			try {
				ObjectArray.this.array[i] = element;
			} catch (ArrayStoreException e) {
				//as in the specifications!
				throw new ClassCastException(e.getMessage());
			}
			return old;
		}

		@Override
		public ObjectArraySpliterator spliterator() {
			return new ObjectArraySpliterator();
		}

		@Override
		public ObjectArrayList subList(int beginIndex, int endIndex) {
			ObjectArray.this.range(beginIndex, endIndex);
			return new ObjectArray(
					ObjectArray.this.array,
					ObjectArray.this.beginIndex,
					ObjectArray.this.beginIndex + endIndex
			).new ObjectArrayList();
		}
	}

	/**
	 * A list iterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public class ObjectArrayListIterator extends AbstractArrayListIterator {
		/**
		 * Construct a new list iterator iterating the elements in the enclosing array.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayListIterator() {
		}

		/**
		 * Construct a new list iterator iterating the elements in the enclosing array, starting
		 * from the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed iterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArrayListIterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;
			this.last = ObjectArray.this.endIndex - 1;
			for (int i = index; i < ObjectArray.this.endIndex; i++)
				consumer.accept(ObjectArray.this.array[i]);
		}

		@Override
		public E next() {
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index++;
				return ObjectArray.this.array[this.last = index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public E previous() {
			int index = this.index - 1;

			if (index >= ObjectArray.this.beginIndex) {
				this.index--;
				return ObjectArray.this.array[this.last = index];
			}

			throw new NoSuchElementException();
		}

		@Override
		public void set(E element) {
			int index = this.last;

			if (index == -1)
				throw new IllegalStateException();

			try {
				ObjectArray.this.array[index] = element;
			} catch (ArrayStoreException e) {
				//as in the specifications!
				throw new ClassCastException(e.getMessage());
			}
		}
	}

	/**
	 * A map backed by the enclosing array.
	 *
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public class ObjectArrayMap<K extends E, V extends E> extends AbstractArrayMap<K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 1924164470541987638L;

		@Override
		public ObjectArrayMap<K, V> clone() {
			//noinspection CloneCallsConstructors,OverridableMethodCallDuringObjectConstruction
			return new ObjectArray(ObjectArray.this.copy())
					.new ObjectArrayMap();
		}

		@Override
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (ObjectArray.this.eq(key, k)) {
					V v = (V) ObjectArray.this.array[i + 1];
					V value = function.apply(k, v);

					if (value == null && v != null)
						throw new UnsupportedOperationException("remove");

					try {
						ObjectArray.this.array[i + 1] = value;
					} catch (ArrayStoreException e) {
						//as in the specifications!
						throw new ClassCastException(e.getMessage());
					}

					return value;
				}
			}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (ObjectArray.this.eq(key, k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (v == null) {
						//done catch someone-else's exceptions!
						V value = function.apply(k);

						try {
							ObjectArray.this.array[i + 1] = value;
						} catch (ArrayStoreException e) {
							//as in the specifications!
							throw new ClassCastException(e.getMessage());
						}

						return value;
					}

					return v;
				}
			}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				K k = (K) ObjectArray.this.array[i];

				if (ObjectArray.this.eq(key, k)) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (v != null) {
						V value = function.apply(k, v);

						if (value == null)
							throw new UnsupportedOperationException("remove");

						try {
							ObjectArray.this.array[i + 1] = value;
						} catch (ArrayStoreException e) {
							//as in the specifications!
							throw new ClassCastException(e.getMessage());
						}

						return value;
					}

					return null;
				}
			}

			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i]))
					return true;

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int y = ObjectArray.this.beginIndex + 1; y < ObjectArray.this.endIndex; y += 2)
				if (ObjectArray.this.eq(value, ObjectArray.this.array[y]))
					return true;

			return false;
		}

		@Override
		public ObjectArrayEntrySet entrySet() {
			return new ObjectArrayEntrySet();
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof Map) {
				Map<?, ?> map = (Map) object;

				if (map.size() == this.size()) {
					for0:
					for (Entry entry : map.entrySet()) {
						Object key = entry.getKey();

						for (int i = ObjectArray.this.beginIndex;
							 i < ObjectArray.this.endIndex; i += 2)
							if (ObjectArray.this.eq(
									key,
									ObjectArray.this.array[i]
							)) {
								if (ObjectArray.this.eq(
										entry.getValue(),
										ObjectArray.this.array[i + 1]
								))
									continue for0;
								break;
							}
						return false;
					}
					return true;
				}
			}
			return false;
		}

		@Override
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				consumer.accept((K) ObjectArray.this.array[i], (V) ObjectArray.this.array[i + 1]);
		}

		@Override
		public V get(Object key) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i]))
					return (V) ObjectArray.this.array[i + 1];

			return null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i]))
					return (V) ObjectArray.this.array[i + 1];

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				hashCode += ObjectArray.this.hash(ObjectArray.this.array[i]) ^
							ObjectArray.this.hash(ObjectArray.this.array[i + 1]);

			return hashCode;
		}

		@Override
		public ObjectArrayKeySet keySet() {
			return new ObjectArrayKeySet();
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			Objects.requireNonNull(value, "value");
			Objects.requireNonNull(function, "function");

			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
					V v = (V) ObjectArray.this.array[i + 1];
					V newValue = v == null ? value : function.apply(v, value);

					if (newValue == null)
						throw new UnsupportedOperationException("remove");

					try {
						ObjectArray.this.array[i + 1] = newValue;
					} catch (ArrayStoreException e) {
						//as in the specifications!
						throw new ClassCastException(e.getMessage());
					}

					return newValue;
				}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public V put(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
					V v = (V) ObjectArray.this.array[i + 1];

					try {
						ObjectArray.this.array[i + 1] = value;
					} catch (ArrayStoreException e) {
						//as in the specifications!
						throw new ClassCastException(e.getMessage());
					}

					return v;
				}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(map, "map");

			for0:
			for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
				K key = entry.getKey();

				for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
					if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
						//done catch someone-else's exceptions!
						V value = entry.getValue();

						try {
							ObjectArray.this.array[i + 1] = value;
						} catch (ArrayStoreException e) {
							//as in the specifications!
							throw new ClassCastException(e.getMessage());
						}

						continue for0;
					}

				throw new IllegalArgumentException("Key not found");
			}
		}

		@Override
		public V putIfAbsent(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
					V v = (V) ObjectArray.this.array[i + 1];

					if (v == null)
						try {
							ObjectArray.this.array[i + 1] = value;
						} catch (ArrayStoreException e) {
							//as in the specifications!
							throw new ClassCastException(e.getMessage());
						}

					return v;
				}

			throw new IllegalArgumentException("Key not found");
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
					if (ObjectArray.this.eq(oldValue, ObjectArray.this.array[i + 1])) {
						try {
							ObjectArray.this.array[i + 1] = newValue;
						} catch (ArrayStoreException e) {
							//as in the specifications!
							throw new ClassCastException(e.getMessage());
						}

						return true;
					}

					break;
				}

			return false;
		}

		@Override
		public V replace(K key, V value) {
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2)
				if (ObjectArray.this.eq(key, ObjectArray.this.array[i])) {
					V v = (V) ObjectArray.this.array[i + 1];

					try {
						ObjectArray.this.array[i + 1] = value;
					} catch (ArrayStoreException e) {
						//as in the specifications!
						throw new ClassCastException(e.getMessage());
					}

					return v;
				}

			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");
			for (int i = ObjectArray.this.beginIndex; i < ObjectArray.this.endIndex; i += 2) {
				//done catch someone-else's exceptions!
				V n = function.apply(
						(K) ObjectArray.this.array[i],
						(V) ObjectArray.this.array[i + 1]
				);

				try {
					ObjectArray.this.array[i + 1] = n;
				} catch (ArrayStoreException e) {
					//as in the specifications!
					throw new ClassCastException(e.getMessage());
				}
			}
		}

		@Override
		public String toString() {
			if (this.isEmpty())
				return "{}";

			StringBuilder builder = new StringBuilder("{");

			int i = ObjectArray.this.beginIndex;
			while (true) {
				builder.append(ObjectArray.this.array[i])
						.append("=")
						.append(ObjectArray.this.array[i + 1]);

				if ((i += 2) >= ObjectArray.this.endIndex)
					return builder.append("}")
							.toString();

				builder.append(", ");
			}
		}

		@Override
		public ObjectArrayValues values() {
			return new ObjectArrayValues();
		}

		/**
		 * An entry backed by a range from {@code index} to {@code index + 1} in the enclosing
		 * array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayEntry extends AbstractArrayEntry {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -7098093446276533400L;

			/**
			 * Construct a new entry backed by a range from {@code index} to {@code index + 1} in
			 * the enclosing array.
			 *
			 * @param thumb the index to where the key (followed by the value) will be in the
			 *              constructed entry.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >=
			 *                                   length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayEntry(int thumb) {
				super(thumb);
			}

			@Override
			public ObjectArrayEntry clone() {
				//noinspection CloneCallsConstructors
				return new ObjectArray<>(
						new ObjectArray<>(
								ObjectArray.this.array,
								ObjectArray.this.beginIndex + this.index,
								ObjectArray.this.endIndex + this.index + 2
						).copy()
				).new ObjectArrayMap<K, V>()
						.new ObjectArrayEntry(0);
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Entry) {
					Entry entry = (Entry) object;

					if (ObjectArray.this.eq(
							entry.getKey(),
							ObjectArray.this.array[this.index]
					))
						return ObjectArray.this.eq(
								entry.getValue(),
								ObjectArray.this.array[this.index + 1]
						);
				}

				return false;
			}

			@Override
			public K getKey() {
				return (K) ObjectArray.this.array[this.index];
			}

			@Override
			public V getValue() {
				return (V) ObjectArray.this.array[this.index + 1];
			}

			@Override
			public int hashCode() {
				return ObjectArray.this.hash(ObjectArray.this.array[this.index]) ^
					   ObjectArray.this.hash(ObjectArray.this.array[this.index + 1]);
			}

			@Override
			public V setValue(V value) {
				V v = (V) ObjectArray.this.array[this.index + 1];

				try {
					ObjectArray.this.array[this.index + 1] = value;
				} catch (ArrayStoreException e) {
					//as in the specifications
					throw new ClassCastException(e.getMessage());
				}

				return v;
			}

			@Override
			public String toString() {
				return ObjectArray.this.array[this.index] + "=" +
					   ObjectArray.this.array[this.index + 1];
			}
		}

		/**
		 * An iterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayEntryIterator extends AbstractArrayEntryIterator {
			/**
			 * Construct a new iterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayEntryIterator() {
			}

			/**
			 * Construct a new iterator iterating the entries in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayEntryIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int t = ObjectArray.this.thumb(index),
					 l = ObjectArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new ObjectArrayEntry(t));
			}

			@Override
			public ObjectArrayEntry next() {
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					return new ObjectArrayEntry(ObjectArray.this.thumb(index));
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A set backed by the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayEntrySet extends AbstractArrayEntrySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -1358655666106432532L;

			@Override
			public ObjectArrayEntrySet clone() {
				//noinspection CloneCallsConstructors,OverridableMethodCallDuringObjectConstruction
				return new ObjectArray<>(ObjectArray.this.copy())
						.new ObjectArrayMap<K, V>()
						.new ObjectArrayEntrySet();
			}

			@Override
			public boolean contains(Object object) {
				if (object instanceof Entry) {
					Entry entry = (Entry) object;
					Object key = entry.getKey();

					for (int i = ObjectArray.this.beginIndex;
						 i < ObjectArray.this.endIndex;
						 i += 2)
						if (ObjectArray.this.eq(
								key,
								ObjectArray.this.array[i]
						)) {
							if (ObjectArray.this.eq(
									entry.getValue(),
									ObjectArray.this.array[i + 1]
							))
								return true;

							break;
						}
				}

				return false;
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Set) {
					Set set = (Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object obj : set) {
							if (obj instanceof Entry) {
								Entry entry = (Entry) obj;
								Object key = entry.getKey();

								for (int i = ObjectArray.this.beginIndex;
									 i < ObjectArray.this.endIndex;
									 i += 2)
									if (ObjectArray.this.eq(
											key,
											ObjectArray.this.array[i]
									)) {
										if (ObjectArray.this.eq(
												entry.getValue(),
												ObjectArray.this.array[i + 1]))
											continue for0;
										break;
									}
							}
							return false;
						}
						return true;
					}
				}
				return false;
			}

			@Override
			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int t = 0,
					 l = ObjectArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new ObjectArrayEntry(t));
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = ObjectArray.this.beginIndex;
					 i < ObjectArray.this.endIndex;
					 i += 2)
					hashCode += ObjectArray.this.hash(ObjectArray.this.array[i]) ^
								ObjectArray.this.hash(ObjectArray.this.array[i + 1]);

				return hashCode;
			}

			@Override
			public ObjectArrayEntryIterator iterator() {
				return new ObjectArrayEntryIterator();
			}

			@Override
			public ObjectArrayEntrySpliterator spliterator() {
				return new ObjectArrayEntrySpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int t = 0,
					 l = ObjectArray.this.length(),
					 j = 0;
					 t < l;
					 t += 2, j++)
					product[j] = new ObjectArrayEntry(t);

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = ObjectArray.this.endIndex - ObjectArray.this.beginIndex >>> 1;
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int t = 0,
					 l = ObjectArray.this.length(),
					 j = 0;
					 t < l;
					 t += 2, j++)
					product[j] = (T) new ObjectArrayEntry(t);

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = ObjectArray.this.beginIndex;
				while (true) {
					builder.append(ObjectArray.this.array[i])
							.append("=")
							.append(ObjectArray.this.array[i + 1]);

					if ((i += 2) >= ObjectArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}

		/**
		 * A spliterator iterating the entries in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class ObjectArrayEntrySpliterator extends AbstractArrayEntrySpliterator {
			/**
			 * Construct a new spliterator iterating the entries in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayEntrySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the entries in the enclosing array, starting
			 * from the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayEntrySpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int t = 0,
					 l = ObjectArray.this.length();
					 t < l;
					 t += 2)
					consumer.accept(new ObjectArrayEntry(t));
			}

			@Override
			public boolean tryAdvance(Consumer<? super Entry<K, V>> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					consumer.accept(new ObjectArrayEntry(ObjectArray.this.thumb(index)));
					return true;
				}

				return false;
			}

			@Override
			public ObjectArrayEntrySpliterator trySplit() {
				int index = this.index;
				int midIndex = index + ObjectArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new ObjectArray<>(
							ObjectArray.this.array,
							ObjectArray.this.beginIndex + index,
							ObjectArray.this.beginIndex + midIndex
					).new ObjectArrayMap<K, V>()
							.new ObjectArrayEntrySpliterator();
				}

				return null;
			}
		}

		/**
		 * An iterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayKeyIterator extends AbstractArrayKeyIterator {
			/**
			 * Construct a new iterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayKeyIterator() {
			}

			/**
			 * Construct a new iterator iterating the keys in the enclosing array, starting from the
			 * given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayKeyIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super K> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int i = index;
					 i < ObjectArray.this.endIndex;
					 i += 2)
					consumer.accept((K) ObjectArray.this.array[i]);
			}

			@Override
			public K next() {
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					return (K) ObjectArray.this.array[index];
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A set backed by the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayKeySet extends AbstractArrayKeySet {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = 6047336320717832956L;

			@Override
			public ObjectArrayKeySet clone() {
				//noinspection CloneCallsConstructors,OverridableMethodCallDuringObjectConstruction
				return new ObjectArray<>(ObjectArray.this.copy())
						.new ObjectArrayMap<K, V>()
						.new ObjectArrayKeySet();
			}

			@Override
			public boolean equals(Object object) {
				if (object == this)
					return true;
				if (object instanceof Set) {
					Set set = (Set) object;

					if (set.size() == this.size()) {
						for0:
						for (Object key : set) {
							for (int i = ObjectArray.this.beginIndex;
								 i < ObjectArray.this.endIndex;
								 i += 2)
								if (ObjectArray.this.eq(
										key,
										ObjectArray.this.array[i]
								))
									continue for0;
							return false;
						}
						return true;
					}
				}
				return false;
			}

			@Override
			public void forEach(Consumer<? super K> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int i = ObjectArray.this.beginIndex;
					 i < ObjectArray.this.endIndex;
					 i += 2)
					consumer.accept((K) ObjectArray.this.array[i]);
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int i = ObjectArray.this.beginIndex;
					 i < ObjectArray.this.endIndex;
					 i += 2)
					hashCode += ObjectArray.this.hash(ObjectArray.this.array[i]);

				return hashCode;
			}

			@Override
			public ObjectArrayKeyIterator iterator() {
				return new ObjectArrayKeyIterator();
			}

			@Override
			public ObjectArrayKeySpliterator spliterator() {
				return new ObjectArrayKeySpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int i = ObjectArray.this.beginIndex,
					 j = 0;
					 i < ObjectArray.this.endIndex;
					 i += 2, j++)
					product[j] = ObjectArray.this.array[i];

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int i = ObjectArray.this.beginIndex,
					 j = 0;
					 i < ObjectArray.this.endIndex;
					 i += 2, j++)
					product[j] = (T) ObjectArray.this.array[i];

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int i = ObjectArray.this.beginIndex;
				while (true) {
					builder.append(ObjectArray.this.array[i]);

					if ((i += 2) >= ObjectArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}

		/**
		 * A spliterator iterating the keys in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class ObjectArrayKeySpliterator extends AbstractArrayKeySpliterator {
			/**
			 * Construct a new spliterator iterating the keys in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayKeySpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the keys in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayKeySpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super K> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int i = index;
					 i < ObjectArray.this.endIndex;
					 i += 2)
					consumer.accept((K) ObjectArray.this.array[i]);
			}

			@Override
			public boolean tryAdvance(Consumer<? super K> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					consumer.accept((K) ObjectArray.this.array[index]);
					return true;
				}

				return false;
			}

			@Override
			public ObjectArrayKeySpliterator trySplit() {
				int index = this.index;
				int midIndex = index + ObjectArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new ObjectArray<>(
							ObjectArray.this.array,
							ObjectArray.this.beginIndex + index,
							ObjectArray.this.beginIndex + midIndex
					).new ObjectArrayMap<K, V>()
							.new ObjectArrayKeySpliterator();
				}

				return null;
			}
		}

		/**
		 * An iterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayValueIterator extends AbstractArrayValueIterator {
			/**
			 * Construct a new iterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayValueIterator() {
			}

			/**
			 * Construct a new iterator iterating the values in the enclosing array, starting from
			 * the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed iterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayValueIterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super V> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int y = index + 1;
					 y < ObjectArray.this.endIndex;
					 y += 2)
					consumer.accept((V) ObjectArray.this.array[y]);
			}

			@Override
			public V next() {
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					return (V) ObjectArray.this.array[index + 1];
				}

				throw new NoSuchElementException();
			}
		}

		/**
		 * A spliterator iterating the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.02
		 */
		public class ObjectArrayValueSpliterator extends AbstractArrayValueSpliterator {
			/**
			 * Construct a new spliterator iterating the values in the enclosing array.
			 *
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayValueSpliterator() {
			}

			/**
			 * Construct a new spliterator iterating the values in the enclosing array, starting
			 * from the given {@code index}.
			 *
			 * @param beginThumb the initial position of the constructed spliterator.
			 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
			 * @since 0.1.5 ~2020.08.06
			 */
			public ObjectArrayValueSpliterator(int beginThumb) {
				super(beginThumb);
			}

			@Override
			public void forEachRemaining(Consumer<? super V> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;
				this.index = ObjectArray.this.endIndex;
				for (int y = index + 1;
					 y < ObjectArray.this.endIndex;
					 y += 2)
					consumer.accept((V) ObjectArray.this.array[y]);
			}

			@Override
			public boolean tryAdvance(Consumer<? super V> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				int index = this.index;

				if (index < ObjectArray.this.endIndex) {
					this.index += 2;
					consumer.accept((V) ObjectArray.this.array[index + 1]);
					return true;
				}

				return false;
			}

			@Override
			public ObjectArrayValueSpliterator trySplit() {
				int index = this.index;
				int midIndex = index + ObjectArray.this.endIndex >>> 1;

				if (index < midIndex) {
					this.index = midIndex;
					return new ObjectArray<>(
							ObjectArray.this.array,
							ObjectArray.this.beginIndex + index,
							ObjectArray.this.beginIndex + midIndex
					).new ObjectArrayMap<K, V>()
							.new ObjectArrayValueSpliterator();
				}

				return null;
			}
		}

		/**
		 * A collection backed by the values in the enclosing array.
		 *
		 * @author LSafer
		 * @version 0.1.5
		 * @since 0.1.5 ~2020.08.03
		 */
		public class ObjectArrayValues extends AbstractArrayValues {
			@SuppressWarnings("JavaDoc")
			private static final long serialVersionUID = -6890162958501631510L;

			@Override
			public ObjectArrayValues clone() {
				//noinspection OverridableMethodCallDuringObjectConstruction,CloneCallsConstructors
				return new ObjectArray<>(ObjectArray.this.copy())
						.new ObjectArrayMap<K, V>()
						.new ObjectArrayValues();
			}

			@Override
			public boolean equals(Object object) {
				return object == this;
			}

			@Override
			public void forEach(Consumer<? super V> consumer) {
				Objects.requireNonNull(consumer, "consumer");
				for (int y = ObjectArray.this.beginIndex + 1;
					 y < ObjectArray.this.endIndex;
					 y += 2)
					consumer.accept((V) ObjectArray.this.array[y]);
			}

			@Override
			public int hashCode() {
				int hashCode = 0;

				for (int y = ObjectArray.this.beginIndex + 1;
					 y < ObjectArray.this.endIndex;
					 y += 2)
					hashCode += ObjectArray.this.hash(ObjectArray.this.array[y]);

				return hashCode;
			}

			@Override
			public ObjectArrayValueIterator iterator() {
				return new ObjectArrayValueIterator();
			}

			@Override
			public ObjectArrayValueSpliterator spliterator() {
				return new ObjectArrayValueSpliterator();
			}

			@Override
			public Object[] toArray() {
				Object[] product = new Object[this.size()];

				for (int y = ObjectArray.this.beginIndex + 1,
					 j = 0;
					 y < ObjectArray.this.endIndex;
					 y += 2, j++)
					product[j] = ObjectArray.this.array[y];

				return product;
			}

			@Override
			public <T> T[] toArray(T[] array) {
				Objects.requireNonNull(array, "array");
				int length = this.size();
				T[] product = array;

				if (array.length < length)
					product = (T[]) java.lang.reflect.Array.newInstance(
							array.getClass().getComponentType(),
							length
					);
				else if (array.length > length)
					product[length] = null;

				for (int y = ObjectArray.this.beginIndex + 1,
					 j = 0;
					 y < ObjectArray.this.endIndex;
					 y += 2, j++)
					product[j] = (T) ObjectArray.this.array[y];

				return product;
			}

			@Override
			public String toString() {
				if (this.isEmpty())
					return "[]";

				StringBuilder builder = new StringBuilder("[");

				int y = ObjectArray.this.beginIndex + 1;
				while (true) {
					builder.append(ObjectArray.this.array[y]);

					if ((y += 2) >= ObjectArray.this.endIndex)
						return builder.append("]")
								.toString();

					builder.append(", ");
				}
			}
		}
	}

	/**
	 * A spliterator iterating the elements in the enclosing array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public class ObjectArraySpliterator extends AbstractArraySpliterator {
		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArraySpliterator() {
		}

		/**
		 * Construct a new spliterator iterating the elements in the enclosing array, starting from
		 * the given {@code index}.
		 *
		 * @param beginThumb the initial position of the constructed spliterator.
		 * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > length}.
		 * @since 0.1.5 ~2020.08.06
		 */
		public ObjectArraySpliterator(int beginThumb) {
			super(beginThumb);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;
			this.index = ObjectArray.this.endIndex;
			for (int i = index; i < ObjectArray.this.endIndex; i++)
				consumer.accept(ObjectArray.this.array[i]);
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.index;

			if (index < ObjectArray.this.endIndex) {
				this.index++;
				consumer.accept(ObjectArray.this.array[index]);
				return true;
			}

			return false;
		}

		@Override
		public ObjectArraySpliterator trySplit() {
			int index = this.index;
			int midIndex = index + ObjectArray.this.endIndex >>> 1;

			if (index < midIndex) {
				this.index = midIndex;
				return ObjectArray.this.sub(index, midIndex)
						.new ObjectArraySpliterator();
			}

			return null;
		}
	}
}
