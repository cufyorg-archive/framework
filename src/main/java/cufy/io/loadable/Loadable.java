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
import cufy.io.RemoteInputStream;
import cufy.io.RemoteOutputStream;
import cufy.io.RemoteReader;
import cufy.io.RemoteWriter;

import java.io.*;

/**
 * An object that can be loaded-from, saved-to another container.
 *
 * @author lsafer
 * @version 0.1.2
 * @since 13-Feb-2020
 */
public interface Loadable {
	/**
	 * Get a new, unused input stream from the container of this loadable.
	 *
	 * @param instructor a controller for the returned stream
	 * @return an input stream from the container of this loadable
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	default InputStream getInputStream(Instructor instructor) throws IOException {
		return new RemoteInputStream(instructor, this.getInputStream());
	}
	/**
	 * Get a new, unused output stream from the container of this loadable.
	 *
	 * @param instructor a controller for the returned stream
	 * @return an output stream from the container of this loadable
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	default OutputStream getOutputStream(Instructor instructor) throws IOException {
		return new RemoteOutputStream(instructor, this.getOutputStream());
	}

	/**
	 * Get a new, unused reader from the container of this loadable.
	 *
	 * @param instructor a controller for the returned reader
	 * @return an reader from the container of this loadable
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	default Reader getReader(Instructor instructor) throws IOException {
		return new RemoteReader(instructor, this.getReader());
	}
	/**
	 * Get a new, unused writer from the container of this loadable.
	 *
	 * @param instructor a controller for the returned writer
	 * @return an writer from the container of this loadable
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	default Writer getWriter(Instructor instructor) throws IOException {
		return new RemoteWriter(instructor, this.getWriter());
	}

	/**
	 * Get a new, unused input stream from the container of this loadable.
	 *
	 * @return an input stream from the container of this loadable
	 * @throws IOException if any I/O exception occurs
	 */
	InputStream getInputStream() throws IOException;
	/**
	 * Get a new, unused output stream from the container of this loadable.
	 *
	 * @return an output stream from the container of this loadable
	 * @throws IOException if any I/O exception occurs
	 */
	OutputStream getOutputStream() throws IOException;

	/**
	 * Get a new, unused reader from the container of this loadable.
	 *
	 * @return an reader from the container of this loadable
	 * @throws IOException if any I/O exception occurs
	 */
	Reader getReader() throws IOException;
	/**
	 * Get a new, unused writer from the container of this loadable.
	 *
	 * @return an writer from the container of this loadable
	 * @throws IOException if any I/O exception occurs
	 */
	Writer getWriter() throws IOException;

	/**
	 * Load this loadable from the container of this loadable.
	 *
	 * @throws IOException if any I/O exception occurs
	 */
	void load() throws IOException;
	/**
	 * Load this loadable from the container of this loadable.
	 *
	 * @param instructor to control the loading operation
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	void load(Instructor instructor) throws IOException;

	/**
	 * Save this loadable to the container of this loadable.
	 *
	 * @throws IOException if any I/O exception occurs
	 */
	void save() throws IOException;
	/**
	 * Save this loadable to the container of this loadable.
	 *
	 * @param instructor to control the saving operation
	 * @throws IOException          if any I/O exception occurs
	 * @throws NullPointerException if the given instructor is null
	 */
	void save(Instructor instructor) throws IOException;
}
