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
import java.io.Writer;
import java.util.Objects;

/**
 * A way to control a reader using an {@link Instructor}.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 13-Feb-2020
 */
public class RemoteWriter extends Writer {
	/**
	 * The instructor to allow this writer to run.
	 */
	final protected Instructor instructor;
	/**
	 * The original writer. (The actual writer)
	 */
	final protected Writer writer;

	/**
	 * Construct a new controllable writer.
	 *
	 * @param instructor to control the this writer with
	 * @param writer     the original writer (to get data from)
	 * @throws NullPointerException if any of the given parameters is null
	 */
	public RemoteWriter(Instructor instructor, Writer writer) {
		Objects.requireNonNull(instructor, "instructor");
		Objects.requireNonNull(writer, "writer");
		this.instructor = instructor;
		this.writer = writer;
	}

	@Override
	public void write(int c) throws IOException {
		this.exec(() -> this.writer.write(c));
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		this.exec(() -> this.writer.write(cbuf));
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		this.exec(() -> this.writer.write(cbuf, off, len));
	}

	@Override
	public void write(String str) throws IOException {
		this.exec(() -> this.writer.write(str));
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		this.exec(() -> this.writer.write(str, off, len));
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		this.exec(() -> this.writer.append(csq));
		return this;
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		this.exec(() -> this.writer.append(csq, start, end));
		return this;
	}

	@Override
	public Writer append(char c) throws IOException {
		this.exec(() -> this.writer.append(c));
		return this;
	}

	@Override
	public void flush() throws IOException {
		this.exec(this.writer::flush);
	}

	@Override
	public void close() throws IOException {
		this.exec(this.writer::close);
	}

	/**
	 * Execute the given code with respect to the {@link #instructor}.
	 *
	 * @param runnable to be executed
	 * @throws IOException          if the instructor has specified that this writer shall be closed. Or if any other I/O exception occurred.
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
			throw new IOException("RemoteWriter stopped");
	}
}
