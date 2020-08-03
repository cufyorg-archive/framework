package cufy.util.array;

import cufy.util.Objects;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A holder for an array of {@link Object}s.
 *
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public class ObjectArray<E> extends Array<E[], E> {
	/**
	 * Construct a new array holder for the given {@code array}.
	 *
	 * @param array the array to be held by the constructed array holder.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	public ObjectArray(E... array) {
		super(array);
	}

	@Override
	public List<E> asList() {
		return new List(this.array);
	}

	@Override
	public Map<E, ?, ?> asMap() {
		return null;
	}

	@Override
	public Set<E[], E> asSet() {
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object instanceof ObjectArray) {
			Object[] array = ((ObjectArray<?>) object).array;

			if (array.length == this.array.length) {
				for (int i = 0; i < this.array.length; i++) {
					Object oe = array[i];
					Object te = this.array[i];

					if (oe != te && (oe == null || !oe.equals(te)))
						return false;
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		for (int i = 0; i < this.array.length; i++)
			consumer.accept(this.array[i]);
	}

	@Override
	public E get(int index) {
		return this.array[index];
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = 0; i < this.array.length; i++) {
			Object element = this.array[i];
			hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
		}

		return hashCode;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator(this.array);
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ListIterator(this.array);
	}

	@Override
	public void set(int index, E element) {
		this.array[index] = element;
	}

	@Override
	public Spliterator<E> spliterator() {
		return new Spliterator(this.array, java.util.Spliterator.SORTED | java.util.Spliterator.IMMUTABLE);
	}

	@Override
	public String toString() {
		if (this.array.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			builder.append(this.array[i]);

			if (++i < this.array.length) {
				builder.append(", ");
				continue;
			}

			return builder.append("]")
					.toString();
		}
	}

	/**
	 * An entry backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the key.
	 * @param <V> the type of the value.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public static final class Entry<E, K extends E, V extends E> extends Array.Entry<E[], E, K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5205619549439266724L;

		/**
		 * Construct a new entry backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed entry.
		 * @param index the index of the key of the constructed entry.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= array.length}.
		 * @since 0.1.5
		 */
		public Entry(E[] array, int index) {
			super(
					array,
					index,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (o instanceof java.util.Map.Entry) {
				if (o instanceof ObjectArray.Entry &&
					((Entry) o).array == this.array &&
					((Entry) o).index == this.index)
					return true;

				Object key = ((java.util.Map.Entry) o).getKey();
				Object k = this.array[this.index];

				if (key == k || key != null && key.equals(k)) {
					Object value = ((java.util.Map.Entry) o).getValue();
					Object v = this.array[this.index + 1];

					return value == v || value != null && value.equals(v);
				}
			}

			return false;
		}

		@Override
		public K getKey() {
			return (K) this.array[this.index];
		}

		@Override
		public V getValue() {
			return (V) this.array[this.index + 1];
		}

		@Override
		public int hashCode() {
			K k = (K) this.array[this.index];
			V v = (V) this.array[this.index + 1];
			return (k == null ? 0 : k.hashCode()) ^
				   (v == null ? 0 : v.hashCode());
		}

		@Override
		public V setValue(V value) {
			V v = (V) this.array[this.index + 1];
			this.array[this.index + 1] = value;
			return v;
		}

		@Override
		public String toString() {
			K k = (K) this.array[this.index];
			V v = (V) this.array[this.index + 1];
			return k + "=" + v;
		}
	}

	/**
	 * Get an iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the key.
	 * @param <V> the type of the value.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public static final class EntryIterator<E, K extends E, V extends E> extends Array.EntryIterator<E[], E, K, V> {
		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public EntryIterator(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator multiplied by 2.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public EntryIterator(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			int index = this.index;
			this.index = this.length;

			for (int i = this.offset + index, l = this.offset + this.length; i < l; i += 2)
				consumer.accept(new Entry(this.array, i));
		}

		@Override
		public Entry<E, K, V> next() {
			int i = this.index;
			this.index += 2;

			if (i < this.length)
				return new Entry(this.array, this.offset + i);

			throw new NoSuchElementException();
		}
	}

	/**
	 * A set backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public static final class EntrySet<E, K extends E, V extends E> extends Array.EntrySet<E[], E, K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -2066094809480697534L;

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed set.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5 ~2020.07.24
		 */
		public EntrySet(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array  the array backing the constructed set.
		 * @param offset the offset where the area backing the constructed set in the given {@code array} is starting.
		 * @param length the length of the constructed set multiplied by 2.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public EntrySet(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public boolean add(java.util.Map.Entry<K, V> entry) {
			K key = entry.getKey();

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V value = entry.getValue();
					V v = (V) this.array[this.offset + i + 1];

					if (value == v || value != null && value.equals(v))
						//already exists
						return false;

					break;
				}
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object entry : collection) {
				if (entry instanceof java.util.Map.Entry) {
					Object key = ((java.util.Map.Entry) entry).getKey();

					for (int i = 0; i < this.length; i += 2) {
						K k = (K) this.array[this.offset + i];

						if (key == k || key != null && key.equals(k)) {
							Object value = ((java.util.Map.Entry) entry).getValue();
							V v = (V) this.array[this.offset + i + 1];

							if (value == v || value != null && value.equals(v))
								//already exists
								continue for0;

							break;
						}
					}
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			if (object instanceof java.util.Map.Entry) {
				Object key = ((java.util.Map.Entry) object).getKey();

				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k)) {
						Object value = ((java.util.Map.Entry) object).getValue();
						V v = (V) this.array[this.offset + i + 1];

						if (value == v || value != null && value.equals(v))
							return true;

						break;
					}
				}
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object entry : collection) {
				if (entry instanceof java.util.Map.Entry) {
					Object key = ((java.util.Map.Entry) entry).getKey();

					for (int i = 0; i < this.length; i += 2) {
						K k = (K) this.array[this.offset + i];

						if (key == k || key != null && key.equals(k)) {
							Object value = ((java.util.Map.Entry) entry).getValue();
							V v = (V) this.array[this.offset + i + 1];

							if (value == v || value != null && value.equals(v))
								continue for0;

							break;
						}
					}
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Set) {
				if (object instanceof ObjectArray.EntrySet &&
					((EntrySet) object).array == this.array &&
					((EntrySet) object).offset == this.offset &&
					((EntrySet) object).length == this.length)
					return true;

				if (((java.util.Set) object).size() == this.length >>> 1) {
					for0:
					for (Object entry : (java.util.Set) object) {
						if (entry instanceof java.util.Map.Entry) {
							Object key = ((java.util.Map.Entry) entry).getKey();

							for (int i = 0; i < this.length; i += 2) {
								K k = (K) this.array[this.offset + i];

								if (key == k || key != null && key.equals(k)) {
									Object value = ((java.util.Map.Entry) entry).getValue();
									V v = (V) this.array[this.offset + i + 1];

									if (value == v || value != null && value.equals(v))
										continue for0;

									break;
								}
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
		public void forEach(Consumer<? super java.util.Map.Entry<K, V>> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = 0; i < this.length; i += 2)
				consumer.accept(new Entry(this.array, this.offset + i));
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];
				V v = (V) this.array[this.offset + i + 1];
				hashCode += (k == null ? 0 : k.hashCode()) ^
							(v == null ? 0 : v.hashCode());
			}

			return hashCode;
		}

		@Override
		public EntryIterator<E, K, V> iterator() {
			return new EntryIterator(this.array, this.offset, this.length);
		}

		@Override
		public Stream<java.util.Map.Entry<K, V>> parallelStream() {
			return StreamSupport.stream(new EntrySpliterator<>(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), true);
		}

		@Override
		public boolean remove(Object object) {
			if (object instanceof java.util.Map.Entry) {
				Object key = ((java.util.Map.Entry) object).getKey();

				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k)) {
						Object value = ((java.util.Map.Entry) object).getValue();
						V v = (V) this.array[this.offset + i + 1];

						if (value == v || value != null && value.equals(v))
							//can not remove
							throw new UnsupportedOperationException("remove");

						break;
					}
				}
			}

			//no match
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object element : collection)
				if (element instanceof java.util.Map.Entry) {
					Object key = ((java.util.Map.Entry) element).getKey();

					for (int i = 0; i < this.length; i += 2) {
						K k = (K) this.array[this.offset + i];

						if (key == k || key != null && key.equals(k)) {
							Object value = ((java.util.Map.Entry) element).getValue();
							V v = (V) this.array[this.offset + i + 1];

							if (value == v || value != null && value.equals(v))
								//can not remove
								throw new UnsupportedOperationException("remove");

							break;
						}
					}
				}

			//no match
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super java.util.Map.Entry<K, V>> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = 0; i < this.length; i += 2)
				if (predicate.test(new Entry(this.array, this.offset + i)))
					//can not remove
					throw new UnsupportedOperationException("remove");

			//no match
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				for (Object entry : collection)
					if (entry instanceof java.util.Map.Entry) {
						Object key = ((java.util.Map.Entry) entry).getKey();

						if (key == k || key != null && key.equals(k)) {
							V v = (V) this.array[this.offset + i + 1];
							Object value = ((java.util.Map.Entry) entry).getValue();

							if (value == v || value != null && value.equals(v))
								//retained
								continue for0;

							break;
						}
					}

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public EntrySpliterator<E, K, V> spliterator() {
			return new EntrySpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			);
		}

		@Override
		public Stream<java.util.Map.Entry<K, V>> stream() {
			return StreamSupport.stream(new EntrySpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), false);
		}

		@Override
		public Entry[] toArray() {
			int length = this.length >>> 1;
			Entry[] product = new Entry[length];

			for (int i = 0; i < length; i++)
				product[i] = new Entry(this.array, this.offset + (i << 1));

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = this.length >>> 1;

			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = 0; i < length; i++)
				product[i] = (T) new Entry(this.array, this.offset + (i << 1));

			return product;
		}

		@Override
		public String toString() {
			if (this.length == 0)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = 0;
			while (true) {
				builder.append(this.array[this.offset + i])
						.append("=")
						.append(this.array[this.offset + i + 1]);

				if ((i += 2) < this.length) {
					builder.append(", ");
					continue;
				}

				return builder.append("]")
						.toString();
			}
		}
	}

	public static final class EntrySpliterator<E, K extends E, V extends E> extends Array.EntrySpliterator<E[], E, K, V> {
		//todo

		public EntrySpliterator(E[] array, int characteristics) {

		}

		public EntrySpliterator(E[] array, int characteristics, int beginIndex, int endIndex) {
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class Iterator<E> extends Array.Iterator<E[], E> {
		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			int index = this.index;
			this.index = this.length;

			for (int i = this.offset + index, l = this.offset + this.length; i < l; i++)
				consumer.accept(this.array[i]);
		}

		@Override
		public E next() {
			int i = this.index++;

			if (i < this.length)
				return this.array[this.offset + i];

			throw new NoSuchElementException();
		}
	}

	/**
	 * Get an iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the key.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public static final class KeyIterator<E, K extends E> extends Array.KeyIterator<E[], K> {
		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public KeyIterator(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator multiplied by 2.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public KeyIterator(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super K> consumer) {
			int index = this.index;
			this.index = this.length;

			for (int i = this.offset + index, l = this.offset + this.length; i < l; i += 2)
				consumer.accept((K) this.array[i]);
		}

		@Override
		public K next() {
			int i = this.index;
			this.index += 2;

			if (i < this.length)
				return (K) this.array[this.offset + i];

			throw new NoSuchElementException();
		}
	}

	/**
	 * A set backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public static final class KeySet<E, K extends E> extends Array.KeySet<E[], K> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 8448014139549718355L;

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed set.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public KeySet(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array  the array backing the constructed set.
		 * @param offset the offset where the area backing the constructed set in the given {@code array} is starting.
		 * @param length the length of the constructed set multiplied by 2.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		public KeySet(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public boolean add(K key) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k))
					//already exists
					return false;
			}

			//can not add
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends K> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (K key : collection) {
				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k))
						//already exists
						continue for0;
				}

				//can not add
				throw new UnsupportedOperationException("add");
			}

			//all already exists
			return false;
		}

		@Override
		public boolean contains(Object object) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (object == k || object != null && object.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object key : collection) {
				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Set) {
				if (object instanceof KeySet &&
					((KeySet) object).array == this.array &&
					((KeySet) object).offset == this.offset &&
					((KeySet) object).length == this.length)
					return true;

				if (((java.util.Set) object).size() == this.length >> 1) {
					for0:
					for (Object key : (java.util.Set) object) {
						for (int i = 0; i < this.length; i += 2) {
							K k = (K) this.array[this.offset + i];

							if (key == k || key != null && key.equals(k))
								continue for0;
						}

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

			for (int i = 0; i < this.length; i += 2)
				consumer.accept((K) this.array[this.offset + i]);
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];
				hashCode += k == null ? 0 : k.hashCode();
			}

			return hashCode;
		}

		@Override
		public KeyIterator<E, K> iterator() {
			return new KeyIterator(this.array, this.offset, this.length);
		}

		@Override
		public Stream<K> parallelStream() {
			return StreamSupport.stream(new KeySpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), true);
		}

		@Override
		public boolean remove(Object object) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (object == k || object != null && object.equals(k))
					//can not remove
					throw new UnsupportedOperationException("remove");
			}

			//no match
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object key : collection)
				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k))
						//can not remove
						throw new UnsupportedOperationException("remove");
				}

			//no match
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super K> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = 0; i < this.length; i += 2)
				if (predicate.test((K) this.array[this.offset + i]))
					//can not remove
					throw new UnsupportedOperationException("remove");

			//no match
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				for (Object key : collection)
					if (key == k || key != null && key.equals(k))
						//retained
						continue for0;

				//can not remove
				throw new UnsupportedOperationException("remove");
			}

			//all retained
			return false;
		}

		@Override
		public KeySpliterator<E, K> spliterator() {
			return new KeySpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			);
		}

		@Override
		public Stream<K> stream() {
			return StreamSupport.stream(new EntrySpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), false);
		}

		@Override
		public Object[] toArray() {
			int length = this.length >>> 1;
			Object[] product = new Object[length];

			for (int i = 0; i < length; i++)
				product[i] = this.array[this.offset + (i << 1)];

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = this.length >>> 1;

			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = 0; i < length; i++)
				product[i] = (T) this.array[this.offset + (i << 1)];

			return product;
		}

		@Override
		public String toString() {
			if (this.length == 0)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = 0;
			while (true) {
				builder.append(this.array[this.offset + i]);

				if ((i += 2) < this.length) {
					builder.append(", ");
					continue;
				}

				return builder.append("]")
						.toString();
			}
		}
	}

	public static final class KeySpliterator<E, K extends E> extends Array.KeySpliterator<E[], K> {
		public KeySpliterator(E[] array, int characteristics) {

		}

		public KeySpliterator(E[] array, int characteristics, int beginIndex, int endIndex) {

		}
	}

	/**
	 * A list backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class List<E> extends Array.List<E[], E> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -4250091141258397846L;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed list.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public List(E... array) {
			super(
					array, 0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param index the index where the area backing the constructed list in the given {@code array} is starting.
		 * @param array the array backing the constructed list.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index > array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public List(int index, E... array) {
			super(
					array, index,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param index  the index where the area backing the constructed list in the given {@code array} is starting.
		 * @param length the length of the constructed list.
		 * @param array  the array backing the constructed list.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code length < 0} or {@code index + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public List(int index, int length, E... array) {
			super(
					Objects.requireNonNull(array, "array"), index,
					length,
					array.length
			);
		}

		@Override
		public boolean contains(Object object) {
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.offset + i];

				if (object == o || object != null && object.equals(o))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object object : collection) {
				for (int i = 0; i < this.length; i++) {
					Object o = this.array[this.offset + i];

					if (object == o || object != null && object.equals(o))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object instanceof java.util.List) {
				if (object instanceof List &&
					((List) object).array == this.array &&
					((List) object).offset == this.offset &&
					((List) object).length == this.length)
					return true;

				java.util.Iterator iterator = ((Iterable) object).iterator();

				int i = 0;
				while (iterator.hasNext()) {
					if (i < this.length) {
						Object o = iterator.next();
						Object t = this.array[this.offset + i++];

						if (o == t || o != null && o.equals(t))
							continue;
					}

					return false;
				}

				return i == this.length;
			}

			return false;
		}

		@Override
		public void forEach(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			for (int i = 0; i < this.length; i++)
				consumer.accept(this.array[this.offset + i]);
		}

		@Override
		public E get(int index) {
			Objects.require(index, Objects::nonNegative, "index");
			Objects.require(index, this.length, Objects::isLess, "index");
			return this.array[this.offset + index];
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = 0; i < this.length; i++) {
				Object element = this.array[this.offset + i];

				hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
			}

			return hashCode;
		}

		@Override
		public int indexOf(Object object) {
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.offset + i];

				if (object == o || object != null && object.equals(o))
					return i;
			}

			return -1;
		}

		@Override
		public Iterator<E> iterator() {
			return new Iterator(
					this.array,
					this.offset,
					this.length
			);
		}

		@Override
		public int lastIndexOf(Object object) {
			for (int i = this.length - 1; i >= 0; i--) {
				Object o = this.array[this.offset + i];

				if (object == o || object != null && object.equals(o))
					return i;
			}

			return -1;
		}

		@Override
		public ListIterator<E> listIterator() {
			return new ListIterator(
					this.array,
					this.offset,
					this.length
			);
		}

		@Override
		public ListIterator<E> listIterator(int index) {
			return new ListIterator(
					this.array,
					this.offset + index,
					this.length - index
			);
		}

		@Override
		public Stream<E> parallelStream() {
			return StreamSupport.stream(new ObjectArray.Spliterator(
					this.array,
					java.util.Spliterator.ORDERED | java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), true);
		}

		@Override
		public boolean remove(Object object) {
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.offset + i];

				if (object == o || object != null && object.equals(o))
					throw new UnsupportedOperationException("remove");
			}

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			for (Object object : collection)
				for (int i = 0; i < this.length; i++) {
					Object o = this.array[this.offset + i];

					if (object == o || object != null && object.equals(o))
						throw new UnsupportedOperationException("removeAll");
				}

			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super E> predicate) {
			Objects.requireNonNull(predicate, "predicate");
			for (int i = 0; i < this.length; i++)
				if (predicate.test(this.array[this.offset + i]))
					throw new UnsupportedOperationException("removeIf");

			return false;
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator, "operator");
			for (int i = 0; i < this.length; i++)
				this.array[this.offset + i] = operator.apply(this.array[this.offset + i]);
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");
			for0:
			for (int i = 0; i < this.length; i++) {
				Object o = this.array[this.offset + i];

				for (Object object : collection)
					if (object == o || object != null && object.equals(o))
						continue for0;

				throw new UnsupportedOperationException("retainAll");
			}

			return false;
		}

		@Override
		public E set(int index, E element) {
			Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
			Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
			E old = this.array[this.offset + index];
			this.array[this.offset + index] = element;
			return old;
		}

		@Override
		public void sort(Comparator<? super E> comparator) {
			java.util.Arrays.sort(this.array, this.offset, this.offset + this.length, comparator);
		}

		@Override
		public Spliterator<E> spliterator() {
			return new Spliterator(
					this.array,
					java.util.Spliterator.ORDERED | java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			);
		}

		@Override
		public Stream<E> stream() {
			return StreamSupport.stream(new Spliterator(
					this.array,
					java.util.Spliterator.ORDERED | java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), false);
		}

		@Override
		public List<E> subList(int beginIndex, int endIndex) {
			Objects.require(beginIndex, Objects::nonNegative, IndexOutOfBoundsException.class, "beginIndex");
			Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
			Objects.require(endIndex, this.length, Objects::nonGreater, IndexOutOfBoundsException.class, "endIndex");
			return new List(this.offset + beginIndex, this.offset + endIndex, this.array);
		}

		@Override
		public Object[] toArray() {
			Object[] product = new Object[this.length];
			System.arraycopy(this.array, this.offset, product, 0, this.length);
			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");

			T[] product = array;

			if (array.length < this.length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), this.length);
			else
				product[this.length] = null;

			System.arraycopy(this.array, this.offset, product, 0, this.length);
			return array;
		}

		@Override
		public String toString() {
			if (this.length == 0)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = 0;
			while (true) {
				builder.append(this.array[this.offset + i]);

				if (++i < this.length) {
					builder.append(", ");
					continue;
				}

				return builder.append("]")
						.toString();
			}
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class ListIterator<E> extends Array.ListIterator<E[], E> {
		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public ListIterator(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array  the array backing the constructed iterator.
		 * @param offset the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code cursor < 0} or {@code
		 *                                        offset + length > array.length} or {@code cursor > length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public ListIterator(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			int cursor = this.index;
			this.index = this.length;
			this.last = this.length - 1;

			for (int i = this.offset + cursor, l = this.offset + this.length; i < l; i++)
				consumer.accept(this.array[i]);
		}

		@Override
		public E next() {
			int i = this.index++;

			if (i < this.length)
				return this.array[this.offset + (this.last = i)];

			throw new NoSuchElementException();
		}

		@Override
		public E previous() {
			int i = --this.index;
			if (i >= 0)
				return this.array[this.offset + (this.last = i)];

			throw new NoSuchElementException();
		}

		@Override
		public void set(E element) {
			int i = this.last;
			if (i < 0 || i >= this.length)
				throw new IllegalStateException();

			this.array[this.offset + i] = element;
		}
	}

	/**
	 * A map backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public static final class Map<E, K extends E, V extends E> extends Array.Map<E[], E, K, V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 416184197704561956L;

		/**
		 * Construct a new map backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed map.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public Map(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new map backed by the given {@code array}.
		 *
		 * @param array  the array backing the constructed map.
		 * @param offset the offset where the area backing the constructed map in the given {@code array} is starting.
		 * @param length the length of the constructed map multiplied by 2.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		public Map(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];
					V value = function.apply(k, v);

					if (value == null && v != null)
						//old:notnull new:null
						throw new UnsupportedOperationException("remove");

					//old:found
					this.array[this.offset + i + 1] = value;
					return value;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];

					if (v == null) {
						//old:null
						V value = function.apply(k);
						this.array[this.offset + i + 1] = value;
						return value;
					}

					//old:notnull
					return null;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];

					if (v != null) {
						V value = function.apply(k, v);

						if (value == null)
							//old:notnull new:null
							throw new UnsupportedOperationException("remove");

						//old:notnull new:notnull
						this.array[this.offset + i + 1] = value;
						return value;
					}

					//old:null
					return null;
				}
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean containsKey(Object key) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			for (int i = 0; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i + 1];

				if (value == v || value != null && value.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public EntrySet<E, K, V> entrySet() {
			return new EntrySet(this.array, this.offset, this.length);
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (object instanceof java.util.Map) {
				if (object instanceof Map &&
					((Map) object).array == this.array &&
					((Map) object).offset == this.offset &&
					((Map) object).length == this.length)
					return true;

				if (((java.util.Map) object).size() == this.length >>> 1) {
					for0:
					for (Entry entry : ((java.util.Map<?, ?>) object).entrySet()) {
						Object key = entry.getKey();

						for (int i = 0; i < this.length; i += 2) {
							K k = (K) this.array[this.offset + i];

							if (key == k || key != null && key.equals(k)) {
								Object value = entry.getValue();
								V v = (V) this.array[this.offset + i + 1];

								if (value == v || value != null && value.equals(v))
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
		public void forEach(BiConsumer<? super K, ? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];
				V v = (V) this.array[this.offset + i + 1];
				consumer.accept(k, v);
			}
		}

		@Override
		public V get(Object key) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k))
					return (V) this.array[this.offset + i + 1];
			}

			return null;
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k))
					return (V) this.array[this.offset + i + 1];
			}

			return defaultValue;
		}

		@Override
		public int hashCode() {
			int hashCode = 1;

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];
				V v = (V) this.array[this.offset + i + 1];
				hashCode += (k == null ? 0 : k.hashCode()) ^
							(v == null ? 0 : v.hashCode());
			}

			return hashCode;
		}

		@Override
		public KeySet<E, K> keySet() {
			return new KeySet(this.array, this.offset, this.length);
		}

		@Override
		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];
					V newValue = v == null ? value : function.apply(v, value);

					if (newValue == null)
						//old:found new:null
						throw new UnsupportedOperationException("remove");

					//old:found new:notnull
					this.array[this.offset + i + 1] = newValue;
					return newValue;
				}
			}

			if (value == null)
				//old:notfound new:null
				return null;

			//old:notfound new:notnull
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V put(K key, V value) {
			for (int i = 0; i < this.array.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					//old:found
					V v = (V) this.array[i + 1];
					this.array[i + 1] = value;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public void putAll(java.util.Map<? extends K, ? extends V> map) {
			Objects.requireNonNull(map, "map");

			for (java.util.Map.Entry entry : map.entrySet()) {
				Object key = entry.getKey();

				for (int i = 0; i < this.length; i += 2) {
					K k = (K) this.array[this.offset + i];

					if (key == k || key != null && key.equals(k)) {
						this.array[this.offset + i + 1] = (V) entry.getValue();
						break;
					}
				}

				//old:notfound
				throw new UnsupportedOperationException("put");
			}
		}

		@Override
		public V putIfAbsent(K key, V value) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					//old:found
					V v = (V) this.array[this.offset + i + 1];
					this.array[this.offset + i + 1] = v == null ? value : v;
					return v;
				}
			}

			//old:notfound
			throw new UnsupportedOperationException("put");
		}

		@Override
		public V remove(Object key) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k))
					//old:found
					throw new UnsupportedOperationException("remove");
			}

			//old:notfound
			return null;
		}

		@Override
		public boolean remove(Object key, Object value) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];

					if (value == v || value != null && value.equals(v))
						//old:match
						throw new UnsupportedOperationException("remove");

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public boolean replace(K key, V oldValue, V newValue) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					V v = (V) this.array[this.offset + i + 1];

					if (oldValue == v || oldValue != null && oldValue.equals(v)) {
						//old:match
						this.array[this.offset + i + 1] = newValue;
						return true;
					}

					break;
				}
			}

			//old:nomatch
			return false;
		}

		@Override
		public V replace(K key, V value) {
			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];

				if (key == k || key != null && key.equals(k)) {
					//old:match
					V v = (V) this.array[this.offset + i + 1];
					this.array[this.offset + i + 1] = value;
					return v;
				}
			}

			//old:nomatch
			return null;
		}

		@Override
		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			Objects.requireNonNull(function, "function");

			for (int i = 0; i < this.length; i += 2) {
				K k = (K) this.array[this.offset + i];
				V v = (V) this.array[this.offset + i + 1];

				this.array[this.offset + i + 1] = function.apply(k, v);
			}
		}

		@Override
		public String toString() {
			if (this.length == 0)
				return "{}";

			StringBuilder builder = new StringBuilder("{");
			int i = 0;
			while (true) {
				builder.append(this.array[this.offset + i])
						.append("=")
						.append(this.array[this.offset + i + 1]);

				if ((i += 2) < this.length) {
					builder.append(", ");
					continue;
				}

				return builder.append("}")
						.toString();
			}
		}

		@Override
		public Values<E, V> values() {
			return new Values(this.array, this.offset, this.length);
		}
	}

	public static final class Set<E> extends Array.Set<E[], E> {

	}

	/**
	 * A spliterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public static final class Spliterator<E> extends Array.Spliterator<E[], E> {
		/**
		 * Construct a new spliterator backed by the given {@code array}.
		 *
		 * @param array           the array backing the constructed spliterator.
		 * @param characteristics the characteristics of the constructed spliterator. ({@link java.util.Spliterator#SIZED} and
		 *                        {@link java.util.Spliterator#SUBSIZED} is set by default)
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Spliterator(E[] array, int characteristics) {
			super(
					array,
					characteristics,
					0,
					Objects.requireNonNull(array, "array").length
			);
		}

		/**
		 * Construct a new spliterator backed by the given {@code array}.
		 *
		 * @param array           the array backing the constructed spliterator.
		 * @param characteristics the characteristics of the constructed spliterator. ({@link java.util.Spliterator#SIZED} and
		 *                        {@link java.util.Spliterator#SUBSIZED} is set by default)
		 * @param beginIndex      the beginIndex where the area backing the constructed spliterator in the given {@code array} is
		 *                        starting.
		 * @param endIndex        the beginIndex where the area backing the constructed spliterator in the given {@code array} is
		 *                        ending.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Spliterator(E[] array, int characteristics, int beginIndex, int endIndex) {
			super(
					array,
					characteristics,
					beginIndex,
					endIndex
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.beginIndex;

			//as specified in the java standard array spliterator
			//don't perform if the 'end' parameter is out of bounds
			if (this.array.length >= this.endIndex &&
				index >= 0 && index < this.endIndex) {
				//as specified in the java standard array spliterator
				//update the index before performing
				this.beginIndex = this.endIndex;

				while (index < this.endIndex)
					consumer.accept(this.array[index++]);
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> consumer) {
			Objects.requireNonNull(consumer, "consumer");
			int index = this.beginIndex;

			//as specified in the java standard spliterator
			//don't check if the index is within the actual bounds of the array
			if (index >= 0 && index < this.endIndex) {
				this.beginIndex++;
				consumer.accept(this.array[index]);
				return true;
			}

			return false;
		}

		@Override
		public Spliterator<E> trySplit() {
			int index = this.beginIndex;
			int halfEnd = index + this.endIndex >>> 1;

			//as specified in the java standard spliterator
			//split only if the half length is positive
			if (index >= halfEnd) {
				this.beginIndex = halfEnd;
				return new Spliterator(
						this.array,
						this.characteristics,
						index,
						halfEnd
				);
			}

			return null;
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public static final class ValueIterator<E, V extends E> extends Array.ValueIterator<E[], V> {
		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public ValueIterator(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator multiplied by 2.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public ValueIterator(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public void forEachRemaining(Consumer<? super V> consumer) {
			int index = this.index;
			this.index = this.length;

			for (int i = this.offset + index + 1, l = this.offset + this.length; i < l; i += 2)
				consumer.accept((V) this.array[i]);
		}

		@Override
		public V next() {
			int i = this.index;
			this.index += 2;

			if (i < this.length)
				return (V) this.array[this.offset + i + 1];

			throw new NoSuchElementException();
		}
	}

	public static final class ValueSpliterator<E, V extends E> extends Array.ValueSpliterator<E[], V> {

		public ValueSpliterator(E[] array, int characteristics) {

		}

		public ValueSpliterator(E[] array, int characteristics, int beginIndex, int endIndex) {

		}
	}

	/**
	 * A collection backed by an array.
	 *
	 * @param <E> the type of the elements.
	 * @param <V> the type of the values.
	 */
	@SuppressWarnings("EqualsAndHashcode")
	public static final class Values<E, V extends E> extends Array.Values<E[], V> {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -6056470846170669797L;

		/**
		 * Construct a new collection backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed collection.
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array.length} is not even.
		 * @since 0.1.5
		 */
		public Values(E... array) {
			super(
					array,
					0,
					Objects.requireNonNull(array, "array").length,
					array.length
			);
		}

		/**
		 * Construct a new collection backed by the given {@code array}.
		 *
		 * @param array  the array backing the constructed collection.
		 * @param offset the offset where the area backing the constructed collection in the given {@code array} is starting.
		 * @param length the length of the constructed collection multiplied by 2.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        array.length}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		public Values(E[] array, int offset, int length) {
			super(
					array,
					offset,
					length,
					Objects.requireNonNull(array, "array").length
			);
		}

		@Override
		public boolean contains(Object object) {
			for (int i = 1; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i];

				if (object == v || object != null && object.equals(v))
					return true;
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (Object value : collection) {
				for (int i = 1; i < this.length; i += 2) {
					V v = (V) this.array[this.offset + i];

					if (value == v || value != null && value.equals(v))
						continue for0;
				}

				return false;
			}

			return true;
		}

		@Override
		public void forEach(Consumer<? super V> consumer) {
			Objects.requireNonNull(consumer, "consumer");

			for (int i = 1; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i];
				consumer.accept(v);
			}
		}

		@Override
		public int hashCode() {
			int hashCode = 0;

			for (int i = 1; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i];
				hashCode += v == null ? 0 : v.hashCode();
			}

			return hashCode;
		}

		@Override
		public ValueIterator<E, V> iterator() {
			return new ValueIterator(this.array, this.offset, this.length);
		}

		@Override
		public Stream<V> parallelStream() {
			return StreamSupport.stream(new ValueSpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), true);
		}

		@Override
		public boolean remove(Object object) {
			for (int i = 1; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i];

				if (object == v || object != null && object.equals(v))
					throw new UnsupportedOperationException("remove");
			}

			return false;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for (Object value : collection)
				for (int i = 1; i < this.length; i += 2) {
					V v = (V) this.array[this.offset + i];

					if (value == v || value != null && value.equals(v))
						throw new UnsupportedOperationException("remove");
				}

			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super V> predicate) {
			Objects.requireNonNull(predicate, "predicate");

			for (int i = 1; i < this.length; i += 2)
				if (predicate.test((V) this.array[this.offset + i]))
					throw new UnsupportedOperationException("remove");

			return false;
		}

		@Override
		public boolean retainAll(Collection<?> collection) {
			Objects.requireNonNull(collection, "collection");

			for0:
			for (int i = 1; i < this.length; i += 2) {
				V v = (V) this.array[this.offset + i];

				for (Object value : collection)
					if (value == v || value != null && value.equals(v))
						continue for0;

				throw new UnsupportedOperationException("remove");
			}

			return false;
		}

		@Override
		public ValueSpliterator<E, V> spliterator() {
			return new ValueSpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			);
		}

		@Override
		public Stream<V> stream() {
			return StreamSupport.stream(new ValueSpliterator(
					this.array,
					java.util.Spliterator.IMMUTABLE,
					this.offset,
					this.offset + this.length
			), false);
		}

		@Override
		public Object[] toArray() {
			int length = this.length >>> 1;
			Object[] product = new Object[length];

			for (int i = 0; i < length; i++)
				product[i] = this.array[this.offset + (i << 1) + 1];

			return product;
		}

		@Override
		public <T> T[] toArray(T[] array) {
			Objects.requireNonNull(array, "array");
			int length = this.length >>> 1;

			T[] product = array;

			if (array.length < length)
				product = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), length);
			else
				product[length] = null;

			for (int i = 0; i < length; i++)
				product[i] = (T) this.array[this.offset + (i << 1) + 1];

			return product;
		}

		@Override
		public String toString() {
			if (this.length == 0)
				return "[]";

			StringBuilder builder = new StringBuilder("[");

			int i = 1;
			while (true) {
				builder.append(this.array[this.offset + i]);

				if ((i += 2) < this.length) {
					builder.append(", ");
					continue;
				}

				return builder.append("]")
						.toString();
			}
		}
	}
}
