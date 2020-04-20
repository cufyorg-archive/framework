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

import java.util.function.UnaryOperator;

/**
 * Functional Interface that can be specified to throw an exception.
 *
 * @param <T> the type of the operand and result of the operator
 * @param <E> the exception
 * @author lsafer
 * @version 0.1.3
 * @since 13-Feb-2020
 */
@FunctionalInterface
public interface ThrowUnaryOperator<T, E extends Throwable> extends UnaryOperator<T> {
	@Override
	default T apply(T t) {
		try {
			return this.apply0(t);
		} catch (Throwable e) {
			throw Reflection.<Error>ignite(e);
		}
	}

	/**
	 * Applies this function to the given argument.
	 *
	 * @param t the function argument
	 * @return the function result
	 * @throws E the exception
	 */
	T apply0(T t) throws E;
}
