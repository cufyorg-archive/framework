package cufy.util.array;

import cufy.util.Objects;

import java.lang.reflect.Array;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An iterator backed by an array.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class ArrayIterator0 implements ListIterator {
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
	public ArrayIterator0(Object array) {
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
	private ArrayIterator0(int index, Object array) {
		Objects.requireNonNull(array, "array");
		Objects.require(array, Objects::isArray, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(index, Array.getLength(array), Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		this.index = index;
		this.array = array;
		this.length = Array.getLength(array);
	}

	/**
	 * Construct a new iterator backed by the given {@code array}.
	 *
	 * @param cursor the index to start iterating at.
	 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
	 * @param array  the array backing the constructed iterator.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws IllegalArgumentException       if the given {@code array} is not an array.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code cursor < 0} or {@code index > array.length} or {@code
	 *                                        cursor > array.length - index}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ArrayIterator0(int cursor, int index, Object array) {
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
		this.length = Array.getLength(array);
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
	 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code index < 0} or {@code cursor < 0} or {@code index +
	 *                                        length > array.length} or {@code cursor > length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ArrayIterator0(int cursor, int index, int length, Object array) {
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
			return Array.get(this.array, this.last = this.index + i);

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
			return Array.get(this.array, this.last = this.index + i);

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
