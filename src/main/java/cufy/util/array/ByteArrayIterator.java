package cufy.util.array;

import cufy.util.Objects;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An iterator backed by an array.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.07.24
 */
public final class ByteArrayIterator implements ListIterator<Byte> {
	/**
	 * The backing array.
	 */
	private final byte[] array;
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
	public ByteArrayIterator(byte... array) {
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
	public ByteArrayIterator(int index, byte... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		this.index = index;
		this.array = array;
		this.length = array.length;
	}

	/**
	 * Construct a new iterator backed by the given {@code array}.
	 *
	 * @param cursor the index to start iterating at.
	 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
	 * @param array  the array backing the constructed iterator.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code index < 0} or {@code cursor < 0} or {@code index > array.length} or {@code
	 *                                        cursor > array.length - index}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ByteArrayIterator(int cursor, int index, byte... array) {
		Objects.requireNonNull(array, "array");
		Objects.require(index, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(cursor, Objects::nonNegative, ArrayIndexOutOfBoundsException.class, "cursor");
		Objects.require(index, array.length, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "index");
		Objects.require(cursor, array.length - index, Objects::nonGreater, ArrayIndexOutOfBoundsException.class, "cursor");
		this.cursor = cursor;
		this.index = index;
		this.array = array;
		this.length = array.length;
	}

	/**
	 * Construct a new iterator backed by the given {@code array}.
	 *
	 * @param cursor the index to start iterating at.
	 * @param index  the index where the area backing the constructed iterator in the given {@code array} is starting.
	 * @param length the length of the constructed iterator.
	 * @param array  the array backing the constructed iterator.
	 * @throws NullPointerException           if the given {@code array} is null.
	 * @throws ArrayIndexOutOfBoundsException if {@code length < 0} or {@code index < 0} or {@code cursor < 0} or {@code index +
	 *                                        length > array.length} or {@code cursor > length}.
	 * @since 0.1.5 ~2020.07.24
	 */
	public ByteArrayIterator(int cursor, int index, int length, byte... array) {
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
	public void add(Byte element) {
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
	public Byte next() {
		int i = this.cursor++;

		if (i < this.length)
			return this.array[this.last = this.index + i];

		throw new NoSuchElementException();
	}

	@Override
	public int nextIndex() {
		return this.cursor;
	}

	@Override
	public Byte previous() {
		int i = --this.cursor;
		if (i >= 0)
			return this.array[this.last = this.index + i];

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
	public void set(Byte element) {
		int i = this.last;
		if (i < 0 || i >= this.length)
			throw new IllegalStateException();

		this.array[this.index + i] = element;
	}
}
