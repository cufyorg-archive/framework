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
 * @param <E>          the type of the elements.
 * @param <UNARY>      the type of the unary operator.
 * @param <TO_INT>     the type of the toIntFunction.
 * @param <TO_DOUBLE>  the type of the toDoubleFunction.
 * @param <TO_LONG>    the type of the toLongFunction.
 * @param <COMPARATOR> the ype of the comparator.
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings("ComparatorNotSerializable")
public interface PrimitiveComparator<
		E,
		UNARY,
		TO_DOUBLE,
		TO_INT,
		TO_LONG,
		COMPARATOR extends PrimitiveComparator<E, UNARY, TO_DOUBLE, TO_INT, TO_LONG, COMPARATOR>
		> extends Comparator<E> {
	@Override
	COMPARATOR reversed();

	@Override
	COMPARATOR thenComparing(Comparator<? super E> other);

	@Override
	<R extends Comparable<? super R>> COMPARATOR thenComparing(Function<? super E, ? extends R> function);

	@Override
	<R> COMPARATOR thenComparing(Function<? super E, ? extends R> function, Comparator<? super R> comparator);

	@Override
	COMPARATOR thenComparingDouble(ToDoubleFunction<? super E> function);

	@Override
	COMPARATOR thenComparingInt(ToIntFunction<? super E> function);

	@Override
	COMPARATOR thenComparingLong(ToLongFunction<? super E> function);

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
	COMPARATOR thenComparing(COMPARATOR other);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a key.
	 *
	 * @param operator the function used to extract the key.
	 * @return a lexicographic-order comparator composed of this and then the key.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	COMPARATOR thenComparing(UNARY operator);

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
	COMPARATOR thenComparing(UNARY operator, COMPARATOR comparator);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code double} key.
	 *
	 * @param function the function used to extract the double key.
	 * @return a lexicographic-order comparator composed of this and then the {@code double} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	COMPARATOR thenComparingDouble(TO_DOUBLE function);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code int} key.
	 *
	 * @param function the function used to extract the int key.
	 * @return a lexicographic-order comparator composed of this and then the {@code int} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	COMPARATOR thenComparingInt(TO_INT function);

	/**
	 * Returns a lexicographic-order comparator with a function that extracts a {@code long} key.
	 *
	 * @param function the function used to extract the long key.
	 * @return a lexicographic-order comparator composed of this and then the {@code long} key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	COMPARATOR thenComparingLong(TO_LONG function);
}
