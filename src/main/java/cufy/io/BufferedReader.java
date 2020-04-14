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

import cufy.util.Reflectionu;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Objects;

/**
 * A boxing for readers as a workaround to support the methods {@link #mark} and {@link #reset()}. Using a {@link Buffer}.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 10-Jan-2020
 */
public class BufferedReader extends Reader {
	/**
	 * The buffer that stores the marked characters.
	 */
	protected Buffer<Integer> buffer;
	/**
	 * The reader that this delegate is delegating to.
	 */
	protected Reader reader;

	/**
	 * Construct a new delegate that delegates to the given reader.
	 *
	 * @param reader to delegate to
	 * @throws NullPointerException if the given reader is null
	 */
	public BufferedReader(Reader reader) {
		super(Objects.requireNonNull(reader, "reader"));
		this.reader = reader;
	}

	/**
	 * Construct a new delegate that delegates to the given reader.
	 *
	 * @param reader to delegate to
	 * @param lock   the lock to synchronize operations to
	 * @throws NullPointerException if any of the given parameters is null
	 */
	public BufferedReader(Reader reader, Object lock) {
		super(lock);
		this.reader = reader;
	}

	@Override
	public int read(CharBuffer target) throws IOException {
		synchronized (this.lock) {
			Objects.requireNonNull(target, "target");

			int len = target.remaining();
			char[] cbuf = new char[len];

			int n = this.read(cbuf, 0, len);

			if (n > 0)
				target.put(cbuf, 0, n);

			return n;
		}
	}

	@Override
	public int read() throws IOException {
		synchronized (super.lock) {
			this.ensureOpen();
			if (this.buffer != null && this.buffer.hasNext()) {
				return (int) Reflectionu.primitiveCast(int.class, this.buffer.read());
			} else {
				int value = this.reader.read();

				if (value != -1 && this.buffer != null)
					this.buffer.write(value);

				return value;
			}
		}
	}

	@Override
	public int read(char[] cbuf) throws IOException {
		synchronized (this.lock) {
			Objects.requireNonNull(cbuf, "cbuf");
			return this.read(cbuf, 0, cbuf.length);
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		synchronized (super.lock) {
			//assertion
			Objects.requireNonNull(cbuf, "cbuf");
			if (off < 0 || len < 0 || off + len > cbuf.length)
				throw new IndexOutOfBoundsException();
			if (len == 0)
				return 0;
			this.ensureOpen();

			//REWIND
			int rewind = 0;
			if (this.buffer != null && this.buffer.hasNext()) {
				rewind = this.buffer.read(cbuf, off, len);

				//rewind is enough
				if (rewind == len)
					return rewind;

				len = len - rewind;
				off = len + rewind;
			}

			//NEW DATA
			int read = this.reader.read(cbuf, off, len);

			//the end
			if (read == -1)
				return rewind == 0 ? -1 : rewind;

			//sneak copy
			if (this.buffer != null)
				this.buffer.write(cbuf, off, read);

			return rewind + read;
		}
	}

	@Override
	public long skip(long ns) throws IOException {
		if (ns < 0)
			throw new IllegalArgumentException("negative long isn't allowed here!");

		int skip = ns > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) ns;

		synchronized (super.lock) {
			//assertion
			this.ensureOpen();

			//REWIND
			int r0 = 0;
			if (this.buffer != null)
				r0 = this.buffer.skip(skip);

			skip -= r0;
			if (skip == 0) {
				//REWIND is enough
				return r0;
			} else {
				//to carry to the buffer
				char[] tempo = new char[skip];

				//read new data
				int r1 = this.reader.read(tempo, 0, skip);

				//No data have been returned!
				if (r1 == 0 || r1 == -1) {
					//only the rewind
					return r0;
				} else if (this.buffer != null) {
					//sneak store
					this.buffer.write(tempo, 0, r1);
				}

				return r0 + r1;
			}
		}
	}

	@Override
	public boolean ready() throws IOException {
		synchronized (super.lock) {
			this.ensureOpen();
			return this.reader.ready();
		}
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public void mark(int readAheadLimit) {
		this.mark(readAheadLimit, 50);
	}

	@Override
	public void reset() throws IOException {
		synchronized (super.lock) {
			this.ensureOpen();
			if (this.buffer == null)
				throw new IOException("No mark to reset to!");

			this.buffer.setCursor(0);
		}
	}

	@Override
	public void close() throws IOException {
		synchronized (super.lock) {
			this.reader.close();
			this.reader = null;
			super.lock = null;
			this.buffer = null;
		}
	}

	/**
	 * Make sure that this buffer is opened. Throw an IOException if it's not.
	 *
	 * @throws IOException if this reader is closed
	 */
	public void ensureOpen() throws IOException {
		synchronized (super.lock) {
			if (this.reader == null)
				throw new IOException("Stream closed");
		}
	}

	/**
	 * Get the {@link Buffer} used CURRENTLY by this reader.
	 *
	 * @return the buffer used currently by this reader
	 */
	public Buffer<Integer> getBuffer() {
		synchronized (super.lock) {
			return this.buffer;
		}
	}

	/**
	 * Return whether this buffer is marked or not. Useful before invoking {@link #mark}.
	 *
	 * @return whether this is currently marked or not
	 */
	public boolean isMarked() {
		synchronized (super.lock) {
			return this.buffer != null;
		}
	}

	/**
	 * Marks the present position in the stream. Subsequent calls to reset() will attempt to reposition the stream to this point.
	 *
	 * @param readAheadLimit the initial capacity of the buffer
	 * @param chunkSize      the size added on each buffer expand request
	 * @throws IllegalArgumentException if the given 'readAheadLimit' is less than zero. Or if the given 'chunkSize' is not greater than zero
	 */
	public void mark(int readAheadLimit, int chunkSize) {
		synchronized (super.lock) {
			if (readAheadLimit < 0)
				throw new IllegalArgumentException("readAheadLimit < 0");
			if (chunkSize <= 0)
				throw new IllegalArgumentException("chunkSize <= 0");

			this.buffer = this.buffer == null ? new Buffer<>(readAheadLimit, chunkSize) : this.buffer.duplicate(this.buffer.getCursor(), chunkSize);
		}
	}

	/**
	 * Remove the last mark. (Remove the buffer)
	 */
	public void unmark() {
		synchronized (super.lock) {
			this.buffer = null;
		}
	}
}
