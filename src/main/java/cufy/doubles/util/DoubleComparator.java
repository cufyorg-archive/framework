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
package cufy.doubles.util;

import cufy.util.PrimitiveComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.*;

/**
 * A comparator specialized for {@code double} values.
 *
 * @author LSafer
 * @version 0.1.5
 * @since 0.1.5 ~2020.09.01
 */
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "ComparatorNotSerializable"})
public interface DoubleComparator extends PrimitiveComparator<
		Double,
		DoubleUnaryOperator,
		DoubleUnaryOperator,
		DoubleToIntFunction,
		DoubleToLongFunction,
		DoubleComparator
		> {
	/**
	 * A global instance of {@link NaturalOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	DoubleComparator NATURAL = new NaturalOrder();
	/**
	 * A global instance of {@link ReverseOrder}.
	 *
	 * @since 0.1.5 ~2020.09.01
	 */
	DoubleComparator REVERSE = new ReverseOrder();

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
	static DoubleComparator comparing(DoubleUnaryOperator operator) {
		Objects.requireNonNull(operator, "operator");
		return (DoubleComparator & Serializable) (v, o) -> Double.compare(
				operator.applyAsDouble(v),
				operator.applyAsDouble(o)
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
	static DoubleComparator comparing(DoubleUnaryOperator operator, DoubleComparator comparator) {
		Objects.requireNonNull(operator, "operator");
		Objects.requireNonNull(comparator, "comparator");
		return (DoubleComparator & Serializable) (v, o) -> comparator.compare(
				operator.applyAsDouble(v),
				operator.applyAsDouble(o)
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
	static DoubleComparator comparingInt(DoubleToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return (DoubleComparator & Serializable) (v, o) -> Long.compare(
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
	static DoubleComparator comparingLong(DoubleToLongFunction function) {
		Objects.requireNonNull(function, "function");
		return (DoubleComparator & Serializable) (v, o) -> Long.compare(
				function.applyAsLong(v),
				function.applyAsLong(o)
		);
	}

	/**
	 * Returns a comparator that compares {@code double}s in natural order.
	 * <p>
	 * The returned comparator is serializable.
	 *
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code double}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static DoubleComparator naturalOrder() {
		return DoubleComparator.NATURAL;
	}

	/**
	 * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
	 * <p>
	 * The returned comparator is serializable..
	 *
	 * @return a comparator that imposes the reverse of the <i>natural ordering</i> on {@code
	 * 		double}s.
	 * @since 0.1.5 ~2020.09.01
	 */
	static DoubleComparator reverseOrder() {
		return DoubleComparator.REVERSE;
	}

	@Override
	default int compare(Double value, Double other) {
		return this.compare((double) value, (double) other);
	}

	@Override
	default DoubleComparator reversed() {
		return new Reverse(this);
	}

	@Override
	default DoubleComparator thenComparing(DoubleComparator other) {
		Objects.requireNonNull(other, "other");
		return (DoubleComparator & Serializable) (v, o) -> {
			int r = this.compare(v, o);
			return r == 0 ? other.compare(v, o) : r;
		};
	}

	@Override
	default DoubleComparator thenComparing(Comparator<? super Double> other) {
		Objects.requireNonNull(other, "other");
		return this.thenComparing(
				other instanceof DoubleComparator ?
				(DoubleComparator) other :
				other::compare
		);
	}

	@Override
	default <R extends Comparable<? super R>> DoubleComparator thenComparing(Function<? super Double, ? extends R> function) {
		//noinspection RedundantComparatorComparing
		return function instanceof DoubleUnaryOperator ?
			   this.thenComparing((DoubleUnaryOperator) function) :
			   this.thenComparing(Comparator.comparing(function));
	}

	@Override
	default DoubleComparator thenComparing(DoubleUnaryOperator operator) {
		return this.thenComparing(DoubleComparator.comparing(
				operator
		));
	}

	@Override
	default <R> DoubleComparator thenComparing(Function<? super Double, ? extends R> function, Comparator<? super R> comparator) {
		//noinspection RedundantComparatorComparing
		return function instanceof DoubleUnaryOperator && comparator instanceof DoubleComparator ?
			   this.thenComparing((DoubleUnaryOperator) function, (DoubleComparator) comparator) :
			   this.thenComparing(Comparator.comparing(function, comparator));
	}

	@Override
	default DoubleComparator thenComparing(DoubleUnaryOperator operator, DoubleComparator comparator) {
		return this.thenComparing(DoubleComparator.comparing(
				operator,
				comparator
		));
	}

	@Override
	default DoubleComparator thenComparingDouble(ToDoubleFunction<? super Double> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(
				function instanceof DoubleUnaryOperator ?
				(DoubleUnaryOperator) function :
				function::applyAsDouble
		);
	}

	@Override
	default DoubleComparator thenComparingDouble(DoubleUnaryOperator function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(DoubleComparator.comparing(
				function
		));
	}

	@Override
	default DoubleComparator thenComparingInt(ToIntFunction<? super Double> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingInt(
				function instanceof DoubleToIntFunction ?
				(DoubleToIntFunction) function :
				function::applyAsInt
		);
	}

	@Override
	default DoubleComparator thenComparingInt(DoubleToIntFunction function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparing(DoubleComparator.comparingInt(
				function
		));
	}

	@Override
	default DoubleComparator thenComparingLong(ToLongFunction<? super Double> function) {
		Objects.requireNonNull(function, "function");
		return this.thenComparingLong(
				function instanceof DoubleToLongFunction ?
				(DoubleToLongFunction) function :
				function::applyAsLong
		);
	}

	@Override
	default DoubleComparator thenComparingLong(DoubleToLongFunction function) {
		return this.thenComparing(DoubleComparator.comparingLong(
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
	int compare(double value, double other);

	/**
	 * An {@link DoubleComparator} that follows the natural ordering.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	class NaturalOrder implements DoubleComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -8066895119922404900L;

		@Override
		public int compare(double value, double other) {
			return Double.compare(value, other);
		}

		@Override
		public DoubleComparator reversed() {
			return DoubleComparator.REVERSE;
		}
	}

	/**
	 * A reversed {@link DoubleComparator} based on another {@link DoubleComparator}.
	 *
	 * @author LSafer
	 * @version 0.1.5
	 * @since 0.1.5 ~2020.09.01
	 */
	@SuppressWarnings("ClassHasNoToStringMethod")
	class Reverse implements DoubleComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = -5427742021938589022L;

		/**
		 * The comparator backing this comparator. The comparator will be reversed when used.
		 *
		 * @since 0.1.5 ~2020.09.01
		 */
		protected final DoubleComparator comparator;

		/**
		 * Construct a new reversed comparator reversing the given {@code comparator}.
		 *
		 * @param comparator the comparator the constructed comparator is backed by the reverse of
		 *                   it.
		 * @throws NullPointerException if the given {@code comparator} is null.
		 * @since 0.1.5 ~2020.09.01
		 */
		public Reverse(DoubleComparator comparator) {
			Objects.requireNonNull(comparator, "comparator");
			this.comparator = comparator;
		}

		@Override
		public int compare(double value, double other) {
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
		public DoubleComparator reversed() {
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
	class ReverseOrder implements DoubleComparator, Serializable {
		@SuppressWarnings("JavaDoc")
		private static final long serialVersionUID = 2243904103573461375L;

		@Override
		public int compare(double value, double other) {
			return Double.compare(other, value);
		}

		@Override
		public DoubleComparator reversed() {
			return DoubleComparator.NATURAL;
		}
	}
}
