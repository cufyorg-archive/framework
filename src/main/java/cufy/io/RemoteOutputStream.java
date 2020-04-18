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

import cufy.concurrent.Once;
import cufy.concurrent.Instructor;
import cufy.util.function.ThrowRunnable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * A way to control an input-stream using an {@link Instructor}.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 13-Feb-2020
 */
public class RemoteOutputStream extends OutputStream {
	/**
	 * The instructor to allow this stream to run.
	 */
	final protected Instructor instructor;
	/**
	 * The original stream. (The actual stream)
	 */
	final protected OutputStream stream;

	/**
	 * Construct a new controllable output-stream.
	 *
	 * @param instructor to control the this stream with
	 * @param stream     the original stream (to get data from)
	 * @throws NullPointerException if any of the given parameters is null
	 */
	public RemoteOutputStream(Instructor instructor, OutputStream stream) {
		Objects.requireNonNull(instructor, "instructor");
		Objects.requireNonNull(stream, "stream");
		this.instructor = instructor;
		this.stream = stream;
	}

	@Override
	public void write(int b) throws IOException {
		this.exec(() -> this.stream.write(b));
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.exec(() -> this.stream.write(b));
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.exec(() -> this.stream.write(b, off, len));
	}

	@Override
	public void flush() throws IOException {
		this.exec(this.stream::flush);
	}

	@Override
	public void close() throws IOException {
		this.exec(this.stream::close);
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

		this.instructor.start(new Once(d -> {
			executed[0] = true;
			runnable.run();
		}));

		if (!executed[0])
			throw new IOException("RemoteOutputStream stopped");
	}
}
