package cufy.util.array;

import cufy.util.Objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.RandomAccess;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * An array holder.
 *
 * @param <A> the type of the array.
 * @param <E> the type of the elements.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.08.03
 */
public abstract class Array<A, E> implements Iterable<E> {
	/**
	 * The array held by this.
	 */
	protected final A array;

	/**
	 * Construct a new array holder for the given {@code array}.
	 *
	 * @param array the array to be held by the constructed array holder.
	 * @throws NullPointerException if the given {@code array} is null.
	 */
	protected Array(A array) {
		Objects.requireNonNull(array, "array");
		this.array = array;
	}

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract void forEach(Consumer<? super E> consumer);

	@Override
	public abstract int hashCode();

	@Override
	public abstract Iterator<A, E> iterator();

	@Override
	public abstract Spliterator<A, E> spliterator();

	@Override
	public abstract String toString();

	/**
	 * Get a list backed by the array.
	 *
	 * @return a list backed by the array.
	 */
	public abstract List<A, E> asList();

	/**
	 * Get a map backed by the array.
	 *
	 * @return a map backed by the array.
	 */
	public abstract Map<A, E, ?, ?> asMap();

	/**
	 * Get a set backed by the array.
	 *
	 * @return a set backed by the array.
	 */
	public abstract Set<A, E> asSet();

	/**
	 * Get the element stored in the array at the given {@code index}.
	 *
	 * @param index the index where the targeted element is stored at.
	 * @return the element stored in teh array at the given {@code index}.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @since 0.1.5
	 */
	public abstract E get(int index);

	/**
	 * Get a list iterator backed by the array.
	 *
	 * @return a list iterator backed by the array.
	 * @since 0.1.5
	 */
	public abstract ListIterator<A, E> listIterator();

	/**
	 * Set the the given {@code element} to the array at the given {@code index}.
	 *
	 * @param index   the index where to set the given {@code element} at the array.
	 * @param element the element to be set.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index >= array.length}.
	 * @throws ArrayStoreException            if the given {@code element} can not be stored to the array.
	 * @since 0.1.5
	 */
	public abstract void set(int index, E element);

	/**
	 * An entry backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class Entry<A, E, K extends E, V extends E> implements java.util.Map.Entry<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -1793396182662638233L;

		/**
		 * The array backing this entry.
		 *
		 * @since 0.1.5
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The index of the key of this entry in the backing array.
		 *
		 * @since 0.1.5
		 */
		protected final int index;

