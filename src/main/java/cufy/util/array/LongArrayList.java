package cufy.util.array;

import cufy.util.Arrays;
import cufy.util.Objects;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A list backed by an array.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class LongArrayList implements List<Long>, Serializable, RandomAccess {
	//todo sort

	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 736277981288898776L;

	/**
	 * The backing array.
	 */
	private final long[] array;
	/**
	 * The index where the area backing this list in the given {@code array} is starting.
	 */
	private final int index;
	/**
	 * The length of this list.
	 */
	private final int length;

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param array the array backing the constructed list.
	 * @throws NullPointerException if the given {@code array} is null.
	 * @since 0.1.5 ~2020.07.24
	 */
	public LongArrayList(long... array) {
		Objects.requireNonNull(array, "array");
		this.array = array;
		this.index = 0;
		this.length = array.length;
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
	public LongArrayList(int index, long... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		this.array = array;
		this.index = index;
		this.length = array.length - index;
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
	public LongArrayList(int index, int length, long... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(length, Objects::nonNegative, "length");
		Objects.require(index + length, array.length, Objects::nonGreater,
				ArrayIndexOutOfBoundsException.class, "index + length");
		this.array = array;
		this.index = index;
		this.length = length;
	}

	@Override
	public boolean add(Long element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public void add(int index, Long element) {
		Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
		Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean addAll(Collection<? extends Long> collection) {
		Objects.requireNonNull(collection, "collection");
		if (collection.isEmpty())
			return false;

		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean addAll(int index, Collection<? extends Long> collection) {
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
	public boolean contains(Object object) {
		if (object instanceof Long) {
			long primitive = (long) object;

			for (int i = 0; i < this.length; i++) {
				long o = this.array[this.index + i];

				if (primitive == o)
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");

		for0:
		for (Object object : collection) {
			if (object instanceof Long) {
				long primitive = (long) object;

				for (int i = 0; i < this.length; i++) {
					long o = this.array[this.index + i];

					if (primitive == o)
						continue for0;
				}
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object instanceof LongArrayList) {
			LongArrayList list = (LongArrayList) object;

			if (list.length != this.length)
				return false;
			if (list.array == this.array && this.index == list.index)
				return true;
			for (int i = 0; i < this.length; i++) {
				long o = list.array[list.index + i];
				long t = this.array[this.index + i];

				if (o != t)
					return false;
			}

			return true;
		}
		if (object instanceof List) {
			java.util.Iterator iterator = ((Iterable) object).iterator();

			int i = 0;
			while (iterator.hasNext()) {
				if (i < this.length) {
					Object o = iterator.next();

					if (o instanceof Long) {
						long p = (long) o;
						long t = this.array[this.index + i++];

						if (p == t)
							continue;
					}
				}

				return false;
			}

			return i == this.length;
		}

		return false;
	}

	@Override
	public void forEach(Consumer<? super Long> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = 0; i < this.length; i++)
			consumer.accept(this.array[this.index + i]);
	}

	@Override
	public Long get(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		return this.array[this.index + index];
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = 0; i < this.length; i++) {
			long element = this.array[this.index + i];

			hashCode = 31 * hashCode + Long.hashCode(element);
		}

		return hashCode;
	}

	@Override
	public int indexOf(Object object) {
		if (object instanceof Long) {
			long primitive = (long) object;

			for (int i = 0; i < this.length; i++) {
				long o = this.array[this.index + i];

				if (primitive == o)
					return i;
			}
		}

		return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}

	@Override
	public Iterator iterator() {
		return new Iterator(0, this.index, this.length, this.array);
	}

	@Override
	public int lastIndexOf(Object object) {
		if (object instanceof Long) {
			long primitive = (long) object;

			for (int i = this.length - 1; i >= 0; i--) {
				long o = this.array[this.index + i];

				if (primitive == o)
					return i;
			}
		}

		return -1;
	}

	@Override
	public Iterator listIterator() {
		return new Iterator(0, this.index, this.length, this.array);
	}

	@Override
	public Iterator listIterator(int index) {
		return new Iterator(index, this.index, this.length, this.array);
	}

	@Override
	public Stream<Long> parallelStream() {
		return Arrays.stream(this.array, this.index, this.index + this.length).parallel().boxed();
	}

	@Override
	public boolean remove(Object object) {
		if (object instanceof Long) {
			long primitive = (long) object;

			for (int i = 0; i < this.length; i++) {
				long o = this.array[this.index + i];

				if (primitive == o)
					throw new UnsupportedOperationException("remove");
			}
		}

		return false;
	}

	@Override
	public Long remove(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for (Object object : collection)
			if (object instanceof Long) {
				long primitive = (long) object;

				for (int i = 0; i < this.length; i++) {
					long o = this.array[this.index + i];

					if (primitive == o)
						throw new UnsupportedOperationException("removeAll");
				}
			}

		return false;
	}

	@Override
	public boolean removeIf(Predicate<? super Long> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		for (int i = 0; i < this.length; i++)
			if (predicate.test(this.array[this.index + i]))
				throw new UnsupportedOperationException("removeIf");

		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<Long> operator) {
		Objects.requireNonNull(operator, "operator");
		for (int i = 0; i < this.length; i++)
			this.array[this.index + i] = operator.apply(this.array[this.index + i]);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		Objects.requireNonNull(collection, "collection");
		for0:
		for (int i = 0; i < this.length; i++) {
			long o = this.array[this.index + i];

			for (Object object : collection)
				if (object instanceof Long) {
					long primitive = (long) object;

					if (primitive == o)
						continue for0;
				}

			throw new UnsupportedOperationException("retainAll");
		}

		return false;
	}

	@Override
	public Long set(int index, Long element) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		long old = this.array[this.index + index];
		this.array[this.index + index] = element;
		return old;
	}

	@Override
	public int size() {
		return this.length;
	}

	@Override
	public Spliterator.OfLong spliterator() {
		return Arrays.spliterator(this.array, this.index, this.index + this.length);
	}

	@Override
	public Stream<Long> stream() {
		return Arrays.stream(this.array, this.index, this.index + this.length).boxed();
	}

	@Override
	public LongArrayList subList(int beginIndex, int endIndex) {
		Objects.require(beginIndex, Objects::nonNegative, IndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(endIndex, this.length, Objects::nonGreater, IndexOutOfBoundsException.class, "endIndex");
		return new LongArrayList(this.index + beginIndex, this.index + endIndex, this.array);
	}

	@Override
	public Long[] toArray() {
		return (Long[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, Long[].class);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		Objects.requireNonNull(array, "array");

		if (array.length < this.length)
			return (T[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, array.getClass());

		Arrays.hardcopy(this.array, this.index, array, 0, this.length);

		if (array.length > this.length)
			array[this.length] = null;

		return array;
	}

	@Override
	public String toString() {
		if (this.length == 0)
			return "[]";

		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		while (true) {
			builder.append(this.array[this.index + i]);

			if (++i < this.length) {
				builder.append(", ");
				continue;
			}

			return builder.append("]")
					.toString();
		}
	}

	/**
	 * An iterator backed by an array.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.07.24
	 */
	public static final class Iterator implements ListIterator<Long> {
		/**
		 * The backing array.
		 */
		private final long[] array;
		/**
		 * The index where the area backing the iterator in the given {@code array} is starting.
		 */
		private final int index;
		/**
		 * The length of this iterator.
		 */
		private final int length;
		/**
		 * The next index.
		 */
		private int cursor;
		/**
		 * The last index.
		 */
		private int last = -1;

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException if the given {@code array} is null.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(long... array) {
			Objects.requireNonNull(array, "array");
			this.array = array;
			this.index = 0;
			this.length = array.length;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index > array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(int index, long... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
			this.index = index;
			this.array = array;
			this.length = array.length - index;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param cursor the index to start iterating at.
		 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code cursor < 0} or {@code index > array.length} or
		 *                                        {@code cursor > array.length - index}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(int cursor, int index, long... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "cursor");
			Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor, array.length - index, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "cursor");
			this.cursor = cursor;
			this.index = index;
			this.array = array;
			this.length = array.length - index;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param cursor the index to start iterating at.
		 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code index < 0} or {@code cursor < 0} or {@code index
		 *                                        + length > array.length} or {@code cursor > length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(int cursor, int index, int length, long... array) {
			Objects.requireNonNull(array, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "cursor");
			Objects.require(index + length, array.length, Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "index + length");
			Objects.require(cursor, length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "cursor");
			this.cursor = cursor;
			this.index = index;
			this.length = length;
			this.array = array;
		}

		@Override
		public void add(Long element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean hasNext() {
			return this.cursor < this.length;
		}

		@Override
		public boolean hasPrevious() {
			return this.cursor > 0;
		}

		@Override
		public Long next() {
			int i = this.cursor++;

			if (i < this.length)
				return this.array[this.index + (this.last = i)];

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Long previous() {
			int i = --this.cursor;
			if (i >= 0)
				return this.array[this.index + (this.last = i)];

			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public void set(Long element) {
			int i = this.last;
			if (i < 0 || i >= this.length)
				throw new IllegalStateException();

			this.array[this.index + i] = element;
		}
	}
}
