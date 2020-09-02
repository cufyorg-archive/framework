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
package cufy.longs.util;

import cufy.util.PrimitiveComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.*;

/**
 * A comparator specialized for {@code long} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface LongComparator extends PrimitiveComparator<
		Long,
		LongUnaryOperator,
		LongToDoubleFunction,
		LongToIntFunction,
		LongUnaryOperator,
		LongComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	LongComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	LongComparator REVERSE = new ReverseOrder();

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
	static LongComparator comparing(LongUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (LongComparator & Serializable) (v, o) -> Long.compare(
				operator.applyAsLong(v),
				operator.applyAsLong(o)
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
	static LongComparator comparing(LongUnaryOperator operator, LongComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (LongComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsLong(v),
				operator.applyAsLong(o)
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
	static LongComparator comparingDouble(LongToDoubleFunction function) {
		Objects.requireNonNull(function, "function");
		return (LongComparator & Serializable) (v, o) -> Double.compare(
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
	static LongComparator comparingInt(LongToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (LongComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsInt(v),
				function.applyAsInt(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code long}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code long}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static LongComparator naturalOrder() {
		return LongComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		long}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static LongComparator reverseOrder() {
		return LongComparator.REVERSE;
	}

	@Override
	default int compare(Long value, Long other) {
		return this.compare((long) value, (long) other);
	}

	@Override
	default LongComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default LongComparator thenComparing(LongComparator other) {
		Objects.requireNonNull(other, "other");
		return (LongComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default LongComparator thenComparing(Comparator<? super Long> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof LongComparator ?
				(LongComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> LongComparator thenComparing(Function<? super Long, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof LongUnaryOperator ?
			   this.thenComparing((LongUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default LongComparator thenComparing(LongUnaryOperator operator) {
		return this.thenComparing(LongComparator.comparing(
				operator
		));
	}

	@Override
	default <R> LongComparator thenComparing(Function<? super Long, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof LongUnaryOperator && comparator instanceof LongComparator ?
			   this.thenComparing((LongUnaryOperator) function, (LongComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default LongComparator thenComparing(LongUnaryOperator operator, LongComparator comparator) {
		return this.thenComparing(LongComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default LongComparator thenComparingDouble(ToDoubleFunction<? super Long> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingDouble(
				function instanceof LongToDoubleFunction ?
				(LongToDoubleFunction) function :
				function::applyAsDouble
		);
	}

	@Override
	default LongComparator thenComparingDouble(LongToDoubleFunction function) {
		return this.thenComparing(LongComparator.comparingDouble(
				function
		));
	}

	@Override
	default LongComparator thenComparingInt(ToIntFunction<? super Long> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof LongToIntFunction ?
				(LongToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default LongComparator thenComparingInt(LongToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(LongComparator.comparingInt(
				function
		));
	}

	@Override
	default LongComparator thenComparingLong(ToLongFunction<? super Long> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(
				function instanceof LongUnaryOperator ?
				(LongUnaryOperator) function :
				function::applyAsLong
		);
	}

	@Override
	default LongComparator thenComparingLong(LongUnaryOperator function) {
		return this.thenComparing(LongComparator.comparing(
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
	int compare(long value, long other);

	/**
	 * An {@link LongComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements LongComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(long value, long other) {
			return Long.compare(value, other);
		}

		@Override
		public LongComparator reversed() {
			return LongComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link LongComparator} based on another {@link LongComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements LongComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final LongComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(LongComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(long value, long other) {
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
		public LongComparator reversed() {
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
	class ReverseOrder implements LongComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(long value, long other) {
			return Long.compare(other, value);
		}

		@Override
		public LongComparator reversed() {
			return LongComparator.NATURAL;
		}
	}
}
