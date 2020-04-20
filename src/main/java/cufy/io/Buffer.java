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
package cufy.io;

import cufy.util.Arrays;

import java.io.Reader;
import java.lang.reflect.Array;
import java.util.Objects;

/**
 * A buffer to store values and get them by a cursor. Type compat all managed by the trust of the user :).
 *
 * @param <T> the type of data this buffer is holding
 * @author lsafer
 * @version 0.1.3
 * @since 9-Jan-2020
 */
public class Buffer<T> {
	/**
	 * The size of every expand on the data of this buffer.
	 */
	final protected int chunkSize;
	/**
	 * Indicates to the index were the next input/output operation will be applied to.
	 */
	protected int cursor = 0;
	/**
	 * The data that this buffer is carrying.
	 */
	protected T[] data;
	/**
	 * Indicates to the size of the valid data on the data array of this.
	 *
	 * @implSpec no decremental allowed
	 */
	protected int size = 0;

	/**
	 * Construct a new buffer with the default chunk size.
	 */
	public Buffer() {
		this(50, 50);
	}

	/**
	 * Construct a new buffer with the specified initial size. And the specified chunk size.
	 *
	 * @param initialSize the initial size
	 * @param chunkSize   the size to be added on each expand request
	 * @throws IllegalArgumentException if the given chunkSize, initialSize is less than one. (As will for the initialSize)
	 */
	public Buffer(int initialSize, int chunkSize) {
		if (initialSize < 1)
			throw new IllegalArgumentException("initialSize is less than one");
		if (chunkSize < 1)
			throw new IllegalArgumentException("chunkSize is less than one");

		this.data = (T[]) new Object[initialSize];
		this.chunkSize = chunkSize;
	}

	/**
	 * Create a new buffer that contains the data of this buffer. (BUT cursor set to 0). Data trimmed from the offset specified.
	 *
	 * @param off       the position to start copying from
	 * @param chunkSize the chunk-size for the returned buffer
	 * @return a duplicate buffer of this
	 * @throws IndexOutOfBoundsException if the given 'off' is out of this buffer's bounds
	 * @throws IllegalArgumentException  if the given 'chunkSize' is not greater than 0
	 */
	public Buffer<T> duplicate(int off, int chunkSize) {
		if (off > this.size)
			throw new IndexOutOfBoundsException(off + " is greater-than, equals-to this buffer's size " + this.size);
		if (chunkSize <= 0)
			throw new IllegalArgumentException("chunkSize <= 0");

		int length = this.data.length - off;

		Buffer buffer = new Buffer(length, chunkSize);
		buffer.size = this.size - off;

		System.arraycopy(this.data, off, buffer.data, 0, length);

		return buffer;
	}

	/**
	 * Returns the index that the cursor of this is pointing to.
	 *
	 * @return the index that the cursor of this is pointing to
	 */
	public int getCursor() {
		return this.cursor;
	}

	/**
	 * Set the current cursor to be pointing to the given index.
	 *
	 * @param index to set the cursor to point to (if the index is greater than the size of this. The index will be set to the size of this)
	 * @throws IndexOutOfBoundsException if the given index is negative.
	 */
	public void setCursor(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException("index is negative");
		this.cursor = Math.min(this.size, index);
	}

	/**
	 * Get the number of values stored at this. Useful when dealing directly to the cursor.
	 *
	 * @return the number of values on this
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Return wither this buffer has more values to return.
	 *
	 * @return whether this buffer has more data to be read or not
	 */
	public boolean hasNext() {
		return this.cursor < this.size;
	}

