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
package cufy.util.shorts;

import cufy.util.PrimitiveComparator;
import cufy.util.shorts.function.ShortToDoubleFunction;
import cufy.util.shorts.function.ShortToIntFunction;
import cufy.util.shorts.function.ShortToLongFunction;
import cufy.util.shorts.function.ShortUnaryOperator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A comparator specialized for {@code short} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface ShortComparator extends PrimitiveComparator<
		Short,
		ShortUnaryOperator,
		ShortToDoubleFunction,
		ShortToIntFunction,
		ShortToLongFunction,
		ShortComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	ShortComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	ShortComparator REVERSE = new ReverseOrder();

	/**
	 * Accepts a function that extracts a key, and returns a {@code Comparator<T>} that compares by
	 * that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param operator the function used to extract the key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code operator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static ShortComparator comparing(ShortUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (ShortComparator & Serializable) (v, o) -> Short.compare(
				operator.applyAsShort(v),
				operator.applyAsShort(o)
		);
	}

	/**
	 * Accepts a function that extracts a key and returns a {@code Comparator<T>} that compares by
	 * that key using the specified {@link Comparator}.
	 * <p>
	 * The returned comparator is serializable if the specified function and comparator are both
	 * serializable.
	 *
	 * @param operator   the function used to extract the key.
	 * @param comparator the {@code Comparator} used to compare the key.
	 * @return a comparator that compares by an extracted key using the specified {@code
	 * 		Comparator}.
	 * @throws NullPointerException if the given {@code operator} or {@code comparator} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static ShortComparator comparing(ShortUnaryOperator operator, ShortComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (ShortComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsShort(v),
				operator.applyAsShort(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code double} key, and returns a {@code Comparator<T>}
	 * that compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the double key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static ShortComparator comparingDouble(ShortToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (ShortComparator & Serializable) (v, o) -> Double.compare(
				function.applyAsDouble(v),
				function.applyAsDouble(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code int} key, and returns a {@code Comparator<T>} that
	 * compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the int key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static ShortComparator comparingInt(ShortToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (ShortComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsInt(v),
				function.applyAsInt(o)
		);
	}

	/**
	 * Accepts a function that extracts a {@code long} key, and returns a {@code Comparator<T>} that
	 * compares by that key.
	 * <p>
	 * The returned comparator is serializable if the specified function is serializable.
	 *
	 * @param function the function used to extract the long key.
	 * @return a comparator that compares by an extracted key.
	 * @throws NullPointerException if the given {@code function} is null.
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("LambdaUnfriendlyMethodOverload")
	static ShortComparator comparingLong(ShortToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (ShortComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code short}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code short}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static ShortComparator naturalOrder() {
		return ShortComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		short}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static ShortComparator reverseOrder() {
		return ShortComparator.REVERSE;
	}

	@Override
	default int compare(Short value, Short other) {
		return this.compare((short) value, (short) other);
	}

	@Override
	default ShortComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default ShortComparator thenComparing(ShortComparator other) {
		Objects.requireNonNull(other, "other");
		return (ShortComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default ShortComparator thenComparing(Comparator<? super Short> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof ShortComparator ?
				(ShortComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> ShortComparator thenComparing(Function<? super Short, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof ShortUnaryOperator ?
			   this.thenComparing((ShortUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default ShortComparator thenComparing(ShortUnaryOperator operator) {
		return this.thenComparing(ShortComparator.comparing(
				operator
		));
	}

	@Override
	default <R> ShortComparator thenComparing(Function<? super Short, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof ShortUnaryOperator && comparator instanceof ShortComparator ?
			   this.thenComparing((ShortUnaryOperator) function, (ShortComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default ShortComparator thenComparing(ShortUnaryOperator operator, ShortComparator comparator) {
		return this.thenComparing(ShortComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default ShortComparator thenComparingDouble(ToDoubleFunction<? super Short> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof ShortToDoubleFunction ?
				(ShortToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default ShortComparator thenComparingDouble(ShortToDoubleFunction function) {
		return this.thenComparing(ShortComparator.comparingDouble(
				function
		));
	}

	@Override
	default ShortComparator thenComparingInt(ToIntFunction<? super Short> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof ShortToIntFunction ?
				(ShortToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default ShortComparator thenComparingInt(ShortToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(ShortComparator.comparingInt(
				function
		));
	}

	@Override
	default ShortComparator thenComparingLong(ToLongFunction<? super Short> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof ShortToLongFunction ?
				(ShortToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default ShortComparator thenComparingLong(ShortToLongFunction function) {
		return this.thenComparing(ShortComparator.comparingLong(
				function
		));
	}

	/**
	 * Compares its two arguments for order.  Returns a negative integer, zero, or a positive
	 * integer as the first argument is less than, equal to, or greater than the second.
	 *
	 * @param value the first argument to be compared.
	 * @param other the second argument to be compared.
	 * @return a negative integer, zero, or a positive integer as the first argument is less than,
	 * 		equal to, or greater than the second.
	 * @since 0.1.5 ~2020.09.01
	 */
	int compare(short value, short other);

	/**
	 * An {@link ShortComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements ShortComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(short value, short other) {
			return Short.compare(value, other);
		}

		@Override
		public ShortComparator reversed() {
			return ShortComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link ShortComparator} based on another {@link ShortComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements ShortComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final ShortComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(ShortComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(short value, short other) {
			return this.comparator.compare(other, value);
		}

		@Override
		public boolean equals(Object object) {
			return object == this ||
				   object instanceof Reverse &&
				   ((Reverse) object).comparator.equals(this.comparator);
		}

		@Override
		public int hashCode() {
			return this.comparator.hashCode() ^ Integer.MIN_VALUE;
		}

		@Override
		public ShortComparator reversed() {
			return this.comparator;
		}
	}

	/**
	 * Reversed <i>natural ordering</i> comparator.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class ReverseOrder implements ShortComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(short value, short other) {
			return Short.compare(other, value);
		}

		@Override
		public ShortComparator reversed() {
			return ShortComparator.NATURAL;
		}
	}
}
