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
package cufy.util.function;

import cufy.util.Reflectionu;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <E> the exception
 * @author lsafer
 * @version 0.1.3
 * @since 13-Feb-2020
 */
@FunctionalInterface
public interface ThrowRunnable<E extends Throwable> extends Runnable {
	@Override
	default void run() {
		try {
			this.run0();
		} catch (Throwable e) {
			Reflectionu.<Error>ignite(e);
		}
	}

	/**
	 * When an object implementing interface Runnable is used to create a thread, starting the thread causes the object's run method to be called in
	 * that separately executing thread. The general contract of the method run is that it may take any action whatsoever.
	 *
	 * @throws E the exception that this runnable throws
	 */
	void run0() throws E;
}
