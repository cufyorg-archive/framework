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

import cufy.concurrent.Do;
import cufy.concurrent.Instructor;
import cufy.util.function.ThrowRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * A way to control an input-stream using an {@link Instructor}.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 12-Feb-2020
 */
public class RemoteInputStream extends InputStream {
	/**
	 * The instructor to allow this stream to run.
	 */
	final protected Instructor instructor;
	/**
	 * The original stream. (The actual stream)
	 */
	final protected InputStream stream;

	/**
	 * Construct a new controllable input-stream.
	 *
	 * @param instructor to control the this stream with
	 * @param stream     the original stream (to get data from)
	 * @throws NullPointerException if any of the given parameters is null
	 */
	public RemoteInputStream(Instructor instructor, InputStream stream) {
		Objects.requireNonNull(instructor, "instructor");
		Objects.requireNonNull(stream, "stream");
		this.instructor = instructor;
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		int[] atomic = new int[1];
		this.exec(() -> atomic[0] = this.stream.read());
		return atomic[0];
	}

	@Override
	public int read(byte[] b) throws IOException {
		int[] atomic = new int[1];
		this.exec(() -> atomic[0] = this.stream.read(b));
		return atomic[0];
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int[] atomic = new int[1];
		this.exec(() -> atomic[0] = this.stream.read(b, off, len));
		return atomic[0];
	}

	@Override
	public long skip(long n) throws IOException {
		long[] atomic = new long[1];
		this.exec(() -> atomic[0] = this.stream.skip(n));
		return atomic[0];
	}

	@Override
	public int available() throws IOException {
		int[] atomic = new int[1];
		this.exec(() -> atomic[0] = this.stream.available());
		return atomic[0];
	}

	@Override
	public void close() throws IOException {
		this.exec(this.stream::close);
	}

	@Override
	public synchronized void mark(int readLimit) {
		this.stream.mark(readLimit);
	}

	@Override
	public synchronized void reset() throws IOException {
		this.exec(this.stream::reset);
	}

	@Override
	public boolean markSupported() {
		return this.stream.markSupported();
	}

	/**
	 * Execute the given code with respect to the {@link #instructor}.
	 *
	 * @param runnable to be executed
	 * @throws IOException          if the instructor has specified that this stream shall be closed. Or if any other I/O exception occurred.
	 * @throws NullPointerException if the given runnable is null
	 */
	protected void exec(ThrowRunnable<IOException> runnable) throws IOException {
		Objects.requireNonNull(runnable, "runnable");
		boolean[] executed = {false};

		this.instructor.start(new Do(d -> {
			executed[0] = true;
			runnable.run();
		}));

		if (!executed[0])
			throw new IOException("RemoteInputStream stopped");
	}
}