		/**
		 * Construct a new entry backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed entry.
		 * @param index       the index of the key of the constructed entry.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index + 1 >= arrayLength}.
		 * @since 0.1.5
		 */
		protected Entry(A array, int index, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index + 1, arrayLength, Objects::isLess, ArrayIndexOutOfBoundsException.class, "index + 1");
			this.index = index;
			this.array = array;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object o);

		@Override
		public abstract K getKey();

		@Override
		public abstract V getValue();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract V setValue(V value);

		@Override
		public abstract String toString();
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class EntryIterator<A, E, K extends E, V extends E> implements java.util.Iterator<java.util.Map.Entry<K, V>> {
		/**
		 * The array backing this iterator.
		 */
		protected final A array;
		/**
		 * The length of this iterator multiplied by 2.
		 */
		protected final int length;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		protected final int offset;
		/**
		 * The next index.
		 */
		protected int index;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset      the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length      the length of the constructed iterator multiplied by 2.
		 * @param array       the array backing the constructed iterator.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected EntryIterator(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::isEven, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> consumer);

		@SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException", "AbstractMethodOverridesAbstractMethod"})
		@Override
		public abstract java.util.Map.Entry<K, V> next();
	}

	/**
	 * A set backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class EntrySet<A, E, K extends E, V extends E> implements java.util.Set<java.util.Map.Entry<K, V>>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -7515045887948351373L;

		/**
		 * The backing array.
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The length of this set multiplied by 2.
		 */
		protected final int length;
		/**
		 * The index where the area backing this set in the {@code array} is starting.
		 */
		protected final int offset;

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed set.
		 * @param offset      the offset where the area backing the constructed set in the given {@code array} is starting.
		 * @param length      the length of the constructed set multiplied by 2.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected EntrySet(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::isEven, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void clear() {
			if (this.length != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return this.length == 0;
		}

		@Override
		public int size() {
			return this.length >>> 1;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean add(java.util.Map.Entry<K, V> entry);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super java.util.Map.Entry<K, V>> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract EntryIterator<A, E, K, V> iterator();

		@Override
		public abstract Stream<java.util.Map.Entry<K, V>> parallelStream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super java.util.Map.Entry<K, V>> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract EntrySpliterator<A, E, K, V> spliterator();

		@Override
		public abstract Stream<java.util.Map.Entry<K, V>> stream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	public abstract static class EntrySpliterator<A, E, K extends E, V extends E> implements java.util.Spliterator<java.util.Map.Entry<K, V>> {

		@Override
		public int characteristics() {
			return 0;
		}

		@Override
		public long estimateSize() {
			return 0;
		}

		@Override
		public void forEachRemaining(Consumer<? super java.util.Map.Entry<K, V>> action) {

		}

		@Override
		public Comparator<? super java.util.Map.Entry<K, V>> getComparator() {
			return null;
		}

		@Override
		public long getExactSizeIfKnown() {
			return 0;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super java.util.Map.Entry<K, V>> action) {
			return false;
		}

		@Override
		public java.util.Spliterator<java.util.Map.Entry<K, V>> trySplit() {
			return null;
		}
	}

	/**
	 * Am iterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract static class Iterator<A, E> implements java.util.Iterator<E> {
		/**
		 * The array backing this iterator.
		 */
		protected final A array;
		/**
		 * The length of this iterator.
		 */
		protected final int length;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		protected final int offset;
		/**
		 * The next index.
		 */
		protected int index;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset      the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length      the length of the constructed iterator.
		 * @param array       the array backing the constructed iterator.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected Iterator(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract E next();
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class KeyIterator<A, K> implements java.util.Iterator<K> {
		/**
		 * The array backing this iterator.
		 */
		protected final A array;
		/**
		 * The length of this iterator multiplied by 2.
		 */
		protected final int length;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		protected final int offset;
		/**
		 * The next index.
		 */
		protected int index;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset      the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length      the length of the constructed iterator multiplied by 2.
		 * @param array       the array backing the constructed iterator.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected KeyIterator(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::isEven, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super K> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract K next();
	}

	/**
	 * A set backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <K> the type of the keys.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class KeySet<A, K> implements java.util.Set<K>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 4627018232494058734L;

		/**
		 * The array backing this set.
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The length of this set multiplied by 2.
		 */
		protected final int length;
		/**
		 * The offset where the area backing this set in the given {@code array} is starting.
		 */
		protected final int offset;

		/**
		 * Construct a new set backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed set.
		 * @param offset      the offset where the area backing the constructed set in the given {@code array} is starting.
		 * @param length      the length of the constructed set multiplied by 2.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		protected KeySet(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			Objects.require(length, Objects::isEven, "length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void clear() {
			if (this.length != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return this.length == 0;
		}

		@Override
		public int size() {
			return this.length >>> 1;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean add(K key);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean addAll(Collection<? extends K> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super K> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract KeyIterator<A, K> iterator();

		@Override
		public abstract Stream<K> parallelStream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super K> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract KeySpliterator<A, K> spliterator();

		@Override
		public abstract Stream<K> stream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	public abstract static class KeySpliterator<A, K> implements java.util.Spliterator<K> {
		//todo
	}

	/**
	 * A list backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract static class List<A, E> implements java.util.List<E>, Serializable, RandomAccess {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5890878610114060287L;

		/**
		 * The backing array.
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The length of this list.
		 */
		protected final int length;
		/**
		 * The index where the area backing this list in the {@code array} is starting.
		 */
		protected final int offset;

		/**
		 * Construct a new list backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed list.
		 * @param offset      the offset where the area backing the constructed list in the given {@code array} is starting.
		 * @param length      the length of the constructed list.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected List(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public void add(int index, E element) {
			Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
			Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			if (collection.isEmpty())
				return false;

			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> collection) {
			Objects.requireNonNull(collection, "collection");
			Objects.require(index, Objects::nonNegative, "index");
			Objects.require(index, this.length, Objects::isLess, "index");
			if (collection.isEmpty())
				return false;

			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			if (this.length != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return this.length == 0;
		}

		@Override
		public E remove(int index) {
			Objects.require(index, Objects::nonNegative, "index");
			Objects.require(index, this.length, Objects::isLess, "index");
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public int size() {
			return this.length;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(Consumer<? super E> consumer);

		@Override
		public abstract E get(int index);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int indexOf(Object object);

		@Override
		public abstract Iterator<A, E> iterator();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int lastIndexOf(Object object);

		@Override
		public abstract ListIterator<A, E> listIterator();

		@Override
		public abstract ListIterator<A, E> listIterator(int index);

		@Override
		public abstract Stream<E> parallelStream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super E> predicate);

		@Override
		public abstract void replaceAll(UnaryOperator<E> operator);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract E set(int index, E element);

		@Override
		public abstract void sort(Comparator<? super E> comparator);

		@Override
		public abstract java.util.Spliterator<E> spliterator();

		@Override
		public abstract Stream<E> stream();

		@Override
		public abstract List<A, E> subList(int beginIndex, int endIndex);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public abstract static class ListIterator<A, E> implements java.util.ListIterator<E> {
		/**
		 * The backing array.
		 */
		protected final A array;
		/**
		 * The length of this iterator.
		 */
		protected final int length;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		protected final int offset;
		/**
		 * The next index.
		 */
		protected int index;
		/**
		 * The last index.
		 */
		protected int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed iterator.
		 * @param offset      the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length      the length of the constructed iterator.
		 * @param arrayLength the length of the given array.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code cursor < 0} or {@code
		 *                                        offset + length > arrayLength} or {@code cursor > length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected ListIterator(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.index > 0;
		}

		@Override
		public int nextIndex() {
			return this.index;
		}

		@Override
		public int previousIndex() {
			return this.index - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract E next();

		@Override
		public abstract E previous();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract void set(E element);
	}

	/**
	 * A map backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @param <K> the type of the keys.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class Map<A, E, K extends E, V extends E> implements java.util.Map<K, V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7692195336903798598L;

		/**
		 * The array backing this map.
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The length of this map multiplied by 2.
		 */
		protected final int length;
		/**
		 * The offset where the area backing this map in the given {@code array} is starting.
		 */
		protected final int offset;

		/**
		 * Construct a new map backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed map.
		 * @param offset      the offset where the area backing the constructed map in the given {@code array} is starting.
		 * @param length      the length of the constructed map multiplied by 2.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		protected Map(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			Objects.require(length, Objects::isEven, "length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void clear() {
			if (this.length != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean isEmpty() {
			return this.length == 0;
		}

		@Override
		public int size() {
			return this.length >>> 1;
		}

		@Override
		public abstract V compute(K key, BiFunction<? super K, ? super V, ? extends V> function);

		@Override
		public abstract V computeIfAbsent(K key, Function<? super K, ? extends V> function);

		@Override
		public abstract V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsKey(Object key);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsValue(Object value);

		@Override
		public abstract EntrySet<A, E, K, V> entrySet();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean equals(Object object);

		@Override
		public abstract void forEach(BiConsumer<? super K, ? super V> consumer);

		@Override
		public abstract V get(Object key);

		@Override
		public abstract V getOrDefault(Object key, V defaultValue);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract KeySet<A, K> keySet();

		@Override
		public abstract V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function);

		@Override
		public abstract V put(K key, V value);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract void putAll(java.util.Map<? extends K, ? extends V> map);

		@Override
		public abstract V putIfAbsent(K key, V value);

		@Override
		public abstract V remove(Object key);

		@Override
		public abstract boolean remove(Object key, Object value);

		@Override
		public abstract boolean replace(K key, V oldValue, V newValue);

		@Override
		public abstract V replace(K key, V value);

		@Override
		public abstract void replaceAll(BiFunction<? super K, ? super V, ? extends V> function);

		@Override
		public abstract String toString();

		@Override
		public abstract Values<A, V> values();
	}

	/**
	 * @param <A>
	 * @param <E>
	 */
	public abstract static class Set<A, E> implements java.util.Set<E>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2092169091443806884L;

		@Override
		public boolean add(E e) {
			return false;
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			return false;
		}

		@Override
		public void clear() {

		}

		@Override
		public boolean contains(Object o) {
			return false;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public void forEach(Consumer<? super E> action) {

		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public java.util.Iterator<E> iterator() {
			return null;
		}

		@Override
		public Stream<E> parallelStream() {
			return null;
		}

		@Override
		public boolean remove(Object o) {
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return false;
		}

		@Override
		public boolean removeIf(Predicate<? super E> filter) {
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return false;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public java.util.Spliterator<E> spliterator() {
			return null;
		}

		@Override
		public Stream<E> stream() {
			return null;
		}

		@Override
		public Object[] toArray() {
			return new Object[0];
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return null;
		}

		@Override
		public String toString() {
			return super.toString();
		}
		//TODO
	}

	/**
	 * A spliterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <E> the type of the elements.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.02
	 */
	public abstract static class Spliterator<A, E> implements java.util.Spliterator<E> {
		/**
		 * The array backing this spliterator.
		 */
		protected final A array;
		/**
		 * The characteristics of this spliterator.
		 */
		protected final int characteristics;
		/**
		 * The index where the area backing this spliterator in the given {@code array} is ending.
		 */
		protected final int endIndex;
		/**
		 * The next index.
		 */
		protected int beginIndex;

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
		protected Spliterator(A array, int characteristics, int beginIndex, int endIndex) {
			Objects.requireNonNull(array, "array");
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
			this.characteristics = characteristics |
								   java.util.Spliterator.SIZED |
								   java.util.Spliterator.SUBSIZED;
			this.array = array;
		}

		@Override
		public int characteristics() {
			return this.characteristics;
		}

		@Override
		public long estimateSize() {
			//as specified in the java standard array spliterator
			//estimated size is the size expected from the parameters
			return this.endIndex - this.beginIndex;
		}

		@Override
		public Comparator<? super E> getComparator() {
			if (this.hasCharacteristics(java.util.Spliterator.SORTED))
				//as specified in the java standard spliterator
				//return null if the this spliterator is defined sorted
				return null;

			throw new IllegalStateException();
		}

		@Override
		public long getExactSizeIfKnown() {
			return this.endIndex - this.beginIndex;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return (this.characteristics & characteristics) == characteristics;
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super E> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean tryAdvance(Consumer<? super E> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract java.util.Spliterator<E> trySplit();
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <V> the type of hte values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	public abstract static class ValueIterator<A, V> implements java.util.Iterator<V> {
		/**
		 * The array backing this iterator.
		 */
		protected final A array;
		/**
		 * The length of this iterator multiplied by 2.
		 */
		protected final int length;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		protected final int offset;
		/**
		 * The next index.
		 */
		protected int index;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param offset      the offset where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length      the length of the constructed iterator multiplied by 2.
		 * @param array       the array backing the constructed iterator.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 * @throws ArrayIndexOutOfBoundsException if {@code offset < 0} or {@code length < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @since 0.1.5 ~2020.07.24
		 */
		protected ValueIterator(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::isEven, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(length, Objects::nonNegative, "length");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public abstract void forEachRemaining(Consumer<? super V> consumer);

		@SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
		@Override
		public abstract V next();
	}

	public abstract static class ValueSpliterator<A, V> implements java.util.Spliterator<V> {
		@Override
		public int characteristics() {
			return 0;
		}

		@Override
		public long estimateSize() {
			return 0;
		}

		@Override
		public void forEachRemaining(Consumer<? super V> action) {

		}

		@Override
		public Comparator<? super V> getComparator() {
			return null;
		}

		@Override
		public long getExactSizeIfKnown() {
			return 0;
		}

		@Override
		public boolean hasCharacteristics(int characteristics) {
			return false;
		}

		@Override
		public boolean tryAdvance(Consumer<? super V> action) {
			return false;
		}

		@Override
		public java.util.Spliterator<V> trySplit() {
			return null;
		}
	}

	/**
	 * A collection backed by an array.
	 *
	 * @param <A> the type of the array.
	 * @param <V> the type of the values.
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.08.03
	 */
	@SuppressWarnings("EqualsAndHashcode")
	public abstract static class Values<A, V> implements java.util.Collection<V>, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 7634421013079755116L;

		/**
		 * The array backing this collection.
		 */
		@SuppressWarnings("NonSerializableFieldInSerializableClass")
		protected final A array;
		/**
		 * The length of this collection multiplied by 2.
		 */
		protected final int length;
		/**
		 * The offset where the area backing this collection in the given {@code array} is starting.
		 */
		protected final int offset;

		/**
		 * Construct a new collection backed by the given {@code array}.
		 *
		 * @param array       the array backing the constructed collection.
		 * @param offset      the offset where the area backing the constructed collection in the given {@code array} is
		 *                    starting.
		 * @param length      the length of the constructed collection multiplied by 2.
		 * @param arrayLength the length of the given {@code array}.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code offset < 0} or {@code offset + length >
		 *                                        arrayLength}.
		 * @throws IllegalArgumentException       if the given {@code length} is not even.
		 */
		protected Values(A array, int offset, int length, int arrayLength) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(offset, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "offset");
			Objects.require(offset + length, arrayLength, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "offset + length");
			Objects.require(length, Objects::isEven, "length");
			this.array = array;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public boolean add(V value) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean addAll(Collection<? extends V> collection) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public void clear() {
			if (this.length != 0)
				throw new UnsupportedOperationException("clear");
		}

		@Override
		public boolean equals(Object object) {
			return object == this;
		}

		@Override
		public boolean isEmpty() {
			return this.length == 0;
		}

		@Override
		public int size() {
			return this.length >>> 1;
		}

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean contains(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean containsAll(Collection<?> collection);

		@Override
		public abstract void forEach(Consumer<? super V> consumer);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract int hashCode();

		@Override
		public abstract ValueIterator<A, V> iterator();

		@Override
		public abstract Stream<V> parallelStream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean remove(Object object);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean removeAll(Collection<?> collection);

		@Override
		public abstract boolean removeIf(Predicate<? super V> predicate);

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract boolean retainAll(Collection<?> collection);

		@Override
		public abstract ValueSpliterator<A, V> spliterator();

		@Override
		public abstract Stream<V> stream();

		@SuppressWarnings("AbstractMethodOverridesAbstractMethod")
		@Override
		public abstract Object[] toArray();

		@Override
		public abstract <T> T[] toArray(T[] array);

		@Override
		public abstract String toString();
	}
}
//Todo stream
