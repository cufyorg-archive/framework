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
package cufy.io.loadable;

import cufy.concurrent.Instructor;
import cufy.io.BufferedInputStream;
import cufy.io.BufferedReader;
import cufy.io.*;

import java.io.*;
import java.util.Objects;

/**
 * A loadable that uses {@link File} as it's container.
 *
 * @author lsafer
 * @version 0.1.3
 * @since 14-Feb-2020
 */
public interface FileLoadable extends Loadable {
	@Override
	default InputStream getInputStream(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		InputStream base = new FileInputStream(this.getFile());
		InputStream buff = new BufferedInputStream(base);
		InputStream ctrl = new RemoteInputStream(instructor, buff);

		return ctrl;
	}
	@Override
	default OutputStream getOutputStream(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		OutputStream base = new FileOutputStream(this.getFile());
		OutputStream ctrl = new RemoteOutputStream(instructor, base);

		return ctrl;
	}

	@Override
	default Reader getReader(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		Reader base = new FileReader(this.getFile());
		Reader buff = new BufferedReader(base);
		Reader ctrl = new RemoteReader(instructor, buff);

		return ctrl;
	}
	@Override
	default Writer getWriter(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		Writer base = new FileWriter(this.getFile());
		Writer ctrl = new RemoteWriter(instructor, base);

		return ctrl;
	}

	@Override
	default InputStream getInputStream() throws IOException {
		InputStream base = new FileInputStream(this.getFile());
		InputStream buff = new BufferedInputStream(base);

		return buff;
	}
	@Override
	default OutputStream getOutputStream() throws IOException {
		OutputStream base = new FileOutputStream(this.getFile());

		return base;
	}

	@Override
	default Reader getReader() throws IOException {
		Reader base = new FileReader(this.getFile());
		Reader buff = new BufferedReader(base);

		return buff;
	}
	@Override
	default Writer getWriter() throws IOException {
		Writer base = new FileWriter(this.getFile());

		return base;
	}

	/**
	 * Get the file targeted by this file-loadable.
	 *
	 * @return the file of this
	 */
	File getFile();
}