	/**
	 * Read the value that the cursor of this buffer is pointing on. Then set the cursor to the next value. If the cursor pointing to a value that is
	 * out of this buffer's bounds. Or the cursor is pointing to a value that isn't set yet. Then null will be returned.
	 *
	 * @return the value pointed to by the cursor of this. Or null if pointing to a value out of bounds or pointing to a value not set yet.
	 */
	public T read() {
		return this.cursor < this.size ? this.data[this.cursor++] : null;
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(Object[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(boolean[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(byte[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(char[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(double[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(float[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(int[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(long[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @see Reader#read(char[], int, int)
	 */
	public int read(short[] buffer, int off, int len) {
		return this.read0(buffer, off, len);
	}

	/**
	 * Reads values into a portion of an array.
	 *
	 * @param buffer Destination buffer
	 * @param off    Offset at which to start storing values
	 * @param len    Maximum number of values to read
	 * @return The number of values read, or -1 if the end of the buffer has been reached
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @throws IllegalArgumentException  if the given 'cbuf' is not an array
	 * @see Reader#read(char[], int, int)
	 */
	public int read0(Object buffer, int off, int len) {
		Objects.requireNonNull(buffer, "array");
		if (!buffer.getClass().isArray())
			throw new IllegalArgumentException(buffer + " isn't an array");

		int l = Array.getLength(buffer);
		if (off < 0 || len < 0 || off >= l || off + len > l) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return 0;
		} else if (this.cursor >= this.size) {
			return -1;
		} else {
			int index = this.cursor;
			int length = Math.min(this.size - this.cursor, len);
			this.cursor += length;

			if (buffer instanceof int[])
				System.arraycopy(this.data, index, buffer, off, length);
			else Arrays.hardcopy(this.data, index, buffer, off, length);

			return length;
		}
	}

	/**
	 * Get the number of remaining values in this. (depending on the cursor)
	 *
	 * @return the number of remaining values
	 */
	public int remaining() {
		return this.size - this.cursor;
	}

	/**
	 * Increment the cursor by the given number. Or set the cursor to the size of this if the size reached by the incrementation.
	 *
	 * @param ns the number of values to be skipped
	 * @return the number of values actually skipped
	 * @throws IllegalArgumentException if the given 'ns' is less than zero
	 */
	public int skip(int ns) {
		if (ns < 0)
			throw new IllegalArgumentException("ns < 0");

		int skip = Math.min(ns, this.remaining());
		this.cursor += skip;
		return skip;
	}

	/**
	 * Add the given value to this on were the cursor of this buffer is on. Then increment the cursor of this by one.
	 *
	 * @param value to be set
	 */
	public void write(T value) {
		int index = this.cursor;
		this.expand(this.size = Math.max(++this.cursor, this.size));

		this.data[index] = value;
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(int[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(char[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(byte[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(short[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(boolean[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(long[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(double[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 */
	public void write(float[] buffer, int off, int len) {
		this.write0(buffer, off, len);
	}

	/**
	 * Add the given values to this on were the cursor of this buffer is on. Then increment the cursor of this by the length of the given collection.
	 *
	 * @param buffer to read from
	 * @param off    the offset to start storing from
	 * @param len    the maximum number of values to store
	 * @throws NullPointerException      if the given array is null
	 * @throws IndexOutOfBoundsException if (off >= cbuf.length). Or (off + len > cbuf.length). Or (off < 0). Or (len < 0)
	 * @throws IllegalArgumentException  if the given 'cbuf' is not an array
	 */
	public void write0(Object buffer, int off, int len) {
		Objects.requireNonNull(buffer, "array");
		if (!buffer.getClass().isArray())
			throw new IllegalArgumentException(buffer + " isn't an array");

		int al = Array.getLength(buffer);
		if (off < 0 || len < 0 || off >= al || off + len > al) {
			throw new IndexOutOfBoundsException();
		} else if (len > 0) {
			int index = this.cursor;
			this.cursor += len;
			this.expand(this.size = Math.max(this.cursor, this.size));

			if (buffer instanceof int[])
				System.arraycopy(buffer, off, this.data, index, len);
			else Arrays.hardcopy(buffer, off, this.data, index, len);
		}
	}

	/**
	 * Expand the data array of this buffer to a length greater than the given length.
	 *
	 * @param length the minimum length allowed
	 * @throws IllegalArgumentException if the given length is less than zero
	 */
	protected void expand(int length) {
		if (length < 0)
			throw new IllegalArgumentException("length < 0");

		if (length > this.data.length) {
			T[] nio = (T[]) new Object[length + this.chunkSize];
			System.arraycopy(this.data, 0, nio, 0, this.data.length);
			this.data = nio;
		}
	}
}
