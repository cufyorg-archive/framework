package cufy.util.array;

import cufy.util.Arrays;
import cufy.util.Objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A list backed by an array.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class ArrayList0 implements List, Serializable, RandomAccess {
	//todo parallelStream | sort | spliterator | stream

	@SuppressWarnings("JavaDoc")
	private static final long serialVersionUID = 7037921306179958530L;

	/**
	 * The backing array.
	 */
	@SuppressWarnings("NonSerializableFieldInSerializableClass")
	private final Object array;
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
	 * @throws NullPointerException     if the given {@code array} is null.
	 * @throws IllegalArgumentException if the given {@code array} is not an array.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ArrayList0(Object array) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		this.array = array;
		this.index = 0;
		this.length = Array.getLength(array);
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param index the index where the area backing the constructed list in the given {@code array} is starting.
	 * @param array the array backing the constructed list.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index > array.length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ArrayList0(int index, Object array) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(index, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		this.array = array;
		this.index = index;
		this.length = Array.getLength(array) - index;
	}

	/**
	 * Construct a new list backed by the given {@code array}.
	 *
	 * @param index  the index where the area backing the constructed list in the given {@code array} is starting.
	 * @param length the length of the constructed list.
	 * @param array  the array backing the constructed list.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code length < 0} or {@code index + length >
	 *                                        array.length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ArrayList0(int index, int length, Object array) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(length, Objects::nonNegative, "length");
		Objects.require(index + length, Array.getLength(array), Objects::nonGreater,
				ArrayIndexOutOfBoundsException.class, "index + length");
		this.array = array;
		this.index = index;
		this.length = length;
	}

	@Override
	public boolean add(Object element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public void add(int index, Object element) {
		Objects.require(index, Objects::nonNegative, IndexOutOfBoundsException.class, "index");
		Objects.require(index, this.length, Objects::isLess, IndexOutOfBoundsException.class, "index");
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean addAll(Collection collection) {
		Objects.requireNonNull(collection, "collection");
		if (collection.isEmpty())
			return false;

		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean addAll(int index, Collection collection) {
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
		for (int i = 0; i < this.length; i++) {
			Object o = Array.get(this.array, this.index + i);

			if (object == o || object != null && object.equals(o))
				return true;
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection collection) {
		Objects.requireNonNull(collection, "collection");

		for0:
		for (Object object : collection) {
			for (int i = 0; i < this.length; i++) {
				Object o = Array.get(this.array, this.index + i);

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
		if (object instanceof ArrayList0) {
			ArrayList0 list = (ArrayList0) object;

			if (list.length != this.length)
				return false;
			if (list.array == this.array && this.index == list.index)
				return true;
			for (int i = 0; i < this.length; i++) {
				Object o = Array.get(list.array, list.index + i);
				Object t = Array.get(this.array, this.index + i);

				if (o != t && (o == null || !o.equals(t)))
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
					Object t = Array.get(this.array, this.index + i++);

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
	public void forEach(Consumer consumer) {
		Objects.requireNonNull(consumer, "consumer");
		for (int i = 0; i < this.length; i++)
			consumer.accept(Array.get(this.array, this.index + i));
	}

	@Override
	public Object get(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		return Array.get(this.array, this.index + index);
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		for (int i = 0; i < this.length; i++) {
			Object element = Array.get(this.array, this.index + i);

			hashCode = 31 * hashCode + (element == null ? 0 : element.hashCode());
		}

		return hashCode;
	}

	@Override
	public int indexOf(Object object) {
		for (int i = 0; i < this.length; i++) {
			Object o = Array.get(this.array, this.index + i);

			if (object == o || object != null && object.equals(o))
				return i;
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
		for (int i = this.length - 1; i >= 0; i--) {
			Object o = Array.get(this.array, this.index + i);

			if (object == o || object != null && object.equals(o))
				return i;
		}

		return -1;
	}

	@Override
	public Iterator listIterator() {
		return new Iterator(0, this.index, this.length, this.array);
	}

	@Override
	public Iterator listIterator(int index) {
		return new Iterator(0, this.index, this.length, this.array);
	}

	@Override
	public boolean remove(Object object) {
		for (int i = 0; i < this.length; i++) {
			Object o = Array.get(this.array, this.index + i);

			if (object == o || object != null && object.equals(o))
				throw new UnsupportedOperationException("remove");
		}

		return false;
	}

	@Override
	public Object remove(int index) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean removeAll(Collection collection) {
		Objects.requireNonNull(collection, "collection");
		for (Object object : collection)
			for (int i = 0; i < this.length; i++) {
				Object o = Array.get(this.array, this.index + i);

				if (object == o || object != null && object.equals(o))
					throw new UnsupportedOperationException("removeAll");
			}

		return false;
	}

	@Override
	public boolean removeIf(Predicate predicate) {
		Objects.requireNonNull(predicate, "predicate");
		for (int i = 0; i < this.length; i++)
			if (predicate.test(Array.get(this.array, this.index + i)))
				throw new UnsupportedOperationException("removeIf");

		return false;
	}

	@Override
	public void replaceAll(UnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		for (int i = 0; i < this.length; i++)
			Array.set(this.array, this.index + i, operator.apply(Array.get(this.array, this.index + i)));
	}

	@Override
	public boolean retainAll(Collection collection) {
		Objects.requireNonNull(collection, "collection");
		for0:
		for (int i = 0; i < this.length; i++) {
			Object o = Array.get(this.array, this.index + i);

			for (Object object : collection)
				if (object == o || object != null && object.equals(o))
					continue for0;

			throw new UnsupportedOperationException("retainAll");
		}

		return false;
	}

	@Override
	public Object set(int index, Object element) {
		Objects.require(index, Objects::nonNegative, "index");
		Objects.require(index, this.length, Objects::isLess, "index");
		Object old = Array.get(this.array, this.index + index);
		Array.set(this.array, this.index + index, element);
		return old;
	}

	@Override
	public int size() {
		return this.length;
	}

	@Override
	public ArrayList0 subList(int beginIndex, int endIndex) {
		Objects.require(beginIndex, Objects::nonNegative, IndexOutOfBoundsException.class, "beginIndex");
		Objects.require(beginIndex, endIndex, Objects::nonGreater, "beginIndex");
		Objects.require(endIndex, this.length, Objects::nonGreater, IndexOutOfBoundsException.class, "endIndex");
		return new ArrayList0(this.index + beginIndex, this.index + endIndex, this.array);
	}

	@Override
	public Object[] toArray() {
		return (Object[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, Object[].class);
	}

	@Override
	public Object[] toArray(Object[] array) {
		Objects.requireNonNull(array, "array");

		if (array.length < this.length)
			return (Object[]) Arrays.copyOfRange0(this.array, this.index, this.index + this.length, array.getClass());

		if (this.array.getClass().getComponentType().isPrimitive() == array.getClass().getComponentType().isPrimitive())
			System.arraycopy(this.array, this.index, array, 0, this.length);
		else
			Arrays.hardcopy0(this.array, this.index, array, 0, this.length);

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
			builder.append(Array.get(this.array, this.index + i));

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
	public static final class Iterator implements ListIterator {
		/**
		 * The backing array.
		 */
		private final Object array;
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
		 * @throws NullPointerException     if the given {@code array} is null.
		 * @throws IllegalArgumentException if the given {@code array} is not an array.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			this.array = array;
			this.index = 0;
			this.length = Array.getLength(array);
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param index the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param array the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code array} is not an array.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code index > array.length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		private Iterator(int index, Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(index, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
			this.index = index;
			this.array = array;
			this.length = Array.getLength(array) - index;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param cursor the index to start iterating at.
		 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code array} is not an array.
		 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code cursor < 0} or {@code index > array.length} or
		 *                                        {@code cursor > array.length - index}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(int cursor, int index, Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "cursor");
			Objects.require(index, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor,
					Array.getLength(array) - index, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "cursor");
			this.cursor = cursor;
			this.index = index;
			this.array = array;
			this.length = Array.getLength(array) - index;
		}

		/**
		 * Construct a new iterator backed by the given {@code array}.
		 *
		 * @param cursor the index to start iterating at.
		 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
		 * @param length the length of the constructed iterator.
		 * @param array  the array backing the constructed iterator.
		 * @throws NullPointerException           if the given {@code array} is null.
		 * @throws IllegalArgumentException       if the given {@code array} is not an array.
		 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code index < 0} or {@code cursor < 0} or {@code index
		 *                                        + length > array.length} or {@code cursor > length}.
		 * @since 0.1.5 ~2020.07.24
		 */
		public Iterator(int cursor, int index, int length, Object array) {
			Objects.requireNonNull(array, "array");
			Objects.require(array, Objects::isArray, "array");
			Objects.require(length, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "length");
			Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
			Objects.require(cursor, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "cursor");
			Objects.require(index + length, Array.getLength(array), Objects::nonGreater,
					ArrayIndexOutOfBoundsException.class, "index + length");
			Objects.require(cursor, length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "cursor");
			this.cursor = cursor;
			this.index = index;
			this.length = length;
			this.array = array;
		}

		@Override
		public void add(Object element) {
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
		public Object next() {
			int i = this.cursor++;

			if (i < this.length)
				return Array.get(this.array, this.index + (this.last = i));

			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return this.cursor;
		}

		@Override
		public Object previous() {
			int i = --this.cursor;
			if (i >= 0)
				return Array.get(this.array, this.index + (this.last = i));

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
		public void set(Object element) {
			int i = this.last;
			if (i < 0 || i >= this.length)
				throw new IllegalStateException();

			Array.set(this.array, this.index + i, element);
		}
	}
}
