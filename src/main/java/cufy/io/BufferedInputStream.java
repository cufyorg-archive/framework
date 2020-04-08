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
import java.io.InputStream;
import java.util.Objects;

/**
 * A boxing for input-streams as a workaround to support the methods {@link #mark} and {@link #reset()}. Using a {@link Buffer}.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 14-Feb-2020
 */
public class BufferedInputStream extends InputStream {
	/**
	 * The buffer that stores the marked characters.
	 */
	protected Buffer<Integer> buffer;
	/**
	 * The stream that this delegate is delegating to.
	 */
	protected InputStream stream;

	/**
	 * Construct a new delegate that delegates to the given stream.
	 *
	 * @param stream to delegate to
	 * @throws NullPointerException if the given stream is null
	 */
	public BufferedInputStream(InputStream stream) {
		Objects.requireNonNull(stream, "stream");
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		this.ensureOpen();
		if (this.buffer != null && this.buffer.hasNext()) {
			return (int) Reflectionu.primitiveCast(int.class, this.buffer.read());
		} else {
			int value = this.stream.read();

			if (value != -1 && this.buffer != null)
				this.buffer.write(value);

			return value;
		}
	}

	@Override
	public int read(byte[] b) throws IOException {
		Objects.requireNonNull(b, "b");
		return this.read(b, 0, b.length);
	}

	@Override
	public int read(byte[] bytes, int pos, int length) throws IOException {
		//assertion
		Objects.requireNonNull(bytes, "bytes");
		if (pos < 0 || length < 0 || pos + length > bytes.length)
			throw new IndexOutOfBoundsException();
		if (length == 0)
			return 0;
		this.ensureOpen();

		//REWIND
		int rewind = 0;
		if (this.buffer != null && this.buffer.hasNext()) {
			rewind = this.buffer.read(bytes, pos, length);

			//rewind is enough
			if (rewind == length)
				return rewind;

			length = length - rewind;
			pos = length + rewind;
		}

		//NEW DATA
		int read = this.stream.read(bytes, pos, length);

		//the end
		if (read == -1)
			return rewind == 0 ? -1 : rewind;

		//sneak copy
		if (this.buffer != null)
			this.buffer.write(bytes, pos, read);

		return rewind + read;
	}

	@Override
	public long skip(long ns) throws IOException {
		if (ns < 0)
			throw new IllegalArgumentException("negative long isn't allowed here!");

		int skip = ns > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) ns;

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
			byte[] tempo = new byte[skip];

			//read new data
			int r1 = this.stream.read(tempo, 0, skip);

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

	@Override
	public int available() throws IOException {
		return (this.buffer == null ? 0 : this.buffer.remaining()) + this.stream.available();
	}

	@Override
	public void close() throws IOException {
		this.stream.close();
		this.stream = null;
		this.buffer = null;
	}

	@Override
	public synchronized void mark(int readAheadLimit) {
		this.mark(readAheadLimit, 50);
	}

	@Override
	public synchronized void reset() throws IOException {
		this.ensureOpen();
		if (this.buffer == null)
			throw new IOException("No mark to reset to!");

		this.buffer.setCursor(0);
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	/**
	 * Make sure that this buffer is opened. Throw an IOException if it's not.
	 *
	 * @throws IOException if this stream is closed
	 */
	public void ensureOpen() throws IOException {
		if (this.stream == null)
			throw new IOException("Stream closed");
	}

	/**
	 * Get the {@link Buffer} used CURRENTLY by this stream.
	 *
	 * @return the buffer used currently by this stream
	 */
	public Buffer<Integer> getBuffer() {
		return this.buffer;
	}

	/**
	 * Return whether this buffer is marked or not. Useful before invoking {@link #mark}.
	 *
	 * @return whether this is currently marked or not
	 */
	public boolean isMarked() {
		return this.buffer != null;
	}

	/**
	 * Marks the present position in the stream. Subsequent calls to reset() will attempt to reposition the stream to this point.
	 *
	 * @param readAheadLimit the initial capacity of the buffer
	 * @param chunkSize      the size added on each buffer expand request
	 * @throws IllegalArgumentException if the given 'readAheadLimit' is less than zero. Or if the given 'chunkSize' is not greater than zero.
	 */
	public void mark(int readAheadLimit, int chunkSize) {
		if (readAheadLimit < 0)
			throw new IllegalArgumentException("readAheadLimit < 0");
		if (chunkSize <= 0)
			throw new IllegalArgumentException("chunkSize <= 0");

		this.buffer = this.buffer == null ? new Buffer<>(readAheadLimit, chunkSize) : this.buffer.duplicate(this.buffer.getCursor(), chunkSize);
	}

	/**
	 * Remove the last mark. (Remove the buffer)
	 */
	public void unmark() {
		this.buffer = null;
	}
}
