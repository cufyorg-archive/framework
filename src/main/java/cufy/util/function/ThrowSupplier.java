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

import cufy.util.Reflection;

import java.util.function.Supplier;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <T> the type of results supplied by this supplier.
 * @param <E> the exception.
 * @author LSafer
 * @version 0.1.3
 * @since 0.1.0 ~2020.02.13
 */
@FunctionalInterface
public interface ThrowSupplier<T, E extends Throwable> extends Supplier<T> {
	@Override
	default T get() {
		try {
			return this.get0();
		} catch (Throwable e) {
			throw Reflection.ignite(e);
		}
	}

	/**
	 * Gets a result.
	 *
	 * @return a result.
	 * @throws E the exception.
	 */
	T get0() throws E;
}