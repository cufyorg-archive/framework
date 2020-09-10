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
package cufy.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialize for primitive values.
 *
 * @param <E> the type of the elements.
 * @param <D> the type of the toDoubleFunction.
 * @param <I> the type of the toIntFunction.
 * @param <L> the type of the toLongFunction.
 * @param <U> the type of the unary operator.
 * @param <T> the ype of the comparator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveComparator
		<E, D, I, L, U, T extends PrimitiveComparator<E, D, I, L, U, T>>
		extends
		Comparator<E> {
	@Override
	T reversed();

	@Override
	<R extends Comparable<? super R>> T thenComparing(Function<? super E, ? extends R> function);

	@Override
	<R> T thenComparing(Function<? super E, ? extends R> function, Comparator<? super R> comparator);

	@Override
	T thenComparing(Comparator<? super E> other);

	@Override
	T thenComparingDouble(ToDoubleFunction<? super E> function);

	@Override
	T thenComparingInt(ToIntFunction<? super E> function);

	@Override
	T thenComparingLong(ToLongFunction<? super E> function);

	/**
	 * Returns a lexicographic-order comparator with another comparator. If this {@code Comparator}
	 * considers two elements equal, i.e. {@code compare(a, b) == 0}, {@code other} is used to
	 * determine the order.
	 * <p>
	 * The returned comparator is serializable if the specified comparator is serializable.
	 *
	 * @param other the other comparator to be used when this comparator compares two objects that
	 *              are equal.
	 * @return a lexicographic-order comparator composed of this and then the other comparator.
	 * @throws NullPointerException if the given {@code other} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparing(T other);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a key.
	 *
	 * @param operator the function used to extract the key.
	 * @return a lexicographic-order comparator composed of this and then the key.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparing(U operator);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a key to be compared
	 * with the given {@code Comparator}.
	 *
	 * @param operator   the function used to extract the key.
	 * @param comparator the {@code Comparator} used to compare the key.
	 * @return a lexicographic-order comparator composed of this comparator and then comparing on
	 * 		the key extracted by the given {@code function}.
	 * @throws NullPointerException if the given {@code operator} or {@code comparator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparing(U operator, T comparator);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code double} key.
	 *
	 * @param function the function used to extract the double key.
	 * @return a lexicographic-order comparator composed of this and then the {@code double} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparingDouble(D function);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code int} key.
	 *
	 * @param function the function used to extract the int key.
	 * @return a lexicographic-order comparator composed of this and then the {@code int} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparingInt(I function);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code long} key.
	 *
	 * @param function the function used to extract the long key.
	 * @return a lexicographic-order comparator composed of this and then the {@code long} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	T thenComparingLong(L function);

	//int compare(primitive value, primitive other)
}
